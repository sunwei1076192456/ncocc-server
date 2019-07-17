package com.tz_tech.module.business.activity.service;

import com.tz_tech.module.common.utils.SpringContextUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.collections4.MapUtils;
import java.util.HashMap;
import java.util.Map;

public class UnpackFlowTaskListener implements TaskListener {

    private ActivityService activityService;

    public ActivityService getActivityService(){
        if(activityService == null){
            activityService = (ActivityService) SpringContextUtil.getApplicationContext().getBean("activityService");
        }
        return activityService;
    }

    @Override
    public void notify(DelegateTask delegateTask)  {
        try {
            DelegateExecution execution = delegateTask.getExecution();
            System.out.println("当前任务ID=" + execution.getCurrentActivityId() + "/当前任务NAME=" + execution.getCurrentActivityName());
            Map<String,Object> dispatcher = new HashMap<String, Object>();
            if("HB-CZ-JD".equals(execution.getCurrentActivityId())){
                dispatcher = getActivityService().receiveOrder(delegateTask.getExecution());
            }else if("HB-DD-SH".equals(execution.getCurrentActivityId())){
                dispatcher = getActivityService().dispatcherAudit(delegateTask.getExecution());
            }else if("HB-DD-PC".equals(execution.getCurrentActivityId())){
                dispatcher = getActivityService().dispatcherAssignCar(delegateTask.getExecution());
            }
            delegateTask.setAssignee(MapUtils.getString(dispatcher,"executor_name",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
