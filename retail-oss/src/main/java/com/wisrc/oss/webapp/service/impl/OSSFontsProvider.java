package com.wisrc.oss.webapp.service.impl;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

import java.io.IOException;

public class OSSFontsProvider extends XMLWorkerFontProvider {
    public OSSFontsProvider() {
        super(null, null);
    }

    @Override
    public Font getFont(String fontname, String encoding, float size, int style) {
        if (size == 0) {
            size = 14;
        }
        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont("fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Font(baseFont, size, Font.NORMAL);
    }
}
