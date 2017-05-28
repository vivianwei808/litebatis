package org.wing4j.litebatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wing4j on 2017/5/15.
 * 数据源工厂
 */
public interface DataSourceFactory {
    /**
     * 设置数据源需要的参数
     * @param props 参数
     */
    void setProperties(Properties props);

    /**
     * 获取数据源
     * @return 数据源
     */
    DataSource getDataSource();
}
