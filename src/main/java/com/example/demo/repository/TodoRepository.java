package com.example.demo.repository;

import com.example.demo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@RepositoryRestResource
@RequiredArgsConstructor
public class TodoRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Collection<Todo> findAll(){
        String sql = "SELECT * FROM TODO";
        return this.namedParameterJdbcTemplate.query(sql, new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Todo.builder()
                        .id(resultSet.getLong("ID"))
                        .title(resultSet.getString("TITLE"))
                        .completed(resultSet.getBoolean("COMPLETED"))
                        .build();
            }
        })
    }
    public int patch(Todo todo){
        String sql = "UPDATE TODO" +
                "SET TITLE = :title, completed = :completed" +
                "WHERE ID = :id";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            if(!todo.getTitle().isEmpty()) parameters.addValue("title", todo.getTitle());
            parameters.addValue("completed", todo.isCompleted());

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<Todo>>() {
                @Override
                public Optional<Todo> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return 1;
                }
            });
        } catch (
                EmptyResultDataAccessException e) {
            return
        }
    }
}
