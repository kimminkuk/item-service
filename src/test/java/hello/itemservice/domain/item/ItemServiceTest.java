package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class ItemServiceTest {
    ItemService itemService;
    ItemMemoryRepository itemMemoryRepository;

    @BeforeEach
    public void beforeEach() {
        itemMemoryRepository = new ItemMemoryRepository();
        itemService = new ItemService(itemMemoryRepository);
    }

    @AfterEach
    public void afterEach() {
        itemMemoryRepository.clearStore();
    }

    @Test
    public void 아이템등록() throws Exception {
        //given
        Item item1 = new Item("요다인형1", 35000, 3);
        Item item2 = new Item("요다인형2", 55000, 13);

        //when
        Long saveItem1 = itemService.join(item1);
        Long saveItem2 = itemService.join(item2);

        //then
        Item findItem1 = itemMemoryRepository.findById(saveItem1).get();
        assertEquals(item1.getItemName(), findItem1.getItemName());
    }

    @Test
    public void 중복_아이템_예외() throws Exception {
        //given
        Item item1 = new Item("요다인형1", 35000, 3);
        Item item2 = new Item("요다인형1", 55000, 13);

        //when
        itemService.join(item1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> itemService.join(item2));//예외 발생 의도 (중복 아이템)

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이템입니다.");

    }
}