package com.tz_tech.module.business.activity.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tz_tech.module.business.bill.dao.BillDao;
import com.tz_tech.module.common.adapter.ObjectTypeAdapter;
import com.tz_tech.module.common.utils.BaseInfoLoadFromDB;
import com.tz_tech.module.common.utils.ProcedureUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

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
    public Map<String,Object> receiveOrder(DelegateExecution execution)throws Exception{
        log.info("====进入接单环节====");
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
        return party;
    }

    /**
     * 流程图中的调度审核环节的执行方法
     * @param execution
     * @throws Exception
     */
    public Map<String,Object> dispatcherAudit(DelegateExecution execution)throws Exception{
        log.info("====进入调度员审核环节====");
        String orderId = (String) execution.getVariable("orderId");
        //查出下一环节执行人--调度员（多个调度员的话就随机）
        Long processInstanceId = Long.valueOf(execution.getProcessInstanceId());
        List dispatcherList = (ArrayList)BaseInfoLoadFromDB.dispatcherMap.get("dispatcher");
        Long index = processInstanceId % dispatcherList.size();
        Map<String,Object> dispatcher = (HashMap<String, Object>)dispatcherList.get(index.intValue());
        //查询环节流程
        Map<String,Object> tache = billDao.queryTacheByActiviId(execution.getCurrentActivityId());
        //往流程表里插一条接单记录ff_flow_msg
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",orderId);
        paramMap.putAll(dispatcher);
        paramMap.putAll(tache);
        paramMap.put("processInstanceId",execution.getProcessInstanceId());
        Map<String,Object> flowReq = getRequestForFlow(paramMap);
        billDao.insertFlowMsg(flowReq);
        return dispatcher;
    }

    /**
     * 调度派车
     * @param execution
     * @return
     * @throws Exception
     */
    public Map<String,Object> dispatcherAssignCar(DelegateExecution execution)throws Exception{
        log.info("====进入调度员派车环节====");
        String orderId = (String) execution.getVariable("orderId");
        //查出下一环节执行人--调度员（多个调度员的话就随机）
        Long processInstanceId = Long.valueOf(execution.getProcessInstanceId());
        List dispatcherList = (ArrayList)BaseInfoLoadFromDB.dispatcherMap.get("dispatcher");
        Long index = processInstanceId % dispatcherList.size();
        Map<String,Object> dispatcher = (HashMap<String, Object>)dispatcherList.get(index.intValue());
        //查询环节流程
        Map<String,Object> tache = billDao.queryTacheByActiviId(execution.getCurrentActivityId());
        //往流程表里插一条接单记录ff_flow_msg
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",orderId);
        paramMap.putAll(dispatcher);
        paramMap.putAll(tache);
        paramMap.put("processInstanceId",execution.getProcessInstanceId());
        Map<String,Object> flowReq = getRequestForFlow(paramMap);
        billDao.insertFlowMsg(flowReq);
        return dispatcher;
    }

    /**
     * 车管审核
     * @param execution
     * @return
     * @throws Exception
     */
    public Map<String,Object> carManagerAudit(DelegateExecution execution)throws Exception{
        log.info("====进入车管审核环节====");
        String orderId = (String) execution.getVariable("orderId");
        //查出下一环节执行人--车管员（多个车管员的话就随机）
        Long processInstanceId = Long.valueOf(execution.getProcessInstanceId());
        List dispatcherList = (ArrayList)BaseInfoLoadFromDB.dispatcherMap.get("carManager");
        Long index = processInstanceId % dispatcherList.size();
        Map<String,Object> dispatcher = (HashMap<String, Object>)dispatcherList.get(index.intValue());
        //查询环节流程
        Map<String,Object> tache = billDao.queryTacheByActiviId(execution.getCurrentActivityId());
        //往流程表里插一条接单记录ff_flow_msg
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",orderId);
        paramMap.putAll(dispatcher);
        paramMap.putAll(tache);
        paramMap.put("processInstanceId",execution.getProcessInstanceId());
        Map<String,Object> flowReq = getRequestForFlow(paramMap);
        billDao.insertFlowMsg(flowReq);
        return dispatcher;
    }

    /**
     * 工单归档
     * @param execution
     * @return
     * @throws Exception
     */
    public Map<String,Object> orderReport(DelegateExecution execution)throws Exception{
        log.info("====进入工单归档环节====");
        String orderId = (String) execution.getVariable("orderId");
        //查出下一环节执行人--归档环节默认sys
        Long processInstanceId = Long.valueOf(execution.getProcessInstanceId());
        Map<String,Object> dispatcher = new HashMap<String, Object>();
        dispatcher.put("executor_id","sys");
        dispatcher.put("executor_name","系统自动");
        dispatcher.put("executor_type","systerm");
        //查询环节流程
        Map<String,Object> tache = billDao.queryTacheByActiviId(execution.getCurrentActivityId());
        //往流程表里插一条接单记录ff_flow_msg
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",orderId);
        paramMap.putAll(dispatcher);
        paramMap.putAll(tache);
        paramMap.put("processInstanceId",execution.getProcessInstanceId());
        Map<String,Object> flowReq = getRequestForFlow(paramMap);
        billDao.insertFlowMsg(flowReq);
        return dispatcher;
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

    /**
     * 通用回单方法
     * @param processInstanceId
     * @param hasParam
     * @param paramMap
     * @throws Exception
     */
    public void completeWorkOrder(String processInstanceId,boolean hasParam,Map<String,Object> paramMap)throws Exception{
        log.info("====【" + processInstanceId + "】进入回单方法====" );
        //工单回单-调存过，传工单id
        Map<String,Object> workOrder = billDao.queryWorkOrderIdByPId(processInstanceId);
        //调用存过删除在途表,同时把工单表的状态更新为10F
        String[] params = {MapUtils.getString(workOrder,"workOrderId"),"out_succ_flag","out_error_msg"};
        ProcedureUtil.executeProcedure(params,2,"complete_work_order");

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(hasParam){
            Map<String, Object> variables=new HashMap<String,Object>();
            if(null != paramMap){
                variables.putAll(paramMap);
            }
            taskService.complete(task.getId(),variables);
        }else{
            taskService.complete(task.getId());
        }
    }
}
