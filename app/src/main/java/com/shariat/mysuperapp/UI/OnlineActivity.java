package com.shariat.mysuperapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shariat.mysuperapp.Models.ToDo;
import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.Utils.HttpRequestHandler;
import com.shariat.mysuperapp.Utils.StringMethods;
import com.shariat.mysuperapp.Utils.TodoRVAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;

public class OnlineActivity extends AppCompatActivity implements View.OnClickListener {

  FloatingActionButton addBtn;
  private ArrayList<ToDo> toDoArrayList;
  private TodoRVAdapter todoRVAdapter;
//  private RecyclerView todoRecyclerView;

  TextView tv;

//  public static final String URL = "http://10.0.2.2:8000/api/todos";
  public static final String GET_URL = "http://10.0.2.2:8000/api/android/get";
  public static final String POST_URL = "http://10.0.2.2:8000/api/android/post";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_online);

    addBtn = findViewById(R.id.add_todo_online);
//    todoRecyclerView = findViewById(R.id.todos_recycler_view_online);
    toDoArrayList = new ArrayList<>();
    todoRVAdapter = new TodoRVAdapter(toDoArrayList, this);

//    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//    todoRecyclerView.setLayoutManager(linearLayoutManager);

//    todoRecyclerView.setAdapter(todoRVAdapter);

    addBtn.setOnClickListener(this);

    tv = findViewById(R.id.response_test);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuItem item = menu.add("GET");
    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        Handler handler = new Handler() {
          @Override
          public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            tv.setText((String) msg.getData().get("content"));
          }
        };

        Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {
            String content = HttpRequestHandler.getDataWithHttpClient(GET_URL);
            Bundle bundle = new Bundle();
            bundle.putString("content", content);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
          }
        });

        thread.start();
        return false;
      }
    });

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.i("info", "onResume");
//    toDoArrayList = dbHandler.getTodoList();
    todoRVAdapter = new TodoRVAdapter(toDoArrayList, this);
    todoRVAdapter.notifyDataSetChanged();
//    todoRecyclerView.setAdapter(todoRVAdapter);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.add_todo_online) {
      Intent intent = new Intent(OnlineActivity.this, AddToDoOnlineActivity.class);
      startActivity(intent);
    }
  }
}