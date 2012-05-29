package org.openymsg.execute.read;

import java.util.ArrayList;
import java.util.List;

import org.openymsg.network.YMSG9Packet;

public class CollectPacketResponse implements SinglePacketResponse {
	private final MultiplePacketResponse response;
	protected List<YMSG9Packet> packets = new ArrayList<YMSG9Packet>();

	public CollectPacketResponse(MultiplePacketResponse response) {
		this.response = response;
	}

	@Override
	public void execute(YMSG9Packet packet) {
		boolean finished = false;
		this.packets.add(packet);
		if (packet.status == response.getProceedStatus()) {
			finished = true;
		} else {
			// TODO log
			// System.err.println("status is: " + packet.status);
		}
		if (finished) {
			this.response.execute(packets);
			this.packets.clear();
		}
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof CollectPacketResponse)) return false;
		CollectPacketResponse other = (CollectPacketResponse) obj;
		if (response == null) {
			if (other.response != null) return false;
		} else if (!response.equals(other.response)) return false;
		return true;
	}
}