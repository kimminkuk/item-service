package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ItemServiceIntegrateTest {
    @Autowired  ItemService itemService;
    @Autowired  ItemRepository itemMemoryRepository;

    @Test
    public void 아이템등록() throws Exception {
        //given
        Item item1 = new Item("요다인형111", 35000, 3);
        Item item2 = new Item("요다인형222", 55000, 13);

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
        Item item1 = new Item("요다인형111", 35000, 3);
        Item item2 = new Item("요다인형222", 55000, 13);

        //when
        itemService.join(item1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> itemService.join(item1));//예외 발생 의도 (중복 아이템)

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이템입니다.");

    }
}