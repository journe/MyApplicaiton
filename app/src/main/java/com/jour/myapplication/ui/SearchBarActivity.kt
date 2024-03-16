package com.jour.myapplication.ui

import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import com.google.android.material.color.MaterialColors
import com.jour.myapplication.R
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseActivity
import com.jour.myapplication.databinding.AcitvitySearchBarBinding
import org.jetbrains.anko.toggleButton

class SearchBarActivity : BaseActivity<AcitvitySearchBarBinding, EmptyViewModel>() {
	override val mViewModel: EmptyViewModel by viewModels()

	override fun AcitvitySearchBarBinding.initView() {
		setSupportActionBar(searchBar)

		val drawerArrowDrawable = DrawerArrowDrawable(this@SearchBarActivity)
		drawerArrowDrawable.color =
			MaterialColors.getColor(searchBar, R.attr.colorOnSurface)
		searchBar.navigationIcon = drawerArrowDrawable

		val toggle = ActionBarDrawerToggle(
			this@SearchBarActivity,
			drawerLayout,
			searchBar,
			R.string.navigation_drawer_open,
			R.string.navigation_drawer_close
		)
		drawerLayout.addDrawerListener(toggle)
		toggle.syncState()
		searchBar.setNavigationOnClickListener {
			drawerLayout.open()
		}
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

}
