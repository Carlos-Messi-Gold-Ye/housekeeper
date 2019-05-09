package com.housekeeper.database.access;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import com.housekeeper.core.util.StopWatch;


/**
 * mybatis查询类
 *
 * @author yezy
 * @since 2019/2/20
 */
public class MybatisAccess {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String COUNT = "___count___";

    private SqlSessionFactory sqlSessionFactory;

    public MybatisAccess(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * mybatis列表数据查询
     *
     * @param id     mybatis sqlMapId 全路径
     * @param param  查询参数
     * @param offset 起始数
     * @param size   查询数量
     * @param <T>    返回类型
     * @return
     */
    public <T> List<T> queryForList(String id, Object param, int offset, int size) {
        StopWatch watch = new StopWatch();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(id, param, new RowBounds(offset, size));
        } finally {
            logger.debug("get, id={}, elapsedTime={}", id, watch.elapsedTime());
        }
    }

    /**
     * mybatis列表数据查询
     *
     * @param id  mybatis sqlMapId 全路径
     * @param <T> 返回类型
     * @return
     */
    public <T> List<T> queryForList(String id) {
        StopWatch watch = new StopWatch();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(id);
        } finally {
            logger.debug("get, id={}, elapsedTime={}", id, watch.elapsedTime());
        }
    }

    public List<Map<String, Object>> queryForMap(String id) {
        StopWatch watch = new StopWatch();
        List<Map<String, Object>> mapList = new ArrayList<>();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Object> result = session.selectList(id);
            if (CollectionUtils.isEmpty(result)) {
                return new ArrayList<>();
            }
            mapList = result.stream().filter(o -> o != null).map(o -> {
                try {
                    return objectToMap(o);
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                    logger.error("objectToMap error:{}", e);
                }
                return null;
            }).collect(Collectors.toList());
        } finally {
            logger.debug("get, id={}, elapsedTime={}", id, watch.elapsedTime());
        }
        return new ArrayList<>();
    }

    private Map<String, Object> objectToMap(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }

    /**
     * mybatis列表总数查询
     *
     * @param id    mybatis sqlMapId 全路径
     * @param param 查询参数
     * @return
     */
    public int queryForTotal(String id, Object param) {
        return queryTotal(id, param).intValue();
    }

    /**
     * mybatis列表总数查询
     *
     * @param id    mybatis sqlMapId 全路径
     * @param param 查询参数
     * @return
     */
    private Long queryTotal(String id, Object param) {
        StopWatch watch = new StopWatch();
        String countStatementId = id + COUNT; //组装总数查询语句 CountStatement
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(countStatementId, param);
        } finally {
            logger.debug("get, countId={}, elapsedTime={}", countStatementId, watch.elapsedTime());
        }
    }
}
