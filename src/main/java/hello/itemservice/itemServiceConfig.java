package hello.itemservice;

import hello.itemservice.domain.item.ItemMemoryRepository;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemService;
import hello.itemservice.repository.JdbcItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class itemServiceConfig {
    private final DataSource dataSource;

    public itemServiceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ItemService itemService() {
        return new ItemService(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        //return new ItemMemoryRepository();
        return new JdbcItemRepository(dataSource);
    }
}
