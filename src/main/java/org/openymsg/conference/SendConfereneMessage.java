package org.openymsg.conference;

import java.io.IOException;

import org.openymsg.YahooConference;
import org.openymsg.network.MessageStatus;
import org.openymsg.network.PacketBodyBuffer;
import org.openymsg.network.ServiceType;

/**
 * Transmit an CONFMSG packet. We send one of these when we wish to send a message to a conference.
 */
public class SendConfereneMessage extends AbstractConferenceMessage {
	private String message;

	public SendConfereneMessage(String username, YahooConference conference, ConferenceMembership membership, String message) {
		super(username, conference, membership);
		this.message = message;
	}

	@Override
	public PacketBodyBuffer getBody() throws IOException {
		PacketBodyBuffer body = new PacketBodyBuffer();
		this.writeUsername(body, "1");
		this.writeConference(body, "57");
		this.writeMembers(body, "53");
		this.writeConference(body, "57");
		body.addElement("14", message);
		// TODO - where to put this?
		// if (Util.isUtf8(message)) body.addElement("97", "1");
		return body;
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.CONFMSG;
	}

	@Override
	public MessageStatus getMessageStatus() {
		return MessageStatus.DEFAULT;
	}

}
