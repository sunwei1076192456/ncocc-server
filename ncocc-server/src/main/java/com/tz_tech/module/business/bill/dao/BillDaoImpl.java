package com.tz_tech.module.business.bill.dao;

import com.google.common.collect.ImmutableMap;
import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.Bill;
import com.tz_tech.module.common.model.OrderStaticData;
import com.tz_tech.module.common.utils.ProcedureUtil;
import com.tz_tech.module.common.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BillDaoImpl extends CommonDao implements BillDao {

    /**
     * ff_order
     * 业务主表-参数名和数据库表名的对应关系，用于构造sql
     */
    private static final Map<String, String> ffOrderColumnPairs = ImmutableMap.<String,String>builder()
            .put("id"			           , "id"			        )
            .put("create_by" 	           , "create_by"	        )
//            .put("remark"		           , "remark"			    )
//            .put("status_id"			   , "status_id"	        )
            .put("businesstype_code"       , "businesstype_code"   )
            .put("customer_id"		       , "customer_id"		    )
            .put("shipping_id"	           , "shipping_id"		    )
            .put("forecastsailingdate"	   , "forecastsailingdate"	)
            .put("sailingdate"	           , "sailingdate"		    )
            .put("shipname_code"	       , "shipname_code"	    )
            .put("voyage"	               , "voyage"		        )
            .put("waybill"	               , "waybill"		        )
            .put("tradetype_code"	       , "tradetype_code"       )
            .put("protocoltype_code"	   , "protocoltype_code"    )
            .put("packingtype_code"	       , "packingtype_code"		)
            .put("containerweight"	       , "containerweight"		)
            .put("productname"	           , "productname"		    )
            .put("productweight"	       , "productweight"	    )
            .put("productvolume"	       , "productvolume"	    )
            .put("productnubmer"	       , "productnubmer"		)
            .build();

    /**
     * ff_container_info
     * 箱体表-参数名和数据库表名的对应关系，用于构造sql
     */
    private static final Map<String, String> ffContainerInfoColumnPairs = ImmutableMap.<String,String>builder()
//            .put("id"			           , "id"			            )
            .put("containernumber" 	       , "containernumber"	        )
            .put("containerbelong_code"	   , "containerbelong_code"		)
            .put("containertype_code"	   , "containertype_code"	     )
            .put("sealnumber"              , "sealnumber"                )
            .put("detention"		       , "detention"		         )
            .put("draw_container_addr"	   , "draw_container_addr"	     )
            .put("return_container_addr"   , "return_container_addr"	 )
            .put("draw_container_time"	   , "draw_container_time"		 )
            .put("return_container_time"   , "return_container_time"	 )
            .put("demurrage"	           , "demurrage"		         )
            .put("port_container_time"	   , "port_container_time"		 )
            .put("ship_container_time"	   , "ship_container_time"       )
            .put("remark"	               , "remark"                    )
            .build();

    /**
     * ff_handling_info
     * 装卸信息表-参数名和数据库表名的对应关系，用于构造sql
     */
    private static final Map<String, String> ffHandlingInfoColumnPairs = ImmutableMap.<String,String>builder()
//            .put("id"			           , "id"			            )
            .put("loading_goods_addr" 	   , "loading_goods_addr"	    )
            .put("unloading_goods_addr"	   , "unloading_goods_addr"		)
            .put("start_time"	           , "start_time"	            )
            .put("end_time"                , "end_time"                )
            .put("is_deduction"		       , "is_deduction"		        )
            .put("is_urgent"	           , "is_urgent"	            )
            .put("is_delay"                , "is_delay"	                )
            .put("contact"	               , "contact"		            )
            .put("contact_res"             , "contact_res"	            )
            .put("iphone"	               , "iphone"		            )
            .put("iphone_res"	           , "iphone_res"		        )
            .put("handle_remark"	       , "handle_remark"            )
            .put("handle_point"            , "handle_point"             )
            .put("receive_goods_state"     , "receive_goods_state"      )
            .put("good_pallet_code"        , "good_pallet_code"         )
            .build();


    @Override
    public int saveBill(Map<String,Object> bill) throws Exception {
        StringBuffer cloumStr = new StringBuffer();
        StringBuffer valueStr = new StringBuffer();
        cloumStr.append(" insert into ff_order(create_at,status_id ");
        valueStr.append(" values(SYSDATE(),'10I' ");
        for(String oneKey : bill.keySet()){
            if(ffOrderColumnPairs.containsKey(oneKey)){
                cloumStr.append(",")
                        .append(ffOrderColumnPairs.get(oneKey) + " ");
                if("forecastsailingdate".equals(oneKey) ||
                        "sailingdate".equals(oneKey)){
                    valueStr.append(",")
                            .append("str_to_date(:" + oneKey + ",'%Y-%m-%d') ");
                }else{
                    valueStr.append(",")
                            .append(":" + oneKey + " ");
                }
            }
        }


        /*StringBuffer sb = new StringBuffer();
        sb.append(" insert into ff_order(id,create_by,create_at,");
        sb.append(" remark,status_id,businesstype_code,customer_id,shipping_id,sailingdate,");
        sb.append(" shipname_code,voyage,containerbelong_code,containertype_code,protocoltype_code,");
        sb.append(" waybill,containernumber,deliveryplace_code,sealnumber,packingtype_code,");
        sb.append(" productname,productweight,productvolume,productnubmer,is_agent,demurrage,");
        sb.append(" detention,detentiontype_code,is_deduction,loadingdate,district_id,loadingdetile_id,contact_id)");
        sb.append(" values(:id,:create_by,SYSDATE(),:remark,:status_id,:businesstype_code,:customer_id,:shipping_id,:sailingdate,");
        sb.append(" :shipname_code,:voyage,:containerbelong_code,:containertype_code,:protocoltype_code,");
        sb.append(" :waybill,:containernumber,:deliveryplace_code,:sealnumber,:packingtype_code,");
        sb.append(" :productname,:productweight,:productvolume,:productnubmer,:is_agent,:demurrage,");
        sb.append(" :detention,:detentiontype_code,:is_deduction,:loadingdate,:district_id,:loadingdetile_id,:contact_id)");
*/
        /*Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",bill.getId());
        paramMap.put("create_by",bill.getCreate_by());
        paramMap.put("remark",bill.getRemark());
        paramMap.put("status_id",bill.getStatus_id()==null?"10I":bill.getStatus_id());
        paramMap.put("businesstype_code",bill.getBusinesstype_code());
        paramMap.put("customer_id",bill.getCustomer_id());
        paramMap.put("shipping_id",bill.getShipping_id());
        paramMap.put("sailingdate",bill.getSailingdate());
        paramMap.put("shipname_code",bill.getShipname_code());
        paramMap.put("voyage",bill.getVoyage());
        paramMap.put("containerbelong_code",bill.getContainerbelong_code());
        paramMap.put("containertype_code",bill.getContainertype_code());
        paramMap.put("protocoltype_code",bill.getProtocoltype_code());
        paramMap.put("waybill",bill.getWaybill());
        paramMap.put("containernumber",bill.getContainernumber());
        paramMap.put("deliveryplace_code",bill.getDeliveryplace_code());
        paramMap.put("sealnumber",bill.getSealnumber());
        paramMap.put("packingtype_code",bill.getPackingtype_code());
        paramMap.put("productname",bill.getProductname());
        paramMap.put("productweight",bill.getProductweight());
        paramMap.put("productvolume",bill.getProductvolume());
        paramMap.put("productnubmer",bill.getProductnubmer());
        paramMap.put("is_agent",bill.getIs_agent());
        paramMap.put("demurrage",bill.getDemurrage());
        paramMap.put("detention",bill.getDetention());
        paramMap.put("detentiontype_code",bill.getDetentiontype_code());
        paramMap.put("is_deduction",bill.getIs_deduction());
        paramMap.put("loadingdate",bill.getLoadingdate());
        paramMap.put("district_id",bill.getDistrict_id());
        paramMap.put("loadingdetile_id",bill.getLoadingdetile_id());
        paramMap.put("contact_id",bill.getContact_id());*/

        cloumStr.append(") ");
        valueStr.append(") ");
        return super.update(cloumStr.append(valueStr).toString(),bill);
    }

    @Override
    public int saveBillContainer(Map<String, Object> paramMap) throws Exception {
        StringBuffer cloumStr = new StringBuffer();
        StringBuffer valueStr = new StringBuffer();
        cloumStr.append(" insert into ff_container_info(id ");
        valueStr.append(" values(:id ");
        for(String oneKey : paramMap.keySet()){
            if(ffContainerInfoColumnPairs.containsKey(oneKey)){
                cloumStr.append(",")
                        .append(ffContainerInfoColumnPairs.get(oneKey) + " ");
                if(("draw_container_time".equals(oneKey) && !"".equals(MapUtils.getString(paramMap,"oneKey",""))) ||
                        ("draw_container_time".equals(oneKey) && !"".equals(MapUtils.getString(paramMap,"oneKey",""))) ||
                        ("port_container_time".equals(oneKey) && !"".equals(MapUtils.getString(paramMap,"oneKey",""))) ||
                        ("ship_container_time".equals(oneKey) && !"".equals(MapUtils.getString(paramMap,"oneKey","")))){
                    valueStr.append(",")
                            .append("str_to_date(:" + oneKey + ",'%Y-%m-%d %H:%i:%s') ");
                }else{
                    valueStr.append(",")
                            .append(":" + oneKey + " ");
                }
            }
        }
        cloumStr.append(") ");
        valueStr.append(") ");
        return super.update(cloumStr.append(valueStr).toString(),paramMap);
    }

    @Override
    public int saveBillHand(Map<String, Object> paramMap) throws Exception {
        StringBuffer cloumStr = new StringBuffer();
        StringBuffer valueStr = new StringBuffer();
        cloumStr.append(" insert into ff_handling_info(id ");
        valueStr.append(" values(:id ");
        for(String oneKey : paramMap.keySet()){
            if(ffHandlingInfoColumnPairs.containsKey(oneKey)){
                cloumStr.append(",")
                        .append(ffHandlingInfoColumnPairs.get(oneKey) + " ");
                if(("start_time".equals(oneKey) && !"".equals(MapUtils.getString(paramMap,"oneKey",""))) ||
                        ("end_time".equals(oneKey) && !"".equals(MapUtils.getString(paramMap,"oneKey","")))){
                    valueStr.append(",")
                            .append("str_to_date(:" + oneKey + ",'%Y-%m-%d %H:%i:%s') ");
                }else{
                    valueStr.append(",")
                            .append(":" + oneKey + " ");
                }
            }
        }
        cloumStr.append(") ");
        valueStr.append(") ");
        return super.update(cloumStr.append(valueStr).toString(),paramMap);
    }

    @Override
    public int updateOrderForInstId(Map<String,Object> bill) throws Exception {
        String sql = "update ff_order set status_id='10N',processInstanceId=:processInstanceId where id=:id ";
        return super.update(sql,bill);
    }

    @Override
    public Map<String, Object> queryPartyById(String id) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select fo.id,fo.create_by as executor_id,su.name as executor_name, ");
        sb.append(" sr.role as executor_type from ff_order fo");
        sb.append(" left join sys_user su on fo.create_by=su.login_name");
        sb.append(" left join sys_user_role sur on fo.create_by=sur.user_login_name");
        sb.append(" left join sys_role sr on sur.role_id=sr.id");
        sb.append(" where fo.id=:id");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",id);
        List<Map<String,Object>> ret = super.queryForList(sb.toString(),paramMap);
        if(ret != null && ret.size() > 0){
            return ret.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> queryTacheByActiviId(String id) throws Exception {
        String sql = "select tache_id,tache_code,tache_name from dict_tache_define where state='10A' and tache_code=:id ";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",id);
        return super.queryForMap(sql,paramMap);
    }

    @Override
    public int insertFlowMsg(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into ff_flow_msg(serial_id,order_id,command_code,");
        sb.append(" send_content,processInstanceId,create_at) values(");
        sb.append(" :serial_id,:order_id,:command_code,:send_content,:processInstanceId,SYSDATE())");
        return super.update(sb.toString(),paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> queryBillsByLoginName(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select workOrderId,id,create_by,create_at,");
        sb.append(" status_id,businesstype_code,customer_id,shipping_id,sailingdate,");
        sb.append(" shipname_code,voyage,containerbelong_code,");
        sb.append(" waybill,containernumber,containertype_code,unloading_goods_addr,contact,iphone,");
        sb.append(" loading_goods_addr,draw_container_addr,return_container_addr,");
        sb.append(" receive_goods_state,handle_remark,start_time,end_time,");
        sb.append(" status,cusabbreviation,shipabbreviation from (");
        sb.append(" select woi.id as workOrderId,oo.id,oo.create_by,oo.create_at,");
        sb.append(" oo.status_id,oo.businesstype_code,oo.customer_id,oo.shipping_id,date_format(oo.sailingdate,'%Y-%m-%d') as sailingdate,");
        sb.append(" oo.shipname_code,oo.voyage,oo.waybill,");
        sb.append(" fci.containernumber,fci.containertype_code,fci.containerbelong_code,");
        //新增提箱地址，反箱地址,装货，卸货地址
        sb.append(" fhi.loading_goods_addr as loading_goods_addr,sa2.address_abbr as draw_container_addr,sa3.address_abbr as return_container_addr, ");
        //新增送货时间，装货时间
        sb.append(" date_format(fhi.start_time,'%Y-%m-%d %H:%i:%s') as start_time,");
        sb.append(" date_format(fhi.end_time,'%Y-%m-%d %H:%i:%s') as end_time,");
        //新增装卸备注
        sb.append(" fhi.receive_goods_state,fhi.handle_remark as handle_remark,");
        sb.append(" fhi.unloading_goods_addr as unloading_goods_addr,fhi.contact,fhi.iphone,");
        sb.append(" dtd.tache_name as status,uc.abbreviation as cusabbreviation,us.abbreviation as shipabbreviation from ff_work_order_ing woi ");
        sb.append(" left join ff_order oo on woi.order_id=oo.id ");
        sb.append(" left join dict_tache_define dtd on dtd.tache_id=woi.tache_id ");
        sb.append(" left join user_customer uc on uc.id=oo.customer_id ");
        sb.append(" left join user_shipping us on us.id=oo.shipping_id ");
        sb.append(" left join ff_container_info fci on fci.id=oo.id ");
        sb.append(" left join ff_handling_info fhi on fhi.id=oo.id ");
//        sb.append(" left join sys_addr sa on sa.addr_id=fhi.unloading_goods_addr");//卸货地址
//        sb.append(" left join sys_addr sa1 on sa1.addr_id=fhi.loading_goods_addr");//装货地址
        sb.append(" left join sys_addr sa2 on sa2.addr_id=fci.draw_container_addr");//提箱地址
        sb.append(" left join sys_addr sa3 on sa3.addr_id=fci.return_container_addr");//返箱地址
        String role = MapUtils.getString(paramMap,"role","");
        switch (role){
            case "management":
                sb.append(" where 1=1 ");
                break;
            case "operatorLeader":
                sb.append(" where 1=1 ");//---后续已区域来区分，每个调度经理能看到各自区域下的单子
                break;
            case "operator":
                sb.append(" where woi.executor_id=:loginName ");
                break;
            case "dispatcher":
                sb.append(" where woi.executor_id=:loginName ");
                break;
        }
        if(!"".equals(MapUtils.getString(paramMap,"waybill",""))){
            sb.append(" and oo.waybill like '%" + MapUtils.getString(paramMap,"waybill") + "%' ");
        }
        if(!"".equals(MapUtils.getString(paramMap,"tacheId",""))){
            sb.append(" and woi.tache_id in (" + MapUtils.getString(paramMap,"tacheId","") + ")");
        }
        if(!"".equals(MapUtils.getString(paramMap,"businessType",""))){
            sb.append(" and oo.businesstype_code in (" + MapUtils.getString(paramMap,"businessType","") + ")");
        }
        sb.append(" order by receive_goods_state,sailingdate ");
        sb.append(") as lo limit :fromPageSize,:pageSize ");
        paramMap.put("fromPageSize",(MapUtils.getLong(paramMap,"page")-1)*MapUtils.getLong(paramMap,"pageSize"));
        return super.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String, Object>> queryBillsDetailById(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id,create_by,create_at,updated_by,updated_at,remark,");
        sb.append(" status_id,businesstype_code,customer_id,shipping_id,forecastsailingdate,sailingdate,");
        sb.append(" shipname_code,voyage,containerbelong_code,containertype_code,");
        sb.append(" protocoltype_code,waybill,containernumber,");
        sb.append(" sealnumber,packingtype_code,productname,productweight,productvolume,");
        sb.append(" productnubmer,demurrage,detention,");
        sb.append(" is_deduction,contact_id,processInstanceId,status,cusabbreviation,shipabbreviation from (");
        sb.append(" select oo.id,oo.create_by,oo.create_at,oo.updated_by,oo.updated_at,oo.remark,");
        sb.append(" oo.status_id,oo.businesstype_code,oo.customer_id,oo.shipping_id,oo.forecastsailingdate,oo.sailingdate,");
        sb.append(" oo.shipname_code,oo.voyage,oo.waybill,oo.tradetype_code,oo.protocoltype_code,oo.packingtype_code,");
        sb.append(" oo.containerweight,oo.productname,oo.productweight,oo.productvolume,oo.productnubmer,oo.processInstanceId,");
        sb.append(" fci.containernumber,fci.containerbelong_code,fci.containertype_code,fci.sealnumber,fci.detention,");
        sb.append(" draw_container_addr,oo.demurrage,oo.detention,");
        sb.append(" oo.is_deduction,oo.contact_id,");
        sb.append(" oo.processInstanceId,dtd.tache_name as status,uc.abbreviation as cusabbreviation,us.abbreviation as shipabbreviation from ff_work_order_ing woi ");
        sb.append(" left join ff_order oo on woi.order_id=oo.id ");
        sb.append(" left join dict_tache_define dtd on dtd.tache_id=woi.tache_id ");
        sb.append(" left join user_customer uc on uc.id=oo.customer_id ");
        sb.append(" left join user_shipping us on us.id=oo.shipping_id ");
        sb.append(" left join ff_container_info fci on fci.id=oo.id ");
        sb.append(" left join ff_handling_info fhi on fhi.id=oo.id ");
        sb.append(" left join sys_addr sa on sa.addr_id=fci.");
        sb.append(" where woi.executor_id=:loginName");
        sb.append(") as lo limit :fromPageSize,:pageSize ");
        paramMap.put("fromPageSize",(MapUtils.getLong(paramMap,"page")-1)*MapUtils.getLong(paramMap,"pageSize"));
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public Long queryBillsCountBying(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) ");
        sb.append(" from ff_work_order_ing woi ");
        sb.append(" left join ff_order oo on woi.order_id=oo.id ");
        String role = MapUtils.getString(paramMap,"role","");
        switch (role){
            case "management":
                sb.append(" where 1=1 ");
                break;
            case "operatorLeader":
                sb.append(" where 1=1 ");
                break;       //---后续已区域来区分，每个调度经理能看到各自区域下的单子
            case "operator":
                sb.append(" where woi.executor_id=:loginName ");
                break;
            case "dispatcher":
                sb.append(" where woi.executor_id=:loginName ");
                break;
        }
        if(!"".equals(MapUtils.getString(paramMap,"waybill",""))){
            sb.append(" and oo.waybill like '%" + MapUtils.getString(paramMap,"waybill") + "%' ");
        }
        if(!"".equals(MapUtils.getString(paramMap,"tacheId",""))){
            sb.append(" and woi.tache_id in (" + MapUtils.getString(paramMap,"tacheId","") + ")");
        }
        if(!"".equals(MapUtils.getString(paramMap,"businessType",""))){
            sb.append(" and oo.businesstype_code in (" + MapUtils.getString(paramMap,"businessType","") + ")");
        }
        return super.queryForObject(sb.toString(),paramMap,Long.class);
    }

    @Override
    public List<Map<String, Object>> queryAllCusInfo() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id,abbreviation,mnemonic,customertype_code,name,");
        sb.append(" address,contact,telephone from user_customer");
        return super.queryForList(sb.toString(),new HashMap<String, Object>());
    }

    @Override
    public List<Map<String, Object>> queryAllShipInfo() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id as value,abbreviation,mnemonic,name,address,contact,telephone");
        sb.append(" from user_shipping ");
        return super.queryForList(sb.toString(),new HashMap<String, Object>());
    }

    @Override
    public List<Map<String, Object>> queryDictById(Long id) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select dpd.id as value,dpd.name as label ");
        sb.append(" from dict_public_detail dpd,dict_public dp ");
        sb.append(" where dpd.public_id=dp.id and dp.id=:id and is_display=1");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",id);
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public List<Map<String, Object>> queryAddrInfo(Long areaId) throws Exception {
        StringBuffer sb = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        sb.append(" select addr_id as value,factory_name as label,district_id ");
        sb.append(" from sys_addr ");
        if(areaId != null){
            sb.append(" where district_id=:areaId");
            paramMap.put("areaId",areaId);
        }
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public List<Map<String, Object>> queryProcessInstanceIdById(String workOrderIds) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select fo.processInstanceId,fo.id from ff_order fo ");
        sb.append(" where fo.id in ('" + workOrderIds + "')");
        return super.queryForList(sb.toString(),new HashMap<>());
    }

    @Override
    public Map<String, Object> queryWorkOrderIdByPId(String processInstanceId) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select fo.id,woi.id as workOrderId from ff_order fo ");
        sb.append(" left join ff_work_order_ing woi on woi.order_id=fo.id ");
        sb.append(" where fo.processInstanceId = :processInstanceId and fo.status_id = '10N' ");
        Map<String,Object> param = new HashMap<>();
        param.put("processInstanceId",processInstanceId);
        return super.queryForMap(sb.toString(),param);
    }

    @Override
    public void updateWorkOrderResult(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" update ff_work_order set work_result=:auditResult,remarks=:auditNote ");
        sb.append(" where id=:workOrderId ");
        super.update(sb.toString(),paramMap);
    }

    @Override
    public List<Map<String, Object>> queryRejectReason(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select foo.remarks as rejectReason,date_format(foo.finish_date,'%Y-%m-%d %H:%i:%s') as rejectTime,");
        sb.append(" foo.executor_name as rejectPerson from ff_work_order foo ");
        sb.append(" where foo.order_id=:orderId ");
        sb.append(" and foo.tache_code=:tacheCode and foo.work_result='1' ");
        sb.append(" order by foo.create_date ");
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public List<Map<String,Object>> isExistDisReject(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select woi.order_id as id,foo.work_result as workResult,foo.remarks,foo.tache_code as tacheCode ");
        sb.append(" from ff_work_order_ing woi ");
        sb.append(" left join ff_order oo on oo.id=woi.order_id ");
        String role = MapUtils.getString(paramMap,"role","");
        switch (role){
            case "management":
                sb.append(" left join ff_work_order foo on foo.order_id=woi.order_id and foo.tache_code in('HB-DD-SH','HB-CG-SH') ");
                sb.append(" where 1=1");
                break;
            case "operatorLeader":
                sb.append(" left join ff_work_order foo on foo.order_id=woi.order_id and foo.tache_code in('HB-DD-SH','HB-CG-SH') ");
                sb.append(" where 1=1");
                break;       //---后续已区域来区分，每个调度经理能看到各自区域下的单子
            case "operator":
                sb.append(" left join ff_work_order foo on foo.order_id=woi.order_id and foo.tache_code in('HB-DD-SH') ");
                sb.append(" where woi.executor_id=:loginName");
                break;
            case "dispatcher":
                sb.append(" left join ff_work_order foo on foo.order_id=woi.order_id and foo.tache_code in('HB-CG-SH') ");
                sb.append(" where woi.executor_id=:loginName");
                break;
        }
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public void updateOrderState(String state, String[] orderIdList) throws Exception {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("state",state);
        paramMap.put("remarks", OrderStaticData.ORDER_STATE.get(state));
        String condition = StringUtils.getQryCondtion(orderIdList,true);

        //修改工单为已完成
        String sql2 = "update ff_work_order set work_order_state='10F',remarks=:remarks,finish_date=SYSDATE() where id in " +
                "(select id from ff_work_order_ing where order_id in " + condition + ")";

        //删除在途单
        String sql1 = "delete from ff_work_order_ing where order_id in " + condition;

        //修改订单为退单
        String sql3 = "update ff_order set status_id=:state,remark=:remarks,updated_at=SYSDATE() where id in " + condition;

        super.update(sql2,paramMap);
        super.update(sql1,paramMap);
        super.update(sql3,paramMap);
    }

    @Override
    public List<Map<String, Object>> queryBillForRH(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select oo.id,oo.waybill,oo.businesstype_code,");
        sb.append(" fhi.loading_goods_addr,fhi.unloading_goods_addr,");
        sb.append(" fhi.start_time,fhi.end_time,");
        sb.append(" sa2.address_abbr as draw_container_addr,sa3.address_abbr as return_container_addr, ");
        sb.append(" fci.containertype_code ");
        sb.append(" from ff_work_order_ing woi ");
        sb.append(" join ff_order oo on woi.order_id=oo.id ");
        sb.append(" left join ff_container_info fci on fci.id=oo.id ");
        sb.append(" left join ff_handling_info fhi on fhi.id=oo.id ");
        sb.append(" left join sys_addr sa2 on sa2.addr_id=fci.draw_container_addr");//提箱地址
        sb.append(" left join sys_addr sa3 on sa3.addr_id=fci.return_container_addr");//返箱地址
        sb.append(" where woi.tache_id=:tacheId ");
        sb.append(" and woi.executor_id=:loginName ");
        sb.append(" and oo.businesstype_code <> :businessType ");
//        sb.append(" and not exists (select gr.order_id from group_order_relation gr where gr.group_id=oo.group_id)");
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public String updateBillInfoForRH(String workOrderIds) throws Exception {
        StringBuffer sb = new StringBuffer();
        String[] params = {"FF-GROUP-","out"};
        Map<String,Object> ret = ProcedureUtil.executeProcedure(params,1,"generateSequence");
        ret.put("out","FF-GROUP-" + MapUtils.getString(ret,"out"));
        sb.append(" update ff_order fo set fo.isGroup=1,fo.group_id=:out ");
        sb.append(" where fo.id in ('" + workOrderIds + "')");
        super.update(sb.toString(),ret);
        return MapUtils.getString(ret,"out","");
    }

    @Override
    public boolean checkIsExistGroupRela(String workOrderIds) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(gor.id) from group_order_relation gor ");
        sb.append(" where gor.order_id in ('" + workOrderIds + "')");
        sb.append(" and gor.state='10A' ");
        int ret = super.queryForInt(sb.toString(),new HashMap<>());
        if(ret > 0){
            return true;
        }
        return false;
    }

    @Override
    public void saveGroupOrderRecord(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into group_order_relation(group_id,order_id,create_date) ");
        sb.append(" values(:groupId,:orderId,sysdate())");
        super.update(sb.toString(),paramMap);
    }

    @Override
    public void clearGroupOrderRecord(String workOrderIds) throws Exception {
        StringBuffer sb = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        StringBuffer str = new StringBuffer();
        str.append(" select fo.group_id as groupId from ff_order fo where fo.id in ('" + workOrderIds + "')");
        List<String> ret = super.queryForList(str.toString(),new HashMap<>(),String.class);
        String groupIdList = StringUtils.getQryCondtion(ret.toArray(new String[ret.size()]),true);
        sb.append(" update ff_order fo set fo.isGroup=0,fo.group_id=null ");
        sb.append(" where fo.id in ('" + workOrderIds + "')");

        sql.append(" update group_order_relation gor set gor.state='10P',gor.updated_date=sysdate() ");
        sql.append(" where gor.group_id in " + groupIdList);

        super.update(sb.toString(),new HashMap<>());
        super.update(sql.toString(),new HashMap<>());
    }
}
