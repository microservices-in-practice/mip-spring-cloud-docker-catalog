package com.capgemini.mip.catalog.testdata;

import com.capgemini.mip.catalog.domain.Item;
import com.capgemini.mip.catalog.domain.ItemBuilder;
import com.capgemini.mip.catalog.service.ItemTO;
import com.capgemini.mip.catalog.service.ItemTOBuilder;

public class TestdataProvider {

  public static Item provideItem() {
    return ItemBuilder.item()
      .withCode("TE01")
      .withName("Test Training (TE01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();
  }

  public static ItemTO provideItemTO(String code) {
    return ItemTOBuilder.item()
      .withCode(code)
      .withName("Test Training")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();
  }

  public static ItemTO provideItemTO() {
    return provideItemTO("TE01");
  }

}
