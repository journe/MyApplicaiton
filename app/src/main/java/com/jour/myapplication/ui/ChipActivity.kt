package com.jour.myapplication.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.databinding.ActivityChipBinding
import com.google.android.material.chip.Chip
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

	}

	override fun onResume() {
		super.onResume()
		textList.forEach { string ->
			binding.chipGroup.addView(Chip(this).apply {
				text = string
				textColor = Color.BLUE
				setOnClickListener {
					toast(string)
				}
			})
		}

	}
}