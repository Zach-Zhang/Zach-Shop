package com.zach.manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zach.manage.vo.PicUploadResult;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("/pic")
@Controller
public class PicUploadController {
    private static final String[] IMAGE_TYPE = {".jpg",".png",".bmp",".jpeg",".gif"};
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping(value = "/upload",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile")MultipartFile multipartFile) throws JsonProcessingException {

        //返回结果
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(1);  //非0表示失败

        //判断文件是否合法
        boolean isLegal = false;

        //校验图片类型
        for (String type : IMAGE_TYPE) {
            if (multipartFile.getOriginalFilename().lastIndexOf(type) > 0) {
                isLegal = true;
                break;
            }
        }

        if(isLegal){
            try {
                BufferedImage image = ImageIO.read(multipartFile.getInputStream());

                //获取tracker server的地址配置文件路径
                String trackerConfig = this.getClass().getClassLoader().getResource("tracker.conf").getPath();

                //设置Tracker server的地址
                ClientGlobal.init(trackerConfig);

                //创建TrackerClient
                TrackerClient trackerClient = new TrackerClient();

                //创建trackerServer
                TrackerServer trackerServer = trackerClient.getConnection();

                //创建stroageServer
                StorageServer storageServer = null;
                //创建StorageClient
                StorageClient storageClient = new StorageClient(trackerServer,storageServer);

                //上传文件后缀名
                String file_ext_name = StringUtils.substringAfter(multipartFile.getOriginalFilename(),".");

                String[] fileInfos = storageClient.upload_file(multipartFile.getBytes(),file_ext_name,null);

                String url = "";
                if(fileInfos !=null && fileInfos.length>1){
                    String groupName = fileInfos[0];
                    String filename = fileInfos[1];

                    //获取储存服务器信息
                    ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer,groupName,filename);

                    url = "http://"+serverInfos[0].getIpAddr()+"/"+groupName+"/"+filename;

                    //设置返回结果
                    picUploadResult.setError(0);
                    picUploadResult.setUrl(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    return MAPPER.writeValueAsString(picUploadResult);

    }
}
