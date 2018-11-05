package ru.otus.elena.cactuscatalogue.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.elena.cactuscatalogue.domain.Cactus;

@Repository("cactusDao")
public class CactusDaoJdbc {

    private final NamedParameterJdbcOperations jdbcOperations;

    public CactusDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations;
    }

    public List<Cactus> getAll() {
        List<Cactus> cactusList = jdbcOperations.query("select * from cactus", new CactusMapper());
        return cactusList;
    }

    public Cactus getById(long id) {
        try {
            final HashMap<String, Object> params = new HashMap<>(1);
            params.put("id", id);
            String query = "select*from cactus where id = :id";
            Cactus cactus = jdbcOperations.queryForObject(query, params, new CactusMapper());
            return cactus;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public void deleteAll() {
        String query = "truncate table cactus";
        jdbcOperations.update(query, new HashMap<String, Object>());
    }

    private static class CactusMapper implements RowMapper<Cactus> {

        @Override
        public Cactus mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String cactusname = resultSet.getString("cactusname");
            long size = resultSet.getLong("size");
            return new Cactus(id, cactusname, size);
        }

    }

}
