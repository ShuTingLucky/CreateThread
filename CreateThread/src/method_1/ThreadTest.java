package method_1;

/**方法一：继承Thread类创建线程类**/
public class ThreadTest extends Thread{

	public void run(){
		
		System.out.println("当前运行的线程名:\t"+getName()); 		
	}
	
	public static void main(String[] args) {		
		 
		/**
		ThreadTest thread1=new ThreadTest();
		thread1.start();**/
		(new ThreadTest()).start();		
	}

}
