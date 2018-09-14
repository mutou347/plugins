package cn.myjo.queryInventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.myjo.queryInventory.pojo.DaysBuf;
import cn.myjo.queryInventory.pojo.Memo;
import cn.myjo.queryInventory.pojo.vo.UpdateFlagVO;
import cn.myjo.queryInventory.service.ExtensionServiceImpl;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MechineController {
    @Autowired
    ExtensionServiceImpl extensionService;
    @GetMapping(value="/updateOuterId")
    public void updateOuterId(@RequestParam(value="ItemId",required=true)
    	String itemId,@RequestParam(value="days",required=true)int days) {
    	extensionService.updateTaoBaoItemId(itemId, days);
    }
    /**
     * @Title: getOuterIdList 
     * @Description: TODO 	得到所有的		"人工修改订单编号列表"		和 		"机器修改订单编号列表"	
     * @return Object[]    返回Object类型
     * @throws
     */
    @GetMapping(value="/getAll")
    public Object[] getOuterIdList(){
    	List<DaysBuf>daysBufDOList  = extensionService.getAll();
    	List<Memo> queryMemoAllList = extensionService.queryMemoAll();
    	
    	DaysBuf[] daysBufDOArray = daysBufDOList.toArray(new DaysBuf[0]);
    	Memo[] queryMemoAllArray = queryMemoAllList.toArray(new Memo[0]);
    	Object[]objects=new Object[2];
    	objects[0]=daysBufDOArray;
    	objects[1]=queryMemoAllArray;
    	return objects;
    }
    @PostMapping(value="/demo")
    public String demo(String str) {
    	System.out.println(str);
    	return "SUCCESS";
	}
    /**
     * @Title: UpdateDemo 
     * @Description: TODO 根据订单编号 , 修改备注
     * @param flag
     * @param memo
     * @param orderId  参数说明 
     * @return  返回类型 
     * @throws
     */
    @PostMapping(value="/updateDemo")
    public void UpdateDemo(@RequestBody UpdateFlagVO updateFlagVO) {
    	log.info("。。。。开始修改备注。。。。");
    	//根据订单编号更改备注
    	boolean bool=extensionService.updateDemo(updateFlagVO);
    	if(bool) {
    		log.info("修改备注成功");
    	}
    }
}