package com.jour.demo.ui

import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialSharedAxis
import com.jour.demo.R
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.databinding.AcitvitySearchBarBinding

class SearchBarActivity : BaseActivity<AcitvitySearchBarBinding, EmptyViewModel>() {

	private var openSearchViewTransition = createSearchViewTransition(true)
	private var closeSearchViewTransition: Transition? = null

	private lateinit var headerContainer: ConstraintLayout
	private lateinit var searchView: SearchView
	private lateinit var searchButton: ImageButton

	override fun AcitvitySearchBarBinding.initView() {
		headerContainer = content.catTocHeaderContainer
		searchView = content.catTocSearchView
		searchButton = content.catTocSearchButton

		setSupportActionBar(content.searchBar)

		val drawerArrowDrawable = DrawerArrowDrawable(this@SearchBarActivity)
		drawerArrowDrawable.color =
			MaterialColors.getColor(content.searchBar, com.google.android.material.R.attr.colorOnSurface)
		content.searchBar.navigationIcon = drawerArrowDrawable

		val toggle = ActionBarDrawerToggle(
			this@SearchBarActivity,
			drawerLayout,
			content.searchBar,
			R.string.navigation_drawer_open,
			R.string.navigation_drawer_close
		)
		drawerLayout.addDrawerListener(toggle)
		toggle.syncState()
		content.searchBar.setNavigationOnClickListener {
			drawerLayout.open()
		}

		initSearchButton()
		initSearchView()
		initSearchViewTransitions()

	}

	private fun initSearchButton() {
		searchButton.setOnClickListener { v: View? -> openSearchView() }
	}

	private fun initSearchView() {
		searchView.setOnClickListener { v: View? -> closeSearchView() }

		searchView.setOnQueryTextListener(
			object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String): Boolean {
					return false
				}

				override fun onQueryTextChange(newText: String): Boolean {
//					tocAdapter.getFilter().filter(newText)
					return false
				}
			})
	}

	private fun initSearchViewTransitions() {
		openSearchViewTransition = createSearchViewTransition(true)
		closeSearchViewTransition = createSearchViewTransition(false)
	}

	private fun openSearchView() {
		TransitionManager.beginDelayedTransition(headerContainer, openSearchViewTransition)
		headerContainer.visibility = View.GONE
		searchView.visibility = View.VISIBLE
		searchView.requestFocus()
	}

	private fun closeSearchView() {
		TransitionManager.beginDelayedTransition(headerContainer, closeSearchViewTransition)
		headerContainer.visibility = View.VISIBLE
		searchView.visibility = View.GONE
		clearSearchView()
	}

	private fun clearSearchView() {
		if (searchView != null) {
			searchView.setQuery("", true)
		}
	}

	private fun createSearchViewTransition(entering: Boolean): MaterialSharedAxis {
		val sharedAxisTransition = MaterialSharedAxis(MaterialSharedAxis.X, entering)
		sharedAxisTransition.addTarget(headerContainer)
		sharedAxisTransition.addTarget(searchView)
		return sharedAxisTransition
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}


}
