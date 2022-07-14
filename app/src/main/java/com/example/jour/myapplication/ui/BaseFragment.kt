package com.example.jour.myapplication.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 * 若把初始化内容放到initData实现
 * 就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 *
 * 注1:
 * 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 *
 * 注2:
 * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 * eg:
 * transaction.hide(aFragment);
 * transaction.show(aFragment);
 *
 * Created by MnyZhao
 * on 2015/11/2.
</pre> *
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {
  /**
   * Fragment title
   */
  var fragmentTitle: String? = null

  /**
   * 是否可见状态
   */
  private var misVisible = false

  /**
   * 标志位，View已经初始化完成。
   * 2016/04/29
   * 用isAdded()属性代替
   * 2016/05/03
   * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
   */
  private var isPrepared = false

  /**
   * 是否第一次加载
   */
  private var isFirstLoad = true
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
    // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
    // 导致initData反复执行,所以这里注释掉
    // isFirstLoad = true;

    // 2016/04/29
    // 取消 isFirstLoad = true的注释 , 因为上述的initData本身就是应该执行的
    // onCreateView执行 证明被移出过FragmentManager initData确实要执行.
    // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
    isFirstLoad = true
    val view = initViews(inflater, container, savedInstanceState)
    isPrepared = true
    lazyLoad()
    return view
  }

  /**
   * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
   *
   * @param isVisibleToUser 是否显示出来了
   */
  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (userVisibleHint) {
      println("执行一次>>>>>>>>>>>>>>>>>")
      misVisible = true
      onVisible()
    } else {
      misVisible = false
      onInvisible()
    }
  }

  /**
   * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
   * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
   *
   * @param hidden hidden True if the fragment is now hidden, false if it is not
   * visible.
   */
  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    if (!hidden) {
      misVisible = true
      onVisible()
    } else {
      misVisible = false
      onInvisible()
    }
  }

  /*再次调用可见方法*/
  override fun onResume() {
    super.onResume()
    if (userVisibleHint) {
      userVisibleHint = true
    }
  }

  protected fun onVisible() {
    lazyLoad()
  }

  protected fun onInvisible() {}

  /**
   * 要实现延迟加载Fragment内容,需要在 onCreateView
   * isPrepared = true;
   */
  protected fun lazyLoad() {
    if (!isPrepared || !misVisible || !isFirstLoad) {
      //if (!isAdded() || !isVisible || !isFirstLoad) {
      return
    }
    isFirstLoad = false
    initData()
  }

  protected abstract fun initViews(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View

  protected abstract fun initData()
  var title: String?
    get() {
      if (null == fragmentTitle) {
        setDefaultFragmentTitle(null)
      }
      return if (TextUtils.isEmpty(fragmentTitle)) "" else fragmentTitle
    }
    set(title) {
      fragmentTitle = title
    }

  /**
   * 设置fragment的Title直接调用 [BaseFragment.setTitle],若不显示该title 可以不做处理
   *
   * @param title 一般用于显示在TabLayout的标题
   */
  protected abstract fun setDefaultFragmentTitle(title: String?)
}