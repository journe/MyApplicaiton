package com.example.lvchen.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lvchen.myapplication.R;
import com.orhanobut.logger.Logger;

@Route(path = "/test/activity2")
public class Arouter2Activity extends AppCompatActivity {

  @Autowired(name = "comment")
  String commentid;

  @Autowired(name = "audit")
  int aditid;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    setContentView(R.layout.activity_arouter);
    ((TextView)findViewById(R.id.arouter_tv1)).setText(commentid);
    ((TextView)findViewById(R.id.arouter_tv2)).setText(aditid+"");
  }
}
