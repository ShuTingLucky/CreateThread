package method_3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableThreadTest implements Callable<String>{

	public String call() {
		
		String threadName=Thread.currentThread().getName();
		return threadName;
	}
	
	public static void main(String[] args) {
		
		CallableThreadTest ctt=new CallableThreadTest();
		//FutureTask<String>是一个包装器，它通过接受Callable<String>来创建，它同时实现了Future和Runnable接口.
		FutureTask<String> ft=new FutureTask<>(ctt);
		
		new Thread(ft).start();
		
		try{
			//注意:get()方法是阻塞的，即:线程无返回结果，get()方法会一直等待。
			System.out.println("子线程的返回值："+ft.get());
			
		}catch(InterruptedException e){  
            e.printStackTrace();  
        }catch (ExecutionException e){  
            e.printStackTrace();  
        }  
	}

}
