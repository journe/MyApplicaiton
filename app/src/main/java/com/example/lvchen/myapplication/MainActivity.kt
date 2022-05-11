package com.example.lvchen.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lvchen.myapplication.ui.*
import com.example.lvchen.myapplication.ui.recyclerview.RecyclerViewRefreshActivity
import com.example.lvchen.myapplication.utils.KeyboardUtils
import com.example.lvchen.myapplication.view.TelNumCheckerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
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
        room_btn.setOnClickListener {
            startActivity(
                Intent(this, RoomActivity::class.java)
            )
        }

        cycle_head_btn.setOnClickListener {
            startActivity(
                Intent(this, CycleHeadActivity::class.java)
            )
        }
        chips.setOnClickListener {
            startActivity(
                Intent(this, ChipActivity::class.java)
            )
        }
        recycle_btn.setOnClickListener {
            startActivity(
                Intent(this, RecyclerViewRefreshActivity::class.java)
            )
        }
        verify_btn.setOnClickListener {

            val dialog = AlertDialog.Builder(this,R.style.inputDialog).apply {
                setView(TelNumCheckerView(context))
                setPositiveButton(
                    "确定"
                ) { dialog, which ->
                }
                setNegativeButton(
                    "关闭"
                ) { dialog, which ->
                }
            }
            dialog.create().show()
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
        val drawer =
            findViewById<View>(R.id.drawer_layout) as androidx.drawerlayout.widget.DrawerLayout
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
//      R.id.nav_share -> IdaddySdk.start()
            R.id.nav_send -> startActivity(Intent(this, XiaoAiTestActivity::class.java))
        }

        val drawer =
            findViewById<View>(R.id.drawer_layout) as androidx.drawerlayout.widget.DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
