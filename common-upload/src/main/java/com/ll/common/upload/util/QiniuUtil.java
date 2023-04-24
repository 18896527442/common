package com.ll.common.upload.util;

import com.ll.common.upload.constant.QiniuConstant;
import com.ll.common.upload.constant.UploadConstant;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class QiniuUtil {

    /**
     * 获取文件ID
     *
     * @return
     */
    public static String getFileId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String[] getBucketByUrl(String imageUrl) throws MalformedURLException {
        URL url = new URL(imageUrl);
        String host = url.getHost();
        String[] array = new String[2];
        array[1] = url.getPath().substring(1);
        if (QiniuConstant.HXTRIP_URL.contains(host)) {
            array[0] = UploadConstant.HX_BUCKET;
        } else if (QiniuConstant.HXTRIP_PRIVATE_URL.contains(host)) {
            array[0] = UploadConstant.HX_BUCKET_PRIVATE;
        }
        return array;
    }

}
