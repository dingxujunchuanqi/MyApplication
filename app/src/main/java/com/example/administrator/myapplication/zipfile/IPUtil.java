package com.example.administrator.myapplication.zipfile;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * ping IP 工具类
 * Created by dingxujun on 2017/12/22.
 *
 * @project MyApplication
 */

public class IPUtil {
    /**status 等于0的时候表示网络可用，status等于2时表示当前网络不可用。
    *@data 创建时间 2017/12/22
    *@author dingxujun
    */
    public static boolean pingIpAddress(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 3 -w 100 " + ipAddress);
            int status = process.waitFor();
            if (status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static final boolean ping(String ip) {

        String result = null;

        try {
           // String ip = "www.baidu.com";// 除非百度挂了，否则用这个应该没问题~

            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);//ping3次

// 读取ping的内容，可不加。

            InputStream input = p.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            StringBuffer stringBuffer = new StringBuffer();

            String content = "";

            while ((content = in.readLine()) != null) {

                stringBuffer.append(content);
            }

            Log.i("TTT", "result content : " + stringBuffer.toString());


// PING的状态

            int status = p.waitFor();

            if (status == 0) {

                result = "successful~";

                return true;

            } else {

                result = "failed~ cannot reach the IP address";

            }

        } catch (IOException e) {

            result = "failed~ IOException";

        } catch (InterruptedException e) {

            result = "failed~ InterruptedException";

        } finally {

            Log.i("TTT", "result = " + result);

        }

        return false;
    }
}
