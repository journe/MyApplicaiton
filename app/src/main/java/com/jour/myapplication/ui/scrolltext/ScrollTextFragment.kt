package com.jour.myapplication.ui.scrolltext

import android.view.View
import androidx.fragment.app.viewModels
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.FragmentTestTextViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScrollTextFragment : BaseFragment<FragmentTestTextViewBinding, EmptyViewModel>() {
	override val mViewModel: EmptyViewModel by viewModels()


	private var textSize = 20
	private var speed = 45
	private var fancyPanel: FancyPanel? = null

	override fun FragmentTestTextViewBinding.initView() {
		container.post {
			fancyPanel = FancyPanel(
				context = requireContext(),
				parentWidth = container.width.toFloat(),
				parentHeight = container.height.toFloat()
			)
			fancyPanel?.addToActivity(requireActivity())
			fancyPanel?.setPanelContent(TEXT)
			fancyPanel?.onResume()
		}

		scrollTextView.setContentText(TEXT)
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

	override fun onResume() {
		fancyPanel?.onResume()
		super.onResume()
	}

	override fun onPause() {
		fancyPanel?.onPause()
		super.onPause()
	}

	fun start(view: View) {
		mBinding.scrollTextView.resume()
	}

	fun pause(view: View) {
		mBinding.scrollTextView.pause()
	}

	fun large(view: View) {
		mBinding.scrollTextView.setContentSize(++textSize)
	}

	fun small(view: View) {
		mBinding.scrollTextView.setContentSize(--textSize)
	}

	fun fast(view: View) {
		mBinding.scrollTextView.setSpeed((++speed).toFloat())
	}

	fun slow(view: View) {
		mBinding.scrollTextView.setSpeed((--speed).toFloat())
	}


	companion object {
		private const val TEXT = "划一根火柴\n" +
				"将慵倦的夜点亮\n" +
				"吐出一缕烟\n" +
				"飘向半掩的窗\n" +
				"你纵身跃入酒杯\n" +
				"梦从此溺亡\n" +
				"心门上一把锁\n" +
				"钥匙在你手上\n" +
				"快将尘埃掸落\n" +
				"别将你眼眸弄脏\n" +
				"或许吧\n" +
				"谈笑中你早已淡忘\n" +
				"而我在颠沛中\n" +
				"已饱经一脸沧桑\n" +
				"思念需要时间\n" +
				"慢慢调养"
	}


}
