/*
 * OpenYMSG, an implementation of the Yahoo Instant Messaging and Chat protocol.
 * Copyright (C) 2007 G. der Kinderen, Nimbuzz.com 
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. 
 */
package org.openymsg.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

/**
 * @author G. der Kinderen, Nimbuzz B.V. guus@nimbuzz.com
 */
public class PropertiesAvailableTest extends TestCase {
	private static final Properties props = new Properties();

	static {
		FileInputStream reader = null;
		try {
			reader = new FileInputStream(
					"resources/yahooAuthenticationForJUnitTests.properties");
			props.load(reader);
			PropertyConfigurator.configure("resources/log4j.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testGetTestUser() {
		final String[] user = getAccount("testuser");
		assertNotNull(user);
		assertEquals("DummyYahooUsername", user[0]);
		assertEquals("YahooPassword", user[1]);
	}

	public static String getUsername(String accountname) {
		final String value = props.getProperty(accountname);
		assertNotNull(
				"The property 'testuser' should be set in the resource file, but isn't.",
				value);
		return value;
	}

	public static String getPassword(String username) {
		assertNotNull(username);
		final String value = props.getProperty(username);
		assertNotNull(
				"There's no property that specifies the password for this username set in the resource file.",
				value);
		return value;
	}

	public static String[] getAccount(String accountName) {
		final String username = getUsername(accountName);
		final String password = getPassword(username);

		return new String[] { username, password };
	}
}
