package com.ll.common.upload.service;

import java.io.InputStream;
import java.net.MalformedURLException;

public interface UploadService {

    /**
     * 判断文件是否存在
     *
     * @param url url地址
     * @return
     */
    boolean doesFileExist(String url);

    /**
     * @param url
     * @Author zhangheng
     * @Description 删除文件
     * @Date 15:52 2018/12/14
     */
    boolean deleteFile(String url);

    /**
     * @param content      图片Byte数组
     * @param prefixPath   文件前缀路径 例如 supplier/10111
     * @param suffix       文件后缀格式
     * @return
     * @Author zhangheng
     * @Description 图片上传服务
     * @Date 18:01 2018/12/13
     */
    String upload(byte[] content, String prefixPath, String suffix);

    /**
     * @param inputStream     图片Byte数组
     * @param prefixPath 文件前缀路径 例如 supplier/10111
     * @param suffix     文件后缀格式
     * @return
     * @Author zhangheng
     * @Description 图片上传服务
     * @Date 18:01 2018/12/13
     */
    String upload(InputStream inputStream, String prefixPath, String suffix);

    /**
     * 上传远程图片
     * @param url   远程图片地址
     * @return  七牛云地址
     */
    String upload(String url, String prefixPath, String suffix);

    String upload(InputStream inputStream, String prefixPath, String suffix, boolean lowFrequency);

    String getAuthorizedUrl(String url,String style);

}
