package com.zys.baselibrary.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.widget.EditText
import java.util.regex.Pattern

class CashierInputFilter: InputFilter{
    internal var mPattern: Pattern
    //输入的最大金额
    private var MAX_VALUE = java.lang.Double.MAX_VALUE
    //小数点后的位数
    private val POINTER_LENGTH = 2
    private val POINTER = "."
    private val ZERO = "0"
    private var OUT_MAX_VALUE = false//是否输出自定义的最大限值
    private var ET: EditText?=null

    constructor() {
        mPattern = Pattern.compile("([0-9]|\\.)*")
    }

    constructor(et: EditText, max_value: Double, out_max_value: Boolean) {
        MAX_VALUE = max_value
        ET = et
        OUT_MAX_VALUE = out_max_value
        mPattern = Pattern.compile("([0-9]|\\.)*")

    }

    override fun filter(
        source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
        dend: Int
    ): CharSequence {
        val sourceText = source.toString()
        val destText = dest.toString()
        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return ""
        }
        val matcher = mPattern.matcher(source)
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return ""
            } else {
                if (POINTER == source) {  //只能输入一个小数点
                    return ""
                }
            }
            //验证小数点精度，保证小数点后只能输入1位
            val index = destText.indexOf(POINTER)
            val length = dend - index
            if (length > POINTER_LENGTH) {
                return dest.subSequence(dstart, dend)
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
            if (!matcher.matches()) {
                return ""
            } else {
                if (POINTER == source && TextUtils.isEmpty(destText)) {
                    return ""
                }
                //如果首位为“0”，则只能再输“.”
                if (ZERO == destText) {
                    if (POINTER != sourceText) {
                        return ""
                    }
                }
            }
        }
        //验证输入金额的大小
        val sumText = java.lang.Double.parseDouble(destText + sourceText)
        if (sumText > MAX_VALUE) {
            if (OUT_MAX_VALUE) {
                if (ET != null) {
                    ET!!.setText(MAX_VALUE.toString() + "")
                }
                return MAX_VALUE.toString() + ""
            }
            return dest.subSequence(dstart, dend)
        }
        return dest.subSequence(dstart, dend).toString() + sourceText
    }
}