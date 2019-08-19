package com.capgemini.mip.catalog.repository;

import com.capgemini.mip.catalog.domain.Item;
import com.capgemini.mip.catalog.domain.ItemBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.capgemini.mip.catalog.testdata.TestdataProvider.provideItem;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ItemRepository itemRepository;


  @Test
  public void shouldFindItem() {

    // given
    Item savedItem = entityManager.persist(provideItem());

    // when
    Item foundItem = itemRepository.getOne(savedItem.getId());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }


  @Test
  public void shouldSaveItem() {

    // given

    // when
    Item savedItem = itemRepository.save(provideItem());

    // then
    Item foundItem = entityManager.find(Item.class, savedItem.getId());
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

  @Test
  public void shouldUpdateItem() {

    // given
    Item savedItem = itemRepository.save(provideItem());
    Item itemToUpdate = ItemBuilder.item()
      .withCode(savedItem.getCode())
      .withDescription(savedItem.getDescription())
      .withName(savedItem.getName())
      .withPrice(savedItem.getPrice() + 100)
      .withId(savedItem.getId())
      .withVersion(savedItem.getVersion())
      .build();

    // when
    Item updatedItem = itemRepository.save(itemToUpdate);

    // then
    Item foundItem = entityManager.find(Item.class, savedItem.getId());
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(itemToUpdate.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(itemToUpdate.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(itemToUpdate.getPrice());
    assertThat(foundItem.getName()).isEqualTo(itemToUpdate.getName());
  }

  @Test
  public void shouldFindByCode() {

    // given
    Item savedItem = entityManager.persist(provideItem());

    // when
    Item foundItem = itemRepository.findByCode(savedItem.getCode());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

}
