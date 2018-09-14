/**   
 * @Title: package-info.java 
 * @Package cn.myjo.queryInventory.pojo.vo 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 麦子
 * @date 2018年8月12日 下午1:49:43 
 * @version V1.0   
 */
package cn.myjo.queryInventory.pojo.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFlagVO{
	//订单编号
	@Pattern(regexp="[0-9]{18}",message="订单编号不符合规范!")
	private @NotNull String orderId;
	//备注
	private @Size(min=0,max=100,message="备注的长度为0-100") String memo;
	//旗帜
	private int flag;
}