package com.jour.myapplication


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.FragmentMainBinding
import com.jour.myapplication.databinding.ItemMainRecyclerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, EmptyViewModel>() {
    override val mViewModel: EmptyViewModel by viewModels()

    override fun FragmentMainBinding.initView() {
        val list = listOf(
            ItemBean(R.drawable.img_main_18, "获取当前手机壁纸", R.id.wallPaperActivity),
            ItemBean(R.drawable.img_main_23, "折叠式标题栏", R.id.coordinatorLayoutActivity),
            ItemBean(R.drawable.img_main_1, "类别Viewpager", R.id.FirstFragment),
            ItemBean(R.drawable.img_main_2, "通知样式", R.id.notificationActivity),
            ItemBean(R.drawable.img_main_3, "上拉加载的RecyclerView", R.id.recyclerViewRefreshActivity),
            ItemBean(R.drawable.img_main_4, "分类选择样式", R.id.catalogueActivity),
            ItemBean(R.drawable.img_main_5, "分类选择样式2", R.id.catalogue2Activity),
            ItemBean(R.drawable.img_main_6, "瀑布流和SceneTransition", R.id.waterFallActivity),
            ItemBean(R.drawable.img_main_7, "自定义可拖动view", R.id.cycleHeadActivity),
            ItemBean(R.drawable.img_main_8, "Material控件样式", R.id.chipActivity),
            ItemBean(R.drawable.img_main_10, "手机验证码", R.id.verificationCodeActivity),
            ItemBean(R.drawable.img_main_11, "媒体控制，播放在线视频", R.id.mediaPlayActivity),
            ItemBean(R.drawable.img_main_12, "折叠的TextView", R.id.folderTextViewActivity),
            ItemBean(R.drawable.img_main_13, "滑动悬停布局设置CoordinatorLayout", R.id.coordinatorActivity),
            ItemBean(R.drawable.img_main_14, "无限轮播走马灯", R.id.bannerActivity),
            ItemBean(R.drawable.img_main_15, "收起键盘悬浮按钮", R.id.editTextActivity),
            ItemBean(
                R.drawable.img_main_16,
                "BitmapShader 显示圆角图片和背景圆角ImageView",
                R.id.roundImageActivity
            ),
            ItemBean(R.drawable.img_main_20, "viewPager滑动没问题", R.id.viewPagerActivity),
            ItemBean(R.drawable.img_main_20, "viewPager2有问题", R.id.viewPager2Activity),
            ItemBean(R.drawable.img_main_19, "彩票结果展示和倒计时view", R.id.lotteryActivity),
            ItemBean(R.drawable.img_main_17, "修改视频MD5和绕过权限", R.id.MD5Activity),
            ItemBean(R.drawable.img_main_21, "夜间模式Theme设置", R.id.themeNightActivity),
            ItemBean(R.drawable.img_main_22, "图片处理", R.id.pictureFragment),
            ItemBean(R.drawable.img_main_24, "自定义文本操作", R.id.customSelectionActionFragment),
        )
        mBinding.recycleView.adapter = MainItemAdapter(list.reversed())
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    data class ItemBean(
        @DrawableRes
        val image: Int,
        val title: String,
        @IdRes
        val dest: Int
    )

    inner class MainItemAdapter(private val data: List<ItemBean>) :
        RecyclerView.Adapter<MainItemAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemMainRecyclerBinding.inflate(
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

        inner class ViewHolder(private val binding: ItemMainRecyclerBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: ItemBean) {
                binding.title.text = bean.title
                binding.image.load(bean.image)
                binding.root.setOnClickListener {
                    findNavController().navigate(bean.dest)
                }
            }
        }
    }


}
