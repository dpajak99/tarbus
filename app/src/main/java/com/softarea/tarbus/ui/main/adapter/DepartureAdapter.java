package com.softarea.tarbus.ui.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.softarea.tarbus.utils.StringUtils;
import com.softarea.tarbus.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class DepartureAdapter extends RecyclerView.Adapter<DepartureAdapter.ViewHolder> {

  private List<Departue> remoteDatabaseDepartues = new ArrayList<>();
  private final Handler handler = new Handler();
  private List<Runnable> runnableList = new ArrayList<>();

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
    public TextView busDirection;

    public TextView tvFirst;
    public TextView tvLive;
    public TextView tvOffline;

    public TextView busDepartueTime;
    public LinearLayout contentHolder;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
      contentHolder = itemView.findViewById(R.id.content_holder);

      tvFirst = itemView.findViewById(R.id.tv_first);
      tvLive = itemView.findViewById(R.id.tv_live);
      tvOffline = itemView.findViewById(R.id.tv_offline);


    }
  }

  public void update(List<Departue> remoteDatabaseDepartues) {
    for (Runnable runnable : runnableList) {
      this.handler.removeCallbacks(runnable);
    }
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
      showHideNextTime(holder, liveTime.charAt(0));
    } else {
      holder.busDepartueTime.setText(TimeUtils.min2HHMM(departueItem.getDepartueTime()));
    }

    if (departueItem.getDepartureTag().isFirst()) {
      Log.i("TEST", departueItem.getBusId() + "");
      holder.tvFirst.setVisibility(View.VISIBLE);
    } else {
      holder.tvFirst.setVisibility(View.GONE);
    }
    if (departueItem.getDepartureTag().isLive()) {
      Log.i("TEST", departueItem.getBusId() + "");
      holder.tvLive.setVisibility(View.VISIBLE);
      holder.tvOffline.setVisibility(View.GONE);
    } else {
      holder.tvLive.setVisibility(View.GONE);
      holder.tvOffline.setVisibility(View.VISIBLE);
    }

    holder.contentHolder.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("busLine", departueItem.getBusLine());
      result.putInt("busId", departueItem.getBusId());
      result.putInt("wariantId", departueItem.getVariantId());
      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_bus_details_map, result);
    });
  }

  private void showHideNextTime(ViewHolder holder, char a) {
    if (a == '<') {
      Log.i("TEST", "timer1");

      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          if (holder.busDepartueTime.getVisibility() == View.VISIBLE) {
            holder.busDepartueTime.setVisibility(View.INVISIBLE);
          } else {
            holder.busDepartueTime.setVisibility(View.VISIBLE);
          }
          handler.postDelayed(this, 500);
          Log.i("TEST", "timer");
        }
      };
      runnableList.add(runnable);
      handler.postDelayed(runnable, 500);
    }


  }


  @Override
  public int getItemCount() {
    return remoteDatabaseDepartues.size();
  }
}
