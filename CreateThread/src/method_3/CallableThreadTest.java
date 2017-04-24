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
		//FutureTask<String>��һ����װ������ͨ������Callable<String>����������ͬʱʵ����Future��Runnable�ӿ�.
		FutureTask<String> ft=new FutureTask<>(ctt);
		
		new Thread(ft).start();
		
		try{
			//ע��:get()�����������ģ���:�߳��޷��ؽ����get()������һֱ�ȴ���
			System.out.println("���̵߳ķ���ֵ��"+ft.get());
			
		}catch(InterruptedException e){  
            e.printStackTrace();  
        }catch (ExecutionException e){  
            e.printStackTrace();  
        }  
	}

}
