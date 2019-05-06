package com.tz_tech.module.business.bill.service;

import com.tz_tech.module.business.activity.service.ActivityService;
import com.tz_tech.module.business.bill.dao.BillDao;
import com.tz_tech.module.common.model.Bill;
import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.utils.MapHelper;
import com.tz_tech.module.user.dao.UserDao;
import org.apache.commons.collections4.MapUtils;
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
    private UserDao userDao;

    @Autowired
    private ActivityService activityService;

    @Transactional(rollbackFor = Exception.class)
    public Result saveBill(Bill bill) throws Exception{
        Result result = Result.fail();

        //生成唯一订单号
        bill.setId(genBusinessFlowNum());

        //javabean转map
        Map<String,Object> paramMap = MapHelper.beanToMap(bill);

        //订单信息业务主表入库
        billdao.saveBill(paramMap);
        //箱信息入库
        billdao.saveBillContainer(paramMap);
        //货物信息入库
        billdao.saveBillHand(paramMap);
        //启动流程,获取流程实例ID
        String processInstanceId = activityService.startProcess(MapUtils.getString(paramMap,"id"));

        //将获取到的流程实例ID入库,同时将订单表的状态改为流程启动中-10N
        paramMap.put("processInstanceId",processInstanceId);
//            bill.setProcessInstanceId(processInstanceId);
        billdao.updateOrderForInstId(paramMap);
        result = Result.success();
        return result;
    }

    public Result queryBillByLoginName(Map<String,Object> paramMap) throws Exception{
        Result result = Result.fail();
        //每个角色有不同的权限--这里只区分操作经理，操作，管理员
        List<Map<String,Object>> roles = userDao.qryUserRoleByUserName(MapUtils.getString(paramMap,"loginName"));
        paramMap.put("role",MapUtils.getString(roles.get(0),"role"));
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
        //查询船名
        List<Map<String,Object>> shipName = billdao.queryDictById(Long.valueOf(7));
        //查询业务种类 1代表业务种类
        List<Map<String,Object>> businessType = billdao.queryDictById(Long.valueOf(1));
        //查询箱属 2
        List<Map<String,Object>> conBelong = billdao.queryDictById(Long.valueOf(2));
        //查询箱型 3
        List<Map<String,Object>> conType = billdao.queryDictById(Long.valueOf(3));
        //运输协议 4
        List<Map<String,Object>> protocolType = billdao.queryDictById(Long.valueOf(4));
        //包装种类 5
        List<Map<String,Object>> packingType = billdao.queryDictById(Long.valueOf(5));
        //免箱使计算方式 6
        List<Map<String,Object>> detentionType = billdao.queryDictById(Long.valueOf(6));
        //查询地址
        List<Map<String,Object>> addrInfo = billdao.queryAddrInfo(null);
        //查询贸易类型 9
        List<Map<String,Object>> tradeType = billdao.queryDictById(Long.valueOf(9));
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("cust",cust);
        data.put("ship",ship);
        data.put("businessType",businessType);
        data.put("conBelong",conBelong);
        data.put("conType",conType);
        data.put("protocolType",protocolType);
        data.put("packingType",packingType);
        data.put("detentionType",detentionType);
        data.put("addrInfo",addrInfo);
        data.put("shipName",shipName);
        data.put("tradeType",tradeType);
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
