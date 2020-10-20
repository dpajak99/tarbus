package com.softarea.tarbus.data.repository;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MpkXmlRepository {
  private MpkService mpkService;
  private static MpkXmlRepository MpkXmlRepository;

  private MpkXmlRepository() {
    TikXml tikxml = new TikXml.Builder().exceptionOnUnreadXml(true).build();
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(MpkService.HTTPS_API_MPK_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(TikXmlConverterFactory.create(tikxml))
      .build();

    mpkService = retrofit.create(MpkService.class);
  }
  public synchronized static MpkXmlRepository getInstance() {
    if (MpkXmlRepository == null) {
      MpkXmlRepository = new MpkXmlRepository();
    }
    return MpkXmlRepository;
  }

  public MpkService getMpkService() {
    return mpkService;
  }
}
