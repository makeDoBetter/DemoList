package others.ye;

import java.util.List;

/**
 * @author maketubo
 * @version 1.0
 * @ClassName SequenceInitJob
 * @description
 * @date 2020/5/31 23:13
 * @since JDK 1.8
 */
public interface SequenceInitJob<T> {

    List<T> injectSequences();

}
