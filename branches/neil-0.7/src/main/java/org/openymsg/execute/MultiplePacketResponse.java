package org.openymsg.execute;

import java.util.List;

import org.openymsg.network.YMSG9Packet;

public interface MultiplePacketResponse {
	void execute(List<YMSG9Packet> packets);
	int getProceedStatus();
}
