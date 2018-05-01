package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 文件相关辅助类
 */
public class FileUtils {
    public static final String FOLDER_NAME = "rt21dms_imgeditor";
    public static final String FOLDER_NAME_COPY = "rt21dms_copy";
    public static final String FOLDER_NAME_FAULT = "rt21dms_fault";
    public static final String ZIP_FILE = "rt21dms_zip";

    /**
     * 获取存贮文件的文件夹路径
     *
     * @return
     */
    public static File createFolders(String folderName) {
        File baseDir;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
            baseDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
        if (baseDir == null)
            return Environment.getExternalStorageDirectory();
        File aviaryFolder = new File(baseDir, folderName);
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.isFile())
            aviaryFolder.delete();
        if (aviaryFolder.mkdirs())
            return aviaryFolder;
        return Environment.getExternalStorageDirectory();
    }

    public static File genEditFile() {
        return FileUtils.getEmptyFile(FOLDER_NAME_COPY, "copy"
                + System.currentTimeMillis() + ".jpg");
    }

    public static File genFaultFile() {
        return FileUtils.getEmptyFile(FOLDER_NAME_FAULT, "fault"
                + System.currentTimeMillis() + ".jpg");
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String path) {
        // 删除缓存
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteFolder(String folderName) {
        File baseDir;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
            baseDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
        File file = new File(baseDir, folderName);
        if (file.exists()) {
            file.delete();
        }
    }

    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static File getEmptyFile(String folderName, String name) {
        File folder = FileUtils.createFolders(folderName);
        if (folder != null) {
            if (folder.exists()) {
                File file = new File(folder, name);
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file;
            }
        }
        return null;
    }

    /**
     * 删除指定文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFileNoThrow(String path) {
        File file;
        try {
            file = new File(path);
        } catch (NullPointerException e) {
            return false;
        }

        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 保存图片
     *
     * @param bitName
     * @param mBitmap
     */
    public static String saveBitmap(String bitName, Bitmap mBitmap) {
        File baseFolder = createFolders(FOLDER_NAME_COPY);
        File f = new File(baseFolder.getAbsolutePath(), bitName);
        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    // 获取文件夹大小
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) { // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位 * * @param size * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024d;
        int megaByte = (int) (kiloByte / 1024d);
        return megaByte + "MB";
    }

    /**
     * 判断SDCard是否可用
     */
    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 根据系统时间、前缀、后缀产生一个文件
     */
    public static File createFile(File folder, String prefix, String suffix) {
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename);
    }

    /**
     * 逐行读取文件
     *
     * @param file 文件
     * @return
     * @throws IOException
     */
    public static List<String> readFile(File file) throws IOException {
        List<String> list = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        return list;
    }

    /**
     * 从json文件读取json字符串
     *
     * @param file
     * @return json字符串
     * @throws IOException
     */
    public static String readJsonFromFile(File file) throws IOException {
        String json = "";
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            json = json + tempString;
        }
        reader.close();
        return json;
    }

    /**
     * 获取系统文件路径
     *
     * @param context
     * @return
     */
    public static String gainSystemPath(Context context,String filePath) {
        String sdState = Environment.getExternalStorageState(); // 判断sd卡是否存在
        // 检查SD卡是否可用
        if (!sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡未准备好！", Toast.LENGTH_SHORT).show();
        }
        //获取系统图片存储路径
        String  path = Environment.getExternalStoragePublicDirectory(filePath).getPath();
        File file =new File(path);
        //创建目录,如果存在了就不创建
        if (!file.mkdirs()) {
            file.mkdirs();
        }

        return   file.getPath();
    }

    /**
     * 获取sd卡文件目录
     * @param context
     * @param folderPath
     * @return
     */
    public  static String gainSDPath(Context context,String folderPath){
        String sdState = Environment.getExternalStorageState(); // 判断sd卡是否存在
        // 检查SD卡是否可用
        if (!sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡未准备好！", Toast.LENGTH_SHORT).show();
        }
        String SDPath = Environment.getExternalStorageDirectory().getPath()+
                File.separator+folderPath+File.separator;
        File filee=new File(SDPath);
        if (!filee.exists()){
            filee.mkdirs();
        }
        return SDPath;
    }



    /**
     * 拷贝数据库文件到sd卡
     *
     * @param context
     * @param dbName
     */
    public static void copyDBtoSD(Context context, String dbName, boolean isDeletSource) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //获取数据库路径
            String pathDatabase = context.getDatabasePath(dbName).getPath();
            String packageName = getPackageName(context);
            //目标路径
            String SDPath = Environment.getExternalStorageDirectory().getPath() + File.separator + packageName + File.separator;
            File mfile = new File(SDPath);
            if (!mfile.exists()) {
                mfile.mkdirs();       //如果不存在则创建新的file
            }
            try {
                File file_sd = new File(mfile, "/old_data.db");
                File file_database = new File(pathDatabase);
                if (!file_database.exists()){
                    file_database.mkdirs();
                }
                copyFileUsingFileStreams(file_database, file_sd, isDeletSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝sd临时目录数据库文件到数据库运行目录
     *
     * @param context
     * @param dbName
     */
    public static void copySDtoDB(Context context, String dbName, boolean isDeletSource) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //获取数据库路径
            String pathDatabase = context.getDatabasePath(dbName).getPath();
            String packageName =getPackageName(context);
            //目标路径
            String SDPath = Environment.getExternalStorageDirectory().getPath() + File.separator + packageName + File.separator;
            File mfile = new File(SDPath);
            if (!mfile.exists()) {
                mfile.mkdirs();       //如果不存在则创建新的file
            }
            try {
                File file_sd = new File(mfile, "/old_data.db");
                File file_database = new File(pathDatabase);
                if (!file_database.exists()) {
                    mfile.mkdirs();       //如果不存在则创建新的file
                }
                copyFileUsingFileStreams(file_sd, file_database, isDeletSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  InputStream input = new FileInputStream(source);如果文件路径不存在input则为null
     * isDeletSource 是否删除源文件
     *@data 创建时间 2018/1/18
     *@author dingxujun
     */
    private static void copyFileUsingFileStreams(File source, File dest, boolean isDeletSource)
            throws IOException {
        InputStream input = new FileInputStream(source);
        OutputStream output = new FileOutputStream(dest);
        try {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            if (source.exists() && isDeletSource) {
                source.delete();
            }
            if (input == null || output == null) {
                return;
            }
            input.close();
            output.close();
        }
    }
    /**
     * TODO:获取程序包名
     *
     * @param context
     * @return 程序包名
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }
}
