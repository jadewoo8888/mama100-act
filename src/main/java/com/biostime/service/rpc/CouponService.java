package com.biostime.service.rpc;

import com.biostime.constant.Constant;
import com.biostime.coupon.rpc.RpcFactory;
import com.biostime.coupon.rpc.thrift.inout.*;

import com.biostime.coupon.rpc.thrift.inout.common.BaseRequest;
import com.biostime.transaction.inout.ApplicationSystem;
import com.biostime.util.NumberUtil;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Resource
    private RpcFactory couponRpcFactory;

    public String sendCoupon(Long customerId, String openId, String couponDefId, String sysSource) throws Exception {
        return sendCoupon(customerId, openId, couponDefId, sysSource, "V1.0", "90001");
    }

    public String sendCoupon(Long customerId, String openId, String couponDefId, String sysSource, String version, String terminalCode) throws Exception {

        String seqNo = NumberUtil.genSeqNo();
        BatchIssueCouponRequest req = new BatchIssueCouponRequest();

        BaseRequest baseReq = new BaseRequest();
        baseReq.setSeqNo(seqNo);

        baseReq.setSourceSystem("MOBILE");
        if(sysSource!=null&&"weixin".equals(sysSource)) {//如果是微信
            baseReq.setSourceSystem(ApplicationSystem.WEIXIN.toString());
        }

        baseReq.setSourceVersion(version == null ? "v4.1.0" : version);

        TCouponIssueInfo issueInfo = new TCouponIssueInfo();
        issueInfo.setCouponDefId(Long.valueOf(couponDefId));
        issueInfo.setTerminalCode(terminalCode == null ? "90001" : terminalCode);

        // 专题领券
        issueInfo.setCouponCondition(/* CouponConditionEnum.CAMPAIGN.name() */"4"); //
        issueInfo.setConditionValue(couponDefId + "");// 跟定义id一样

        List<TCouponIssueInfo> issueInfoList = new ArrayList<TCouponIssueInfo>(1);
        issueInfoList.add(issueInfo);

        req.setBaseReq(baseReq);
        if(customerId != 0L)
            req.setCustomerId(customerId);
        else if(org.apache.commons.lang3.StringUtils.isNotBlank(openId))
            req.setOpenId(openId);

        req.setCouponIssueInfos(issueInfoList);

        //logger.info("batchIssueCoupon with " + LogObjectFieldUtil.logObjectField(req));

        Long startTime = System.currentTimeMillis();

        Future<BatchIssueCouponResponse> future = couponRpcFactory.getMobileCouponService().batchIssueCoupon(req);

        BatchIssueCouponResponse res = Await.result(future);

        Long endTime = System.currentTimeMillis();
        Double elpaseTime = (endTime - startTime) / 1000.00;

        String responseCode = res.getBaseResp().getRespCode();
        String responseDesc = res.getBaseResp().getRespDesc();

        StringBuffer logStr = new StringBuffer();
        logStr.append("batchIssueCoupon response seqNo=").append(seqNo).append(" elapse time=")
                .append(String.valueOf(elpaseTime)).append(" s. Response code=").append(responseCode).append(",desc=")
                .append(responseDesc);
        logger.info(logStr.toString());

        if (Constant.COUPON_SUCCESS.equals(responseCode)) {
            return res.getCouponCodes().get(0);
        }

        return null;
    }
}
