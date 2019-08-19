package com.capgemini.mip.catalog.domain;

public final class ItemBuilder {
  private Long id;
  private int version;
  private String code;
  private String name;
  private Double price;
  private String description;

  private ItemBuilder() {
  }

  public static ItemBuilder item() {
    return new ItemBuilder();
  }

  public ItemBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public ItemBuilder withVersion(int version) {
    this.version = version;
    return this;
  }

  public ItemBuilder withCode(String code) {
    this.code = code;
    return this;
  }

  public ItemBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ItemBuilder withPrice(Double price) {
    this.price = price;
    return this;
  }

  public ItemBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public Item build() {
    Item item = new Item();
    item.setId(id);
    item.setVersion(version);
    item.setCode(code);
    item.setName(name);
    item.setPrice(price);
    item.setDescription(description);
    return item;
  }
}
