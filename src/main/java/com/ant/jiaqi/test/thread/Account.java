package com.ant.jiaqi.test.thread;

import java.math.BigDecimal;

public class Account {
	private String name;
	private BigDecimal amount;
	
	public Account(String name, BigDecimal amount) {
		this.name = name;
		this.amount = amount;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void addAmount(BigDecimal amount) {
		synchronized(this.amount) {
			
			for(int i = 0; i < 10000000; i ++){}
			
			try {
				System.out.println(Thread.currentThread().getName() + "---wait");
				this.amount.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.amount = this.amount.add(amount);
			System.out.println(Thread.currentThread().getName() + "---addAmount");
		}
	}
	
	public void minusAmount(BigDecimal amount) {
		synchronized(this.amount) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.amount = this.amount.subtract(amount);
		}
	}
	
	@Override
	public String toString(){
		return this.name + "---" + this.amount;
	}
}
