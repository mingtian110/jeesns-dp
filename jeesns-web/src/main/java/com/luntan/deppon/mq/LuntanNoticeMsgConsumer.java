package com.luntan.deppon.mq;

import com.alibaba.fastjson.JSONObject;
import com.luntan.deppon.model.common.Notice;
import com.pamirs.mq.client.IMqConsumer;
import com.pamirs.mq.client.domain.MonoMqResultDTO;
import com.pamirs.mq.client.exception.MonoMqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author mingtian110
 *
 */
@Service("luntanNoticeMsgConsumer")
public class LuntanNoticeMsgConsumer implements IMqConsumer<Notice> {


    private static Logger logger = LoggerFactory.getLogger(LuntanNoticeMsgConsumer.class);

    @Override
    public MonoMqResultDTO consume(final Notice notice) throws MonoMqException {
        MonoMqResultDTO monoMqResultDTO = new MonoMqResultDTO();

        System.out.println(JSONObject.toJSONString(notice));

        return monoMqResultDTO;
    }

}
