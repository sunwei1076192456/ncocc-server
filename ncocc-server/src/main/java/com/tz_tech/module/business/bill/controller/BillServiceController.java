package com.tz_tech.module.business.bill.controller;

import com.tz_tech.module.business.bill.service.BillService;
import com.tz_tech.module.common.model.Bill;
import com.tz_tech.module.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
        return billService.saveBill(bill);
    }

    /**
     * 单据查询
     * 查询在途库
     */
    @RequestMapping(value = "/queryBillByIng.do",method = RequestMethod.GET)
    @ResponseBody
    public Result queryBillByLoginName(@RequestParam("loginName") String loginName,  @RequestParam("page") String page,
                                       @RequestParam("pageSize") String pageSize,HttpServletRequest request)throws Exception{
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loginName",loginName);
        paramMap.put("page",Long.valueOf(page));
        paramMap.put("pageSize",Long.valueOf(pageSize));
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

}
