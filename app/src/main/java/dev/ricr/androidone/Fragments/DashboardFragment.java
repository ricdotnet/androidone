package dev.ricr.androidone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.ricr.androidone.R;

public class DashboardFragment extends Fragment implements View.OnClickListener {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private TextView text;

  public DashboardFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment DashboardFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DashboardFragment newInstance(String param1, String param2) {
    DashboardFragment fragment = new DashboardFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

    // listener
    text = view.findViewById(R.id.some_text);
    text.setOnClickListener(this);

    // Inflate the layout for this fragment
    return view;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.some_text:
        System.out.println("posting echo....");
        break;
    }
  }
}