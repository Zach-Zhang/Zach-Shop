import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class FastDFSTest {

    @Test
        public void test() throws IOException, MyException {

        //t配置文件路径
        String trackerConfig = ClassLoader.getSystemResource("tracker.conf").getPath();

        //设置tracker server地址
        ClientGlobal.init(trackerConfig);

        //创建trackerClient
        TrackerClient trackerClient = new TrackerClient();

        //通过trackClient,创建trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();

        //创建stroageServer
        StorageServer storageServer = null;

        //创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);

        //上传文件
        String[] fileInfos = storageClient.upload_file("E:\\av.jpg", "jpg", null);


        //获取存取服务器的信息
        if(fileInfos !=null && fileInfos.length>1){
            for (String info : fileInfos)
                System.out.println(info);

            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, fileInfos[0], fileInfos[1]);
            for (ServerInfo serverInfo : serverInfos)
                System.out.println("ipAddr="+serverInfo.getIpAddr()+";port="+serverInfo.getPort());

            //拼接url
            String url = "http://"+serverInfos[0].getIpAddr()+"/"+fileInfos[0]+"/"+fileInfos[1];
            System.out.println(url);
        }

    }
}
