package com.wisrc.oss.webapp.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.lowagie.text.DocumentException;
import com.wisrc.oss.webapp.service.PdfService;
import com.wisrc.oss.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Service
public class PdfServiceImpl implements PdfService {
    private final Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

    @Override
    public Result exportPdf(HttpServletResponse response, String html) {

        Document document = new Document(PageSize.A4);

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            document.addAuthor("smartdo");

            document.addCreator("smartdo");

            document.addSubject("matrix");

            document.addCreationDate();

            document.addTitle("Matrix 系统导出PDF文件");
            OSSFontsProvider fontsProvider = new OSSFontsProvider();
            fontsProvider.addFontSubstitute("lowagie", "garamond");
            fontsProvider.setUseUnicode(true);
            CssAppliers cssAppliers = new CssAppliersImpl(fontsProvider);
            HtmlPipelineContext htmlcontext = new HtmlPipelineContext(cssAppliers);
            htmlcontext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.getDefaultCssResolver(true);
            worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(html.getBytes("UTF-8")), (InputStream) null, new OSSFontsProvider());
            document.close();
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure(423, e.getMessage(), e);
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }

    @Override
    public Result exportPdfFromHtml(HttpServletResponse response, String html) {
        ITextRenderer iTextRenderer = new ITextRenderer();

        try {
            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            String windir = System.getenv("windir");
            String fileSeparator = System.getProperty("file.separator");
            if (windir != null && fileSeparator != null) {
                addFontDirectory(windir + fileSeparator + "fonts", BaseFont.NOT_EMBEDDED, fontResolver);
            }
            addFontDirectory("/usr/share/X11/fonts", BaseFont.EMBEDDED, fontResolver);
            addFontDirectory("/usr/X/lib/X11/fonts", BaseFont.EMBEDDED, fontResolver);
            addFontDirectory("/usr/openwin/lib/X11/fonts", BaseFont.EMBEDDED, fontResolver);
            addFontDirectory("/usr/share/fonts", BaseFont.EMBEDDED, fontResolver);
            addFontDirectory("/usr/X11R6/lib/X11/fonts", BaseFont.EMBEDDED, fontResolver);
            addFontDirectory("/Library/Fonts", BaseFont.EMBEDDED, fontResolver);
            addFontDirectory("/System/Library/Fonts", BaseFont.EMBEDDED, fontResolver);
        } catch (IOException e) {
            logger.error("字体路径读取异常", e);
            return Result.failure(423, "字体路径读取异常" + e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error("字体解析异常", e);
            return Result.failure(423, "字体解析异常" + e.getMessage(), e);
        }
        iTextRenderer.setPDFVersion(PdfWriter.VERSION_1_7);
        iTextRenderer.setDocumentFromString(html);
        iTextRenderer.layout();
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            iTextRenderer.createPDF(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            return Result.failure(423, "生成pdf错误" + e.getMessage(), e);
        }
        return Result.success();
    }

    /**
     * 因为默认设置的字体编码为西文，需要重写改为<code>BaseFont.IDENTITY_H</code>
     *
     * @param dir
     * @param embedded
     * @param fontResolver
     * @throws DocumentException
     * @throws IOException
     */
    private void addFontDirectory(String dir, boolean embedded, ITextFontResolver fontResolver)
            throws DocumentException, IOException {
        File f = new File(dir);
        if (f.isDirectory()) {
            File[] files = f.listFiles((dir1, name) -> {
                String lower = name.toLowerCase();
                return lower.endsWith(".otf") || lower.endsWith(".ttf") || lower.endsWith(".ttc");
            });
            for (int i = 0; i < files.length; i++) {
                fontResolver.addFont(files[i].getAbsolutePath(), BaseFont.IDENTITY_H, embedded);
            }
        }
    }

}
