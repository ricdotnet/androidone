package dev.ricr.androidone.Auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

import dev.ricr.androidone.LoginActivity;

public class LoginTask implements Executor {
    HttpURLConnection urlConnection;
    URL url;
    BufferedReader in;
    OutputStream out;

    public void doLogin(String username, String password, LoginActivity reflector) {
        new Thread(() -> execute(() -> handleLogin(username, password, reflector))).start();
    }

    private void handleLogin(String username, String password, LoginActivity reflector) {
        try {
            url = new URL("url");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // set to send data
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "text/plain");

            String body = "{\"username\":\"" + username + "\", \"" + password + "\": \"12345\" }";
            out = urlConnection.getOutputStream();
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            out.write(input, 0, input.length);

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String s;
            while ((s = in.readLine()) != null) {
                builder.append(s);
            }

            // TODO: Reflection is broken on callbacks
            try {
                Class<?> c = Class.forName(reflector.getClass().getName());

                Method fn = c.getDeclaredMethod("onLoginSuccess");
                fn.invoke(LoginActivity.class);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
