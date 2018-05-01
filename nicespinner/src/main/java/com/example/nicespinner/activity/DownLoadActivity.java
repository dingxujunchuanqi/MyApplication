package com.example.nicespinner.activity;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.nicespinner.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.download.DownloadService;
import com.lzy.okserver.listener.DownloadListener;
import com.lzy.okserver.task.ExecutorWithListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 多任务下载 在界面退出后仍可以下载的，因为底层用的是服务
 */
public class DownLoadActivity extends AppCompatActivity implements ExecutorWithListener.OnAllTaskEndListener {
    private DownloadManager downloadManager;//下载的管理器
    private String destFileDir;//文件下载路径
    private List<DownloadInfo> allTask;
    List<String> urlList = new ArrayList<>();
    String mUrl = "http://60.28.123.129/f4.market.xiaomi.com/download/AppStore/04f515e21146022934085454a1121e11ae34396ae/com.tencent.karaoke.apk";
    String a = "http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/096f34dec955dbde0597f4e701d1406000d432064/com.immomo.momo.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        urlList.add(mUrl);
        urlList.add(a);
        initView();
    }

    private void initView() {
        downloadManager = DownloadService.getDownloadManager();
        destFileDir = "/aaa";
        downloadManager.setTargetFolder(gainSystemPath(this, "uuu"));
        downloadManager.getThreadPool().getExecutor().addOnAllTaskEndListener(this);
        downloadManager.getThreadPool().setCorePoolSize(3);//开的线程数
        downloadManager.removeAllTask();
    }

    public void aiqiyi(View v) {
        //   downLoad(mUrl,"aiqiyi.apk");//单个下载
        Toast.makeText(this, "单个下载", Toast.LENGTH_LONG).show();

        // 循环全部下载
        for (int i = 0; i < urlList.size(); i++) {
            if (i == 0) {
                downLoad(urlList.get(i), "weixin");
            }else
            {
                downLoad(urlList.get(i), "aiqiyi");

            }
        }
    }

    private void downLoad(String mUrl, String filename) {

        GetRequest request = OkGo.get(mUrl);
        downloadManager.addTask(filename,mUrl, request, new DownloadListener() {
            @Override
            public void onProgress(DownloadInfo downloadInfo) {
                System.out.println("--------downloadInfo--------------" + downloadInfo.getProgress());

            }

            @Override
            public void onFinish(DownloadInfo downloadInfo) {
                System.out.println("--------downloadInfo-------结束-------");
            }

            @Override
            public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {

            }

            @Override
            public void onAdd(DownloadInfo downloadInfo) {
                super.onAdd(downloadInfo);
            }

            @Override
            public void onRemove(DownloadInfo downloadInfo) {
                super.onRemove(downloadInfo);
            }

        });
        allTask = downloadManager.getAllTask();
        System.out.println("----onAllTaskEnd----" + allTask.size());
    }

    public void weixin(View v) {
        //downLoad(a, "weixin.apk");//单个下载
        Toast.makeText(this, "单个下载", Toast.LENGTH_LONG).show();
    }

    /**
     * 获取系统文件路径
     *
     * @param context
     * @return
     */
    public static String gainSystemPath(Context context, String filePath) {
        String sdState = Environment.getExternalStorageState(); // 判断sd卡是否存在
        // 检查SD卡是否可用
        if (!sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡未准备好！", Toast.LENGTH_SHORT).show();
        }
        //获取系统下载存储路径
        String path = Environment.getExternalStoragePublicDirectory(filePath).getPath();
        File file = new File(path);
        //创建目录,如果存在了就不创建
        if (!file.mkdirs()) {
            file.mkdirs();
        }

        return file.getPath();
    }

    /**
     * 任务结束的监听
     *
     * @date 创建时间 2018/3/29
     * @author dingxujun
     */
    @Override
    public void onAllTaskEnd() {
        for (DownloadInfo downloadInfo : allTask) {
            if (downloadInfo.getState() == DownloadManager.DOWNLOADING) {//监听下载过程中的状态

            }


        }
        System.out.println("----onAllTaskEnd----" + allTask.size());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
