package dev.ricr.androidone.Fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.ricr.androidone.Types.Echo;
import dev.ricr.androidone.databinding.FragmentEchoBinding;

public class EchoListRecycler extends RecyclerView.Adapter<EchoListRecycler.EchoView> {

  private final ArrayList<Echo> echoes;

  public EchoListRecycler(ArrayList<Echo> echoes) {
    this.echoes = echoes;
  }

  @NonNull
  @Override
  public EchoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new EchoView(FragmentEchoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull EchoListRecycler.EchoView holder, int position) {
    holder.echo = echoes.get(position);
    holder.username.setText(holder.echo.getUsername());
    holder.content.setText(holder.echo.getContent());
    holder.createdAt.setText(holder.echo.getCreatedAt().toString());
  }

  @Override
  public int getItemCount() {
    return echoes.size();
  }

  public class EchoView extends RecyclerView.ViewHolder {

    public final TextView username;
    public final TextView content;
    public final TextView createdAt;
    public Echo echo;

    public EchoView(FragmentEchoBinding binding) {
      super(binding.getRoot());
      username = binding.username;
      content = binding.content;
      createdAt = binding.createdAt;
    }

    @Override
    public String toString() {
      return super.toString() + " '" + content.getText() + "'";
    }

  }
}
