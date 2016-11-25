/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.zwpc.dao;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.jspc.zwpc.model.EvalItem;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;

/**
 * 评测项dao
 * @author cuixubin
 */
@Component("evalItemDao")
public class EvalItemDao extends BaseDao {
    /**
     * 注入数据源
     *
     * @param dataSource 数据源对象
     */
    @Resource(name = "defaultDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    
    /**
     * 增加一条评测信息记录
     * @param item
     * @throws Exception 
     */
    public int insertItem(EvalItem item) throws Exception {
        StringBuffer sql = new StringBuffer();
        StringBuilder into = new StringBuilder();
        StringBuilder values = new StringBuilder();
        insertSql(into, values, EvalItem.class);
        sql.append("insert into jspc_evalitem (").append(into.toString()).append(") ");
        sql.append("values (").append(values.toString()).append(")"); 
        return this.updateObject(sql.toString(), item);
    }
    
    /**
     * 根据评测项id删除
     * @param itemId
     * @return
     * @throws Exception 
     */
    public int deleteItem(String itemId) throws Exception {
        String sql = "delete from jspc_evalitem where evalItem_id = ?";
        return this.update(sql, itemId);
    }
    
    /**
     * 根据评测项id获取记录
     * @param itemId
     * @return
     * @throws Exception 
     */
    public EvalItem getEvalItemById(String itemId) throws Exception {
        String sql = "select e.* from jspc_evalitem e where evalItem_id = ?";
        return this.queryForObject(sql, new ClassRowMapper<EvalItem>(EvalItem.class), itemId);
    }
    
    /**
     * 更新评测项信息
     * @param item
     * @return
     * @throws Exception 
     */
    public int updateEvalItem(EvalItem item) throws Exception {
        StringBuilder sql = new StringBuilder();
        StringBuilder str = new StringBuilder();
        updateSql(str,EvalItem.class,item);
        sql.append("update jspc_evalitem set ").append(str);
        
        sql.append("where evalItem_id = ${evalItem_id }");
        return this.updateObject(sql.toString(), item);
    }
    
    /**
     * 根据评审记录id获取其所有评测项记录
     * @param evalId 评审id
     * @return 评测项信息记录集合
     */
    public List<EvalItem> getAllByExpId(String evalId) {
        StringBuffer sql = new StringBuffer();
        RowMapper<EvalItem> rm = ParameterizedBeanPropertyRowMapper.newInstance(EvalItem.class);
        sql.append("select et.*, uc.user_name creator_name, ue.user_name editor_name ");
        sql.append("from jspc_evalitem et ");
        sql.append("left join core_user uc on et.creator=uc.user_id ");
	sql.append("left join core_user ue on et.editor=ue.user_id ");
	sql.append("where et.evalResult_id = ? ");
        return this.query(sql.toString(), rm, evalId);
    }
    
    /**
     * 根据评审记录id和评测项类型获取其所有评测项记录
     * @param expId 评审id
     * @param itemType 评测项类型
     * @return 评测项信息记录集合
     */
    public List<EvalItem> getByExpIdAndItemType(String expId, String itemType) {
        StringBuffer sql = new StringBuffer();
        RowMapper<EvalItem> rm = ParameterizedBeanPropertyRowMapper.newInstance(EvalItem.class);
        sql.append("select et.*, uc.user_name creator_name, ue.user_name editor_name ");
        sql.append("from jspc_evalitem et ");
        sql.append("left join core_user uc on et.creator=uc.user_id ");
	sql.append("left join core_user ue on et.editor=ue.user_id ");
	sql.append("where et.evalResult_id = ? and et.type = ? order by et.createDate");
        return this.query(sql.toString(), rm, expId, itemType);
    }
    
    /**
     * 根据用户id获取其所有评测项记录
     * @param userId 用户id
     * @return 评测项信息记录集合
     */
    public List<EvalItem> getAllByUserId(String userId) {
        StringBuffer sql = new StringBuffer();
        RowMapper<EvalItem> rm = ParameterizedBeanPropertyRowMapper.newInstance(EvalItem.class);
        sql.append("select et.*, uc.user_name creator_name, ue.user_name editor_name ");
        sql.append("from jspc_evalitem et ");
        sql.append("left join core_user uc on et.creator=uc.user_id ");
	sql.append("left join core_user ue on et.editor=ue.user_id ");
	sql.append("where et.creator = ? ");
        return this.query(sql.toString(), rm, userId);
    }
}
