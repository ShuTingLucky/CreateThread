package method_1;

/**����һ���̳�Thread�ഴ���߳���**/
public class ThreadTest extends Thread{

	public void run(){
		
		System.out.println("��ǰ���е��߳���:\t"+getName()); 		
	}
	
	public static void main(String[] args) {		
		 
		/**
		ThreadTest thread1=new ThreadTest();
		thread1.start();**/
		(new ThreadTest()).start();		
	}

}
