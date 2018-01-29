package com.example.zzbmi.readapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * @author ZhuZiBo
 * @date 2017/10/25
 * 配合viewpager使用的懒加载Fragment
 * 此类实现的是：已加载过的页面不会重新执行initialize
 */
abstract class BaseLazyViewPagerFragment : Fragment() {
    /**
     * 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的
     * 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
     */
    private var isPrepared: Boolean = false
    /**标志当前页面是否可见 */
    private var isCanVisible: Boolean = false
    private var isFirst = true
    private var mContentView: View? = null
    protected abstract val contentLayout: Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = inflater!!.inflate(contentLayout, null, false)
        isPrepared = true
        lazyLoad()
        return mContentView

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isCanVisible = true
            lazyLoad()
        } else {
            isCanVisible = false
            onInvisible()
        }
    }


    private fun lazyLoad() {
        if (!isPrepared || !isCanVisible || !isFirst) {
            return
        }
        initialize(mContentView)
        isFirst = false
    }

    private fun onInvisible() {

    }

    /**
     * 初始化数据
     */
    protected abstract fun initialize(rootView: View?)

}
