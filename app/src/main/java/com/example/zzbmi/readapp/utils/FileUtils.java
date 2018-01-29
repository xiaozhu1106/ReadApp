package com.example.zzbmi.readapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.zzbmi.readapp.db.ExcelBean;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author ZhuZiBo
 * @date 2017/12/8
 */
public class FileUtils {
    ArrayList<ExcelBean> excelBeans = new ArrayList<>();
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

}
