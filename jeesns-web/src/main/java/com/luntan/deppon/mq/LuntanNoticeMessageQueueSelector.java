package com.luntan.deppon.mq;

/**
 * @author 380853    mingruishen
 * @create 2018/1/27
 * jeesns
 */

import com.alibaba.rocketmq.common.message.MessageQueue;
import com.luntan.deppon.model.common.Notice;
import com.pamirs.mq.client.queue.MQueueSelector;

import java.util.List;

/**
 * ˳����Ϣ��������ѡ��
 *
 * @author wuxing
 */
public class LuntanNoticeMessageQueueSelector implements MQueueSelector<Notice> {
    @Override
    public MessageQueue select(List<MessageQueue> mqs, String topic,
                               String tags, String keys, int flag, Notice messageObject,
                               Object extendParam) {
        //���
        if (null != extendParam && extendParam instanceof Long) {
            //id
            Long id = Long.parseLong(String.valueOf(extendParam));
            //size
            Long size = Long.parseLong(String.valueOf(mqs.size()));
            //index
            Long index = id % size;
            //���ؽ��
            return mqs.get(index.intValue());
        } else {
            //���ؽ��
            return mqs.get(0);
        }
    }
}
