package hello.itemservice.repository;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateItemRepository implements ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateItemRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("item").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("itemname", item.getItemName());
        parameters.put("price", item.getPrice());
        parameters.put("quantity", item.getQuantity());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        item.setId(key.longValue());

        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        List<Item> result = jdbcTemplate.query("select * from item where id = ?", itemRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Item> findAll() {
        return jdbcTemplate.query("select * from item", itemRowMapper());
    }

    @Override
    public Optional<Item> findByName(String itemName) {
        List<Item> result = jdbcTemplate.query("select * from item where itemname = ?", itemRowMapper(), itemName);
        return result.stream().findAny();
    }

    @Override
    public void update(Long itemId, Item updateParam) {
        jdbcTemplate.update("update item set itemname = ?, price = ?, quantity = ? where id = ?",
                updateParam.getItemName(), updateParam.getPrice(), updateParam.getQuantity(), itemId);
    }

    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("itemname"));
            item.setPrice(rs.getInt("price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        };
    }
}
