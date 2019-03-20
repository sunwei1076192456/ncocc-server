package com.tz_tech.module.business.activity.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tz_tech.module.business.bill.dao.BillDao;
import com.tz_tech.module.common.adapter.ObjectTypeAdapter;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private BillDao billDao;

    private static Gson gson = new GsonBuilder().setLenient().serializeNulls()
            .registerTypeAdapter(Map.class, new ObjectTypeAdapter())
            .registerTypeAdapter(List.class, new ObjectTypeAdapter())
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    private static final Logger log = Logger.getLogger(ActivityService.class);

    /**
     * 启动流程,获取流程实例ID
     * @return
     * @throws Exception
     */
    public String startProcess(String orderId) throws Exception{

        Map<String, Object> params=new HashMap<String, Object>();
        params.put("orderId",orderId);
        //启动流程
        ProcessInstance process = runtimeService.startProcessInstanceByKey("unpackProcess_1",params);
        //获取流程实例ID
        String processInstanceId = process.getId();

        return processInstanceId;

    }

    /**
     * 流程图中的接单环节的执行方法
     * @param execution
     * @throws Exception
     */
    public void receiveOrder(DelegateExecution execution)throws Exception{
        String orderId = (String) execution.getVariable("orderId");
        //查出下一环节执行人--订单表里的接单人
        Map<String,Object> party = billDao.queryPartyById(orderId);
        log.info("下一环节执行人=" + party);
        //查询环节流程
        Map<String,Object> tache = billDao.queryTacheByActiviId(execution.getCurrentActivityId());
        log.info("下一环节=" + tache);
        //往流程表里插一条接单记录ff_flow_msg
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.putAll(party);
        paramMap.putAll(tache);
        paramMap.put("processInstanceId",execution.getProcessInstanceId());
        Map<String,Object> flowReq = getRequestForFlow(paramMap);
        billDao.insertFlowMsg(flowReq);
    }

    /**
     * 构造插入流程表的参数
     * @return
     */
    private Map<String,Object> getRequestForFlow(Map<String,Object> param){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        //生成serial_id
        paramMap.put("serial_id", UUID.randomUUID().toString().replace("-", ""));
        paramMap.put("order_id", MapUtils.getString(param,"id"));
        paramMap.put("command_code","createWork");
        paramMap.put("send_content",gson.toJson(param));
        paramMap.put("processInstanceId",MapUtils.getString(param,"processInstanceId"));
        log.info("插入流程表参数=" + paramMap);
        return paramMap;
    }
}
