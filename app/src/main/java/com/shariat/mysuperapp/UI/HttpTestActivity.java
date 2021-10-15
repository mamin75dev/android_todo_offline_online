package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.Utils.HttpRequestData;
import com.shariat.mysuperapp.Utils.HttpRequestHandler;

import java.util.ArrayList;
import java.util.List;

public class HttpTestActivity extends AppCompatActivity {

  private ProgressBar progressBar;
  private TextView textView;
  private List<HttpTask> tasks = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_http_test);
    progressBar = findViewById(R.id.progress_bar);
//    progressBar.setVisibility(View.GONE);
    textView = findViewById(R.id.result_tv);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add("GET").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
//        HttpRequestData requestData = new HttpRequestData(OnlineActivity.GET_URL, "GET");
        HttpRequestData requestData = new HttpRequestData(OnlineActivity.POST_URL, "POST");
        requestData.addParameterToRequest("name", "amin");
        requestData.addParameterToRequest("age", "25");
        new HttpTask().execute(requestData);
        return false;
      }
    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    return super.onCreateOptionsMenu(menu);

  }

  public class HttpTask extends AsyncTask<HttpRequestData, Void, String> {
    @Override
    protected void onPreExecute() {
      if (tasks.isEmpty()) {
        progressBar.setVisibility(View.VISIBLE);
      }
      tasks.add(this);
    }

    @Override
    protected String doInBackground(HttpRequestData... httpRequestData) {
      return HttpRequestHandler.getDataWithHttpURLConnection(httpRequestData[0]);
    }

    @Override
    protected void onPostExecute(String result) {
      if (result == null) {
        result = "null";
      }
      tasks.remove(this);
      if (tasks.isEmpty()) {
        progressBar.setVisibility(View.GONE);
      }
      textView.setText(result);
    }
  }
}