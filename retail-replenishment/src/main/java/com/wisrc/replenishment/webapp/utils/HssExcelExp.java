package com.wisrc.replenishment.webapp.utils;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Footer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;


/**
 * excel导出类
 * <p>处理.xls格式<p>
 *
 * @date 2016-6-2
 * @since jdk1.6
 */

public class HssExcelExp extends ExcelExp {


    public HssExcelExp() {
        super();
    }

    /**
     * 构造函数
     * ExcelExp
     *
     * @param filePath 文件路径，如com/test/template/test.xls
     * @param sheetNum 要操作的页签，0为第一个页签
     * @throws IOException
     */
    public HssExcelExp(String filePath, int sheetNum) throws IOException {
        File resource = new File(filePath);
        InputStream is = new FileInputStream(resource);
        hssWb = new HSSFWorkbook(is);
        hssSheet = hssWb.getSheetAt(sheetNum);
    }

    @Override
    public void createFooter() {
        Footer footer = hssSheet.getFooter();
        footer.setRight("第" + HSSFFooter.page() + "页，共" + HSSFFooter.numPages() + "页");
    }

    @Override
    public void downloadExcel(HttpServletResponse response, String filaName) throws IOException {
        String encodeFileName = URLEncoder.encode(filaName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
        ServletOutputStream out = response.getOutputStream();
        hssWb.write(out);
        out.flush();
        out.close();
    }

    @Override
    public void insertRows(int startRow, int rows) {
        int bottomRow = hssSheet.getLastRowNum();
        if (startRow > bottomRow) {
            int n = startRow - bottomRow;
            for (int i = 1; i <= n; i++) {
                hssSheet.createRow(bottomRow + i);
            }
        }
        hssSheet.shiftRows(startRow, hssSheet.getLastRowNum(), rows, true, false);
    }

    @Override
    public void replaceExcelData(Map<String, String> map) {
        int rowNum = hssSheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            HSSFRow row = hssSheet.getRow(i);
            if (row == null) continue;
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                HSSFCell cell = row.getCell(j);
                if (cell == null) continue;
                String key = cell.getStringCellValue();
                if (map.containsKey(key)) {
                    cell.setCellValue(map.get(key));
                }
            }
        }
    }


}