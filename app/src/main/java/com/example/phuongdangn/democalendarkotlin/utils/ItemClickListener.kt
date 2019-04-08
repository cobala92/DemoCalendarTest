package com.example.phuongdangn.democalendarkotlin.utils

import android.view.View
import com.example.phuongdangn.democalendarkotlin.model.MyDate

/**
 * Created by phuongdn on 4/8/19.
 */
interface ItemClickListener {
    fun onLongItemClick(view: View, position: Int)
    fun onDateSelect(date: MyDate, isEndDate: Boolean)
}