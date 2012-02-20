package org.openymsg.keepalive;

import org.openymsg.execute.Executor;
import org.openymsg.network.ServiceType;

public class SessionKeepAliveImpl implements SessionKeepAlive {
	private Executor executor;
	
	public SessionKeepAliveImpl(Executor executor, String username) {
		this.executor = executor;
		this.executor.register(ServiceType.PING, new PingResponse());
		this.executor.schedule(new PingRequest(), (60 * 60 * 1000));
		this.executor.schedule(new KeepAliveRequest(username), (60 * 1000));
	}

}
