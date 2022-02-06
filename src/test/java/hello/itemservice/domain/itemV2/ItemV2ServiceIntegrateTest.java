package hello.itemservice.domain.itemV2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ItemV2ServiceIntegrateTest {
    @Autowired ItemV2Service itemV2Service;
    @Autowired ItemV2Repository itemV2Repository;

    @Test
    public void 아이템등록() throws Exception {
        //given
        ItemV2 item1 = new ItemV2("요다인형2", 45000, 1, "itemImageBox", "요다인형.png");
        ItemV2 item2 = new ItemV2("요다인형3", 50000, 3, "itemImageBox", "요다인형2.png");

        //when
        Long savedItem1 = itemV2Service.join(item1);
        Long savedItem2 = itemV2Service.join(item2);

        //then
        ItemV2 findItem1 = itemV2Repository.findById(savedItem1).get();

        assertEquals(item1.getItemName(), findItem1.getItemName());
    }

    @Test
    public void 중복_아이템_예외() throws Exception {

    }
}