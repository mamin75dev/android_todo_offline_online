package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shariat.mysuperapp.Controller.DBHandler;
import com.shariat.mysuperapp.Models.ToDo;
import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.Utils.TodoRVAdapter;

import java.util.ArrayList;

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener {

  FloatingActionButton addBtn;
  private ArrayList<ToDo> toDoArrayList;
  private DBHandler dbHandler;
  private TodoRVAdapter todoRVAdapter;
  private RecyclerView todoRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_offline);

    addBtn = findViewById(R.id.addTodo);

    dbHandler = new DBHandler(this);

    toDoArrayList = new ArrayList<>();
    toDoArrayList = dbHandler.getTodoList();

    todoRVAdapter = new TodoRVAdapter(toDoArrayList, this);

    todoRecyclerView = findViewById(R.id.todos_recycler_view);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    todoRecyclerView.setLayoutManager(linearLayoutManager);

    todoRecyclerView.setAdapter(todoRVAdapter);

    addBtn.setOnClickListener(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.i("info", "onResume");
    toDoArrayList = dbHandler.getTodoList();
    todoRVAdapter = new TodoRVAdapter(toDoArrayList, this);
    todoRVAdapter.notifyDataSetChanged();
    todoRecyclerView.setAdapter(todoRVAdapter);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.addTodo) {
      Intent intent = new Intent(OfflineActivity.this, AddToDoOfflineActivity.class);
      startActivity(intent);
    }
  }
}