package dev.ricr.androidone.Fragments;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import dev.ricr.androidone.Types.Echo;
import dev.ricr.androidone.databinding.FragmentEchoBinding;

public class EchoListRecycler extends RecyclerView.Adapter<EchoListRecycler.EchoView> {

  private ArrayList<Echo> echoes = new ArrayList<>();

  public EchoListRecycler(ArrayList<Echo> echoes) {
    if (this.echoes.size() > 0) {
      for (Echo e : this.echoes) {
        this.echoes.remove(e);
      }
    }
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

    SpannableString username = new SpannableString(holder.echo.getUsername());
    username.setSpan(new AbsoluteSizeSpan(60), 0, holder.echo.getUsername().length(), 0); // set size

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.UK);

    String[] dateNumbers = holder.echo.getCreatedAt().split(" ")[0].split("-");
    StringBuilder newDate = new StringBuilder();
    newDate.append(dateNumbers[2]).append("-").append(dateNumbers[1]).append("-").append(dateNumbers[0]);

    SpannableString createdAt = new SpannableString(newDate.toString());
    createdAt.setSpan(new AbsoluteSizeSpan(40), 0, newDate.toString().length(), 0); // set size

    CharSequence finalEchoTop = TextUtils.concat(username, " - ", createdAt);

    holder.echoTop.setText(finalEchoTop);
    holder.content.setText(holder.echo.getContent());
  }

  @Override
  public int getItemCount() {
    return echoes.size();
  }

  public class EchoView extends RecyclerView.ViewHolder {

    public final TextView echoTop;
    public final TextView content;
    //    public final TextView createdAt;
    public Echo echo;

    public EchoView(FragmentEchoBinding binding) {
      super(binding.getRoot());
      echoTop = binding.echoTop;
      content = binding.content;
//      createdAt = binding.createdAt;
    }

    @Override
    public String toString() {
      return super.toString() + " '" + content.getText() + "'";
    }

  }
}
