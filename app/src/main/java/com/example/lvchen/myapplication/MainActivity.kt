package com.example.lvchen.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.idaddy.android.opensdk.lib.IdaddySdk
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lvchen.myapplication.ui.Catalogue2Activity
import com.example.lvchen.myapplication.ui.CatalogueActivity
import com.example.lvchen.myapplication.ui.GranzortViewActivity
import com.example.lvchen.myapplication.ui.NotificationActivity
import com.example.lvchen.myapplication.ui.RecycleViewActivity
import com.example.lvchen.myapplication.ui.CoordinatorLayoutActivity
import com.example.lvchen.myapplication.ui.ViewPagerActivity
import com.example.lvchen.myapplication.ui.WallPaperActivity
import com.example.lvchen.myapplication.ui.WaterFallActivity
import com.example.lvchen.myapplication.ui.XiaoAiTestActivity
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.fab
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_main.*

/**
 * @author lvchen
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null)
          .show()
      val intent = Intent(Intent.ACTION_VIEW)
      val str =
        "suning://m.suning.com/index?adTypeCode=1002&adId=https%3A%2F%2Fcuxiao.m.suning.com%2Fscms%2Fcw03.html%3Futm_source%3Ddsp-ay%26utm_medium%3Das-cw1-30ll2-in5%26utm_campaign%3D%252C3%252Ca691f49313b24ea197107a1406aa9cf7%26utm_term%3DfZjq4KzfGgsu3k5D6IPl0PsfGaHe58%26adtype%3D7&utm_source=dsp-ay&utm_medium=as-cw1-30ll2-in5&utm_campaign=%2C3%2Ca691f49313b24ea197107a1406aa9cf7&utm_term=fZjq4KzfGgsu3k5D6IPl0PsfGaHe58&adtype=7"
      val str1 = "http://www.baidu.com"
      intent.data = Uri.parse(str)
      if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
      } else {
        intent.data = Uri.parse(str1)
      }
      startActivity(intent)
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
    catalogue2.setOnClickListener {
      startActivity(
          Intent(this, Catalogue2Activity::class.java)
      )
    }
    waterfall.setOnClickListener {
      startActivity(
          Intent(this, WaterFallActivity::class.java)
      )
    }
    router_btn.setOnClickListener {
      ARouter.getInstance()
          .build("/test/activity2")
//          .withInt("audit", 666)
//          .withString("comment", "888")
          .navigation()
    }
//    NetworkService.apiService.sendVerifyCode2("18512527462")
//        .enqueue(object :
//            Callback<ResponseBody> {
//          override fun onFailure(
//            call: Call<ResponseBody>,
//            t: Throwable
//          ) {
//            Logger.d(t.message)
//          }
//
//          override fun onResponse(
//            call: Call<ResponseBody>,
//            response: Response<ResponseBody>
//          ) {
//            Logger.d(response.body().toString())
//          }
//
//        })
  }

  override fun onBackPressed() {
    val drawer = findViewById<View>(R.id.drawer_layout) as androidx.drawerlayout.widget.DrawerLayout
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
      R.id.nav_manage -> startActivity(Intent(this, CoordinatorLayoutActivity::class.java))
      R.id.nav_gallery -> startActivity(Intent(this, RecycleViewActivity::class.java))
      R.id.nav_slideshow -> startActivity(Intent(this, GranzortViewActivity::class.java))
      R.id.nav_share -> IdaddySdk.start()
      R.id.nav_send -> startActivity(Intent(this, XiaoAiTestActivity::class.java))
    }

    val drawer = findViewById<View>(R.id.drawer_layout) as androidx.drawerlayout.widget.DrawerLayout
    drawer.closeDrawer(GravityCompat.START)
    return true
  }

}
