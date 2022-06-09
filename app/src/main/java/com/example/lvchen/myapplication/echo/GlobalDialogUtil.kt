package tech.echoing.base.util

import android.app.Activity
import androidx.lifecycle.MutableLiveData

/**
 * Create by Crazy on 2020/12/8
 * DialogUtil : 全剧弹窗管理
 */
object GlobalDialogUtil {

    val globalDialogState = MutableLiveData<(Activity) -> Unit>()


}