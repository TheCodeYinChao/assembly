package cn.bainuo.assembly.data;

import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.plugin.ParameterMap;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AssemblyDataApplicationTests {
	@Autowired
	private CouponService couponService;

	@Test
	public void contextLoads() {
		ParameterMap pm = new ParameterMap();
		pm.put("rows",10);
		pm.put("page",1);

		List<Coupons> couponVos = couponService.selectCoupons(pm);
		log.info(JSON.toJSONString(couponVos));
	}

}
