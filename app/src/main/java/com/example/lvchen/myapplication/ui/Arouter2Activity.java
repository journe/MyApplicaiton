package com.example.lvchen.myapplication.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lvchen.myapplication.R;
import com.example.lvchen.myapplication.utils.BaseAsyncTask;
import com.example.lvchen.myapplication.utils.JsoupAsyncTask;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Route(path = "/test/activity2")
public class Arouter2Activity extends AppCompatActivity {

  @Autowired(name = "comment")
  String commentid;

  @Autowired(name = "audit")
  int aditid;

  @SuppressLint("StaticFieldLeak") @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    setContentView(R.layout.activity_arouter);
    ((TextView) findViewById(R.id.arouter_tv1)).setText(commentid);
    ((TextView) findViewById(R.id.arouter_tv2)).setText(aditid + "");
    final String a =
        "https://dev02.share1diantong.com/common/activity/share-money/rule";

    new JsoupAsyncTask() {
      @Override protected Document doInBackground(Void... voids) {
        Document doc = null;
        try {
          doc = Jsoup.connect(a).get();
          Logger.d(doc.head()
                  //.selectFirst("#routename")
              //.attr("content")
          );
        } catch (IOException e) {
          e.printStackTrace();
        }
        return doc;
      }
    }.execute();

    //try {
    //  URL obj = new URL(a);
    //  URLConnection conn = obj.openConnection();
    //  Map<String, List<String>> map = conn.getHeaderFields();
    //
    //  System.out.println("显示响应Header信息...\n");
    //
    //  for (Map.Entry<String, List<String>> entry : map.entrySet()) {
    //    System.out.println("Key : " + entry.getKey() +
    //        " ,Value : " + entry.getValue());
    //  }
    //
    //  System.out.println("\n使用key获得响应Header信息 \n");
    //  List<String> server = map.get("Server");
    //
    //  if (server == null) {
    //    System.out.println("Key 'Server' is not found!");
    //  } else {
    //    for (String values : server) {
    //      System.out.println(values);
    //    }
    //  }
    //} catch (Exception e) {
    //  e.printStackTrace();
    //}
  }
}
