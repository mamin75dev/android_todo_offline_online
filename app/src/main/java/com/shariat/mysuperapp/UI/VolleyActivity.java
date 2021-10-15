package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.Utils.VolleyRequest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

  private RequestQueue requestQueue;
  private TextView tv;
  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_volley);
    requestQueue = Volley.newRequestQueue(getApplicationContext());
    tv = findViewById(R.id.response_volley);
    imageView = findViewById(R.id.image_view);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add("GET").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        Map<String, String> params = new HashMap<>();
        params.put("name", "amin");
        params.put("age", "23");
        params.put("famliy", "sharait");
        String url = OnlineActivity.GET_URL + "?" + VolleyRequest.prepareQueryStringForGetRequest(params);
        VolleyRequest request = new VolleyRequest(
            Request.Method.GET,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                tv.setText(response);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolleyActivity.this, "request failed!!", Toast.LENGTH_SHORT).show();
              }
            });
        requestQueue.add(request);
        return false;
      }
    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    menu.add("POST").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        VolleyRequest request = new VolleyRequest(
            Request.Method.POST,
            OnlineActivity.POST_URL,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                tv.setText(response);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolleyActivity.this, "request failed!!", Toast.LENGTH_SHORT).show();
              }
            });
        Map<String, String> params = new HashMap<>();
        params.put("name", "sahar");
        params.put("age", "21");
        params.put("famliy", "amindokht");
        request.setParameters(params);
        requestQueue.add(request);
        return false;
      }
    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    menu.add("IMAGE").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        String url = "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png";
        ImageRequest request = new ImageRequest(
            url,
            new Response.Listener<Bitmap>() {
              @Override
              public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
              }
            },
            160,
            160,
            ImageView.ScaleType.FIT_CENTER,
            null,
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolleyActivity.this, "request failed!!", Toast.LENGTH_SHORT).show();
              }
            });
        requestQueue.add(request);
        return false;
      }
    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    return super.onCreateOptionsMenu(menu);
  }
}