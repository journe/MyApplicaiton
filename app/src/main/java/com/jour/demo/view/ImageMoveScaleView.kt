package com.jour.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.hjq.shape.view.ShapeImageView
import com.jour.demo.utils.TouchController
import com.jour.demo.utils.TouchController.TouchMapListener
import kotlin.apply
import kotlin.ranges.coerceAtLeast
import kotlin.ranges.coerceAtMost

class ImageMoveScaleView : ShapeImageView {

	constructor(context: Context) : super(context)

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

	private var mScaleFactor = 1f

	private val touchController = TouchController().apply {
		touchListener = object : TouchMapListener {
			override fun onDrag(xDiff: Double, yDiff: Double) {
				val dx = xDiff.toInt()
				val dy = yDiff.toInt()
				layout(left + dx, top + dy, right + dx, bottom + dy)
			}

			override fun scale(scale: Double) {
				mScaleFactor *= scale.toFloat()
				// Don't let the object get too small or too large.
				mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(5.0f))

				scaleX = mScaleFactor
				scaleY = mScaleFactor
			}
		}
	}

	override fun onTouchEvent(event: MotionEvent): Boolean {
		touchController.onTouch(event)
		when (event.action) {
			MotionEvent.ACTION_DOWN -> {
				isSelected = true
			}

			MotionEvent.ACTION_UP -> {
				isSelected = false
			}
		}

		return true
	}
}
