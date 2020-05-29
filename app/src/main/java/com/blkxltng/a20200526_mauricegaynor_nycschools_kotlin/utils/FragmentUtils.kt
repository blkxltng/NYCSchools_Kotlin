package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment

fun Fragment.isNetworkConnected(): Boolean {
    val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
}