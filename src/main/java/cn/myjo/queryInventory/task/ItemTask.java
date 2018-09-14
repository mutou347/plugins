package cn.myjo.queryInventory.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.myjo.queryInventory.API.TaoBaoHttpClint;
import cn.myjo.queryInventory.dao.ItemIdMapper;
import cn.myjo.queryInventory.pojo.DaysBuf;
import cn.myjo.queryInventory.pojo.ItemSkuPO;
@Component
public class ItemTask {
	private Logger LOG=LoggerFactory.getLogger(ItemTask.class);
	@Autowired
	ItemIdMapper itemIdMapper;
	@Autowired
	TaoBaoHttpClint taoBaoHttpClint;
	//每隔三个小时执行一次检测
	 @Scheduled(cron = "0 0 */1 * * ?")
	public void delTask() {
		LOG.info("开始执行定时任务");
		//查找过期商家编码
		List<DaysBuf> selectItemIdByEndTime = itemIdMapper.selectItemIdByEndTime();
		//遍历过期临时商家编码,删除临时
		for(DaysBuf daysBufDO:selectItemIdByEndTime) {
			String itemId = daysBufDO.getItemId();
			if(itemId.endsWith("临时")) {
				itemId.substring(0,itemId.lastIndexOf("临时")-2);
			}
			//查找numiid和skuId .根据商家编码
			ItemSkuPO itemSkuDOResult = itemIdMapper.queryNumIidAndSkuId(itemId);
			if(itemSkuDOResult!=null) {
				Long sku_id = Long.valueOf(itemSkuDOResult.getSku_id());
				Long num_iid = Long.valueOf(itemSkuDOResult.getNum_id());
				//更新商家编码,去掉"临时"
				boolean tianMaoOuterIdUpdate = taoBaoHttpClint.TianMaoOuterIdUpdate(sku_id,num_iid,itemId);
				if(tianMaoOuterIdUpdate) {
					LOG.info("数据更新成功!");
					//根据更新成功的数据更新数据库
					int deleteDaysBufByItemId = itemIdMapper.deleteDaysBufByItemId(itemId);
					if(deleteDaysBufByItemId>0) {
						LOG.info("删除数据库中对应的商家编码数据:{}",itemId);
					}
				}
			}
		}
		itemIdMapper.deleteItemIdByEndTime();
		LOG.info("执行结束定时任务");
	}
}