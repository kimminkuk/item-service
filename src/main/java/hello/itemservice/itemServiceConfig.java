package hello.itemservice;

import hello.itemservice.domain.item.ItemMemoryRepository;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemService;
import hello.itemservice.repository.JdbcItemRepository;
import hello.itemservice.repository.JdbcTemplateItemRepository;
import hello.itemservice.repository.JpaItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class itemServiceConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    public itemServiceConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

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
}
