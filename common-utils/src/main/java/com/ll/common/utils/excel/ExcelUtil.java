package com.ll.common.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xueguoping
 * @desc: TODO excel 操作工具类
 * @date 2022/7/1409:45
 */
public class ExcelUtil {

    /**
     * @param inputStream 文件流
     * @param tClass      实体类映射
     * @return excel 数据list
     * @desc 读取第一个sheet的Excel
     * @author xuegp
     * @date 2022/7/14 09:46
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> tClass) {
        return readExcel(inputStream, tClass, 1);
    }

    /**
     * @param inputStream 文件流
     * @param tClass      实体类映射
     * @param sheetNo     sheet 的序号 从1开始 /sheetNO 为空则读取第一个sheetNO
     * @return excel 数据list
     * @desc 读取某个sheet的Excel
     * @author xuegp
     * @date 2022/7/14 10:13
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> tClass, Integer sheetNo) {
        sheetNo = Objects.isNull(sheetNo) || sheetNo < 1 ? 1 : sheetNo;

        EasyExcelListener<T> listener = new EasyExcelListener<T>();
        EasyExcel.read(inputStream, tClass, listener).sheet(sheetNo - 1).doRead();
        return listener.getDatas();
    }

    /**
     * @param outputStream OutputStream
     * @param list         数据 list
     * @param clazz        导出结构体
     * @param sheetName    导入文件的 sheet 名
     * @return
     * @desc 导出 Excel ：一个 sheet，带表头
     * @author xuegp
     * @date 2022/7/14 10:16
     */
    public static <T> void writeExcel(OutputStream outputStream, List<T> list, Class clazz, String sheetName) {
        EasyExcel.write(outputStream, clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet(sheetName).doWrite(list);
    }

    /**
     * @param response  HttpServletResponse
     * @param fileName  文件名
     * @param list      数据 lis
     * @param clazz     导出结构体
     * @param sheetName 导入文件的 sheet 名
     * @return
     * @desc 导出Excel
     * @author xuegp
     * @date 2022/7/14 10:20
     */
    public static <T> void writeExcel(HttpServletResponse response, String fileName, List<T> list, Class<T> clazz, String sheetName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameEnCode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "+");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileNameEnCode + ".xlsx");
        writeExcel(response.getOutputStream(), list, clazz, sheetName);

    }

    /**
     * @param response  OutputStream
     * @param fileName  文件名
     * @param head      表格头
     * @param data      数据
     * @param sheetName 导入文件的 sheet 名
     * @return
     * @desc 动态表头 Excel ：一个 sheet，带表头
     */
    public static <T> void writeExcel(HttpServletResponse response, String fileName, List<List<String>> head, List data, String sheetName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameEnCode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "+");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileNameEnCode + ".xlsx");
        EasyExcel.write(response.getOutputStream()).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).head(head).sheet(sheetName).doWrite(data);
    }

    public static <T> void writeExcel(HttpServletResponse response, String fileName, List<List<String>> head, List data, String sheetName, WriteHandler... handlers) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameEnCode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "+");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileNameEnCode + ".xlsx");
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(response.getOutputStream()).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        for (WriteHandler handler : handlers) {
            excelWriterBuilder.registerWriteHandler(handler);
        }
        excelWriterBuilder.head(head).sheet(sheetName).doWrite(data);
    }

    public static <T> void writeExcel(HttpServletResponse response, String fileName, List<List<String>> head, List data, String sheetName,Integer relativeHeadRowIndex, List<WriteHandler> handlers) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameEnCode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "+");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileNameEnCode + ".xlsx");
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(response.getOutputStream()).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        for (WriteHandler handler : handlers) {
            excelWriterBuilder.registerWriteHandler(handler);
        }
        excelWriterBuilder.relativeHeadRowIndex(relativeHeadRowIndex).head(head).sheet(sheetName).doWrite(data);
    }



}
