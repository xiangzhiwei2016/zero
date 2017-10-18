/* @(#)
 *
 * Project:Clear
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   lihj         2015年12月01日        first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2015 Quantdo.com.cn All rights reserved.
 *
 *
 * Warning:
 * =============================================================================
 *
 */
package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * 
 * <br>
 * 创建日期：2015年12月01日 下午3:32:30 <br>
 * <b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * 
 * @author 李会军
 * @since 1.0
 * @version 1.0
 */
public class FtpUtil {

	private final static Logger logger = Logger.getLogger(FtpUtil.class);

	private String server;
	private String username;
	private String password;

	private FTPClient ftp;

	public static final String JS_FILE_SEPARATOR = "/";

	/**
	 * true:binary模式下载 false:ascii模式下载
	 */
	private boolean binaryTransfer = true;

	/**
	 * @param server
	 *            ftp服务器地址
	 * @param username
	 *            ftp服务器登陆用户
	 * @param password
	 *            ftp用户密码
	 */
	public FtpUtil(String server, String username, String password) {
		this.server = server;
		this.username = username;
		this.password = password;
		this.ftp = new FTPClient();
		// // 打印FTP命令
		// if(Configuration.PrintFTPCommandLog){
		// ftp.addProtocolCommandListener(new PrintCommandListener());
		// }
	}

	/**
	 * 连接FTP服务器
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {
		try {
			ftp.connect(server);

			// 连接后检测返回码来校验连接是否成功
			int reply = ftp.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply)) {
				if (ftp.login(username, password)) {
					ftp.enterLocalPassiveMode(); // 设置为passive模式
				}
			} else {
				ftp.disconnect();
				logger.error("连接FTP服务器失败：FTP服务器拒绝连接.");
				throw new Exception("连接FTP服务器失败：FTP服务器拒绝连接.");
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					logger.error("连接FTP服务器失败：关闭FTP服务器失败.");
				}
			}
			e.printStackTrace();
			logger.error("连接FTP服务器失败：无法连接服务器.", e);
			throw new Exception("连接FTP服务器失败：无法连接服务器.", e);
		}
	}

	/**
	 * 列出远程目录下所有的文件
	 * 
	 * @param remotePath
	 *            远程目录名
	 * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
	 */
	public String[] listNames(String remotePath) throws Exception {
		String[] fileNames = null;
		try {
			FTPFile[] remotefiles = ftp.listFiles(remotePath);
			fileNames = new String[remotefiles.length];
			for (int i = 0; i < remotefiles.length; i++) {
				fileNames[i] = remotefiles[i].getName();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("无法列出FTP服务器文件列表.", e);
			throw new Exception("无法列出FTP服务器文件列表.", e);
		}
		return fileNames;
	}

	/**
	 * 列出远程目录下所有的文件
	 * 
	 * @param remotePath
	 *            远程目录名
	 * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
	 */
	public String[] listNames2(String remotePath) throws Exception {
		List<String> fileNameList = new ArrayList<String>();
		String[] fileNames = null;
		try {
			FTPFile[] remotefiles = ftp.listFiles(remotePath);
			for (int i = 0; i < remotefiles.length; i++) {
				if (remotefiles[i].isFile()) {
					fileNameList.add(remotefiles[i].getName());
				}
			}
			fileNames = new String[fileNameList.size()];

			if (fileNameList.size() < 1) {
				return null;
			} else {
				for (int i = 0; i < fileNameList.size(); i++) {
					fileNames[i] = (String) fileNameList.get(i);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("无法列出FTP服务器文件列表.", e);
			throw new Exception("无法列出FTP服务器文件列表.", e);
		}

		return fileNames;
	}
	/**
	 * =======================上传文件========================
	 */

	/**
	 * 上传多个文件到远程目录中
	 * 
	 * @param localPath
	 *            本地文件所在路径(路径：path)
	 * @param remotePath
	 *            目标文件所在路径(路径：path)
	 * @param fileNames
	 *            本地文件名数组(文件名：filename)数组
	 * @return
	 */
	public boolean[] putFilesToPath(String localPath, String remotePath, String[] fileNames) {
		String[] localFullPaths = new String[fileNames.length];
		String[] remoteFullPaths = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			localFullPaths[i] = localPath + JS_FILE_SEPARATOR + fileNames[i];
			remoteFullPaths[i] = remotePath + JS_FILE_SEPARATOR + fileNames[i];
		}

		return this.putFilesToFiles(localFullPaths, remoteFullPaths);
	}

	/**
	 * 上传多个文件到远程路径中
	 * 
	 * @param localPaths
	 *            本地文件名数组(完整全路径：path+filename)
	 * @param remotePaths
	 *            目标路径数组(完整全路径：path+filename)
	 * @param delFile
	 *            成功后是否删除文件
	 * @return
	 */
	public boolean[] putFilesToFiles(String[] localFullPaths, String[] remoteFullPaths) {
		// 初始化返回结构数组
		boolean[] result = new boolean[localFullPaths.length];
		for (int j = 0; j < result.length; j++) {
			result[j] = false;
		}
		//
		for (int i = 0; i < localFullPaths.length; i++) {
			String localFullPath = localFullPaths[i];
			String remoteFullPath = remoteFullPaths[i];
			result[i] = this.putFileToFile(localFullPath, remoteFullPath);
		}
		return result;
	}

	/**
	 * 上传一个文件到远程指定路径中
	 * 
	 * @param localFullPath
	 *            本地文件名(完整全路径：path+filename)
	 * @param remotePath
	 *            目标路径(完整全路径：path+filename)
	 * @return 成功时，返回true，失败返回false
	 */
	public boolean putFileToFile(String localFullPath, String remoteFullPath) {
		return this.put(localFullPath, remoteFullPath);
	}

	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param localAbsoluteFile
	 *            本地文件名(包括完整路径)
	 * @param remoteAbsoluteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 */
	private boolean put(String localFullFile, String remoteFullFile) {
		boolean result = false;
		InputStream input = null;
		try {
			// 设置文件传输类型
			if (binaryTransfer) {
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			} else {
				ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			}

			// 处理传输
			// 将本地文件读成数据流
			input = new FileInputStream(localFullFile); 
			// 储存本地文件到远程FTP目录下
			result = ftp.storeFile(remoteFullFile, input); 

			logger.info("上传文件" + localFullFile + "到FTP成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("本地文件" + localFullFile + "上传失败：无法找到本地文件", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("本地文件" + localFullFile + "上传失败：无法上传", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("关闭上传的本地文件" + localFullFile + "失败", e);
				}
			}
		}

		return result;
	}

	/**
	 * =======================下载文件========================
	 */
	/**
	 * 下载多个文件到本地目录中
	 * 
	 * @param localPath
	 *            本地文件所在路径(路径：path)
	 * @param remotePath
	 *            目标文件所在路径(路径：path)
	 * @param fileNames
	 *            本地文件名数组(文件名：filename)数组
	 * @return
	 */
	public boolean[] getFilesToPath(String localPath, String remotePath, String[] fileNames) {

		String[] localFullPaths = new String[fileNames.length];
		String[] remoteFullPaths = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			localFullPaths[i] = localPath + JS_FILE_SEPARATOR + fileNames[i];
			remoteFullPaths[i] = remotePath + JS_FILE_SEPARATOR + fileNames[i];
		}

		return this.getFilesToFiles(localFullPaths, remoteFullPaths);
	}

	/**
	 * 下载多个文件到本地路径中
	 * 
	 * @param localPaths
	 *            本地文件名数组(完整全路径：path+filename)
	 * @param remotePaths
	 *            目标路径数组(完整全路径：path+filename)
	 * @param delFile
	 *            成功后是否删除文件
	 * @return
	 */
	public boolean[] getFilesToFiles(String[] localFullPaths, String[] remoteFullPaths) {
		// 初始化返回结构数组
		boolean[] result = new boolean[localFullPaths.length];
		for (int j = 0; j < result.length; j++) {
			result[j] = false;
		}
		//
		for (int i = 0; i < localFullPaths.length; i++) {
			String localFullPath = localFullPaths[i];
			String remoteFullPath = remoteFullPaths[i];
			result[i] = this.getFileToFile(localFullPath, remoteFullPath);
		}
		return result;
	}

	/**
	 * 下载一个文件到本地路径中
	 * 
	 * @param fileName
	 *            localFullPath 本地文件名(完整全路径：path+filename)
	 * @param remotePath
	 *            目标路径(完整全路径：path+filename)
	 * @return 成功时，返回true，失败返回false
	 */
	public boolean getFileToFile(String localFullPath, String remoteFullPath) {
		return this.get(localFullPath, remoteFullPath);
	}

	/**
	 * 下载一个远程文件到本地的指定文件
	 * 
	 * @param remoteFullFile
	 *            远程文件名(包括完整路径)
	 * @param localFullFile
	 *            本地文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 * @throws Exception
	 */
	public boolean get(String localFullFile, String remoteFullFile) {
		boolean result = false;
		OutputStream output = null;
		try {
			// 设置文件传输类型
			if (binaryTransfer) {
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			} else {
				ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			}
			// 处理传输
			// 获得本地文件的输出流
			output = new FileOutputStream(localFullFile); 
			// 获取文件
			ftp.retrieveFile(remoteFullFile, output); 
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("无法从FTP服务器下载文件." + remoteFullFile, e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error("关闭下载的本地文件输出流" + localFullFile + "失败", e);
				}
			}
		}
		return result;
	}

	/**
	 * =======================FTP模式设置========================
	 * binaryTransfer:true,binary模式下载;false,ascii模式下载;
	 */
	public void setBinaryTransfer() {
		this.binaryTransfer = true;
	}

	public void setAsciiTransfer() {
		this.binaryTransfer = false;
	}

	/**
	 * 断开FTP服务器连接
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception {
		try {
			ftp.logout();
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("关闭连接FTP服务器失败.", e);
			throw new Exception("关闭连接FTP服务器失败.", e);
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param oldName
	 *            原文件名
	 * @param fileName
	 *            新文件名
	 */
	public void rename(String oldName, String fileName) {

		try {
			ftp.rename(oldName, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param pathName
	 * @throws @since
	 *             <br>
	 *             <b>作者： 李会军</b> <br>
	 *             创建时间：2015年12月01日 
	 */
	public boolean makeDir(String pathName) throws Exception {
		boolean result = false;
		try {
			 result = makeDirs(pathName);
		} catch (IOException e) {
			logger.error("创建远程文件夹失败！.", e);
			throw new Exception("创建远程文件夹失败！", e);
		}
		return result;
	}

	public boolean makeDirs(String path) throws Exception{
		// 保证可以创建多层目录
		StringTokenizer s = new StringTokenizer(path, "/");
		s.countTokens();
		StringBuilder pathName = new StringBuilder();
		while (s.hasMoreElements()) {
			pathName = pathName.append("/").append((String) s.nextElement());
			try {
				ftp.mkd(pathName.toString());
			} catch (Exception e) {
				logger.info("ftp文件夹创建失败");
				throw e;
			}
		}
		return true;
	}
	
	/**
	 * 删除
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	public boolean remove(String pathname) throws IOException{
		return ftp.deleteFile(pathname);
	}
	
	/**
	 * 删除文件(删除文件夹及文件夹下文件)
	 * @param pathName
	 */
	public boolean deleteDir(String pathName) throws Exception {
		boolean delFlag = false;
		try {
			FTPFile[] ftpFiles = ftp.listFiles(pathName);
			if (ftpFiles!= null && ftpFiles.length > 0) {
				// 删除文件
				for (FTPFile file : ftpFiles) {
					delFlag = ftp.deleteFile(pathName+"/"+file.getName());
				}
			}
			// 删除文件夹
			String parePathName = pathName.substring(0, pathName.lastIndexOf("/"));
			ftp.changeWorkingDirectory(parePathName);
			delFlag =ftp.removeDirectory(pathName);
		} catch (IOException e) {
			logger.error("删除远程文件夹失败！.", e);
			throw new Exception("删除远程文件夹失败！", e);
		}
		return delFlag;
	}
	
	/**
	 * 删除文件(删除文件)
	 * @param pathAllName(完整路径和文件名)
	 */
	public boolean deleteFile(String pathAllName) throws Exception {
		boolean delFlag = false;
		try {
			delFlag = ftp.deleteFile(pathAllName);
		} catch (IOException e) {
			logger.error("删除远程文件夹失败！.", e);
			throw new Exception("删除远程文件夹失败！", e);
		}
		return delFlag;
	}
	
	/** 
	 * 判断Ftp目录是否存在 
	 * @throws IOException 
	 */
	 public boolean isDirExist(String dir) throws IOException{
		 return ftp.changeWorkingDirectory(dir); 
	 }

	public static void main(String[] args) throws Exception {
		FtpUtil ftp = new FtpUtil("192.168.100.101", "ftp123", "ftp123");
		ftp.connect();
		// System.out.println(
		// ftp.putFileToFile("D:/software/PowerDesigner15.1_CN_CR.rar",
		// "/ftp/PowerDesigner15.1_CN_CR.rar"));
//		ftp.get("d:/git/a.zip", "/ftp/jdk1.8.0_45.zip");
		boolean flag = ftp.remove("/temp/check/20170809/Block1000000000004");
		System.out.println(flag);
		ftp.disconnect();
	}
}