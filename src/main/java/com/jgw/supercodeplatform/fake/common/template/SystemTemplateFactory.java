package com.jgw.supercodeplatform.fake.common.template;

/**
 * 实例化不同系统的工厂类
 * @author JGW
 *
 */
public class SystemTemplateFactory {
	
	public static final int TYPE_FAKE = 1;			//防伪系统
	
	public static final int TYPE_TRANSPORT = 2;		//物流系统
	
	public static final int TYPE_TRACE = 3;			//溯源系统
	
	public static BasicSystemType createSystemTemplate(int type){
		switch(type){
			case TYPE_TRANSPORT:
				return new TransportSystemTemplate();
			case TYPE_TRACE:
				return new TraceSystemTemplate();
			case TYPE_FAKE:
			default:
				return new FakeSystemTemplate();
		}
	}
	
	
}
