package com.capgemini.mip.catalog.service;

public final class ItemTOBuilder {
  private Long id;
  private int version;
  private String code;
  private String name;
  private Double price;
  private String description;

  private ItemTOBuilder() {
  }

  public static ItemTOBuilder item() {
    return new ItemTOBuilder();
  }

  public ItemTOBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public ItemTOBuilder withVersion(int version) {
    this.version = version;
    return this;
  }

  public ItemTOBuilder withCode(String code) {
    this.code = code;
    return this;
  }

  public ItemTOBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ItemTOBuilder withPrice(Double price) {
    this.price = price;
    return this;
  }

  public ItemTOBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public ItemTO build() {
    ItemTO item = new ItemTO();
    item.setId(id);
    item.setVersion(version);
    item.setCode(code);
    item.setName(name);
    item.setPrice(price);
    item.setDescription(description);
    return item;
  }
}
