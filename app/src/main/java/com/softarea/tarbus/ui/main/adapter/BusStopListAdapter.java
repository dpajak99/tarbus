package com.softarea.tarbus.ui.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.model.DatabaseBusStop;
import com.softarea.tarbus.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BusStopListAdapter extends RecyclerView.Adapter<BusStopListAdapter.ViewHolder> implements Filterable {

  private List<DatabaseBusStop> busStops = new ArrayList<>();
  private List<DatabaseBusStop> filteredData = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView admin;
    public TextView id;
    public LinearLayout busStop;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      id = itemView.findViewById(R.id.schedule_bus_stop_id);
      name = itemView.findViewById(R.id.schedule_bus_stop_name);
      busStop = itemView.findViewById(R.id.layout_bus_stop);
    }
  }

  public void update(List<DatabaseBusStop> busStops) {
    this.busStops = busStops;
    this.filteredData = busStops;
    this.notifyDataSetChanged();
  }

  public BusStopListAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public BusStopListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_bus_stop_list, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    DatabaseBusStop busStop = filteredData.get(position);
    holder.id.setText(String.valueOf(busStop.getId()));
    holder.name.setText(busStop.getName());


    holder.busStop.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("id", busStop.getId());
      result.putString("BUS_STOP_NAME", busStop.getName());

      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_slide_bus_stop, result);
    });
  }

  @Override
  public int getItemCount() {
    return filteredData.size();
  }

  @Override
  public Filter getFilter() {
    return exampleFilter;
  }
  private Filter exampleFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      List<DatabaseBusStop> filteredList = new ArrayList<>();

      String filterPattern = StringUtils.normalize(constraint.toString().toLowerCase()).trim();
      String[] arrOfStr = filterPattern.split(" ", 5);

      if (constraint == null || constraint.length() == 0) {
        filteredList.addAll(busStops);
      } else {

        for (DatabaseBusStop item : busStops) {
          boolean status = false;
          for (String pattern : arrOfStr) {
            if (StringUtils.normalize(item.getName().toLowerCase()).contains(pattern)) {
              status = true;
            } else {
              status = false;
              break;
            }
          }

        if(status) {
          filteredList.add(item);
        }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      filteredData = (ArrayList<DatabaseBusStop>) results.values;
      notifyDataSetChanged();
    }
  };
}
