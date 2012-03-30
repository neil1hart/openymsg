package org.openymsg.contact.status;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openymsg.execute.read.MultiplePacketResponse;
import org.openymsg.network.YMSG9Packet;

/**
 * LOGON packets can contain multiple friend status sections, ISAWAY and ISBACK packets contain only one. Update the
 * YahooUser details and fire event. status == 0 is a single status
 */
public class ListOfStatusesResponse implements MultiplePacketResponse {
	private static final Log log = LogFactory.getLog(ListOfStatusesResponse.class);
	private SingleStatusResponse singleStatusResponse;

	public ListOfStatusesResponse(SingleStatusResponse singleStatusResponse) {
		this.singleStatusResponse = singleStatusResponse;
	}

	@Override
	public void execute(List<YMSG9Packet> packets) {
		if (packets.isEmpty()) {
			log.info("Not status packets");
			return;
		}
		YMSG9Packet primaryPacket = packets.get(0);
		for (int i = 1; i < packets.size(); i++) {
			primaryPacket.append(packets.get(i));
		}
		this.singleStatusResponse.execute(primaryPacket);
	}

	@Override
	public int getProceedStatus() {
		return 0;
	}

}