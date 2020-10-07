package com.softarea.tarbus.ui.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.interfaces.Departue;
import com.softarea.tarbus.utils.ListUtils;
import com.softarea.tarbus.utils.StringUtils;
import com.softarea.tarbus.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

  private List<Departue> remoteDatabaseDepartues = new ArrayList<>();

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
    public TextView busDirection;
    public TextView busDepartueTime;
    public LinearLayout contentHolder;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
      contentHolder = itemView.findViewById(R.id.content_holder);
    }
  }

  public void update(List<Departue> remoteDatabaseDepartues) {
    this.remoteDatabaseDepartues.clear();
    Collections.sort(remoteDatabaseDepartues, new ListUtils.Sortbyroll());
    this.remoteDatabaseDepartues.addAll(prepareDeparturesList(remoteDatabaseDepartues));
    this.notifyDataSetChanged();
  }

  public BusAdapter() {
  }

  private List<Departue> prepareDeparturesList(List<Departue> departues) {
    //TODO: Research for simplest solution
    List<Integer> usedLines = new ArrayList<>();
    List<Integer> itemsToRemove = new ArrayList<>();
    int size = departues.size();
    for (int i = 0; i < size; i++) {
      Departue departue = departues.get(i);
      if (departue.getLiveTime() == null) {
        if (departue.getDepartueTime() > 0 && departue.getDepartueTime() < 60) {
          itemsToRemove.add(i);
          departues.add(departue);
        } else if (departue.getDepartueTime() < TimeUtils.getCurrentTimeInMin()) {
          itemsToRemove.add(i);
          continue;
        }
        if (!ListUtils.isIntInList(usedLines, departue.getBusLine())) {
          usedLines.add(departue.getBusLine());
          itemsToRemove.add(i);
        }
      } else {
        if (departue.getDepartueTime() < 1000) {
          if (TimeUtils.liveTimeToMin( departue.getLiveTime() ) < TimeUtils.getCurrentTimeInMin()) {
            itemsToRemove.add(i);
            departues.add(departue);
          }
        }
      }
    }

    return removeDepartuesFromList(itemsToRemove, departues);
  }

  public static List<Departue> removeDepartuesFromList(List<Integer> itemsToRemove, List<Departue> list) {
    int o = 0;
    for (int i : itemsToRemove) {
      list.remove(i - o);
      o++;
    }
    return list;
  }

  @NonNull
  @Override
  public BusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_bus, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Departue departueItem = remoteDatabaseDepartues.get(position);

    holder.busNumber.setText(departueItem.getBusLine() + "\n" + departueItem.getBusId());
    holder.busDirection.setText(departueItem.getDestination());
    if (departueItem.getLiveTime() != null) {
      holder.busDepartueTime.setText(StringUtils.replaceHTML(departueItem.getLiveTime()));
    } else {
      holder.busDepartueTime.setText(TimeUtils.min2HHMM(departueItem.getDepartueTime()));
    }

    holder.contentHolder.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("busLine", departueItem.getBusLine());
      result.putInt("busId", departueItem.getBusId());
      result.putInt("wariantId", departueItem.getVariantId());
      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_bus_details_map, result);
    });
  }

  @Override
  public int getItemCount() {
    return remoteDatabaseDepartues.size();
  }
}
