package cn.gavinliu.similar.photo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cn.gavinliu.similar.photo.entry.Photo
import cn.gavinliu.similar.photo.util.PermissionsUtils
import cn.gavinliu.similar.photo.util.PhotoRepository
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import coil.load
import tech.jour.similar.R
import tech.jour.similar.databinding.ItemGridImageBinding


class MainActivity : AppCompatActivity(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .components {
                add(VideoFrameDecoder.Factory())
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_similar)
        PermissionsUtils.getPermissions(this)
        val photos: List<Photo> = PhotoRepository.getPhoto(this)
        val gridView: RecyclerView = findViewById<RecyclerView>(R.id.grid)
        gridView.adapter = PhotoAdapter(photos)
    }

    fun find(view: View?) {
        val intent = Intent(this@MainActivity, GroupActivity::class.java)
        startActivity(intent)
    }


    inner class PhotoAdapter(private val data: List<Photo>) :
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemGridImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ItemGridImageBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: Photo) {
//            binding.storageType.text = testDataBean.type
                binding.image.load(bean.path)
            }
        }
    }
}