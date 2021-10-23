package com.shariat.mysuperapp.Data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shariat.mysuperapp.Models.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityRequestHelper {
  private static final String CITIES_BASE_URL = "http://10.0.2.2:8000/api/cities/";

  private RequestQueue queue;
  private List<City> citiesList;

  public CityRequestHelper(Context context) {
    this.queue = Volley.newRequestQueue(context);
    citiesList = new ArrayList<>();
  }

  public void setCitiesList(List<City> citiesList) {
    this.citiesList = citiesList;
  }

  public List<City> getCitiesList() {
    return citiesList;
  }

  public void getCities() {
    String url = CITIES_BASE_URL + "all";
    JsonArrayRequest request = new JsonArrayRequest(
        Request.Method.GET,
        url,
        null,
        new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(JSONArray response) {
            Log.i("cities_response2", response.toString());
            for (int i = 0; i < response.length(); i++) {
              try {
                JSONObject obj = response.getJSONObject(i);
                citiesList.add(City.convertJsonObjectToCity(obj));
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.i("cities_error", error.getMessage());
          }
        });
    queue.add(request);
  }

  public City getCityDetails(long id) {
    String url = CITIES_BASE_URL + id;
    final City[] city = {null};
    JsonObjectRequest request = new JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            city[0] = City.convertJsonObjectToCity(response);
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.i("city_error", error.getMessage());
          }
        });

    queue.add(request);
    return city[0];
  }
}
