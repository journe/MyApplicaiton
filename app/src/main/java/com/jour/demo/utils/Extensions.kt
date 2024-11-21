package com.jour.demo.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jour.demo.base.utils.toast
import com.permissionx.guolindev.PermissionX


fun Fragment.actionWithPermission(permissions: List<String>, action: () -> Unit) {
    PermissionX.init(this)
        .permissions(permissions)
        .request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                action.invoke()
            } else {
                toast("请授予相关权限")
            }

        }
}

fun AppCompatActivity.actionWithPermission(permissions: List<String>, action: () -> Unit) {
    PermissionX.init(this)
        .permissions(permissions)
        .request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                action.invoke()
            } else {
                toast("请授予相关权限")
            }

        }
}