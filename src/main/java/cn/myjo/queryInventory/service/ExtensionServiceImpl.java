package cn.myjo.queryInventory.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.myjo.queryInventory.API.TaoBaoHttpClint;
import cn.myjo.queryInventory.dao.ItemIdMapper;
import cn.myjo.queryInventory.pojo.DaysBuf;
import cn.myjo.queryInventory.pojo.ItemSkuPO;
import cn.myjo.queryInventory.pojo.Memo;
import cn.myjo.queryInventory.pojo.vo.UpdateFlagVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExtensionServiceImpl implements ExtensionService {
	
	@Autowired
	private ItemIdMapper itemIdMapper;
	//淘宝API调用接口
	@Autowired
	private TaoBaoHttpClint taobaoApi;
	@Override
	public void updateTaoBaoItemId(String itemId, int days) {
		log.info("人工修改ItemId增加临时: oldItemId[{}],持续时间:days[{}]",itemId,days);
		DaysBuf daysBufDO=new DaysBuf();
		//设置上过期时间
		daysBufDO.setDays(days);
		//根据商家编码查找出对应的num_iid和sku_iid(封装成一个实体类:ItemSkuDO)
		ItemSkuPO itemSkuDO = itemIdMapper.queryNumIidAndSkuId(itemId);
		//如果不包含临时,就添加上临时
		if(!itemId.contains("临时")) {
			itemId=new StringBuilder(itemId).append("临时").toString();
			daysBufDO.setItemId(itemId);
		}else {
			daysBufDO.setItemId(itemId);
		}
		//------------------------------------------------------------
		if(itemSkuDO!=null) {
			//得到修改商家编码需要的参数
			long sku_Id = Long.parseLong(itemSkuDO.getSku_id());
			long num_iid = Long.parseLong(itemSkuDO.getNum_id());
			//------------------------------------------------------------
			//修改商家编码,添加临时
			boolean tianMaoOuterIdUpdate = taobaoApi.TianMaoOuterIdUpdate(sku_Id, num_iid, itemId);
			if(tianMaoOuterIdUpdate) {
				log.info("添加临时成功!");
				log.info("将添加临时的数据添加到数据库:ItemId:{},nowTime:{}",itemId,LocalDateTime.now());
				//插入数据库
				int result = itemIdMapper.insertItemId(daysBufDO);
				if(result>0) {
					log.info("添加itemId:{}数据成功",itemId);
				}else {
					log.error("数据库数据添加失败:ItemId:{},nowTime:{}",itemId,LocalDateTime.now());
				}
			}
		}else {
			log.error("根据ItemId:{},无法查找出对应的numIId",itemId);
		}
	}
	@Override
	public List<DaysBuf> getAll() {
		log.info("开始查找所有的人工改临时的信息");
		List<DaysBuf> queryAll = itemIdMapper.queryAll();
		
		log.info("查找结束>>>人工改临时的个数为:[{}]",queryAll.size());
		return queryAll;
	}
	@Override
	public void deleteTaoBaoItemId() {
		itemIdMapper.deleteByEndDays();
	}
	
	@Override
	public boolean updateDemo(UpdateFlagVO updateFlagVO) {
		log.info("更改备注:order:[memo:{},flag:{}]!",updateFlagVO.getMemo(),updateFlagVO);
		int updateDemo = itemIdMapper.updateDemo(updateFlagVO);
		log.info("更改备注成功:order:updateDemo[{}]",updateDemo);
		return true;
	}
	@Override
	public List<Memo> queryMemoAll() {
		log.info("开始查找所有的机器改临时的信息");
		//查找所有机器下单的信息
		List<Memo> memoAll = itemIdMapper.queryMemoAll();
		log.info(memoAll.toString());
		log.info("查找结束>>>机器改临时信息个数为:{}",memoAll.size());
		return memoAll;
	}
	
}
