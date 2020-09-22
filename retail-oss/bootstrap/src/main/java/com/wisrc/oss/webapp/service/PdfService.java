package com.wisrc.oss.webapp.service;

import com.wisrc.crawler.webapp.utils.Result;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    Result exportPdf(HttpServletResponse response, String html);

    Result exportPdfFromHtml(HttpServletResponse response, String html);
}
