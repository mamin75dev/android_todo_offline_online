package com.shariat.mysuperapp.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.shariat.mysuperapp.Models.City;
import com.shariat.mysuperapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityDBHelper extends SQLiteOpenHelper {

  private static final int DB_VERSION = 1;
  private static final String DB_NAME = "cities_db";
  public static final String CITY_TABLE = "cities_table";
  private String[] ALL_COLUMNS = {"id", "name", "country_code", "latitude", "longitude"};
  private Context context;

  private static final String CMD_CREATE_CITIES_TABLE =
      "CREATE TABLE IF NOT EXISTS '" + CITY_TABLE + "' (" +
          "'id' INTEGER PRIMARY KEY NOT NULL," +
          "'name' TEXT, " +
          "'latitude' DOUBLE, " +
          "'longitude' DOUBLE, " +
          "'country_code' TEXT)";

  public CityDBHelper(@Nullable Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    this.context = context;
    if (getAllCitiesFromDB(null, null).isEmpty()) {
      initContents();
    }
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CMD_CREATE_CITIES_TABLE);
    initContents();
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE);
    onCreate(db);
  }

  public void initContents() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        InputStream citiesFileInputStream = context.getResources().openRawResource(R.raw.city_list);
        BufferedReader reader = new BufferedReader(new InputStreamReader(citiesFileInputStream));
        String line = "";
        String[] cityLineArray;
        try {
          SQLiteDatabase db = getWritableDatabase();
          while ((line = reader.readLine()) != null) {
            cityLineArray = line.split("\t");
            insertCityToDB(
                new City(
                    Long.parseLong(cityLineArray[0]),
                    cityLineArray[1],
                    cityLineArray[4],
                    Double.parseDouble(cityLineArray[2]),
                    Double.parseDouble(cityLineArray[3])
                ), db);
          }
          db.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  public void insertCityToDB(City city, SQLiteDatabase db) {
    db.insert(CITY_TABLE, null, city.getContentValuesForDB());
  }

  public List<City> getAllCitiesFromDB(String selection, String[] selectionArgs) {
    SQLiteDatabase db = this.getReadableDatabase();
    List<City> cities = new ArrayList<>();
    Cursor citiesCursor = db.query(
        CITY_TABLE,
        ALL_COLUMNS,
        selection,
        selectionArgs,
        null,
        null,
        "desc"
    );
    if (citiesCursor.moveToFirst()) {
      do {
        cities.add(City.convertCursorToCity(citiesCursor));
      } while (citiesCursor.moveToNext());
    }
    citiesCursor.close();
    db.close();
    return cities;
  }
}
