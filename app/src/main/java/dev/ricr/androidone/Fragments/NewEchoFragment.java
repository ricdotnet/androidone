package dev.ricr.androidone.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.ricr.androidone.Echoes.EchoTask;
import dev.ricr.androidone.R;
import dev.ricr.androidone.Views.EchoesActivity;

public class NewEchoFragment extends Fragment implements View.OnClickListener {

  TextView username;
  TextView today;
  TextInputEditText contentInput;
  Button postEchoButton;

  public NewEchoFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    SharedPreferences userData = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

    View view = inflater.inflate(R.layout.fragment_new_echo, container, false);
    username = view.findViewById(R.id.username);
    today = view.findViewById(R.id.today);
    contentInput = view.findViewById(R.id.echo_content);
    postEchoButton = view.findViewById(R.id.post_echo_button);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
    today.setText(dateFormat.format(new Date()));
    today.setTextSize(15);

    username.setText(new String("@" + userData.getString("username", null)));
    postEchoButton.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.post_echo_button) {
      if (contentInput.getText().toString().isEmpty()) {
        Snackbar.make(requireView(), "Cannot post an empty echo.", 5000).show();
        return;
      }

      new EchoTask().postEcho(username.getText().toString(), contentInput.getText().toString(), this);
    }
  }

  public void onPostSuccess(String response) {
    // this is the best way I found without messing up the bottom navigation
    Intent intent = new Intent(requireContext(), EchoesActivity.class);
    startActivity(intent);

    // todo: make this show up somehow
//    Snackbar.make(requireView(), response, 5000).show();
//    contentInput.setText("");
  }

  public void onPostError(String response) {
    Snackbar.make(requireView(), response, 5000).show();
  }
}