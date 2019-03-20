package com.tz_tech.module.business.bill.dao;

import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.Bill;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BillDaoImpl extends CommonDao implements BillDao {

    @Override
    public int saveBill(Bill bill) throws Exception {
        StringBuffer sb = new StringBuffer();
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

        Map<String,Object> paramMap = new HashMap<String, Object>();
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
        paramMap.put("contact_id",bill.getContact_id());
        return super.update(sb.toString(),paramMap);
    }

    @Override
    public int updateOrderForInstId(Bill bill) throws Exception {
        String sql = "update ff_order set status_id='10N',processInstanceId=:processInstanceId where id=:id ";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("processInstanceId",bill.getProcessInstanceId());
        paramMap.put("id",bill.getId());
        return super.update(sql,paramMap);
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

    @Override
    public List<Map<String, Object>> queryBillsByLoginName(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id,create_by,create_at,updated_by,updated_at,remark,");
        sb.append(" status_id,businesstype_code,customer_id,shipping_id,sailingdate,");
        sb.append(" shipname_code,voyage,containerbelong_code,containertype_code,");
        sb.append(" protocoltype_code,waybill,containernumber,deliveryplace_code,");
        sb.append(" sealnumber,packingtype_code,productname,productweight,productvolume,");
        sb.append(" productnubmer,is_agent,demurrage,detention,detentiontype_code,");
        sb.append(" is_deduction,loadingdate,district_id,loadingdetile_id,contact_id,processInstanceId,status,cusabbreviation,shipabbreviation from (");
        sb.append(" select oo.id,oo.create_by,oo.create_at,oo.updated_by,oo.updated_at,oo.remark,");
        sb.append(" oo.status_id,oo.businesstype_code,oo.customer_id,oo.shipping_id,oo.sailingdate,");
        sb.append(" oo.shipname_code,oo.voyage,oo.containerbelong_code,oo.containertype_code,");
        sb.append(" oo.protocoltype_code,oo.waybill,oo.containernumber,oo.deliveryplace_code,");
        sb.append(" oo.sealnumber,oo.packingtype_code,oo.productname,oo.productweight,oo.productvolume,");
        sb.append(" oo.productnubmer,oo.is_agent,oo.demurrage,oo.detention,oo.detentiontype_code,");
        sb.append(" oo.is_deduction,oo.loadingdate,oo.district_id,oo.loadingdetile_id,oo.contact_id,");
        sb.append(" oo.processInstanceId,dtd.tache_name as status,uc.abbreviation as cusabbreviation,us.abbreviation as shipabbreviation from ff_work_order_ing woi ");
        sb.append(" left join ff_order oo on woi.order_id=oo.id ");
        sb.append(" left join dict_tache_define dtd on dtd.tache_id=woi.tache_id ");
        sb.append(" left join user_customer uc on uc.id=oo.customer_id ");
        sb.append(" left join user_shipping us on us.id=oo.shipping_id ");
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
        sb.append(" where woi.executor_id=:loginName");
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
        sb.append(" select id,abbreviation,mnemonic,name,address,contact,telephone");
        sb.append(" from user_shipping ");
        return super.queryForList(sb.toString(),new HashMap<String, Object>());
    }
}
