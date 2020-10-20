package com.softarea.tarbus.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.model.LiveDepartue;
import com.softarea.tarbus.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MapBusStopAdapter extends RecyclerView.Adapter<MapBusStopAdapter.ViewHolder> {

  private List<LiveDepartue> remoteDepartues = new ArrayList<>();

  FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
    public TextView busDepartueTime;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
//      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
    }
  }

  public void update(List<LiveDepartue> remoteDepartues) {
    this.remoteDepartues.addAll(remoteDepartues);
    this.notifyDataSetChanged();
  }

  public void clear() {
    this.remoteDepartues.clear();
    this.notifyDataSetChanged();
  }

  public MapBusStopAdapter() {
  }

  @NonNull
  @Override
  public MapBusStopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_map_departure, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    LiveDepartue departue = remoteDepartues.get(position);

    holder.busNumber.setText(String.valueOf(departue.getBusLine()));
    holder.busDepartueTime.setText(StringUtils.replaceHTML(departue.getLiveTime()));
  }

  @Override
  public int getItemCount() {
    return remoteDepartues.size();
  }
}
