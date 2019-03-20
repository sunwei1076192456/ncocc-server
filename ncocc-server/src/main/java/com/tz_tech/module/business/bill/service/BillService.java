package com.tz_tech.module.business.bill.service;

import com.tz_tech.module.business.activity.service.ActivityService;
import com.tz_tech.module.business.bill.dao.BillDao;
import com.tz_tech.module.common.model.Bill;
import com.tz_tech.module.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BillService {

    @Autowired
    private BillDao billdao;

    @Autowired
    private ActivityService activityService;

    public Result saveBill(Bill bill) throws Exception{
        Result result = Result.fail();

        try {
            //生成唯一订单号
            bill.setId(genBusinessFlowNum());
            //订单信息入库
            billdao.saveBill(bill);

            //启动流程,获取流程实例ID
            String processInstanceId = activityService.startProcess(bill.getId());

            //将获取到的流程实例ID入库,同时将订单表的状态改为流程启动中-10N
            bill.setProcessInstanceId(processInstanceId);
            billdao.updateOrderForInstId(bill);
            result = Result.success();
        } catch (Exception e) {
            e.getStackTrace();
            result.setResultMsg("单据入库失败!");
        }
        return result;
    }

    public Result queryBillByLoginName(Map<String,Object> paramMap) throws Exception{
        Result result = Result.fail();
        List<Map<String,Object>> bills = billdao.queryBillsByLoginName(paramMap);
        //查询出单据总量
        Long totalCount = billdao.queryBillsCountBying(paramMap);

        Map<String,Object> data = new HashMap<String, Object>();
        data.put("bill",bills);
        data.put("totalCount",totalCount);
        result = Result.success(data);
        return result;
    }

    public Result queryCommonInfo() throws Exception{
        Result result = Result.fail();
        //查客户信息
        List<Map<String,Object>> cust = billdao.queryAllCusInfo();
        //查船务信息
        List<Map<String,Object>> ship = billdao.queryAllShipInfo();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("cust",cust);
        data.put("ship",cust);
        result = Result.success(data);
        return result;
    }

    private String genBusinessFlowNum()
    {
        Random random = new Random();
        String num = "";
        for(int i=0;i<3;i++){
             num += random.nextInt(10);//3位随机数
        }
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        return  "FF" + sd.format(currDate) + "A" + num;//总长度不超过20位
    }

}
