package com.shuting.fileTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class GetAllFileOnCatalogue {

	//文件目录下所有的文件名及对应的文件大小
	private ArrayList<File> fileList;
	private ArrayList<Long> sizeList;
	//文件目录中存储数据的总量
	private long fileListAllSize;
	
	      
	/**构造函数**/
	public GetAllFileOnCatalogue(){
		
		fileList = new ArrayList<File>();
		sizeList = new ArrayList<Long>();
		fileListAllSize = 0;		
	}
	
	
	/**获取文件目录下包含的文件并返回文件数量**/
	private long getFileList(File file) {
		
		long size = 0;
		File flist[] = file.listFiles();
		for (int i = 0; i < flist.length; i++) {
			
			// 如果是文件目录
			if (flist[i].isDirectory()) {
				  
				size = size + getFileList(flist[i]);
			} 
			// 如果是文件
			else {
				//size = size + flist[i].length();
				long fileSize=getFileSizes(flist[i]);
				size = size + fileSize;
				fileList.add(flist[i]);
				sizeList.add(fileSize);
			}
		}
		
		return size;
	}	
	
	
	/**获取文件目录中包含的文件列表**/
	public void readFileCatalogue(File file) {
	
		try {
			
			fileListAllSize = getFileList(file);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}	
	

	/**递归求取目录文件个数**/
	public long countFileNum(File file) {
		
		//size存储的是文件目录中包含的文件个数（不包含目录）
		long fileNum = 0;
		File flist[] = file.listFiles();
		fileNum = flist.length;		
		for (int i = 0; i < flist.length; i++) {
			
			if (flist[i].isDirectory()) {
				fileNum = fileNum + countFileNum(flist[i]);
				fileNum--;
			}
		}
		
		return fileNum;
	}	
	
	
	/**数值单位的转换（B、K、M、G格式）**/	
	public static String FormetFileSize(long fileSize) {
		
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeStr = "";
		if (fileSize < 1024) {			
			fileSizeStr = df.format((double) fileSize) + "B";
		} 
		else if (fileSize < 1048576) {
			fileSizeStr = df.format((double) fileSize / 1024) + "K";
		} 
		else if (fileSize < 1073741824) {
			fileSizeStr = df.format((double) fileSize / 1048576) + "M";
		} 
		else {
			fileSizeStr = df.format((double) fileSize / 1073741824) + "G";
		}
		
		return fileSizeStr;
	}

	
	/**获取一个文件的大小**/
	public long getFileSizes(File file) {
		
		long fileSize = 0;
		if (file.exists()) {
			
			FileInputStream fin = null;
			try {
				
				fin = new FileInputStream(file);
				fileSize = fin.available();
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} 
		else {
			System.out.println("文件不存在");
		}
		
		return fileSize;
	}

	
	/**获取文件目录下最大文件的大小**/
	public long getMaxFileSize() {		
		
		Iterator<File> iter = fileList.iterator();
		long maxSize = 0;
		while(iter.hasNext()){
			
			File fileName = iter.next();
			long size = getFileSizes(fileName);
			if (size > maxSize) {
				maxSize = size;
			}			
		}
	
		return maxSize;
	}

	
	/**获取最大文件的文件名**/
	public String getMaxSizeFileName() {
		
		long maxSize = 0;
		String maxSizeFileName = "";
		Iterator<File> iter = fileList.iterator();
		while (iter.hasNext()) {
			
			File fileName = iter.next();
			long size = getFileSizes(fileName);
			if (size > maxSize) {
				maxSize = size;
				maxSizeFileName = fileName.getAbsolutePath();
			}
		}
		
		return maxSizeFileName;
	}
	
	
	/**获取文件目录下最小文件的大小**/
    public long getMinFileSize() {
    	
        long minSize = 0;
        Iterator<File> iter= fileList.iterator();
        while (iter.hasNext()) {
           
        	File fileName = iter.next();
            long size = getFileSizes(fileName);
            if (size < minSize) {
                minSize = size;
            }
        }
        
        return minSize;
    }
    
    
	/**获取最小文件的文件名**/
	public String getMinSizeFileName() {
		
		long minSize = 0;
		String minSizeFileName = "";
		Iterator<File> iter = fileList.iterator();
		while (iter.hasNext()) {
			
			File fileName = iter.next();
			long size = getFileSizes(fileName);			
			if (size < minSize) {
	                
				minSize = size;
				minSizeFileName = fileName.getAbsolutePath();
			}			
		}
		
		return minSizeFileName;
	}
	
	
	/**将所有大于 fileSize 的文件存储到新的文件目录**/
	public void removeFileToNewPath(long fileSize, String newPath) {
		
		Iterator<File> iter = fileList.iterator();
		while (iter.hasNext()) {
			
			File file = iter.next();
			long size = getFileSizes(file);
			if (size > fileSize) {
				
				File fnewpath = new File(newPath);
				// 判断文件夹是否存在
				if (!fnewpath.exists()) {
					fnewpath.mkdirs();
				}
				
				// 将文件移到新文件里
				File fnew = new File(newPath + "/" + file.getName());
				file.renameTo(fnew);
			}
		}
	}
	
	public ArrayList<File> getFileList() {
		return fileList;
	}

	public ArrayList<Long> getSizeList() {
		return sizeList;
	}
	
	public long getFileListAllSize() {
		return fileListAllSize;
	}

	/**
	public static void main(String[] args) {
		
		String filePath="E:\\JavaTestData\\JFileTest\\old";
		
		GetAllFileOnCatalogue test=new GetAllFileOnCatalogue();
		
		test.readFileCatalogue(new File(filePath));
		ArrayList<File> allFile=test.getFileList();
		ArrayList<Long> allSize=test.getSizeList();
		for(int i=0;i<allFile.size();i++){
			
			System.out.println(allFile.get(i).getName().replace(".txt", "")+"   Size:"+allSize.get(i));
		}
		
		System.out.println(test.getFileListAllSize());
		
		String newPath="E:\\JavaTestData\\JFileTest\\new";
		test.removeFileToNewPath(888, newPath);
	}**/
	
	
}
