package com.biostime.controller;

import com.biostime.constant.Constant;
import com.biostime.controller.base.BaseController;
import com.biostime.response.base.BaseActRes;
import com.biostime.service.test1.rpc.CouponService;
import com.biostime.service.test1.rpc.PointService;
import com.biostime.transaction.rpc.thrift.request.CustomerPointRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 13006 on 2017/6/13.
 */
@RestController
@RequestMapping("activity")
public class TestRpcController extends BaseController {

    @Resource
    CouponService couponService;
    @Resource
    PointService pointService;

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

    @ApiOperation(value="测试送积分rpc", notes="")
    @RequestMapping(value = "/rpc/point",method = RequestMethod.GET)
    public BaseActRes<String> sendPoint() throws Exception {
        BaseActRes<String> res = new BaseActRes<>();

        CustomerPointRequest request = new CustomerPointRequest();
        request.setAccountType(1);//帐号类型：1合生元 2人民币 3swisse
        request.setCustomerId(32737223L);
        request.setHandler(32737223+"");//操作人
        request.setMemo("刘烨的爸气宝箱活动");//操作备注
        request.setPoint(1);//分值
        request.setTerminalCode("");//门店编码
        request.setTypeId(90);//TX_ACTIVITY_TYPE.ID

        boolean isSuccess = pointService.sendPoint(request, "app", "590");

        res.setData("测试送积分rpc"+isSuccess);
        res.setCode(Constant.SUCCESS);
        res.setDesc("ok");
        return res;
    }
}
