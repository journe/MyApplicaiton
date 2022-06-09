package tech.echoing.base.base

import androidx.lifecycle.MutableLiveData

/**
 * Create by Crazy on 2020/12/29
 * EchoListViewModel : 基础列表ViewModel
 */
abstract class EchoListViewModel<K> : EchoViewModel() {
    var loadMoreType = LoadMoreType.OFFSET
    val dataList = MutableLiveData<List<K>>()
    var offset: Int? = 0
    var next: String? = null
    var limit = 10

    open fun refresh() = immediateLaunch {
        dataList.value = null
        next = null
        offset = 0
        dataList.value = requestData(next, offset?.toString())
    }

    open fun loadMore() = immediateLaunch {
        val tmpList = requestData(next, offset?.toString())
        when (loadMoreType) {
            LoadMoreType.OFFSET -> offset?.let { offset = it + tmpList.size }
        }
        dataList.value = tmpList
    }

    override fun afterView() {
        immediateLaunch { refresh() }
    }

    abstract suspend fun requestData(next: String?, offset: String?): List<K>

}