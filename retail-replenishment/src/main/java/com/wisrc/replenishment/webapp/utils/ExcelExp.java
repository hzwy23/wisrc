package com.wisrc.replenishment.webapp.utils;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * excel模板导出基类
 *
 * @date 2016-6-2
 * @since jdk1.6
 */

public abstract class ExcelExp {


    protected HSSFWorkbook hssWb;

    protected HSSFSheet hssSheet;

    /**
     * 设置页脚
     */
    public abstract void createFooter();

    /**
     * 插入行
     *
     * @param startRow
     * @param rows
     */
    public abstract void insertRows(int startRow, int rows);

    /**
     * 替换模板中变量
     *
     * @param map
     */
    public abstract void replaceExcelData(Map<String, String> map);

    /**
     * 下载excel
     *
     * @param response
     * @param filaName
     * @throws IOException
     */
    public abstract void downloadExcel(HttpServletResponse response, String filaName) throws IOException;


    public HSSFWorkbook getHssWb() {
        return hssWb;
    }


    public void setHssWb(HSSFWorkbook hssWb) {
        this.hssWb = hssWb;
    }


    public HSSFSheet getHssSheet() {
        return hssSheet;
    }


    public void setHssSheet(HSSFSheet hssSheet) {
        this.hssSheet = hssSheet;
    }

}
