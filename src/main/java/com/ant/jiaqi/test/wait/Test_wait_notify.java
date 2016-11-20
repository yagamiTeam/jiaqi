package com.ant.jiaqi.test.wait;

import java.util.LinkedList;
import java.util.List;

public class Test_wait_notify {
	private List<String> list = new LinkedList<String>();
	
	public String removeElement() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---开始消费");
		
		synchronized(list) {
			while(list.isEmpty()) {
				System.out.println(Thread.currentThread().getName() + "---list为空，阻塞线程，释放锁");
				Thread.sleep(1000);
				list.wait();
				System.out.println(Thread.currentThread().getName() + "---接收到了notify，跳出wait");
			}
			String element = (String) list.remove(0);
			System.out.println(Thread.currentThread().getName() + "---消费了元素: " + element);
			return element;
		}
	}
	
	public void addElement() {
		System.out.println(Thread.currentThread().getName() + "---开始生产");
		
		synchronized(list) {
			System.out.println(Thread.currentThread().getName() + "---新元素: product1");
			list.add("product1");
			
			/*System.out.println(Thread.currentThread().getName() + "---新元素: product1、product2");
			list.add("product1");
			list.add("product2");*/
			
			/*System.out.println(Thread.currentThread().getName() + "---通知某个阻塞线程");
			list.notify();*/
			
			System.out.println(Thread.currentThread().getName() + "---通知所有阻塞线程");
			list.notifyAll();
		}
		
		System.out.println(Thread.currentThread().getName() + "---结束生产");
	}
	
	public static void main(String args[]) {
		final Test_wait_notify productList = new Test_wait_notify();
		
		Runnable consumerRun = new Runnable(){
			@Override
			public void run() {
				try {
					productList.removeElement();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Runnable producerRun = new Runnable(){
			@Override
			public void run() {
				productList.addElement();
			}
		};
		
		Thread consumer_thread1 = new Thread(consumerRun, "consumer1");
		Thread consumer_thread2 = new Thread(consumerRun, "consumer2");
		consumer_thread1.start();
		consumer_thread2.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("consumer_thread1当前状态：" + consumer_thread1.getState());
		System.out.println("consumer_thread2当前状态：" + consumer_thread2.getState());
		
		Thread producer_thread1 = new Thread(producerRun, "producer1");
		producer_thread1.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("consumer_thread1当前状态：" + consumer_thread1.getState());
		System.out.println("consumer_thread2当前状态：" + consumer_thread2.getState());
	}
}
