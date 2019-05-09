package com.housekeeper.database.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author yezy
 * @since 2019/2/22
 */
public final class SqlBuilder {

    private static final String SPACE = " ";
    private static final String MYSQL_FUZZY = "%";

    private final Sql sql;

    private final List<Consumer<Sql>> consumers = new ArrayList<>();

    private SqlBuilder() {
        this.sql = new Sql();
    }

    public static SqlBuilder of(String selectPart) {
        return new SqlBuilder().with(Sql::setSelectSql, selectPart);
    }

    public static SqlBuilder of() {
        return new SqlBuilder();
    }

    public <U> SqlBuilder with(BiConsumer<Sql, U> consumer, U sqlPart) {
        return with(consumer, sqlPart, Condition.alwaysTrue(null));
    }

    public <U, V> SqlBuilder with(BiConsumer<Sql, U> consumer, U sqlPart, Condition<V> condition) {
        condition.ifTrue(o -> consumers.add(sql -> consumer.accept(sql, sqlPart)));
        return this;
    }

    public <K, V> SqlBuilder with(MapConsumer<Sql, K, V> consumer, K key, V value) {
        return with(consumer, key, value, Condition.alwaysTrue(value));
    }

    public <K, V> SqlBuilder with(MapConsumer<Sql, K, V> consumer, K key, V value, Condition<V> condition) {
        condition.ifTrue(o -> consumers.add(sql -> consumer.accept(sql, key, value)));
        return this;
    }

    public Sql build() {
        consumers.forEach(c -> c.accept(this.sql));
        return this.sql;
    }


    public static class Sql {

        private static final String SELECT_COUNT_1 = "select count(1)";
        private String selectSql;
        private StringBuilder sqlParts = new StringBuilder();
        private final Map<String, Object> params = new HashMap<>();

        public void setSelectSql(String selectSql) {
            this.selectSql = selectSql;
        }

        public void appendSql(String sql) {
            this.sqlParts.append(SPACE).append(sql);
        }

        public void putParam(String key, Object value) {
            params.putIfAbsent(key, value);
        }

        public void putFuzzyParam(String key, Object value) {
            putParam(key, MYSQL_FUZZY + value + MYSQL_FUZZY);
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public String getSql() {
            return selectSql + SPACE + sqlParts.toString();
        }

        public String getCountSql() {
            return SELECT_COUNT_1 + SPACE + sqlParts.toString();
        }
    }

    @FunctionalInterface
    public interface MapConsumer<T, K, V> {

        void accept(T t, K k, V v);
    }

    public static class Condition<V> {

        private final Predicate<V> predicate;
        private final V value;

        public Condition(Predicate<V> predicate, V value) {
            this.predicate = predicate;
            this.value = value;
        }

        public static <V> Condition<V> of(Predicate<V> predicate, V value) {
            return new Condition<>(predicate, value);
        }

        public static <V> Condition<V> alwaysTrue(V value) {
            return new Condition<>(v -> true, value);
        }

        public void ifTrue(Consumer consumer) {
            if (predicate.test(value)) {
                consumer.accept(this);
            }
        }
    }
}
