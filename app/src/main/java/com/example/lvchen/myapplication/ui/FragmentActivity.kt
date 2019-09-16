package com.example.lvchen.myapplication.ui

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lvchen.myapplication.R
import kotlinx.android.synthetic.main.activity_fragment.fragment_btn

class FragmentActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fragment)

    fragment_btn.setOnClickListener {
      val fragment = BlankFragment()
      supportFragmentManager.beginTransaction()
          .replace(R.id.fragmentview, fragment)
          .commit()
    }
  }

  override fun onFragmentInteraction(uri: Uri) {

  }
}
