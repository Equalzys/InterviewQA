package com.zys.qa.ui.search

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.hjq.toast.ToastUtils
import com.lookbi.baselibrary.base.BaseActivity
import com.lookbi.baselibrary.base.BaseMVPActivity
import com.lookbi.baselibrary.lisenter.OnItemClickListener
import com.zys.qa.R
import com.zys.qa.adapter.QAListAdapter
import com.zys.qa.adapter.SearchAdapter
import com.zys.qa.bean.QAList
import com.zys.qa.ui.main.MainActivity
import com.zys.qa.ui.qa_info.QAInfoActivity
import com.zys.qa.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class SearchActivity : BaseMVPActivity<SearchContract.IView, SearchPresentImpl>(),
    SearchContract.IView {

    var search: String = ""
    internal var mList: MutableList<QAList.QA> = ArrayList<QAList.QA>()
    internal var mAdapter: SearchAdapter? = null
    override fun createPresenter(): SearchPresentImpl {
        return SearchPresentImpl(this)
    }

    override fun getFristTopViewId(): Int {
        return R.id.view_top
    }

    override fun bindLayout(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        initBlackToolbar(toolbar)
        rcv_list.layoutManager = LinearLayoutManager(this)
        et_search.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search = et_search.getText().toString().trim({ it <= ' ' })
                if (TextUtils.isEmpty(search)) {
                    ToastUtil.show("请输入您想要搜索的关键字")
                    return@setOnEditorActionListener true
                }
                hideKeyBoard()
                searchQ()
                return@setOnEditorActionListener true
            }
            false
        }
        )
    }

    private fun searchQ() {
        mPresenter?.search(search)
    }


    override fun initData(savedInstanceState: Bundle?) {
        mAdapter= SearchAdapter(this,mList);
        rcv_list.adapter=mAdapter
        mAdapter!!.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                startActivity<QAInfoActivity>(
                    "FromSearch" to true,
                    "QA" to mList[position]
                )
            }

        })
    }


    override fun searchSuccess(list: List<QAList.QA>) {
        mList.clear()
        mList.addAll(list)
        mAdapter?.notifyDataSetChanged()
    }

    override fun onHttpError(e: String) {
        ToastUtil.show(e)
    }

    override fun onNoData(code: Int) {
        mList.clear()
        mAdapter?.notifyDataSetChanged()
    }

    override fun onRequestEnd() {
    }

    private fun hideKeyBoard() {
        val view = currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}
