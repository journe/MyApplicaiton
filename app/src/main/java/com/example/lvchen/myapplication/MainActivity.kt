package com.example.lvchen.myapplication

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.idaddy.android.opensdk.lib.IdaddySdk
import com.example.lvchen.myapplication.ui.BlankFragment
import com.example.lvchen.myapplication.ui.GranzortViewActivity
import com.example.lvchen.myapplication.ui.PActivity
import com.example.lvchen.myapplication.ui.RecycleViewActivity
import com.example.lvchen.myapplication.ui.ScrollingActivity
import com.example.lvchen.myapplication.ui.ViewPagerActivity
import com.example.lvchen.myapplication.ui.XiaoAiTestActivity
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.fab
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_main.fragment_btn
import kotlinx.android.synthetic.main.content_main.granzort_btn
import kotlinx.android.synthetic.main.content_main.imageView
import kotlinx.android.synthetic.main.content_main.scroll_button
import kotlinx.android.synthetic.main.content_main.viewpager_btn
import kotlinx.android.synthetic.main.content_main.wallpaper_btn
import kotlinx.android.synthetic.main.content_main.xiao_ai_btn
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author lvchen
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener,
    BlankFragment.OnFragmentInteractionListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null)
          .show()
    }

    val toggle = ActionBarDrawerToggle(
        this, drawer_layout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    )
    drawer_layout.setDrawerListener(toggle)
    toggle.syncState()
    nav_view.setNavigationItemSelectedListener(this)
    granzort_btn.setOnClickListener(this)
    viewpager_btn.setOnClickListener(this)
    wallpaper_btn.setOnClickListener(this)
    fragment_btn.setOnClickListener(this)
    scroll_button.setOnClickListener(this)
    xiao_ai_btn.setOnClickListener { startActivity(Intent(this, XiaoAiTestActivity::class.java)) }
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
    val id = item.itemId

    when (id) {
      R.id.nav_camera -> {
        // Handle the camera action
      }
      R.id.nav_gallery -> startActivity(Intent(this@MainActivity, RecycleViewActivity::class.java))
      R.id.nav_slideshow -> {

      }
      R.id.nav_manage -> startActivity(Intent(this@MainActivity, PActivity::class.java))
      R.id.nav_share -> IdaddySdk.start()
      R.id.nav_send -> {

      }
    }

    val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
    drawer.closeDrawer(GravityCompat.START)
    return true
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.granzort_btn -> startActivity(
          Intent(this@MainActivity, GranzortViewActivity::class.java)
      )
      R.id.viewpager_btn -> startActivity(Intent(this@MainActivity, ViewPagerActivity::class.java))
      R.id.fragment_btn -> {
        val fragment = BlankFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentview, fragment)
            .commit()
      }
      R.id.scroll_button -> {
        startActivity(Intent(this@MainActivity, ScrollingActivity::class.java))
      }
      R.id.wallpaper_btn -> {
        val wallpaperManager = WallpaperManager
            .getInstance(applicationContext)
        // 获取当前壁纸
        val drawable = wallpaperManager.drawable as BitmapDrawable
        Log.d("MainActivity", "drawable.getIntrinsicHeight():" + drawable.intrinsicHeight)
        imageView!!.setImageDrawable(drawable)
        val bitmap = drawable.bitmap
        var fos: FileOutputStream? = null
        try {
          fos = openFileOutput("image.png", Context.MODE_PRIVATE)
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
        } finally {
          if (fos != null) {
            try {
              fos.flush()
              fos.close()
            } catch (e: IOException) {
            }

          }
        }
      }
      else -> {
      }
    }
  }

  override fun onFragmentInteraction(uri: Uri) {

  }
}
