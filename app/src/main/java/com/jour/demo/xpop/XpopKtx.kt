package com.jour.demo.xpop

import android.content.Context
import com.lxj.xpopup.XPopup

fun toastCenter(context: Context, popTextStr: String = "") {
    XPopup.Builder(context)
        .asCustom(CenterPopup(context, popTextStr))
        .show()
}