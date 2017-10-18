package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linuxense.javadbf.DBFReader;

/**
 * 读写文件
 * @author xiangzhiwei
 *
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/**
	 * 读取excle文件
	 * @param file
	 */
	void readExcel(File file){
		List<String[]> returnList = new ArrayList<String[]>();
		// 配置一次导入数量
		int maxRowNumber = Integer.valueOf(1000);
		// 默认行读取
		int startLine = 0;
		Workbook workbook = null;
		int count = 0;
		try {
			workbook = WorkbookFactory.create(file);
			String[] array = null;
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = startLine -1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if(null == row){
						continue;
					}
					array = new String[row.getPhysicalNumberOfCells()];
					for(int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++){
						array[j] = row.getCell(j).toString();
					}
					returnList.add(array);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	void readTxt(File file){
		List<String[]> returnList = new ArrayList<String[]>();
		// 配置一次导入数量
		int maxRowNumber = 1000;
		// 考虑到编码格式
		String encoding="GBK";
		// 分隔符
		String separator = "&";
		// 从第几行开始读取
//		String startLine = StringUtils.convertToString(modelMap.get(Constants.START_LINE));
		int start = 0;
		logger.info("从第【" + start + "】行开始读取文件");
		InputStreamReader read = null;
		try {
			read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);

			String lineTxt = null;
			int count = 0;
			// 记录文件的行数
			int lineCount = 0;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineCount++;
				if (lineCount < start) {
					continue;
				}
				String[] array = lineTxt.split(separator);
				returnList.add(array);
			}
			// 导入总的的记录
			logger.info("count:"+count);
		} catch (Exception e) {
			logger.error("读取txt文件内容出错！"+e.getMessage());
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void readDbf(File file){
		List<String[]> returnList = new ArrayList<String[]>();
		FileInputStream fis = null;
		DBFReader reader = null;
		// 配置一次导入数量
		int maxRowNumber = 1000;
		// 从第几行开始读取
		int start = 0;
		logger.info("从第【" + start + "】行开始读取文件");
		int count = 0;
		try {
			fis = new FileInputStream(file);
			reader = new DBFReader(fis);
			reader.setCharactersetName("GBK");
            Object[] rowValues;
            // 记录文件的行数
         	int lineCount = 0;
            while ((rowValues = reader.nextRecord()) != null) {
            	lineCount++;
            	if(lineCount < start){
            		continue;
            	}
            	String[] resultArr = new String[rowValues.length];
            	for(int i = 0; i<rowValues.length; i++){
            		Object obj = rowValues[i];
            		if(null == obj || "".equals(obj) || "".equals(StringUtils.convertToString(obj).trim())){
            			resultArr[i] = "";
            		}else{
            			resultArr[i] =  StringUtils.convertToString(obj);
            		}
            	}
            	returnList.add(resultArr);
            }
			// 导入总的的记录
			logger.info("count:"+count);
		} catch (Exception e) {
			logger.error("读取dbf文件内容出错！"+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				logger.error("流关闭出错!"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成文件
	 * @param fileName
	 * @param list
	 * @return
	 * @throws ServiceException
	 */
	public String genFile(String fileName, List<String> list){
		logger.info(">>>>>>>>>>>>>>>>>>文件生成>>>>>>>>>>>>>>>>>>>>");
		String tradeDate = DateUtils.currentDate();
		// 盘后文件生成路径目录是否存在，不存在则创建目录
		String root = System.getProperty("user.dir") + "test";
		File dir = null;
		if (null != root) {
			dir = new File(root,tradeDate);
			if (!dir.exists()) {
				logger.info("创建文件生成路径目录【"+root+"】");
				dir.mkdirs();
			}
		}
		String path = root + "//"+ tradeDate + "//" + fileName;
		File tempFolder = new File(path);
		OutputStreamWriter streamWriter = null;
		BufferedWriter bw = null;
		try {
			if (!tempFolder.exists()) {
				tempFolder.createNewFile();
			}
			streamWriter = new OutputStreamWriter(new FileOutputStream(tempFolder), "UTF-8");
			bw = new BufferedWriter(streamWriter);
			for (Object obj : list) {
				bw.write(obj + "\n");
			}
		} catch (IOException e) {
			logger.error("写入文件出错", e);
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (streamWriter != null) {
				try {
					streamWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tempFolder.getAbsolutePath();
	}
	
	/**
	 * 删除本地文件夹
	 * @param file
	 */
	@SuppressWarnings("unused")
	private void deleteFile(File file) {
		logger.info("删除文件,"+file.getAbsolutePath()+"begin");
		// 判断文件是否存在
		if (file.exists()) {
			// 判断是否是文件
			if (file.isFile()) {
				// 删除文件
				file.delete();
			} else if (file.isDirectory()) {
				// 否则如果它是一个目录，声明目录下所有的文件 files[];
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					// 把每个文件用这个方法进行迭代
					this.deleteFile(files[i]);
				}
				// 删除文件夹
				file.delete();
			}
		}
		logger.info("删除文件,"+file.getAbsolutePath()+"end");
	} 
	
	/**
	 * 递归，读取一个文件夹下所有文件及子文件夹下的所有文件
	 * @param filePath
	 * @param filelist
	 * @return
	 */
    public List<File> readAllFile(String filePath,List<File> filelist) {  
        File f = null;  
        f = new File(filePath); 
        // 得到f文件夹下面的所有文件
        File[] files = f.listFiles(); 
        for (File file : files) {  
            if(file.isDirectory()) {  
                //如何当前路径是文件夹，则循环读取这个文件夹下的所有文件  
            	readAllFile(file.getAbsolutePath(),filelist);  
            } else {  
            	filelist.add(file);  
            }  
        }  
       return filelist; 
    }
    
    /** 
     * 文件最后修改时间
     * @param file
     * @return
     */
    public String lastUpdate(File file){
    	// 最后修改时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(file.lastModified());
		String date = sdf.format(cal.getTime());
		logger.info("文件【"+file.getName()+"】最后修改时间：【" + date + "】");
		return date;
    }
}
