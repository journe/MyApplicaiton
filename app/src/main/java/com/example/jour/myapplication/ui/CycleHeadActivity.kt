package com.example.jour.myapplication.ui

import android.os.Bundle
import com.example.jour.myapplication.R
import com.example.jour.myapplication.base.BaseActivity

class CycleHeadActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cycle_head)
    val image = arrayOf(
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498189897260&di=2d1a7ea92b9ab52263a842905f84550e&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201507%2F28%2F20150728145551_N5XTe.thumb.700_0.jpeg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498189936006&di=eee9217f234c05cb29b29303ba16855a&imgtype=0&src=http%3A%2F%2Fuserimage8.360doc.com%2F16%2F1209%2F08%2F38724159_201612090843090107526618.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498189957932&di=e305af413dab18dfe6cd0960ba2b5c5b&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fblog%2F201501%2F23%2F20150123152324_FM3nH.png",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498784582&di=fd1663334c56ed2db6e0900372479777&imgtype=jpg&er=1&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fblog%2F201312%2F28%2F20131228110530_8cLY4.thumb.700_0.jpeg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498784606&di=9b18c4e0dca59e5e2ba8be33299858b6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg05.tooopen.com%2Fimages%2F20140723%2Fsy_67151969477.jpg",
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=997636855,2670044197&fm=26&gp=0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498190108728&di=85a61ed2577f67ec8a28a84446bf88cd&imgtype=0&src=http%3A%2F%2Fwww.lieqi.me%2Fpics%2F20160113%2F2-1601131R442.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498190173459&di=95950aa66e020e934bb7d2a825c44bf1&imgtype=0&src=http%3A%2F%2Fi2.17173.itc.cn%2F2011%2Fnews%2F2011%2F07%2F14%2Fy0714db02s.jpg",
        "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1498179673&di=2cebe2c7288b185bd3db7c940afd7e4a&src=http://img.tupianzj.com/uploads/allimg/160821/9-160R1150K2.jpg"
    )

//    for (i in image.indices) {
//      val inflate: View = LayoutInflater.from(this)
//          .inflate(layout.item_cycle_head, customGroup, false)
//      val imageView =
//        inflate.findViewById<View>(id.image) as ImageView
//      Glide.with(this)
//          .load(image[i])
//          .into(imageView)
//      customGroup.addView(inflate)
//    }
  }
}