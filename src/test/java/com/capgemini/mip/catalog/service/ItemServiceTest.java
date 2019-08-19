package com.capgemini.mip.catalog.service;

import com.capgemini.mip.catalog.domain.Item;
import com.capgemini.mip.catalog.testdata.TestdataProvider;
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

import static com.capgemini.mip.catalog.testdata.TestdataProvider.provideItemTO;
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
    ItemTO item = TestdataProvider.provideItemTO();

    // when
    ItemTO savedItem = itemService.saveItem(item);

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
    ItemTO item = TestdataProvider.provideItemTO();
    ItemTO savedItem = itemService.saveItem(item);

    // when
    savedItem.setPrice(200.0);
    ItemTO updatedItem = itemService.saveItem(savedItem);

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
    ItemTO savedItem = itemService.saveItem(TestdataProvider.provideItemTO());
    assertThat(entityManager.find(Item.class, savedItem.getId())).isNotNull();

    // when
    itemService.deleteItem(savedItem.getId());

    // then
    assertThat(entityManager.find(Item.class, savedItem.getId())).isNull();

  }

  @Test
  public void shouldFindById() {
    // given
    ItemTO savedItem = itemService.saveItem(TestdataProvider.provideItemTO());

    // when
    ItemTO foundItem = itemService.findById(savedItem.getId());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getId()).isEqualTo(savedItem.getId());
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

  @Test
  public void shouldFindByCode() {
    // given
    ItemTO savedItem = itemService.saveItem(TestdataProvider.provideItemTO());

    // when
    ItemTO foundItem = itemService.findByCode(savedItem.getCode());

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
      provideItemTO("TE01"),
      provideItemTO("TE02"),
      provideItemTO("TE03")
    ).stream()
      .map(item -> itemService.saveItem(item))
      .map(ItemTO::getId)
      .toArray(Long[]::new);

    // when
    List<ItemTO> items = itemService.findAll();

    // then
    assertThat(items.stream().map(ItemTO::getId).collect(Collectors.toList())).contains(itemIds);

  }


}
