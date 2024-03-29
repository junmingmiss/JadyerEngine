package com.jadyer.engine.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jadyer.engine.web.user.User;

public class FastjsonTest {
	/**
	 * 测试Fastjson针对常见Java数据类型的序列化与反序列化
	 * @create 2015-5-28 上午1:53:59
	 * @author 玄玉<http://blog.csdn.net/jadyer>
	 */
	@Test
	public void dataTypeTest(){
		//List<Person> personList = JSON.parseArray(jsonString, Person.class);
		Test11 test11 = new Test11();
		test11.setName("铁面生");
		test11.setIsBuy("true");
		test11.setCurrentTime("2015-05-28 01:47:30");
		test11.setMoney("8000.58");
		String test11msg = JSON.toJSONString(test11);
		System.out.println("全部String类型的数据生成JSON为-->" + test11msg);
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		Test22 test22 = JSON.parseObject(test11msg, Test22.class);
		System.out.println("全部String类型生成的JSON解析为标准Java数据类型对象后的属性为" + ReflectionToStringBuilder.toString(test22, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		Test33 test33 = new Test33();
		test33.setName("铁面生");
		test33.setIsBuy(true);
		test33.setCurrentTime(new Date());
		test33.setMoney(new BigDecimal("8000.58"));
		String test33msg = JSON.toJSONString(test33);
		System.out.println("标准Java数据类型的数据生成JSON为-->" + test33msg);
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		Test22 test22again = JSON.parseObject(test33msg, Test22.class);
		System.out.println("标准Java数据类型生成的JSON解析为标准Java数据类型对象后的属性为" + ReflectionToStringBuilder.toString(test22again, ToStringStyle.MULTI_LINE_STYLE));
	}
	static class Test11{
		private String name;
		private String isBuy;
		private String money;
		private String currentTime;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIsBuy() {
			return isBuy;
		}
		public void setIsBuy(String isBuy) {
			this.isBuy = isBuy;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public String getCurrentTime() {
			return currentTime;
		}
		public void setCurrentTime(String currentTime) {
			this.currentTime = currentTime;
		}
	}
	static class Test22{
		private String name;
		private boolean isBuy;
		private BigDecimal money;
		private Date currentTime;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isBuy() {
			return isBuy;
		}
		public void setBuy(boolean isBuy) {
			this.isBuy = isBuy;
		}
		public BigDecimal getMoney() {
			return money;
		}
		public void setMoney(BigDecimal money) {
			this.money = money;
		}
		public Date getCurrentTime() {
			return currentTime;
		}
		public void setCurrentTime(Date currentTime) {
			this.currentTime = currentTime;
		}
	}
	static class Test33{
		private String name;
		private Boolean isBuy;
		private BigDecimal money;
		private Date currentTime;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Boolean getIsBuy() {
			return isBuy;
		}
		public void setIsBuy(Boolean isBuy) {
			this.isBuy = isBuy;
		}
		public BigDecimal getMoney() {
			return money;
		}
		public void setMoney(BigDecimal money) {
			this.money = money;
		}
		public Date getCurrentTime() {
			return currentTime;
		}
		public void setCurrentTime(Date currentTime) {
			this.currentTime = currentTime;
		}
	}


	/**
	 * java.util.List<String>和List<Map<String,Object>>对象的生成与解析JSON
	 * @create 2015-7-6 下午3:16:14
	 * @author 玄玉<http://blog.csdn.net/jadyer>
	 */
	@Test
	public void listStringTest(){
		List<String> dataList = new ArrayList<String>();
		dataList.add("铁面生");
		dataList.add("汪藏海");
		dataList.add("解连环");
		String jsonData = JSON.toJSONString(dataList);
		System.out.println("生成JSON-->" + jsonData);
		List<String> list = JSON.parseObject(jsonData, new TypeReference<List<String>>(){});
		for(int i=0,len=list.size(); i<len; i++){
			System.out.println("解析到["+len+"]个字符串,第["+(i+1)+"]个字符串为-->" + list.get(i));
		}
		System.out.println("-------------------------------------------------------------------");
		Map<String, Object> map11 = new HashMap<String, Object>();
		map11.put("鬼吹灯", "张佛爷");
		map11.put("藏海花", "张起灵");
		Map<String, Object> map22 = new HashMap<String, Object>();
		map22.put("青蚨门人", 1);
		map22.put("国术通神", 2);
		Map<String, Object> map33 = new HashMap<String, Object>();
		map33.put("盗墓笔记", "铁面生");
		map33.put("三国演义", "曹孟德");
		List<Map<String,Object>> datalist = new ArrayList<Map<String,Object>>();
		datalist.add(map11);
		datalist.add(map22);
		datalist.add(map33);
		jsonData = JSON.toJSONString(datalist);
		System.out.println("生成JSON-->" + jsonData);
		List<Map<String,Object>> list22 = JSON.parseObject(jsonData, new TypeReference<List<Map<String,Object>>>(){});
		for(int i=0,len=list22.size(); i<len; i++){
			System.out.println("解析到["+len+"]个对象,第["+(i+1)+"]个对象属性如下" + ReflectionToStringBuilder.toString(list22.get(i), ToStringStyle.MULTI_LINE_STYLE));
		}
	}


	/**
	 * java.util.List<JavaBean>对象的生成与解析JSON
	 * @create 2015-7-6 下午3:05:03
	 * @author 玄玉<http://blog.csdn.net/jadyer>
	 */
	@Test
	public void listJavaBeanTest(){
		User user11 = new User();
		user11.setId(11);
		user11.setUsername("铁面生");
		user11.setPassword("02200059");
		User user22 = new User();
		user22.setId(22);
		user22.setUsername("汪藏海");
		user22.setPassword("02200060");
		User user33 = new User();
		user33.setId(33);
		user33.setUsername("解连环");
		user33.setPassword("02200061");
		List<User> dataList = new ArrayList<User>();
		dataList.add(user11);
		dataList.add(user22);
		dataList.add(user33);
		String jsonData = JSON.toJSONString(dataList);
		System.out.println("生成JSON-->" + jsonData);
		//也可采用下面的方式解析List<User> list = JSON.parseArray(jsonData, User.class);
		List<User> list = JSON.parseObject(jsonData, new TypeReference<List<User>>(){});
		for(int i=0,len=list.size(); i<len; i++){
			System.out.println("解析到["+len+"]个对象,第["+(i+1)+"]个对象属性如下" + ReflectionToStringBuilder.toString(list.get(i), ToStringStyle.MULTI_LINE_STYLE));
		}
	}
	

	/**
	 * java.util.Map<String,List<Object>>对象的生成与解析JSON
	 * @create 2015-7-6 下午3:46:57
	 * @author 玄玉<http://blog.csdn.net/jadyer>
	 */
	@Test
	public void mapTest(){
		User user11 = new User();
		user11.setId(11);
		user11.setUsername("铁面生");
		user11.setPassword("02200059");
		User user22 = new User();
		user22.setId(22);
		user22.setUsername("汪藏海");
		user22.setPassword("02200060");
		User user33 = new User();
		user33.setId(33);
		user33.setUsername("解连环");
		user33.setPassword("02200061");
		List<User> dataList = new ArrayList<User>();
		dataList.add(user11);
		dataList.add(user22);
		dataList.add(user33);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("comment", "这是测试java.util.Map<String,List<Object>>对象的生成与解析JSON");
		dataMap.put("data", dataList);
		String jsonData = JSON.toJSONString(dataMap);
		System.out.println("生成JSON-->" + jsonData);
		Map<String, String> map = JSON.parseObject(jsonData, new TypeReference<Map<String, String>>(){});
		System.out.println("解析到comment-->" + map.get("comment"));
		System.out.println("解析到data-->" + map.get("data"));
		List<User> list = JSON.parseObject(map.get("data"), new TypeReference<List<User>>(){});
		for(int i=0,len=list.size(); i<len; i++){
			System.out.println("解析到["+len+"]个对象,第["+(i+1)+"]个对象属性如下" + ReflectionToStringBuilder.toString(list.get(i), ToStringStyle.MULTI_LINE_STYLE));
		}
	}
}