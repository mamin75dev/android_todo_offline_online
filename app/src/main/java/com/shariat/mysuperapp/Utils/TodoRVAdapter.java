package com.shariat.mysuperapp.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shariat.mysuperapp.Controller.DBHandler;
import com.shariat.mysuperapp.Models.ToDo;
import com.shariat.mysuperapp.R;

import java.util.ArrayList;

public class TodoRVAdapter extends RecyclerView.Adapter<TodoRVAdapter.ViewHolder> {

  private ArrayList<ToDo> toDoArrayList;
  private Context context;

  public TodoRVAdapter(ArrayList<ToDo> toDoArrayList, Context context) {
    this.toDoArrayList = toDoArrayList;
    this.context = context;
  }

  @NonNull
  @Override
  public TodoRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ToDo todo = toDoArrayList.get(position);

    holder.todoTitleText.setText(todo.getTitle());
    holder.todoWorkerText.setText(todo.getWorker());
    holder.todoDateText.setText(todo.getDate());
    holder.todoStatusCheckBox.setChecked(todo.isStatus());
    holder.todoStatusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DBHandler dbHandler = new DBHandler(context);
        dbHandler.updateTodoStatus(isChecked, todo.getId());
      }
    });
    holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DBHandler dbHandler = new DBHandler(context);
        dbHandler.deleteTodo(todo.getId());
      }
    });
  }

  @Override
  public int getItemCount() {
    return toDoArrayList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView todoTitleText, todoWorkerText, todoDateText;
    private ImageView editImageButton, deleteImageButton;
    private CheckBox todoStatusCheckBox;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      todoTitleText = itemView.findViewById(R.id.todo_item_title);
      todoWorkerText = itemView.findViewById(R.id.todo_item_worker);
      todoDateText = itemView.findViewById(R.id.todo_item_date);
      editImageButton = itemView.findViewById(R.id.todo_item_edit_button);
      deleteImageButton = itemView.findViewById(R.id.todo_item_delete_button);
      todoStatusCheckBox = itemView.findViewById(R.id.todo_item_checkbox);
    }
  }
}
