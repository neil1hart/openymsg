/**
 * 
 */
package org.openymsg.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FireEvent;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.ServiceType;
import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;

/**
 * @author Giancarlo Frison - Nimbuzz B.V. <giancarlo@nimbuzz.com>
 * 
 */
public class ContactsTest extends YahooTestAbstract {

	@Test
	public void testAddContact() throws Exception {
		removeAllContacts(sess1);
		removeAllContacts(sess2);
		
		if (!sess1.getRoster().isEmpty()) {
			throw new IllegalStateException("Test setup problem. Roster1 should have been emptied by now.");
		}

		if (!sess2.getRoster().isEmpty()) {
			throw new IllegalStateException("Test setup problem. Roster2 should have been emptied by now.");
		}
		
		addfriend();
	}

	@Test
	public void testReLoginFriendAndChangeStatusBuddy()
			throws IllegalStateException, IOException, AccountLockedException,
			LoginRefusedException, InterruptedException {
		sess2.logout();
		Thread.sleep(500);
		YahooUser buddy = sess1.getRoster().getUser(OTHERUSR);
		assertNotNull(buddy);

		assertEquals(Status.OFFLINE, buddy.getStatus());
		sess2.login(OTHERUSR, OTHERPWD);
		Thread.sleep(500);
		buddy = sess1.getRoster().getUser(OTHERUSR);
		assertNotNull(buddy);
		assertEquals(Status.AVAILABLE, buddy.getStatus());
	}

	/**
	 * @throws IOException
	 */
	private void addfriend() {

		drain();

		sess1.getRoster().add(new YahooUser(OTHERUSR, "group"));
		FireEvent event = listener2.waitForEvent(5, ServiceType.CONTACTNEW);
		assertNotNull(event);
		assertEquals(event.getType(), ServiceType.CONTACTNEW);
		assertEquals(event.getEvent().getFrom(), USERNAME);
		event = listener1.waitForEvent(5, ServiceType.FRIENDADD);
		assertEquals(event.getType(), ServiceType.FRIENDADD);
		assertTrue(sess1.getRoster().containsUser(OTHERUSR));
	}

	@Test
	public void testRejectContact() throws IOException, InterruptedException {
		removeAllContacts(sess1);
		sess1.getRoster().add(new YahooUser(OTHERUSR, "group"));
//		assertNotNull(listener1.waitForEvent(5, ServiceType.FRIENDADD));
		Thread.sleep(500);
		
		final FireEvent event = listener2.waitForEvent(5, ServiceType.CONTACTNEW);
		assertNotNull(event);
		
		sess2.rejectContact(event.getEvent(), "i don't want you");
		assertNotNull(listener1.waitForEvent(5, ServiceType.CONTACTREJECT));

		assertFalse(sess1.getRoster().containsUser(OTHERUSR));
	}

	@Test
	public void testRemoveUnknowContact() {
		sess1.getRoster().remove(new YahooUser("ewrgergerg", CHATMESSAGE));
		FireEvent event = listener1.waitForEvent(5);
		assertNull(event);
	}
}