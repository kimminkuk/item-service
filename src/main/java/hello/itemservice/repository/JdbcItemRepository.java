package hello.itemservice.repository;

import javax.sql.DataSource;

public class JdbcItemRepository {
    private final DataSource dataSource;

    public JdbcItemRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
