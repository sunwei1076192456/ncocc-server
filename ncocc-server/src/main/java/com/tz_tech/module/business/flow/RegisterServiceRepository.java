package com.tz_tech.module.business.flow;

import com.tz_tech.module.common.dao.CommonHelper;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterServiceRepository {

    private static final Logger log = Logger.getLogger(RegisterServiceRepository.class);

    public void init()throws Exception{
        BufferStorage storage = new BufferStorage();
        //查询配置项
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type","Thread");
//        paramMap.put("key","Producer");
        List<Map<String,Object>> threadConfList = CommonHelper.getCommonDao().queryForList("select skey,codea,codeb,codec,coded,codee,codef from sys_public_config where type=:type",paramMap);
        for(Map<String,Object> conf : threadConfList){
            String key = MapUtils.getString(conf,"skey");//线程的业务种类
            int threadNum = MapUtils.getInteger(conf,"codea");//启动的线程数量
            int selectNum = MapUtils.getInteger(conf,"codeb");//每次处理数据量--生产者用
            int sleepTime = MapUtils.getInteger(conf,"codec");//休眠时间
            String loadsql = MapUtils.getString(conf,"coded");//生产者取数据sql
            if("Producer".equals(key)){
                for(int j = 0 ; j < threadNum ; j++){
                    try {
                        new Thread(new ProducerThread(j,threadNum,selectNum,sleepTime,loadsql,storage)).start();
                        log.info("线程"+ (j+1) +"/" + threadNum + "创建成功。");
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("线程"+ (j+1) +"/" + threadNum + "创建失败。");
                    }
                }
            }else if("Consumer".equals(key)){
                for(int j = 0 ; j < threadNum ; j++){
                    try {
                        new Thread(new ConsumerThread(j,threadNum,sleepTime,storage)).start();
                        log.info("线程"+ (j+1) +"/" + threadNum + "创建成功。");
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("线程"+ (j+1) +"/" + threadNum + "创建失败。");
                    }
                }
            }else if("dealSuccFlow".equals(key)){
                //后续增加 --- 将流程表中10F的数据移到历史表中去

            }
        }
    }
}
