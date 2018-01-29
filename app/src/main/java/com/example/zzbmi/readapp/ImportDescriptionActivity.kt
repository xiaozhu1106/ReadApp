package com.example.zzbmi.readapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * @author ZhuZiBo
 * @date 2017/12/8
 */
class ImportDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_description)
        iv_setting.visibility = View.GONE

    }


    fun back(v: View) {
        finish()
    }
}