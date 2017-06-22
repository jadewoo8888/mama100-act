package com.biostime.service.rpc;

import com.biostime.transaction.inout.ApplicationSystem;
import com.biostime.transaction.rpc.TransactionRpcFactory;
import com.biostime.transaction.rpc.thrift.request.BaseRequest;
import com.biostime.transaction.rpc.thrift.request.CustomerPointRequest;
import com.biostime.transaction.rpc.thrift.response.CustomerPointResponse;
import com.biostime.util.NumberUtil;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 13006 on 2017/6/14.
 */
@Service
public class PointService {

    private static final Logger logger = LoggerFactory.getLogger(PointService.class);

    @Resource
    TransactionRpcFactory pointRpcFactory;

    public boolean sendPoint(CustomerPointRequest request, String sourceSystem, String curVersion) throws Exception {

        /*

        baseRequest.setSourceVersion("v"+sendPointParam.getCurVersion());*/

       /* CustomerPointRequest request = new CustomerPointRequest();
        request.setAccountType(accountType);//帐号类型：1合生元 2人民币 3swisse
        request.setCustomerId(customerId);
        request.setBaseRequest(baseRequest);
        request.setHandler(handler);//操作人
        request.setMemo(memo);//操作备注
        request.setPoint(point);//分值
        request.setTerminalCode(terminalCode);//门店编码
        request.setTypeId(typeId);//TX_ACTIVITY_TYPE.ID*/

        BaseRequest baseRequest = new BaseRequest();
        String seqNo = NumberUtil.genSeqNo();
        baseRequest.setSeqNo(seqNo);

        baseRequest.setSourceVersion("v"+curVersion);
        baseRequest.setSourceSystem("MOBILE");
        if(sourceSystem != null && "weixin".equals(sourceSystem)){//如果是微信
            baseRequest.setSourceSystem(ApplicationSystem.WEIXIN.toString());
        }

        request.setBaseRequest(baseRequest);

        Long startTime = System.currentTimeMillis();

        Future<CustomerPointResponse> future = pointRpcFactory.getTransactionService().customerPoint(request);
        CustomerPointResponse res = Await.result(future);

        Long endTime = System.currentTimeMillis();
        Double elpaseTime = (endTime - startTime) / 1000.00;

        int responseCode = res.getBaseResponse().getRespCode();
        String responseDesc = res.getBaseResponse().getRespDesc();

        StringBuffer logStr = new StringBuffer();
        logStr.append("send customer point response seqNo=").append(seqNo).append(" elapse time=")
                .append(String.valueOf(elpaseTime)).append(" s. Response code=").append(responseCode).append(",desc=")
                .append(responseDesc);
        logger.info(logStr.toString());

        if (responseCode != 100) {
            return false;
        } else {
            return true;
        }
    }


}
