package com.example.zzbmi.readapp.utils

import android.content.SharedPreferences
import com.example.zzbmi.readapp.App

object SPFUitl {
    private val SPF_NAME = "read_config"
    private var sharedPreferences: SharedPreferences? = App.context.getSharedPreferences(SPF_NAME, 0)

    //存String(上下文,key,value)
    fun saveStringData(key: String, value: String) {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        sharedPreferences!!.edit().putString(key, value).commit()
    }

    //取
    fun getStringData(key: String, defValue: String): String? {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        return sharedPreferences!!.getString(key, defValue)
    }

    fun getSharedPreferences(): SharedPreferences {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        return sharedPreferences!!
    }


    //存String(上下文,key,value)
    fun saveIntData(key: String, value: Int) {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        sharedPreferences!!.edit().putInt(key, value).commit()
    }

    //取
    fun getIntData(key: String, defValue: Int): Int {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        return sharedPreferences!!.getInt(key, defValue)
    }


    //存String(上下文,key,value)
    fun saveBooleanData(key: String, value: Boolean) {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        sharedPreferences!!.edit().putBoolean(key, value).commit()
    }

    //取
    fun getBooleanData(key: String, defValue: Boolean): Boolean {
        if (sharedPreferences == null) {
            sharedPreferences = App.context.getSharedPreferences(SPF_NAME, 0)
        }
        return sharedPreferences!!.getBoolean(key, defValue)
    }


}
