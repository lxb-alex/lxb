package com.lxb.common.excel;

import com.lxb.common.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Description
 * @author Liaoxb
 * @Date 2017/12/20 0020 9:44:44
 */
public class poiExcelUtil {

    private static HSSFWorkbook workbook;

    public static void createExcel(){
        workbook = new HSSFWorkbook();
        String sheetName = "数据统计";
        HSSFSheet sheet = workbook.createSheet(sheetName);

    }

    /**
     * 设置 sheet 标题
     * @param sheet sheet 页
     * @param rowIndex 标题所在行下标， 0开始
     * @param colIndex 合并的行数下标， 0开始
     * @param title 标题内容
     */
    public static void setTitle(HSSFSheet sheet, int rowIndex, int colIndex, String title){
        region(sheet, rowIndex, rowIndex, 0, colIndex);
        HSSFRow row = sheet.getRow(rowIndex);
        HSSFCell cell = row.createCell(colIndex, HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(title);

    }

    /**
     * 合并单元格
     * @param sheet sheet 页
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    public static void region(HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol){
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 设置表头样式
     *
     * @return
     */
    public static HSSFCellStyle setTitleStyle() {
        // 设置标题样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
       /* HorizontalAlignment.GENERAL, 常规对齐
        HorizontalAlignment.LEFT,   左对齐
        HorizontalAlignment.CENTER, 居中对齐
        HorizontalAlignment.RIGHT,  右对齐
        HorizontalAlignment.FILL,   填充对齐
        HorizontalAlignment.JUSTIFY, 	两端对齐
        HorizontalAlignment.CENTER_SELECTION,   跨列居中
        HorizontalAlignment.DISTRIBUTED;    	分散对齐*/
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // /水平居中

        cellStyle.setBorderBottom(BorderStyle.THIN); // 边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        // 设置字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 30);
        font.setFontName("IMPACT");
        font.setItalic(true);
        //Set font into style
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置单元格
     * @param index 列号
     * @param value 单元格填充值
     */
    public static void setCell(HSSFRow row, int index, Integer value) {
        if(value == null){
            setCell(row, index,"");
        }else{
            HSSFCell cell = row.createCell((short) index);
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(value);
            cell.setCellStyle(setTitleStyle());
        }
    }
    /**
     * 设置单元格
     * @param index  列号
     * @param value  单元格填充值
     */
    public void setCell(HSSFRow row, int index, Double value) {
        if(value == null){
            setCell(row, index,"");
        }else{
            HSSFCell cell = row.createCell(index);
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(value);
            HSSFDataFormat format = workbook.createDataFormat();
            HSSFCellStyle cellStyle = setTitleStyle();
            cellStyle.setDataFormat(format.getFormat("0.00%")); // 设置cell样式为定制的浮点数格式
            cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
        }
    }

    /**
     * 设置单元格
     * @param index 列号
     * @param value 单元格填充值
     */
    public static void setCell(HSSFRow row, int index, String value) {
        HSSFCell cell = row.createCell((short) index);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(value);
        cell.setCellStyle(setTitleStyle());
    }

    /**
     * 获取单元格的值
     * @param cell 单元格
     * @param type 单元格类型 1：int； -1： float
     * @return 单元格的值
     */
    private static String getCellFormatValue(HSSFCell cell, int type) {
        String cellvalue = "";
        if(cell != null){
            //判断单元格Cell类型Type
            // 目前已过时，但文档标识 在4.0之后会支持，目前官网还未提供替代方法
            switch(cell.getCellType()){
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    if(type==1){
                        DecimalFormat dft = new DecimalFormat("#");
                        cellvalue = dft.format(cell.getNumericCellValue());
                    }else if(type == -1){
                        DecimalFormat dft = new DecimalFormat("#.####");
                        cellvalue = dft.format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_FORMULA://公式
                    //判断当前的Cell是否为Date
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        // 如果是Date类型则，转化为Data格式
                        //方法1：这样子的data格式是带时分秒的：date.toLocaleString();  2011-10-12 0:00:00
                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        cellvalue = DateUtil.dateConvertString(date, "yyyy-MM-dd");
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING://String类型
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        }else{
            cellvalue = "";
        }
        return cellvalue;
    }

}
