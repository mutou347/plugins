/**   
 * @Title: package-info.java 
 * @Package cn.myjo.queryInventory.API 
 * @Description: TODO 
 * @author 麦子
 * @date 2018年7月30日 下午1:35:42 
 * @version V1.0   
 */
package cn.myjo.queryInventory.API;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TmallItemOuteridUpdateRequest;
import com.taobao.api.request.TmallItemOuteridUpdateRequest.UpdateSkuOuterId;
import com.taobao.api.response.TmallItemOuteridUpdateResponse;

import cn.myjo.queryInventory.pojo.ApiConfiguration;
/**
 * 
 * @ClassName: TaoBaoHttpClint 
 * @Description: TODO 淘宝API接口调用
 * @author 麦子
 * @date 2018年7月30日 下午4:20:12 
 *
 */
@Component
public class TaoBaoHttpClint {
	/**
	 * @Title: TianMaoOuterIdUpdate 
	 * @Description: TODO 更新商家编码的方法
	 * @param skuId 
	 * @param newOuterId	商家编码
	 * @param numIId
	 * @return boolean    返回类型 
	 */
	public boolean TianMaoOuterIdUpdate(long skuId, long numIId,String newOuterId) {
		TaobaoClient client = new DefaultTaobaoClient(ApiConfiguration.TAOBAO_URL, ApiConfiguration.APP_KEY,
				ApiConfiguration.SECRET);
		TmallItemOuteridUpdateRequest req = new TmallItemOuteridUpdateRequest();
		//设置numIId
		req.setItemId(numIId);
		List<UpdateSkuOuterId> list2 = new ArrayList<UpdateSkuOuterId>();
		UpdateSkuOuterId obj3 = new UpdateSkuOuterId();
		list2.add(obj3);
		obj3.setSkuId(skuId);
		obj3.setOuterId(newOuterId);		//	设置最新的商家编码
		req.setSkuOuters(list2);
		TmallItemOuteridUpdateResponse rsp=null;
		try {
			rsp = client.execute(req, ApiConfiguration.SESSION_KEY);
			if(rsp.isSuccess()) {
				System.out.println(rsp.getBody());
				return Boolean.TRUE;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
}
