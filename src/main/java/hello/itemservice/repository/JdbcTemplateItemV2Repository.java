package hello.itemservice.repository;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.itemV2.ItemV2Repository;
import hello.itemservice.domain.itemV2.ItemV2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateItemV2Repository implements ItemV2Repository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateItemV2Repository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ItemV2 save(ItemV2 item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("itemv2").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("itemname", item.getItemName());
        parameters.put("price", item.getPrice());
        parameters.put("quantity", item.getQuantity());
        parameters.put("imgpath", item.getFilePath());
        parameters.put("imgname", item.getStoFileName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        item.setId(key.longValue());
        return item;
    }

    @Override
    public Optional<ItemV2> findById(Long id) {
        List<ItemV2> result = jdbcTemplate.query("select * from itemv2 where id = ?", itemRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<ItemV2> findAll() {
        return jdbcTemplate.query("select * from itemv2", itemRowMapper());
    }

    @Override
    public Optional<ItemV2> findByName(String itemName) {
        List<ItemV2> result = jdbcTemplate.query("select * from itemv2 where itemname = ?", itemRowMapper(), itemName);
        return result.stream().findAny();
    }

    @Override
    public void update(Long itemId, ItemV2 updateParam) {
        jdbcTemplate.update("update itemv2 set itemname = ?, price = ?, quantity = ?, imgname = ?, where id = ?",
                updateParam.getItemName(), updateParam.getPrice(), updateParam.getQuantity(),updateParam.getStoFileName(), itemId);
    }

    private RowMapper<ItemV2> itemRowMapper() {
        return (rs, rowNum) -> {
            ItemV2 itemV2 = new ItemV2();
            itemV2.setId(rs.getLong("id"));
            itemV2.setItemName(rs.getString("itemname"));
            itemV2.setPrice(rs.getInt("price"));
            itemV2.setQuantity(rs.getInt("quantity"));
            itemV2.setStoFileName(rs.getString("imgname"));
            itemV2.setFilePath(rs.getString("imgpath"));
            return itemV2;
        };
    }
}
