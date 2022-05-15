package dev.ricr.androidone.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import dev.ricr.androidone.Echoes.EchoTask;
import dev.ricr.androidone.Types.Echo;
import dev.ricr.androidone.R;

/**
 * A fragment representing a list of Items.
 */
public class EchoesFragment extends Fragment {

  ArrayList<Echo> echoes = new ArrayList<>();

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public EchoesFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_echoes_list, container, false);

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      recyclerView.setAdapter(new EchoListRecycler(echoes));
    }
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    new EchoTask().fetchEchoes(this);
  }

  public void onFetchSuccess(String response) {
    InputStream in = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

    JsonReader jsonReader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    try {
      readEchoes(jsonReader);
    } finally {
      try {
        jsonReader.close();
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }

  }

  public void onFetchError(String response) {
    Snackbar.make(requireView(), response, 5000).show();
  }

  private void readEchoes(JsonReader reader) {

    if (this.echoes.size() > 0) {
      this.echoes = new ArrayList<>();
    }

    try {
      reader.beginArray();
      while (reader.hasNext()) {
        this.echoes.add(readEcho(reader));
      }
      reader.endArray();
    } catch (IOException exception) {
      exception.printStackTrace();
    }

  }

  private Echo readEcho(JsonReader reader) {
    String username = null;
    String content = null;
    String createdAt = null;

    try {
      reader.beginObject();
      while (reader.hasNext()) {
        String name = reader.nextName();

        switch (name) {
          case "username":
            username = reader.nextString();
            break;
          case "content":
            content = reader.nextString();
            break;
          case "created_at":
            createdAt = reader.nextString();
            break;
          default:
            reader.skipValue();
            break;
        }
      }
      reader.endObject();

    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return new Echo(username, content, createdAt);
  }
}