package com.zys.qa.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lookbi.baselibrary.lisenter.OnItemClickListener
import com.lookbi.baselibrary.lisenter.OnItemLongClickListener
import com.zys.qa.R
import com.zys.qa.bean.QAList
import kotlinx.android.synthetic.main.item_qa_search.view.*

class SearchAdapter(internal var mContext: Context, internal var list: List<QAList.QA>?) :
    RecyclerView.Adapter<SearchAdapter.Holder>() {

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_qa_search, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val mData = list!!.get(position)

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener {
                mOnItemClickListener!!.onItemClick(holder.itemView, position)
            }
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener {
                mOnItemLongClickListener!!.onItemLongClick(holder.itemView, position)
                true
            }
        }
        holder.tv_title?.text = mData.question

    }

    var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    var mOnItemLongClickListener: OnItemLongClickListener? = null


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView? = itemView.tv_title


    }

}