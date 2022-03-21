package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import dev.ricr.androidone.Auth.LoginTask;

public class LoginActivity extends AppCompatActivity {

    TextView registerLink, recoverPasswordLink;
    EditText usernameInput, passwordInput;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink = findViewById(R.id.register_link);
        recoverPasswordLink = findViewById(R.id.recover_password_link);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);

        setListeners();
    }

    public void onDoLogin(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty() || !username.equals("ricdotnet")) {
            Snackbar.make(view, "Invalid username.", 5000).show();
            return;
        }

        if (password.isEmpty() || !password.equals("12345")) {
            Snackbar.make(view, "Invalid password.", 5000).show();
            return;
        }

        new LoginTask().doLogin(username, password, this);
//        Snackbar.make(view, "Logged in.", 5000).show();

    }

    public void onLoginSuccess() {
        View current = this.getCurrentFocus();
        Snackbar.make(current, "Logged with success", 5000).show();
    }

    public Runnable onLoginError() {
        return null;
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onRecoverClick(View view) {
        System.out.println("clicked the recover password link");
    }

    private void setListeners() {
        loginButton.setOnClickListener(this::onDoLogin);
        registerLink.setOnClickListener(this::onRegisterClick);
        recoverPasswordLink.setOnClickListener(this::onRecoverClick);
    }
}