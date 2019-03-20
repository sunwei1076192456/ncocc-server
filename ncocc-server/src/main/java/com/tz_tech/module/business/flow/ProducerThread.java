package com.tz_tech.module.business.flow;

import com.tz_tech.module.common.dao.CommonHelper;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产者
 */
public class ProducerThread implements Runnable  {

    // 当前线程序列号。
    private int producerThreadSeq;
    // 总线程数
    private int producerThreadNum;
    // 每次从数据库加载的数据量
    private int selectNum;
    // 线程的休眠时间
    private int sleepTime;
    //进程信息
    private String threadInfo;
    //运行标识
    private static boolean runFlag = true;
    //取单SQL
    private String loadFlowMsgsSQL;

    private BufferStorage storage = null;

    private static final Logger log = Logger.getLogger(ProducerThread.class);

    private static String UP_SQL = "update ff_flow_msg set state = :state where serial_id = :serial_id";

    public ProducerThread(int producerThreadSeq, int producerThreadNum, int selectNum, int sleepTime, String loadFlowMsgsSQL,BufferStorage storage){
        this.producerThreadSeq = producerThreadSeq;
        this.producerThreadNum = producerThreadNum;
        this.selectNum = selectNum;
        this.sleepTime = sleepTime;
        this.threadInfo = "生产线程" + " " + (producerThreadSeq+1) + "/" + producerThreadNum;
        this.loadFlowMsgsSQL = loadFlowMsgsSQL;
        this.storage = storage;
        log.info("生产线程捞取sql=" + loadFlowMsgsSQL);
    }

    public List<Map<String,Object>> loadFlowMsgs() throws Exception{
        log.info(threadInfo + "拿到了锁");
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("selectNum", Long.valueOf(this.selectNum));
        List<Map<String,Object>> result = CommonHelper.getCommonDao().queryForList(loadFlowMsgsSQL,paramMap);
        //修改状态为执行中
        try
        {
            if(null != result && result.size()>0)
            {
                for(Map<String,Object> temp : result)
                {
                    Map reqMap = new HashMap();
                    reqMap.put("serial_id", temp.get("serial_id"));
                    reqMap.put("state", "10D");
                        CommonHelper.getCommonDao().update(UP_SQL,reqMap);
                }
            }
            //手动提交事务
//			transactionManager.commit(status);
        }
        catch (Exception e)
        {
            log.error("修改状态失败");
        }
        return result;
    }

    public void run() {
        try {
            while (runFlag) {
                if(this.storage.bufferSize() <= 10000){
                    List<Map<String,Object>> flowList = new ArrayList<Map<String, Object>>();
                    synchronized (ProducerThread.class){
                        flowList = loadFlowMsgs();
                    }
                    log.info(threadInfo + "加载的消息数：" + flowList.size());
                    //读取ff_flow_msg表中状态是10I的
                    if(flowList != null && flowList.size() > 0){
                        //放入缓冲区
                        storage.produce(flowList);
                    }else{
                        log.info("流程表没有可执行的数据，" + threadInfo + " 休眠" + sleepTime + "s");
                        try {
                            Thread.sleep(1000*sleepTime);
                        } catch (InterruptedException e) {
                            log.error("线程休眠异常，但是此异常可以忽略掉。");
                        }
                    }
                }else{
                    log.info("缓冲区数据已超过最大限度，" + threadInfo + " 休眠" + sleepTime + "s");
                    try {
                        Thread.sleep(1000*sleepTime);
                    } catch (InterruptedException e) {
                        log.error("线程休眠异常，但是此异常可以忽略掉。");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
