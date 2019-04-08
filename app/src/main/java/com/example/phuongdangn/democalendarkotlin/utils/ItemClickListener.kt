package com.example.phuongdangn.democalendarkotlin.utils

import android.view.View

/**
 * Created by phuongdn on 4/8/19.
 */
interface ItemClickListener {
    fun onItemClick(view: View, position: Int)
    fun onLongItemClick(view: View, position: Int)
}