package com.jour.demo.ui

import android.content.Context
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.jour.demo.base.ktx.d
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseFragment
import com.jour.demo.databinding.FragmentAssetsBinding
import com.jour.demo.xpop.JsonDtResultPopup
import com.jour.demo.xpop.JsonResultPopup
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import kotlin.math.roundToInt

@AndroidEntryPoint
class AssetsFragment : BaseFragment<FragmentAssetsBinding, EmptyViewModel>() {
    override val mViewModel: EmptyViewModel by viewModels()

    override fun FragmentAssetsBinding.initView() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        var result = ""
//        result = AssetsUtils.readAssetsFileAndXor(requireActivity(), "SYN/S001.jn")

        val rootFiles = requireActivity().assets.list("SYN")
        rootFiles?.forEach { fileName ->
            mBinding.chipGroup.addView(Chip(requireContext()).apply {
                text = fileName
                setOnClickListener {
                    val json =
                        AssetsUtils.readAssetsFileAndXor(requireActivity(), "SYN/$fileName")
                    XPopup.Builder(context)
                        .hasShadowBg(true)
                        .asCustom(JsonResultPopup(context, json))
                        .show()
                }
            })
        }

        val dtFiles = requireActivity().assets.list("SYN/DAT")
        dtFiles?.forEach { fileName ->
            mBinding.chipGroup.addView(Chip(requireContext()).apply {
                text = fileName
                setOnClickListener {
                    val waveformData =
                        AssetsUtils.parseDtFile(requireActivity(), "SYN/DAT/$fileName")
                    waveformData.d()
                    val resultData = smoothWithMovingAverage(waveformData)
                    XPopup.Builder(context)
                        .hasShadowBg(true)
                        .asCustom(JsonDtResultPopup(context, waveformData))
                        .show()
                }
            })
        }
        mBinding.resultTv.text = result
    }

    private fun smoothWithMovingAverage(correctedData: List<Int>): MutableList<Int> {
        // 目标显示范围（0~4095）
        val targetMin = 0
        val targetMax = 1023

        // 步骤2：数值归一化（映射到0~4095）
        val minVal = correctedData.minOrNull() ?: 0
        val maxVal = correctedData.maxOrNull() ?: 0
        val normalizedData = if (maxVal == minVal) {
            correctedData.map { 0 } // 避免除零
        } else {
            correctedData.map { value ->
                ((value - minVal).toFloat() * (targetMax - targetMin) / (maxVal - minVal) + targetMin).roundToInt()
            }
        }
        // 步骤3：3点滑动平均滤波
        val smoothed = mutableListOf<Int>()
        for (i in normalizedData.indices) {
            when (i) {
                0 -> smoothed.add(normalizedData[i]) // 首点用原数据
                normalizedData.size - 1 -> smoothed.add(normalizedData[i]) // 尾点用原数据
                else -> {
                    val avg =
                        (normalizedData[i - 1] + normalizedData[i] + normalizedData[i + 1]) / 3
                    smoothed.add(avg)
                }
            }
        }
        return smoothed
    }
}


/**
 * Assets 工具类：读取文件并处理字节
 */
object AssetsUtils {
    /**
     * 读取 Assets 中文件的字节，与 0xFF 异或后转换为字符串
     * @param context 上下文（Activity/Fragment/Application）
     * @param assetsFilePath Assets 中的文件路径（如 "test.txt"、"data/secret.dat"）
     * @param charset 字符编码（默认 UTF-8，可根据文件实际编码修改为 GBK 等）
     * @return 处理后的字符串，失败返回空字符串
     */
    fun readAssetsFileAndXor(
        context: Context, assetsFilePath: String, charset: String = "UTF-8"
    ): String {
        return try {
            // 1. 读取 Assets 文件的所有字节
            val inputStream: InputStream = context.assets.open(assetsFilePath)
            val originalBytes = inputStream.readBytes() // 读取所有字节到数组
            inputStream.close() // 关闭流

            // 2. 对每个字节与 0xFF 按位异或
            val xorBytes = originalBytes.map { byte ->
                (byte.toInt() xor 0xFF).toByte() // 先转 Int 避免位运算溢出，再转回 Byte
            }.toByteArray()

            // 3. 异或后的字节数组转换为字符串
            String(xorBytes, charset(charset))
        } catch (e: Exception) {
            e.printStackTrace()
            "" // 异常时返回空字符串
        }
    }

    private const val TOTAL_BYTES = 1024 * 2 // 需读取的总字节数
    private const val INTEGER_COUNT = 1024 // 最终解析的整数个数

    /**
     * 从 Assets 读取 D001.dt 并解析为 1024 个整数（双字节大端序）
     * @param context 上下文
     * @return 解析后的整数列表，失败返回空列表
     */
    fun parseDtFile(context: Context, assetsFilePath: String): List<Int> {
        val intList = mutableListOf<Int>()
        try {
            // 1. 打开 Assets 文件输入流
            context.assets.open(assetsFilePath).use { inputStream ->
                // 2. 读取指定长度的字节（最多 TOTAL_BYTES 个）
                val byteArray = ByteArray(TOTAL_BYTES)
                val actualRead = inputStream.read(byteArray)

                // 容错：若读取的字节数不足 2048，直接返回空列表
                if (actualRead != TOTAL_BYTES) {
                    println("文件字节数不足，实际读取：$actualRead 字节，需要：$TOTAL_BYTES 字节")
                    return emptyList()
                }

                intList.addAll(LittleEndianConverter.byteArrayToIntList(byteArray))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("解析 $assetsFilePath 失败：${e.message}")
        }
        return intList
    }
}

object LittleEndianConverter {

    /**
     * 场景1：小端序组合两个字节为16位整数（核心逻辑）
     * @param highByte 高位字节（如0x8E）
     * @param lowByte 低位字节（如0xFD）
     * @param xorFlag 是否先对每个字节执行 ^0xFF 操作（true=执行，false=不执行）
     * @return 组合后的16位整数
     */
    fun bytesToInt16(highByte: Byte, lowByte: Byte, xorFlag: Boolean = false): Int {
        // 步骤1：将Byte转为无符号Int（避免负数干扰）
        var hb = highByte.toInt() and 0xFF // 0x8E → 142
        var lb = lowByte.toInt() and 0xFF  // 0xFD → 253

        // 步骤2：可选：每个字节^0xFF（你之前要求的预处理）
        if (xorFlag) {
            hb = hb xor 0xFF // 0x8E ^ 0xFF → 0x71 (113)
            lb = lb xor 0xFF // 0xFD ^ 0xFF → 0x02 (2)
        }

        // 步骤3：小端序组合（低位左移8位 + 高位）
        return (lb shl 8) + hb
    }

    /**
     * 场景2：16位整数拆分为小端序的两个字节
     * @param value 16位整数（如64910）
     * @param xorFlag 是否对拆分后的每个字节执行 ^0xFF 操作
     * @return Pair(高位字节, 低位字节)
     */
    fun int16ToBytes(value: Int, xorFlag: Boolean = false): Pair<Byte, Byte> {
        // 步骤1：确保值在16位范围内
        val validValue = value and 0xFFFF

        // 步骤2：小端序拆分（先取低位字节，再取高位字节）
        var lowByte = (validValue shr 8) and 0xFF // 低位字节（小端序：高8位是原数的低位）
        var highByte = validValue and 0xFF        // 高位字节（小端序：低8位是原数的高位）

        // 步骤3：可选：每个字节^0xFF
        if (xorFlag) {
            lowByte = lowByte xor 0xFF
            highByte = highByte xor 0xFF
        }

        // 转换为Byte并返回（高位、低位）
        return Pair(highByte.toByte(), lowByte.toByte())
    }

    /**
     * 场景3：批量处理字节数组为小端序整数列表（适配1024个采样点场景）
     * @param byteArray 原始字节数组（长度需为偶数，每两个字节对应一个16位整数）
     * @param xorFlag 是否预处理^0xFF
     * @return 小端序组合后的整数列表
     */
    fun byteArrayToIntList(byteArray: ByteArray, xorFlag: Boolean = true): List<Int> {
        require(byteArray.size % 2 == 0) { "字节数组长度必须为偶数！" }
        val result = mutableListOf<Int>()
        for (i in byteArray.indices step 2) {
            // 小端序：数组中先读低位字节，后读高位字节
            val highByte = byteArray[i]
            val lowByte = byteArray[i + 1]
            val intValue = bytesToInt16(highByte, lowByte, xorFlag)
            result.add(intValue)
        }
        return result
    }
}

