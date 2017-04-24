package com.shuting.fileTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class JFile {

	private JFile() {	
		
		 throw new Error("不要实例化我!!!");
	}   
	
	  
	/**以数组的方式返回一个文件中存储的数据(自定义格式)**/
	public static ArrayList<String> readFileToList(File filePath, String format) {

		ArrayList<String> lines = new ArrayList<String>();
		InputStreamReader read = null;

		try {
			// 以format格式读取文件内容
			read = new InputStreamReader(new FileInputStream(filePath), format);
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(read);		
		try {
			
			//按行读取文件
			String line;		
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

			reader.close();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}	
	
	/**以数组的方式返回一个文件中存储的数据(使用默认的"utf-8")**/
	public static ArrayList<String> readFileToList(File filePath) {
		
		return readFileToList(filePath, "utf-8");		
	}
	
	
	/**以字符串的方式返回一个文件中存储的数据(自定义格式)**/
	public static String readFileToString(File filePath,String format) {
        
		InputStreamReader read = null;
        try {        	
            read = new InputStreamReader(new FileInputStream(filePath), format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        BufferedReader reader = new BufferedReader(read);       
        String lineStr= new String();
        try {
        	
        	 String line;           
        	 while ((line = reader.readLine()) != null) {
        		 lineStr += line+"\n";            
        	 }
            
        	 reader.close();           
        	 read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineStr;
    }		
	
	/**以字符串的方式返回一个文件中存储的数据(使用默认的"utf-8")**/
	public static String readFileToString(File filePath) {
		
		return readFileToString(filePath, "utf-8");
	}
	
	
	/**读取一个文件目录中所有文件的内容(自定义格式)**/
	public static ArrayList<String> getAllLinesByFilePath (String filePath, String format) {
        
		ArrayList<String> lists = new ArrayList<String>();
        GetAllFileOnCatalogue readCatalogue = new GetAllFileOnCatalogue();
        readCatalogue.readFileCatalogue(new File(filePath));
        List<File> allFile=readCatalogue.getFileList();

        for (File file : allFile) {
        	
            String str = readFileToString(file, format);

            lists.add(str);
        }

        return lists;
    }

	/**读取一个文件目录中所有文件的内容(使用默认的"utf-8")**/
	public static ArrayList<String> getAllLinesByFilePath (String filePath) {		
		
		return  getAllLinesByFilePath (filePath,"utf-8");		
	}
	
    /**向文件中写入字符串形式的数据并保存文件(自定义格式)**/
    public static void writeToFiles(String filePath, String dataStr,String format) {   
                                 
        BufferedWriter writer = null;
        FileOutputStream writerStream = null;
        try {
            writerStream = new FileOutputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
        	
            writer = new BufferedWriter(new OutputStreamWriter(writerStream, format));
            writer.write(dataStr);
            
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }        
    
    /**向文件中写入字符串形式的数据并保存文件(使用默认的"utf-8")**/
    public static void writeToFiles(String filePath, String dataStr) {
    	
    	writeToFiles(filePath, dataStr,"UTF-8");        
    }
    
    
    /**向文件中写入数组形式的数据并保存文件**/
    public static void writeToFiles(String filePath,ArrayList<String> dataSet) {
    	
        appendToFile(filePath, dataSet);
    }

    /**给文件尾部追加一系列数据**/
    public static void appendToFile(String fileName, List<String> dataSet) {
    	
        for(String dataStr : dataSet) {
        	
            appendToFile(fileName, dataStr);
        }
    }

    /**给文件尾部追加一条数据**/
    public static void appendToFile(String fileName, String dataStr){
        
    	try {
    		
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(dataStr + "\n");
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * @param sourceFile 输入文件目录
     * @param destFile 输出文件目录
     * @param destDiff 输出文件后缀**/
    public static void containFile(String sourceCatalogue, String destCatalogue,
                                   String destFileDiff, String newPath) {
    	
        GetAllFileOnCatalogue readCatalogue = new GetAllFileOnCatalogue();
       
        readCatalogue.readFileCatalogue(new File(sourceCatalogue));
        List<File> sourceFiles = readCatalogue.getFileList();
        readCatalogue.readFileCatalogue(new File(destCatalogue));
        List<File> destFiles = readCatalogue.getFileList();

        //遍历输入文件目录, 如果输出目录存在, 则将文件移动到新的目录
        for(File file : sourceFiles) {
        	
            boolean flag = false;
            //查询输出文件目录中是否存在
            for(File f : destFiles) {
            	
                String name;
                if(destFileDiff.equals("null")) {
                    name = f.getName();
                }
                //去除文件后缀
                else{
                    name = f.getName().substring(0, f.getName().lastIndexOf(destFileDiff) - 1);
                }
            
                if(name.equals(file.getName())){
                	
                	flag = true; 
                	break;
                }                    
            }
            if(flag){
            	
                File fnewpath = new File(newPath);
                //判断文件夹是否存在
                if(!fnewpath.exists()){
                    fnewpath.mkdirs();
                }
                //移动文件
                File news = new File(newPath + "/" + file.getName());
                file.renameTo(news);
            }
        }
    }

    
    /**
     * 使用默认文件后缀
     * @param sourceFile 输入文件目录
     * @param destFile 输出文件目录  **/   
    public static void containFile(String sourceFile, String destFile, String newPath) {
        containFile(sourceFile, destFile, "null", newPath);
    }
	
    /**
	public static void main(String[] args) {
		
		String filePath="E:\\JavaTestData\\JFileTest\\new\\多边形中的点可见性快速算法.txt";
	
		String fileStr=JFile.readFileToString(new File(filePath),"utf-8");		
		System.out.println(fileStr);		
		
		List<String> allLine=JFile.readFileToList(new File(filePath),"utf-8");		
		for(String line : allLine){
			
			System.out.println(line);
		}		
		
	}**/
}
