package com.taotao.manager.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.pojo.PicUploadResult;

@Controller
@RequestMapping("pic/upload")
public class PicUploadController {

	// 声明Jackson的工具类，用三个功能：1对象转json；2json转对象；3直接解析json
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Value("${TAOTAO_IMAGE_URL}")
	private String url;

	// 声明哪些类型的图片可以上传
	private String[] TYPE = { ".jpg", ".png", ".jpeg", "gif", ".bmp" };

	// filePostName : "uploadFile", //上传图片的文件名
	// uploadJson : '/rest/pic/upload', //上传的请求路径
	// dir : "image" //上传文件类型
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(MultipartFile uploadFile) throws Exception {
		// 声明PicUploadResult，
		PicUploadResult picUploadResult = new PicUploadResult();
		// 设置默认值为上传失败，error为1
		picUploadResult.setError(1);

		// 声明标识位，默认为false
		Boolean flag = false;

		// 校验文件类型，其实就是校验文件后缀
		for (String type : TYPE) {
			// 判断是否是要求的后缀结尾
			if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
				// 如果是要求的后缀结尾，修改标识位为ture
				flag = true;

				// 跳出循环
				break;
			}
		}

		// if(flag==false){
		if (!flag) {
			// 如果校验失败，直接返回
			// return picUploadResult;
			// 使用工具类，把返回对象转为json格式的字符串
			String json = MAPPER.writeValueAsString(picUploadResult);
			return json;
		}

		// 重置标识位
		flag = false;

		// 校验文件内容
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			if (image != null) {
				// 获取图片的宽和高
				picUploadResult.setHeight("" + image.getHeight());
				picUploadResult.setWidth(String.valueOf(image.getWidth()));

				// 能到这里，表示校验通过
				flag = true;
			}
		} catch (IOException e) {
			// 为什么不处理异常
			// 这里就是在做校验，如果有异常就表示校验失败，没有必要处理异常了
		}

		// 判断校验成功
		// if(flag==true){
		if (flag) {
			// 上传图片
			// 初始化图片上传，加载配置文件
			ClientGlobal.init(System.getProperty("user.dir") + "\\src\\main\\resources\\tracker.conf");

			// 创建TrackerClient
			TrackerClient trackerClient = new TrackerClient();

			// 使用TrackerClient获取TrackerServer
			TrackerServer trackerServer = trackerClient.getConnection();

			// 声明StorageServer，声明null
			StorageServer storageServer = null;

			// 创建StorageClient，作用是图片上传
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);

			// 使用StorageClient进行图片上传，返回字符串数组1.2.3.4.jpg
			String extName = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");
			String[] str = storageClient.upload_file(uploadFile.getBytes(), extName, null);

			// 根据字符串数组拼接上传成功突破的url
			String pic = this.url + str[0] + "/" + str[1];

			// 设置返回的数据
			// 设置图片的url
			picUploadResult.setUrl(pic);
			// 设置上传成功，error为0
			picUploadResult.setError(0);
		}

		// return picUploadResult;
		// 使用工具类，把返回对象转为json格式的字符串
		String json = MAPPER.writeValueAsString(picUploadResult);
		return json;
	}

}
