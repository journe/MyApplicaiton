package com.example.jour.myapplication.utils;

import android.os.AsyncTask;
import org.jsoup.nodes.Document;

public abstract class BaseAsyncTask<Params, Progress, Result>
    extends AsyncTask<Params, Progress, Document> {

}
