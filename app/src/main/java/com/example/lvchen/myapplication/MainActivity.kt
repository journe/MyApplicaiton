package com.example.lvchen.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import cn.idaddy.android.opensdk.lib.IdaddySdk
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lvchen.myapplication.ui.BaseActivity
import com.example.lvchen.myapplication.ui.Catalogue2Activity
import com.example.lvchen.myapplication.ui.CatalogueActivity
import com.example.lvchen.myapplication.ui.CountDownViewWrap2
import com.example.lvchen.myapplication.ui.GranzortViewActivity
import com.example.lvchen.myapplication.ui.NotificationActivity
import com.example.lvchen.myapplication.ui.RecycleViewActivity
import com.example.lvchen.myapplication.ui.ScrollingActivity
import com.example.lvchen.myapplication.ui.ViewPagerActivity
import com.example.lvchen.myapplication.ui.WallPaperActivity
import com.example.lvchen.myapplication.ui.XiaoAiTestActivity
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.fab
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_main.catalogue
import kotlinx.android.synthetic.main.content_main.catalogue2
import kotlinx.android.synthetic.main.content_main.main_frameLayout
import kotlinx.android.synthetic.main.content_main.notification_btn
import kotlinx.android.synthetic.main.content_main.router_btn
import kotlinx.android.synthetic.main.content_main.viewpager_btn
import kotlinx.android.synthetic.main.content_main.wallpaper_btn
import org.jetbrains.anko.dip

/**
 * @author lvchen
 */
class MainActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    val cdView = CountDownViewWrap2(this, null)
    main_frameLayout.addView(cdView)
    cdView.layoutParams = (cdView.layoutParams as FrameLayout.LayoutParams).apply {
      topMargin = dip(36)
      rightMargin = dip(80)
      gravity = Gravity.RIGHT
      width = ViewGroup.LayoutParams.WRAP_CONTENT
      height = ViewGroup.LayoutParams.WRAP_CONTENT
    }
    cdView.isClickable = true
    cdView.setOnTouchListener(object :View.OnTouchListener{
      override fun onTouch(
        v: View?,
        event: MotionEvent?
      ): Boolean {
        MetTouchLeftClick(event!!,v!!)
        return true
      }
    })
    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null)
          .show()
    }

    val toggle = ActionBarDrawerToggle(
        this, drawer_layout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
    nav_view.setNavigationItemSelectedListener(this)

    viewpager_btn.setOnClickListener {
      startActivity(
          Intent(this, ViewPagerActivity::class.java)
      )
    }

    notification_btn.setOnClickListener {
      startActivity(
          Intent(this, NotificationActivity::class.java)
      )
    }

    wallpaper_btn.setOnClickListener {
      startActivity(
          Intent(this, WallPaperActivity::class.java)
      )
    }
    catalogue.setOnClickListener {
      startActivity(
          Intent(this, CatalogueActivity::class.java)
      )
    }
    catalogue2.setOnClickListener {
      startActivity(
          Intent(this, Catalogue2Activity::class.java)
      )
    }
    router_btn.setOnClickListener {
      ARouter.getInstance()
          .build("/test/activity2")
//          .withInt("audit", 666)
//          .withString("comment", "888")
          .navigation()
    }
  }

  var y1 = 0;
  var newY = 0;
  var dY = 0;
  var dX = 0;
  var newX = 0;
  var x1 = 0;

  private fun MetTouchLeftClick(
    event: MotionEvent,
    cdView: View
  ) {
    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        //当手指按下的时候
        x1 = event.rawX.toInt()
        y1 = event.rawY.toInt()
      }
      MotionEvent.ACTION_MOVE -> {
        newY = event.rawY.toInt()
        newX = event.rawX.toInt()
        dY = newY - y1
        dX = newX - x1
        var l = cdView.getLeft()
        var t = cdView.getTop()
        l += dX
        t += dY
        val r = l + cdView.getWidth()
        val b = t + cdView.getHeight()
        cdView.layout(l, t, r, b)

        x1 = newX
        y1 = newY
      }
    }
  }

  override fun onBackPressed() {
    val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    val id = item.itemId
    return if (id == R.id.action_settings) {
      true
    } else super.onOptionsItemSelected(item)

  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
//      R.id.nav_camera -> startActivity(Intent(this, FragmentActivity::class.java))
      R.id.nav_manage -> startActivity(Intent(this, ScrollingActivity::class.java))
      R.id.nav_gallery -> startActivity(Intent(this, RecycleViewActivity::class.java))
      R.id.nav_slideshow -> startActivity(Intent(this, GranzortViewActivity::class.java))
      R.id.nav_share -> IdaddySdk.start()
      R.id.nav_send -> startActivity(Intent(this, XiaoAiTestActivity::class.java))
    }

    val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
    drawer.closeDrawer(GravityCompat.START)
    return true
  }

}
