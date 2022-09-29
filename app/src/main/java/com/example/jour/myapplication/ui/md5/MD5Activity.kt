package com.example.jour.myapplication.ui.md5

import CoilEngine
import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import coil.load
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.jour.myapplication.databinding.ActivityMd5Binding
import com.example.jour.myapplication.utils.actionWithPermission
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import org.jetbrains.anko.toast
import java.io.*

class MD5Activity : AppCompatActivity() {
    lateinit var binding: ActivityMd5Binding

    private var oldVideoPath = ""
    private var newVideoPath = ""
    private var oldFileName = ""
    private var newFileName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMd5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.selectVideo.setOnClickListener {
            openGallery()
        }
        binding.fileDri.text = "fileDri\n" + filesDir.path
        binding.dataDir.text = "dataDir\n" + dataDir.path
        binding.cacheDir.text = "cacheDir\n" + cacheDir.path
        binding.externalCacheDir.text = "externalCacheDir\n" + externalCacheDir?.path

        binding.showMD5.setOnClickListener {
            binding.fileMD5.text = oldVideoPath
            val f = FileUtils.getFileByPath(oldVideoPath)

            if (f == null) {
                ToastUtils.showLong("FileUtils null")
            }
            binding.fileMD5.text = FileUtils.getFileMD5ToString(oldVideoPath)
        }

        binding.changeMD5.setOnClickListener {
//            FileUtils
            changeFileMD5()
            binding.newMD5.text = FileUtils.getFileMD5ToString(newVideoPath)
//            copyFileToGallery(FileUtils.getFileByPath(newVideoPath), newFileName)

            val suffix = FileUtils.getFileExtension(newVideoPath)
            saveVideo2Public(
                FileUtils.getFileByPath(newVideoPath).inputStream(),
                newFileName, suffix
            )
        }

    }

    fun changeFileMD5() {
        try {
            val bytes = FileUtils.getFileByPath(oldVideoPath).readBytes()
//                contentResolver.openInputStream(oldVideoPath.toUri())
//                    ?.readBytes()
            val fos =
                openFileOutput("new$oldFileName", Context.MODE_PRIVATE)
            fos.write(bytes)
            fos.write(System.currentTimeMillis().toString().toByteArray())
            fos.close()
            newFileName = "new$oldFileName"
            newVideoPath = filesDir.path + "/" + newFileName
        } catch (e: Exception) {
            toast(e.message.toString())
        }
    }

    private fun saveVideo2Public(
        source: InputStream,
        fileName: String,
        suffix: String
    ): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Video.Media.TITLE, fileName.replace(suffix, ""))
            put(MediaStore.Video.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Video.Media.MIME_TYPE, "video/$suffix")
            put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES)
        }
        val contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var result: Uri? = null
        contentResolver?.let { resolver ->
            resolver.insert(
                contentUri,
                values
            )?.let { insertUri ->
                result = insertUri
                resolver.openOutputStream(insertUri)?.use { outPut ->
                    var read: Int = -1
                    val buffer = ByteArray(2014)
                    while (source.read(buffer).also { read = it } != -1) {
                        outPut.write(buffer, 0, read)
                    }
                }
            }
        }
        return result
    }


    private fun openGallery() {
        actionWithPermission(
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.TYPE_VIDEO)
                .setMaxSelectNum(1)
                .setImageEngine(CoilEngine())
                .isDisplayCamera(false)
                .isPreviewVideo(false)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>?) {
                        if (result != null) {
                            val path = result.first().realPath
//                            videoPath = result.first().realPath
//                            videoPath = result.first().availablePath

                            oldFileName = result.first().fileName
                            try {
                                val bytes =
                                    contentResolver.openInputStream(result.first().availablePath.toUri())
                                        ?.readBytes()
                                val fos =
                                    openFileOutput(oldFileName, Context.MODE_PRIVATE)
                                fos.write(bytes)
                                fos.close()
                                oldVideoPath = filesDir.path + "/" + oldFileName

                            } catch (e: Exception) {
                                toast(e.message.toString())
                            }

                            binding.filepath.text = path
                            binding.imageView.load(result.first().availablePath)
                        }
                    }

                    override fun onCancel() {
                    }

                })
        }
    }
}