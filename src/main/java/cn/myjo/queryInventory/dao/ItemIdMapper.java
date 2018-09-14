/**   
 * @Title: package-info.java 
 * @Package cn.myjo.dao 
 * @Description: TODO 对数据库进行操作
 * @author 麦子
 * @date 2018年7月24日 下午1:27:56 
 * @version V1.0   
 */
package cn.myjo.queryInventory.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.EnumTypeHandler;

import cn.myjo.queryInventory.pojo.DaysBuf;
import cn.myjo.queryInventory.pojo.ItemSkuPO;
import cn.myjo.queryInventory.pojo.Memo;
import cn.myjo.queryInventory.pojo.vo.UpdateFlagVO;

public interface ItemIdMapper{
	//**************************************************************************************************
	//										人工添加临时
	//**************************************************************************************************
	//插入一条记录,字段为itemID,开始时间（当前时间）,结束时间,时间间隔     oc2_autoorder_daysbuf
	@Insert("REPLACE INTO oc2_autoorder_daysbuf(itemId,startDays,endDays,days) "
			+ "VALUES(#{itemId},(SELECT NOW()),"
			+ "(select date_add(NOW(), interval #{days} day)),#{days})")
	int insertItemId(DaysBuf daysBufDO);
	
	//查询所有的数据    oc2_autoorder_daysbuf
	@Select("SELECT itemId,startDays,endDays,days FROM oc2_autoorder_daysbuf")
	List<DaysBuf> queryAll();
	
	//根据结束时间删除item记录 
	@Delete("DELETE FROM oc2_autoorder_daysbuf WHERE endDays<(select CURRENT_DATE())")
	int deleteByEndDays();
	
	@Delete("DELETE FROM oc2_autoorder_daysbuf WHERE itemId=#{itemId}")
	int deleteDaysBufByItemId(String itemId);
	
	//根据终止时间查找订单编号
	@Select("SELECT * FROM oc2_autoorder_daysbuf WHERE endDays<CURRENT_DATE()")
	List<DaysBuf> selectItemIdByEndTime();
	//根据终止时间删除订单编号
	@Delete("DELETE FROM oc2_autoorder_daysbuf WHERE endDays<CURRENT_DATE()")
	void deleteItemIdByEndTime();
	//*****************************************************************************************************
	//										自动添加临时
	//**************************************************************************************************
	//查找所有数据 装在MemoDo
	@Select("SELECT * FROM oc2_plugin_memo order by performance_time desc limit 100")
	@Result(typeHandler=EnumTypeHandler.class)
	List<Memo>queryMemoAll();
	
	//根据订单编号改变旗帜颜色以及备注
	@Update("UPDATE oc2_plugin_memo SET memo=#{memo},flag=#{flag} WHERE orderId=#{orderId}")
	int updateDemo(UpdateFlagVO updateFlagVO);
	
	//查找ItemSku表中的数据
	//根据商家编码查找num_iid和Sku_id
	@Select("SELECT sku_id,num_id FROM oc2_sychro_itemsku WHERE outer_id=#{outer_id}")
	ItemSkuPO queryNumIidAndSkuId(String OuterId);

}