package hello.itemservice;

import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemService;
import hello.itemservice.domain.itemV2.ItemV2Repository;
import hello.itemservice.domain.itemV2.ItemV2Service;
import hello.itemservice.repository.JdbcTemplateItemRepository;
import hello.itemservice.repository.JdbcTemplateItemV2Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class itemServiceConfig {
    private final DataSource dataSource;

    public itemServiceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
//    private final EntityManager em;
//
//    public itemServiceConfig(DataSource dataSource, EntityManager em) {
//        this.dataSource = dataSource;
//        this.em = em;
//    }

    @Bean
    public ItemService itemService() {
        return new ItemService(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        //return new ItemMemoryRepository();
        //return new JdbcItemRepository(dataSource);
        return new JdbcTemplateItemRepository(dataSource);
        //return new JpaItemRepository(em);
    }

    @Bean
    public ItemV2Service itemV2Service() {
        return new ItemV2Service(itemV2Repository());
    }

    @Bean
    public ItemV2Repository itemV2Repository() {
        return new JdbcTemplateItemV2Repository(dataSource);
    }
}
