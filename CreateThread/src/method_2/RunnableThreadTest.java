package method_2;

/**��������ͨ��ʵ��Runnable�ӿڴ����߳���**/
public class RunnableThreadTest implements Runnable{

	public void run(){
		
		System.out.println("��ǰ���е��߳���:\t"+Thread.currentThread().getName()); 
	}
	
	public static void main(String[] args) {
		
		RunnableThreadTest target=new RunnableThreadTest();
		
		new Thread(target).start();
		new Thread(target).start();
		new Thread(target).start();
		
		//����ͨ���ӿڴ����̵߳���һ��ʵ�ַ�ʽ��ֱ���ں�������ʹ��
		Thread thread2=new Thread(new Runnable(){
			
			public void run(){
				
				System.out.println("��ǰ���е��߳���:\t"+Thread.currentThread().getName()); 
			}		
		});
		
		thread2.start();
	}

}
