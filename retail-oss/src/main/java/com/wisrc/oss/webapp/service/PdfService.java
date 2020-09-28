package com.wisrc.oss.webapp.service;


import com.wisrc.oss.webapp.utils.Result;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    Result exportPdf(HttpServletResponse response, String html);

    Result exportPdfFromHtml(HttpServletResponse response, String html);
}
