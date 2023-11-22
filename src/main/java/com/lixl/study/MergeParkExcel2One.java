package com.lixl.study;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MergeParkExcel2One {


    static File destFile = new File("/Users/lixl/Desktop/汇总.xlsx");

    static File tempFile = new File("/Users/lixl/Desktop/result.xlsx");

    //组装新的excel
    public static void rewriteExcel(String filepath) {


        File file = new File(filepath);
        File[] xlsFileList = file.listFiles();
        for (File xlsFile : xlsFileList) {
            try {
                String industry = xlsFile.getName();
                industry = industry.substring(0, industry.lastIndexOf("."));
                if ("".equals(industry)) continue;
                //读取excel
                List<Temp> temps = readExcelfromFile(xlsFile);

                // 写Excel
                if (destFile.exists()) {
                    // 第二次按照原有格式，不需要表头，追加写入
                    EasyExcel.write(destFile, Temp.class).needHead(false).
                            withTemplate(destFile).file(tempFile).sheet().doWrite(temps);
                } else {
                    // 第一次写入需要表头
                    EasyExcel.write(destFile, Temp.class).sheet().doWrite(temps);
                }
                if (tempFile.exists()) {
                    destFile.delete();
                    tempFile.renameTo(destFile);
                }

            } catch (Exception e) {
                System.out.println("------异常行数---");
                e.printStackTrace();
            }
        }
    }

    //读取excel到内存中
    public static List<Temp> readExcelfromFile(File file) {
        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        List<Temp> tempList = new ArrayList<>();
//        String filename = "D:\\study\\excel\\read.xlsx";
        String finalFileName = fileName;
        EasyExcel.read(file, Temp.class, new AnalysisEventListener<Temp>() {
            int num = 0;

            //解析一行运行一次此方法。
            @Override
            public void invoke(Temp student, AnalysisContext analysisContext) {
                num++;
                student.setIndustry(finalFileName);
                System.out.println(student.getProvince() + " " + student.getCity() + " " + student.getRegion() + " " + student.getDecription());
                tempList.add(student);

            }

            //解析所有数据完成，运行此方法。
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                System.out.println("读取完成" + "共 " + num + " 条数据");
            }
        }).sheet().doRead();
        return tempList;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Temp {
        @ExcelProperty(value = "所在省份")
        String province;

        @ExcelProperty(value = "所在城市")
        String city;

        @ExcelProperty(value = "所在区/县")
        String region;

        @ExcelProperty(value = "园区名称")
        String name;

        @ExcelProperty(value = "园区简介")
        String decription;

        @ExcelProperty(value = "行业")
        String industry = "";
    }

    public static void main(String[] args) {
        rewriteExcel("/Users/lixl/Desktop/园区");
        System.out.println("success");
    }
}
