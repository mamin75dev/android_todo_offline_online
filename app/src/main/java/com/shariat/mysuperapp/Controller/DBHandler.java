package com.shariat.mysuperapp.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.shariat.mysuperapp.Models.ToDo;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

  // creating a constant variables for our database.
  // below variable is for our database name.
  private static final String DB_NAME = "TodoDB";

  // below int is our database version
  private static final int DB_VERSION = 1;

  // below variable is for our table name.
  private static final String TABLE_NAME = "Todos";

  // below variable is for our id column.
  private static final String ID_COL = "id";

  // below variable is for our to do title column
  private static final String TITLE_COL = "title";

  // below variable id for our to do description column.
  private static final String DESCRIPTION_COL = "description";

  // below variable for our to do status column.
  private static final String STATUS_COL = "status";

  // below variable is for our to do worker column.
  private static final String WORKER_COL = "worker";

  // below variable is for our to do date column.
  private static final String DATE_COL = "date";


  // creating a constructor for our database handler.
  public DBHandler(@Nullable Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  // below method is for creating a database by running a sqlite query
  @Override
  public void onCreate(SQLiteDatabase db) {
    // on below line we are creating
    // an sqlite query and we are
    // setting our column names
    // along with their data types.
    String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
        ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        TITLE_COL + " TEXT, " +
        DESCRIPTION_COL + " TEXT, " +
        STATUS_COL + " INTEGER, " +
        WORKER_COL + " TEXT, " +
        DATE_COL + " TEXT)";

    // at last we are calling a exec sql
    // method to execute above sql query
    db.execSQL(createTableQuery);
  }

  public void addNewTodo(ToDo todo) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put(TITLE_COL, todo.getTitle());
    values.put(DESCRIPTION_COL, todo.getDescription());
    values.put(STATUS_COL, todo.isStatus() ? 1 : 0);
    values.put(WORKER_COL, todo.getWorker());
    values.put(DATE_COL, todo.getDate());

    db.insert(TABLE_NAME, null, values);

    db.close();
  }

  public ArrayList<ToDo> getTodoList() {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor toDosCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    ArrayList<ToDo> toDoArrayList = new ArrayList<>();
    if (toDosCursor.moveToFirst()) {
      do {
        toDoArrayList.add(new ToDo(toDosCursor.getInt(toDosCursor.getColumnIndex(ID_COL)),
            toDosCursor.getString(toDosCursor.getColumnIndex(TITLE_COL)),
            toDosCursor.getString(toDosCursor.getColumnIndex(DESCRIPTION_COL)),
            toDosCursor.getInt(toDosCursor.getColumnIndex(STATUS_COL)) == 1,
            toDosCursor.getString(toDosCursor.getColumnIndex(WORKER_COL)),
            toDosCursor.getString(toDosCursor.getColumnIndex(DATE_COL))));

      } while (toDosCursor.moveToNext());
    }

    toDosCursor.close();
    db.close();
    return toDoArrayList;
  }

  public void updateTodoStatus(boolean status, int id) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(STATUS_COL, status ? 1 : 0);
    db.update(TABLE_NAME, values, "id == " + id, null);
    db.close();
  }

  public void deleteTodo(int id) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, "id == " + id, null);
    db.close();
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // this method is called to check if the table exists already.
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }
}
