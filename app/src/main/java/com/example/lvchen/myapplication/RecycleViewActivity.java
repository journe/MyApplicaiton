package com.example.lvchen.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecycleViewActivity extends AppCompatActivity {

	private RecyclerView mViewRecyclerView;

	private String[] proName = {"名称0", "名称1", "名称2", "名称3", "名称4", "名称5",
			"名称6", "名称7", "名称8", "名称9", "名称10", "名称11", "名称12", "名称13",
			"名称14", "名称15", "名称16", "名称17", "名称18", "名称19"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycle_view);
		mViewRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
		mViewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mViewRecyclerView.setAdapter(new MyRecycleAdapter());

	}

	class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.RecycleViewHolder> {

		@Override
		public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new RecycleViewHolder(LayoutInflater.from(RecycleViewActivity.this)
					.inflate(R.layout.view_pager_grid_view_item, null));
		}

		@Override
		public void onBindViewHolder(RecycleViewHolder holder, int position) {
			holder.textView.setText(position+"");
			holder.imageView.setImageResource(R.drawable.ic_menu_camera);
		}

		@Override
		public int getItemCount() {
			return proName.length;
		}

		class RecycleViewHolder extends RecyclerView.ViewHolder {
			ImageView imageView;
			TextView textView;

			public RecycleViewHolder(View itemView) {
				super(itemView);
				imageView = itemView.findViewById(R.id.grid_item_iv);
				textView = itemView.findViewById(R.id.grid_item_tv);
			}
		}

	}

}
