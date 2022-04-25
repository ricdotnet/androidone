package dev.ricr.androidone.Fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.ricr.androidone.Types.Echo;
import dev.ricr.androidone.databinding.FragmentEchoBinding;
import dev.ricr.androidone.databinding.FragmentEchoesListBinding;

import java.util.ArrayList;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

  private final ArrayList<Echo> echoes;

  public MyItemRecyclerViewAdapter(ArrayList<Echo> echoes) {
    this.echoes = echoes;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    return new ViewHolder(FragmentEchoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = echoes.get(position);
    holder.mIdView.setText(position + 1 + "");
    holder.mContentView.setText(echoes.get(position).getContent());
  }

  @Override
  public int getItemCount() {
    return echoes.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView mIdView;
    public final TextView mContentView;
    public Echo mItem;

    public ViewHolder(FragmentEchoBinding binding) {
      super(binding.getRoot());
      mIdView = binding.itemNumber;
      mContentView = binding.content;
    }

    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }
  }
}