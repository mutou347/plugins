package cn.myjo.queryInventory.service;

import java.util.List;

import cn.myjo.queryInventory.pojo.DaysBuf;
import cn.myjo.queryInventory.pojo.Memo;
import cn.myjo.queryInventory.pojo.vo.UpdateFlagVO;

/**
 * 
 * @ClassName: ItemIdService 
 * @Description: TODO 处理商家编码的具体业务
 * @author 麦子
 * @date 2018年7月24日 下午1:03:56 
 *
 */
public interface ExtensionService {
	/**
	 * 
	 * @Title: updateTaoBaoItemId 
	 * @Description: TODO 更新淘宝ItemId,添加临时
	 * @param @param itemId	 要更新的淘宝id
	 * @param @param days	设置更新的天数====>多少天后转换回来
	 * @param @return  参数说明 
	 * @return boolean    返回类型 
	 * @throws
	 */
	void updateTaoBaoItemId(String itemId,int days);
	/**
	 * 
	 * @Title: getAll 
	 * @Description: TODO 得到添加的数据
	 * @param @return  参数说明 
	 * @return List<Map<String,Integer>>    返回类型 
	 * @throws
	 */
	List<DaysBuf> getAll();
	/**
	 * 
	 * @Title: deleteTaoBaoItemId 
	 * @Description: TODO 删除小于当天的订单记录
	 * @param   参数说明 
	 * @return void    返回类型 
	 * @throws
	 */
	void deleteTaoBaoItemId();
/**
 * 
 * @Title: updateDemo 
 * @Description: TODO(这里用一句话描述这个方法的作用) 修改旗子和备注
 * @param @param updateFlagVO
 * @param @return  参数说明 
 * @return boolean    返回类型 
 * @throws
 */
	boolean updateDemo(UpdateFlagVO updateFlagVO);
	/**
	 * 
	 * @Title: queryMemoAll 
	 * @Description: TODO 查询所有的机器改临时信息
	 * @param @return  参数说明 
	 * @return List<MemoDO>    返回类型 
	 * @throws
	 */
	List<Memo> queryMemoAll();

}
