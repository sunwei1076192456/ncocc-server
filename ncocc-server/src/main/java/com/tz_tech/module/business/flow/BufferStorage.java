package com.tz_tech.module.business.flow;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * 生产者和消费者的缓冲区
 */
public class BufferStorage<T> {

    // 仓库最大存储量
    private final int MAX_SIZE = 10000;

    private static Logger logger = Logger.getLogger(BufferStorage.class);

    private LinkedList<T> bufferList = new LinkedList<T>(); //缓冲区集合

    //缓冲区中的数据条数
    public int bufferSize(){
        synchronized (this.bufferList){
            return this.bufferList.size();
        }
    }

    //生产者，即扫表读数据
    public void produce(List<T> t){
        synchronized (this.bufferList){
            logger.info("【缓冲区】生产者的产品，进入缓冲区，数量:" + t.size());
            while(this.bufferList.size() > MAX_SIZE){ //当缓冲区中的数据超过最大值，暂停放入数据
                try{
                    bufferList.wait(); //生产阻塞
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            logger.info("开始添加数据到【缓冲区】");
            for(int i=0; i<t.size(); i++){
                bufferList.add(t.get(i));
            }
            logger.info("【缓冲区】数据添加完成 size=" + t.size() + "\n");
            bufferList.notifyAll();
        }
    }

    //消费者，消费数据
    public T consume(){
        synchronized (this.bufferList){
            while(this.bufferList.size() == 0){ //当缓冲区，没有产品时，消费者被阻塞
                try {
                    this.bufferList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T result = this.bufferList.getFirst();
            this.bufferList.remove();//移除第一个
            this.bufferList.notifyAll();
            return result;
        }

    }
}
