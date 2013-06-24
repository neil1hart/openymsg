package org.openymsg.contact.status;

import org.openymsg.YahooStatus;

public class NormalStatusMessage extends AbstractStatusMessage {

	public NormalStatusMessage(YahooStatus status) {
		super(status);
	}

	@Override
	public String toString() {
		return "NormalStatusMessage [status=" + this.getStatus() + "]";
	}

}
