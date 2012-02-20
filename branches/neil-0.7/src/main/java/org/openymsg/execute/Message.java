package org.openymsg.execute;

import java.io.IOException;

import org.openymsg.network.MessageStatus;
import org.openymsg.network.PacketBodyBuffer;
import org.openymsg.network.ServiceType;

public interface Message {
	PacketBodyBuffer getBody() throws IOException ;

	ServiceType getServiceType();

	MessageStatus getMessageStatus();
	
	void messageProcessed();
}
