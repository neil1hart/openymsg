/**
 * 
 */
package org.openymsg.legacy.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openymsg.SlowTest;
import org.openymsg.legacy.network.FireEvent;
import org.openymsg.legacy.network.ServiceType;
import org.openymsg.legacy.network.Status;
import org.openymsg.legacy.network.YahooProtocol;
import org.openymsg.legacy.network.YahooUser;

import java.io.IOException;

/**
 * @author Giancarlo Frison - Nimbuzz B.V. <giancarlo@nimbuzz.com>
 */
@Category(SlowTest.class)
public class ContactsIT extends YahooTestAbstract {
	@Test
	@Category(SlowTest.class)
	public void testAddContact() throws Exception {
		removeAllContacts(sess1, listener1);
		if (!sess1.getRoster().isEmpty()) {
			throw new IllegalStateException("Test setup problem. Roster1 should have been emptied by now.");
		}
		removeAllContacts(sess2, listener2);
		if (!sess2.getRoster().isEmpty()) {
			// System.err.print("Roster still contains: ");
			// for (YahooUser user : sess2.getRoster()) {
			// System.err.print(user.getId() + ",");
			// }
			// System.err.println(".");
			throw new IllegalStateException("Test setup problem. Roster2 should have been emptied by now.");
		}
		addfriend();
	}

	@Test
	@Category(SlowTest.class)
	public void testReLoginFriendAndChangeStatusBuddy() throws Exception {
		YahooUser buddy = sess1.getRoster().getUser(OTHERUSR);
		assertNotNull(buddy);
		assertEquals(Status.AVAILABLE, buddy.getStatus());
		sess2.logout();
		Thread.sleep(500);
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
		sess1.getRoster().add(new YahooUser(OTHERUSR, "group", YahooProtocol.YAHOO));
		FireEvent event = listener2.waitForEvent(5, ServiceType.Y7_AUTHORIZATION);
		assertNotNull(event);
		assertEquals(event.getType(), ServiceType.Y7_AUTHORIZATION);
		assertEquals(event.getEvent().getFrom(), USERNAME);
		event = listener1.waitForEvent(5, ServiceType.FRIENDADD);
		assertEquals(event.getType(), ServiceType.FRIENDADD);
		assertTrue(sess1.getRoster().containsUser(OTHERUSR));
	}

	@Test
	@Category(SlowTest.class)
	public void testRejectContact() throws IOException, InterruptedException {
		removeAllContacts(sess1, listener1);
		assertFalse(sess1.getRoster().containsUser(OTHERUSR));
		sess1.getRoster().add(new YahooUser(OTHERUSR, "group", YahooProtocol.YAHOO));
		// assertNotNull(listener1.waitForEvent(5, ServiceType.FRIENDADD));
		Thread.sleep(500);
		final FireEvent event = listener2.waitForEvent(5, ServiceType.Y7_AUTHORIZATION);
		assertNotNull(event);
		sess2.rejectContact(event.getEvent(), "i don't want you");
		assertNotNull(listener1.waitForEvent(5, ServiceType.CONTACTREJECT));
		assertFalse(sess1.getRoster().containsUser(OTHERUSR));
	}

	@Test
	public void testRemoveUnknowContact() {
		sess1.getRoster().remove(new YahooUser("ewrgergerg", CHATMESSAGE, YahooProtocol.YAHOO));
		FireEvent event = listener1.waitForEvent(5);
		assertNull(event);
	}
}
