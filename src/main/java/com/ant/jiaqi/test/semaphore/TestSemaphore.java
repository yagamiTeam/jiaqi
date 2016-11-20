package com.ant.jiaqi.test.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore {
	public static void main(String args[]) {
		final Semaphore semaphore = new Semaphore(5, true);//初始permits=5，采用FIFO的公平策略
		
		//----------写法1----------
		/*for(int i = 0; i < 10; i++) {
			
			Runnable runnable = new Runnable(){
				@Override
				public void run() {
					try {
						semaphore.acquire();//获取一个permit，如果没有可用，阻塞线程，一直等待
						System.out.println("thread---" + Thread.currentThread().getName());
						Thread.sleep((long)(Math.random() * 10000));
						semaphore.release();//释放一个permit
						System.out.println("availablePermits---" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread thread = new Thread(runnable);
			thread.start();
		}*/
		
		//----------写法2----------
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 10; i++) {
			
			Runnable runnable = new Runnable(){
				@Override
				public void run() {
					try {
						semaphore.acquire();//获取一个permit，如果没有可用，阻塞线程，一直等待
						System.out.println("thread---" + Thread.currentThread().getName());
						Thread.sleep((long)(Math.random() * 10000));
						semaphore.release();//释放一个permit
						System.out.println("availablePermits---" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			
			exec.execute(runnable);//将runnable对象传入线程池，进行执行
		}
		exec.shutdown();//不是终止线程的运行，而是不接收新的runnable任务
		
	}
}
