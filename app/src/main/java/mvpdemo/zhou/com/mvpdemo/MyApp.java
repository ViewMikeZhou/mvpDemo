package mvpdemo.zhou.com.mvpdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhou on 2017/3/25.
 */

public class MyApp extends Application {
    static Context mContext ;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this ;
    }
   public static Context getContext(){
       return mContext ;
   }
}
