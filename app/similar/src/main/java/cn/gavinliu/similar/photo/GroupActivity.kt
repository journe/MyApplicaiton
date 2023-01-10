package cn.gavinliu.similar.photo

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cn.gavinliu.similar.photo.entry.Group
import cn.gavinliu.similar.photo.entry.Photo
import cn.gavinliu.similar.photo.util.PermissionsUtils
import cn.gavinliu.similar.photo.util.PhotoRepository
import coil.load
import tech.jour.similar.R
import tech.jour.similar.databinding.ItemListGroupBinding

/**
 * Created by gavin on 2017/3/27.
 */
class GroupActivity : AppCompatActivity() {
    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        PermissionsUtils.getPermissions(this)
        val photos: List<Photo> = PhotoRepository.getPhoto(this)
//        val listView = findViewById<View>(R.id.list) as ListView
        val listView = findViewById<RecyclerView>(R.id.list)
        mHandler = Handler(mainLooper)
        Thread {
            val groups = SimilarPhoto.find(this@GroupActivity, photos)
            mHandler!!.post { listView.adapter = GroupAdapter(groups) }
        }.start()
    }


    class GroupAdapter(private val data: List<Group>) :
        RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemListGroupBinding.inflate(
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
            holder.bind(data[position], position)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ItemListGroupBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: Group, position: Int) {
                val linearLayout: LinearLayout = binding.images
                binding.images.removeAllViews()
                binding.name.text = "Group: $position"
                val photos = bean.photos
                for (p in photos) {
                    val image = ImageView(linearLayout.context)
                    val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(300, 300)
                    linearLayout.addView(image, param)
                    image.load(p.path)
                }
            }
        }
    }

}