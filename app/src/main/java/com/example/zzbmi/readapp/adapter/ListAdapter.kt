package com.example.zzbmi.readapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zzbmi.readapp.R
import com.example.zzbmi.readapp.db.ExcelBean

class ListAdapter(data: List<ExcelBean>) : BaseQuickAdapter<ExcelBean, BaseViewHolder>(R.layout.item_read, data) {

    override fun convert(helper: BaseViewHolder, item: ExcelBean) {
        helper.setText(R.id.tv_content, item.content)
        helper.setText(R.id.tv_result, item.result)
    }
}