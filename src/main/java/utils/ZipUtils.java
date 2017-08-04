package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	/**
	 * 压缩文件夹
	 * 
	 * @param filePath
	 * @param zipPath
	 */
	public static boolean dir2Zip(String dirPath, String zipPath) {
		boolean flag = false;
		File file = new File(dirPath);// 要被压缩的文件夹
		File zipFile = new File(zipPath);
		InputStream input = null;
		ZipOutputStream zipOut = null;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; ++i) {
					input = new FileInputStream(files[i]);
					zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
					int temp = 0;
					while ((temp = input.read()) != -1) {
						zipOut.write(temp);
					}
				}
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
				zipOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	// public static void main(String[] args) {
	// String filePath= "D:\\Users\\xiangzhiwei.GUOFUOUTSIDE\\Desktop\\test";
	// String zipPath = "D:\\Users\\xiangzhiwei.GUOFUOUTSIDE\\Desktop\\test.zip";
	// dir2Zip(filePath,zipPath);
	// }
}
