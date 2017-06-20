package org.wing4j.litebatis.executor.resultset;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.executor.ErrorContext;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.mapping.ResultMap;
import org.wing4j.litebatis.mapping.ResultMapping;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public DefaultResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql,
                                   RowBounds rowBounds) {
        this.executor = executor;
        this.configuration = mappedStatement.getConfiguration();
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.parameterHandler = parameterHandler;
        this.boundSql = boundSql;
        this.resultHandler = resultHandler;
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
            cleanUpAfterHandlingResultSet();
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

        return collapseSingleResultList(multipleResults);
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

    @Override
    public void handleOutputParameters(CallableStatement cs) throws SQLException {

    }
}
