package com.tz_tech.module.business.bill.controller;

import com.tz_tech.module.business.bill.service.BillService;
import com.tz_tech.module.common.model.Bill;
import com.tz_tech.module.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/BillService")
public class BillServiceController {

    @Autowired
    private BillService billService;

    /**
     * 单据录入
     * @return
     */
    @RequestMapping(value = "/saveBill.do")
    @ResponseBody
    public Result saveBill(@RequestBody(required=false) Bill bill, HttpServletRequest request)throws Exception{
        Result result = Result.fail();
        try {
            result = billService.saveBill(bill);
        } catch (Exception e) {
            e.getStackTrace();
            result.setResultMsg("单据入库失败!");
        }
        return result;
    }

    /**
     * 单据查询
     * 查询在途库
     */
    @RequestMapping(value = "/queryBillByIng.do",method = RequestMethod.GET)
    @ResponseBody
    public Result queryBillByLoginName(@RequestParam("loginName") String loginName,  @RequestParam("page") String page,
                                       @RequestParam("pageSize") String pageSize,@RequestParam(value="waybill",required=false) String waybill, @RequestParam(value="tacheId",required=false) String tacheId,
                                       @RequestParam(value="businessType",required=false) String businessType,HttpServletRequest request)throws Exception{
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loginName",loginName);
        paramMap.put("page",Long.valueOf(page));
        paramMap.put("pageSize",Long.valueOf(pageSize));
        paramMap.put("waybill",waybill);
        paramMap.put("tacheId",tacheId);
        paramMap.put("businessType",businessType);
        return billService.queryBillByLoginName(paramMap);
    }

    /**
     * 查询共用信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryCommonInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public Result queryCommonInfo(HttpServletRequest request)throws Exception{
        return billService.queryCommonInfo();
    }


    /**
     * 订单作废
     * @param groupId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteOrder.do",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteOrder(@RequestBody List<String> groupId, HttpServletRequest request) throws Exception{
        Result result = Result.fail();
        try {
            result = billService.deleteOrder(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultMsg("订单作废失败");
        }
        return result;
    }

    /**
     *
     * @param orderId
     * @param tacheCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBillRejectReason.do",method = RequestMethod.GET)
    @ResponseBody
    public Result queryBillRejectReason(@RequestParam("orderId") String orderId,
                                        @RequestParam("tacheCode") String tacheCode)throws Exception{
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId",orderId);
        paramMap.put("tacheCode",tacheCode);
        return billService.queryBillRejectReason(paramMap);
    }

    /**
     * 确认接单-按钮
     * @param groupId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/confirmAcceptOrder.do",method = RequestMethod.POST)
    @ResponseBody
    public Result confirmAcceptOrder(@RequestBody List<String> groupId, HttpServletRequest request) throws Exception{
        return billService.confirmAcceptOrder(groupId);
    }

    /**
     * 调度审核确定按钮
     * @param paramMap
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dispatcherAudit.do",method = RequestMethod.POST)
    @ResponseBody
    public Result dispatcherAudit(@RequestBody Map<String,Object> paramMap, HttpServletRequest request) throws Exception{
        /*Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("auditResult",auditResult);
        paramMap.put("auditNote",auditNote);
        paramMap.put("orderId",orderId);
        paramMap.put("workOrderId",workOrderId);*/
        return billService.confirmDispatcherAudit(paramMap);
    }

}
