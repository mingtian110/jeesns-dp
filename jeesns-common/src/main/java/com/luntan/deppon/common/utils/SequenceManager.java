package com.luntan.deppon.common.utils;
import com.pamirs.pddl.client.sequence.Sequence;
import com.pamirs.pddl.client.sequence.exception.SequenceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 273238
 * @ClassName: CalcDetailSequenceManager
 * @Description: 清算明细pddl序列manager
 * @date 2017/7/19 9:24
 */
@Service("sequenceManager")
public class SequenceManager {

    @Resource
    private Sequence noticeStatSeq;

    /**
     * 生成sequence
     *
     * @return
     * @throws SequenceException
     */
    public long generateSeq() throws SequenceException {
        return noticeStatSeq.nextValue();
    }

    /**
     * 生成ID
     *
     * @param payDeptCode
     * @return
     */
    public long generateId(String payDeptCode) throws SequenceException {
        return IdGenerator.generateId(generateSeq(), payDeptCode);
    }

    /**
     * 生成编码
     *
     * @param date
     * @param id
     * @return
     */
    public String generateNo(Date date, Long id) {
        // Q+年月日+ID
        return "Q" + DateUtils.convert(date, "yyyyMMdd") + id;
    }

    public static String  returnToId(Integer memberId) {
        String toId="";
        if((memberId+"").length()==1){
            toId="000"+memberId;
        }else if((memberId+"").length()==2){
            toId="00"+memberId;
        }else if((memberId+"").length()==3){
            toId="0"+memberId;
        }
        return toId;
    }

}
