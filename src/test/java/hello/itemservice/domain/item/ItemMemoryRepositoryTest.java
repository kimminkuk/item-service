package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMemoryRepositoryTest {
    ItemMemoryRepository itemMemoryRepository = new ItemMemoryRepository();

    @AfterEach
    void afterEach() {
        itemMemoryRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemMemoryRepository.save(item);

        //then
        Item findItem = itemMemoryRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item itemV1 = new Item("item1", 10000, 10);
        Item itemV2 = new Item("item2", 20000, 20);
        itemMemoryRepository.save(itemV1);
        itemMemoryRepository.save(itemV2);

        //when
        List<Item> result = itemMemoryRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(itemV1, itemV2);
    }

    @Test
    void findByName() {
        //given
        Item itemV1 = new Item("baby-Yoda", 6400000, 2);
        Item itemV2 = new Item("Special-Yoda", 320000, 6);
        itemMemoryRepository.save(itemV1);
        itemMemoryRepository.save(itemV2);

        //when
        Item findItem = itemMemoryRepository.findByName("baby-Yoda").get();

        //then
        assertThat(findItem).isEqualTo(itemV1);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("item1", 10000, 10);
        Item savedItem = itemMemoryRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("item2", 20000, 30);
        itemMemoryRepository.update(itemId, updateParam);

        //then
        Item findItem = itemMemoryRepository.findById(itemId).get();
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}