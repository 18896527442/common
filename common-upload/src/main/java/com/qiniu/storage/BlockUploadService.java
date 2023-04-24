package com.qiniu.storage;

import com.ll.common.upload.constant.QiniuConstant;
import com.ll.common.upload.constant.UploadConstant;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.model.ResumeBlockInfo;
import com.qiniu.util.*;
import org.springframework.stereotype.Service;


/**
 * @program: hxtrip-common-parent
 * @description:七牛云分块上传
 * @author: xgy
 * @create: 2020-02-12 09:14
 **/
@Service("QiniuBlockUploadServiceImpl")
public class BlockUploadService {
    public String blockUpload(byte[] blockBuffer,int blockSize) throws Exception{
        //构造一个带指定Zone对象的配置类
        //华东
        Configuration cfg = new Configuration(Zone.zone0());
        ConfigHelper configHelper = new ConfigHelper(cfg);
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);
        String upToken = auth.uploadToken(UploadConstant.HX_BUCKET);

        String host = null;
        try {
            host = configHelper.upHost(upToken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        long crc = Crc32.bytes(blockBuffer, 0, blockSize);
        Client client = new Client();
        Response response = null;
        boolean retry = false;
        try {
            response = makeBlock(host,client,blockBuffer,blockSize,upToken);
        } catch (QiniuException e) {
            if (e.code() < 0 || (e.response != null && e.response.needSwitchServer())) {
                host = changeHost(configHelper,upToken, host);
            }
            if (e.response == null || e.response.needRetry()) {
                retry = true;
            }
        }

        if (retry) {
            try {
                response = makeBlock(host,client,blockBuffer,blockSize,upToken);
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }

        ResumeBlockInfo blockInfo = response.jsonToObject(ResumeBlockInfo.class);
        if (blockInfo.crc32 != crc) {
            throw new QiniuException(new Exception("block's crc32 is not match"));
        }

        return blockInfo.ctx;
    }

    public String blockMerge(String fileName,String[] contexts,long size) throws Exception{
        Configuration cfg = new Configuration(Zone.zone0());
        ConfigHelper configHelper = new ConfigHelper(cfg);
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(QiniuConstant.ACCESS_KEY, QiniuConstant.Secret_Key);
        String upToken = auth.uploadToken(UploadConstant.HX_BUCKET);
        String host = null;
        Client client = new Client();
        try {
            host = configHelper.upHost(upToken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return makeFile(host,client,fileName,contexts,size,upToken);
    }

    private String makeFile(String host,Client client,String fileName,String[] contexts,long size,String upToken) throws QiniuException{
        String url = host + "/mkfile/" + size +"/key/"+UrlSafeBase64.encodeToString(fileName)+ "/mimeType/" + UrlSafeBase64.encodeToString(Client.DefaultMime)
                + "/fname/" + UrlSafeBase64.encodeToString(fileName);

        String data = StringUtils.join(contexts, ",");
        Response response = client.post(url, data, new StringMap().put("Authorization", "UpToken " + upToken));
        return response.jsonToMap().get("key").toString();
    }


    private String changeHost(ConfigHelper configHelper,String upToken, String host) {
        try {
           return configHelper.tryChangeUpHost(upToken, host);
        } catch (Exception e) {
            return "";
        }
    }

    private Response makeBlock(String host,Client client,byte[] blockBuffer, int blockSize,String upToken) throws QiniuException {
        String url = host + "/mkblk/" + blockSize;
        return client.post(url, blockBuffer, 0, blockSize, new StringMap().put("Authorization", "UpToken " + upToken),
                Client.DefaultMime);
    }
}
