package com.example.demo.util;

import com.example.demo.util.constants.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	/**
	 *
	 * @param file 文件
	 * @param path   文件存放路径
	 * @return
	 */
	public static String upload(MultipartFile file, String path){

		//1获得文件名字
		String fileName = file.getOriginalFilename();
		// 生成新的文件名
		String realPath = path + "/" + FileNameUtils.getFileName(fileName);

		//使用原文件名
		// String realPath = path + "/" + fileName;

		File dest = new File(realPath);

		//判断文件父目录是否存在
		if(!dest.getParentFile().exists()){
			dest.getParentFile().mkdir();
		}

		try {
			//保存文件
			file.transferTo(dest);
			return realPath.replace("D:", Constants.localhost);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "false";
		} catch (IOException e) {
			e.printStackTrace();
			return "false";
		}

	}

	public static String deleteFile(String imageUrl)
	{
		imageUrl = imageUrl.replace(Constants.localhost,"D:");
		File file = new File(imageUrl);
		file.delete();
		return "true";
	}

}