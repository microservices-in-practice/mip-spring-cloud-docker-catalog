package com.capgemini.mip.catalog.service;

import com.capgemini.mip.catalog.domain.ItemEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@DirtiesContext
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Autowired
  private EntityManager entityManager;

  @Test
  public void shouldCreateItem() {
    // given
    Item item = provideItem();

    // when
    Item savedItem = itemService.saveItem(item);

    // then

    assertSoftly(softly -> {
      softly.assertThat(savedItem).isNotNull();
      softly.assertThat(savedItem.getCode()).isEqualTo(item.getCode());
      softly.assertThat(savedItem.getDescription()).isEqualTo(item.getDescription());
      softly.assertThat(savedItem.getPrice()).isEqualTo(item.getPrice());
      softly.assertThat(savedItem.getName()).isEqualTo(item.getName());
      softly.assertThat(savedItem.getId()).isNotNull();
      softly.assertThat(savedItem.getVersion()).isNotNull();
    });
  }

  @Test
  public void shouldUpdateItem() {
    // given
    Item item = provideItem();
    Item savedItem = itemService.saveItem(item);

    // when
    savedItem.setPrice(200.0);
    Item updatedItem = itemService.saveItem(savedItem);

    // then
    assertThat(updatedItem).isNotNull();
    assertThat(updatedItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(updatedItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(updatedItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(updatedItem.getName()).isEqualTo(savedItem.getName());
    assertThat(updatedItem.getId()).isEqualTo(savedItem.getId());
  }

  @Test
  public void shouldDeleteItem() {
    // given
    Item savedItem = itemService.saveItem(provideItem());
    assertThat(entityManager.find(ItemEntity.class, savedItem.getId())).isNotNull();

    // when
    itemService.deleteItem(savedItem.getId());

    // then
    assertThat(entityManager.find(ItemEntity.class, savedItem.getId())).isNull();

  }

  @Test
  public void shouldFindById() {
    // given
    Item savedItem = itemService.saveItem(provideItem());

    // when
    Item foundItem = itemService.findById(savedItem.getId());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getId()).isEqualTo(savedItem.getId());
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

  @Test
  public void shouldFindByCode() {
    // given
    Item savedItem = itemService.saveItem(provideItem());

    // when
    Item foundItem = itemService.findByCode(savedItem.getCode());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getId()).isEqualTo(savedItem.getId());
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

  @Test
  public void shouldFindAll() {
    // given
    Long[] itemIds = Arrays.asList(
      provideItem("TE01"),
      provideItem("TE02"),
      provideItem("TE03")
    ).stream()
      .map(item -> itemService.saveItem(item))
      .map(Item::getId)
      .toArray(Long[]::new);

    // when
    List<Item> items = itemService.findAll();

    // then
    assertThat(items.stream().map(Item::getId).collect(Collectors.toList())).contains(itemIds);

  }

  private static Item provideItem(String code) {
    return ItemBuilder.item()
      .withCode(code)
      .withName("Test Training")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();
  }

  private static Item provideItem() {
    return provideItem("TE01");
  }

}
