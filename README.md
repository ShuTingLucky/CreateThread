# CreateThread
*基于Java的多线程实现*  

Java的多线程实现主要有四种方式  
1. 继承Thread类（method_1） 
2. 实现Runnable接口（method_2）
3. 实现Callable接口通过FutureTask包装器来创建Thread线程（method_3）
4. 使用ExecutorService、Callable、Future实现有返回结果的多线程（method_4）  

 **前两种方式线程执行完后都没有返回值，后两种是带返回值的**  
 #### 启动线程的唯一方法就是通过Thread类的start()实例方法。start()方法是一个native方法，它将启动一个新线程，并执行run()方法。  
 
### method_1()  
* 定义Thread类的子类，并重写该类的run方法，该run方法的方法体就代表了线程要完成的任务。因此把run()方法称为执行体。
* 创建Thread子类的实例，即创建了线程对象。
* 调用线程对象的start()方法来启动该线程。

### method_2()  
* 定义runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。
* 创建 Runnable实现类的实例，并依此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。
* 调用线程对象的start()方法来启动该线程。

### method_3()
* 创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。
* 创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。
* 使用FutureTask对象作为Thread对象的target创建并启动新线程。
* 调用FutureTask对象的get()方法来获得子线程执行结束后的返回值  

### method_4()  
* ExecutorService、Callable、Future三个接口实际上都是属于Executor框架。返回结果的线程是在JDK1.5中引入的新特征，有了这种特征就不需要再为了得到返回值而大费周折了。
* 可返回值的任务必须实现Callable接口。类似的，无返回值的任务必须实现Runnable接口。
* 执行Callable任务后，可以获取一个Future的对象，在该对象上调用get就可以获取到Callable任务返回的Object了。
* 再结合线程池接口ExecutorService就可以实现传说中有返回结果的多线程了。  

**注意：get方法是阻塞的，即：线程无返回结果，get方法会一直等待**  

代码中Executors类，提供了一系列工厂方法用于创建线程池，返回的线程池都实现了ExecutorService接口  

1. `public static ExecutorService newFixedThreadPool(int nThreads)`  
*创建固定数目线程的线程池*   

2. `public static ExecutorService newCachedThreadPool()`  
*创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程*    

3. `public static ExecutorService newSingleThreadExecutor()`  
*创建一个单线程化的Executor*    

4. `public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)`  
*创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类*   

5. *ExecutoreService提供了submit()方法，传递一个Callable，或Runnable，返回Future。如果Executor后台线程池还没有完成Callable的计算，这调用返回Future对象的get()方法，会阻塞直到计算完成。*    

## 不同方式的对比：  
### 采用实现Runnable、Callable接口的方式创见多线程  
***优势：***
线程类只是实现了Runnable接口或Callable接口，还可以继承其他类。
在这种方式下，多个线程可以共享同一个target对象，所以非常适合多个相同线程来处理同一份资源的情况，从而可以将CPU、代码和数据分开，形成清晰的模型，较好地体现了面向对象的思想。  

***劣势：***
编程稍微复杂，如果要访问当前线程，则必须使用Thread.currentThread()方法。

### 使用继承Thread类的方式创建多线程  
***优势：***
编写简单，如果需要访问当前线程，则无需使用Thread.currentThread()方法，直接使用this即可获得当前线程  

***劣势：***
线程类已经继承了Thread类，所以不能再继承其他父类。
 
 
 
 









