package com.example.jour.myapplication.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.jour.myapplication.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
  private val context: Context,
  fm: androidx.fragment.app.FragmentManager
) : androidx.fragment.app.FragmentPagerAdapter(fm) {

  override fun getItem(position: Int): androidx.fragment.app.Fragment {
    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    return PlaceholderFragment.newInstance(
        position + 1
    )
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return context.resources.getString(TAB_TITLES[position])
  }

  override fun getCount(): Int {
    // Show 2 total pages.
    return 2
  }
}