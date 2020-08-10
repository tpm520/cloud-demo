package com.tpblog.springcloudOrder;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {
    public static void main(String[] args) {


        try {
            compressFile("E:\\company_1\\笔记\\dir\\newFile.zip", new File("E:\\company_1\\笔记"));
        } catch (Exception e) {}
    }

    /**
     * 压缩文件
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public static void compressFile(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
        compressFile(zipOutputStream, inputFile, "");

        zipOutputStream.close();

        //unzip();
    }

    public static void compressFile(ZipOutputStream zipOutputStream, File inputFile, String base){
        //System.out.println(base);
        try {
            if (inputFile.isDirectory()) {
                // 获取当前目录下的所有文件
                File[] files = inputFile.listFiles();

                ZipEntry entry = new ZipEntry(base+"/");

                    zipOutputStream.putNextEntry(entry);

                    base = base.length() == 0 ? "" : base + "/"; // 判断参数是否为空

                    for (int i = 0; i < files.length; i++) { // 循环遍历数组中文件

                        compressFile(zipOutputStream, files[i], base + files[i]);
                    }

            }else {
                zipOutputStream.putNextEntry(new ZipEntry(base));

                FileInputStream inputStream = new FileInputStream(inputFile);

                int i;

                while((i = inputStream.read())!=-1) {

                    zipOutputStream.write(i);
                }
                zipOutputStream.flush();
                inputStream.close();
            }
        } catch (Exception e) {}
    }


    public static void unzip(){


    }

}
