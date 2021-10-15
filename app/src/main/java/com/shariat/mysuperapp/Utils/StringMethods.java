package com.shariat.mysuperapp.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringMethods {

  public static String inputStreamToString(InputStream stream) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    StringBuilder builder = new StringBuilder();
    String line = "";
    try {
      while ((line = reader.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }

}
