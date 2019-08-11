package com.tz_tech.module.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Bill implements Serializable {

    private String id;

    private String create_by;

    private Date create_at;

    private String updated_by;

    private Date updated_at;

//    private String remark;

    private String status_id;

    private int businesstype_code;

    private int customer_id;

    private int shipping_id;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private String sailingdate;

    private String forecastsailingdate;

    private int shipname_code;

    private String voyage;

    private String waybill;

    private int tradetype_code;

    private int protocoltype_code;

    private int packingtype_code;

    private int containerweight;

    private String productname;

    private BigDecimal productweight;

    private BigDecimal productvolume;

    private BigDecimal productnubmer;

    private int containerbelong_code;

    private int containertype_code;

    private String containernumber;

    private String sealnumber;

    private int detention;

    private int draw_container_addr;

    private int return_container_addr;

    private String draw_container_time;

    private String return_container_time;

    private int demurrage;

    private String port_container_time;

    private String ship_container_time;

    private String remark;

    private String loading_goods_addr;

    private String unloading_goods_addr;

    private String start_time;

    private String end_time;

    private int is_deduction;

    private int is_urgent;

    private int is_delay;

    private String contact;

    private String contact_res;

    private String iphone;

    private String iphone_res;

    private String handle_remark;

    private String handle_point;

    private String receive_goods_state;

    private String good_pallet_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public int getBusinesstype_code() {
        return businesstype_code;
    }

    public void setBusinesstype_code(int businesstype_code) {
        this.businesstype_code = businesstype_code;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(int shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getSailingdate() {
        return sailingdate;
    }

    public void setSailingdate(String sailingdate) {
        this.sailingdate = sailingdate;
    }

    public String getForecastsailingdate() {
        return forecastsailingdate;
    }

    public void setForecastsailingdate(String forecastsailingdate) {
        this.forecastsailingdate = forecastsailingdate;
    }

    public int getShipname_code() {
        return shipname_code;
    }

    public void setShipname_code(int shipname_code) {
        this.shipname_code = shipname_code;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public int getTradetype_code() {
        return tradetype_code;
    }

    public void setTradetype_code(int tradetype_code) {
        this.tradetype_code = tradetype_code;
    }

    public int getProtocoltype_code() {
        return protocoltype_code;
    }

    public void setProtocoltype_code(int protocoltype_code) {
        this.protocoltype_code = protocoltype_code;
    }

    public int getPackingtype_code() {
        return packingtype_code;
    }

    public void setPackingtype_code(int packingtype_code) {
        this.packingtype_code = packingtype_code;
    }

    public int getContainerweight() {
        return containerweight;
    }

    public void setContainerweight(int containerweight) {
        this.containerweight = containerweight;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public BigDecimal getProductweight() {
        return productweight;
    }

    public void setProductweight(BigDecimal productweight) {
        this.productweight = productweight;
    }

    public BigDecimal getProductvolume() {
        return productvolume;
    }

    public void setProductvolume(BigDecimal productvolume) {
        this.productvolume = productvolume;
    }

    public BigDecimal getProductnubmer() {
        return productnubmer;
    }

    public void setProductnubmer(BigDecimal productnubmer) {
        this.productnubmer = productnubmer;
    }

    public int getContainerbelong_code() {
        return containerbelong_code;
    }

    public void setContainerbelong_code(int containerbelong_code) {
        this.containerbelong_code = containerbelong_code;
    }

    public int getContainertype_code() {
        return containertype_code;
    }

    public void setContainertype_code(int containertype_code) {
        this.containertype_code = containertype_code;
    }

    public String getContainernumber() {
        return containernumber;
    }

    public void setContainernumber(String containernumber) {
        this.containernumber = containernumber;
    }

    public String getSealnumber() {
        return sealnumber;
    }

    public void setSealnumber(String sealnumber) {
        this.sealnumber = sealnumber;
    }

    public int getDetention() {
        return detention;
    }

    public void setDetention(int detention) {
        this.detention = detention;
    }

    public int getDraw_container_addr() {
        return draw_container_addr;
    }

    public void setDraw_container_addr(int draw_container_addr) {
        this.draw_container_addr = draw_container_addr;
    }

    public int getReturn_container_addr() {
        return return_container_addr;
    }

    public void setReturn_container_addr(int return_container_addr) {
        this.return_container_addr = return_container_addr;
    }

    public String getDraw_container_time() {
        return draw_container_time;
    }

    public void setDraw_container_time(String draw_container_time) {
        this.draw_container_time = draw_container_time;
    }

    public String getReturn_container_time() {
        return return_container_time;
    }

    public void setReturn_container_time(String return_container_time) {
        this.return_container_time = return_container_time;
    }

    public int getDemurrage() {
        return demurrage;
    }

    public void setDemurrage(int demurrage) {
        this.demurrage = demurrage;
    }

    public String getPort_container_time() {
        return port_container_time;
    }

    public void setPort_container_time(String port_container_time) {
        this.port_container_time = port_container_time;
    }

    public String getShip_container_time() {
        return ship_container_time;
    }

    public void setShip_container_time(String ship_container_time) {
        this.ship_container_time = ship_container_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLoading_goods_addr() {
        return loading_goods_addr;
    }

    public void setLoading_goods_addr(String loading_goods_addr) {
        this.loading_goods_addr = loading_goods_addr;
    }

    public String getUnloading_goods_addr() {
        return unloading_goods_addr;
    }

    public void setUnloading_goods_addr(String unloading_goods_addr) {
        this.unloading_goods_addr = unloading_goods_addr;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getIs_deduction() {
        return is_deduction;
    }

    public void setIs_deduction(int is_deduction) {
        this.is_deduction = is_deduction;
    }

    public int getIs_urgent() {
        return is_urgent;
    }

    public void setIs_urgent(int is_urgent) {
        this.is_urgent = is_urgent;
    }

    public int getIs_delay() {
        return is_delay;
    }

    public void setIs_delay(int is_delay) {
        this.is_delay = is_delay;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact_res() {
        return contact_res;
    }

    public void setContact_res(String contact_res) {
        this.contact_res = contact_res;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getIphone_res() {
        return iphone_res;
    }

    public void setIphone_res(String iphone_res) {
        this.iphone_res = iphone_res;
    }

    public String getHandle_remark() {
        return handle_remark;
    }

    public void setHandle_remark(String handle_remark) {
        this.handle_remark = handle_remark;
    }

    public String getHandle_point() {
        return handle_point;
    }

    public void setHandle_point(String handle_point) {
        this.handle_point = handle_point;
    }

    public String getReceive_goods_state() {
        return receive_goods_state;
    }

    public void setReceive_goods_state(String receive_goods_state) {
        this.receive_goods_state = receive_goods_state;
    }

    public String getGood_pallet_code() {
        return good_pallet_code;
    }

    public void setGood_pallet_code(String good_pallet_code) {
        this.good_pallet_code = good_pallet_code;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", create_by='" + create_by + '\'' +
                ", create_at=" + create_at +
                ", updated_by='" + updated_by + '\'' +
                ", updated_at=" + updated_at +
                ", status_id='" + status_id + '\'' +
                ", businesstype_code=" + businesstype_code +
                ", customer_id=" + customer_id +
                ", shipping_id=" + shipping_id +
                ", sailingdate='" + sailingdate + '\'' +
                ", forecastsailingdate='" + forecastsailingdate + '\'' +
                ", shipname_code=" + shipname_code +
                ", voyage='" + voyage + '\'' +
                ", waybill='" + waybill + '\'' +
                ", tradetype_code=" + tradetype_code +
                ", protocoltype_code=" + protocoltype_code +
                ", packingtype_code=" + packingtype_code +
                ", containerweight=" + containerweight +
                ", productname='" + productname + '\'' +
                ", productweight=" + productweight +
                ", productvolume=" + productvolume +
                ", productnubmer=" + productnubmer +
                ", containerbelong_code=" + containerbelong_code +
                ", containertype_code=" + containertype_code +
                ", containernumber='" + containernumber + '\'' +
                ", sealnumber='" + sealnumber + '\'' +
                ", detention=" + detention +
                ", draw_container_addr=" + draw_container_addr +
                ", return_container_addr=" + return_container_addr +
                ", draw_container_time='" + draw_container_time + '\'' +
                ", return_container_time='" + return_container_time + '\'' +
                ", demurrage=" + demurrage +
                ", port_container_time='" + port_container_time + '\'' +
                ", ship_container_time='" + ship_container_time + '\'' +
                ", remark='" + remark + '\'' +
                ", loading_goods_addr=" + loading_goods_addr +
                ", unloading_goods_addr=" + unloading_goods_addr +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", is_deduction=" + is_deduction +
                ", is_urgent=" + is_urgent +
                ", is_delay=" + is_delay +
                ", contact='" + contact + '\'' +
                ", contact_res='" + contact_res + '\'' +
                ", iphone='" + iphone + '\'' +
                ", iphone_res='" + iphone_res + '\'' +
                ", handle_remark='" + handle_remark + '\'' +
                ", handle_point='" + handle_point + '\'' +
                ", receive_goods_state='" + receive_goods_state + '\'' +
                ", good_pallet_code='" + good_pallet_code + '\'' +
                '}';
    }
}
