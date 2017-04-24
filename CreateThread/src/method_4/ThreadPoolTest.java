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
		
		System.out.println("----------���߳���������----------");  
		
		Date date1 = new Date(); 		  
		int taskSize = 6;  
		//����һ���̳߳�  
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);  
		//��������з���ֵ������  
		List<Future> list = new ArrayList<Future>();  
		for (int i = 0; i < taskSize; i++) {
			
		    Callable c = new CallableThreadTest(i);  
		    //ִ�����񲢻�ȡFuture����  
		    Future f = pool.submit(c);	    
		    list.add(f);  
		   
		}  
		   
		//�ر��̳߳�  		   
		pool.shutdown(); 	  
		   
		//��ȡ���в�����������н��  
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
		System.out.println("\n----------���߳��������----------");
		System.out.println("����ʱ��["+ (date2.getTime() - date1.getTime()) + "����]"); 	
		System.out.println("------------------------------");
	}
	
}

class CallableThreadTest implements Callable<String>{

	private  int threadNum;
	
	public CallableThreadTest(int threadNum){
		
		this.threadNum=threadNum;
	}
	
	public String call() throws InterruptedException {
		
		System.out.println(">>>" +threadNum+ " �߳�����"); 		   
		Date dateTmp1 = new Date();  
		Thread.sleep(1000);  
		Date dateTmp2 = new Date();  
		long time = dateTmp2.getTime() - dateTmp1.getTime();  
		System.out.println(">>>" +threadNum+ " �߳̽���");  
		   
		String resultStr=threadNum+"�̻߳��ѵ�ʱ��["+time+"����]";
		return resultStr; 	
	}
}