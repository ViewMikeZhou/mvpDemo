package mvpdemo.zhou.com.mvpdemo.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mvpdemo.zhou.com.mvpdemo.MyApp;
import mvpdemo.zhou.com.mvpdemo.api.ApiService;
import mvpdemo.zhou.com.mvpdemo.utils.NetWorkUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhou on 2017/3/25.
 */

public class RetrofitHelp {
    private static OkHttpClient sOkHttpClient;
    private static final String BASE_URL = "http://gank.io/api/";
    static {
        initOkHttpClilent();
    }
    public static ApiService createApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

    private static void initOkHttpClilent() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Cache cache = new Cache(
                new File(MyApp.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        if (sOkHttpClient == null) {
            synchronized (RetrofitHelp.class) {
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(new CacheIntercepter())
                            .cache(cache)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }
        private static class CacheIntercepter implements Interceptor {

            @Override
            public Response intercept(Chain chain) throws IOException {
                int maxAge = 60 * 60;  // 设置无网缓存
                int maxStale = 60 * 60 * 24; // 设置有网缓存
                Request request = chain.request();
                if (NetWorkUtils.isNetworkConnected()) {
                    //有网就网络请求
                    request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                } else {
                    request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtils.isNetworkConnected()) {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        }
    }
