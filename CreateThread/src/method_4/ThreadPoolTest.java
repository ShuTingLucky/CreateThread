package method_4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolTest {

	public static void main(String[] args) {
		
		System.out.println("----------多线程任务启动----------");  
		
		Date date1 = new Date(); 		  
		int taskSize = 6;  
		//创建一个线程池  
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);  
		//创建多个有返回值的任务  
		List<Future> list = new ArrayList<Future>();  
		for (int i = 0; i < taskSize; i++) {
			
		    Callable c = new CallableThreadTest(i);  
		    //执行任务并获取Future对象  
		    Future f = pool.submit(c);	    
		    list.add(f);  
		   
		}  
		   
		//关闭线程池  		   
		pool.shutdown(); 	  
		   
		//获取所有并发任务的运行结果  
		try{
			
			for (Future f : list) {				    
				System.out.println(">>>" + f.get().toString()); 		   
			} 			
		}catch(InterruptedException e){  
            e.printStackTrace();  
        }catch (ExecutionException e){  
            e.printStackTrace();  
        } 	  
		
		Date date2 = new Date();
		System.out.println("\n----------多线程任务结束----------");
		System.out.println("运行时间["+ (date2.getTime() - date1.getTime()) + "毫秒]"); 	
		System.out.println("------------------------------");
	}
	
}

class CallableThreadTest implements Callable<String>{

	private  int threadNum;
	
	public CallableThreadTest(int threadNum){
		
		this.threadNum=threadNum;
	}
	
	public String call() throws InterruptedException {
		
		System.out.println(">>>" +threadNum+ " 线程启动"); 		   
		Date dateTmp1 = new Date();  
		Thread.sleep(1000);  
		Date dateTmp2 = new Date();  
		long time = dateTmp2.getTime() - dateTmp1.getTime();  
		System.out.println(">>>" +threadNum+ " 线程结束");  
		   
		String resultStr=threadNum+"线程花费的时间["+time+"毫秒]";
		return resultStr; 	
	}
}