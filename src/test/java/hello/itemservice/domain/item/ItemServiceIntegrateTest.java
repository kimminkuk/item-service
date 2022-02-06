package hello.itemservice.domain.item;

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
        Item itemV1 = new Item("요다인형111", 35000, 3);
        Item itemV2 = new Item("요다인형222", 55000, 13);

        //when
        Long saveItem1 = itemService.join(itemV1);
        Long saveItem2 = itemService.join(itemV2);

        //then
        Item findItem1V2 = itemMemoryRepository.findById(saveItem1).get();
        assertEquals(itemV1.getItemName(), findItem1V2.getItemName());
    }

    @Test
    public void 중복_아이템_예외() throws Exception {
        //given
        Item itemV1 = new Item("요다인형111", 35000, 3);
        Item itemV2 = new Item("요다인형222", 55000, 13);

        //when
        itemService.join(itemV1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> itemService.join(itemV1));//예외 발생 의도 (중복 아이템)

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이템입니다.");

    }
}