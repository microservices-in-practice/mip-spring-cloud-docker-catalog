package com.capgemini.mip.catalog.testdata;

import com.capgemini.mip.catalog.domain.ItemEntity;
import com.capgemini.mip.catalog.domain.ItemEntityBuilder;
import com.capgemini.mip.catalog.service.Item;
import com.capgemini.mip.catalog.service.ItemBuilder;

public class TestdataProvider {

  public static ItemEntity provideItemEntity() {
    return ItemEntityBuilder.itemEntity().withCode("TE01")
      .withName("Test Training (TE01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();
  }

  public static Item provideItem(String code) {
    return ItemBuilder.item()
      .withCode(code)
      .withName("Test Training")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();
  }

  public static Item provideItem() {
    return provideItem("TE01");
  }

}
