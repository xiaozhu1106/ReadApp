package com.example.zzbmi.readapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.zzbmi.readapp.event.UpdateDbEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.greendao.database.Database


/**
 * @author ZhuZiBo
 * @date 2017/12/8
 * 数据库版本更新方案
 */
class MySQLiteOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?) : DaoMaster.OpenHelper(context, name, factory) {

    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        //保留原数据库所有表单信息
//        MigrationHelper.migrate(db, object : MigrationHelper.ReCreateAllTableListener {
//            override fun onCreateAllTables(db: Database, ifNotExists: Boolean) {
//                DaoMaster.createAllTables(db, ifNotExists)
//            }
//
//            override fun onDropAllTables(db: Database, ifExists: Boolean) {
//                DaoMaster.dropAllTables(db, ifExists)
//            }
//        }, ExcelBeanDao::class.java)
        //我这里的处理是直接默认GreenDao的处理，即删除原有所有表单数据，并创新新的空表单，即清空所有数据，但不会报错
        DaoMaster.dropAllTables(db, true) //删除
        onCreate(db) //新建
        Log.e("MySQLiteOpenHelper", "update")
        EventBus.getDefault().post(UpdateDbEvent())

    }

}