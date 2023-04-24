package com.ll.common.utils.excel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.ll.common.utils.StringUtils;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

@Data
public class ExcelMergeRow implements CellWriteHandler {
    private int[] mergeRowIndex;
    private int mergeColumnIndex;

    public ExcelMergeRow() {
    }

    public ExcelMergeRow(int[] mergeRowIndex, int mergeColumnIndex) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        int curRow = cell.getRowIndex();
        int curCol = cell.getColumnIndex();

        Sheet sheet = writeSheetHolder.getSheet();
        if (curCol > 1 && StringUtils.isBlank(cell.getSheet().getRow(curRow).getCell(curCol).getStringCellValue())) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(curRow, curRow, curCol - 1,
                    curCol);
            sheet.addMergedRegion(cellRangeAddress);
        }

        if (curRow > 1
                && curCol == mergeColumnIndex
                && cell.getSheet().getRow(curRow).getCell(curCol).getStringCellValue().equals(cell.getSheet().getRow(curRow - 1).getCell(curCol).getStringCellValue())) {
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            CellRangeAddress cellRangeAddress = new CellRangeAddress(curRow - 1, curRow, curCol,
                    curCol);
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size(); i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellRangeAddr.isInRange(curRow - 1, curCol)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRow);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                }
            }

            if(!isMerged){
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }
}
