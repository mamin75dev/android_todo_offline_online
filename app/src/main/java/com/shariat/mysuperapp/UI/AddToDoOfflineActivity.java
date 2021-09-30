package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shariat.mysuperapp.Controller.DBHandler;
import com.shariat.mysuperapp.Models.ToDo;
import com.shariat.mysuperapp.R;

public class AddToDoOfflineActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {

  EditText titleInput, descriptionInput, workerInput;
  RelativeLayout submitBtn;
  TextView submitText;
  ProgressBar submitProgress;
  DatePicker todoDatePicker;

  String date = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_to_do_offline);

    titleInput = findViewById(R.id.add_offline_title_input);
    descriptionInput = findViewById(R.id.add_offline_description_input);
    workerInput = findViewById(R.id.add_offline_worker_input);
    submitBtn = findViewById(R.id.add_offline_submit_button);
    submitText = findViewById(R.id.add_offline_submit_text);
    submitProgress = findViewById(R.id.add_offline_submit_progress);
    todoDatePicker = findViewById(R.id.add_offline_date_picker);

    submitBtn.setOnClickListener(this);
    todoDatePicker.setOnDateChangedListener(this);
  }

  @Override
  public void onClick(View v) {
    String title = String.valueOf(titleInput.getText());
    String description = String.valueOf(descriptionInput.getText());
    String worker = String.valueOf(workerInput.getText());

    if (title.isEmpty() || description.isEmpty() || worker.isEmpty() || date.isEmpty()) {
      Toast.makeText(this, "Please complete form!", Toast.LENGTH_SHORT);
      return;
    }

    submitText.setVisibility(View.GONE);
    submitProgress.setVisibility(View.VISIBLE);

    ToDo todo = new ToDo(title, description, false, worker, date);

    DBHandler dbHandler = new DBHandler(this);
    dbHandler.addNewTodo(todo);
    Toast.makeText(this, "Todo added successfully!", Toast.LENGTH_SHORT);
    titleInput.setText("");
    descriptionInput.setText("");
    workerInput.setText("");
    submitText.setVisibility(View.VISIBLE);
    submitProgress.setVisibility(View.GONE);
    finish();

  }

  @Override
  public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    this.date = year + "-" + monthOfYear + "-" + dayOfMonth;
  }
}