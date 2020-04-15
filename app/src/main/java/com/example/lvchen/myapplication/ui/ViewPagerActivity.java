package com.example.lvchen.myapplication.ui;

import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lvchen.myapplication.R;
import com.example.lvchen.myapplication.utils.MyGridViewAdpter;
import com.example.lvchen.myapplication.utils.ProdctBean;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
	private ViewPager viewPager;
	private LinearLayout group;//圆点指示器
	private ImageView[] ivPoints;//小圆点图片的集合
	private int totalPage; //总的页数
	private int mPageSize = 8; //每页显示的最大的数量
	private List<ProdctBean> listDatas;//总的数据源
	private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
	//private int currentPage;//当前页

	private String[] proName = {"名称0", "名称1", "名称2", "名称3", "名称4", "名称5",
			"名称6", "名称7", "名称8", "名称9", "名称10", "名称11", "名称12", "名称13",
			"名称14", "名称15", "名称16", "名称17", "名称18", "名称19"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		//初始化控件
		initView();
		//添加业务逻辑
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		group = (LinearLayout) findViewById(R.id.points);
		listDatas = new ArrayList<ProdctBean>();
		for (int i = 0; i < proName.length; i++) {
			listDatas.add(new ProdctBean(proName[i], R.drawable.ic_menu_camera));
		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		//总的页数向上取整
		totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
		viewPagerList = new ArrayList<View>();
		for (int i = 0; i < totalPage; i++) {
			//每个页面都是inflate出一个新实例
			final GridView gridView = (GridView) View.inflate(this, R.layout.view_pager_grid_view, null);
			gridView.setAdapter(new MyGridViewAdpter(this, listDatas, i, mPageSize));
			//添加item点击监听
			gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
				                        int position, long arg3) {
					// TODO Auto-generated method stub
					Object obj = gridView.getItemAtPosition(position);
					if (obj != null && obj instanceof ProdctBean) {
						System.out.println(obj);
						Toast.makeText(ViewPagerActivity.this, ((ProdctBean) obj).getName(), Toast.LENGTH_SHORT).show();
					}
				}
			});
			//每一个GridView作为一个View对象添加到ViewPager集合中
			viewPagerList.add(gridView);
		}
		//设置ViewPager适配器
		viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));

//		//添加小圆点
//		ivPoints = new ImageView[totalPage];
//		for(int i = 0; i < totalPage; i++){
//			//循坏加入点点图片组
//			ivPoints[i] = new ImageView(this);
//			if(i==0){
//				ivPoints[i].setImageResource(R.drawable.page_focuese);
//			}else {
//				ivPoints[i].setImageResource(R.drawable.page_unfocused);
//			}
//			ivPoints[i].setPadding(8, 8, 8, 8);
//			group.addView(ivPoints[i]);
//		}
//		//设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
//		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
//			@Override
//			public void onPageSelected(int position) {
//				// TODO Auto-generated method stub
//				//currentPage = position;
//				for(int i=0 ; i < totalPage; i++){
//					if(i == position){
//						ivPoints[i].setImageResource(R.drawable.page_focuese);
//					}else {
//						ivPoints[i].setImageResource(R.drawable.page_unfocused);
//					}
//				}
//			}
//		});
	}

	/**
	 * ViewPage的适配器
	 *
	 * @author Administrator
	 */
	public class MyViewPagerAdapter extends PagerAdapter {

		private List<View> viewList;//View就二十GridView


		public MyViewPagerAdapter(List<View> viewList) {
			this.viewList = viewList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList != null ? viewList.size() : 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		/**
		 * 将当前的View添加到ViewGroup容器中
		 * 这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPage上
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(viewList.get(position));
			return viewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}
	}
}