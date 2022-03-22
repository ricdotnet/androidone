package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

  EditText usernameInput, passwordInput, emailInput;
  Button registerButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    registerButton = findViewById(R.id.register_button);

    setListeners();
  }

  private void doRegister(View view) {
    System.out.println("registering user....");
  }

  private void setListeners() {
    registerButton.setOnClickListener(this::doRegister);
  }
}