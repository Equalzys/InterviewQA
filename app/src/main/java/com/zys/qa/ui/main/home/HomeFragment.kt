package com.zys.qa.ui.main.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zys.baselibrary.base.BaseMVPFragment
import com.zys.baselibrary.lisenter.OnItemClickListener
import com.zys.qa.constant.NoCodeConstant
import com.zys.qa.R
import com.zys.qa.utils.ToastUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zys.qa.adapter.QAListAdapter
import com.zys.qa.bean.QAList
import com.zys.qa.ui.add_qa.AddQAActivity
import com.zys.qa.ui.qa_info.QAInfoActivity
import com.zys.qa.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.ArrayList

class HomeFragment : BaseMVPFragment<HomeContract.IView, HomePresentImpl>(), HomeContract.IView {


    internal var mList: MutableList<QAList.QA> = ArrayList<QAList.QA>()
    internal var mAdapter: QAListAdapter? = null
    //    internal var mMember: Member? = null
    var from: Int = 1;

    override fun createPresenter(): HomePresentImpl {
        return HomePresentImpl(activity!!)
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_home
    }

    override fun getFristTopViewId(): Int {
        return R.id.view_home_top
    }

    override fun initView() {
        rcv_list.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?

        refershlayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                from++
                mPresenter?.setRefershOrLoadmore(true)
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                from = 1
                mPresenter?.setRefershOrLoadmore(true)
                getData()
            }
        })
        iv_search.setOnClickListener {
            startActivity<SearchActivity>()
        }
        iv_add.setOnClickListener {
            startActivity<AddQAActivity>()
        }

    }


    override fun initData() {
//        EventBusUtil.register(this)
        mAdapter = QAListAdapter(mActivity!!, mList)
        rcv_list.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                mList.get(position).views = mList.get(position).views + 1
                mAdapter?.notifyDataSetChanged()
                startActivity<QAInfoActivity>(
                    "QAList" to mList,
                    "position" to position
                )
            }

        })
        getData()
    }


    private fun getData() {
//        showLoading()
        mPresenter?.getQAList(from)
    }


    override fun getQAListSuccess(list: List<QAList.QA>) {
        if (from == 1) {
            mList.clear()
        }
        mList.addAll(list)
        mAdapter?.notifyDataSetChanged()
    }

    override fun onHttpError(e: String) {
        ToastUtil.show(e)
    }

    override fun onNoData(code: Int) {
        if (code == NoCodeConstant.NOQALIST) {
            if (from == 1) {
                mList.clear()
                mAdapter?.notifyDataSetChanged()
            } else {
                from--
            }
        }
    }

    override fun onRequestEnd() {
        if (from == 1) {
            refershlayout.finishRefresh()
        } else {
            refershlayout.finishLoadMore()
        }

    }

//    @Subscribe
//    fun homeEvent(event: EventBean) {
//        if (event.event == EventConstant.SCANCODESUCCESS) {
//            val c = event.data as String
//            mPresenter?.getMachineID(AppContext.instance.getTokenStr()!!, c)
//        }
//        if (event.event == EventConstant.CHANGESTATUS) {
//            getData()
//        }
//    }
}