package com.jgw.supercodeplatform.marketing.lock;

import java.util.concurrent.locks.ReentrantLock;

public class GlobalLock {
	//扫码时es操作锁
 public static ReentrantLock scanEsSaveLock=new ReentrantLock();
}
