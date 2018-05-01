package com.example.administrator.myapplication.zipfile;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by dingxujun on 2017/9/28.
 */

public class DataReadManager {

    private static String stringResult;
    public static Context mContext;
    /**
     * 解压到指定目录
     *
     * @param zipPath
     * @param descDir
     */
    public static void unZipFiles(Context context, String zipPath, String descDir) throws IOException {
        mContext = context;
        unZipFiles(new File(zipPath), descDir);
    }

    /**
     * 解压文件到指定目录
     * 解压后的文件名，和之前一致
     *
     * @param zipFile 待解压的zip文件
     * @param descDir 指定目录
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));

        File pathFile = new File(descDir + name);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");

            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
//          System.out.println(outPath);

            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024*5];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
                readFromCacheDir(outPath);
                System.out.println("******************解压完毕********************" + outPath);
            }
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");
        return;
    }

    /**
     * 从data/data/<application package>/cache缓存目录下读取内容
     *
     * @param filePath 文件路径
     */
    private static int sum;

    public static String readFromCacheDir(String filePath) {
        try {
            File file = new File(filePath);
            //字节输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            byte buffer[] = new byte[1024*5];
            //定义初始长度
            int len = 0;
            //开始读
            while ((len = fileInputStream.read(buffer)) != -1) {
                stringResult = new String(buffer, 0, len);
                System.out.println("===============" + sum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("读取SQL语句成功" + stringResult);
        return stringResult;
    }
}
