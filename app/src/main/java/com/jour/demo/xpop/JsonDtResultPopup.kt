package com.jour.demo.xpop

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jour.demo.R
import com.jour.demo.databinding.PopupJsonDtResultBinding
import com.lxj.xpopup.core.CenterPopupView

class JsonDtResultPopup(context: Context, private val waveformData: List<Int> = emptyList()) :
    CenterPopupView(context) {
    lateinit var mBinding: PopupJsonDtResultBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_json_dt_result
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupJsonDtResultBinding.bind(popupImplView)
//        mBinding.waveformView.setWaveformData(waveformData)
//        mBinding.fixedWaveformView.setWaveformData(waveformData)


        // 2. 将整数转换为Chart的Entry（X轴为索引，Y轴为数值）
        val entries = mutableListOf<Entry>()
        waveformData.forEachIndexed { index, value ->
            entries.add(Entry(index.toFloat(), value.toFloat()))
        }

        // 3. 配置折线数据集
        val dataSet = LineDataSet(entries, "波形数据").apply {
            color = Color.parseColor("#FF4081") // 折线颜色
            lineWidth = 2f // 线宽
            setDrawValues(false) // 不显示数值标签
            setDrawCircles(false)
        }

        // 4. 配置图表数据
        val lineData = LineData(dataSet)
        mBinding.lineChart.apply {
            data = lineData
            description = Description().apply { text = "1024点波形图" } // 图表描述
            xAxis.isEnabled = false // 隐藏X轴（可选）
            axisRight.isEnabled = false // 隐藏右侧Y轴
            setTouchEnabled(false) // 禁用触摸交互（可选）
            invalidate() // 刷新图表
        }

    }

}