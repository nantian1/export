import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: VectorTest
 * @Description: 工具类用于处理导出公共信息
 * @author: Chengkaifeng
 * @date: 2022/8/4
 */
public class VectorTest {

    public static void main(String[] args) throws IOException {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("测试");
        int rowLine = 0;
        int cellLine = 0;
        //设置标题样式css
        CellStyle mergeStyle = wb.createCellStyle();
        mergeStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中

        //设置正文样式
        CellStyle infoStyle = wb.createCellStyle();
        infoStyle.setAlignment(HorizontalAlignment.LEFT);
        infoStyle.setBorderLeft(BorderStyle.THIN);
        infoStyle.setBorderRight(BorderStyle.THIN);
        infoStyle.setBorderTop(BorderStyle.THIN);
        infoStyle.setBorderBottom(BorderStyle.THIN);

        //获取数据
        Map<String, Object> map = dataList();
        //获取标题
        String title = map.get("title").toString();
        //获取展示列
        String[] headTitle = (String[]) map.get("headTitle");
        //获取塞入数据正文
        List<Object> infoList = (List<Object>) map.get("info");
        //标题
        Row titleRow = sheet.createRow(rowLine);
        CellRangeAddress cellRange = new CellRangeAddress(rowLine, rowLine, rowLine, headTitle.length - 1);
        sheet.addMergedRegion(cellRange);
        Cell cell0 = titleRow.createCell(cellLine);
        cell0.setCellValue(title);
        cell0.setCellStyle(mergeStyle);
        //分标题
        Row headTitleRow = sheet.createRow(rowLine + 1);
        for (int i = 0; i < headTitle.length; i++) {
            Cell cell = headTitleRow.createCell(i);
            cell.setCellValue(headTitle[i]);
            cell.setCellStyle(infoStyle);
        }
        //正文内容
        rowLine = rowLine + 2;
        for (int j = 0; j < infoList.size(); j++) {
            Row row = sheet.createRow(rowLine + j);
            Object[] objs = (Object[]) infoList.get(j);
            for (int k = 0; k < objs.length; k++) {
                Cell cell = row.createCell(k);
                if (k == 1 || k == 3) {
                    cell.setCellValue(objs[k].toString());
                    cell.setCellStyle(infoStyle);
                } else {
                    cell.setCellValue(Double.valueOf(objs[k].toString()));
                    cell.setCellStyle(infoStyle);
                }
            }

        }

        //加边框
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRange, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRange, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRange, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRange, sheet);

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


    /**
     * 描述：造数据单元格填充的数据
     * 2022-08-04
     */
    private static Map<String, Object> dataList() {
        Map<String, Object> map = new HashMap<>();
        String[] headTitle = {"学号", "姓名", "年龄", "班级", "语文", "数学", "英语"};
        List<Object> list = new ArrayList<>();
        Object[] obj1 = {"001", "张三", 16, "高一1班", 120, 130, 115};
        Object[] obj2 = {"002", "李四", 15, "高一1班", 110, 120, 135};
        Object[] obj3 = {"003", "王五", 16, "高一2班", 140, 110, 145};
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        map.put("title", "学生信息记录表");
        map.put("headTitle", headTitle);
        map.put("info", list);
        return map;
    }


}
