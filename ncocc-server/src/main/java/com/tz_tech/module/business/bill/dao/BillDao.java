package com.tz_tech.module.business.bill.dao;

import com.tz_tech.module.common.model.Bill;

import java.util.List;
import java.util.Map;

public interface BillDao {

    public int saveBill(Map<String,Object> bill) throws Exception;

    public int updateOrderForInstId(Map<String,Object> bill) throws Exception;

    public Map<String,Object> queryPartyById(String id)throws Exception;

    public Map<String,Object> queryTacheByActiviId(String id) throws Exception;

    public int insertFlowMsg(Map<String,Object> paramMap) throws Exception;

    public List<Map<String,Object>> queryBillsByLoginName(Map<String, Object> paramMap) throws Exception;

    public Long queryBillsCountBying(Map<String, Object> paramMap) throws Exception;

    public List<Map<String,Object>> queryAllCusInfo()throws Exception;

    public List<Map<String,Object>> queryAllShipInfo()throws Exception;

    public List<Map<String,Object>> queryDictById(Long id)throws Exception;

    public List<Map<String,Object>> queryAddrInfo(Long areaId) throws Exception;

    public int saveBillContainer(Map<String,Object> paramMap) throws Exception;

    public int saveBillHand(Map<String,Object> paramMap) throws Exception;
}
