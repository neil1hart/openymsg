package org.openymsg.contact;

import java.util.Set;

import org.openymsg.YahooContact;
import org.openymsg.YahooContactGroup;
import org.openymsg.YahooContactStatus;
import org.openymsg.contact.group.SessionGroupImpl;
import org.openymsg.contact.roster.SessionRosterImpl;
import org.openymsg.contact.status.SessionStatusImpl;
import org.openymsg.execute.Executor;
import org.openymsg.network.ServiceType;

//TODO verify, no status without a contact
public class SessionContactImpl implements SessionContact {
	private SessionRosterImpl sessionRoster;
	private SessionGroupImpl sessionGroup;
	private SessionStatusImpl sessionStatus;
	private Executor executor;

	public SessionContactImpl(Executor executor, String username, SessionContactCallback callback) {
		this.executor = executor;
		sessionRoster = new SessionRosterImpl(executor, username, callback);
		sessionGroup = new SessionGroupImpl(executor, username);
		sessionStatus = new SessionStatusImpl(executor, callback);
		this.executor.register(ServiceType.LIST_15, new ListOfContactsResponse(sessionRoster, sessionGroup,
				sessionStatus));
	}

	@Override
	public Set<YahooContactGroup> getContactGroups() {
		return this.sessionGroup.getContactGroups();
	}

	@Override
	public Set<YahooContact> getContacts() {
		return this.sessionRoster.getContacts();
	}

	@Override
	public void acceptFriendAuthorization(YahooContact contact) throws IllegalStateException {
		this.sessionRoster.acceptFriendAuthorization(contact);
	}

	@Override
	public void rejectFriendAuthorization(YahooContact contact, String message) throws IllegalStateException {
		this.sessionRoster.rejectFriendAuthorization(contact, message);
	}

	@Override
	public void removeFromGroup(YahooContact contact, YahooContactGroup group) {
		this.sessionRoster.removeFromGroup(contact, group);
	}

	@Override
	public void addContact(YahooContact contact, YahooContactGroup group) throws IllegalArgumentException {
		this.sessionRoster.addContact(contact, group);
	}

	@Override
	public void addGroup(String groupName) {
		this.sessionGroup.addGroup(groupName);
	}

	@Override
	public YahooContactStatus getStatus(YahooContact contact) {
		return this.sessionStatus.getStatus(contact);
	}

}
