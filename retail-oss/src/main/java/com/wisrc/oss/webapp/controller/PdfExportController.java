package com.wisrc.oss.webapp.controller;

import com.google.gson.Gson;
import com.wisrc.oss.webapp.service.ObsObjectService;
import com.wisrc.oss.webapp.service.PdfService;
import com.wisrc.oss.webapp.utils.Result;
import com.wisrc.oss.webapp.vo.HtmlContentVO;
import com.wisrc.oss.webapp.vo.KeyValue;
import com.wisrc.oss.webapp.vo.RenderHTMLDataVo;
import freemarker.core.InvalidReferenceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


@Controller
@Api(tags = "PDF转换与导出服务")
@RequestMapping(value = "/images")
public class PdfExportController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ObsObjectService obsObjectService;

    @ResponseBody
    @Deprecated
    @ApiOperation(value = "根据HTML内容，导出PDF", notes = "可以通过在模板中设置替换变量来修改模板中的值")
    @RequestMapping(value = "/pdf/html", method = RequestMethod.POST)
    public void exportPdf(HttpServletResponse response, @RequestBody(required = false) HtmlContentVO htmlContentVO) {

        response.setContentType("application/pdf");

        String html = htmlContentVO.getHtml();
        for (KeyValue ele : htmlContentVO.getKeyValues()) {
            html = htmlContentVO.getHtml().replaceAll(ele.getKey(), ele.getValue());
        }

        Result result = pdfService.exportPdf(response, html);

        if (result.getCode() != 200) {
            writeError(response, result);
        }
    }

    @ResponseBody
    @Deprecated
    @ApiOperation(value = "根据OBS存储桶中存储的模板导出PDF", notes = "可以通过在模板中设置替换变量来修改模板中的值")
    @RequestMapping(value = "/pdf/obs", method = RequestMethod.POST)
    public void exportPdf(HttpServletResponse response, @RequestParam("obsName") String obsName, @RequestParam("fd") String fd, @RequestBody(required = false) KeyValue[] keyValue) {

        response.setContentType("application/pdf;charset=utf-8");

        Result obsResult = obsObjectService.findFileByKey(obsName, fd);

        Map<String, Object> map = (HashMap<String, Object>) obsResult.getData();

        byte[] bytes = (byte[]) map.get("data");

        String html = new String(bytes);

        for (KeyValue ele : keyValue) {
            html = html.replaceAll(ele.getKey(), ele.getValue());
        }

        Result result = pdfService.exportPdf(response, html);

        if (result.getCode() != 200) {
            writeError(response, result);
        }

        return;
    }


    @ResponseBody
    @ApiOperation(value = "根据HTML内容，导出PDF(新）", notes = "可以通过在模板中设置替换变量来修改模板中的值,支持freemarker模板和HTML<br/>" +
            "HTML为字符串替换，key->value,支持正则表达式，替换的顺序不指定<br/>" +
            "FreeMarker为需要所有的模板值均在data中有对应<br/>" +
            "两种模板都需要闭合所有的HTML标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateType", value = "模板类型", dataType = "string", paramType = "path", allowableValues = "HTML,FreeMarker", required = true),
            @ApiImplicitParam(name = "renderHTMLDataVo", value = "渲染内容实体", required = true, dataType = "RenderHTMLDataVo")
    })
    @RequestMapping(value = "/generatePdf/from{templateType:(?:HTML|FreeMarker)}", method = RequestMethod.POST)
    public void exportPdfV2(HttpServletResponse response,
                            @RequestBody RenderHTMLDataVo renderHTMLDataVo,
                            @PathVariable String templateType) {
        String htmlContent = renderHTMLDataVo.getHtmlContent();
        Map<String, Object> data = renderHTMLDataVo.getData();
        renderPDF(response, templateType, htmlContent, data);
    }

    @ResponseBody
    @ApiOperation(value = "根据OBS存储桶中存储的模板导出PDF(新）", notes = "可以通过在模板中设置替换变量来修改模板中的值,支持freemarker模板和HTML<br/>" +
            "HTML为字符串替换，key->value,支持正则表达式，替换的顺序不指定<br/>" +
            "FreeMarker为需要所有的模板值均在data中有对应<br/>" +
            "两种模板都需要闭合所有的HTML标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateType", value = "模板类型", dataType = "string", paramType = "path", allowableValues = "HTML,FreeMarker", required = true),
            @ApiImplicitParam(name = "keyValues", value = "渲染内容键值对", required = true, dataType = "Map")
    })
    @RequestMapping(value = "/generatePdf/fromObsAs{templateType:(?:HTML|FreeMarker)}", method = RequestMethod.POST)
    public void exportPdfV2(HttpServletResponse response,
                            @RequestParam("obsName") String obsName,
                            @RequestParam("fd") String fd,
                            @RequestBody(required = false) Map<String, Object> keyValues,
                            @PathVariable String templateType) {
        if (keyValues == null) {
            keyValues = new HashMap<>();
        }
        response.setContentType("application/pdf;charset=utf-8");
        Result obsResult = obsObjectService.findFileByKey(obsName, fd);
        Object data = obsResult.getData();
        //这里获得的文件如果不存在，则返回的是一张图片，需要判断类型
        if (!(data instanceof Map)) {
            writeError(response, Result.failure(423, "文件不存在", null));
            return;
        }
        Map<String, Object> map = (HashMap<String, Object>) obsResult.getData();
        byte[] bytes = (byte[]) map.get("data");
        renderPDF(response, templateType, new String(bytes), keyValues);

    }

    private void renderPDF(HttpServletResponse response, String templateType, String htmlContent, Map<String, Object> data) {
        String htmlForRender = "<body></body>";//如果未知的错误导致直接进行了渲染，那么完整的标签可以保证不出错并且没有内容
        switch (templateType) {
            case "FreeMarker": {
                try (StringWriter sw = new StringWriter()) {
                    Template template =
                            new Template("template",
                                    new StringReader(htmlContent),
                                    new Configuration(Configuration.VERSION_2_3_27),
                                    "UTF-8");
                    template.process(data, sw);
                    sw.flush();
                    htmlForRender = sw.toString();
                } catch (InvalidReferenceException e) {
                    writeError(response, Result.failure(423, String.format("字段%s没有定义", e.getBlamedExpressionString()), null));
                    return;
                } catch (IOException e) {
                    writeError(response, Result.failure(423, e.getMessage(), null));
                    return;
                } catch (TemplateException e) {
                    writeError(response, Result.failure(423, "渲染模板数据错误" + e.getMessage(), null));
                    return;
                }
            }
            break;
            case "HTML": {
                for (String key : data.keySet()) {
                    Object o = data.get(key);
                    if (!(o instanceof String)) {
                        writeError(response, Result.failure(423, key + "对应的值不是String类型", null));
                        return;
                    }
                    String value = (String) o;
                    htmlContent = htmlContent.replaceAll(key, value);
                }
                htmlForRender = htmlContent;
            }
            break;
            default:
                writeError(response, Result.failure(423, "错误的模板类型" + templateType, null));
                return;
        }
        response.setContentType("application/pdf");
        Result result = pdfService.exportPdfFromHtml(response, htmlForRender);
        if (result.getCode() != 200) {
            writeError(response, result);
        }
    }

    private void writeError(HttpServletResponse response, Result result) {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        try (Writer writer = response.getWriter()) {
            writer.write(gson.toJson(result));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
