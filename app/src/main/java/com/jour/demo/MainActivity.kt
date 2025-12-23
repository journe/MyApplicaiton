package com.jour.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.databinding.ActivityMainBinding
import com.jour.demo.databinding.FragmentFirstBinding
import com.jour.demo.ui.GranzortViewActivity
import com.jour.demo.ui.RecycleViewActivity
import com.jour.demo.ui.XiaoAiTestActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, EmptyViewModel>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun ActivityMainBinding.initView() {

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_manage -> startActivity(
                    Intent(
                        this@MainActivity, cn.gavinliu.similar.photo.MainActivity::class.java
                    )
                )

                R.id.nav_gallery -> startActivity(
                    Intent(
                        this@MainActivity,
                        RecycleViewActivity::class.java
                    )
                )

                R.id.nav_slideshow -> startActivity(
                    Intent(
                        this@MainActivity,
                        GranzortViewActivity::class.java
                    )
                )
                //      R.id.nav_share -> IdaddySdk.start()
                R.id.nav_send -> startActivity(
                    Intent(
                        this@MainActivity,
                        XiaoAiTestActivity::class.java
                    )
                )
            }

            val drawer =
                findViewById<View>(R.id.drawer_layout) as androidx.drawerlayout.widget.DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            true
        }

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?
            ?: return

        navController = host.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)


//        binding.mainInclude.contentMain.routerBtn.setOnClickListener {
//            ARouter.getInstance()
//                .build("/test/activity2")
////          .withInt("audit", 666)
////          .withString("comment", "888")
//                .navigation()
//        }

    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    // 重写此方法，让 Toolbar 的返回键/汉堡菜单生效
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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
}
