package com.xy.o2o.util;

import java.io.File;

import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class PathUtil {
	private static String separator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/developer kits/Images/";
		} else {
			basePath = "/home/image";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}

	public static String getShopImagePath(Long shopId) {
		String imagePath = "upload/item/shop"+shopId+"/";
		return imagePath.replace("/", separator);
	}

}
