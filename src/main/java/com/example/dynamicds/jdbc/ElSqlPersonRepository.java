package com.example.dynamicds.jdbc;

import com.example.dynamicds.entity.Person;
import com.opengamma.elsql.ElSql;
import com.opengamma.elsql.ElSqlBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ElSqlPersonRepository {

    private final NamedParameterJdbcTemplate namedJdbc;
    private final ElSql sql;

//    public ElSqlPersonRepository(JdbcTemplate jdbcTemplate, ElSql sqlBundle) {
//        this.namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
//        this.sql = sqlBundle;
//    }

    public List<Person> findAll() {
        String sqlQuery = sql.getSql("selectAll");
        return namedJdbc.query(sqlQuery, (rs, n) -> {
            Person p = new Person();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            return p;
        });
    }

    public List<Person> findByName(String name) {
        String sqlQuery = sql.getSql("selectByName");
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("pattern", "%" + name + "%");
        return namedJdbc.query(sqlQuery, params, (rs, n) -> {
            Person p = new Person();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            return p;
        });
    }

    public int insert(String name) {
        String sqlQuery = sql.getSql("insertPerson");
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", name);
        return namedJdbc.update(sqlQuery, params);
    }
}
