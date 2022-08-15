import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: ExportTest
 * @Description:
 * @author: Chengkaifeng
 * @date: 2022/8/4
 */
public class ExportTest {

    public static void main(String[] args) throws IOException {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("测试");
        //创建开始行
        Row row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("hello");


        Row row1 = sheet.createRow(1);
        CellRangeAddress cellRange = new CellRangeAddress(1,3,1,5);
        sheet.addMergedRegion(cellRange);
        Cell cell1 = row1.createCell(1);
        cell1.setCellValue("单元格合并实现了！！！");


        /**
         * 实现上下左右居中
         */
        Row row2 = sheet.createRow(5);
        CellRangeAddress cellRange2 = new CellRangeAddress(5,8,1,5);
        sheet.addMergedRegion(cellRange2);
        Cell cell2 = row2.createCell(1);
        //设置样式
        CellStyle style = wb.createCellStyle();
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置合并单元格中字体上下左右居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置字体加粗
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)12);
        font.setFontName("楷体");
        style.setFont(font);
        cell2.setCellValue("该单元格实现了上下左右居中！！！");
        cell2.setCellStyle(style);

        //为合并的单元格添加边框
        RegionUtil.setBorderLeft(BorderStyle.THIN,cellRange2,sheet); //左边框
        RegionUtil.setBorderRight(BorderStyle.THIN,cellRange2,sheet); //右边框
        RegionUtil.setBorderTop(BorderStyle.THIN,cellRange2,sheet); //上边框
        RegionUtil.setBorderBottom(BorderStyle.THIN,cellRange2,sheet); //下边框

        try {
            FileOutputStream output = new FileOutputStream("C:\\Users\\ckf16\\Desktop\\Test.xlsx");
            wb.write(output);
            output.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb)
                wb.close();
        }

    }
}
