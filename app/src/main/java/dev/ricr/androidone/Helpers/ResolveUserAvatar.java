package dev.ricr.androidone.Helpers;

import static android.os.FileUtils.copy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executor;

import dev.ricr.androidone.Fragments.DashboardFragment;

public class ResolveUserAvatar {

  static String url;
  static Bitmap avatarBitmap;

  public static void loadBitmap(String url, DashboardFragment df) {
    ResolveUserAvatar.url = url;

    new UserAvatarTask().runner(df);
  }

  static class UserAvatarTask implements Executor {

    public void runner(DashboardFragment df) {
      new Thread(() -> execute(() -> downloadInBackground(df))).start();
    }

    private void downloadInBackground(DashboardFragment df) {
      Bitmap bitmap = null;
      InputStream in = null;
      BufferedOutputStream out = null;

      try {
        in = new BufferedInputStream(new URL(url).openStream());

        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        out = new BufferedOutputStream(dataStream);
        copy(in, out);
        out.flush();

        final byte[] data = dataStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 1;

        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        in.close();
        out.close();
      } catch (IOException e) {
//      Log.e(TAG, "Could not load Bitmap from: " + url);
      }

      ResolveUserAvatar.avatarBitmap = bitmap;
      df.setUserAvatarCallback(bitmap);
    }

    @Override
    public void execute(Runnable runnable) {
      runnable.run();
    }

  }

}