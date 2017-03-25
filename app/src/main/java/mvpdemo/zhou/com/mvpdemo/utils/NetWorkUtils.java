package mvpdemo.zhou.com.mvpdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import mvpdemo.zhou.com.mvpdemo.MyApp;

/**
 * Created by zhou on 2017/3/25.
 */

public class NetWorkUtils {
    private NetWorkUtils() {
    }


    public static  boolean isNetworkConnected() {
        if (MyApp.getContext() != null) {
            ConnectivityManager mConnectivityManager
                    = (ConnectivityManager) MyApp.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static boolean isWifiConnected() {

        if (MyApp.getContext() != null) {
            ConnectivityManager mConnectivityManager
                    = (ConnectivityManager) MyApp.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static boolean isMobileConnected() {

        if (MyApp.getContext()!= null) {
            ConnectivityManager mConnectivityManager
                    = (ConnectivityManager) MyApp.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static int getConnectedType() {

        if (MyApp.getContext() != null) {
            ConnectivityManager mConnectivityManager
                    = (ConnectivityManager) MyApp.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
