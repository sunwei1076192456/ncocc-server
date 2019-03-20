package com.tz_tech.module.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Bill implements Serializable {

    private String id;

    private String create_by;

    private String updated_by;

    private String remark;

    private String status_id;

    private int businesstype_code;

    private int customer_id;

    private int shipping_id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date sailingdate;

    private int shipname_code;

    private String voyage;

    private int containerbelong_code;

    private int containertype_code;

    private int protocoltype_code;

    private String waybill;

    private String containernumber;

    private int deliveryplace_code;

    private String sealnumber;

    private int packingtype_code;

    private String productname;

    private BigDecimal productweight;

    private BigDecimal productvolume;

    private BigDecimal productnubmer;

    private int is_agent;

    private int demurrage;

    private int detention;

    private int detentiontype_code;

    private int is_deduction;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date loadingdate;

    private int district_id;

    private int loadingdetile_id;

    private int contact_id;

    private String processInstanceId;

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

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getProtocoltype_code() {
        return protocoltype_code;
    }

    public void setProtocoltype_code(int protocoltype_code) {
        this.protocoltype_code = protocoltype_code;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public String getContainernumber() {
        return containernumber;
    }

    public void setContainernumber(String containernumber) {
        this.containernumber = containernumber;
    }

    public int getDeliveryplace_code() {
        return deliveryplace_code;
    }

    public void setDeliveryplace_code(int deliveryplace_code) {
        this.deliveryplace_code = deliveryplace_code;
    }

    public String getSealnumber() {
        return sealnumber;
    }

    public void setSealnumber(String sealnumber) {
        this.sealnumber = sealnumber;
    }

    public int getPackingtype_code() {
        return packingtype_code;
    }

    public void setPackingtype_code(int packingtype_code) {
        this.packingtype_code = packingtype_code;
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

    public int getIs_agent() {
        return is_agent;
    }

    public void setIs_agent(int is_agent) {
        this.is_agent = is_agent;
    }

    public int getDemurrage() {
        return demurrage;
    }

    public void setDemurrage(int demurrage) {
        this.demurrage = demurrage;
    }

    public int getDetention() {
        return detention;
    }

    public void setDetention(int detention) {
        this.detention = detention;
    }

    public int getDetentiontype_code() {
        return detentiontype_code;
    }

    public void setDetentiontype_code(int detentiontype_code) {
        this.detentiontype_code = detentiontype_code;
    }

    public int getIs_deduction() {
        return is_deduction;
    }

    public void setIs_deduction(int is_deduction) {
        this.is_deduction = is_deduction;
    }


    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getLoadingdetile_id() {
        return loadingdetile_id;
    }

    public void setLoadingdetile_id(int loadingdetile_id) {
        this.loadingdetile_id = loadingdetile_id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public Date getSailingdate() {
        return sailingdate;
    }

    public void setSailingdate(Date sailingdate) {
        this.sailingdate = sailingdate;
    }

    public Date getLoadingdate() {
        return loadingdate;
    }

    public void setLoadingdate(Date loadingdate) {
        this.loadingdate = loadingdate;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", create_by='" + create_by + '\'' +
                ", updated_by='" + updated_by + '\'' +
                ", remark='" + remark + '\'' +
                ", status_id='" + status_id + '\'' +
                ", businesstype_code=" + businesstype_code +
                ", customer_id=" + customer_id +
                ", shipping_id=" + shipping_id +
                ", sailingdate=" + sailingdate +
                ", shipname_code=" + shipname_code +
                ", voyage='" + voyage + '\'' +
                ", containerbelong_code=" + containerbelong_code +
                ", containertype_code=" + containertype_code +
                ", protocoltype_code=" + protocoltype_code +
                ", waybill='" + waybill + '\'' +
                ", containernumber='" + containernumber + '\'' +
                ", deliveryplace_code=" + deliveryplace_code +
                ", sealnumber='" + sealnumber + '\'' +
                ", packingtype_code=" + packingtype_code +
                ", productname='" + productname + '\'' +
                ", productweight=" + productweight +
                ", productvolume=" + productvolume +
                ", productnubmer=" + productnubmer +
                ", is_agent=" + is_agent +
                ", demurrage=" + demurrage +
                ", detention=" + detention +
                ", detentiontype_code=" + detentiontype_code +
                ", is_deduction=" + is_deduction +
                ", loadingdate=" + loadingdate +
                ", district_id=" + district_id +
                ", loadingdetile_id=" + loadingdetile_id +
                ", contact_id=" + contact_id +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
