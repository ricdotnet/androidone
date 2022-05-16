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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.Executor;

import dev.ricr.androidone.Helpers.AsyncRunner;
import dev.ricr.androidone.Helpers.RealPathUtil;
import dev.ricr.androidone.Helpers.ResolveUserAvatar;
import dev.ricr.androidone.R;
import dev.ricr.androidone.Tasks.DashboardTask;
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

    if (!userData.getString("firstName", null).isEmpty()
        && !userData.getString("lastName", null).isEmpty()) {
      firstName.setText(userData.getString("firstName", null));
      lastName.setText(userData.getString("lastName", null));
    }

    userAvatar = view.findViewById(R.id.dashboard_user_avatar);
    saveButton = view.findViewById(R.id.dashboard_save);

    userAvatar.setOnClickListener(this);
    saveButton.setOnClickListener(this);

    if (userData.getString("avatar", null) != null) {
      String userAvatarUrl = "http://10.0.2.2:4001/images/" + userData.getString("avatar", null);
      ResolveUserAvatar.loadBitmap(userAvatarUrl, this);
    }

    return view;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.dashboard_user_avatar:
        selectImage();
        break;
      case R.id.dashboard_save:
        updateUserDetails();
        break;
    }
  }

  public void updateUserDetails() {
    String newUsername = username.getText().toString();
    String newFirstName = firstName.getText().toString();
    String newLastName = lastName.getText().toString();

    if (newUsername.trim().isEmpty() || newFirstName.trim().isEmpty() || newLastName.trim().isEmpty()) {
      Snackbar.make(requireView(), "Please fill all details", 5000).show();
      return;
    }

    new DashboardTask().doSaveProfile(newUsername, newFirstName, newLastName, this);
  }

  public void selectImage() {
    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    photoPickerIntent.setType("image/*");
    startActivityForResult(photoPickerIntent, 200);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {

      if (requestCode == 200) {
        Uri selectedImageUri = data.getData();

        if (null != selectedImageUri) {
          new UploadImageTask().uploadAvatar(userData.getString("username", null), getPath(selectedImageUri), this);
//          userData.edit().putString("avatar", selectedImageUri.toString()).apply();
//          userAvatar.setImageURI(selectedImageUri);
        }
      }
    }
  }

  public String getPath(Uri uri) {
    String[] projection = {MediaStore.Images.Media.DATA};
    Cursor cursor = this.getContext().getContentResolver().query(uri, projection, null, null, null);
    if (cursor == null) return null;
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
    String s = cursor.getString(column_index);
    cursor.close();
    return s;
  }

  public void onAvatarUploadSuccess(String response) {
    userData.edit().putString("avatar", response).apply();

    String userAvatar = "http://10.0.2.2:4001/images/" + response;
    ResolveUserAvatar.loadBitmap(userAvatar, this);
  }

  public void setUserAvatarCallback(Bitmap bitmap) {
    new Handler(Looper.getMainLooper()).post(() ->  {
      try {
          userAvatar.setImageBitmap(bitmap);
      } catch (RuntimeException e) {
        System.out.println("trying to draw an image too large...");
      }
    });
  }

  public void onUpdateSuccess(String message) {
    Snackbar.make(this.requireView(), message, 5000).show();
  }

  public void onUpdateError(String message) {
    Snackbar.make(this.requireView(), message, 5000).show();
  }

}