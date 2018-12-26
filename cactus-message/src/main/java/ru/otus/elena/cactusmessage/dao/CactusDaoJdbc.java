package ru.otus.elena.cactusmessage.dao;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.elena.cactusmessage.domain.Cactus;

@Repository("cactusDao")
public class CactusDaoJdbc {

    private final NamedParameterJdbcOperations jdbcOperations;

    public CactusDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations;
    }

    public long insert(Cactus cactus) {
        try {
            String cactusQuery = "insert into CACTUS values (null, :cactusname, :url, :photo)";
            final KeyHolder bKeyHolder = new GeneratedKeyHolder();
            final HashMap<String, Object> cactusParams = new HashMap<>(3);
            cactusParams.put("cactusname", cactus.getCactusname());
            cactusParams.put("url", cactus.getUrl());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) cactus.getPhoto(), "jpg", baos);
            Blob blFile = new SerialBlob(baos.toByteArray());
            cactusParams.put("photo", blFile);
            MapSqlParameterSource parameters = new MapSqlParameterSource().addValues(cactusParams);
            jdbcOperations.update(cactusQuery, parameters, bKeyHolder);
            long id = bKeyHolder.getKey().longValue();
            cactus.setId(id);
            return id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public List<Cactus> getAll() {
        List<Cactus> cactusList = jdbcOperations.query("select * from cactus", new CactusMapper());
        return cactusList;
    }

    public List<Cactus> getByName(String cactusname) {
        String query = "select*from cactus where cactusname = :cactusname";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("cactusname", cactusname);
        List<Cactus> list = jdbcOperations.query(query, params, new CactusMapper());
        return list;
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
            try {
                long id = resultSet.getLong("id");
                String cactusname = resultSet.getString("cactusname");
                String url = resultSet.getString("url");
                Blob blob = resultSet.getBlob("photo");
                BufferedImage photo = ImageIO.read(blob.getBinaryStream());
                return new Cactus(id, cactusname, url, photo);
            } catch (IOException ioe) {
                return null;
            }
        }

    }

}
