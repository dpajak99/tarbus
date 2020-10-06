package com.softarea.tarbus.ui.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.softarea.tarbus.R;

public class BusStopDetailsScheduleFragment extends Fragment {
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_schedule, container, false);

    /*List<BusLineStopMediator> busLineStopMediators = new ArrayList<>();
    for(String busLine : BusStopDetailsSlideFragment.busLinesFromBusStop) {
      busLineStopMediators.add(new BusLineStopMediator(BusStopDetailsSlideFragment.busStopId, BusStopDetailsSlideFragment.busStopNameGlobal,  busLine));
    }

    RecyclerView scheduleBusLineRecycler = root.findViewById(R.id.rv_schedule_all);
    ScheduleBusLineAdapter scheduleBusLineAdapter = new ScheduleBusLineAdapter(getActivity());
    scheduleBusLineRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    scheduleBusLineRecycler.setAdapter(scheduleBusLineAdapter);
    scheduleBusLineAdapter.update(busLineStopMediators);


*/
    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

}
