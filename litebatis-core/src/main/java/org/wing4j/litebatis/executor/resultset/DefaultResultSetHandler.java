package org.wing4j.litebatis.executor.resultset;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.cache.CacheKey;
import org.wing4j.litebatis.exception.ExecutorException;
import org.wing4j.litebatis.executor.ErrorContext;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.result.DefaultResultContext;
import org.wing4j.litebatis.executor.result.DefaultResultHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.mapping.ResultMap;
import org.wing4j.litebatis.mapping.ResultMapping;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.ObjectFactory;
import org.wing4j.litebatis.reflection.ReflectorFactory;
import org.wing4j.litebatis.session.ResultContext;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wing4j on 2017/6/17.
 */
public class DefaultResultSetHandler implements ResultSetHandler {
    private final Executor executor;
    private final Configuration configuration;
    private final MappedStatement mappedStatement;
    private final RowBounds rowBounds;
    private final ParameterHandler parameterHandler;
    private final ResultHandler<?> resultHandler;
    private final BoundSql boundSql;
    private final ObjectFactory objectFactory;
    private final ReflectorFactory reflectorFactory;
    private final Map<String, ResultMapping> nextResultMaps = new HashMap<String, ResultMapping>();

    public DefaultResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql,
                                   RowBounds rowBounds) {
        this.executor = executor;
        this.configuration = mappedStatement.getConfiguration();
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.parameterHandler = parameterHandler;
        this.boundSql = boundSql;
        this.resultHandler = resultHandler;
        this.objectFactory = configuration.getObjectFactory();
        this.reflectorFactory = configuration.getReflectorFactory();
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        ErrorContext.instance().activity("handling results").object(mappedStatement.getId());

        final List<Object> multipleResults = new ArrayList<Object>();

        ResultSetWrapper rsw = getFirstResultSet(stmt);
        //一个映射的语句对象可能对应多个结果映射
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        int resultMapCount = resultMaps.size();
        //校验结果映射至少要存在一个
        validateResultMapsCount(rsw, resultMapCount);
        //尝试的结果集计数
        int resultSetCount = 0;
        while (rsw != null && resultMapCount > resultSetCount) {
            ResultMap resultMap = resultMaps.get(resultSetCount);
            //根据映射进行结果集处理，将结果放入multipleResults
            handleResultSet(rsw, resultMap, multipleResults, null);
            //获取下一个结果集进行包装
            rsw = getNextResultSet(stmt);
            //清理处理的数据
//            cleanUpAfterHandlingResultSet();
            resultSetCount++;
        }
        //获取映射的多个结果集名称
        String[] resultSets = mappedStatement.getResulSets();
        if (resultSets != null) {
            while (rsw != null && resultSetCount < resultSets.length) {
                //获得映射信息
                ResultMapping parentMapping = nextResultMaps.get(resultSets[resultSetCount]);
                if (parentMapping != null) {
                    String nestedResultMapId = parentMapping.getNestedResultMapId();
                    ResultMap resultMap = configuration.getResultMap(nestedResultMapId);
                    handleResultSet(rsw, resultMap, null, parentMapping);
                }
                rsw = getNextResultSet(stmt);
                cleanUpAfterHandlingResultSet();
                resultSetCount++;
            }
        }

        return (List<E>) collapseSingleResultList(multipleResults);
    }

    void cleanUpAfterHandlingResultSet() {
//        nestedResultObjects.clear();
//        ancestorColumnPrefix.clear();
    }

    List<Object> collapseSingleResultList(List<Object> multipleResults) {
        return multipleResults.size() == 1 ? (List<Object>) multipleResults.get(0) : multipleResults;
    }

    ResultSetWrapper getNextResultSet(Statement stmt) throws SQLException {
        // Making this method tolerant of bad JDBC drivers
        try {
            if (stmt.getConnection().getMetaData().supportsMultipleResultSets()) {
                // Crazy Standard JDBC way of determining if there are more results
                if (!((!stmt.getMoreResults()) && (stmt.getUpdateCount() == -1))) {
                    ResultSet rs = stmt.getResultSet();
                    return rs != null ? new ResultSetWrapper(rs, configuration) : null;
                }
            }
        } catch (Exception e) {
            // Intentionally ignored.
        }
        return null;
    }

    /**
     * 从JDBC语句对象获取第一个结果集
     *
     * @param stmt
     * @return
     * @throws SQLException
     */
    ResultSetWrapper getFirstResultSet(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            // move forward to get the first resultset in case the driver
            // doesn't return the resultset as the first result (HSQLDB 2.1)
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    // no more results. Must be no resultset
                    break;
                }
            }
        }
        return rs != null ? new ResultSetWrapper(rs, configuration) : null;
    }

    void validateResultMapsCount(ResultSetWrapper rsw, int resultMapCount) {
        if (rsw != null && resultMapCount < 1) {
            throw new ExecutorException("A query was run and no Result Maps were found for the Mapped Statement '" + mappedStatement.getId()
                    + "'.  It's likely that neither a Result Type nor a Result Map was specified.");
        }
    }

    void handleResultSet(ResultSetWrapper rsw, ResultMap resultMap, List<Object> multipleResults, ResultMapping parentMapping) throws SQLException {
        try {
            if (parentMapping != null) {
                handleRowValues(rsw, resultMap, null, RowBounds.DEFAULT, parentMapping);
            } else {
                if (resultHandler == null) {
                    DefaultResultHandler defaultResultHandler = new DefaultResultHandler(objectFactory);
                    handleRowValues(rsw, resultMap, defaultResultHandler, rowBounds, null);
                    multipleResults.add(defaultResultHandler.getResultList());
                } else {
                    handleRowValues(rsw, resultMap, resultHandler, rowBounds, null);
                }
            }
        } finally {
            // issue #228 (close resultsets)
            closeResultSet(rsw.getResultSet());
        }
    }

    void handleRowValues(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping) throws SQLException {
        if (resultMap.hasNestedResultMaps()) {
//            ensureNoRowBounds();
//            checkResultHandler();
//            handleRowValuesForNestedResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping);
        } else {
            handleRowValuesForSimpleResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping);
        }
    }

    void handleRowValuesForSimpleResultMap(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping)
            throws SQLException {
        DefaultResultContext<Object> resultContext = new DefaultResultContext<Object>();
        //逻辑上跳过指定的记录
        skipRows(rsw.getResultSet(), rowBounds);
        //如果没有超过逻辑行数
        while (shouldProcessMoreRows(resultContext, rowBounds) && rsw.getResultSet().next()) {
            ResultMap discriminatedResultMap = resolveDiscriminatedResultMap(rsw.getResultSet(), resultMap, null);
            Object rowValue = getRowValue(rsw, discriminatedResultMap);
            storeObject(resultHandler, resultContext, rowValue, parentMapping, rsw.getResultSet());
        }
    }

    void skipRows(ResultSet rs, RowBounds rowBounds) throws SQLException {
        if (rs.getType() != ResultSet.TYPE_FORWARD_ONLY) {
            if (rowBounds.getOffset() != RowBounds.NO_ROW_OFFSET) {
                rs.absolute(rowBounds.getOffset());
            }
        } else {
            for (int i = 0; i < rowBounds.getOffset(); i++) {
                rs.next();
            }
        }
    }

    boolean shouldProcessMoreRows(ResultContext<?> context, RowBounds rowBounds) throws SQLException {
        return !context.isStopped() && context.getResultCount() < rowBounds.getLimit();
    }

    ResultMap resolveDiscriminatedResultMap(ResultSet rs, ResultMap resultMap, String columnPrefix) throws SQLException {
//    Set<String> pastDiscriminators = new HashSet<String>();
//    Discriminator discriminator = resultMap.getDiscriminator();
//    while (discriminator != null) {
//      final Object value = getDiscriminatorValue(rs, discriminator, columnPrefix);
//      final String discriminatedMapId = discriminator.getMapIdFor(String.valueOf(value));
//      if (configuration.hasResultMap(discriminatedMapId)) {
//        resultMap = configuration.getResultMap(discriminatedMapId);
//        Discriminator lastDiscriminator = discriminator;
//        discriminator = resultMap.getDiscriminator();
//        if (discriminator == lastDiscriminator || !pastDiscriminators.add(discriminatedMapId)) {
//          break;
//        }
//      } else {
//        break;
//      }
//    }
        return resultMap;
    }

    Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap, CacheKey combinedKey, CacheKey absoluteKey, String columnPrefix, Object partialObject) throws SQLException {
        final String resultMapId = resultMap.getId();
        Object resultObject = partialObject;
        if (resultObject != null) {
            final MetaObject metaObject = configuration.newMetaObject(resultObject);
//      putAncestor(absoluteKey, resultObject, resultMapId, columnPrefix);
//      applyNestedResultMappings(rsw, resultMap, metaObject, columnPrefix, combinedKey, false);
//      ancestorObjects.remove(absoluteKey);
        }
//    else {
//      final ResultLoaderMap lazyLoader = new ResultLoaderMap();
//      resultObject = createResultObject(rsw, resultMap, lazyLoader, columnPrefix);
//      if (resultObject != null && !typeHandlerRegistry.hasTypeHandler(resultMap.getType())) {
//        final MetaObject metaObject = configuration.newMetaObject(resultObject);
//        boolean foundValues = !resultMap.getConstructorResultMappings().isEmpty();
//        if (shouldApplyAutomaticMappings(resultMap, true)) {
//          foundValues = applyAutomaticMappings(rsw, resultMap, metaObject, columnPrefix) || foundValues;
//        }
//        foundValues = applyPropertyMappings(rsw, resultMap, metaObject, lazyLoader, columnPrefix) || foundValues;
//        putAncestor(absoluteKey, resultObject, resultMapId, columnPrefix);
//        foundValues = applyNestedResultMappings(rsw, resultMap, metaObject, columnPrefix, combinedKey, true) || foundValues;
//        ancestorObjects.remove(absoluteKey);
//        foundValues = lazyLoader.size() > 0 || foundValues;
//        resultObject = foundValues ? resultObject : null;
//      }
//      if (combinedKey != CacheKey.NULL_CACHE_KEY) {
//        nestedResultObjects.put(combinedKey, resultObject);
//      }
//    }
        return resultObject;
    }

    Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap) throws SQLException {
//        Object resultObject = createResultObject(rsw, resultMap, lazyLoader, null);
//        if (resultObject != null && !typeHandlerRegistry.hasTypeHandler(resultMap.getType())) {
//            final MetaObject metaObject = configuration.newMetaObject(resultObject);
//            boolean foundValues = !resultMap.getConstructorResultMappings().isEmpty();
//            //如果可以进行自动映射
//            if (shouldApplyAutomaticMappings(resultMap, false)) {
//                foundValues = applyAutomaticMappings(rsw, resultMap, metaObject, null) || foundValues;
//            }
//            //进行映射
//            foundValues = applyPropertyMappings(rsw, resultMap, metaObject, lazyLoader, null) || foundValues;
//            foundValues = lazyLoader.size() > 0 || foundValues;
//            resultObject = foundValues ? resultObject : null;
//            return resultObject;
//        }
        return null;
    }

    void storeObject(ResultHandler<?> resultHandler, DefaultResultContext<Object> resultContext, Object rowValue, ResultMapping parentMapping, ResultSet rs) throws SQLException {
        if (parentMapping != null) {
//            linkToParents(rs, parentMapping, rowValue);
        } else {
            callResultHandler(resultHandler, resultContext, rowValue);
        }
    }

    void callResultHandler(ResultHandler<?> resultHandler, DefaultResultContext<Object> resultContext, Object rowValue) {
        resultContext.nextResultObject(rowValue);
        ((ResultHandler<Object>) resultHandler).handleResult(resultContext);
    }

    void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    @Override
    public void handleOutputParameters(CallableStatement cs) throws SQLException {

    }
}
