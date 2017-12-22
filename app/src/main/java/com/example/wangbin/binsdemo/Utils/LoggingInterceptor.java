package com.example.wangbin.binsdemo.Utils;

import com.github.lisicnu.log4android.BuildConfig;
import com.github.lisicnu.log4android.LogManager;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by momo on 2017/12/22.
 */

public class LoggingInterceptor implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (BuildConfig.DEBUG) {
            LogManager.d("intercept-发送请求:", request.method());
            LogManager.d("intercept-url:", request.url());
            LogManager.d("intercept-headers:", request.headers());
            LogManager.d("intercept-body:", request.body());
        }
        return chain.proceed(request);
    }
}
