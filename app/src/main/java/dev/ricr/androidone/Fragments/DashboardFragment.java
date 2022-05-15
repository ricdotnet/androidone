package dev.ricr.androidone.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.Executor;

import dev.ricr.androidone.Helpers.AsyncRunner;
import dev.ricr.androidone.Helpers.RealPathUtil;
import dev.ricr.androidone.Helpers.ResolveUserAvatar;
import dev.ricr.androidone.R;
import dev.ricr.androidone.Tasks.UploadImageTask;

public class DashboardFragment extends Fragment implements View.OnClickListener {

  private TextInputEditText username;
  private TextInputEditText firstName;
  private TextInputEditText lastName;

  private ImageView userAvatar;
  private Button saveButton;

  SharedPreferences userData;

  public DashboardFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
    userData = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

    username = view.findViewById(R.id.dashboard_username);
    username.setText(userData.getString("username", null));

    firstName = view.findViewById(R.id.dashboard_first_name);
    lastName = view.findViewById(R.id.dashboard_last_name);

    userAvatar = view.findViewById(R.id.dashboard_user_avatar);
    saveButton = view.findViewById(R.id.dashboard_save);

    userAvatar.setOnClickListener(this);
    saveButton.setOnClickListener(this);

//    if (userData.getString("avatar", null) != null) {
//      System.out.println("there is an avatar....");
//      userAvatar.setImageURI(Uri.parse(userData.getString("avatar", null)));
//    userAvatar.setImageBitmap(ResolveUserAvatar.loadBitmap("https://avatars.dicebear.com/api/male/ricdotnet.png"));
    ResolveUserAvatar.loadBitmap("https://avatars.dicebear.com/api/male/ricdotnet.png", this);
//    }

    return view;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.dashboard_user_avatar:
        selectImage();
        break;
      case R.id.dashboard_save:
        System.out.println("saving details...");
        break;
    }
  }

  void selectImage() {
    // create an instance of the
    // intent of the type image
    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    photoPickerIntent.setType("image/*");
//    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    // pass the constant to compare it
    // with the returned requestCode
    startActivityForResult(photoPickerIntent, 200);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {

      // compare the resultCode with the
      // SELECT_PICTURE constant
      if (requestCode == 200) {
        // Get the url of the image from data
        Uri selectedImageUri = data.getData();

        if (null != selectedImageUri) {
//          System.out.println(selectedImageUri);
          new UploadImageTask().uploadAvatar(getPath(selectedImageUri));
          // update the preview image in the layout
          userData.edit().putString("avatar", selectedImageUri.toString()).apply();
          userAvatar.setImageURI(selectedImageUri);
        }
      }
    }
  }

  public String getPath(Uri uri) {
    String[] projection = {MediaStore.Images.Media.DATA};
//    Cursor cursor = this.getContext().getContentResolver(uri, projection, null, null, null);
    Cursor cursor = this.getContext().getContentResolver().query(uri, projection, null, null, null);
    if (cursor == null) return null;
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
    String s = cursor.getString(column_index);
    cursor.close();
    return s;
  }

  public void setUserAvatarCallback(Bitmap bitmap) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        userAvatar.setImageBitmap(bitmap);
      }
    });
  }

  public void onUpdateSuccess(String message) {

  }

  public void onUpdateError(String message) {

  }

}