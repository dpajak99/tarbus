package com.softarea.tarbus.data.repository;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MpkJsonRepository {
  private MpkService mpkService;
  private static MpkJsonRepository mpkJsonRepository;

  private MpkJsonRepository() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(MpkService.HTTPS_API_MPK_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    mpkService = retrofit.create(MpkService.class);
  }

  public synchronized static MpkJsonRepository getInstance() {
    if (mpkJsonRepository == null) {
      mpkJsonRepository = new MpkJsonRepository();
    }
    return mpkJsonRepository;
  }

  public MpkService getMpkService() {
    return mpkService;
  }
}
