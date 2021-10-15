package com.shariat.mysuperapp.Utils;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class VolleyRequest extends StringRequest {

  private Map<String, String> parameters;

  public VolleyRequest(
      int method,
      String url,
      Response.Listener<String> listener,
      @Nullable Response.ErrorListener errorListener
  ) {
    super(method, url, listener, errorListener);
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  public static String prepareQueryStringForGetRequest(Map<String, String> params) {
    StringBuilder builder = new StringBuilder();
    try {
      for (String key : params.keySet()) {
        if (builder.length() > 0) {
          builder.append("&");
        }

//      String value = this.parameters.get((key));
        builder.append(key);
        builder.append("=");
        builder.append(URLEncoder.encode(params.get(key), "UTF-8"));
      }
      return builder.toString();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Nullable
  @Override
  protected Map<String, String> getParams() throws AuthFailureError {
    return this.parameters;
  }
}
