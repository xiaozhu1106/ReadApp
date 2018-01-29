package com.example.zzbmi.readapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZZB on 2017/2/28.
 */
public class PermissionUtils {

    private static int mRequestCode = -1;

    private static OnPermissionListener mOnPermissionListener;

    /**
     * 6.0以下的手机，配置清单如果配置，就默认返回成功
     * >=6.0，才允许用户自己判断
     */
    public interface OnPermissionListener {

        void onPermissionGranted();  //所有权限申请成功

        void onPermissionDenied();   //当有权限申请失败时
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermissions(Activity activity, int requestCode
            , String[] permissions, OnPermissionListener listener) {
        mOnPermissionListener = listener;
        List<String> deniedPermissions = getDeniedPermissions(activity, permissions);
        if (deniedPermissions.size() > 0) {
            mRequestCode = requestCode;
            activity.requestPermissions(deniedPermissions
                    .toArray(new String[deniedPermissions.size()]), requestCode);

        } else {
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted();
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private static List<String> getDeniedPermissions(Activity activity, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (mOnPermissionListener != null) {
                if (verifyPermissions(grantResults)) {
                    mOnPermissionListener.onPermissionGranted();
                } else {
                    mOnPermissionListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 验证所有权限是否都已经授权
     */
    private static boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}