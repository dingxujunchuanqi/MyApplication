package com.example.nicespinner;

import android.app.Application;
import android.util.Config;

import com.example.nicespinner.utils.CollectLog;
import com.example.nicespinner.utils.CrashHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.logging.Level;

/**
 * Created by dingxujun on 2018/4/3.
 *
 * @project MyApplication
 */

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initOkGo();
//      CrashHandler.getInstance(this);
        CollectLog clog = CollectLog.getInstance();
        clog.init(this);
    }

    private void initOkGo() {
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey1", "commonHeaderValue1");
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey1", "commonParamsValue1");
        params.put("commonParamsKey2", "这里支持中文参数");
        //OkGo初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            OkGo.getInstance()
                    .debug("OkGo", Level.INFO,true)//打开该调试开关,打印级别INFO,最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)//全局的连接超时时间 默认60秒
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)//全局的读取超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)//全局的写入超时时间
                    .setCacheMode(CacheMode.NO_CACHE)//全局统一设置缓存模式,默认是不使用缓存
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)//全局统一设置缓存时间,默认永不过期
                    .setRetryCount(3)//全局统一设置超时重连次数,默认为三次,最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setCookieStore(new PersistentCookieStore())//cookie持久化存储，如果cookie不过期，则一直有效
                    .setCertificates();//方法一：信任所有证书,不安全有风险
            //.setCookieStore(new MemoryCookieStore()) //cookie使用内存缓存（app退出后，cookie消失）
            //.setCertificates(new SafeTrustManager()) //方法二：自定义信任规则，校验服务端证书
            //.setCertificates(getAssets().open("srca.cer"))//方法三：使用预埋证书，校验服务端证书（自签名证书）
            //.setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))
            //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
            //.setHostnameVerifier(new SafeHostnameVerifier())
            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败,自定義
            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//          .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}