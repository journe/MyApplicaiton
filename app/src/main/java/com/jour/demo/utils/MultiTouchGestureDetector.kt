package com.jour.demo.utils

import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.pow

class MultiTouchGestureDetector(context: Context, listener: OnMultiTouchGestureListener) {
	private val mContext: Context? = context
	private val mListener: OnMultiTouchGestureListener = listener

	/**
	 * Get the X coordinate of the current gesture's focal point.
	 * If a gesture is in progress, the focal point is between
	 * each of the pointers forming the gesture.
	 *
	 * If [.isInProgress] would return false, the result of this
	 * function is undefined.
	 *
	 * @return X coordinate of the focal point in pixels.
	 */
	var focusX: Float = 0f
		private set

	/**
	 * Get the Y coordinate of the current gesture's focal point.
	 * If a gesture is in progress, the focal point is between
	 * each of the pointers forming the gesture.
	 *
	 * If [.isInProgress] would return false, the result of this
	 * function is undefined.
	 *
	 * @return Y coordinate of the focal point in pixels.
	 */
	var focusY: Float = 0f
		private set

	private var mPreviousFocusX = 0f
	private var mPreviousFocusY = 0f

	private var mCurrentSpan = 0f
	private var mPreviousSpan = 0f

	private var mCurrentRotation = 0f
	private var mPreviousRotation = 0f

	/**
	 * Return the event time of the current event being processed.
	 *
	 * @return Current event time in milliseconds.
	 */
	var eventTime: Long = 0
		private set
	private var mPrevTime: Long = 0

	/**
	 * Returns `true` if a scale gesture is in progress.
	 */
	var isInProgress: Boolean = false
		private set

	private var mInitialSpan = 0f
	private val mSpanSlop: Int

	private var mInitialFocusX = 0f
	private var mInitialFocusY = 0f
	private val mTouchSlopSquare: Int

	/**
	 * Creates a MultiTouchGestureDetector with the supplied listener.
	 * You may only use this constructor from a [Looper][android.os.Looper] thread.
	 *
	 * @param context the application's context
	 * @param listener the listener invoked for all the callbacks, this must
	 * not be null.
	 *
	 * @throws NullPointerException if `listener` is null.
	 */
	init {

		val configuration = ViewConfiguration.get(context)
		val touchSlop = configuration.getScaledTouchSlop()
		mTouchSlopSquare = touchSlop * touchSlop

		mSpanSlop = configuration.getScaledTouchSlop() * 2
	}

	/**
	 * Accepts MotionEvents and dispatches events to a [OnMultiTouchGestureListener]
	 * when appropriate.
	 *
	 *
	 *
	 * Applications should pass a complete and consistent event stream to this method.
	 * A complete and consistent event stream involves all MotionEvents from the initial
	 * ACTION_DOWN to the final ACTION_UP or ACTION_CANCEL.
	 *
	 *
	 * @param event The event to process
	 * @return true if the event was processed and the detector wants to receive the
	 * rest of the MotionEvents in this event stream.
	 */
	fun onTouchEvent(event: MotionEvent): Boolean {
		this.eventTime = event.getEventTime()

		val action = event.getActionMasked()
		val count = event.getPointerCount()

		val touchComplete =
			action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
		val touchStart = action == MotionEvent.ACTION_DOWN

		if (touchStart || touchComplete) {
			if (this.isInProgress) {
				mListener.onEnd(this)
				this.isInProgress = false
			}

			if (touchComplete) {
				return true
			}
		}

		val configChanged =
			action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_POINTER_DOWN

		val pointerUp = action == MotionEvent.ACTION_POINTER_UP
		val skipIndex = if (pointerUp) event.getActionIndex() else -1

		// Determine focal point
		var sumX = 0f
		var sumY = 0f

		val focusX: Float
		val focusY: Float

		val div = if (pointerUp) count - 1 else count

		// compute focusX, focusY
		for (i in 0..<count) {
			if (skipIndex == i) {
				continue
			}

			sumX += event.getX(i)
			sumY += event.getY(i)
		}

		focusX = sumX / div
		focusY = sumY / div

		// Determine average deviation from focal point
		var devSumX = 0f
		var devSumY = 0f
		for (i in 0..<count) {
			if (skipIndex == i) {
				continue
			}

			// Convert the resulting diameter into a radius.
			devSumX += abs((event.getX(i) - focusX).toDouble()).toFloat()
			devSumY += abs((event.getY(i) - focusY).toDouble()).toFloat()
		}
		val devX = devSumX / div
		val devY = devSumY / div

		// Span is the average distance between touch points through the focal point;
		// i.e. the diameter of the circle with a radius of the average deviation from
		// the focal point.
		val spanX = devX * 2
		val spanY = devY * 2

		val span = hypot(spanX.toDouble(), spanY.toDouble()).toFloat()

		// compute rotate
		var rotation = 0f
		outer@ for (i in 0..<count) {
			if (skipIndex == i) {
				continue
			}

			inner@ for (j in i + 1..<count) {
				if (skipIndex == j) {
					continue
				}

				val deltaX = (event.getX(i) - event.getX(j)).toDouble()
				val deltaY = (event.getY(i) - event.getY(j)).toDouble()

				// Convert the resulting diameter into a radius.
				rotation +=
					(
							(Math.toDegrees(
								atan2(
									deltaY,
									deltaX
								)
							) + MAX_ROTATION) % MAX_ROTATION).toFloat()
				break@outer
			}
		}

		// Dispatch begin/end events as needed.
		// If the configuration changes, notify the app to reset its current state by beginning
		// a fresh scale event stream.
		val wasInProgress = this.isInProgress
		if (this.isInProgress && configChanged) {
			mListener.onEnd(this)
			this.isInProgress = false
		}

		if (configChanged) {
			mCurrentSpan = span
			mPreviousSpan = mCurrentSpan
			mInitialSpan = mPreviousSpan

			this.focusX = focusX
			mPreviousFocusX = this.focusX
			mInitialFocusX = mPreviousFocusX
			this.focusY = focusY
			mPreviousFocusY = this.focusY
			mInitialFocusY = mPreviousFocusY

			mCurrentRotation = rotation
			mPreviousRotation = mCurrentRotation
		}

		if (!this.isInProgress && (wasInProgress
					|| abs((span - mInitialSpan).toDouble()) > mSpanSlop || (this.focusX - mInitialFocusX).toDouble()
				.pow(2.0) + (this.focusY - mInitialFocusY).toDouble().pow(2.0) > mTouchSlopSquare)
		) {
			mCurrentSpan = span
			mPreviousSpan = mCurrentSpan
			mPrevTime = this.eventTime

			this.focusX = focusX
			mPreviousFocusX = this.focusX
			this.focusY = focusY
			mPreviousFocusY = this.focusY

			mCurrentRotation = rotation
			mPreviousRotation = mCurrentRotation

			this.isInProgress = mListener.onBegin(this)
		}

		// Handle motion; focal point and span/scale factor are changing.
		if (action == MotionEvent.ACTION_MOVE) {
			mCurrentSpan = span

			this.focusX = focusX
			this.focusY = focusY

			mCurrentRotation = rotation

			if (this.isInProgress) {
				if (this.scale != NO_SCALE) {
					mListener.onScale(this)
				}

				if (this.rotation != NO_ROTATE) {
					mListener.onRotate(this)
				}

				if (this.moveX != NO_MOVE || this.moveY != NO_MOVE) {
					mListener.onMove(this)
				}
			}

			mPreviousSpan = mCurrentSpan

			mPreviousFocusX = this.focusX
			mPreviousFocusY = this.focusY

			mPreviousRotation = mCurrentRotation

			mPrevTime = this.eventTime
		}

		return true
	}

	val moveX: Float
		/**
		 * Return the X coordinate distance from the previous focus event to the current
		 * event. This value is defined as
		 * ([.mCurrentFocusX] - [.mPreviousFocusX]).
		 *
		 * @return X coordinate Distance between focal points in pixels.
		 */
		get() = this.focusX - mPreviousFocusX

	val moveY: Float
		/**
		 * Return the Y coordinate distance from the previous focus event to the current
		 * event. This value is defined as
		 * ([.mCurrentFocusY] - [.mPreviousFocusY]).
		 *
		 * @return Y coordinate Distance between focal points in pixels.
		 */
		get() = this.focusY - mPreviousFocusY

	val rotation: Float
		/**
		 * Return the average rotate between each of the pointers forming the
		 * gesture in progress through the focal point.
		 *
		 * ([.mCurrentRotation] - [.mPreviousRotation]).
		 *
		 * @return rotate between pointers in degrees.
		 */
		get() = mCurrentRotation - mPreviousRotation

	val scale: Float
		/**
		 * Return the scaling factor from the previous scale event to the current
		 * event. This value is defined as
		 * ([.mCurrentSpan] / [.mPreviousSpan]).
		 *
		 * @return The current scaling factor.
		 */
		get() = if (mPreviousSpan > 0) mCurrentSpan / mPreviousSpan else 1f

	val timeDelta: Long
		/**
		 * Return the time difference in milliseconds between the previous
		 * accepted scaling event and the current scaling event.
		 *
		 * @return Time difference since the last scaling event in milliseconds.
		 */
		get() = this.eventTime - mPrevTime

	/**
	 * The listener for receiving notifications when gestures occur.
	 * If you want to listen for all the different gestures then implement
	 * this interface. If you only want to listen for a subset it might
	 * be easier to extend [SimpleOnMultiTouchGestureListener].
	 *
	 * An application will receive events in the following order:
	 *
	 *  * One [OnMultiTouchGestureListener.onBegin]
	 *  * Zero or more [OnMultiTouchGestureListener.onScale]
	 *  * One [OnMultiTouchGestureListener.onEnd]
	 *
	 */
	interface OnMultiTouchGestureListener {
		/**
		 * Responds to scaling events for a gesture in progress.
		 * Reported by pointer motion.
		 *
		 * @param detector The detector reporting the event - use this to
		 * retrieve extended info about event state.
		 * @return Whether or not the detector should consider this event
		 * as handled. If an event was not handled, the detector
		 * will continue to accumulate movement until an event is
		 * handled. This can be useful if an application, for example,
		 * only wants to update scaling factors if the change is
		 * greater than 0.01.
		 */
		fun onScale(detector: MultiTouchGestureDetector?)


		/**
		 * Responds to moving events for a gesture in progress.
		 * Reported by pointer motion.
		 *
		 * @param detector The detector reporting the event - use this to
		 * retrieve extended info about event state.
		 * @return Whether or not the detector should consider this event
		 * as handled. If an event was not handled, the detector
		 * will continue to accumulate movement until an event is
		 * handled. This can be useful if an application, for example,
		 * only wants to update scaling factors if the change is
		 * greater than 0.01.
		 */
		fun onMove(detector: MultiTouchGestureDetector?)

		/**
		 * Responds to rotating events for a gesture in progress.
		 * Reported by pointer motion.
		 *
		 * @param detector The detector reporting the event - use this to
		 * retrieve extended info about event state.
		 * @return Whether or not the detector should consider this event
		 * as handled. If an event was not handled, the detector
		 * will continue to accumulate movement until an event is
		 * handled. This can be useful if an application, for example,
		 * only wants to update scaling factors if the change is
		 * greater than 0.01.
		 */
		fun onRotate(detector: MultiTouchGestureDetector?)

		/**
		 * Responds to the beginning of a touch gesture. Reported by
		 * new pointers going down.
		 *
		 * @param detector The detector reporting the event - use this to
		 * retrieve extended info about event state.
		 * @return Whether or not the detector should continue recognizing
		 * this gesture. For example, if a gesture is beginning
		 * with a focal point outside of a region where it makes
		 * sense, onBegin() may return false to ignore the
		 * rest of the gesture.
		 */
		fun onBegin(detector: MultiTouchGestureDetector?): Boolean

		/**
		 * Responds to the end of a scale gesture. Reported by existing
		 * pointers going up.
		 *
		 * Once a touch has ended, [MultiTouchGestureDetector.getFocusX]
		 * and [MultiTouchGestureDetector.getFocusY] will return focal point
		 * of the pointers remaining on the screen.
		 *
		 * @param detector The detector reporting the event - use this to
		 * retrieve extended info about event state.
		 */
		fun onEnd(detector: MultiTouchGestureDetector?)
	}

	open class SimpleOnMultiTouchGestureListener : OnMultiTouchGestureListener {
		override fun onScale(detector: MultiTouchGestureDetector?) {}

		override fun onMove(detector: MultiTouchGestureDetector?) {}

		override fun onRotate(detector: MultiTouchGestureDetector?) {}

		override fun onBegin(detector: MultiTouchGestureDetector?): Boolean {
			return true
		}

		override fun onEnd(detector: MultiTouchGestureDetector?) {
			// Intentionally empty
		}
	}

	companion object {
		const val TAG: String = "MultiTouchGestureDetector"

		const val MAX_ROTATION: Int = 360

		const val NO_SCALE: Float = 1.0f
		const val NO_ROTATE: Float = 0.0f
		const val NO_MOVE: Float = 0.0f
	}
}
