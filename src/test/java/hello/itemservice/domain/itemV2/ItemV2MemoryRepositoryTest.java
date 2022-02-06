package hello.itemservice.domain.itemV2;

import hello.itemservice.domain.itemV2.ItemV2;
import hello.itemservice.domain.itemV2.ItemV2MemoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemV2MemoryRepositoryTest {
    ItemV2MemoryRepository itemV2MemoryRepository = new ItemV2MemoryRepository();

    @AfterEach
    void afterEach() {
        itemV2MemoryRepository.clearStore();
    }

    @Test
    void save() {
        //given
        ItemV2 item = new ItemV2("베이비요다", 10000, 10, "베이비요다아기버전.png", "resources/static/itemImageBox");

        //when
        ItemV2 savedItem = itemV2MemoryRepository.save(item);

        //then
        ItemV2 findItem = itemV2MemoryRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);

    }
}
