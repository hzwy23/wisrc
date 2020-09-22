package com.wisrc.sales.webapp.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * FileExportUtil
 *
 * @author MAJANNING
 * @date 2018/6/30
 */
public class FileExportUtil {

    /**
     * 导出文件为 <code>filePrefix</code><code>dateFormat</code><code>filePostfixWithDot</code>
     *
     * @param request
     * @param response
     * @param workbook
     * @param filePrefix
     * @param dateFormat
     * @param filePostfixWithDot
     * @throws IOException
     */
    public static void exportExcelTo(HttpServletRequest request, HttpServletResponse response,
                                     Workbook workbook,
                                     String filePrefix, String dateFormat, String filePostfixWithDot) throws IOException {
        response.setContentType("application/octet-stream");
        String fileName = filePrefix
                + new SimpleDateFormat(dateFormat).format(System.currentTimeMillis()) + filePostfixWithDot;
        String exportName = FileNameUtil.exportTo(fileName, request);
        response.setHeader("Content-disposition", "attachment;filename=\"" + exportName + "\"");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
