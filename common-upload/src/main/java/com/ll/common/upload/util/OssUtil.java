package com.ll.common.upload.util;

import com.ll.common.upload.constant.OssConstant;
import com.ll.common.upload.constant.UploadConstant;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class OssUtil {

    /**
     * 获取文件ID
     *
     * @return
     */
    public static String getFileId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取对用的bucket地址
     *
     * @param ossUrl
     * @return
     * @throws MalformedURLException
     */
    public static String[] getBucketByUrl(String ossUrl) throws MalformedURLException {
        URL url = new URL(ossUrl);
        String host = url.getHost();
        String[] array = new String[2];
        array[1] = url.getPath().substring(1);
        if (OssConstant.HXTRIP_URL.contains(host)) {
            array[0] = UploadConstant.HX_BUCKET;
        } else if (OssConstant.HXTRIP_LOW_URL.contains(host)) {
            array[0] = UploadConstant.HX_BUCKET_LOW;
        } else if (OssConstant.HXTRIP_PRIVATE_URL.contains(host)) {
            array[0] = UploadConstant.HX_BUCKET_PRIVATE;
        } else if (OssConstant.HXTRIP_PRIVATE_LOW_URL.contains(host)) {
            array[0] = UploadConstant.HX_BUCKET_PRIVATE_LOW;
        }
        return array;
    }

}
