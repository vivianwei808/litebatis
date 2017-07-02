package org.wing4j.litebatis.executor.resultset;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.exception.ExecutorException;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.result.DefaultResultContext;
import org.wing4j.litebatis.executor.result.DefaultResultHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.mapping.ResultMap;
import org.wing4j.litebatis.mapping.ResultMapping;
import org.wing4j.litebatis.reflection.*;
import org.wing4j.litebatis.session.AutoMappingBehavior;
import org.wing4j.litebatis.session.ResultContext;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;
import org.wing4j.litebatis.type.TypeHandler;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.lang.reflect.Constructor;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by wing4j on 2017/6/17.
 */
public class SimpleResultSetHandler implements ResultSetHandler {
    private static final Object DEFERED = new Object();
    private final Executor executor;
    private final Configuration configuration;
    private final MappedStatement mappedStatement;
    private final RowBounds rowBounds;
    private final ParameterHandler parameterHandler;
    private final ResultHandler<?> resultHandler;
    private final BoundSql boundSql;
    private final ObjectFactory objectFactory;
    private final ReflectorFactory reflectorFactory;
    private final TypeHandlerRegistry typeHandlerRegistry;
    private final Map<String, ResultMapping> nextResultMaps = new HashMap<String, ResultMapping>();

    public SimpleResultSetHandler(Executor executor,
                                  MappedStatement mappedStatement,
                                  ParameterHandler parameterHandler,
                                  ResultHandler<?> resultHandler,
                                  BoundSql boundSql,
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
        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    }

    /**
     * 处理简单的结果集，互联网应用不使用储存过程，不会出现多结果集问题
     *
     * @param stmt JDBC语句对象
     * @param <E>
     * @return
     * @throws SQLException
     */
    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        final List<E> resultList = new ArrayList();
        //根据JDBC结果集包装为ResultSetWrapper
        ResultSetWrapper rsw = getFirstResultSet(stmt);
        //一个映射的语句对象可能对应多个结果映射
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        int resultMapCount = resultMaps.size();
        //校验结果映射至少要存在一个
        validateResultMapsCount(rsw, resultMapCount);
        //根据映射进行结果集处理，将结果放入resultList
        handleResultSet(rsw, resultMaps.get(0), resultList);
        return resultList.size() == 1 ? (List<E>) resultList.get(0) : resultList;
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
            // move forward to get the first resultSet in case the driver
            // doesn't return the resultSet as the first result (HSQLDB 2.1)
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

    /**
     * 进行结果集处理
     *
     * @param rsw
     * @param resultMap
     * @param resultList
     * @throws SQLException
     */
    void handleResultSet(ResultSetWrapper rsw, ResultMap resultMap, List resultList) throws SQLException {
        try {
            //结果处理器如果为空，则新建；如果不为空，则直接处理
            if (resultHandler == null) {
                DefaultResultHandler defaultResultHandler = new DefaultResultHandler(objectFactory);
                handleRowValuesForSimpleResultMap(rsw, resultMap, defaultResultHandler, rowBounds);
                resultList.add(defaultResultHandler.getResultList());
            } else {
                handleRowValuesForSimpleResultMap(rsw, resultMap, resultHandler, rowBounds);
            }
        } finally {
            // issue #228 (close resultsets)
            closeResultSet(rsw.getResultSet());
        }
    }

    void handleRowValuesForSimpleResultMap(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler resultHandler, RowBounds rowBounds) throws SQLException {
        DefaultResultContext<Object> resultContext = new DefaultResultContext();
        //逻辑上跳过指定的记录
        skipRows(rsw.getResultSet(), rowBounds);
        //如果没有超过逻辑行数，循环遍历结果行
        while (shouldProcessMoreRows(resultContext, rowBounds) && rsw.next()) {
            //获取一条记录
            Object rowValue = getRowValue(rsw, resultMap);
            //将行记录转换为结果映射的对象
            storeObject(resultHandler, resultContext, rowValue, rsw.getResultSet());
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

    /**
     * 上下文未停止，且上下问的结果条数未超过行限制对象
     *
     * @param context
     * @param rowBounds
     * @return
     * @throws SQLException
     */
    boolean shouldProcessMoreRows(ResultContext<?> context, RowBounds rowBounds) throws SQLException {
        return !context.isStopped() && context.getResultCount() < rowBounds.getLimit();
    }

    Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap) throws SQLException {
        Object resultObject = createResultObject(rsw, resultMap, null);
        if (resultObject != null && !typeHandlerRegistry.hasTypeHandler(resultMap.getType())) {
            final MetaObject metaObject = configuration.newMetaObject(resultObject);
            boolean foundValues = !resultMap.getConstructorResultMappings().isEmpty();
            //如果可以进行自动映射
            if (shouldApplyAutomaticMappings(resultMap, false)) {
//                foundValues = applyAutomaticMappings(rsw, resultMap, metaObject, null) || foundValues;
            }
            //进行映射
            foundValues = applyPropertyMappings(rsw, resultMap, metaObject, null) || foundValues;
            resultObject = foundValues ? resultObject : null;
            return resultObject;
        }
        return resultObject;
    }

    private boolean applyPropertyMappings(ResultSetWrapper rsw, ResultMap resultMap, MetaObject metaObject, String columnPrefix)
            throws SQLException {
        final List<String> mappedColumnNames = rsw.getMappedColumnNames(resultMap, columnPrefix);
        boolean foundValues = false;
        final List<ResultMapping> propertyMappings = resultMap.getPropertyResultMappings();
        for (ResultMapping propertyMapping : propertyMappings) {
            String column = prependPrefix(propertyMapping.getColumn(), columnPrefix);
            if (propertyMapping.getNestedResultMapId() != null) {
                // the user added a column attribute to a nested result map, ignore it
                column = null;
            }
            if (propertyMapping.isCompositeResult()
                    || (column != null && mappedColumnNames.contains(column.toUpperCase(Locale.ENGLISH)))
                    || propertyMapping.getResultSet() != null) {
                Object value = getPropertyMappingValue(rsw.getResultSet(), metaObject, propertyMapping, columnPrefix);
                // issue #541 make property optional
                final String property = propertyMapping.getProperty();
                // issue #377, call setter on nulls
                if (value != DEFERED
                        && property != null
                        && (value != null || (configuration.isCallSettersOnNulls() && !metaObject.getSetterType(property).isPrimitive()))) {
                    metaObject.setValue(property, value);
                }
                if (value != null || value == DEFERED) {
                    foundValues = true;
                }
            }
        }
        return foundValues;
    }

    private Object getPropertyMappingValue(ResultSet rs, MetaObject metaResultObject, ResultMapping propertyMapping, String columnPrefix)
            throws SQLException {
        final TypeHandler<?> typeHandler = propertyMapping.getTypeHandler();
        final String column = prependPrefix(propertyMapping.getColumn(), columnPrefix);
        return typeHandler.getResult(rs, column);
    }

    private Object createResultObject(ResultSetWrapper rsw, ResultMap resultMap, String columnPrefix) throws SQLException {
        final List<Class<?>> constructorArgTypes = new ArrayList();
        final List<Object> constructorArgs = new ArrayList();
        Object resultObject = createResultObject(rsw, resultMap, constructorArgTypes, constructorArgs, columnPrefix);
        return resultObject;
    }

    private Object createResultObject(ResultSetWrapper rsw, ResultMap resultMap, List<Class<?>> constructorArgTypes, List<Object> constructorArgs, String columnPrefix)
            throws SQLException {
        final Class<?> resultType = resultMap.getType();
        final MetaClass metaType = DefaultMetaClass.forClass(resultType, reflectorFactory);
        final List<ResultMapping> constructorMappings = resultMap.getConstructorResultMappings();
        if (typeHandlerRegistry.hasTypeHandler(resultType)) {
            return createPrimitiveResultObject(rsw, resultMap, columnPrefix);
        } else if (!constructorMappings.isEmpty()) {
            return createParameterizedResultObject(rsw, resultType, constructorMappings, constructorArgTypes, constructorArgs, columnPrefix);
        } else if (resultType.isInterface() || metaType.hasDefaultConstructor()) {
            return objectFactory.create(resultType);
        } else if (shouldApplyAutomaticMappings(resultMap, false)) {
            return createByConstructorSignature(rsw, resultType, constructorArgTypes, constructorArgs, columnPrefix);
        }
        throw new ExecutorException("Do not know how to create an instance of " + resultType);
    }

    private boolean shouldApplyAutomaticMappings(ResultMap resultMap, boolean isNested) {
        if (resultMap.getAutoMapping() != null) {
            return resultMap.getAutoMapping();
        } else {
            if (isNested) {
                return AutoMappingBehavior.FULL == configuration.getAutoMappingBehavior();
            } else {
                return AutoMappingBehavior.NONE != configuration.getAutoMappingBehavior();
            }
        }
    }

    private Object createByConstructorSignature(ResultSetWrapper rsw, Class<?> resultType, List<Class<?>> constructorArgTypes, List<Object> constructorArgs,
                                                String columnPrefix) throws SQLException {
        for (Constructor<?> constructor : resultType.getDeclaredConstructors()) {
            if (typeNames(constructor.getParameterTypes()).equals(rsw.getClassNames())) {
                boolean foundValues = false;
                for (int i = 0; i < constructor.getParameterTypes().length; i++) {
                    Class<?> parameterType = constructor.getParameterTypes()[i];
                    String columnName = rsw.getColumnNames().get(i);
                    TypeHandler<?> typeHandler = rsw.getTypeHandler(parameterType, columnName);
                    Object value = typeHandler.getResult(rsw.getResultSet(), prependPrefix(columnName, columnPrefix));
                    constructorArgTypes.add(parameterType);
                    constructorArgs.add(value);
                    foundValues = value != null || foundValues;
                }
                return foundValues ? objectFactory.create(resultType, constructorArgTypes, constructorArgs) : null;
            }
        }
        throw new ExecutorException("No constructor found in " + resultType.getName() + " matching " + rsw.getClassNames());
    }

    private List<String> typeNames(Class<?>[] parameterTypes) {
        List<String> names = new ArrayList<String>();
        for (Class<?> type : parameterTypes) {
            names.add(type.getName());
        }
        return names;
    }

    private Object createPrimitiveResultObject(ResultSetWrapper rsw, ResultMap resultMap, String columnPrefix) throws SQLException {
        final Class<?> resultType = resultMap.getType();
        final String columnName;
        if (!resultMap.getResultMappings().isEmpty()) {
            final List<ResultMapping> resultMappingList = resultMap.getResultMappings();
            final ResultMapping mapping = resultMappingList.get(0);
            columnName = prependPrefix(mapping.getColumn(), columnPrefix);
        } else {
            columnName = rsw.getColumnNames().get(0);
        }
        final TypeHandler<?> typeHandler = rsw.getTypeHandler(resultType, columnName);
        return typeHandler.getResult(rsw.getResultSet(), columnName);
    }

    Object createParameterizedResultObject(ResultSetWrapper rsw, Class<?> resultType, List<ResultMapping> constructorMappings,
                                           List<Class<?>> constructorArgTypes, List<Object> constructorArgs, String columnPrefix) {
        boolean foundValues = false;
        for (ResultMapping constructorMapping : constructorMappings) {
            final Class<?> parameterType = constructorMapping.getJavaType();
            final String column = constructorMapping.getColumn();
            final Object value;
            try {
                final TypeHandler<?> typeHandler = constructorMapping.getTypeHandler();
                value = typeHandler.getResult(rsw.getResultSet(), prependPrefix(column, columnPrefix));
            } catch (SQLException e) {
                throw new ExecutorException("Could not process result for mapping: " + constructorMapping, e);
            }
            constructorArgTypes.add(parameterType);
            constructorArgs.add(value);
            foundValues = value != null || foundValues;
        }
        return foundValues ? objectFactory.create(resultType, constructorArgTypes, constructorArgs) : null;
    }

    private String prependPrefix(String columnName, String prefix) {
        if (columnName == null || columnName.length() == 0 || prefix == null || prefix.length() == 0) {
            return columnName;
        }
        return prefix + columnName;
    }

    void storeObject(ResultHandler<?> resultHandler, DefaultResultContext<Object> resultContext, Object rowValue, ResultSet rs) throws SQLException {
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
