package com.atguigu.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

public interface CustomerMapper {
	/*
	@SelectKey(before=true,keyProperty="id",key="id",statement="select crm_cust_seq.nextval from dual",resultType=Long.class)
	@Insert("insert into customers(id,name,no,state) values(#{id},#{custName},#{no},#{state})")
	public void insert(Map<String,Object> map);*/
	
	public List<Customer> getContent(Map<String,Object> map);
	
	public long getTotalElements(Map<String,Object> params);
	
	
	@Select(value="select item from dicts where type= #{str}")
	public List<Object> getRegions(String str);

	@Select(value="select item from dicts where type= #{str}")
	public List<Object>  getLevels(String str);
	
	@Select(value="select item from dicts where type= #{str}")
	public List<Object>  getCredits(String str);
	
	@Select(value="select item from dicts where type= #{str}")
	public List<Object>  getSatifys(String str);
	
	public Customer getCustomerById(Integer id);
	
	@Select(value="select * from contacts ")
	public List<Contact> getManagers();
	
	@Update(value="update customers c "
		+" set c.zip=#{zip,jdbcType=VARCHAR},"
		+"  c.tel=#{tel,jdbcType=VARCHAR},"
		+"  c.fax=#{fax,jdbcType=VARCHAR},"
		+"  c.bank=#{bank,jdbcType=VARCHAR},"
		+"  c.name=#{name,jdbcType=VARCHAR},"
		+"  c.chief=#{chief,jdbcType=VARCHAR},"
		+"  c.state=#{state,jdbcType=VARCHAR},"
		+"  c.credit=#{credit,jdbcType=VARCHAR},"
		+"  c.websit=#{websit,jdbcType=VARCHAR},"
		+"  c.region=#{region,jdbcType=VARCHAR},"
		+"  c.satify=#{satify,jdbcType=VARCHAR},"
		+"  c.address=#{address,jdbcType=VARCHAR},"
		+"  c.turnover=#{turnover,jdbcType=DECIMAL}, "
		+"  c.bankroll=#{bankroll,jdbcType=DECIMAL}, "
		+"  c.bank_account=#{bankAccount,jdbcType=VARCHAR}, "
		+"  c.customer_level=#{level,jdbcType=VARCHAR},"
		+"  c.licence_no=#{licenceNo,jdbcType=VARCHAR},"
		+"  c.manager_id=#{manager.id,jdbcType=DECIMAL},"
		+"  c.local_tax_no=#{localTaxNo,jdbcType=VARCHAR},"
		+"  c.national_tax_no=#{nationalTaxNo,jdbcType=VARCHAR}"
		+" where id=#{id,jdbcType=DECIMAL}")
	public void saveCoustomer(Customer customer);
	@Update(value="update customers set state = '删除' where id=#{id}")
	public void delete(Integer id);
	
}
