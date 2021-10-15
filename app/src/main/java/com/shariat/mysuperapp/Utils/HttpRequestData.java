package com.shariat.mysuperapp.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class HttpRequestData {
  private String uri;
  private String method;
  private HashMap<String, String> parameters;


  public HttpRequestData() {
    this("", "GET");
  }

  public HttpRequestData(String uri, String method) {
    this.uri = uri;
    this.method = method;
    this.parameters = new HashMap<>();
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public HashMap<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(HashMap<String, String> parameters) {
    if (parameters == null) {
      parameters = new HashMap<>();
    }
    this.parameters = parameters;
  }

  public void addParameterToRequest(String key, String value) {
    if (this.parameters == null) {
      this.parameters = new HashMap<>();
    }
    this.parameters.put(key, value);
  }

  public String makeQueryStringFromParams() {
    StringBuilder builder = new StringBuilder();
    try {
      for (String key : this.parameters.keySet()) {
        if (builder.length() > 0) {
          builder.append("&");
        }

//      String value = this.parameters.get((key));
        builder.append(key);
        builder.append("=");
        builder.append(URLEncoder.encode(this.parameters.get(key), "UTF-8"));
        return builder.toString();
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }
}
