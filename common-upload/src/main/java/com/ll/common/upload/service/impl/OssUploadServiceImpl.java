package com.ll.common.upload.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.ObjectMetadata;
import com.ll.common.upload.constant.OssConstant;
import com.ll.common.upload.constant.UploadConstant;
import com.ll.common.upload.exception.UploadApiException;
import com.ll.common.upload.service.UploadService;
import com.ll.common.upload.util.OssUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;

@Service("OssUploadServiceImpl")
public class OssUploadServiceImpl implements UploadService {

    private Logger log = LoggerFactory.getLogger(OssUploadServiceImpl.class);

    /**
     * 判断文件是否存在
     *
     * @param url url地址
     * @return
     */
    @Override
    public boolean doesFileExist(String url) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(OssConstant.ENDPOINT, OssConstant.ACCESS_KEY_ID, OssConstant.ACCESS_KEY_SECRET);
        String[] array;
        try {
            array = OssUtil.getBucketByUrl(url);
        } catch (MalformedURLException e) {
            throw new UploadApiException(500, "图片解析出错");
        }
        // 判断文件是否存在。
        boolean found = ossClient.doesObjectExist(array[0], array[1]);
        // 关闭OSSClient。
        ossClient.shutdown();
        return found;
    }

    /**
     * @param url
     * @Author zhangheng
     * @Description 删除文件
     * @Date 15:52 2018/12/14
     */
    @Override
    public boolean deleteFile(String url) {
        boolean result=false;
        OSSClient ossClient = new OSSClient(OssConstant.ENDPOINT, OssConstant.ACCESS_KEY_ID, OssConstant.ACCESS_KEY_SECRET);
        String[] array;
        try {
            array = OssUtil.getBucketByUrl(url);
            ossClient.deleteObject(array[0], array[1]);
            result=true;
        } catch (MalformedURLException e) {
            throw new UploadApiException(500, "图片解析出错");
        } catch (OSSException ex){
            log.error(ex.toString());
            result=false;
        }

        ossClient.shutdown();

        return result;
    }

    /**
     * @param content         图片Byte数组
     * @param prefixPath 文件前缀路径 例如 supplier/10111
     * @param suffix     文件后缀格式
     * @return
     * @Author zhangheng
     * @Description
     * @Date 10:07 2018/12/14
     */
    @Override
    public String upload(byte[] content, String prefixPath, String suffix) {
        InputStream inputStream = new ByteArrayInputStream(content);
        return upload(inputStream, prefixPath, suffix, false);
    }

    @Override
    public String upload(InputStream inputStream, String prefixPath, String suffix) {
        return upload(inputStream, prefixPath, suffix, false);
    }

    /**
     * @param inputStream     图片Byte数组
     * @param prefixPath 文件前缀路径 例如 supplier/10111
     * @param suffix     文件后缀格式
     * @param lowFrequency    是否是低频数据
     * @return
     * @Author zhangheng
     * @Description 图片上传服务
     * @Date 18:01 2018/12/13
     */
    @Override
    public String upload(InputStream inputStream, String prefixPath, String suffix, boolean lowFrequency) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(prefixPath)) {
            sb.append(prefixPath);
        }
        if (!prefixPath.endsWith("/")) {
            sb.append("/");
        }
        String fileName = sb.append(OssUtil.getFileId()).append(".").append(suffix).toString();
        String imageUrl;
        // 创建OSSClient实例。
        OSSClient ossClient = null;
        try {
            // 对上传数据进行加密处理
            ObjectMetadata meta = new ObjectMetadata();
            meta.setServerSideEncryption(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            if (lowFrequency) {
                ossClient = new OSSClient(OssConstant.HXTRIP_BUCKET_URL, OssConstant.ACCESS_KEY_ID, OssConstant.ACCESS_KEY_SECRET);
                ossClient.putObject(UploadConstant.HX_BUCKET_LOW, fileName, inputStream, meta);
                imageUrl = OSSConstants.PROTOCOL_HTTP + OssConstant.HXTRIP_LOW_URL + fileName;
            } else {
                //2019/2/19 13:39
                ossClient = new OSSClient(OssConstant.HXTRIP_BUCKET_URL, OssConstant.ACCESS_KEY_ID, OssConstant.ACCESS_KEY_SECRET);

                ossClient.putObject(UploadConstant.HX_BUCKET, fileName, inputStream, meta);
                imageUrl = OSSConstants.PROTOCOL_HTTP + OssConstant.HXTRIP_URL + fileName;
            }
        } catch (ClientException oe) {
            log.error("文件上传失败,失败原因:{}", oe.getMessage());
            throw new UploadApiException(500, "上传失败");
        } catch (OSSException ce) {
            log.error("文件上传失败,失败编码:{},失败原因{}", ce.getErrorCode(), ce.getErrorMessage());
            throw new UploadApiException(500, "上传失败");
        } catch (Exception e) {
            log.error("文件上传失败,失败原因:{}", e.getMessage());
            throw new UploadApiException(500, "上传失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return imageUrl;
    }

    @Override
    public String upload(String url, String prefixPath, String suffix){

        return null;
    }

    @Override
    public String getAuthorizedUrl(String url,String style){
        return null;
    }

}
