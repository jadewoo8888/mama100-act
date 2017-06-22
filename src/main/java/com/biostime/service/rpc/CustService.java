package com.biostime.service.rpc;

import com.biostime.transaction.inout.ApplicationSystem;
import com.biostime.util.NumberUtil;
import com.mama100.rpc.cust.CustRpcFactory;
import com.mama100.rpc.cust.thrift.inout.CustAttributeListResponse;
import com.mama100.rpc.cust.thrift.inout.TCustAttribute;
import com.mama100.rpc.cust.thrift.inout.common.BaseRequest;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 13006 on 2017/6/22.
 */
@Service
public class CustService {

    private static final Logger logger = LoggerFactory.getLogger(CustService.class);

    @Autowired
    private CustRpcFactory custRpcFactory;

    public String getCustAppHeaderImg(Long customerId, String version) throws Exception {

        //组装请求数据
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setFromSystem(ApplicationSystem.MAMA_MOB.toString());
        String seqNo = NumberUtil.genSeqNo();
        baseRequest.setSeqNo(seqNo);
        baseRequest.setVersion(version == null ? "v580" : "v"+version);

        logger.info("query customer appHeaderPic. customerId:" + customerId);

        Long startTime = System.currentTimeMillis();

        Future<CustAttributeListResponse> future = custRpcFactory.getAttributeService().getCustAttributes(baseRequest, customerId);
        CustAttributeListResponse response = Await.result(future);

        Long endTime = System.currentTimeMillis();
        Double elpaseTime = (endTime - startTime) / 1000.00;

        String responseCode = response.getBaseResp().getRespCode();
        String responseDesc = response.getBaseResp().getRespDesc();

        StringBuffer logStr = new StringBuffer();
        logStr.append("query customer appHeaderPic response seqNo=").append(seqNo).append(" elapse time=")
                .append(String.valueOf(elpaseTime)).append(" s. Response code=").append(responseCode).append(",desc=")
                .append(responseDesc);
        logger.info(logStr.toString());

        if (!"100".equals(responseCode)) {
            logger.error("查询用户app头像出错,responseCode=" + responseCode);
            return null;
        }

        List<TCustAttribute> tCustAttributeList = response.getValue();
        if (tCustAttributeList != null && tCustAttributeList.size() > 0) {
            for (TCustAttribute tCustAttribute : tCustAttributeList) {
                if( tCustAttribute.getAttributeId() == 1) {//attributeId=1时 attributeValue值为用户头像
                    return tCustAttribute.getAttributeValue();
                }
            }
        } else {
            logger.info("会员中心接口查不到该用户信息：customerId="+customerId);
            return null;
        }

        return null;
    }
}
