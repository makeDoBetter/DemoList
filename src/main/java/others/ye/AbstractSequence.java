package others.ye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maketubo
 * @version 1.0
 * @ClassName AbstractSequence
 * @description
 * @date 2020/6/1 14:55
 * @since JDK 1.8
 */
public abstract class AbstractSequence {

    private Logger logger = LoggerFactory.getLogger(AbstractSequence.class);

    /*
     * loadSequences : 需要从外部注入seq重新初始化时调用
     *
     */
    abstract <T> void loadSequences(SequenceInitJob<T> job, String prefix);

    abstract Long getNext(String seqName);

    abstract Long getNext(String seqName, int num);

    protected <T> Map<String, Long> getSequenceMap(List<T> sequences, String prefix) {
        Assert.isTrue(sequences != null || !sequences.isEmpty(), "sequence must not empty");
        Map<String, Long> sequenceMap = new HashMap<>();
        Class<?> clazz = sequences.get(0).getClass();
        Field seqNameField = null;
        Field seqNextValField = null;
        // 会读取包括父类
        Class<?> targetClass = clazz;
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.getAnnotation(SeqNameField.class) != null) {
                        seqNameField = field;
                    }
                    if (field.getAnnotation(SeqNextValField.class) != null) {
                        seqNextValField = field;
                    }                    }
                catch (Exception ex) {
                    //do nothing
                    logger.warn("解析SequenceEntity遇到错误");
                    ex.printStackTrace();
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        try {
            for (T obj : sequences){
                seqNameField.setAccessible(true);
                seqNextValField.setAccessible(true);
                String seqName = prefix.concat((String) seqNameField.get(obj));
                Assert.isTrue(seqName.getBytes(Charset.forName("UTF-8")).length <= 32,
                        "序列名请不要超过32位");
                sequenceMap.putIfAbsent(seqName, (Long) seqNextValField.get(obj));
            }
        } catch (Exception e){
            throw new RuntimeException("反射获取属性时发生异常", e);
        }
        return sequenceMap;
    }
}
