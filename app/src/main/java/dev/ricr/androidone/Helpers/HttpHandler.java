package dev.ricr.androidone.Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpHandler {

  HttpURLConnection urlConnection;
  URL url;

  BufferedReader in;
  OutputStream out;

  String serverResponse;
  int errorCode;

  public HttpHandler(String requestUrl, String requestMethod, String body) {
    initRequest(requestUrl, requestMethod, body);
  }

  private void initRequest(String requestUrl, String requestMethod, String body) {
    try {
      url = new URL(requestUrl);
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setDoOutput(true);
      urlConnection.setRequestMethod(requestMethod);
      urlConnection.setRequestProperty("Content-Type", "application/json");
      urlConnection.setRequestProperty("Accept", "application/json");

      setOutput(body);

      errorCode = urlConnection.getResponseCode();
      readInput();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readInput() {
    try {
      if (urlConnection.getResponseCode() == 200) {
        in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      } else {
        in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
      }
      StringBuilder builder = new StringBuilder();
      String s;
      while ((s = in.readLine()) != null) {
        builder.append(s);
      }
      serverResponse = builder.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setOutput(String body) {
    try {
      out = urlConnection.getOutputStream();
      byte[] input = body.getBytes(StandardCharsets.UTF_8);
      out.write(input, 0, input.length);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getServerResponse() {
    return this.serverResponse;
  }

  public int getErrorCode() {
    return this.errorCode;
  }
}
