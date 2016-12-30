package com.ofweek.live.core.modules.sys.utils;

import java.util.HashMap;
import java.util.Map;

import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.modules.sys.dao.SequenceDao;
import com.ofweek.live.core.modules.sys.entity.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * sequence工具类
 *
 * @author tangqian
 */
public class SequenceUtils {

    private static Logger logger = LoggerFactory.getLogger(SequenceUtils.class);

    private static SequenceDao sequenceDao = SpringContextHolder.getBean(SequenceDao.class);

    private static PlatformTransactionManager ptm = SpringContextHolder.getBean("transactionManager");

    private static final Map<String, KeyInfo> keyMap = new HashMap<>(32);

    private static final int POOL_SIZE = 2;

    /**
     * 获取下一个sequence值
     *
     * @param keyName
     * @return
     */
    public static String getNextString(String keyName) {
        return String.valueOf(getNextInt(keyName));
    }

    /**
     * 获取下一个sequence值
     *
     * @param keyName Sequence名称
     * @return 下一个Sequence键值
     */
    private static int getNextInt(String keyName) {
        KeyInfo keyInfo = null;
        if (keyMap.containsKey(keyName)) {
            keyInfo = keyMap.get(keyName);
        } else {
            keyInfo = new KeyInfo(keyName, POOL_SIZE);
            keyMap.put(keyName, keyInfo);
        }
        return keyInfo.getNextValue();
    }

    private static class KeyInfo {

        private int maxValue;

        private int nextValue;

        /**
         * Sequence缓存多少个值
         */
        private int poolSize;

        /**
         * Sequence的名称
         */
        private String keyName;

        public KeyInfo(String keyName, int poolSize) {
            this.poolSize = poolSize;
            this.keyName = keyName;
            retrieveFromDB();
        }

        /**
         * 获取下一个Sequence值
         *
         * @return 下一个Sequence值
         */
        public synchronized int getNextValue() {
            if (nextValue >= maxValue) {
                retrieveFromDB();
            }
            return nextValue++;
        }

        /**
         * 执行Sequence表初始化和更新工作
         */
        private void retrieveFromDB() {
            int tmpNextValue = 0, tmpMaxVaule = 0;
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            TransactionStatus status = ptm.getTransaction(def);
            try {
                Sequence seq = sequenceDao.get(keyName);
                if (seq != null) {
                    tmpNextValue = seq.getNextId();
                    tmpMaxVaule = tmpNextValue + poolSize;

                    seq.setStep(poolSize);
                    seq.preUpdate();
                    sequenceDao.update(seq);
                } else {
                    tmpNextValue = 1;
                    tmpMaxVaule = tmpNextValue + poolSize;

                    seq = new Sequence();
                    seq.setName(keyName);
                    seq.setNextId(tmpMaxVaule);
                    seq.preInsert();
                    sequenceDao.insert(seq);
                }
                ptm.commit(status);
            } catch (Exception e) {
                ptm.rollback(status);
                logger.error("自增长序列获取nextValue出错", e);
                throw e;
            }
            nextValue = tmpNextValue;
            maxValue = tmpMaxVaule;
        }
    }

}
