package org.openymsg.connection;

public enum ConnectionState {
	/**
	 * Not logged in
	 */
    UNSTARTED(false, true),
    /**
     * Started to logon
     */
    CONNECTING(false, false), 
    /**
     *  Connected to yahoo
     */
    CONNECTED(true, false), 
    /**
     * Failed connecting
     */
    FAILED_CONNECTING(false, false), 
    /**
     * Failed after connected
     */
    FAILED_AFTER_CONNECTED(false, false); 

    private boolean connected;
    private boolean startable;

    ConnectionState(boolean connected, boolean startable) {
		this.connected = connected;
		this.startable = startable;
    }

	public boolean isConnected() {
		return connected;
	}

	public boolean isStartable() {
		return startable;
	}
	
}
