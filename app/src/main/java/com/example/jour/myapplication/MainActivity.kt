package com.example.jour.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.example.jour.myapplication.databinding.ActivityMainBinding
import com.example.jour.myapplication.ui.*
import com.example.jour.myapplication.ui.banner.BannerActivity
import com.example.jour.myapplication.ui.catalogue.Catalogue2Activity
import com.example.jour.myapplication.ui.catalogue.CatalogueActivity
import com.example.jour.myapplication.ui.coordinator.CoordinatorActivity
import com.example.jour.myapplication.ui.editview.EditTextActivity
import com.example.jour.myapplication.ui.media.MediaPlayActivity
import com.example.jour.myapplication.ui.recyclerview.RecyclerViewRefreshActivity
import com.example.jour.myapplication.ui.room.RoomActivity
import com.example.jour.myapplication.ui.verification.VerificationCodeActivity
import com.example.jour.myapplication.ui.vp2.ViewPager2Activity
import com.example.jour.myapplication.view.TelNumCheckerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


/**
 * @author lvchen
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.mainInclude.toolbar)

        binding.mainInclude.fab.setOnClickListener { view ->
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
            this,
            binding.drawerLayout,
            binding.mainInclude.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        binding.mainInclude.contentMain.viewpagerBtn.setOnClickListener {
            startActivity(
                Intent(this, ViewPagerActivity::class.java)
            )
        }

        binding.mainInclude.contentMain.notificationBtn.setOnClickListener {
            startActivity(
                Intent(this, NotificationActivity::class.java)
            )
        }

        binding.mainInclude.contentMain.wallpaperBtn.setOnClickListener {
            startActivity(
                Intent(this, WallPaperActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.catalogue.setOnClickListener {
            startActivity(
                Intent(this, CatalogueActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.catalogue2.setOnClickListener {
            startActivity(
                Intent(this, Catalogue2Activity::class.java)
            )
        }
        binding.mainInclude.contentMain.waterfall.setOnClickListener {
            startActivity(
                Intent(this, WaterFallActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.routerBtn.setOnClickListener {
            ARouter.getInstance()
                .build("/test/activity2")
//          .withInt("audit", 666)
//          .withString("comment", "888")
                .navigation()
        }
        binding.mainInclude.contentMain.roomBtn.setOnClickListener {
            startActivity(
                Intent(this, RoomActivity::class.java)
            )
        }

        binding.mainInclude.contentMain.cycleHeadBtn.setOnClickListener {
            startActivity(
                Intent(this, CycleHeadActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.chips.setOnClickListener {
            startActivity(
                Intent(this, ChipActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.recycleBtn.setOnClickListener {
            startActivity(
                Intent(this, RecyclerViewRefreshActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.vp2Btn.setOnClickListener {
            startActivity(
                Intent(this, ViewPager2Activity::class.java)
            )
        }
        binding.mainInclude.contentMain.vpBtn.setOnClickListener {
            startActivity(
                Intent(this, com.example.jour.myapplication.ui.vp.ViewPagerActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.mediaBtn.setOnClickListener {
            startActivity(
                Intent(this, MediaPlayActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.coordinator.setOnClickListener {
            startActivity(
                Intent(this, CoordinatorActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.scrollableBanner.setOnClickListener {
            startActivity(
                Intent(this, BannerActivity::class.java)
            )
        }
        binding.mainInclude.contentMain.verifyBtn2.setOnClickListener {
            startActivity(Intent(this, VerificationCodeActivity::class.java))
        }

        binding.mainInclude.contentMain.verifyBtn.setOnClickListener {

            val dialog = AlertDialog.Builder(this, R.style.inputDialog).apply {
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

        binding.mainInclude.contentMain.FolderTextView.setOnClickListener {
            startActivity(Intent(this, FolderTextViewActivity::class.java))
        }

        binding.mainInclude.contentMain.editBtn.setOnClickListener {
            startActivity(Intent(this, EditTextActivity::class.java))
        }
        binding.mainInclude.contentMain.loading.show()
//        showLoading()

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


    private var progressDialog: ProgressDialog? = null

    private fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

}
