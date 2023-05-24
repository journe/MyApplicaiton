package com.jour.myapplication.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.databinding.ActivityChipBinding
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast

class ChipActivity : AppCompatActivity() {

	private lateinit var binding: ActivityChipBinding

	private val textList = listOf(
		"tag1",
		"tag2",
		"tag3",
		"tag29",
		"tag28",
		"tag27",
		"tag26",
		"tag25",
		"tag24",
		"tag23",
		"tag22",
		"tag21"
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityChipBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)

		textList.forEach { string ->
			binding.chipGroup.addView(Chip(this).apply {
				text = string
				textColor = Color.BLUE
				setOnClickListener {
					toast(string)
				}
			})
		}
		binding.fab.setOnClickListener { view ->
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

	}

	override fun onResume() {
		super.onResume()


	}
}