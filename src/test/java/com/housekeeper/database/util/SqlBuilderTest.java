package com.housekeeper.database.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * @author yezy
 * @Date Created in 18:04 2019/4/18
 */
public class SqlBuilderTest {

    @Test
    public void test() {
        SqlBuilder.Sql sql = SqlBuilder.of("select * from example_user where 1=1")
                .with(SqlBuilder.Sql::appendSql, "and user_name =:userName")
                .with(SqlBuilder.Sql::putParam, "userName", "www.example.com")
                .with(SqlBuilder.Sql::appendSql, "and customer_detail_id =:customerId", SqlBuilder.Condition.of(StringUtils::hasText, ""))
                .with(SqlBuilder.Sql::putParam, "customerId", "50013231231", SqlBuilder.Condition.of(StringUtils::hasText, ""))
                .with(SqlBuilder.Sql::appendSql, "and user_id =:userId", SqlBuilder.Condition.of(StringUtils::hasText, "2"))
                .with(SqlBuilder.Sql::putParam, "userId", "321312", SqlBuilder.Condition.of(StringUtils::hasText, "2"))
                .build();
        Assert.assertEquals("select * from example_user where 1=1  and user_name =:userName and user_id =:userId", sql.getSql());
        Assert.assertTrue(sql.getParams().containsKey("userName"));
        Assert.assertEquals("www.example.com", sql.getParams().get("userName"));
        Assert.assertFalse(sql.getParams().containsKey("customerId"));
        Assert.assertTrue(sql.getParams().containsKey("userId"));
        Assert.assertEquals("321312", sql.getParams().get("userId"));
    }

}