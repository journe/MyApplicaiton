package com.jour.myapplication.ui.picture

import android.Manifest
import android.graphics.BitmapFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.blankj.utilcode.util.ImageUtils
import com.jour.myapplication.base.ktx.clickDelay
import com.jour.myapplication.base.ktx.d
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.FragmentPictureBinding
import com.jour.myapplication.utils.actionWithPermission
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.DateUtils
import copyToAlbum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast
import saveToAlbum
import java.io.File

@AndroidEntryPoint
class PictureFragment : BaseFragment<FragmentPictureBinding, EmptyViewModel>() {
    override val mViewModel: EmptyViewModel by viewModels()

    override fun FragmentPictureBinding.initView() {
        zipImg.clickDelay {
            openGallery(::doZipImg, 1)
        }
        mergeImg.clickDelay {
            openGallery(::doMergeImg, 9)
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }


    private fun doZipImg(result: ArrayList<LocalMedia>) {
        val path = result.first().compressPath
        val name = result.first().fileName
        val savedFile = File(path).copyToAlbum(requireContext(), name, relativePath = null)

        mBinding.imgResult.load(savedFile)
        mBinding.imgInfo.text = savedFile.toString()
//        lifecycleScope.launch {
//            Compressor.compress(requireContext(), actualImageFile)
//            FileUtils
//            FileIOUtils.readFile2BytesByStream()
//        }
    }

    private fun doMergeImg(result: ArrayList<LocalMedia>) {
        if (result.size < 2) {
            toast("至少选择两张图片")
            return
        }
        lifecycleScope.launch {
            val bitmaps = result.map {
//                FileIOUtils.readFile2BytesByStream(it.compressPath)
                BitmapFactory.decodeFile(it.compressPath)
            }
            val savedFile = bitmaps.puzzleBitmap().saveToAlbum(
                requireContext(),
                DateUtils.getCreateFileName("PUZZLE_") + ".jpg", relativePath = null
            )
            mBinding.imgResult.load(savedFile)
            mBinding.imgInfo.text = savedFile.toString()
        }



        result.map { it.compressPath }.d()
//        BitmapUtils
    }

    private fun openGallery(function: (result: ArrayList<LocalMedia>) -> Unit, maxCount: Int) {
        actionWithPermission(
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.TYPE_IMAGE)
                .setMaxSelectNum(maxCount)
                .setImageEngine(CoilEngine())
                .isDisplayCamera(false)
                .setCompressEngine(ImageFileCompressEngine())
                .isPreviewVideo(false)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>?) {
                        if (result != null) {
                            function(result)
//                            val path = result.first().realPath
//                            videoPath = result.first().realPath
//                            videoPath = result.first().availablePath
//                            oldFileName = result.first().fileName
//                            try {
//                                val bytes =
//                                    contentResolver.openInputStream(result.first().availablePath.toUri())
//                                        ?.readBytes()
//                                val fos =
//                                    openFileOutput(oldFileName, Context.MODE_PRIVATE)
//                                fos.write(bytes)
//                                fos.close()
//                                oldVideoPath = filesDir.path + "/" + oldFileName
//
//                            } catch (e: Exception) {
//                                toast(e.message.toString())
//                            }
//
//                            binding.filepath.text = path
//                            binding.imageView.load(result.first().availablePath)
                        }
                    }

                    override fun onCancel() {
                    }

                })
        }
    }


}
