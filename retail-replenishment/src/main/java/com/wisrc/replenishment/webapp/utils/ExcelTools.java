package com.wisrc.replenishment.webapp.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;


/**
 * 操作Excel的工具类
 *
 * @author LEE.SIU.WAH
 * @version 1.0
 * @email lixiaohua7@163.com
 * @date 2015年4月7日 上午9:42:43
 */
public final class ExcelTools {

    /**
     * 数据导出Excel的方法
     *
     * @param sheetname 工作单的名称
     * @param titles    标题行
     * @param data      数据
     * @param fileName  文件名
     * @param response  响应对象
     */
    public static void exportExcel(String sheetname, String[] titles, List<?> data,
                                   String fileName, HttpServletResponse response,
                                   HttpServletRequest request) throws Exception {
        /** 创建工作簿 */
        HSSFWorkbook workbook = new HSSFWorkbook();
        /** 创建工作单 */
        HSSFSheet sheet = workbook.createSheet(sheetname);

        /** 创建Excel中第一行(作为标题行) */
        HSSFRow row = sheet.createRow(0);
        /** 循环创建第一行中的Cell（单元格） */
        for (int i = 0; i < titles.length; i++) {
            /** 创建Cell */
            HSSFCell cell = row.createCell(i);
            /** 设置单元格中的内容 */
            cell.setCellValue(titles[i]);
        }

        /**  循环创建下面行中的数据   */
        for (int i = 0; i < data.size(); i++) {
            /** 循环创建行 */
            row = sheet.createRow(i + 1);
            /** 获取集合中元素 */
            Object obj = data.get(i);
            /**
             * 把obj对象中数据作为Excel中的一行数据,
             * obj对象中的属性就是一行中的列
             * 利用反射获取所有的Field
             * */
            Field[] fields = obj.getClass().getDeclaredFields();
            /** 迭代Field */
            for (int j = 0; j < fields.length; j++) {
                /** 创建Cell */
                HSSFCell cell = row.createCell(j);
                /** 获取该字段名称 */
                String fieldName = fields[j].getName();
                /** 转化成get方法 */
                String getMethodName = "get" + StringUtils.capitalize(fieldName);
                /** 获取方法 */
                Method method = obj.getClass().getDeclaredMethod(getMethodName);
                /** 调用get方法该字段的值 */
                Object res = method.invoke(obj);
                /** 设置该Cell中的内容 */
                cell.setCellValue(res == null ? "" : res.toString());
            }
        }
        /** 获取当前浏览器 */
        String userAgent = request.getHeader("user-agent");
        /** 文件名是中文转码 */
        if (userAgent.toLowerCase().indexOf("msie") != -1) {
            fileName = URLDecoder.decode(fileName, "gbk"); // utf-8 --> gbk
            fileName = new String(fileName.getBytes("gbk"), "iso8859-1"); // gbk --> iso8859-1
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso8859-1"); // utf-8 --> iso8859-1
        }
        /** 以流的形式下载文件 */
        response.setContentType("application/octet-stream");
        /** 告诉浏览器文件名 */
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        /** 强制将缓冲区中的数据发送出去 */
        response.flushBuffer();
        /** 向浏览器输出Excel */
        workbook.write(response.getOutputStream());
    }

    /**
     * 读取Excel数据方法
     *
     * @param excel 文件
     * @return 集合
     */
    public static List<Map<Integer, Object>> excelImport(File excel) throws Exception {
        /** 创建工作簿 */
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excel));
        /** 获取第一个工作单 */
        HSSFSheet sheet = workbook.getSheetAt(0);
        /** 获取最后一行的编号 */
        int totalNum = sheet.getLastRowNum();
        /** 创建List集合封装Excel中的数据 */
        List<Map<Integer, Object>> lists = new ArrayList<>();
        /** 迭代读取Excel中一行数据 */
        for (int i = 1; i <= totalNum; i++) {
            /** 获取该行列的迭代器 */
            Iterator<Cell> cells = sheet.getRow(i).cellIterator();
            /** 创建Map集合封装一行数据 */
            Map<Integer, Object> data = new HashMap<>();
            /** 迭代列 */
            while (cells.hasNext()) {
                /** 获取列  */
                Cell cell = cells.next();
                /** 获取当前列的索引号 */
                int index = cell.getColumnIndex();
                /** 对列中的数据类型做判断 */
                if (cell.getCellType() == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        data.put(index, date);
                    } else {
                        data.put(index, cell.getNumericCellValue());
                    }
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    data.put(index, cell.getBooleanCellValue());
                } else {
                    data.put(index, cell.getStringCellValue());
                }
            }
            lists.add(data);
        }
        return lists;
    }

    public static void forCopyRow(Sheet sheet, Integer start, Integer skip, Integer count) {
        for (int n = 1; n < count; n++) {
            sheet.shiftRows(start + skip, sheet.getLastRowNum(), skip);
            for (int m = 0; m < skip; m++) {
                if (sheet.getRow(start + skip + m) == null) {
                    sheet.createRow(start + skip + m);
                }
                Row fromRow = sheet.getRow(start + m);
                Row toRow = sheet.getRow(start + skip + m);
                copyRow(sheet.getWorkbook(), sheet, fromRow, toRow, true);
            }
            start += skip;
        }
    }

    /**
     * 行复制功能
     *
     * @param fromRow
     * @param toRow
     */
    public static void copyRow(Workbook wb, Sheet sheet, Row fromRow, Row toRow, boolean copyValueFlag) {
        toRow.setHeight(fromRow.getHeight());
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(), (toRow.getRowNum() +
                        (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress
                        .getFirstColumn(), cellRangeAddress.getLastColumn());
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            Cell tmpCell = (Cell) cellIt.next();
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell, copyValueFlag);
        }

    }

    /**
     * 复制单元格
     *
     * @param srcCell
     * @param distCell
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyCell(Workbook wb, Cell srcCell, Cell distCell,
                                boolean copyValueFlag) {
        CellStyle newstyle = wb.createCellStyle();
        newstyle.cloneStyleFrom(srcCell.getCellStyle());
        //样式
        distCell.setCellStyle(newstyle);
        //评论
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType srcCellType = srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if (copyValueFlag) {
            if (srcCellType == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(srcCell)) {
                    distCell.setCellValue(srcCell.getDateCellValue());
                } else {
                    distCell.setCellValue(srcCell.getNumericCellValue());
                }
            } else if (srcCellType == CellType.STRING) {
                distCell.setCellValue(srcCell.getRichStringCellValue());
            } else if (srcCellType == CellType.BLANK) {
                // nothing21
            } else if (srcCellType == CellType.BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());
            } else if (srcCellType == CellType.ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());
            } else if (srcCellType == CellType.FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());
            } else { // nothing29
            }
        }
    }
}