package com.housekeeper.database.access;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.housekeeper.core.util.StopWatch;


/**
 * @author yezy
 * @since 2019/2/22
 */
public class JDBCAccess {

    private static Logger logger = LoggerFactory.getLogger(JDBCAccess.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    public <T> List<T> find(String sql, RowMapper<T> rowMapper, Map<String, ?> params) {
        StopWatch watch = new StopWatch();
        int returnedRows = 0;
        try {
            List<T> results = jdbcTemplate.query(sql, params, rowMapper);
            returnedRows = results.size();
            return results;
        } finally {
            logger.debug("find, sql={}, params={}, returnedRows={}, elapsedTime={}", sql, params, returnedRows, watch.elapsedTime());
        }
    }

    public <T> List<T> find(String sql, RowMapper<T> rowMapper) {
        StopWatch watch = new StopWatch();
        int returnedRows = 0;
        try {
            List<T> results = jdbcTemplate.query(sql, rowMapper);
            returnedRows = results.size();
            return results;
        } finally {
            logger.debug("find, sql={}, params={}, returnedRows={}, elapsedTime={}", sql, returnedRows, watch.elapsedTime());
        }
    }

    public <T> T findUniqueResult(String sql, RowMapper<T> rowMapper, Map<String, ?> params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("findUniqueResult did not find any result", e);
            return null;
        } finally {
            logger.debug("findUniqueResult, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForMap(sql, params);
        } finally {
            logger.debug("queryForMap, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

}
