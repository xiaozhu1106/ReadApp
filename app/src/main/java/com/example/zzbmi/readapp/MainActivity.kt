package com.example.zzbmi.readapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.zzbmi.readapp.db.ExcelBean
import com.example.zzbmi.readapp.db.ReadDbUtils
import com.example.zzbmi.readapp.event.SearchEvent
import com.example.zzbmi.readapp.event.UpdateDbEvent
import com.example.zzbmi.readapp.fragment.*
import com.example.zzbmi.readapp.utils.*
import jxl.Workbook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.FileInputStream


/**
 * @author ZhuZiBo
 * @date 2017/12/7
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "ExcelDataAsyncTask"
    private var count: Int = 0
    /**
     * view是否已经初始化
     */
    private var isInited = false
    val DEFAULT_EXCEL_NAME = "stx.xls"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        iv_back.visibility = View.GONE
        Log.e(TAG, "MainActivity")
        val isSaved = SPFUitl.getBooleanData("isSaved", false)
        if (isSaved) {
            initView()
        } else {
            readExcelData()
        }
        iv_setting.setOnClickListener {
            showPopupMenu(iv_setting)
        }
    }


    private fun showPopupMenu(view: View) {
        // 这里的view代表popupMenu需要依附的view
        val popupMenu = PopupMenu(this@MainActivity, view)
        // 获取布局文件
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.show()
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener {
            // 控件每一个item的点击事件
            when (it.itemId) {
                R.id.menu_item0 -> requestWritePermission()
                R.id.menu_item1 -> {
                    startActivity(Intent(this@MainActivity, ImportDescriptionActivity::class.java))
                }
                R.id.menu_item2 -> {
                    ReadDbUtils.instance.clearAllData()
                    EventBus.getDefault().post(SearchEvent(et_search.text.toString()))
                }
                else -> {
                }
            }
            true
        }
        popupMenu.setOnDismissListener {
            // 控件消失时的事件
        }


    }

    /**
     * 请求读写权限
     */
    private fun requestWritePermission() {
        PermissionUtils.requestPermissions(this@MainActivity, 1, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), object : PermissionUtils.OnPermissionListener {
            override fun onPermissionGranted() {
                //权限通过
                showFileChooser()
            }

            override fun onPermissionDenied() {
                //权限拒绝
                val build = AlertDialog.Builder(this@MainActivity)
                val alertDialog = build.create()
                build.setTitle("权限不足")
                build.setMessage("读写权限为此操作必要权限，请点击确定前往开启")
                build.setCancelable(true)
                build.setNegativeButton("取消", { dialogInterface, i ->
                    alertDialog.dismiss()
                })
                build.setPositiveButton("确定", { dialogInterface, i ->
                    alertDialog.dismiss()
                    PermissionSetting.gotoPermissionSetting(this@MainActivity)
                })
                alertDialog.show()
            }

        })
    }


    /**
     * 默认读取本地assets目录文件
     */
    private fun readExcelData(excelName: String = DEFAULT_EXCEL_NAME) {
//        ExcelDataLoader(0).execute("stx.xls")
//        ExcelDataLoader(1).execute("stx.xls")
//        ExcelDataLoader(2).execute("stx.xls")
        ExcelDataLoader().execute(excelName)
    }


    /**
     * 获取整个 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     */
    private fun getXlsData(xlsName: String): ArrayList<ExcelBean> {
        val countryList = ArrayList<ExcelBean>()
        val assetManager = assets

        try {
            val workbook = Workbook.getWorkbook(if (xlsName == DEFAULT_EXCEL_NAME) assetManager.open(xlsName) else FileInputStream(xlsName))
            //总共有几页
            val sheetNums = workbook.numberOfSheets

            //先遍历页
            for (i in 0 until sheetNums) {

                val sheet = workbook.getSheet(i)
                val sheetRows = sheet.rows
                val sheetColumns = sheet.columns

//                Log.d(TAG, "the num of sheets is " + sheetNums)
//                Log.d(TAG, "the name of sheet is  " + sheet.name)
//                Log.d(TAG, "total rows is 行=" + sheetRows)
//                Log.d(TAG, "total cols is 列=" + sheetColumns)

                //遍历行，第一行为标题信息，不需要
                for (j in 1 until sheetRows) {
                    val bean = ExcelBean()
                    //遍历列
                    for (k in 0 until sheetColumns) {
                        when (k) {
                            0 -> bean.type = sheet.getCell(k, j).contents
                            1 -> bean.content = sheet.getCell(k, j).contents
                            2 -> bean.result = sheet.getCell(k, j).contents
                            3 -> bean.selectA = sheet.getCell(k, j).contents
                            4 -> bean.selectB = sheet.getCell(k, j).contents
                            5 -> bean.selectC = sheet.getCell(k, j).contents
                            6 -> bean.selectD = sheet.getCell(k, j).contents
                            7 -> bean.selectE = sheet.getCell(k, j).contents
                        }
                    }
                    //每列结束遍历后，额外加全拼与简拼
                    //存储全拼
                    bean.fullLetter = PinYinUtils.hanZiToFullPinyin(bean.content)
                    //存储简拼
                    bean.shortLetter = PinYinUtils.hanZiToShortPinyin(bean.content)
//                    Log.e(TAG, "content:" + bean.content + "---pinfull:" + PinYinUtils.hanZiToFullPinyin(bean.content) + "---pinshort:" + PinYinUtils.hanZiToShortPinyin(bean.content))
                    countryList.add(bean)
                }

            }
            workbook.close()

        } catch (e: Exception) {
            Log.e(TAG, "read error=" + e, e)
        }

        return countryList
    }


    //在异步方法中 调用
    @SuppressLint("StaticFieldLeak")
    private inner class ExcelDataLoader : AsyncTask<String, Void, ArrayList<ExcelBean>>() {
        override fun onPreExecute() {
            progress_bar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String): ArrayList<ExcelBean> {
            return getXlsData(params[0])
        }

        override fun onPostExecute(models: ArrayList<ExcelBean>?) {

            if ( models!!.size > 0) {
                SPFUitl.saveBooleanData("isSaved", true)
                //存在数据
                ReadDbUtils.instance.saveLists(models)
                progress_bar.visibility = View.INVISIBLE
                Log.e(TAG,"isInited:" + isInited)
                if (isInited) {
                    EventBus.getDefault().post(SearchEvent(et_search.text.toString()))
                } else {
                    initView()
                }
            } else {
                //加载失败
                Toast.makeText(this@MainActivity, "读取数据失败", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initView() {
        val fragments = ArrayList<BaseLazyViewPagerFragment>()
        fragments.add(AllFragment())
        fragments.add(PanDuanFragment())
        fragments.add(MulFragment())
        fragments.add(SingleFragment())
        view_pager.adapter = MyAdapter(supportFragmentManager, fragments)
        view_pager.offscreenPageLimit = 4
        tab_layout.setupWithViewPager(view_pager)
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                EventBus.getDefault().post(SearchEvent(s.toString()))
            }
        })
        isInited = true
    }


    class MyAdapter(fm: FragmentManager, private val fragments: List<BaseLazyViewPagerFragment>) : FragmentPagerAdapter(fm) {
        private val titleArr = arrayOf("所有", "判断", "多选", "单选")
        override fun getItem(position: Int): Fragment {
            // TODO Auto-generated method stub
            return fragments[position]
        }

        override fun getCount(): Int {
            // TODO Auto-generated method stub
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titleArr[position]
        }

    }

    /**
     * 如果检测到数据库升级，重新读取Excel表里的数据，重新存储
     */
    @Subscribe
    fun receivedUpdateDb(updateCode: UpdateDbEvent) {
        readExcelData()
    }


    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 120)
        } catch (exception: Exception) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "未发现您的文件管理器，请先去下载一个.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 120 && Activity.RESULT_OK == resultCode) {
            // Get the Uri of the selected file
            val uri = data!!.data
            Log.d(TAG, "File Uri: " + uri.toString())
            // Get the path
            val path = FileUtils.getPath(this, uri)
            Log.d(TAG, "File Path: " + path)
            if (path.endsWith(".xls")) {
                readExcelData(path!!)
            } else {
                Toast.makeText(this, "请选择以.xls为后缀结尾的Excel文件", Toast.LENGTH_SHORT).show()
            }

        }

    }


}
