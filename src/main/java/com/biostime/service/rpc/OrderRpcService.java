package com.biostime.service.rpc;

import com.biostime.helper.ProductHelper;
import com.biostime.util.LoggerUtil;
import com.biostime.util.NumberUtil;
import com.mama100.order.rpc.OrderRpcJavaFactory;
import com.mama100.order.rpc.bean.*;
import com.mama100.order.rpc.thrift.inout.common.*;
import com.mama100.rpc.merchandise.thrift.inout.FSku;
import com.mama100.rpc.merchandise.thrift.inout.FTerminalSKU;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by 13006 on 2017/6/19.
 */
@Service
public class OrderRpcService {

    @Resource
    private OrderRpcJavaFactory orderRpcJavaFactory;

    private static Logger logger = LogManager.getLogger(OrderRpcService.class);

    /**
     * 构造订单提交入参
     * @param customerId
     * @param fTerminalSKU 门店商品信息
     * @param orderReciverBean 收货人信息
     * @param tradeAttr 身份证信息
     * @return
     */
    public OrderBatchSubmitResponse orderBatchSubmit(Long customerId, FTerminalSKU fTerminalSKU, OrderReceiverBean orderReciverBean, Map<String, String> tradeAttr) throws InterruptedException, ExecutionException {

        OrderDetailBean orderDetailBean = buildOrderDetailBean(customerId, fTerminalSKU);

        List<OrderDetailBean> orderProducts = new ArrayList<>(1);
        orderProducts.add(orderDetailBean);

        // 收货人信息
       /*
        orderReciverBean.setReceiver("collectOrder.getReceiver()");//变量
        orderReciverBean.setContactMobile("collectOrder.getContactMobile()");//变量
        orderReciverBean.setReceiverAddress("collectOrder.getReceiverAddress()");//变量
        orderReciverBean.setProvinceCode("collectOrder.getProvinceCode()");//变量
        orderReciverBean.setCityCode("collectOrder.getProvinceCode()");//变量
        orderReciverBean.setDistrictCode("collectOrder.getDistrictCode()");//变量*/

        //订单基本信息
        OrderBean orderBean = buildOrderBean(customerId, fTerminalSKU, orderReciverBean, orderProducts);

        RequestHeader header = new RequestHeader();
        header.setSeqNo(NumberUtil.genSeqNo());
        header.setHandler(customerId + "");
        header.setSourceSystem(SourceSystem.SWISSE_WEIXIN);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderBean(orderBean);
        orderRequest.setRequestHeader(header);

        OrderBatchSubmitRequest orderBatchSubmitRequest = new OrderBatchSubmitRequest();

        orderBatchSubmitRequest.setRequestHeader(header);
        orderBatchSubmitRequest.setOrders(Arrays.asList(orderRequest));

        //添加身份证信息
        /*Map<String, String> tradeAttr = new HashMap<String, String>();
        tradeAttr.put("idtype", "2");// 证件类型  //变量
        tradeAttr.put("idno", "");//身份证号 //变量
        tradeAttr.put("custName", "");  //姓名 //变量*/
        orderBatchSubmitRequest.setTradeAttr(tradeAttr);

        long startTime = System.currentTimeMillis();
        OrderBatchSubmitResponse response = orderRpcJavaFactory.getOrderBatchClientJavaService().orderBatchSubmit(orderBatchSubmitRequest).get();
        LoggerUtil.withInfo(logger, "orderBatchClientJavaService().orderBatchSubmit", System.currentTimeMillis() - startTime, orderBatchSubmitRequest, response);
        return response;
    }

    /**
     * 构造订单基本信息
     * @param customerId
     * @param fTerminalSKU 门店商品信息
     * @param orderReciverBean 收货人信息
     * @param orderProducts 订单商品信息
     * @return
     */
    private OrderBean buildOrderBean(Long customerId, FTerminalSKU fTerminalSKU, OrderReceiverBean orderReciverBean, List<OrderDetailBean> orderProducts) {
        OrderBean orderBean = new OrderBean();

        orderBean.setOrderType(OrderType.PRODUCT_ORDER);
        orderBean.setPayStyle(PayStyle.WEIXIN);
        orderBean.setBillTitle("biostime");
        orderBean.setFreigh(0D);
        orderBean.setShipment(Shipments.SHIP_BY_TERMINAL);
        orderBean.setMchType("Other");//标识为活动订单
        orderBean.setOrderSourceBussiness(OrderSourceBusiness.SWISSE_SEA_AMOY_ACTIVITY);// 设为swisse微信海淘
        orderBean.setCustomerId(customerId);
        orderBean.setHandler(customerId+"");
        orderBean.setCustomerPreferTerminal(fTerminalSKU.getCode());//门店编码
        double taxCost = ProductHelper.taxCost(fTerminalSKU.getPriceDiscounted(), fTerminalSKU.getCustoms(), fTerminalSKU.getConsumptionTax(), fTerminalSKU.getValueAddedTax());
        orderBean.setPersonalPostalArticlesTax(taxCost);// 税费

        orderBean.setOrderReciver(orderReciverBean);

        orderBean.setOrderProducts(orderProducts);

        return orderBean;
    }

    /**
     * 构造订单商品信息
     * @param customerId
     * @param fTerminalSKU 门店商品信息
     * @return
     */
    private OrderDetailBean buildOrderDetailBean(Long customerId, FTerminalSKU fTerminalSKU) {
        // 商品信息
        OrderDetailBean orderDetailBean = new OrderDetailBean();
        FSku fSku = fTerminalSKU.getFsku();
        orderDetailBean.setProductId(fTerminalSKU.getSku());
        orderDetailBean.setProductName(fSku.getName());
        orderDetailBean.setQuantity(1);// 数量固定为1
        orderDetailBean.setProductPrice(fTerminalSKU.getPriceDiscounted());//优惠价
        orderDetailBean.setHandler(customerId + "");

        orderDetailBean.setConsumptionTaxRate(fTerminalSKU.getConsumptionTax()); // 消费税率
        orderDetailBean.setValueAddedTaxRate(fTerminalSKU.getValueAddedTax()); // 增值税率
        orderDetailBean.setTariffRate(fTerminalSKU.getCustoms()); // 关税率

        return orderDetailBean;
    }

    /**
     *
     * @param proOrderCode 商品订单号
     * @param customerId
     * @param transactionId 交易流水 微信支付的流水,微信支付回调返回的参数值
     * @param openId
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public OrderResponse orderPay(String proOrderCode, Long customerId, String transactionId, String openId) throws InterruptedException, ExecutionException {
        OrderPayRequest request = new OrderPayRequest();
        request.setOrderCode(proOrderCode);// 订单号编 必须
        RequestHeader header = new RequestHeader();
        header.setSeqNo(NumberUtil.genSeqNo());
        header.setHandler(customerId + "");
        header.setSourceSystem(SourceSystem.SWISSE_WEIXIN);
        request.setRequestHeader(header);
        request.setTransactionSeq(transactionId);// 交易流水 微信支付的流水
        request.setOpenId(openId);// 微信支付的
        request.setPayStyle(PayStyle.WEIXIN.getValue());
        long startTime = System.currentTimeMillis();
        OrderResponse response = orderRpcJavaFactory.getOrderClientJavaService().orderPay(request).get();
        LoggerUtil.withInfo(logger, "OrderClientJavaService().orderPay", System.currentTimeMillis() - startTime, request, response);
        return response;
    }
}
