package tech.echoing.base.base

/**
 * Create by Crazy on 2020/12/8
 * IBaseView : 用于统一基础Fragment与Activity方法
 */
interface IBaseView {
    fun getLayoutId():Int
    fun afterView()
}