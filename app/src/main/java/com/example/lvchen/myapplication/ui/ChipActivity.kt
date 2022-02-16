package com.example.lvchen.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lvchen.myapplication.databinding.ActivityChipBinding
import com.google.android.material.chip.Chip

class ChipActivity : AppCompatActivity() {

	private lateinit var binding: ActivityChipBinding

	private val textList = listOf("tag1", "tag2", "tag3", "tag2", "tag2")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityChipBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)

	}

	override fun onResume() {
		super.onResume()
		textList.forEach {
			val chip = Chip(baseContext)
			chip.text = it
			binding.chipGroup.addView(chip)
		}

	}
}