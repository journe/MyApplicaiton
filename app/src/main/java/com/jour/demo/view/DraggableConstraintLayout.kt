package com.jour.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.customview.widget.ViewDragHelper

class DraggableConstraintLayout : ConstraintLayout {

	private lateinit var viewDragHelper: ViewDragHelper

	constructor(context: Context) : super(context) {
		init(context)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init(context)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
		context,
		attrs,
		defStyle
	) {
		init(context)
	}

	private fun init(context: Context) {
		viewDragHelper = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
			override fun tryCaptureView(
				child: View,
				pointerId: Int
			): Boolean {
				return true
			}

			override fun clampViewPositionHorizontal(
				child: View,
				left: Int,
				dx: Int
			): Int {
				return left
			}

			override fun clampViewPositionVertical(
				child: View,
				top: Int,
				dy: Int
			): Int {
				return top
			}
		})
	}

	override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
		return viewDragHelper.shouldInterceptTouchEvent(ev!!)
	}

	override fun onTouchEvent(event: MotionEvent?): Boolean {
		viewDragHelper.processTouchEvent(event!!)
		return true;
	}


}