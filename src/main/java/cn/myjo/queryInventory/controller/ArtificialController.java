package cn.myjo.queryInventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtificialController {
	private Logger LOG=LoggerFactory.getLogger(ArtificialController.class);
	/**
	 * 
	 * @Title: loadItemId 
	 * @Description: TODO 加载itemId 修改临时
	 * @param   参数说明 
	 * @return void    返回类型 
	 * @throws
	 */
	public void loadItemId(String itemId) {
		LOG.info("修改临时{}",itemId);
		LOG.info("修改临时成功{}",itemId);
	}
}
