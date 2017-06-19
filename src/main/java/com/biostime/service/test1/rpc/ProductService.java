package com.biostime.service.test1.rpc;

import com.biostime.util.LoggerUtil;
import com.biostime.util.NumberUtil;
import com.mama100.merchandise.enums.FromSystem;
import com.mama100.rpc.merchandise.MerchandiseRpcFactory;
import com.mama100.rpc.merchandise.thrift.inout.*;
import com.mama100.rpc.merchandise.thrift.inout.common.BaseRequest;
import com.mama100.rpc.merchandise.thrift.inout.request.*;
import com.mama100.rpc.merchandise.thrift.inout.response.*;
import com.twitter.util.Await;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by 13006 on 2017/6/15.
 */
@Service
public class ProductService {

    private static Logger logger = LogManager.getLogger(ProductService.class);

    @Resource
    private MerchandiseRpcFactory merchandiseRpcFactory;

    /**
     * 查询门店商品TerminalSKU详情
     * @param termCode
     * @param sku
     * @return 商品详情
     * @throws Exception
     */
    public FTerminalSKU getFTerminalSKU(String termCode, Long sku) throws Exception{
        FTSSGetTerminalSKURequest request = new FTSSGetTerminalSKURequest();
        request.setBaseReq(new BaseRequest(NumberUtil.genSeqNo(String.valueOf(FromSystem.MAMA_MOB.getId()))));
        request.setCode(termCode);
        request.setSku(sku);
        request.setGallery(true);
        request.setAttribute(true);
        request.setStatus(-1);
        long startTime = System.currentTimeMillis();
        FTerminalSKUResponse response = Await.result(merchandiseRpcFactory.getTerminalSKUService().getFTerminalSKU(request));
        LoggerUtil.withInfo(logger, "terminalSKUService.getFTerminalSKU", System.currentTimeMillis() - startTime, request,
                response);

        return response.getValue();
    }

    /**
     * 查询SKU详情
     * @param sku
     * @return
     * @throws Exception
     */
    public List<FSku> querySKUInfo(Long sku) throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(sku);
        return querySKUInfos(ids);
    }

    /**
     * 批量查询SKU详情
     * @param skus
     * @return
     * @throws Exception
     */
    public List<FSku> querySKUInfos(List<Long> skus) throws Exception {
        SKUQuerySKUInfoRequest request = new SKUQuerySKUInfoRequest();
        request.setBaseReq(new BaseRequest(NumberUtil.genSeqNo(String.valueOf(FromSystem.MAMA_MOB.getId()))));
        request.setSkuIds(skus);
        request.setGallery(true);// 图片
        request.setModel(true);// 型号规格
        long startTime = System.currentTimeMillis();
        FSKUListResponse response = Await.result(merchandiseRpcFactory.getSKUService().querySKUInfo(request));
        LoggerUtil.withInfo(logger, "skuService.querySKUInfo", System.currentTimeMillis() - startTime, request,
                response);

        return response.getValue();
    }

    /**
     * 查询商品图片
     * @param sku
     * @return
     * @throws Exception
     */
    public List<FGallery> queryGalleryBySKU(Long sku) throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(sku);
        return queryGalleryBySKU(ids);
    }

    /**
     * 批量查询商品图片
     * @param skus
     * @return
     * @throws Exception
     */
    public List<FGallery> queryGalleryBySKU(List<Long> skus) throws Exception {
        SKUQueryGalleryBySKUIdsRequest request = new SKUQueryGalleryBySKUIdsRequest(
                new BaseRequest(NumberUtil.genSeqNo(String.valueOf(FromSystem.MAMA_MOB.getId()))), skus);
        long startTime = System.currentTimeMillis();
        FGalleryListResponse response = Await.result(merchandiseRpcFactory.getSKUService().queryGalleryBySKU(request));
        LoggerUtil.withInfo(logger, "skuService.queryGalleryBySKU", System.currentTimeMillis() - startTime, request,
                response);
        return response.getValue();
    }

    /**
     * 获取门店SPU 图片
     * @param spu
     * @return
     * @throws Exception
     */
    public String queryGallaryDetail(long spu) throws Exception {
        I64Request request = new I64Request();
        request.setBaseReq(new BaseRequest(NumberUtil.genSeqNo(String.valueOf(FromSystem.MAMA_MOB.getId()))));
        request.setReq(spu);
        long startTime = System.currentTimeMillis();
        FSPUGalleryDetailResponse response = Await.result(merchandiseRpcFactory.getProductService().getSPUGalleryDetail(request));
        LoggerUtil.withInfo(logger, "productService.getSPUGalleryDetail", System.currentTimeMillis() - startTime,
                request, response);
        return response.getValue().getDetail();
    }

    /**
     * 查询门店SPU---TerminalSPU详情
     * @param termCode
     * @param spu
     * @return FTerminalSPUItem详情
     * @throws Exception
     */
    public FTerminalSPUItem getFTerminalSPU(String termCode, Long spu) throws Exception {
        FTSMSGetTerminalSPURequest request = new FTSMSGetTerminalSPURequest();
        request.setBaseReq(new BaseRequest(NumberUtil.genSeqNo(String.valueOf(FromSystem.MAMA_MOB.getId()))));
        request.setSpu(spu);
        request.setTerminalCode(termCode);
        request.setAttribute(true);
        request.setStatus(-1);
        long startTime = System.currentTimeMillis();
        FTerminalSPUResponse response = Await.result(merchandiseRpcFactory.getTSManagerService().getFTerminalSPU(request));
        LoggerUtil.withInfo(logger, "tSManagerService.getFTerminalSPU", System.currentTimeMillis() - startTime,
                request, response);
        return response.getValue();
    }

    /**
     * 批量修改门店商品的冻结库存
     * @param sku
     * @param code
     * @param freezeStock 当freezeStock为正数就是加冻结库存，反之负数就是减冻结库存
     * @param updatedBy
     * @return 成功处理的门店商品列表（只有sku和门店code）
     * @throws Exception
     */
    public List<FTerminalSKU> handleFreezeStock(Long sku,String code,Double freezeStock,String updatedBy) throws Exception{
        FTSMSHandleFTerminalSKURequest request = new FTSMSHandleFTerminalSKURequest();
        request.setBaseReq(new BaseRequest(NumberUtil.genSeqNo(String.valueOf(FromSystem.MAMA_MOB.getId()))));
        FTerminalSKU fTerminalSKU = new FTerminalSKU();
        fTerminalSKU.setSku(sku);
        fTerminalSKU.setCode(code);
        fTerminalSKU.setFreezeStock(freezeStock.intValue());
        fTerminalSKU.setUpdatedBy(updatedBy);
        request.setTerminalSKUs(Arrays.asList(fTerminalSKU));
        long startTime = System.currentTimeMillis();
        FTerminalSKUList2Response response = Await.result(merchandiseRpcFactory.getTSManagerService().handleFreezeStock(request));
        LoggerUtil.withInfo(logger, "tSManagerService.handleFreezeStock", System.currentTimeMillis() - startTime,
                request, response);
        return response.getValue();
    }

    /**
     * 获取门店商品的图片
     * @param terminalSKU
     * @param sizeType 图片规格 300*300
     * @return
     */
    public String getImg(FTerminalSKU terminalSKU, String sizeType) {
        //String img = null;
        Map<String,List<FTerminalSKUGallery>> tskuGalleryMap = terminalSKU.getTskuGallerys();//key为图片规格
        if (tskuGalleryMap.containsKey(sizeType)) {
            List<FTerminalSKUGallery> list = tskuGalleryMap.get(sizeType);
            return list.get(0).getImgUrl();
        }
        /*for (Map.Entry<String,List<FTerminalSKUGallery>> entry : tskuGalleryMap.entrySet()) {
            List<FTerminalSKUGallery> list = entry.getValue();
            img = list.get(0).getImgUrl();//默认值
            for (FTerminalSKUGallery terminalSKUGallery : list) {
                if (terminalSKUGallery.getImgType().equals(sizeType)) {
                    img = terminalSKUGallery.getImgUrl();
                    break;
                }
            }

        }*/
        return null;
    }
}
