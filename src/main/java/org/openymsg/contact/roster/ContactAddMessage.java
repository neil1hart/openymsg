package org.openymsg.contact.roster;

import org.openymsg.Name;
import org.openymsg.YahooContact;
import org.openymsg.YahooContactGroup;
import org.openymsg.connection.write.Message;
import org.openymsg.network.MessageStatus;
import org.openymsg.network.PacketBodyBuffer;
import org.openymsg.network.ServiceType;

import java.io.IOException;

/**
 * Transmit a ADD_BUDDY packet. If all goes well we'll get a ADD_BUDDY packet back with the details of the friend to
 * confirm the transaction (usually preceded by a CONTACTNEW packet with well detailed info).
 */
public class ContactAddMessage implements Message {
	private final String username;
	private final YahooContact contact;
	private final YahooContactGroup group;
	private final String message;
	private final Name name;

	// TODO what if name is null
	public ContactAddMessage(String username, YahooContact contact, YahooContactGroup group, String message,
			Name name) {
		this.username = username;
		this.contact = contact;
		this.group = group;
		this.message = message;
		this.name = name;
	}

	@Override
	public String toString() {
		return "ContactAddMessage [username=" + username + ", contact=" + contact + ", group=" + group + ", message="
				+ message + ", name=" + name + "]";
	}

	@Override
	public PacketBodyBuffer getBody() throws IOException {
		PacketBodyBuffer body = new PacketBodyBuffer();
		if (this.message == null) {
			body.addElement("14", "");
		} else {
			body.addElement("14", this.message);
		}
		body.addElement("65", this.group.getName());
		body.addElement("97", "1"); // TODO - UNICODE?
		if (this.name != null) {
			body.addElement("216", this.name.getFirstName());
			body.addElement("254", this.name.getLastName());
		}
		body.addElement("1", username);
		body.addElement("302", "319");
		body.addElement("300", "319");
		body.addElement("7", this.contact.getName());
		if (!this.contact.getProtocol().isYahoo()) {
			body.addElement("241", "" + this.contact.getProtocol().getValue()); // type
		}
		body.addElement("301", "319");
		body.addElement("303", "319");
		return body;
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.ADD_BUDDY;
	}

	@Override
	public MessageStatus getMessageStatus() {
		return MessageStatus.DEFAULT;
	}
}
