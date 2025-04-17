package com.jour.demo.utils

import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import kotlin.also
import kotlin.let
import kotlin.math.abs
import kotlin.math.sqrt

class TouchController {
	private val MIN_THRESHOLD = 2

	var touchListener: TouchMapListener? = null

	val zoomCenterPoint = doubleArrayOf(0.0, 0.0)
	val translateStartPoint = doubleArrayOf(0.0, 0.0)

	var lastDistance = 0.0

	//通过官方文档描述，需要记录下当前的默认手指Id
	private var mActivePointerId = INVALID_POINTER_ID
	fun onTouch(event: MotionEvent): Boolean {
		when (event.actionMasked) {
			MotionEvent.ACTION_DOWN -> {
				//第一个手指按下，把这个Id记录下来，并且把其坐标作为起始点击坐标
				event.actionIndex.also { pointerIndex ->
					translateStartPoint[0] = event.getX(pointerIndex).toDouble()
					translateStartPoint[1] = event.getY(pointerIndex).toDouble()
				}

				mActivePointerId = event.getPointerId(0)
			}

			MotionEvent.ACTION_POINTER_DOWN -> {
				//第二个以上手指按下，计算两个手指之间的中心位置，因为我们要实现缩放的同时支持平移，
				//所以，一个手指按下时，平移的基点就是这个手指的坐标，两个手指按下时，我们就以两个手指的中心
				//点坐标作为基点，同时也以这个基点作为缩放的基准点
				calculateCenter(event).also {
					zoomCenterPoint[0] = it[0]
					zoomCenterPoint[1] = it[1]
					translateStartPoint[0] = it[0]
					translateStartPoint[1] = it[1]
				}
				//计下按下时两个手指的直线距离，为双指缩放准备
				lastDistance = spacing(event)
			}

			MotionEvent.ACTION_POINTER_UP -> {
				//多点触摸的时候，当有一个手指抬起时，会触发这个Action，我们需要判断，抬起的手指是不是
				//当前记录的第一个手指的Id，如果是，说明默认的手指Id已经发生变化，变成另一个了，所以
				//我们的平移基准点坐标也要改变成变成另一个，否则会出现松开第一种手指时，没有进行拖拽却计算到很大的平移距离的问题，这里也是参考官方文档解释后发现的问题
				event.actionIndex.also { pointerIndex ->
					val pointerId = event.getPointerId(pointerIndex)
					if (pointerId == mActivePointerId) {
						val newPointerIndex = if (pointerIndex == 0) 1 else 0
						translateStartPoint[0] = event.getX(newPointerIndex).toDouble()
						translateStartPoint[1] = event.getY(newPointerIndex).toDouble()
						//重新设置默认手指的id 保证取的坐标没问题
						mActivePointerId = event.getPointerId(newPointerIndex)
					} else {
						//松开的不是第一个按下的手指，直接把基准点重置成第一个手指的坐标
						translateStartPoint[0] =
							event.getX(event.findPointerIndex(mActivePointerId)).toDouble()
						translateStartPoint[1] =
							event.getY(event.findPointerIndex(mActivePointerId)).toDouble()
					}
				}
			}

			MotionEvent.ACTION_MOVE -> {
				val translateEndPoint = doubleArrayOf(0.0, 0.0)
				//拖放结束点先用默认手指的当前位置
				event.findPointerIndex(mActivePointerId).let { pointerIndex ->
					translateEndPoint[0] = event.getX(pointerIndex).toDouble()
					translateEndPoint[1] = event.getY(pointerIndex).toDouble()
				}
				//如果多点触控，我们要计算两个手指的中心点作为终点
				if (event.pointerCount >= 2) {
					calculateCenter(event).also {
						zoomCenterPoint[0] = it[0]
						zoomCenterPoint[1] = it[1]

						translateEndPoint[0] = it[0]
						translateEndPoint[1] = it[1]
					}

					val currentDistance = spacing(event)
					val distanceDiff = currentDistance - lastDistance
					if (abs(distanceDiff) > MIN_THRESHOLD) {
						//把事件传给地图去控制比例尺缩放
//						if (distanceDiff < 0) {
//							touchListener?.onZoomIn(zoomCenterPoint[0], zoomCenterPoint[1])
//						} else {
//							touchListener?.onZoomOut(zoomCenterPoint[0], zoomCenterPoint[1])
//						}

						val scale = currentDistance / lastDistance;
						touchListener?.scale(scale)
						lastDistance = currentDistance
					}
				}
				//计算x、y方向的平移量
				val xDiff = translateEndPoint[0] - translateStartPoint[0]
				val yDiff = translateEndPoint[1] - translateStartPoint[1]

				if (abs(xDiff) > MIN_THRESHOLD || abs(yDiff) > MIN_THRESHOLD) {
					//把事件传给地图去控制平移
					touchListener?.onDrag(xDiff, yDiff)
				}
				translateStartPoint[0] = translateEndPoint[0]
				translateStartPoint[1] = translateEndPoint[1]
			}

			MotionEvent.ACTION_CANCEL,
			MotionEvent.ACTION_UP -> {
				mActivePointerId = INVALID_POINTER_ID
			}
		}
		return true
	}

	/**
	 * 计算连个手指连线中心点
	 */
	private fun calculateCenter(event: MotionEvent): DoubleArray {
		//计算起点中心坐标
		val x0 = event.getX(0)
		val y0 = event.getY(0)

		val x1 = event.getX(1)
		val y1 = event.getY(1)

		return doubleArrayOf(
			x0 + (x1 - x0) / 2.0,
			y0 + (y1 - y0) / 2.0
		)
	}

	/**
	 * 计算两个点的距离
	 *
	 * @param event
	 * @return
	 */
	private fun spacing(event: MotionEvent): Double {
		return if (event.pointerCount == 2) {
			val x = event.getX(0) - event.getX(1)
			val y = event.getY(0) - event.getY(1)
			sqrt((x * x + y * y).toDouble())
		} else 0.0
	}


	interface TouchMapListener {
		fun onDrag(xDiff: Double, yDiff: Double)

		//		fun onZoomIn(centerX: Double, centerY: Double)
//		fun onZoomOut(centerX: Double, centerY: Double)
		fun scale(scale: Double)
	}
}