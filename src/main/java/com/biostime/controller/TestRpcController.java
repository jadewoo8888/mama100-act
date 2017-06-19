package com.biostime.controller;

import com.biostime.constant.Constant;
import com.biostime.controller.base.BaseController;
import com.biostime.response.TermSkuInfoRes;
import com.biostime.response.base.BaseActRes;

import com.biostime.service.test1.rpc.CouponService;
import com.biostime.service.test1.rpc.PointService;
import com.biostime.service.test1.rpc.ProductService;
import com.biostime.transaction.rpc.thrift.request.CustomerPointRequest;
import com.biostime.util.LoggerUtil;
import com.mama100.rpc.merchandise.thrift.inout.FSku;
import com.mama100.rpc.merchandise.thrift.inout.FTerminal;
import com.mama100.rpc.merchandise.thrift.inout.FTerminalSKU;
import com.mama100.rpc.merchandise.thrift.inout.FTerminalSKUGallery;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    @Resource
    ProductService skuService;

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

    @ApiOperation(value="测试查询商品rpc", notes="")
    @RequestMapping(value = "/rpc/skuInfo",method = RequestMethod.GET)
    public BaseActRes<TermSkuInfoRes> skuInfo() throws Exception {
        BaseActRes<TermSkuInfoRes> res = new BaseActRes<>();

        FTerminalSKU fTerminalSKU = skuService.getFTerminalSKU("912721",223407L);

        TermSkuInfoRes termSkuInfoRes = new TermSkuInfoRes();
        termSkuInfoRes.setExchangePoint(fTerminalSKU.getExchangePoint());
        //termSkuInfoRes.setAdDesc(sku.getFsku().get);
        String img = skuService.getImg(fTerminalSKU, "600*600");
        termSkuInfoRes.setImgUrl(img);
        termSkuInfoRes.setPrice(fTerminalSKU.getPriceDiscounted());
        FSku fsku = fTerminalSKU.getFsku();
        //FTerminal fTerminal = sku.getFterminal();
        //fTerminal.getName();
        termSkuInfoRes.setProductName(fsku.getName());
        termSkuInfoRes.setSku(fTerminalSKU.getSku()+"");
        termSkuInfoRes.setSpu(fTerminalSKU.getSpu()+"");
        termSkuInfoRes.setStock(fTerminalSKU.getStock());
        termSkuInfoRes.setTerminalCode(fTerminalSKU.getCode());
        termSkuInfoRes.setSuggestPrice(fTerminalSKU.getPrice());

        res.setData(termSkuInfoRes);
        res.setCode(Constant.SUCCESS);
        res.setDesc("ok");
        return res;
    }
}
