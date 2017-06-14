package com.biostime.controller;

import com.biostime.constant.Constant;
import com.biostime.controller.base.BaseController;
import com.biostime.response.base.BaseActRes;
import com.biostime.service.test1.rpc.CouponService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 13006 on 2017/6/13.
 */
@RestController
@RequestMapping("activity")
public class TestRpcController extends BaseController {

    @Autowired
    CouponService couponService;

    @ApiOperation(value="测试送优惠券rpc", notes="")
    @RequestMapping(value = "/rpc/coupon",method = RequestMethod.GET)
    public BaseActRes<String> issueCoupon() throws Exception {
        BaseActRes<String> res = new BaseActRes<>();

        Long customerId = 32737223L;
        //169472、169473、169474
        Long couponDefId = 169936L;
        //String couponCode = couponService.issueCoupon(customerId, null, couponDefId);
        String couponCode = couponService.sendCoupon(customerId, null, couponDefId+"", "app");
        res.setData("测试送优惠券rpc"+couponCode);
        res.setCode(Constant.SUCCESS);
        res.setDesc("ok");
        return res;
    }
}
