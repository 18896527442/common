package com.ll.common.utils.excel;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.ll.common.utils.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;

public class TitleRowWriteHandler implements RowWriteHandler {

    private String titleName;

    private Integer totalRow;

    private Integer totalCell;

    private String orderDate;

    private Integer titleType=0;

    private Integer currentRowIndex=0;

    public TitleRowWriteHandler() {
    }

    public TitleRowWriteHandler(String titleName,Integer totalRow,String orderDate) {
        this.titleName = titleName;
        this.totalRow = totalRow;
        this.orderDate = orderDate;

    }

    public TitleRowWriteHandler(String titleName,Integer totalRow,String orderDate,Integer titleType) {
        this.titleName = titleName;
        this.totalRow = totalRow;
        this.orderDate = orderDate;
        this.titleType = titleType;
    }

    public TitleRowWriteHandler(String titleName,Integer totalRow,Integer totalCell,String orderDate,Integer titleType) {
        this.titleName = titleName;
        this.totalRow = totalRow;
        this.totalCell=totalCell;
        this.orderDate = orderDate;
        this.titleType = titleType;
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex, Integer relativeRowIndex, Boolean isHead) {

        //生成标题，展示医院名称+报表名称
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = sheet.getWorkbook();

        if (currentRowIndex==0){
            Row row1 = sheet.createRow(0);
            row1.setHeight((short) 800);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(titleName);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight((short) 400);
            cellStyle.setFont(font);
            cell1.setCellStyle(cellStyle);
            if (CollectionUtil.isNotEmpty(writeSheetHolder.getHead())){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, writeSheetHolder.getHead().size()-1));
            }else {
                sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, totalCell-1));
            }
            //生成订单日期和报表创建时间
            Row row2 = sheet.createRow(1);
            Cell cell2 = row2.createCell(0);
            switch (titleType){
                case 0:
                    cell2.setCellValue("订单时间");
                    break;
                case 1:
                    cell2.setCellValue("操作时间");
                    break;
                case 2:
                    cell2.setCellValue("统计时间");
                    break;
            }


            Cell cell3 = row2.createCell(1);
            cell3.setCellValue(orderDate);
            sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 1, 2));

            Cell cell4 = row2.createCell(4);

            if (titleType==2){
                cell4.setCellValue("单位:元");
            }else {
                cell4.setCellValue("生成时间");
                Cell cell5 = row2.createCell(5);
                cell5.setCellValue(DateUtil.format(new Date(), DateUtil.DATE_TIME_PATTERN));
            }

            sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 5, 6));
            currentRowIndex++;
        }

        if (rowIndex==totalRow-1){
            //报表底部签字人
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);

            Row row = sheet.createRow(totalRow);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("会计签字:");
            sheet.addMergedRegionUnsafe(new CellRangeAddress(totalRow, totalRow+1, 0, 1));
            cell1.setCellStyle(cellStyle);

            sheet.addMergedRegionUnsafe(new CellRangeAddress(totalRow, totalRow+1, 2, 3));

            Cell cell2 = row.createCell(5);
            cell2.setCellValue("审核人:");
            sheet.addMergedRegionUnsafe(new CellRangeAddress(totalRow, totalRow+1, 5, 6));
            cell2.setCellStyle(cellStyle);

            sheet.addMergedRegionUnsafe(new CellRangeAddress(totalRow, totalRow+1, 7, 8));
        }
        currentRowIndex++;

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean isHead) {

    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {

    }
}
