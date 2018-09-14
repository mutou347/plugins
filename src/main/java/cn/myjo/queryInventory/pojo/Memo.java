package cn.myjo.queryInventory.pojo;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
	//订单编号
	@Pattern(regexp="[0-9]{18}")
	private @NotNull String orderId;
	//买家Id
	private @NotNull String buyer;
	//商家编码
	@Pattern(regexp = "^[\\w]{6}-[\\w\\W]{1,3}-[\\w\\W]{1,3}|[\\w]{6}-[\\w\\W]{1,3}$"
			,message = "商家编码格式错误")
	private @NotNull String itemId;
	//付款金额
	@Min(0)
	private double pay_amount;
	//缺货时间	
	private @Past Date performance_time;
	//短信
	private boolean note;
	//临时
	private boolean temporary;
	//备注
	private @Size(min=0,max=100) String memo;
	//旗帜
	private @Size(min=0,max=5) int flag;
	//付款时间
	private @Past Date payTime;

}
