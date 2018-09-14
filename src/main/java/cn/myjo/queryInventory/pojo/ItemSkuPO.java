package cn.myjo.queryInventory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName: ItemSkuDO 
 * @Description: TODO itemSKu数据表的实体对象
 * @author 麦子
 * @date 2018年7月30日 下午4:44:27 
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSkuPO {
	private String outer_id;
	private String num_id;
	private String properties_name;
	private String sku_id;
}
