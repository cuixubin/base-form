package com.dlshouwen.core.mail.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.extra.grid.model.Pager;
import com.dlshouwen.core.base.extra.grid.utils.GridUtils;
import com.dlshouwen.core.base.extra.spring.mapper.ClassRowMapper;
import com.dlshouwen.core.base.utils.SqlUtils;
import com.dlshouwen.core.mail.model.Mail;

/**
 * 邮件
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03 198
 */
@Component("mailDao")
public class MailDao extends BaseDao {

	/**
	 * 注入数据源
	 * @param dataSource 数据源对象
	 */
	@Resource(name="defaultDataSource")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}

	/**
	 * 获取收件箱邮件列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param userId 用户编号
	 * @throws Exception 抛出全部异常
	 */
	public void getReceiveMailList(Pager pager, HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.mail_id, m.title, m.sender, u.user_name sender_name, m.send_time, m.send_status, ");
		sql.append("mr.receiver, r.user_name receiver_name, mr.receive_status from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join core_mail_receive mr on mr.mail_id=m.mail_id ");
		sql.append("left join core_user r on mr.receiver=r.user_id ");
		sql.append("where m.send_status not in ('0') and mr.receive_status in ('0', '1') and mr.receiver=? ");
		sql.append("order by m.send_time desc ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response, userId);
	}

	/**
	 * 获取发件箱邮件列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param userId 用户编号
	 * @throws Exception 抛出全部异常
	 */
	public void getSendMailList(Pager pager, HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.mail_id, m.title, m.sender, u.user_name sender_name, m.send_time, m.send_status, m.create_time, ");
		sql.append("mr.receiver_count, mr.receiver_name from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join (");
		sql.append("select mr.mail_id, substring_index(group_concat(u.user_name), ',', 1) receiver_name, count(mr.mail_id) receiver_count ");
		sql.append("from core_mail_receive mr left join core_user u on mr.receiver=u.user_id group by mail_id");
		sql.append(") mr on mr.mail_id=m.mail_id ");
		sql.append("where m.send_status in ('1') and m.sender=? ");
		sql.append("order by m.send_time desc ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response, userId);
	}

	/**
	 * 获取草稿箱邮件列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param userId 用户编号
	 * @throws Exception 抛出全部异常
	 */
	public void getDraftMailList(Pager pager, HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.mail_id, m.title, m.sender, u.user_name sender_name, m.send_time, m.send_status, m.create_time, ");
		sql.append("mr.receiver_count, mr.receiver_name from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join (");
		sql.append("select mr.mail_id, substring_index(group_concat(u.user_name), ',', 1) receiver_name, count(mr.mail_id) receiver_count ");
		sql.append("from core_mail_receive mr left join core_user u on mr.receiver=u.user_id group by mail_id");
		sql.append(") mr on mr.mail_id=m.mail_id ");
		sql.append("where m.send_status in ('0') and m.sender=? ");
		sql.append("order by m.send_time desc ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response, userId);
	}

	/**
	 * 获取已删除邮件列表
	 * @param pager 分页对象
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param userId 用户编号
	 * @throws Exception 抛出全部异常
	 */
	public void getDeleteMailList(Pager pager, HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		sql.append("select m.mail_id, m.title, m.send_time, m.create_time, 'send' delete_from ");
		sql.append("from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join core_mail_receive mr on mr.mail_id=m.mail_id ");
		sql.append("left join core_user r on mr.receiver=r.user_id ");
		sql.append("where m.send_status in ('2') and m.sender=? ");
		sql.append("union ");
		sql.append("select m.mail_id, m.title, m.send_time, m.create_time, 'receive' delete_from ");
		sql.append("from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join core_mail_receive mr on mr.mail_id=m.mail_id ");
		sql.append("left join core_user r on mr.receiver=r.user_id ");
		sql.append("where mr.receive_status in ('2') and mr.receiver=? ");
		sql.append(") t where 1=1 ");
		sql.append("order by t.send_time desc ");
		GridUtils.queryForGrid(this, sql.toString(), pager, request, response, userId, userId);
	}

	/**
	 * 获取邮件信息
	 * @param mailId 邮件编号
	 * @return 邮件信息
	 * @throws Exception 抛出全部异常
	 */
	public Map<String, Object> getMailInfoById(String mailId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.mail_id, m.title, m.content, m.sender, u.user_name sender_name, m.send_time, m.send_status, m.create_time, ");
		sql.append("mr.receiver, mr.receiver_name from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join (");
		sql.append("select mr.mail_id, group_concat(mr.receiver) receiver, group_concat(u.user_name) receiver_name, count(mr.mail_id) receiver_count ");
		sql.append("from core_mail_receive mr left join core_user u on mr.receiver=u.user_id group by mail_id");
		sql.append(") mr on mr.mail_id=m.mail_id ");
		sql.append("where m.mail_id=?");
		return this.queryForMap(sql.toString(), mailId);
	}

	/**
	 * 收件人已读
	 * @param mailId 邮件编号
	 * @param userId 用户编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int receiverViewMail(String mailId, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_mail_receive set receive_status='1' where mail_id=? and receiver=? and receive_status='0'");
		return this.update(sql.toString(), mailId, userId);
	}

	/**
	 * 获取邮件对象
	 * @param mailId 邮件编号
	 * @return 邮件对象
	 * @throws Exception 抛出全部异常
	 */
	public Mail getMailById(String mailId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select m.mail_id, m.title, m.content, m.sender, u.user_name sender_name, m.send_time, m.send_status, m.create_time, ");
		sql.append("mr.receiver, mr.receiver_name from core_mail m ");
		sql.append("left join core_user u on m.sender=u.user_id ");
		sql.append("left join (");
		sql.append("	select mr.mail_id, group_concat(mr.receiver) receiver, ");
		sql.append("	group_concat(u.user_name) receiver_name, count(mr.mail_id) receiver_count ");
		sql.append("	from core_mail_receive mr ");
		sql.append("	left join core_user u on mr.receiver=u.user_id ");
		sql.append("	group by mail_id ");
		sql.append(") mr on mr.mail_id=m.mail_id ");
		sql.append("where m.mail_id=?");
		return this.queryForObject(sql.toString(), new ClassRowMapper<Mail>(Mail.class), mailId);
	}

	/**
	 * 删除临时邮件
	 * @param mailId 邮件编号
	 * @throws Exception 抛出全部异常
	 */
	public void deleteTempMail(String mailId) throws Exception {
		StringBuffer sql_1 = new StringBuffer();
		sql_1.append("delete from core_mail where mail_id=?");
		this.update(sql_1.toString(), mailId);
		StringBuffer sql_2 = new StringBuffer();
		sql_2.append("delete from core_mail_receive where mail_id=?");
		this.update(sql_2.toString(), mailId);
	}

	/**
	 * 新增邮件
	 * @param mail 邮件对象
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertMail(Mail mail) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_mail (mail_id, title, content, send_status, sender, send_time, create_time) ");
		sql.append("values (${mail_id }, ${title }, ${content }, ${send_status }, ${sender }, ${send_time}, ${create_time })");
		return this.updateObject(sql.toString(), mail);
	}

	/**
	 * 新增邮件收件关系
	 * @param mailId 邮件编号
	 * @param receivers 收件人列表
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int insertMailReceivers(String mailId, String receivers) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into core_mail_receive (mail_id, receiver, receive_status) ");
		sql.append("select ?, u.user_id, '0' from core_user u where u.user_id in ("+SqlUtils.getArgsKey(receivers, ",")+")");
		List<Object> args = SqlUtils.getArgsValue(receivers, ",");
		args.add(0, mailId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 判断邮件是否来自草稿
	 * @param mailId 邮件编号
	 * @return 是否来自草稿
	 * @throws Exception 抛出全部异常
	 */
	public boolean getMailIsFromDraft(String mailId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from core_mail where mail_id=? and send_status='0'");
		return this.queryForInt(sql.toString(), mailId)>0?true:false;
	}

	/**
	 * 发送邮件
	 * @param mailId 邮件编号
	 * @param sendTime 发送时间
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int sendMail(String mailId, String sendTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_mail set send_status='1', send_time=? where mail_id=?");
		return this.update(sql.toString(), sendTime, mailId);
	}

	/**
	 * 发信人删除
	 * @param mailIds 邮件编号列表
	 * @param userId 用户编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteMailForSend(String mailIds, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_mail set send_status='2' where mail_id in ("+SqlUtils.getArgsKey(mailIds, ",")+") and sender=?");
		List<Object> args = SqlUtils.getArgsValue(mailIds, ",");
		args.add(userId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 收信人删除
	 * @param mailIds 邮件编号列表
	 * @param userId 用户编号
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteMailForReceive(String mailIds, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update core_mail_receive set receive_status='2' where mail_id in ("+SqlUtils.getArgsKey(mailIds, ",")+") and receiver=?");
		List<Object> args = SqlUtils.getArgsValue(mailIds, ",");
		args.add(userId);
		return this.update(sql.toString(), args.toArray());
	}

	/**
	 * 草稿删除
	 * @param mailIds 邮件编号列表
	 * @param userId 用户编号
	 * @throws Exception 抛出全部异常
	 */
	public void deleteMailForDraft(String mailIds, String userId) throws Exception {
		StringBuffer sql_1 = new StringBuffer();
		sql_1.append("delete from core_mail where mail_id in ("+SqlUtils.getArgsKey(mailIds, ",")+") and sender=? and send_status in ('0')");
		List<Object> args_1 = SqlUtils.getArgsValue(mailIds, ",");
		args_1.add(userId);
		this.update(sql_1.toString(), args_1.toArray());
		StringBuffer sql_2 = new StringBuffer();
		sql_2.append("delete from core_mail_receive where mail_id in ("+SqlUtils.getArgsKey(mailIds, ",")+")");
		List<Object> args_2 = SqlUtils.getArgsValue(mailIds, ",");
		this.update(sql_2.toString(), args_2.toArray());
	}

	/**
	 * 彻底删除
	 * @param mailId 邮件编号
	 * @param userId 用户编号
	 * @param deleteFrom 删除来源
	 * @return 影响的记录条数
	 * @throws Exception 抛出全部异常
	 */
	public int deleteMail(String mailId, String userId, String deleteFrom) throws Exception {
		StringBuffer sql = new StringBuffer();
		if("send".equals(deleteFrom))
			sql.append("update core_mail set send_status='3' where mail_id=? and sender=?");
		else
			sql.append("update core_mail_receive set receive_status='3' where mail_id=? and receiver=?");
		return this.update(sql.toString(), mailId, userId);
	}

}