package com.fhr.smackjsdemo.test;

import static org.junit.Assert.*;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.junit.Test;

public class XMPPUtilsTest {

	@Test
	public void testLogin() throws XMPPException {
		XMPPConnection xmppConnection=new XMPPConnection("localhost");
		xmppConnection.connect();
		xmppConnection.login("ranrandemo", "admin123");
	}
	@Test
	public void testCreateXMPPConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnectServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRelaseXMPPConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegist() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoginXMPPConnectionStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoginStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPresence() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateChat() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRosterEntry() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRosterGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRosterEntrysForGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserVCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserNoGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserHaveGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeStateMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeUserImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangePassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHostRooms() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testJoinMultiUserChat() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUsersInMultiUserChat() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendFileToUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOfflnMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsUserOnLine() {
		fail("Not yet implemented");
	}

}
