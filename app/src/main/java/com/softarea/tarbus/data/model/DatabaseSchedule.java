package com.softarea.tarbus.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.softarea.tarbus.utils.StringUtils;
import com.softarea.tarbus.utils.TimeUtils;


public class DatabaseSchedule {
  @ColumnInfo(name = "godzina_odjazdu_in_sec")
  public int departue;
  @ColumnInfo(name = "dodatkowe_oznaczenia_string")
  private String variants;
  @Ignore
  private String variantsShortcut;

  public DatabaseSchedule(int departue, String variants) {
    this.departue = departue;
    this.variants = variants;
    this.variantsShortcut = StringUtils.getFirstLetterFromWordsSeperatedBySemicolon(variants);
  }


  public String getDepartue() {
    return TimeUtils.min2HHMM(departue);
  }

  public String getVariants() {
    return variants;
  }

  public String getVariantsShortcut() {
    return variantsShortcut;
  }
}
