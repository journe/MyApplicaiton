package com.jour.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.core.view.ViewCompat
import com.hjq.shape.view.ShapeImageView
import com.jour.demo.utils.TouchController
import com.jour.demo.utils.TouchController.TouchMapListener
import kotlin.apply
import kotlin.ranges.coerceAtLeast
import kotlin.ranges.coerceAtMost

class ImageMoveScaleGestureDetectorView : ShapeImageView {

	constructor(context: Context) : super(context)

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

	private val AXIS_X_MIN = 0f
	private val AXIS_Y_MIN = 0f
	private val AXIS_X_MAX = 1080f
	private val AXIS_Y_MAX = 1920f

	private var mScaleFactor = 1f
	private val mCurrentViewport = RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX)



	// The current destination rectangle, in pixel coordinates, into which the
// chart data must be drawn.
	private val mContentRect: Rect? = null

	private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
		override fun onScroll(
			e1: MotionEvent?,
			e2: MotionEvent,
			distanceX: Float,
			distanceY: Float
		): Boolean {
			// Scrolling uses math based on the viewport, as opposed to math using
			// pixels.

			mContentRect?.apply {
				// Pixel offset is the offset in screen pixels, while viewport offset is the
				// offset within the current viewport.
				val viewportOffsetX = distanceX * mCurrentViewport.width() / width()
				val viewportOffsetY = -distanceY * mCurrentViewport.height() / height()


				// Updates the viewport and refreshes the display.
				setViewportBottomLeft(
					mCurrentViewport.left + viewportOffsetX,
					mCurrentViewport.bottom + viewportOffsetY
				)
			}

			return true
		}
	}

	private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

		override fun onScale(detector: ScaleGestureDetector): Boolean {
			mScaleFactor *= detector.scaleFactor

			// Don't let the object get too small or too large.
			mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f))

			invalidate()
			return true
		}
	}

	private val mScaleGestureDetector = ScaleGestureDetector(context, scaleListener)
	private val mGestureDetector = GestureDetector(context, mGestureListener)

	/**
	 * Sets the current viewport, defined by mCurrentViewport, to the given
	 * X and Y positions. The Y value represents the topmost pixel position,
	 * and thus the bottom of the mCurrentViewport rectangle.
	 */
	private fun setViewportBottomLeft(x: Float, y: Float) {
		/*
		 * Constrains within the scroll range. The scroll range is the viewport
		 * extremes, such as AXIS_X_MAX, minus the viewport size. For example, if
		 * the extremes are 0 and 10 and the viewport size is 2, the scroll range
		 * is 0 to 8.
		 */

		val curWidth: Float = mCurrentViewport.width()
		val curHeight: Float = mCurrentViewport.height()
		val newX: Float = Math.max(AXIS_X_MIN, Math.min(x, AXIS_X_MAX - curWidth))
		val newY: Float = Math.max(AXIS_Y_MIN + curHeight, Math.min(y, AXIS_Y_MAX))

		mCurrentViewport.set(newX, newY - curHeight, newX + curWidth, newY)

		// Invalidates the View to update the display.
		postInvalidateOnAnimation()
	}

	override fun onTouchEvent(event: MotionEvent): Boolean {
		return mScaleGestureDetector.onTouchEvent(event)
				|| mGestureDetector.onTouchEvent(event)
				|| super.onTouchEvent(event)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		canvas.apply {
			save()
			scale(mScaleFactor, mScaleFactor)
			// onDraw() code goes here.
			restore()
		}
	}
}
