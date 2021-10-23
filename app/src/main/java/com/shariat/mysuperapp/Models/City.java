package com.shariat.mysuperapp.Models;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

public class City {

  private long id;
  private String name, countryCode;
  private double latitude, longitude;

  public City(long id, String name, String countryCode, double latitude, double longitude) {
    this.id = id;
    this.name = name;
    this.countryCode = countryCode;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return String.format("%s, %s", name, countryCode);
  }

  public ContentValues getContentValuesForDB() {
    ContentValues cityCV = new ContentValues();
    cityCV.put("id", this.id);
    cityCV.put("name", this.name);
    cityCV.put("country_code", this.countryCode);
    cityCV.put("longitude", this.longitude);
    cityCV.put("latitude", this.latitude);

    return cityCV;
  }

  public ContentValues createContentValuesFromCity(
      long id,
      String name,
      String countryCode,
      double latitude,
      double longitude
  ) {
    ContentValues cityCV = new ContentValues();
    cityCV.put("id", id);
    cityCV.put("name", name);
    cityCV.put("country_code", countryCode);
    cityCV.put("longitude", longitude);
    cityCV.put("latitude", latitude);
    return cityCV;
  }

  public static City convertCursorToCity(Cursor cursor) {
    return new City(
        cursor.getLong(cursor.getColumnIndex("id")),
        cursor.getString(cursor.getColumnIndex("name")),
        cursor.getString(cursor.getColumnIndex("country_code")),
        cursor.getDouble(cursor.getColumnIndex("latitude")),
        cursor.getDouble(cursor.getColumnIndex("longitude"))
    );
  }

  public static City convertJsonObjectToCity(JSONObject object) {
    try {
      return new City(
          object.getLong("id"),
          object.getString("name"),
          object.getString("country"),
          object.getDouble("latitude"),
          object.getDouble("longitude")
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
