package com.example.nicespinner.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 手机SD卡目录 logcat管理类
 * Created by dingxujun on 2018/5/22.
 * <p>
 * 将错误日志写入到sd卡,默认为Android/data/包名/files/logs下面，放这个目录下主要是为了不需要权限
 * <p>
 * init方法 有两种自定义日志路径的方法
 */

public class CollectLog implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CollectLog.class.getCanonicalName();

    //The system default UncaughtException handler class
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler instance
    private static CollectLog INSTANCE = new CollectLog();
    //Context
    private Context mContext;
    //Used to store device information and exception information
    private Map<String, String> infos = new HashMap<String, String>();

    //Used to format the date as part of the log file name
    private DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
    //Custom the path
    private String filePath = "";
    private String pathLogcat;
    private  SimpleDateFormat logfile = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private int SDCARD_LOG_FILE_SAVE_DAYS = 3;//删除三天之前的日志
    private int order = 0;

    private CollectLog() {
    }


    /**
     * Get instances, singleton pattern
     *
     * @return instances
     */
    public static CollectLog getInstance() {
        return INSTANCE;
    }

    /**
     * initialization
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        //Gets the system's default UncaughtException handler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //Set the CrashHandler as the default handler for the program
        Thread.setDefaultUncaughtExceptionHandler(this);
        pathLogcat = mContext.getExternalFilesDir("logs").getAbsolutePath() + File.separator;
        if (!TextUtils.isEmpty(filePath)){
            pathLogcat=filePath;
        }
        File file = new File(pathLogcat);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            if (availableSpace()) {
                delFile(order);
            } else {
                deleteDirectory(pathLogcat);
            }
        }


    }

    public void delFile(int order) {
        try {
            String needDelFiel = logfile.format(getDateBefore(order));
            needDelFiel = needDelFiel.substring(0, 8);
            int needDelTime = Integer.parseInt(needDelFiel);

            File dirFile = new File(pathLogcat);
            if (dirFile.exists() || dirFile.isDirectory()) {
                File[] files = dirFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String fileName = files[i] + "";
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                        fileName = fileName.substring(0, 8);
                        int filetime = Integer.parseInt(fileName);
                        if (filetime <= needDelTime) {
                            files[i].delete();
                        }
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private Date getDateBefore(int order) {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS + order);
        order++;
        if (order == SDCARD_LOG_FILE_SAVE_DAYS) {
            order = 0;
        }
        return now.getTime();
    }

    public void deleteDirectory(String filePath) {

        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (dirFile.exists() || dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {

                    files[i].delete();
                }
            }
        }

        dirFile.delete();
    }

    /**
     * initialization ,Can custom the path
     *
     * @param context context
     * @param path    custom the path
     */
    public void init(Context context, String path) {
        init(context);
        filePath = path;
    }

    /**
     * When the UncaughtException occurs, it is passed to the function to process
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // If the user does not deal with the system is the default exception handler to handle
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
//                Log.e(TAG, "error : ", e);
            }
            // exit app
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean availableSpace() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();

        long totalBlocks = blockSize * blockCount / 1024;

        long availableBlocks = availCount * blockSize / 1024;
        if (availableBlocks < totalBlocks) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Custom error handling, error message collection error reporting and other operations are completed here.
     *
     * @param ex Throwable
     * @return If the exception is processed return true; false otherwise.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // Collect device parameter information
        collectDeviceInfo(mContext);
        //Save the log file
        String str = saveCrashInfo2File(ex);
        Log.e(TAG, str);
        return false;
    }

    /**
     * Collect device parameter information
     *
     * @param ctx context
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }

        } catch (PackageManager.NameNotFoundException e) {
//            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
//                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
//                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * Save the error message to a file
     *
     * @param ex Throwable
     * @return Returns the name of the file to facilitate transfer of the file to the server
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("[" + key + ", " + value + "]\n");
        }
        sb.append("\n" + getStackTraceString(ex));
        try {
            String time = getFileName();
            String fileName = time + ".txt";
            File sdDir = null;
            sdDir = mContext.getExternalFilesDir("logs").getAbsoluteFile();
            File file = null;
            if (!TextUtils.isEmpty(filePath)) {
                File files = new File(filePath);
                if (!files.exists()) {
                    //Create a directory
                    files.mkdirs();
                }
                file = new File(filePath + File.separator + fileName);
            } else {
                file = new File(sdDir + File.separator + fileName);
            }
            if (file == null) {
                file = new File(sdDir + File.separator + fileName);
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * Gets the string of the caught exception
     *
     * @param tr throwable
     * @return the string of the caught exception
     */
    public static String getStackTraceString(Throwable tr) {
        try {
            if (tr == null) {
                return "";
            }
            Throwable t = tr;
            while (t != null) {
                if (t instanceof UnknownHostException) {
                    return "";
                }
                t = t.getCause();
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public  String getFileName() {
        String date = logfile.format(new Date(System.currentTimeMillis()));
        return date;
    }
}
