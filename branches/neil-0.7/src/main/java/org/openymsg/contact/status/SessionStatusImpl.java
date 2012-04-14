package org.openymsg.contact.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openymsg.Contact;
import org.openymsg.ContactStatus;
import org.openymsg.execute.Executor;
import org.openymsg.network.ServiceType;

/**
 * Handles several kinds of status messages. Status15 - single messages for Yahoo 9 users logging in and out/invisible
 * for MSN users Y6 Status Update for Yahoo 9 users changing status
 * @author neilhart
 */
public class SessionStatusImpl implements SessionStatus, SessionStatusCallback {
	private static final Log log = LogFactory.getLog(SessionStatusImpl.class);
	private Executor executor;
	private SessionStatusCallback callback;
	private Map<Contact, ContactStatus> statuses = new HashMap<Contact, ContactStatus>();

	public SessionStatusImpl(Executor executor, SessionStatusCallback callback) {
		this.executor = executor;
		this.callback = callback;
		SingleStatusResponse singleStatusResponse = new SingleStatusResponse(this);
		this.executor.register(ServiceType.STATUS_15, new ListOfStatusesResponse(singleStatusResponse));
		this.executor.register(ServiceType.Y6_STATUS_UPDATE, singleStatusResponse);
	}

	public ContactStatus getStatus(Contact contact) {
		return this.statuses.get(contact);
	}

	@Override
	public void statusUpdate(Contact contact, ContactStatus status) {
		log.trace("statusUpdate: " + contact + " " + status);
		this.statuses.put(contact, status);
		this.callback.statusUpdate(contact, status);
	}

	public void addPending(Contact contact) {
		// TODO Auto-generated method stub

	}

	public void publishPending(Contact contact) {
		// TODO Auto-generated method stub

	}

	public void addedIgnored(Set<Contact> usersOnIgnoreList) {
		for (Contact contact : usersOnIgnoreList) {
			System.err.println("ignored: " + contact);
		}
	}

	public void addedPending(Set<Contact> usersOnPendingList) {
		// for (Contact contact : usersOnPendingList) {
		// System.err.println("pending:" + contactImpl.getId() + "/" + contactImpl.getProtocol());
		// }
	}

}
