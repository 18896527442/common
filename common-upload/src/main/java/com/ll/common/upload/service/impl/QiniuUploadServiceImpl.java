package com.ll.common.upload.service.impl;


import com.ll.common.upload.constant.UploadConstant;
import com.ll.common.upload.exception.UploadApiException;
import com.ll.common.upload.service.UploadService;
import com.ll.common.upload.util.QiniuUtil;
import com.ll.common.upload.constant.QiniuConstant;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.smart.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Service("QiniuUploadServiceImpl")
public class QiniuUploadServiceImpl implements UploadService {

    private Logger log = LoggerFactory.getLogger(QiniuUploadServiceImpl.class);

    /**
     * 判断文件是否存在
     *
     * @param url url地址
     * @return
     */
    @Override
    public boolean doesFileExist(String url) {
        boolean result=false;
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        String[] array;

        try {
            array= QiniuUtil.getBucketByUrl(url);
            FileInfo fileInfo = bucketManager.stat(array[0], array[1]);
            result=true;
        } catch (MalformedURLException e){
            throw new UploadApiException(500, "图片解析出错");
        } catch (QiniuException ex) {
            log.error(ex.response.toString());
            result=false;
        }

        return result;
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
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        String[] array;

        try {
            array= QiniuUtil.getBucketByUrl(url);
            bucketManager.delete(array[0], array[1]);
            result=true;
        } catch (MalformedURLException e){
            throw new UploadApiException(500, "图片解析出错");
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error(ex.response.toString());
            result=false;
        }

        return result;
    }

    /**
     * @param content      图片Byte数组
     * @param prefixPath   文件前缀路径 例如 supplier/10111
     * @param suffix       文件后缀格式
     * @return
     * @Author zhangheng
     * @Description 图片上传服务
     * @Date 18:01 2018/12/13
     */
    @Override
    public String upload(byte[] content, String prefixPath, String suffix) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(prefixPath)) {
            sb.append(prefixPath);
        }
        if (!prefixPath.endsWith("/")) {
            sb.append("/");
        }
        String fileName = sb.append(QiniuUtil.getFileId()).append(".").append(suffix).toString();
        String imageUrl;

        //构造一个带指定Zone对象的配置类
        //华东
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);

        String upToken = auth.uploadToken(UploadConstant.HX_BUCKET);
        try {
            Response response = uploadManager.put(content, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = response.jsonToObject(DefaultPutRet.class); //xgy 修改
            //DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            imageUrl= QiniuConstant.PROTOCOL_HTTP + QiniuConstant.HXTRIP_URL + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("七牛云文件上传失败,异常原因:{}",r.toString());
            try {
                log.error("七牛云文件上传失败,失败原因body:{}",r.bodyString());
                throw new UploadApiException(500, "上传失败");
            } catch (QiniuException ex2) {
                log.error("七牛云文件上传失败,失败原因:{}", ex2.getMessage());
                throw new UploadApiException(500, "上传失败");
            }
        }

        return imageUrl;
    }

    /**
     * @param inputStream     图片Byte数组
     * @param prefixPath 文件前缀路径 例如 supplier/10111
     * @param suffix     文件后缀格式
     * @return
     * @Author zhangheng
     * @Description 图片上传服务
     * @Date 18:01 2018/12/13
     */
    @Override
    public String upload(InputStream inputStream, String prefixPath, String suffix) {
        return upload(inputStream,prefixPath,suffix,false);
    }

    @Override
    public String upload(InputStream inputStream, String prefixPath, String suffix, boolean lowFrequency){
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(prefixPath)) {
            sb.append(prefixPath);
        }
        if (!prefixPath.endsWith("/")) {
            sb.append("/");
        }
        String fileName = sb.append(QiniuUtil.getFileId()).append(".").append(suffix).toString();
        String imageUrl;

        //构造一个带指定Zone对象的配置类
        //华东
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);

        String bucket=UploadConstant.HX_BUCKET;
        if(lowFrequency){
            bucket=UploadConstant.HX_BUCKET_PRIVATE;
        }

        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, fileName, upToken,null,null);
            //解析上传成功的结果
            DefaultPutRet putRet = response.jsonToObject(DefaultPutRet.class);
            imageUrl= QiniuConstant.PROTOCOL_HTTP + QiniuConstant.HXTRIP_URL + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("七牛云文件上传失败,异常原因:{}",r.toString());
            try {
                log.error("七牛云文件上传失败,失败原因body:{}",r.bodyString());
                throw new UploadApiException(500, "上传失败");
            } catch (QiniuException ex2) {
                log.error("七牛云文件上传失败,失败原因:{}", ex2.getMessage());
                throw new UploadApiException(500, "上传失败");
            }
        }

        return imageUrl;
    }

    /**
     * 上传远程图片
     * @param url   远程图片地址
     * @return  七牛云地址
     */
    @Override
    public String upload(String url, String prefixPath, String suffix){
        //判断suffix是否有传，没传读取url的后缀
        String defaultSuffix="jpg";
        if(suffix==null||"".equals(suffix)){
            suffix = url.substring(url.lastIndexOf(".") + 1);
            if(suffix==null||"".equals(suffix)){
                suffix=defaultSuffix;
            }
        }

        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(prefixPath)) {
            sb.append(prefixPath);
        }
        if (!prefixPath.endsWith("/")) {
            sb.append("/");
        }
        String fileName = sb.append(QiniuUtil.getFileId()).append(".").append(suffix).toString();
        String imageUrl;

        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //抓取网络资源到空间
        try {
            FetchRet fetchRet = bucketManager.fetch(url, UploadConstant.HX_BUCKET, fileName);
            imageUrl= QiniuConstant.PROTOCOL_HTTP + QiniuConstant.HXTRIP_URL + fetchRet.key;
        } catch (QiniuException ex) {
            log.error("七牛云文件上传失败,失败原因:{}", ex.getMessage());
            throw new UploadApiException(500, "上传失败");
        }

        return imageUrl;
    }

    @Override
    public String getAuthorizedUrl(String url,String style){
        return null;
    }

    public void getFileList(){
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        try {

            BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator("hxtrip","pro/image/wx/",1000,"");
            while (fileListIterator.hasNext()) {
                //处理获取的file list结果
                List<String> files=new ArrayList<>();
                FileInfo[] items = fileListIterator.next();
                for (FileInfo item : items) {
                    files.add(item.key);
                }

                String[] strs1=files.toArray(new String[files.size()]);

                BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
                batchOperations.addDeleteOp("hxtrip", strs1);
                Response response = bucketManager.batch(batchOperations);
                System.out.println("删除");
            }



        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error(ex.response.toString());

        }
    }

    public static void main(String[] args){

        QiniuUploadServiceImpl service=new QiniuUploadServiceImpl();
        service.getFileList();
//         service.deleteFile("http://qiniu.hxtrip.com/integration/image/wx/news/wxa6731511fc26b01c/fd76ca3906db4b27a97fdcd24dfbc682.jpg");
    }



}
