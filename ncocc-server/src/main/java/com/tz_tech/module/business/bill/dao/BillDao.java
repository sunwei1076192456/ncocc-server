package com.tz_tech.module.business.bill.dao;

import com.tz_tech.module.common.model.Bill;

import java.util.List;
import java.util.Map;

public interface BillDao {

    int saveBill(Map<String,Object> bill) throws Exception;

    int updateOrderForInstId(Map<String,Object> bill) throws Exception;

    Map<String,Object> queryPartyById(String id)throws Exception;

    Map<String,Object> queryTacheByActiviId(String id) throws Exception;

    int insertFlowMsg(Map<String,Object> paramMap) throws Exception;

    List<Map<String,Object>> queryBillsByLoginName(Map<String, Object> paramMap) throws Exception;

    List<Map<String,Object>> queryBillByCondition(Map<String, Object> paramMap) throws Exception;

    Long queryBillsCountBying(Map<String, Object> paramMap) throws Exception;

    Long queryBillsCountByCondition(Map<String, Object> paramMap) throws Exception;

    List<Map<String,Object>> queryAllCusInfo()throws Exception;

    List<Map<String,Object>> queryAllShipInfo()throws Exception;

    List<Map<String,Object>> queryDictById(Long id)throws Exception;

    List<Map<String,Object>> queryAddrInfo(Long areaId) throws Exception;

    int saveBillContainer(Map<String,Object> paramMap) throws Exception;

    int saveBillHand(Map<String,Object> paramMap) throws Exception;

//    public Map<String,Object> queryDispatcherParty()throws Exception;

    List<Map<String,Object>> queryProcessInstanceIdById(String workOrderIds)throws Exception;

    Map<String,Object> queryWorkOrderIdByPId(String processInstanceId)throws Exception;

    void updateWorkOrderResult(Map<String,Object> paramMap)throws Exception;

    List<Map<String,Object>> queryRejectReason(Map<String, Object> paramMap)throws Exception;

    List<Map<String,Object>> isExistDisReject(Map<String, Object> paramMap)throws Exception;

    void updateOrderState(String state,String[] orderIdList)throws Exception;

    List<Map<String,Object>> queryBillForRH(Map<String, Object> paramMap)throws Exception;

    String updateBillInfoForRH(String workOrderIds)throws Exception;

    boolean checkIsExistGroupRela(String workOrderIds)throws Exception;

    void saveGroupOrderRecord(Map<String, Object> paramMap)throws Exception;

    void clearGroupOrderRecord(String workOrderIds)throws Exception;

    List<Map<String,Object>> queryAllTransportInfo()throws Exception;

    void insertOrderTransportRela(Map<String, Object> paramMap)throws Exception;

    List<Map<String,Object>> queryGroupOrder(Map<String, Object> paramMap,boolean flag)throws Exception;

    boolean isGroupOrder(Map<String, Object> paramMap)throws Exception;

    Map<String,Object> queryTransportInfoById(Map<String, Object> paramMap)throws Exception;

    List<Map<String,Object>> queryOrderTransportRela(Map<String, Object> paramMap)throws Exception;

    void updateOrderTransportRelaState(Map<String, Object> paramMap)throws Exception;
}
