package cn.itcast.fastdfs.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSTest {

	public static void main(String[] args) throws Exception {
		// 1. 初始化图片上传，加载配置文件，配置文件是tracker.conf
		// 配置文件的内容是 tracker_server=192.168.37.161:22122
		System.out.println(System.getProperty("user.dir"));
		// ClientGlobal.init("C:/0407/workspace/itcast-fastdfs/src/main/resources\\tracker.conf");
		ClientGlobal.init(System.getProperty("user.dir") + "/src/main/resources\\tracker.conf");

		// 2. 创建TrackerClient,直接new
		TrackerClient trackerClient = new TrackerClient();

		// 3. 使用TrackerClient，获取TrackerServer
		TrackerServer trackerServer = trackerClient.getConnection();

		// 4. 声明StorageServer，直接声明null即可
		StorageServer storageServer = null;

		// 5. 创建StorageClient，使用它进行图片上传。创建他需要连个参数，TrackerServer，StorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);

		// 6. 使用StorageClient进行图片上传，返回字符串数组
		// String[] str =
		// storageClient.upload_file("C:/Users/tree/Desktop/99_Web_0.jpg",
		// "jpg", null);
		String[] str = storageClient.upload_file("C:/Users/tree/Desktop/图片/英雄01/91_Web_0.jpg", "jpg", null);

		// 7. 遍历并打印结果
		for (String s : str) {
			System.out.println(s);
		}

	}

}
