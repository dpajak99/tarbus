package com.softarea.tarbus.ui.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.softarea.tarbus.utils.AnimationUtils;
import com.softarea.tarbus.utils.StringUtils;
import com.softarea.tarbus.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class DepartureAdapter extends RecyclerView.Adapter<DepartureAdapter.ViewHolder> {

  private List<Departue> remoteDatabaseDepartues = new ArrayList<>();
  private final Handler handler = new Handler();

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
    public TextView busDirection;

    public TextView tvFirst;
    public TextView tvLive;
    public TextView tvOffline;

    public TextView busDepartueTime;
    public LinearLayout contentHolder;
    public Runnable runnable;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
      contentHolder = itemView.findViewById(R.id.content_holder);

      tvFirst = itemView.findViewById(R.id.tv_first);
      tvLive = itemView.findViewById(R.id.tv_live);
      tvOffline = itemView.findViewById(R.id.tv_offline);
      runnable = null;
    }
  }

  public void update(List<Departue> remoteDatabaseDepartues) {
    reset();
    this.remoteDatabaseDepartues.clear();
    this.remoteDatabaseDepartues.addAll(remoteDatabaseDepartues);
    this.notifyDataSetChanged();
  }

  public DepartureAdapter() {
  }

  @NonNull
  @Override
  public DepartureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_departure, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Departue departueItem = remoteDatabaseDepartues.get(position);

    holder.busNumber.setText(String.valueOf(departueItem.getBusLine()));
    holder.busDirection.setText(departueItem.getDestination());

    if (departueItem.getLiveTime() != null) {
      String liveTime = StringUtils.replaceHTML(departueItem.getLiveTime());
      holder.busDepartueTime.setText(liveTime);
      if (liveTime.charAt(0) == '<') {
        holder.busDepartueTime.startAnimation(AnimationUtils.getBlinkAnimation());
      }
    } else {
      holder.busDepartueTime.setText(TimeUtils.min2HHMM(departueItem.getDepartueTime()));
    }

    if (departueItem.getDepartureTag().isFirst()) {
      holder.tvFirst.setVisibility(View.VISIBLE);
    } else {
      holder.tvFirst.setVisibility(View.GONE);
    }
    if (departueItem.getDepartureTag().isLive()) {
      holder.tvLive.setVisibility(View.VISIBLE);
      holder.tvOffline.setVisibility(View.GONE);
    } else {
      holder.tvLive.setVisibility(View.GONE);
      holder.tvOffline.setVisibility(View.VISIBLE);
    }

    holder.contentHolder.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("BUS_LINE", departueItem.getBusLine());
      result.putInt("BUS_ID", departueItem.getBusId());
      result.putInt("VARIANT_ID", departueItem.getVariantId());
      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_map_departure_details, result);
    });
  }


  @Override
  public int getItemCount() {
    return remoteDatabaseDepartues.size();
  }

  public void reset() {
    this.handler.removeCallbacks(null);
  }
}
