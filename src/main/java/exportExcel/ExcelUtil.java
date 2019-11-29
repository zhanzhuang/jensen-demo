package exportExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

public class ExcelUtil {

    private boolean writeExcel(HttpServletResponse response, List<String> stringList) {
        if (stringList == null || stringList.size() == 0) {
            return false;
        }
        String[] header = {"SAP编号", "Contract编号", "Cube编号", "联系人", "电话", "邮箱"};

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 添加标题
        Row headerRow = sheet.createRow(0);
        Stream.iterate(0, i -> i + 1).limit(header.length).forEach(i -> {
            headerRow.createCell(i).setCellValue(header[i]);
        });
        // 添加内容
        Stream.iterate(0, i -> i + 1).limit(stringList.size()).forEach(i -> {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(stringList.get(i));
            row.createCell(1).setCellValue(stringList.get(i));
            row.createCell(2).setCellValue(stringList.get(i));
            row.createCell(3).setCellValue(stringList.get(i));
            row.createCell(4).setCellValue(stringList.get(i));
            row.createCell(5).setCellValue(stringList.get(i));
        });

        // 列宽随着内容自动适应
        for (int i = 0; i < header.length; i++) {
            int columnWidth = sheet.getColumnWidth(i) / 256;
            for (int j = 0; j < sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                if (row.getCell(i) != null) {
                    Cell cell = row.getCell(i);
                    int length = cell.getStringCellValue().getBytes().length;
                    if (columnWidth < length) {
                        columnWidth = length;
                    }
                }
            }
            sheet.setColumnWidth(i, (columnWidth + 4) * 256);
        }

        try {
            // todo 测试时本地生成excel
//            FileOutputStream stream = new FileOutputStream("C:/Users/zhanjens/Desktop/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx");
            // todo 浏览器点击生成excel
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Content-Disposition", "Attachment;Filename=TMService.xlsx");
            OutputStream stream = response.getOutputStream();

            workbook.write(stream);
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int readExcel(String filePath) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                row.getCell(0).toString();
                row.getCell(1).toString();
                row.getCell(2).toString();
            }

            return sheet.getLastRowNum();
        } catch (Exception e) {
            return -1;
        }
    }
}
