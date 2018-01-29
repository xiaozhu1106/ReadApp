package com.example.zzbmi.readapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.zzbmi.readapp.App
import java.util.*

/**
 * @author ZhuZiBo
 * @date 2017/12/8
 * 数据库工具类
 */
class ReadDbUtils private constructor() {
    private var openHelper: MySQLiteOpenHelper? = null
    private val context: Context = App.context
    private val dbName = "readsearch.db"

    /**
     * 获取可读数据库
     */
    private val readableDatabase: SQLiteDatabase
        get() {
            if (openHelper == null) {
                openHelper = MySQLiteOpenHelper(context, dbName, null)
            }
            return openHelper!!.readableDatabase
        }

    /**
     * 获取可写数据库
     */
    private val writableDatabase: SQLiteDatabase
        get() {
            if (openHelper == null) {
                openHelper = MySQLiteOpenHelper(context, dbName, null)
            }
            return openHelper!!.writableDatabase
        }

    init {
        openHelper = MySQLiteOpenHelper(context, dbName, null)
    }


    fun saveBean(info: ExcelBean) {
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val chatListDao = daoSession.excelBeanDao
        chatListDao.insertOrReplace(info)
    }


    fun saveLists(infos: List<ExcelBean>) {
        for (info in infos) {
            saveBean(info)
        }
    }


    /**
     * 查询所有的
     * @param searchContent
     * @return
     * 条件语句：  type && （content || shortLetter）
     */
    fun getAllSearch(searchContent: String?): MutableList<ExcelBean> {
        try {
            val daoMaster = DaoMaster(readableDatabase)
            val daoSession = daoMaster.newSession()
            val chatListDao = daoSession.excelBeanDao
            val qb = chatListDao.queryBuilder()
            qb.where(qb.or(ExcelBeanDao.Properties.Content.like("%$searchContent%"), ExcelBeanDao.Properties.ShortLetter.like("%$searchContent%")))
            return qb.list()
        } catch (e: Exception) {
        }

        return ArrayList()
    }

    /**
     * 判断题
     * @param searchContent
     * @return
     */
    fun getPanduanSearch(searchContent: String?): MutableList<ExcelBean> {
        try {
            val daoMaster = DaoMaster(readableDatabase)
            val daoSession = daoMaster.newSession()
            val chatListDao = daoSession.excelBeanDao
            val qb = chatListDao.queryBuilder()
            qb.where(ExcelBeanDao.Properties.Type.eq("判断题"), qb.or(ExcelBeanDao.Properties.Content.like("%$searchContent%"), ExcelBeanDao.Properties.ShortLetter.like("%$searchContent%")))
            return qb.list()
        } catch (e: Exception) {
        }

        return ArrayList()
    }

    /**
     * 多选题
     * @param searchContent
     * @return
     */
    fun getMulSearch(searchContent: String?): MutableList<ExcelBean> {
        try {
            val daoMaster = DaoMaster(readableDatabase)
            val daoSession = daoMaster.newSession()
            val chatListDao = daoSession.excelBeanDao
            val qb = chatListDao.queryBuilder()
            qb.where(ExcelBeanDao.Properties.Type.eq("多选题"), qb.or(ExcelBeanDao.Properties.Content.like("%$searchContent%"), ExcelBeanDao.Properties.ShortLetter.like("%$searchContent%")))
            return qb.list()
        } catch (e: Exception) {
        }

        return ArrayList()
    }

    /**
     * 单选题
     * @param searchContent
     * @return
     */
    fun getSingleSearch(searchContent: String?): MutableList<ExcelBean> {
        try {
            val daoMaster = DaoMaster(readableDatabase)
            val daoSession = daoMaster.newSession()
            val chatListDao = daoSession.excelBeanDao
            val qb = chatListDao.queryBuilder()
            qb.where(ExcelBeanDao.Properties.Type.eq("单选题"), qb.or(ExcelBeanDao.Properties.Content.like("%$searchContent%"), ExcelBeanDao.Properties.ShortLetter.like("%$searchContent%")))
            return qb.list()
        } catch (e: Exception) {
        }

        return ArrayList()
    }

    /**
     * 清除所有表单数据
     */
    fun clearAllData() {
        val daoMaster = DaoMaster(readableDatabase)
        val daoSession = daoMaster.newSession()
        DaoMaster.dropAllTables(daoSession.database,true)
        DaoMaster.createAllTables(daoMaster.database, true)

    }

    companion object {
        val instance: ReadDbUtils by lazy { ReadDbUtils() }
    }

}
