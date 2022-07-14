package com.example.jour.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //ARouter.getInstance().inject(this);
  }
}
