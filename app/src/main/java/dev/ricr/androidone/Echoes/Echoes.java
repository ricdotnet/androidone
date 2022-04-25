package dev.ricr.androidone.Echoes;

import java.util.ArrayList;
import java.util.Date;

import dev.ricr.androidone.Types.Echo;

public class Echoes {

  ArrayList<Echo> echoesList = new ArrayList<>();

  public Echoes() {
    this.echoesList.add(new Echo("echo one", "ricdotnet", new Date("10/10/2021")));
    this.echoesList.add(new Echo("echo two", "ricardo", new Date("11/10/2021")));
    this.echoesList.add(new Echo("echo three", "ricdotnet", new Date("11/10/2021")));
    this.echoesList.add(new Echo("echo four", "ricdotnet", new Date("11/10/2021")));
    this.echoesList.add(new Echo("echo five", "ricdotnet", new Date("11/10/2021")));
    this.echoesList.add(new Echo("echo six", "johndoe", new Date("11/10/2021")));
    this.echoesList.add(new Echo("echo seven", "johndoe", new Date("12/10/2021")));
    this.echoesList.add(new Echo("echo eight", "janedoe", new Date("12/10/2021")));
    this.echoesList.add(new Echo("echo nine", "ricdotnet", new Date("12/10/2021")));
  }

  public ArrayList<Echo> getEchoesList() {
    return this.echoesList;
  }

}

