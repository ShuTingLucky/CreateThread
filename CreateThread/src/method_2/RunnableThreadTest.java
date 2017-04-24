package method_2;

/**方法二：通过实现Runnable接口创建线程类**/
public class RunnableThreadTest implements Runnable{

	public void run(){
		
		System.out.println("当前运行的线程名:\t"+Thread.currentThread().getName()); 
	}
	
	public static void main(String[] args) {
		
		RunnableThreadTest target=new RunnableThreadTest();
		
		new Thread(target).start();
		new Thread(target).start();
		new Thread(target).start();
		
		//对于通过接口创建线程的另一种实现方式：直接在函数体中使用
		Thread thread2=new Thread(new Runnable(){
			
			public void run(){
				
				System.out.println("当前运行的线程名:\t"+Thread.currentThread().getName()); 
			}		
		});
		
		thread2.start();
	}

}
