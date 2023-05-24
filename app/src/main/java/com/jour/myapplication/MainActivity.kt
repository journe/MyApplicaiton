package com.jour.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.jour.myapplication.databinding.ActivityMainBinding
import com.jour.myapplication.ui.GranzortViewActivity
import com.jour.myapplication.ui.RecycleViewActivity
import com.jour.myapplication.ui.XiaoAiTestActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)


//        binding.mainInclude.contentMain.routerBtn.setOnClickListener {
//            ARouter.getInstance()
//                .build("/test/activity2")
////          .withInt("audit", 666)
////          .withString("comment", "888")
//                .navigation()
//        }

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
            R.id.nav_manage -> startActivity(
                Intent(
                    this, cn.gavinliu.similar.photo.MainActivity::class.java
                )
            )
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
