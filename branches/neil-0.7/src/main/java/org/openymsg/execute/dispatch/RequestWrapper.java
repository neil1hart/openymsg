package org.openymsg.execute.dispatch;


public class RequestWrapper implements Runnable {
	private Request request;

	public RequestWrapper(Request request) {
		this.request = request;
	}

	@Override
	public void run() {
		try {
			this.request.execute();
		}
		catch (Exception e) {
			this.request.failure(e);
		}
	}

}