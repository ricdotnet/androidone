package dev.ricr.androidone.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
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
import java.util.Date;

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

  // TODO: Customize parameter initialization
//  @SuppressWarnings("unused")
//  public static EchoesFragment newInstance(int columnCount) {
//    EchoesFragment fragment = new EchoesFragment();
//    Bundle args = new Bundle();
//    args.putInt(ARG_COLUMN_COUNT, columnCount);
//    fragment.setArguments(args);
//    return fragment;
//  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    if (getArguments() != null) {
//      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_echoes_list, container, false);

    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
//      if (mColumnCount <= 1) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//      } else {
//        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//      }
      recyclerView.setAdapter(new EchoListRecycler(echoes));
    }
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    new EchoTask().fetchEchoes(this);

//    echo = echoes.getEchoesList().get(0);
//    System.out.println(echo.getContent());
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
//    ArrayList<Echo> echoes = new ArrayList<>();

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

        if (name.equals("username")) {
          username = reader.nextString();
        } else if (name.equals("content")) {
          content = reader.nextString();
        } else if (name.equals("created_at")) {
          createdAt = reader.nextString();
        } else {
          reader.skipValue();
        }
      }
      reader.endObject();

    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return new Echo(username, content, createdAt);
  }
}