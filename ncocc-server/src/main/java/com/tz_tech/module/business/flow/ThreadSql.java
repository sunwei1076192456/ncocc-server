package com.tz_tech.module.business.flow;

public class ThreadSql {

    public static String getCreateWorkSql(){
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into ff_work_order (order_id,tache_id,tache_code,");
        sb.append(" work_order_state,executor_id,executor_name,executor_type,");
        sb.append(" create_date) values (:order_id,:tache_id,:tache_code,:work_order_state,");
        sb.append(" :executor_id,:executor_name,:executor_type,SYSDATE())");
        return sb.toString();
    };

    public static String updateFlowMsgState(){
        StringBuffer sb = new StringBuffer();
        sb.append(" update ff_flow_msg set finish_at=SYSDATE(),state=:state,receive_content=:receive_content ");
        sb.append(" where serial_id=:serial_id");
        return sb.toString();
    }
}
