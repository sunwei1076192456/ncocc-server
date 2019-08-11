package com.tz_tech.module.business.bill.service;

import com.tz_tech.module.business.activity.service.ActivityService;
import com.tz_tech.module.business.bill.dao.BillDao;
import com.tz_tech.module.common.model.Bill;
import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.utils.BaseInfoLoadFromDB;
import com.tz_tech.module.common.utils.MapHelper;
import com.tz_tech.module.user.dao.UserDao;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

    private static final Logger log = Logger.getLogger(BillService.class);


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

        //查看是否被驳回
        List<Map<String,Object>> rejectReason = billdao.isExistDisReject(paramMap);
        covertOrderInfo(bills,rejectReason);

        //查询出单据总量
        Long totalCount = billdao.queryBillsCountBying(paramMap);

        Map<String,Object> data = new HashMap<String, Object>();
        data.put("bill",bills);
        data.put("totalCount",totalCount);
        result = Result.success(data);
        return result;
    }

    private void covertOrderInfo(List<Map<String,Object>> bills,List<Map<String,Object>> rejectReason){
        if(rejectReason != null && rejectReason.size() > 0){
            for(Map<String,Object> bill : bills){
                bill.put("isDisReject","N");
                bill.put("isCarReject","N");
                for(Map<String,Object> reject : rejectReason){
                    if(MapUtils.getString(bill,"id","").equals(MapUtils.getString(reject,"id"))){
                        if("1".equals(MapUtils.getString(reject,"workResult")) &&
                            "HB-DD-SH".equals(MapUtils.getString(reject,"tacheCode"))){
                            bill.put("isDisReject","Y");
                            bill.put("disRejectTache","HB-DD-SH");
                            break;
                        }
                    }
                }
                for(Map<String,Object> reject : bills){
                    if(MapUtils.getString(bill,"id","").equals(MapUtils.getString(reject,"id"))){
                        if("1".equals(MapUtils.getString(reject,"workResult")) &&
                             "HB-CG-SH".equals(MapUtils.getString(reject,"tacheCode"))){
                            bill.put("isCarReject","Y");
                            bill.put("carRejectTache","HB-CG-SH");
                            break;
                        }
                    }
                }
            }
        }
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
        //查询省市区县
        List<Map<String,Object>> district = (ArrayList)BaseInfoLoadFromDB.dispatcherMap.get("district");
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
        data.put("district",district);
        result = Result.success(data);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Result deleteOrder(List<String> groupId)throws Exception{
        Result result = Result.fail();
        if(groupId != null && groupId.size() > 0){
            //作废订单
            modifyOrderState("10R",groupId);//10R:退单
            result = Result.success();
        }else{
            result.setResultMsg("请至少勾选一条记录作废!");
        }
        return result;
    }

    public void modifyOrderState(String state,List<String> orderIdList)throws Exception{
        //
        String[] order = orderIdList.toArray(new String[orderIdList.size()]);
        billdao.updateOrderState(state,order);
    }

    public Result queryBillRejectReason(Map<String,Object> paramMap) throws Exception{
        Result result = Result.fail();
        List<Map<String,Object>> rejectReason = billdao.queryRejectReason(paramMap);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("rejectReasonList",rejectReason);
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

    public Result confirmAcceptOrder(List<String> groupId) throws Exception{
        Result result = Result.fail();
        if(groupId != null && groupId.size() > 0){
            //list转为以逗号分隔的字符串
            try {
                String ids = StringUtils.join(groupId.toArray(), "','");
                //查询出所有的流程实例ID
                List<Map<String,Object>> processInstanceIdList = billdao.queryProcessInstanceIdById(ids);
                if(null != processInstanceIdList && processInstanceIdList.size() > 0){
                    for(Map<String,Object> temp : processInstanceIdList){
                        String processInstanceId = MapUtils.getString(temp,"processInstanceId","");
                        if(!"".equals(processInstanceId)){
                            //通用回单方法,不需要指定下一环节执行人
                            activityService.completeWorkOrder(processInstanceId,false,null);
                            log.info("订单号【" + MapUtils.getString(temp,"id","") + "】确认接单成功！");
                        }
                    }
                }
                result = Result.success();
            } catch (Exception e) {
                e.printStackTrace();
                result.setResultMsg("接单失败!");
            }
        }else {
            result.setResultMsg("请至少勾选一条记录接单!");
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Result confirmDispatcherAudit(Map<String,Object> paramMap)throws Exception{
        Result result = Result.fail();
        //写入审核结果 0:通过  1：不通过
        billdao.updateWorkOrderResult(paramMap);

        //回单
        List<Map<String,Object>> processInstanceIdList = billdao.queryProcessInstanceIdById(MapUtils.getString(paramMap,"orderId"));
        Map<String,Object> variables = new HashMap<>();
        variables.put("isPass",MapUtils.getInteger(paramMap,"auditResult"));
        String processInstanceId = MapUtils.getString(processInstanceIdList.get(0),"processInstanceId","");
        activityService.completeWorkOrder(processInstanceId,true,variables);
        result = Result.success();
        return result;
    }

}
