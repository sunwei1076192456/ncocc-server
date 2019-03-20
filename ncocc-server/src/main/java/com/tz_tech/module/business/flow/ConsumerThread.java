package com.tz_tech.module.business.flow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tz_tech.module.common.adapter.ObjectTypeAdapter;
import com.tz_tech.module.common.dao.CommonHelper;
import com.tz_tech.module.common.utils.ConstantUtil;
import com.tz_tech.module.common.utils.ProcedureUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerThread implements Runnable {

    // 当前线程序列号。
    private int consumerThreadSeq;
    // 总线程数
    private int consumerThreadNum;
    // 线程的休眠时间
    private int sleepTime;
    //进程信息
    private String threadInfo;
    //运行标识
    private static boolean runFlag = true;

    private BufferStorage storage = null;

    private static final Logger log = Logger.getLogger(ProducerThread.class);

    private static Gson gson = new GsonBuilder().setLenient().serializeNulls()
            .registerTypeAdapter(Map.class, new ObjectTypeAdapter())
            .registerTypeAdapter(List.class, new ObjectTypeAdapter())
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    public ConsumerThread(int consumerThreadSeq, int consumerThreadNum, int sleepTime, BufferStorage storage){
        this.consumerThreadSeq = consumerThreadSeq;
        this.consumerThreadNum = consumerThreadNum;
        this.sleepTime = sleepTime;
        this.threadInfo = "消费线程" + " " + (consumerThreadSeq+1) + "/" + consumerThreadNum;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (runFlag) {
                //从缓存区取出数据
                if(this.storage.bufferSize() != 0){
                    Map<String,Object>  consumeMap = (Map<String, Object>) storage.consume();
                    try {
                        //开始业务操作
                        if(ConstantUtil.BusinessCommandCode.COMMAND_CODE_ONE.getCode().equals(
                                MapUtils.getString(consumeMap,"command_code",""))){
                            //创建工单
                            Map<String,Object> send_content =  gson.fromJson(MapUtils.getString(consumeMap,"send_content"),Map.class);
                            String orderId = MapUtils.getString(consumeMap,"order_id");//订单ID
                            String processInstanceId = MapUtils.getString(consumeMap,"processInstanceId");//流程实例ID
                            send_content.put("order_id",orderId);
                            send_content.put("work_order_state","10I");
                            //插入工单表ff_work_order
                            CommonHelper.getCommonDao().update(ThreadSql.getCreateWorkSql(),send_content);
                            //获取自增的workOrderId
                            int workOrderId = CommonHelper.getCommonDao().queryForInt("SELECT LAST_INSERT_ID()",new HashMap<String, Object>());
                            String[] params = {String.valueOf(workOrderId),MapUtils.getString(consumeMap,"serial_id"),"out_succ_flag","out_error_msg"};
                            //调用存过插入在途表,同时把流程表的状态更新为10F
                            ProcedureUtil.executeProcedure(params,2,"create_work_order_ing");
                        }
                    } catch (Exception e) {
                        log.error("生成流程失败,e=" + e.getMessage());
                        //把流程表的状态改为10E
                        Map<String,Object> param = new HashMap<String, Object>();
                        param.put("serial_id",MapUtils.getString(consumeMap,"serial_id"));
                        param.put("state","10E");
                        param.put("receive_content",(e.getMessage() != null && e.getMessage().length() > 2000)? e.getMessage().substring(0,2000) : e.getMessage());
                        CommonHelper.getCommonDao().update(ThreadSql.updateFlowMsgState(),param);
                    }
                }else{
                    log.info("缓冲区无数据，" + threadInfo + " 休眠" + sleepTime + "s");
                    try {
                        Thread.sleep(1000*sleepTime);
                    } catch (InterruptedException e) {
                        log.error("线程休眠异常，但是此异常可以忽略掉。");
                    }
                }
            }
        } catch (Exception e) {
            log.error("消费者线程" + threadInfo + " 执行异常，e=" + e.getMessage());
            try {
                Thread.sleep(1000*sleepTime);
            } catch (InterruptedException e1) {
                log.error("线程休眠异常，但是此异常可以忽略掉。");
            }
        }
    }
}
