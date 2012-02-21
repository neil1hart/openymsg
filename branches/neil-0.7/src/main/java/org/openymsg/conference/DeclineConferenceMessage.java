package org.openymsg.conference;

import java.io.IOException;

import org.openymsg.Conference;
import org.openymsg.execute.Message;
import org.openymsg.network.MessageStatus;
import org.openymsg.network.PacketBodyBuffer;
import org.openymsg.network.ServiceType;

/**
 * Transmit an CONFDECLINE packet. We send one of these when we decline an offer to join a conference.
 */
public class DeclineConferenceMessage implements Message {
	private String username;
	private Conference conference;
	private String message;

	public DeclineConferenceMessage(String username, Conference conference, String message) {
		this.username = username;
		this.conference = conference;
		this.message = message;
	}

	@Override
	public PacketBodyBuffer getBody() throws IOException {
		PacketBodyBuffer body = new PacketBodyBuffer();
		body.addElement("1", this.username);
		for (String user : this.conference.getMemberIds()) {
			body.addElement("3", user);
		}
		body.addElement("57", this.conference.getId());
		// TODO - if not null?
		if (this.message != null) {
			body.addElement("14", this.message);
		}
		return body;
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.CONFDECLINE;
	}

	@Override
	public MessageStatus getMessageStatus() {
		return MessageStatus.DEFAULT;
	}

	@Override
	public void messageProcessed() {
		// TODO - remove conference
		// Flag this conference as now dead
		// YahooConference yc = getConference(room);
		// yc.closeConference();
	}

}