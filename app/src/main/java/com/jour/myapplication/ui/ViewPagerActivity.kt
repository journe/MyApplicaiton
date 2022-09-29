package com.jour.myapplication.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.jour.myapplication.R.drawable
import com.jour.myapplication.R.id
import com.jour.myapplication.R.layout
import com.jour.myapplication.utils.MyGridViewAdpter
import com.jour.myapplication.utils.ProdctBean
import java.util.ArrayList
import kotlin.math.ceil

class ViewPagerActivity : AppCompatActivity() {
  private var viewPager: ViewPager? = null
  private var group // 圆点指示器
      : LinearLayout? = null

  //  private val ivPoints // 小圆点图片的集合
//      : Array<ImageView>
  private val mPageSize = 8 // 每页显示的最大的数量

  private var viewPagerList // GridView作为一个View对象添加到ViewPager集合中
      : MutableList<View> = ArrayList()

  // private int currentPage;//当前页
  private val proName = arrayOf(
      "名称0", "名称1", "名称2", "名称3", "名称4", "名称5", "名称6", "名称7", "名称8", "名称9", "名称10", "名称11", "名称12",
      "名称13", "名称14", "名称15", "名称16", "名称17", "名称18", "名称19"
  )
  private val listDatas = proName.map {
    ProdctBean(it, drawable.ic_menu_camera)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_view_pager)
    // 初始化控件
    initView()
    // 添加业务逻辑
    initData()
  }

  private fun initView() {
    viewPager = findViewById<View>(id.viewpager) as ViewPager
    group = findViewById<View>(id.points) as LinearLayout
  }

  private fun initData() {
    // 总的页数向上取整
    val totalPage = ceil(listDatas!!.size * 1.0 / mPageSize)
        .toInt()
    viewPagerList = ArrayList()
    for (i in 0 until totalPage) {
      // 每个页面都是inflate出一个新实例
      val gridView =
        View.inflate(this, layout.view_pager_grid_view, null) as GridView
      gridView.adapter = MyGridViewAdpter(this, listDatas, i, mPageSize)
      // 添加item点击监听
      gridView.onItemClickListener =
        OnItemClickListener { arg0, arg1, position, arg3 -> // TODO Auto-generated method stub
          val obj = gridView.getItemAtPosition(position)
          if (obj != null && obj is ProdctBean) {
            println(obj)
            Toast.makeText(
                this@ViewPagerActivity, obj.name, Toast.LENGTH_SHORT
            )
                .show()
          }
        }
      // 每一个GridView作为一个View对象添加到ViewPager集合中
      viewPagerList.add(gridView)
    }
    // 设置ViewPager适配器
    viewPager!!.adapter = MyViewPagerAdapter(viewPagerList)

    //		//添加小圆点
    //		ivPoints = new ImageView[totalPage];
    //		for(int i = 0; i < totalPage; i++){
    //			//循坏加入点点图片组
    //			ivPoints[i] = new ImageView(this);
    //			if(i==0){
    //				ivPoints[i].setImageResource(R.drawable.page_focuese);
    //			}else {
    //				ivPoints[i].setImageResource(R.drawable.page_unfocused);
    //			}
    //			ivPoints[i].setPadding(8, 8, 8, 8);
    //			group.addView(ivPoints[i]);
    //		}
    //		//设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
    //		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
    //			@Override
    //			public void onPageSelected(int position) {
    //				// TODO Auto-generated method stub
    //				//currentPage = position;
    //				for(int i=0 ; i < totalPage; i++){
    //					if(i == position){
    //						ivPoints[i].setImageResource(R.drawable.page_focuese);
    //					}else {
    //						ivPoints[i].setImageResource(R.drawable.page_unfocused);
    //					}
    //				}
    //			}
    //		});

    //设置viewpager2
  }

  /**
   * ViewPage的适配器
   *
   * @author Administrator
   */
  inner class MyViewPagerAdapter(// View就二十GridView
    private val viewList: List<View>?
  ) :
      PagerAdapter() {

    override fun getCount(): Int {
      return viewList?.size ?: 0
    }

    override fun isViewFromObject(
      arg0: View,
      arg1: Any
    ): Boolean {
      return arg0 === arg1
    }

    /** 将当前的View添加到ViewGroup容器中 这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPage上  */
    override fun instantiateItem(
      container: ViewGroup,
      position: Int
    ): Any {
      container.addView(viewList!![position])
      return viewList[position]
    }

    override fun destroyItem(
      container: ViewGroup,
      position: Int,
      `object`: Any
    ) {
      container.removeView(`object` as View)
    }

  }
}