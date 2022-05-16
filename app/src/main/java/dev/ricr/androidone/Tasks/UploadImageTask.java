package dev.ricr.androidone.Tasks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.concurrent.Executor;

import dev.ricr.androidone.Fragments.DashboardFragment;

public class UploadImageTask implements Executor {

  private String uploadEndpoint = "http://10.0.2.2:4001/index.php?type=avatar";

  public void uploadAvatar(String username, String filePath, DashboardFragment c) {
    new Thread(() -> execute(() -> {
      try {
        multipartRequest(username, uploadEndpoint, filePath, c);
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    })).start();
  }

  public String multipartRequest(String username, String urlTo, String filepath, DashboardFragment c) throws ParseException, IOException {
    HttpURLConnection connection = null;
    DataOutputStream outputStream = null;
    InputStream inputStream = null;

    String twoHyphens = "--";
    String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
    String lineEnd = "\r\n";

    String result = "";

    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;

    String[] q = filepath.split("/");
    int idx = q.length - 1;

    try {
      File file = new File(filepath);
      FileInputStream fileInputStream = new FileInputStream(file);

      String queryParam = "&username=" + username;
      URL url = new URL(urlTo + queryParam);
      connection = (HttpURLConnection) url.openConnection();

      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setUseCaches(false);

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Connection", "Keep-Alive");
      connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
      connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

      outputStream = new DataOutputStream(connection.getOutputStream());
      outputStream.writeBytes(twoHyphens + boundary + lineEnd);
      outputStream.writeBytes("Content-Disposition: form-data; name=\"avatar\"; filename=\"" + q[idx] + "\"" + lineEnd);
      outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
      outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
      outputStream.writeBytes(lineEnd);

      bytesAvailable = fileInputStream.available();
      bufferSize = Math.min(bytesAvailable, maxBufferSize);
      buffer = new byte[bufferSize];

      bytesRead = fileInputStream.read(buffer, 0, bufferSize);
      while (bytesRead > 0) {
        outputStream.write(buffer, 0, bufferSize);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
      }

      outputStream.writeBytes(lineEnd);

      // Upload POST Data
//      String[] posts = post.split("&");
//      int max = posts.length;
//      for(int i=0; i<max;i++) {
//        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//        String[] kv = posts[i].split("=");
//        outputStream.writeBytes("Content-Disposition: form-data; name=\"" + kv[0] + "\"" + lineEnd);
//        outputStream.writeBytes("Content-Type: text/plain"+lineEnd);
//        outputStream.writeBytes(lineEnd);
//        outputStream.writeBytes(kv[1]);
//        outputStream.writeBytes(lineEnd);
//      }

      outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

      inputStream = connection.getInputStream();
      result = this.convertStreamToString(inputStream);

      if (connection.getResponseCode() == 200) {
        c.onAvatarUploadSuccess(result);
      }

      fileInputStream.close();
      inputStream.close();
      outputStream.flush();
      outputStream.close();

      return result;
    } catch (Exception e) {
//      Log.e("MultipartRequest","Multipart Form Upload Error");
      System.out.println("File not found?");
      e.printStackTrace();
      return "error";
    }
  }

  private String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }
}
