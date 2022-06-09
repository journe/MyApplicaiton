package tech.echoing.base.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * Create by Crazy on 2020/12/29
 * EchoListActivity : Echo列表Activity
 */
abstract class EchoListActivity<K, T : EchoListViewModel<K>> : EchoActivity<T>() {


    val dataList = mutableListOf<K>()
    lateinit var adapter: BaseQuickAdapter<K, *>
    override fun afterView() {
        dataList.clear()
        viewModel.loadMoreType
        val rv = obtainRecyclerView()
        rv.layoutManager = initLayoutManager()
        adapter = initAdapter()
        rv.adapter = adapter
        adapter.setNewInstance(dataList)

        val srl = obtainSmartRefreshLoad()
        srl?.setEnableAutoLoadMore(true)
        srl?.setOnRefreshListener {
            viewModel.refresh()
            srl.setEnableLoadMore(true)
        }
        srl?.setOnLoadMoreListener { viewModel.loadMore() }

        viewModel.dataList.observe(this, {
            dataChanged()
            when {
                it == null -> {
                    dataList.clear()
                    srl?.setEnableLoadMore(true)
                    adapter.notifyDataSetChanged()
                }
                it.isEmpty() -> srl?.setEnableLoadMore(false)
                else -> {
                    adapter.addData(it)
                }
            }
        })
    }

    open fun initLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    open fun loadMoreType() = ""

    abstract fun initAdapter(): BaseQuickAdapter<K, *>

    abstract fun obtainRecyclerView(): RecyclerView

    abstract fun obtainSmartRefreshLoad(): SmartRefreshLayout?

    open fun dataChanged() {
        obtainSmartRefreshLoad()?.let {
            it.finishRefresh()
            it.finishLoadMore()
        }
    }

}