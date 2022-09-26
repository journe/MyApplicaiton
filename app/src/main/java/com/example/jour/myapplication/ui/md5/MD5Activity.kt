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

    fun saveVideo2Public(
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

    //把照片和视频复制一份到相册
    fun copyFileToGallery(file: File, fileName: String) {

        var uri: Uri = MediaStore.Files.getContentUri("external")

        // 将要新建的文件的文件索引插入到 external.db 数据库中
        // 需要插入到 external.db 数据库 files 表中, 这里就需要设置一些描述信息
        var contentValues = ContentValues()

        // 设置插入 external.db 数据库中的 files 数据表的各个字段的值

        // 设置存储路径 , files 数据表中的对应 relative_path 字段在 MediaStore 中以常量形式定义
        contentValues.put(
            MediaStore.Downloads.RELATIVE_PATH,
            "${Environment.DIRECTORY_DOWNLOADS}/jour"
        )
        // 设置文件名称
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, newFileName)

        var insert: Uri = contentResolver.insert(uri, contentValues)!!

        // 向 Download/hello/hello.txt 文件中插入数据
        var os: OutputStream = contentResolver.openOutputStream(insert)!!
        var bos = BufferedOutputStream(os)
        bos.write("Hello World".toByteArray())
        bos.close()


//        val resolver: ContentResolver = contentResolver
//        val values = ContentValues()
//
//        val uriSavedVideo: Uri? = if (Build.VERSION.SDK_INT >= 29) {
//            values.put(MediaStore.Files.FileColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
//            values.put(MediaStore.Files.FileColumns.DATE_TAKEN, System.currentTimeMillis())
//            values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, "Pictures/jour")
//            values.put(MediaStore.Files.FileColumns.IS_PENDING, true)
//            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
//            if (fileName.contains("JPG")) {
//                values.put(MediaStore.Files.FileColumns.MIME_TYPE, "image")
//                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            } else {
//                values.put(MediaStore.Files.FileColumns.MIME_TYPE, "video")
//                resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
//            }
//
//        } else {
//            values.put(MediaStore.Files.FileColumns.TITLE, fileName)
//            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
//            values.put(MediaStore.Files.FileColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
//            if (fileName.contains("JPG")) {
//                values.put(MediaStore.Files.FileColumns.MIME_TYPE, "image")
//                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            } else {
//                values.put(MediaStore.Files.FileColumns.MIME_TYPE, "video")
//                resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
//            }
//
//        }
//        uriSavedVideo?.apply {
//            runCatching {
//                val pfd = resolver.openFileDescriptor(this, "w") as ParcelFileDescriptor
//                val out = FileOutputStream(pfd.fileDescriptor)
//                val `in` = FileInputStream(file)
//                val buf = ByteArray(8192)
//                var len: Int
//                while (`in`.read(buf).also { len = it } > 0) {
//                    out.write(buf, 0, len)
//                }
//                out.close()
//                `in`.close()
//                pfd.close()
//            }
//            if (Build.VERSION.SDK_INT >= 29) {
//                values.clear()
//                values.put(MediaStore.Files.FileColumns.IS_PENDING, 0)
//                resolver.update(this, values, null, null)
//            }
//        }
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