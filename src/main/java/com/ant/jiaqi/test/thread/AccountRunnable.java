package com.ant.jiaqi.test.thread;

import java.math.BigDecimal;

public class AccountRunnable implements Runnable{
	
	private Account account;
	
	public AccountRunnable(Account account) {
		this.account = account;
	}
	
	@Override
	public void run(){
		account.addAmount(new BigDecimal(1));
	}
}
