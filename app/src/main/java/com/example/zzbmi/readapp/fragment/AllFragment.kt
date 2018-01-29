package com.example.zzbmi.readapp.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.zzbmi.readapp.R
import com.example.zzbmi.readapp.adapter.ListAdapter
import com.example.zzbmi.readapp.db.ExcelBean
import com.example.zzbmi.readapp.db.ReadDbUtils
import com.example.zzbmi.readapp.event.SearchEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * @author ZhuZiBo
 * @date 2017/12/8
 */
class AllFragment : BaseLazyViewPagerFragment() {

    private var recyclerView: RecyclerView? = null
    private lateinit var excelBeans: MutableList<ExcelBean>
    private var adapter: ListAdapter? = null

    override val contentLayout: Int
        get() = R.layout.fragment_common

    override fun initialize(rootView: View?) {
        EventBus.getDefault().register(this)
        Log.e("all" , "initialize")
        recyclerView = rootView!!.findViewById(R.id.rl_container)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        excelBeans = ArrayList()
        adapter = ListAdapter(excelBeans!!)
        recyclerView!!.adapter = adapter
        notifyUI()
    }


    private fun notifyUI(msg:String? = "") {
        val tempList = ReadDbUtils.instance.getAllSearch(msg)
        Log.e("AllFragment", tempList.toString())
        recyclerView!!.post{
            excelBeans.clear()
            excelBeans.addAll(tempList)
            adapter!!.notifyDataSetChanged()
        }
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun searchDbData(searchEvent: SearchEvent) {
        Log.e("AllFragment" , "Event")
        notifyUI(searchEvent.searchContent)
    }


}
