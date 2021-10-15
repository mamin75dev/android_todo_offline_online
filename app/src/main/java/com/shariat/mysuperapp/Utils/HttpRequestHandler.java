package com.shariat.mysuperapp.Utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestHandler {
  public static String getDataWithHttpClient(String uri) {
    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet(uri);
    try {
      HttpResponse response = client.execute(request);
      Log.i("getData", response.toString());
      Log.i("getData 2", StringMethods.inputStreamToString(response.getEntity().getContent()));
      return StringMethods.inputStreamToString(response.getEntity().getContent());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }
//  public static String getDataWithHttpURLConnection(String uri) {
//    try {
//      URL url = new URL(uri);
//      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//      return StringMethods.inputStreamToString(connection.getInputStream());
//    } catch (IOException e) {
//      Log.i("request error", e.getLocalizedMessage());
//      e.printStackTrace();
//    }
//    return "null";
//  }

  public static String getDataWithHttpURLConnection(HttpRequestData requestData) {
    String uri = requestData.getUri();
    try {
      if (requestData.getMethod().equals("GET")) {
        uri += "?" + requestData.makeQueryStringFromParams();
      }
      URL url = new URL(uri);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(requestData.getMethod());
      if (requestData.getMethod().equals("POST")) {
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestData.makeQueryStringFromParams());
        writer.flush();
        writer.close();
      }
      return StringMethods.inputStreamToString(connection.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }

}
