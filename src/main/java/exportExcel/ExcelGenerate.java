//package exportExcel;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class ExcelGenerate {
//
//    public static void main(String[] args) throws Exception {
//
//
//        String title = "交易数据";
//        String[] rowsName = new String[]{"服务编号", "服务名称", "字段类型", "数据元编码", "中文名称", "英文名称", "数据类型", "数据长度", "数据精度",
//                "单位", "业务规则", "英文缩写", "数据元类型域", "数据存储类型", "数据长度", "数据精度", "数据格式", "说明", "是否重复", "重复组结构体名", "父结构体名", "",
//                ""};
//        List<Object[]> listObject = new ArrayList<Object[]>();
//        Object[] obj1 = new Object[23];
//        obj1[0] = "1111222";
//        obj1[1] = "1111222";
//        obj1[2] = "1111222";
//        obj1[3] = "1111222";
//        obj1[4] = "1111222";
//        obj1[5] = "1111222";
//        obj1[6] = "1111222";
//        obj1[7] = "1111222";
//        obj1[8] = "1111222";
//        obj1[9] = "1111222";
//        obj1[10] = "1111222";
//        obj1[11] = "1111222";
//        obj1[12] = "1111222";
//        obj1[13] = "1111222";
//        obj1[14] = "1111222";
//        obj1[15] = "1111222";
//        obj1[16] = "1111222";
//        obj1[17] = "1111222";
//        obj1[18] = "1111222";
//        obj1[19] = "1111222";
//        obj1[20] = "1111222";
//        obj1[21] = "1111222";
//        obj1[22] = "1111222";
//        Object[] obj2 = new Object[23];
//        obj2[0] = "sdf";
//        obj2[1] = "sdf";
//        obj2[2] = "sdf";
//        obj2[3] = "sdf";
//        obj2[4] = "sdf";
//        obj2[5] = "sdf";
//        obj2[6] = "sdf";
//        obj2[7] = "sdf";
//        obj2[8] = "sdf";
//        obj2[9] = "sdf";
//        obj2[10] = "sdf";
//        obj2[11] = "sdf";
//        obj2[12] = "sdf";
//        obj2[13] = "sdf";
//        obj2[14] = "sdf";
//        obj2[15] = "sdf";
//        obj2[16] = "sdf";
//        obj2[17] = "sdf";
//        obj2[18] = "sdf";
//        obj2[19] = "sdf";
//        obj2[20] = "sdf";
//        obj2[21] = "sdf";
//        obj2[22] = "sdf";
//        listObject.add(obj1);
//        listObject.add(obj2);
//        String path = "./src/output/excel/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls";
//
//        BaseExportExcel ex = new BaseExportExcel(title, rowsName, listObject, path);
//        ex.export();
//    }
//
//}