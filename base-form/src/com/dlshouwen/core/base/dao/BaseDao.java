package com.dlshouwen.core.base.dao;

import com.dlshouwen.core.base.Annotation.Attached;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.LogUtils;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.log.model.SqlLog;
import java.lang.reflect.Field;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
 * 数据操作对象的基类
 *
 * @author 大连首闻科技有限公司
 * @version 2013-7-22 15:20:43
 */
@SuppressWarnings({"deprecation"})
public class BaseDao extends JdbcTemplate {

    /**
     * 批量执行操作
     * <p>
     * 执行批量操作，定义SQL，设置缓冲池大小，定义占位符类别及参数列表即可批量执行，该SQL占位符使用英文半角问号：?
     *
     * @param prepareSql 预编译的SQL，支持?占位符
     * @param batchSize 缓冲池大小，即每达到多少条批量执行一次
     * @param dataType 占位符类别 {Types.VARCHAR}
     * @param argsList 参数列表，Object数组泛型
     * @throws Exception 抛出全部异常
     */
    public void batchExcute(String prepareSql, int batchSize, int[] dataType, List<Object[]> argsList) throws Exception {
        SqlLog sqlLog = new SqlLog(prepareSql.toString(), "大批量操作", "1", "batchExcute", "void");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(this.getDataSource(), prepareSql);
            batchSqlUpdate.setBatchSize(batchSize);
            batchSqlUpdate.setTypes(dataType);
            for (Object[] args : argsList) {
                batchSqlUpdate.update(args);
            }
            batchSqlUpdate.flush();
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (Exception exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            LogUtils.insertSqlLog(this, sqlLog);
        }
    }

    /**
     * 批量更新操作
     * <p>
     * 批量更新SQL语句
     *
     * @param sql 批量更新的SQL语句
     * @return 影响的记录条数数组
     * @throws DataAccessException 抛出数据处理异常
     */
    public int[] batchUpdate(String[] sql) throws DataAccessException {
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "batchUpdate", "int[]");
        int[] result = new int[]{};
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.batchUpdate(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 批量更新操作（包含参数）
     * <p>
     * 包含参数的批量更新，定义参数列表即可批量更新，该SQL占位符使用英文半角问号：?
     *
     * @param sql 批量更新的SQL语句
     * @param batchArgs 参数列表，Object数组泛型
     * @return 影响的记录条数数组
     * @throws DataAccessException 抛出数据处理异常
     */
    @Override
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {
        SqlLog sqlLog = new SqlLog(sql.toString(), batchArgs.toString(), "1", "batchUpdate", "int[]");
        int[] result = new int[]{};
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.batchUpdate(sql, batchArgs);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
     /**
     * 批量更新操作（包含参数）
     * <p>
     * 包含参数的批量更新，定义参数列表即可批量更新，该SQL占位符使用英文半角问号：?
     *
     * @param sql 批量更新的SQL语句
     * @param batchArgs 参数列表，Object数组泛型
     * @param ps
     * @return 影响的记录条数数组
     * @throws DataAccessException 抛出数据处理异常
     */
    public int[] batchUpdate(String sql, List batchArgs, BatchPreparedStatementSetter ps) throws DataAccessException {
        SqlLog sqlLog = new SqlLog(sql, batchArgs.toString(), "1", "batchUpdate", "int[]");
        int[] result = new int[]{};
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.batchUpdate(sql, ps);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 批量更新操作（包含参数）不记日志
     * <p>
     * 包含参数的批量更新，定义参数列表即可批量更新，该SQL占位符使用英文半角问号：?，此操作不记录数据操作日志
     *
     * @param sql 批量更新的SQL语句
     * @param batchArgs 参数列表，Object数组泛型
     * @return 影响的记录条数数组
     * @throws DataAccessException 抛出数据处理异常
     */
    public int[] batchUpdateNoLog(String sql, List<Object[]> batchArgs) throws DataAccessException {
        int[] result = new int[]{};
        try {
            result = super.batchUpdate(sql, batchArgs);
        } catch (DataAccessException exception) {
            throw exception;
        }
        return result;
    }

    /**
     * 执行操作
     * <p>
     * 执行SQL语句
     *
     * @param sql 需要执行的SQL语句
     * @throws DataAccessException 抛出数据处理异常
     */
    public void execute(String sql) throws DataAccessException {
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "execute", "void");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            super.execute(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 执行操作
     * <p>
     * 执行SQL语句不记录日志
     *
     * @param sql 需要执行的SQL语句
     * @throws DataAccessException 抛出数据处理异常
     */
    public void executeNoLog(String sql) throws DataAccessException {
        try {
            super.execute(sql);
        } catch (DataAccessException exception) {
            throw exception;
        } 
    }

    /**
     * 更新操作
     * <p>
     * 更新SQL语句
     *
     * @param sql 需要更新的SQL语句
     * @return 影响的记录条数
     * @throws DataAccessException 抛出数据处理异常
     */
    public int update(String sql) throws DataAccessException {
        int result = 0;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "update", "int");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.update(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 更新操作（包含参数列表）
     * <p>
     * 更新SQL语句，定义参数列表即可更新，该SQL占位符使用英文半角问号：?
     *
     * @param sql 需要更新的SQL语句
     * @param args 参数列表，可变参数
     * @return 影响的记录条数
     * @throws DataAccessException 抛出数据处理异常
     */
    public int update(String sql, Object... args) throws DataAccessException {
        int result = 0;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "update", "int");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.update(sql, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 更新操作（包含参数列表）（不记录日志）
     * <p>
     * 更新SQL语句，定义参数列表即可更新，该SQL占位符使用英文半角问号：?，此操作不记录数据操作日志
     *
     * @param sql 需要更新的SQL语句
     * @param args 参数列表，可变参数
     * @return 影响的记录条数
     * @throws DataAccessException 抛出数据处理异常
     */
    public int updateNoLog(String sql, Object... args) throws DataAccessException {
        int result = 0;
        try {
            result = super.update(sql, args);
        } catch (DataAccessException exception) {
            throw exception;
        } finally {
        }
        return result;
    }

    /**
     * 查询int类型数据
     * <p>
     * 查询int类型数据
     *
     * @param sql 查询的SQL
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public int queryForInt(String sql) throws DataAccessException {
        int result = 0;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForInt", "int");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForInt(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 查询int类型数据不记录日志
     * <p>
     * 查询int类型数据
     *
     * @param sql 查询的SQL
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public int queryForIntNoLog(String sql) throws DataAccessException {
        int result = 0;
        try {
            result = super.queryForInt(sql);
//			======= 执行结束 ======
        } catch (DataAccessException exception) {
            throw exception;
        }
        return result;
    }

    /**
     * 查询int类型数据（包含参数列表）
     * <p>
     * 查询int类型数据，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public int queryForInt(String sql, Object... args) throws DataAccessException {
        int result = 0;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForInt", "int");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForInt(sql, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询long类型数据
     * <p>
     * 查询long类型数据
     *
     * @param sql 查询的SQL
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public long queryForLong(String sql) throws DataAccessException {
        long result = 0;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForLong", "long");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForLong(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Long类型数据（包含参数列表）
     * <p>
     * 查询long类型数据，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public long queryForLong(String sql, Object... args) throws DataAccessException {
        long result = 0;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForLong", "long");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForLong(sql, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Map类型数据
     * <p>
     * 查询Map类型数据，仅限查询单行数据，若查询多行数据则会保存，未查出数据返回null
     *
     * @param sql 查询的SQL
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public Map<String, Object> queryForMap(String sql) throws DataAccessException {
        Map<String, Object> result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForMap", "Map<String, Object>");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForMap(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (EmptyResultDataAccessException exception) {
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            result = null;
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Map类型数据（包含参数列表）
     * <p>
     * 查询Map类型数据，仅限查询单行数据，若查询多行数据则会抛出异常，未查出数据返回null，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
        Map<String, Object> result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForMap", "Map<String, Object>");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForMap(sql, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (EmptyResultDataAccessException exception) {
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            result = null;
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Object类型数据
     * <p>
     * 查询Object类型数据，仅限查询单行数据，若查询多行数据则会抛出异常，未查出数据返回null，
     * 该查询仅支持Java基础对象，如Integer、String等类型
     *
     * @param sql 查询的SQL
     * @param requiredType 查询的泛型类型，不支持自定义对象
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        T result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForObject", requiredType.getName());
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForObject(sql, requiredType);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (EmptyResultDataAccessException exception) {
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            result = null;
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Object类型数据（包含参数列表）
     * <p>
     * 查询Object类型数据，仅限查询单行数据，若查询多行数据则会抛出异常，未查出数据返回null，
     * 该查询仅支持Java基础对象，如Integer、String等类型，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param requiredType 查询的泛型类型，不支持自定义对象
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
        T result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForObject", requiredType.getName());
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForObject(sql, requiredType, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (EmptyResultDataAccessException exception) {
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            result = null;
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Object类型数据，行映射泛型对象
     * <p>
     * 查询Object类型数据，仅限查询单行数据，若查询多行数据则会抛出异常，未查出数据返回null，
     * 该查询仅支持自定义对象，不支持Integer、String等基础类型
     *
     * @param sql 查询的SQL
     * @param rowMapper 对象映射，仅限自定义对象
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> T queryForObject(String sql, ClassRowMapper<T> rowMapper) throws DataAccessException {
        T result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForObject", rowMapper.getClazz().getName());
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForObject(sql, rowMapper);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (EmptyResultDataAccessException exception) {
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            result = null;
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询Object类型数据，行映射泛型对象（包含参数列表）
     * <p>
     * 查询Object类型数据，仅限查询单行数据，若查询多行数据则会抛出异常，未查出数据返回null，
     * 该查询仅支持自定义对象，不支持Integer、String等基础类型，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param rowMapper 对象映射，仅限自定义对象
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> T queryForObject(String sql, ClassRowMapper<T> rowMapper, Object... args) throws DataAccessException {
        T result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForObject", rowMapper.getClazz().getName());
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForObject(sql, rowMapper, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (EmptyResultDataAccessException exception) {
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            result = null;
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询List类型数据
     * <p>
     * 查询List类型数据，未查出数据返回null
     *
     * @param sql 查询的SQL
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        List<Map<String, Object>> result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForList", "List<Map<String, Object>>");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForList(sql);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询List类型数据（包含参数列表）
     * <p>
     * 查询List类型数据，未查出数据返回null，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
        List<Map<String, Object>> result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForList", "List<Map<String, Object>>");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.queryForList(sql, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询List类型数据，行映射泛型对象
     * <p>
     * 查询List类型数据，未查出数据返回null，支持对象映射，仅支持自定义对象， 不支持Integer、String等基础类型
     *
     * @param sql 查询的SQL
     * @param rowMapper 对象映射，仅支持自定义对象
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> List<T> queryForList(String sql, ClassRowMapper<T> rowMapper) throws DataAccessException {
        List<T> result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), "无参数", "1", "queryForList", "List<[" + rowMapper.getClazz().getName() + "]>");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.query(sql, new RowMapperResultSetExtractor<T>(rowMapper));
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询List类型数据，行映射泛型对象（包含参数列表）
     * <p>
     * 查询List类型数据，未查出数据返回null，支持对象映射，仅支持自定义对象，
     * 不支持Integer、String等基础类型，定义参数列表即可查询，该SQL占位符使用英文半角问号：?
     *
     * @param sql 查询的SQL
     * @param rowMapper 对象映射，仅支持自定义对象
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> List<T> queryForList(String sql, ClassRowMapper<T> rowMapper, Object... args) throws DataAccessException {
        List<T> result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "queryForList", "List<[" + rowMapper.getClazz().getName() + "]>");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = super.query(sql, rowMapper, args);
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询List类型数据，行映射泛型对象（包含参数列表）-无日志
     * <p>
     * 查询List类型数据，未查出数据返回null，支持对象映射，仅支持自定义对象，
     * 不支持Integer、String等基础类型，定义参数列表即可查询，该SQL占位符使用英文半角问号：?，此操作不记录数据操作日志
     *
     * @param sql 查询的SQL
     * @param rowMapper 对象映射，仅支持自定义对象
     * @param args 参数列表，可变参数
     * @return 查询结果
     * @throws DataAccessException 抛出数据处理异常
     */
    public <T> List<T> queryForListNoLog(String sql, ClassRowMapper<T> rowMapper, Object... args) throws DataAccessException {
        List<T> result = null;
        try {
            result = super.query(sql, rowMapper, args);
        } catch (DataAccessException exception) {
            throw exception;
        }
        return result;
    }

    /**
     * 更新Blob数据
     * <p>
     * 更新Blob对象数据，如果有插入的对象中包含Blob数据，则需要先执行insert方法插入基础数据， 然后通过该方法对数据中的Blob数据进行更新
     *
     * @param sql 更新的SQL
     * @param content Blob字节数组
     * @return 影响的记录条数
     * @throws DataAccessException 抛出数据处理异常
     */
    public int updateBlob(String sql, final byte[] content) throws DataAccessException {
        int result;
        SqlLog sqlLog = new SqlLog(sql.toString(), "Blob", "1", "updateBlob", "int");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            result = this.execute(sql, new AbstractLobCreatingPreparedStatementCallback(new DefaultLobHandler()) {
                protected void setValues(PreparedStatement ps, LobCreator lobCreator)
                        throws SQLException, DataAccessException {
                    lobCreator.setBlobAsBytes(ps, 1, content);
                }
            });
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取Blob数据
     * <p>
     * 获取Blob数据，返回Blob字节数组
     *
     * @param sql 获取的SQL
     * @param args 参数列表，可变参数
     * @return Blob字节数组
     * @throws DataAccessException 抛出数据处理异常
     */
    public byte[] getBlobByte(String sql, Object... args) throws DataAccessException {
        byte[] result = null;
        SqlLog sqlLog = new SqlLog(sql.toString(), getArgsString(args), "1", "getBlobByte", "Blob");
        try {
            sqlLog.setStart_time(new Timestamp(System.currentTimeMillis()));
//			======= 执行开始 ======
            final DefaultLobHandler oracleHander = new DefaultLobHandler();
            List<Object> list = super.query(sql, new RowMapper<Object>() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    byte[] blobByte = oracleHander.getBlobAsBytes(rs, 1);
                    return blobByte;
                }
            }, args);
            if (list != null && list.size() > 0) {
                result = (byte[]) list.get(0);
            }
//			======= 执行结束 ======
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
        } catch (DataAccessException exception) {
            sqlLog.setCall_result("0");
            sqlLog.setError_reason(exception.getMessage());
            sqlLog.setEnd_time(new Timestamp(System.currentTimeMillis()));
            throw exception;
        } finally {
            try {
                LogUtils.insertSqlLog(this, sqlLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 更新方法，可以直接更新一个对象（包含参数列表）
     * <p>
     * 更新对象，可更新自定义对象，传入需要更新的SQL语句及参数即可完成查询，
     * 参数格式为${*}，其中$符号及{}符号为固定，括号内字符标识需要引用对象的哪个属性，
     * 方法体中会通过反射自动匹配传入对象的对应属性，若匹配不到，则按顺序使用参数列表中的参数，
     * 需要保证最终生成的参数要同SQL中定义的参数个数一致，否则将抛出异常
     *
     * @param sql 需要执行操作的SQL
     * @param object 被执行的对象
     * @param args 参数列表，可变参数
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int updateObject(String sql, Object object, Object... args) throws Exception {
        return this.update(SqlUtils.getObjectSql(sql), SqlUtils.getObjectArgs(sql, object, args).toArray());
    }

    /**
     * 更新方法，可以直接更新一个对象（包含参数列表）-不记录日志
     * <p>
     * 更新对象，可更新自定义对象，传入需要更新的SQL语句及参数即可完成查询，
     * 参数格式为${*}，其中$符号及{}符号为固定，括号内字符标识需要引用对象的哪个属性，
     * 方法体中会通过反射自动匹配传入对象的对应属性，若匹配不到，则按顺序使用参数列表中的参数，
     * 需要保证最终生成的参数要同SQL中定义的参数个数一致，否则将抛出异常，此操作不记录数据操作日志
     *
     * @param sql 需要执行操作的SQL
     * @param object 被执行的对象
     * @param args 参数列表，可变参数
     * @return 影响的记录条数
     * @throws Exception 抛出全部异常
     */
    public int updateObjectNoLog(String sql, Object object, Object... args) throws Exception {
        return this.updateNoLog(SqlUtils.getObjectSql(sql), SqlUtils.getObjectArgs(sql, object, args).toArray());
    }

    /**
     * 获取上一次插入的序列
     * <p>
     * MySQL数据库特有方法，对于自动增加的列获取上一次插入的编号，出于数据库通用角度考虑，建议不使用此方法，
     * 使用GUID方式完成编号的定义，缺点是会增加数据库容载量
     *
     * @return 序列号
     * @throws Exception 抛出全部异常
     */
    public int getLastInsertId() throws Exception {
        return this.queryForInt("select last_insert_id()");
    }

//	====================================================
//	内部调取方法
//	====================================================
    /**
     * 获取参数列表映射为字符串
     * <p>
     * 该方法用于将参数列表转换为字符串，主要用于日志记录
     *
     * @param args 参数列表，可变参数
     * @return 字符串
     */
    public String getArgsString(Object... args) {
        StringBuffer result = new StringBuffer();
        for (Object arg : args) {
            result.append(arg == null ? "null" : arg.toString()).append(", ");
        }
        if (result.length() > 1) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }

    /**
     * 封装insert sql
     *
     * @param into insert into后的sql
     * @param values insert into values后的sql
     * @param obj 实体对象Class
     * @throws Exception
     */
    public void insertSql(StringBuilder into, StringBuilder values, Class obj) throws Exception {
        Field[] field = obj.getDeclaredFields();
        for (int j = 0; j < field.length; j++) {
            boolean flag = field[j].isAnnotationPresent(Attached.class);
            if(flag) {
                continue;
            }
            String name = field[j].getName();
            if (j == (field.length - 1)) {
                into.append(name);
                values.append("${").append(name).append("} ");
            } else {
                into.append(name).append(",");
                values.append("${").append(name).append("}").append(",");
            }
        }
        if(into.charAt(into.length()-1) == ',') {
            into = into.deleteCharAt(into.length()-1);
        }
        if(values.charAt(values.length()-1) == ',') {
            values = values.deleteCharAt(values.length()-1);
        }
    }

    /**
     * 更新sql
     *
     * @param setStr update table set后的sql
     * @param obj 实体对象Class
     * @throws Exception
     */
    public void updateSql(StringBuilder setStr, Class obj, Object object) throws Exception {
        Field[] field = obj.getDeclaredFields();
        for (int j = 0; j < field.length; j++) {
            boolean flag = field[j].isAnnotationPresent(Attached.class);
            if(flag) {
                continue;
            }
            String name = field[j].getName();
            field[j].setAccessible(true);
            Object value = field[j].get(object);
            if (j == (field.length - 1)) {
                if (value instanceof String) {
                    if (value == null || value.toString().trim().length() == 0) {
                        continue;
                    }
                } else if (value == null) {
                    continue;
                }
                setStr.append(name).append("=");
                setStr.append("${").append(name).append("} ");
            } else {
                if (value instanceof String) {
                    if (value == null || value.toString().trim().length() == 0) {
                        continue;
                    }
                } else if (value == null) {
                    continue;
                }
                setStr.append(name).append("=");
                setStr.append("${").append(name).append("}").append(",");
            }
        }
        if(setStr.charAt(setStr.length()-1) == ',') {
            setStr = setStr.deleteCharAt(setStr.length()-1);
        }
    }
}
