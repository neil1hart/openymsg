/**
 * 
 */
package org.openymsg.test;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openymsg.network.ServiceType;
import org.openymsg.network.Session;
import org.openymsg.network.YahooGroup;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.WaitListener;

/**
 * @author Giancarlo Frison - Nimbuzz B.V. <giancarlo@nimbuzz.com>
 *
 */
public class YahooTest {
	/**
	 * 
	 */
	protected static final String CHATMESSAGE = "CHATMESSAGE";

	protected static String USERNAME = PropertiesAvailableTest.getUsername("presenceuser1");

	protected static String PASSWORD = PropertiesAvailableTest	.getPassword(USERNAME);

	protected static String OTHERUSR = PropertiesAvailableTest.getUsername("logintestuser3");

	protected static String OTHERPWD = PropertiesAvailableTest.getPassword(OTHERUSR);
	protected static Session sess1;
	protected static Session sess2;

	protected static WaitListener listener1;

	protected static WaitListener listener2;


	/**
	 * @throws Throwable 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		try {
		sess1 = new Session();
		sess2 = new Session();
		listener1 = new WaitListener(sess1);
		listener2 = new WaitListener(sess2);
		sess1.login(USERNAME, PASSWORD);
		sess2.login(OTHERUSR, OTHERPWD);
		sess1.addSessionListener(listener1);
		sess2.addSessionListener(listener2);
		listener1.waitForEvent(2, ServiceType.LOGON);
		listener2.waitForEvent(2, ServiceType.LOGON);
		removeAll(sess1);

		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		removeAll(sess1);
		sess1.logout();
		sess2.logout();
	}
	/**
	 * @return 
	 * @throws IOException
	 */
	protected static void removeAll(Session sess) throws IOException {
		drain();
		for(YahooGroup group: sess.getGroups())
			for(YahooUser user:group.getUsers()) {
				sess1.removeFriend(user.getId(), group.getName());
				listener1.waitForEvent(5, ServiceType.FRIENDREMOVE);
			}
	}


	/**
	 * 
	 */
	protected static void drain() {
		listener1.clearEvents();
		listener2.clearEvents();
	}

}
