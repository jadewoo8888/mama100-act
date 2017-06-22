package com.biostime.controller;

import com.biostime.constant.Constant;
import com.biostime.controller.base.BaseController;
import com.biostime.response.BatchSubmitOrderRes;
import com.biostime.response.TermSkuInfoRes;
import com.biostime.response.base.BaseActRes;
import com.biostime.service.rpc.*;
import com.biostime.transaction.rpc.thrift.request.CustomerPointRequest;
import com.biostime.util.NumberUtil;
import com.mama100.order.rpc.bean.*;
import com.mama100.rpc.merchandise.thrift.inout.FSku;
import com.mama100.rpc.merchandise.thrift.inout.FTerminalSKU;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    OrderRpcService orderRpcService;
    @Resource
    CustService custService;

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

    @ApiOperation(value="测试下单rpc", notes="")
    @RequestMapping(value = "/rpc/batchSubmitOrder",method = RequestMethod.GET)
    public BaseActRes<BatchSubmitOrderRes> batchSubmitOrder() throws Exception {
        BaseActRes<BatchSubmitOrderRes> res = new BaseActRes<>();

        // 收货人信息
        OrderReceiverBean orderReciverBean = new OrderReceiverBean();
        orderReciverBean.setReceiver("小吴");
        orderReciverBean.setContactMobile("18565338848");
        orderReciverBean.setReceiverAddress("雷州市北京镇上海村");
        orderReciverBean.setProvinceCode("440000000000");
        orderReciverBean.setCityCode("440100000000");
        orderReciverBean.setDistrictCode("440106000000");

        //添加身份证信息
        Map<String, String> tradeAttr = new HashMap<String, String>();
        tradeAttr.put("idtype", "2");// 证件类型  //变量
        tradeAttr.put("idno", "440882199909098888");//身份证号 //变量
        tradeAttr.put("custName", "吴迪");  //姓名 //变量

        FTerminalSKU fTerminalSKU = skuService.getFTerminalSKU("912721",223407L);

        Long customerId = 32737223L;
        OrderBatchSubmitResponse orderBatchSubmitResponse = orderRpcService.orderBatchSubmit(customerId,fTerminalSKU,orderReciverBean, tradeAttr);
        //logger.info("下单:proOrderCode="+orderBatchSubmitResponse.getBatchSubmitOrderCode()+"  proOrdeRespDesc:"+orderBatchSubmitResponse.getBaseResp().getRespDesc());
        //下单成功
        if(null != orderBatchSubmitResponse && Constant.SUCCESS.equals(orderBatchSubmitResponse.getBaseResp().getRespCode()) && StringUtils.isNotBlank(orderBatchSubmitResponse.getBatchSubmitOrderCode())) {
            String proOrderCode = orderBatchSubmitResponse.getBatchSubmitOrderCode();

            res.setDesc("下单成功！");
            res.setCode(Constant.SUCCESS);
            //调支付接口
            OrderResponse payOrderResponse = orderRpcService.orderPay(proOrderCode, customerId, "2343433899e", null);
            //logger.info("下单支付:payOrderRespDesc="+payOrderResponse.getBaseResp().getRespDesc());
            //支付成功
            if (null != payOrderResponse && Constant.SUCCESS.equals(payOrderResponse.getBaseResp().getRespCode())) {

                BatchSubmitOrderRes batchSubmitOrderRes = new BatchSubmitOrderRes();
                batchSubmitOrderRes.setTradeNo(proOrderCode);
                batchSubmitOrderRes.setOnlinePayAmount(NumberUtil.formatFloat(orderBatchSubmitResponse.getOnlinePayAmount().floatValue()));
                res.setData(batchSubmitOrderRes);

                res.setDesc("下单支付成功！");
                res.setCode(Constant.SUCCESS);
            } else {
                res.setDesc("下单支付失败！");
                res.setCode(Constant.ERROR);
            }
        } else {
            res.setCode(Constant.ERROR);
            res.setDesc("下单失败！");
        }

        return res;
    }

    @ApiOperation(value="测试会员rpc", notes="")
    @RequestMapping(value = "/rpc/cust",method = RequestMethod.GET)
    public BaseActRes<String> cust() throws Exception {
        BaseActRes<String> res = new BaseActRes<>();
        Long customerId = 32737223L;
        String headerImg = custService.getCustAppHeaderImg(customerId, null);

        res.setData(headerImg);
        res.setCode(Constant.SUCCESS);
        return res;
    }

}
