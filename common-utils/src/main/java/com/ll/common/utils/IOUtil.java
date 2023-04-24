package com.ll.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * @version 1.0
 * @ClassName IOUtil
 * @Description
 * @Date 2018/12/21 13:55
 **/
public class IOUtil extends IOUtils {

    /**
     * 转换为{@link PushbackInputStream}<br>
     * 如果传入的输入流已经是{@link PushbackInputStream}，强转返回，否则新建一个
     *
     * @param in           {@link InputStream}
     * @param pushBackSize 推后的byte数
     * @return {@link PushbackInputStream}
     * @since 3.1.0
     */
    public static PushbackInputStream toPushbackStream(InputStream in, int pushBackSize) {
        return (in instanceof PushbackInputStream) ? (PushbackInputStream) in : new PushbackInputStream(in, pushBackSize);
    }

    /**
     * 将{@link InputStream}转换为支持mark标记的流<br>
     * 若原流支持mark标记，则返回原流，否则使用{@link BufferedInputStream} 包装之
     *
     * @param in 流
     * @return {@link InputStream}
     * @since 4.0.9
     */
    public static InputStream toMarkSupportStream(InputStream in) {
        if (null == in) {
            return null;
        }
        if (!in.markSupported()) {
            return new BufferedInputStream(in);
        }
        return in;
    }


}
