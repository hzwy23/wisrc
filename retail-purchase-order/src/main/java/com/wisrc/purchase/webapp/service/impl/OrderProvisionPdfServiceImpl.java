package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.dto.purchasePlan.PayManInfoDto;
import com.wisrc.purchase.webapp.entity.ProducPackingInfoEntity;
import com.wisrc.purchase.webapp.entity.ProductDeliveryInfoEntity;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.utils.CnUpperCaser;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.OrderBasisInfoVO;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderPdfProdictVO;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderPdfVO;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrderProvisionPdfServiceImpl implements OrderProvisionPdfService {
    private static final String TEMPLATES_ORDER_PROVISION_HTML = "orderProvision.html";
    //常见的html标签    来自http://www.w3school.com.cn/tags/index.asp
    private static final String[] FREQUENT_VALID_HTML_TAGS
            = {"!--...--", "!DOCTYPE", "a", "abbr", "acronym", "address", "applet", "area", "article", "aside", "audio", "b",
            "base", "basefont", "bdi", "bdo", "big", "blockquote", "body", "br", "button", "canvas", "caption", "center", "cite",
            "code", "col", "colgroup", "command", "datalist", "dd", "del", "details", "dir", "div", "dfn", "dialog", "dl", "dt",
            "em", "embed", "fieldset", "figcaption", "figure", "font", "footer", "form", "frame", "frameset", "h1", "h6", "head",
            "header", "hr", "html", "i", "iframe", "img", "input", "ins", "isindex", "kbd", "keygen", "label", "legend", "li",
            "link", "map", "mark", "menu", "menuitem", "meta", "meter", "nav", "noframes", "noscript", "object", "ol", "optgroup",
            "option", "output", "p", "param", "pre", "progress", "q", "rp", "rt", "ruby", "s", "samp", "script", "section", "select",
            "small", "source", "span", "strike", "strong", "style", "sub", "summary", "details", "sup", "table", "tbody", "td",
            "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "tt", "u", "ul", "var", "video", "wbr", "xmp"};
    private static final ClassLoaderTemplateResolver templateResolver;

    static {
        templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setPrefix("/templates/");
        templateResolver.setCharacterEncoding("UTF-8");
    }

    @Autowired
    private PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OrderProvisionService orderProvisionService;
    @Autowired
    private ProductHandleService productHandleService;
    @Autowired
    private SupplierOutsideService supplierOutsideService;

    @Override
    public String createPdf(String orderId, String payee, String bank, String account) {
        OrderPdfVO pdfInfo = findPdfInfo(orderId, new PayManInfoDto(payee, bank, account));
        return renderTemplate(pdfInfo, TEMPLATES_ORDER_PROVISION_HTML);
    }

    @Override
    public OrderPdfVO findPdfInfo(String orderId, PayManInfoDto payManInfoDto) {
        DecimalFormat moneyFormat = new DecimalFormat(",##0.00");//钱款格式
        OrderPdfVO vo = new OrderPdfVO();
        OrderBasisInfoVO basisInfoById = purchcaseOrderBasisInfoService.findBasisInfoByIdNeet(orderId);
        try {
            //调整获取供应商地址代码
            Map supplierResult = supplierOutsideService.getSupplierInfo(basisInfoById.getSupplierId());
            String success = String.valueOf(supplierResult.get("success"));
            if ("true".equalsIgnoreCase(success)) {
                Map supplier = (Map) supplierResult.get("supplier");
                Map supplierInfo = (Map) supplier.get("supplierInfo");
                String province = (String) supplierInfo.get("province");
                String city = (String) supplierInfo.get("city");
                String county = (String) supplierInfo.get("county");//县，乡村
                String street = (String) supplierInfo.get("street");
                String supplierAddress =
                        (province == null ? "" : province) +
                                (city == null ? "" : city) +
                                (county == null ? "" : county) +
                                (street == null ? "" : street);
                vo.setSupplierAddress(supplierAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
            vo.setSupplierAddress("");
        }

        Map map = new HashMap();
        Result companyInfo = companyService.getCompanyInfo();
        if (companyInfo.getCode() == 200) {
            map = (Map) companyInfo.getData();
        }
        vo.setCompanyTelephone((String) map.get("companyTelephone"));
        vo.setCompanyName((String) map.get("companyName"));
        vo.setCompanyAddress((String) map.get("companyAddress"));
        BeanUtils.copyProperties(basisInfoById, vo);
        vo.setBillDate(basisInfoById.getBillDate());
        vo.setSignedOnDate(basisInfoById.getBillDate());
        vo.setPurchaser(basisInfoById.getEmployeeName());
//        合计
        if (basisInfoById.getTotalAmount() == 0) {
            vo.setTotalAmount("");
        } else {
            /*DecimalFormat df = new DecimalFormat(",##0.00");
            String total = df.format(basisInfoById.getTotalAmount());
            if (basisInfoById.getTotalAmount() < 1 && basisInfoById.getTotalAmount() > -1) {
                total = "0.00" + total;
            }*/
            vo.setTotalAmount(basisInfoById.getAmountWithTax() + "");
        }
        vo.setTotalAmountCN("人民币 " + CnUpperCaser.number2CNMontrayUnit(new BigDecimal(basisInfoById.getTotalAmount())));

//        运费
        vo.setFreight(moneyFormat.format(basisInfoById.getFreight()));

        List<OrderPdfProdictVO> PdfList = new ArrayList<>();
        List skuIds = new ArrayList();
        List<AddDetailsProdictAllVO> detailsList = purchcaseOrderBasisInfoService.findDetailsListNeet(orderId);
        for (AddDetailsProdictAllVO pVo : detailsList) {
            skuIds.add(pVo.getSkuId());
        }
        int num = 1;
        for (AddDetailsProdictAllVO pVo : detailsList) {
            Map productList = productHandleService.getProductList(skuIds);
            Map m = (Map) productList.get(pVo.getSkuId());
            OrderPdfProdictVO opvo = new OrderPdfProdictVO();
            BeanUtils.copyProperties(pVo, opvo);
//            含税单价
            if (pVo.getUnitPriceWithTax() == 0) {
                opvo.setUnitPriceWithTax("");
            } else {
                opvo.setUnitPriceWithTax(new DecimalFormat(",###.##").format(pVo.getUnitPriceWithTax()));
            }
//           不含税单价
            if (pVo.getUnitPriceWithoutTax() == 0) {
                opvo.setUnitPriceWithoutTax("");
            } else {
                opvo.setUnitPriceWithoutTax(new DecimalFormat(",###.##").format(pVo.getUnitPriceWithoutTax()));
            }
//            含税金额
            if (pVo.getAmountWithTax() == 0) {
                opvo.setAmountWithTax("");
            } else {
                opvo.setAmountWithTax(moneyFormat.format(pVo.getAmountWithTax()));
            }
//            不含税金额
            if (pVo.getAmountWithoutTax() == 0) {
                opvo.setAmountWithoutTax("");
            } else {
                opvo.setAmountWithoutTax(moneyFormat.format(pVo.getAmountWithoutTax()));
            }
            opvo.setNum(num);
            ProducPackingInfoEntity producPackingInfoEntity = pVo.getProducPackingInfoEn();
            List<ProductDeliveryInfoEntity> productDeliveryInfoEnList = pVo.getProductDeliveryInfoEnList();
            //装箱率
            if (producPackingInfoEntity.getPackingRate() == 0) {
                opvo.setPackingRate("");
            } else {
                opvo.setPackingRate(String.valueOf(new Double(producPackingInfoEntity.getPackingRate()).intValue()));
            }
//            外箱 长X宽X高 (CM)
            if (producPackingInfoEntity.getPackLong() == 0 && producPackingInfoEntity.getPackWide() == 0 && producPackingInfoEntity.getPackHigh() == 0) {
                opvo.setProducPackingInfoEn("");
            } else {
                StringBuilder sb = new StringBuilder();
                DecimalFormat df = new DecimalFormat("###.#");
                sb.append(df.format(producPackingInfoEntity.getPackLong()));
                sb.append("*");
                sb.append(df.format(producPackingInfoEntity.getPackWide()));
                sb.append("*");
                sb.append(df.format(producPackingInfoEntity.getPackHigh()));
                opvo.setProducPackingInfoEn(sb.toString());
            }
            StringBuffer bf = new StringBuffer();
            for (ProductDeliveryInfoEntity en : productDeliveryInfoEnList) {
                if (en.getDeliveryTime() != null) {
                    bf.append(en.getDeliveryTime().toString()).append("\n");
                }
            }
            opvo.setDeliveryTime(bf.toString());
            if (opvo.getSkuType() == null) {
                opvo.setSkuType("");
            }
            opvo.setSkuName((String) m.get("declareNameZh"));
            opvo.setUnit((String) m.get("issuingOffice"));
            opvo.setSkuType((String) m.get("model"));
            PdfList.add(opvo);
            num += 1;
        }
        //查询订单条款并且条款供应商相关收款人，银行等信息
        OrderProvisionVO orderProvision = orderProvisionService.findOrderProvision(orderId);
        String provisionContent = orderProvision.getProvisionContent();
        if (provisionContent != null) {
             /*  变更为前端传来的值 使用 收款人：${payee}     开户银行：${bank}    银行账号：${account} 替换
                if (orderProvision.getPayee() != null) {
                    provisionContent = provisionContent.replaceAll("供应商收款人", orderProvision.getPayee());
                }
                if (orderProvision.getBank() != null) {
                    provisionContent = provisionContent.replaceAll("供应商开户银行", orderProvision.getBank());
                }
                if (orderProvision.getAccount() != null) {
                    provisionContent = provisionContent.replaceAll("供应商银行账户", orderProvision.getAccount());
                }*/
            provisionContent = provisionContent
                    .replaceAll("\\$\\{payee}", payManInfoDto.getPayee() == null ? "" : payManInfoDto.getPayee())
                    .replaceAll("\\$\\{bank}", payManInfoDto.getBank() == null ? "" : payManInfoDto.getBank())
                    .replaceAll("\\$\\{account}", payManInfoDto.getAccount() == null ? "" : payManInfoDto.getAccount());
            //处理非闭合的标签
            provisionContent = handleNoneCloseHTMLTag(provisionContent);
        }
        vo.setOrderProvision(provisionContent);
        vo.setList(PdfList);
        BeanUtils.copyProperties(vo, basisInfoById);
        if (vo.getOrderProvision() == null) {
            vo.setOrderProvision("");
        }
        return vo;
    }

    /**
     * 处理其中因用户输入非闭合的标签
     *
     * @param provisionContent
     * @return
     */
    private String handleNoneCloseHTMLTag(String provisionContent) {
        if (null == provisionContent || provisionContent.trim().isEmpty()) {
            return provisionContent;
        }
        String[] split = provisionContent.split("");
        List<Integer> needToReplaceLeftCharIndexList = new ArrayList<>();
        //处理单独或者连续的  <
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("<")) {
                if (!needToReplaceLeftCharIndexList.isEmpty()) {
                    for (Integer index : needToReplaceLeftCharIndexList) {
                        split[index] = "&lt;";
                    }
                    needToReplaceLeftCharIndexList.clear();
                }
                needToReplaceLeftCharIndexList.add(i);
            }
            if (split[i].equals(">") && needToReplaceLeftCharIndexList.size() > 0) {
                needToReplaceLeftCharIndexList.remove(needToReplaceLeftCharIndexList.size() - 1);
            }
        }
        //处理单独的  >
        boolean hasReplace = true;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("<")) {
                hasReplace = false;
            }
            if (split[i].equals(">") && hasReplace) {
                split[i] = "&gt;";
                hasReplace = true;
            }
        }
        String tmp = String.join("", split);
        //处理匹配的 < > 的非法标签
        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(tmp);
        while (matcher.find()) {
            if (matcher.group(1) == null || matcher.group(1).trim().isEmpty()) {
                tmp = tmp.replace("<" + matcher.group(1) + ">", "&lt;" + matcher.group(1) + "&gt;");
                continue;
            }
            boolean match = false;
            for (String tag : FREQUENT_VALID_HTML_TAGS) {
                if (matcher.group(1).toLowerCase().contains(tag)) {
                    match = true;
                    break;
                }
            }
            if (match) {
                continue;
            }
            tmp = tmp.replace("<" + matcher.group(1) + ">", "&lt;" + matcher.group(1) + "&gt;");
        }
        return tmp;
    }

    /**
     * freemarker渲染html
     */
    private String renderTemplate(OrderPdfVO vo, String htmlTemplate) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariable("data", vo);
        return templateEngine.process(htmlTemplate, context);
    }
}