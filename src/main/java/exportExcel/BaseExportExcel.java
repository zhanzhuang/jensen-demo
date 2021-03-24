//package exportExcel;
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.CellRangeAddress;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.IndexedColors;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class BaseExportExcel {
//    private String title;// 显示导出表的标题
//    private String[] rowName;// 导出表的列名
//    private List<Object[]> dataList; // 填充的数据
//    private String path;// 导出的路径
//
//    public BaseExportExcel(String title, String[] rowName, List<Object[]> dataList, String path) {
//        super();
//        this.title = title;
//        this.rowName = rowName;
//        this.dataList = dataList;
//        this.path = path;
//    }
//
//    // 导出数据
//    public void export() throws Exception {
//        try {
//            HSSFWorkbook workbook = new HSSFWorkbook();// 创建工作簿对象
//            HSSFSheet sheet = workbook.createSheet(title);
//
//            // 产生表格标题行，第一行
//            HSSFRow rowm1 = sheet.createRow(0);
//            HSSFCell cell1 = rowm1.createCell(0);
//            // rowm1.setHeight((short) (25 * 30));//设置第一行的高度
//
//            // sheet样式定义[getcolumnTopStyle()/getStyle()均为自定义方法-在下面-可扩展]
//            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
//            HSSFCellStyle style = this.getStyle(workbook);
//
//            // 定义所需要的列数
//            int columnNum = rowName.length;
//            HSSFRow rowRowName1 = sheet.createRow(0);
//            HSSFRow rowRowName2 = sheet.createRow(1);
//            rowRowName1.setHeight((short) (15 * 15));// 设置第一行高度
//            rowRowName2.setHeight((short) (20 * 25));// 设置第二行高度
//
//            // 将列头设置到sheet单元格中
//            for (int n = 0; n < columnNum; n++) {
//                // 设置第一行的值
//                HSSFCell cellRowName1 = rowRowName1.createCell(n); // 创建列头对应个数的单元格
//                cellRowName1.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
//                HSSFRichTextString text1 = new HSSFRichTextString(rowName[n]);
//                cellRowName1.setCellValue(text1); // 设置列头单元格的值
//                cellRowName1.setCellStyle(columnTopStyle); // 设置列头单元格样式
//                // 设置第二行的值
//                HSSFCell cellRowName2 = rowRowName2.createCell(n); // 创建列头对应个数的单元格
//                cellRowName2.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
//                HSSFRichTextString text2 = new HSSFRichTextString(rowName[n]);
//                cellRowName2.setCellValue(text2); // 设置列头单元格的值
//                cellRowName2.setCellStyle(columnTopStyle); // 设置列头单元格样式
//            }
//
//            // 合并业务属性后设置合并单元格的值
//            HSSFRow rowywsx = sheet.getRow(0);
//            HSSFCell cellywsx = rowywsx.getCell(6);
//            cellywsx.setCellValue("业务属性");
//
//            // 合并技术属性后设置合并单元格的值
//            HSSFRow rowjssx = sheet.getRow(0);
//            HSSFCell celljssx = rowjssx.getCell(11);
//            celljssx.setCellValue("技术属性");
//
//            // 合并重复组后设置合并单元格的值
//            HSSFRow rowjcfz = sheet.getRow(0);
//            HSSFCell celljcfz = rowjcfz.getCell(18);
//            celljcfz.setCellValue("重复组");
//
//            // 将前三列合并
//            sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, 0));
//            sheet.addMergedRegion(new CellRangeAddress(2, 6, 1, 1));
//            sheet.addMergedRegion(new CellRangeAddress(2, 6, 2, 2));
//
//            // 将第一行与第二行合并
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
//
//            // 合并业务属性
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 10));
//
//            // 合并技术属性
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 16));
//
//            // 合并说明
//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 17, 17));
//
//            // 合并重复组
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 20));
//
//            // 将查询出的数据设置到sheet对应的单元格中
//            for (int i = 0; i < dataList.size(); i++) {
//
//                Object[] object = dataList.get(i);// 遍历每个对象
//                HSSFRow row = sheet.createRow(i + 2);// 创建所需的行数
//
//                row.setHeight((short) (25 * 35)); // 设置第三行开始的单元格高度
//
//                for (int j = 0; j < object.length; j++) {
//                    HSSFCell cell = null; // 设置单元格的数据类型
//                    cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
//                    if (!"".equals(object[j]) && object[j] != null) {
//                        cell.setCellValue(object[j].toString()); // 设置单元格的值
//                    }
//                    cell.setCellStyle(style); // 设置单元格样式
//                }
//            }
//
////             让列宽随着导出的列长自动适应
//            for (int colNum = 0; colNum < columnNum; colNum++) {
//                int columnWidth = sheet.getColumnWidth(colNum) / 256;
//                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
//                    HSSFRow currentRow;
//                    // 当前行未被使用过
//                    if (sheet.getRow(rowNum) == null) {
//                        currentRow = sheet.createRow(rowNum);
//                    } else {
//                        currentRow = sheet.getRow(rowNum);
//                    }
//                    if (currentRow.getCell(colNum) != null) {
//                        HSSFCell currentCell = currentRow.getCell(colNum);
//                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//                            int length = currentCell.getStringCellValue().getBytes().length;
//                            if (columnWidth < length) {
//                                columnWidth = length;
//                            }
//                        }
//                    }
//                }
//                if (colNum == 0) {
//                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 128);
//                } else {
//                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
//                }
//
//            }
//
//            if (workbook != null) {
//                try {
//                    FileOutputStream out = new FileOutputStream(path);
//                    workbook.write(out);
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /*
//     * 列头单元格样式
//     */
//    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
//
//        // 设置字体
//        HSSFFont font = workbook.createFont();
//        // 设置字体大小
//        font.setFontHeightInPoints((short) 9);
//        // 字体加粗
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        // 设置字体名字
//        font.setFontName("Times New Roman");
//        // 设置样式;
//        HSSFCellStyle style = workbook.createCellStyle();
//        // 设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        // 设置底边框颜色;
//        style.setBottomBorderColor(HSSFColor.BLACK.index);
//        // 设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        // 设置左边框颜色;
//        style.setLeftBorderColor(HSSFColor.BLACK.index);
//        // 设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        // 设置右边框颜色;
//        style.setRightBorderColor(HSSFColor.BLACK.index);
//        // 设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        // 设置顶边框颜色;
//        style.setTopBorderColor(HSSFColor.BLACK.index);
//        // 在样式用应用设置的字体;
//        style.setFont(font);
//        // 设置自动换行;
//        style.setWrapText(true);
//        // 设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
//
//        // 设置单元格背景颜色
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//        return style;
//
//    }
//
//    /*
//     * 列数据信息单元格样式
//     */
//    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
//        // 设置字体
//        HSSFFont font = workbook.createFont();
//        // 设置字体大小
//        font.setFontHeightInPoints((short) 9);
//        // 字体加粗
//        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        // 设置字体名字
//        font.setFontName("Times New Roman");
//        // 设置样式;
//        HSSFCellStyle style = workbook.createCellStyle();
//        // 设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        // 设置底边框颜色;
//        style.setBottomBorderColor(HSSFColor.BLACK.index);
//        // 设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        // 设置左边框颜色;
//        style.setLeftBorderColor(HSSFColor.BLACK.index);
//        // 设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        // 设置右边框颜色;
//        style.setRightBorderColor(HSSFColor.BLACK.index);
//        // 设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        // 设置顶边框颜色;
//        style.setTopBorderColor(HSSFColor.BLACK.index);
//        // 在样式用应用设置的字体;
//        style.setFont(font);
//        // 设置自动换行;
//        style.setWrapText(true);
//        // 设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
//
//        return style;
//
//    }
//}