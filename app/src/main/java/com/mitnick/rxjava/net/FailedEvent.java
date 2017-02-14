package com.mitnick.rxjava.net;

public class FailedEvent {
	private int type;
	private int arg0, arg1;
	private Object object;
	
	public FailedEvent(int type) {
		this.setType(type);
	}
	
	public FailedEvent(int type, int arg0) {
		this(type);
		this.setArg0(arg0);
	}
	
	public FailedEvent(int type, int arg0, int arg1) {
		this(type, arg0);
		this.setArg1(arg1);
	}

	public FailedEvent(int type, Object object) {
		this.type = type;
		this.object = object;
	}

	public FailedEvent(int type, int arg0, Object object) {
		this(type, arg0);
		this.setObject(object);
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getArg0() {
		return arg0;
	}

	public void setArg0(int arg0) {
		this.arg0 = arg0;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
