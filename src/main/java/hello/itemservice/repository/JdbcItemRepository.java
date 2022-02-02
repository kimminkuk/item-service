package hello.itemservice.repository;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcItemRepository implements ItemRepository {
    private final DataSource dataSource;

    public JdbcItemRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(itemName, price, quantity) values(?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, item.getItemName());
            pstmt.setInt(2, item.getPrice());
            pstmt.setInt(3, item.getQuantity());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            } else {
                throw new SQLException("id search error");
            }
            return item;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select * from item where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong("id"));
                item.setItemName(rs.getString("itemname"));
                item.setPrice(rs.getInt("price"));
                item.setQuantity(rs.getInt("quantity"));
                return Optional.of(item);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Item> findByName(String itemName) {
        return Optional.empty();
    }

    @Override
    public List<Item> findAll() {
        String sql = "select * from item";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong("id"));
                item.setItemName(rs.getString("itemname"));
                item.setPrice(rs.getInt("price"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void update(Long itemId, Item updateParam) {
        String sql = "update item set itemname=?, price=?, quantity=? where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(4, itemId);
            pstmt.setString(1, updateParam.getItemName());
            pstmt.setInt(2, updateParam.getPrice());
            pstmt.setInt(3, updateParam.getQuantity());

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
