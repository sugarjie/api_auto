package com.sugar.pojo;

public class CellData {
    private int sheetIndex;
    private int rowNo;
    private int columnNo;
    private String content;

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CellData(int sheetIndex, int rowNo, int columnNo, String content) {
        this.sheetIndex = sheetIndex;
        this.rowNo = rowNo;
        this.columnNo = columnNo;
        this.content = content;
    }

    @Override
    public String toString() {
        return "CellData{" +
                "sheetIndex=" + sheetIndex +
                ", rowNo=" + rowNo +
                ", columnNo=" + columnNo +
                ", content='" + content + '\'' +
                '}';
    }
}
