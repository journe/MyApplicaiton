package com.jour.demo.ui

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.google.android.material.chip.Chip
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.common.ui.BaseFragment
import com.jour.demo.databinding.FragmentAssetsBinding
import com.jour.demo.databinding.FragmentFirstBinding
import com.jour.demo.xpop.CenterPopup
import com.jour.demo.xpop.JsonDtResultPopup
import com.jour.demo.xpop.JsonResultPopup
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

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
                    val json =
                        AssetsUtils.parseDtFile(requireActivity(), "SYN/DAT/$fileName")
                    XPopup.Builder(context)
                        .hasShadowBg(true)
                        .asCustom(JsonDtResultPopup(context, json))
                        .show()
                }
            })
        }
        mBinding.resultTv.text = result
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

                // 3. 每 2 个字节解析为一个大端序整数，循环 1024 次
                for (i in 0 until INTEGER_COUNT) {
                    val index = i * 2 // 每个整数对应字节数组的起始索引
                    if (index + 1 >= byteArray.size) break // 防止数组越界

                    // 高位字节（前一个字节）和低位字节（后一个字节）
                    val highByte = byteArray[index]
                    val lowByte = byteArray[index + 1]

                    // 解析大端序整数：高位左移8位，与低位按位或
                    val intValue = (highByte.toInt() and 0xFF) shl 8 or (lowByte.toInt() and 0xFF)
                    intList.add(intValue)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("解析 $assetsFilePath 失败：${e.message}")
        }
        return intList
    }
}