package com.capgemini.mip.catalog.domain;

public final class ItemEntityBuilder {
  private Long id;
  private int version;
  private String code;
  private String name;
  private Double price;
  private String description;

  private ItemEntityBuilder() {
  }

  public static ItemEntityBuilder itemEntity() {
    return new ItemEntityBuilder();
  }

  public ItemEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public ItemEntityBuilder withVersion(int version) {
    this.version = version;
    return this;
  }

  public ItemEntityBuilder withCode(String code) {
    this.code = code;
    return this;
  }

  public ItemEntityBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ItemEntityBuilder withPrice(Double price) {
    this.price = price;
    return this;
  }

  public ItemEntityBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public ItemEntity build() {
    ItemEntity itemEntity = new ItemEntity();
    itemEntity.setId(id);
    itemEntity.setVersion(version);
    itemEntity.setCode(code);
    itemEntity.setName(name);
    itemEntity.setPrice(price);
    itemEntity.setDescription(description);
    return itemEntity;
  }
}
