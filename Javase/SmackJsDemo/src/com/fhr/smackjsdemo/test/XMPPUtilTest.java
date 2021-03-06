package com.fhr.smackjsdemo.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence.Type;
import org.junit.Test;

import com.fhr.smackjsdemo.utils.XMPPUtil;
/**
 * XMPPUtil测试
 * @author fhr
 * @date 2017/04/25 
 */
public class XMPPUtilTest {
	
	@Test
	public void testCreateXMPPConnection() {
		XMPPConnection xmppConnection=XMPPUtil.createXMPPConnection();
		assertNotNull(xmppConnection);
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testConnectServer() {
		XMPPUtil.relaseXMPPConnection(providerConnection());
	}

	@Test
	public void testRelaseXMPPConnection() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRegist() {
		XMPPConnection xmppConnection=providerConnection();
		Map<String, String> attributes=new HashMap<String,String>(){
			{
				put("name", "Tom");
				put("first ", "Cruise");
				put("last ", "Tom");
				put("email ", "81545455@qq.com");
				put("city","Chengdu");
				put("text ", "test");
			}
		};
		assertTrue(XMPPUtil.regist(xmppConnection, "Tom0425", "123",attributes));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testLoginXMPPConnectionStringString() {
		XMPPConnection xmppConnection=providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection,"ranrandemo", "admin123"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testLoginStringString() {
		XMPPConnection xmppConnection = providerConnection();
		assertNotNull(XMPPUtil.login("ranrandemo", "admin123"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testSetPresence() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertTrue(XMPPUtil.setPresence(xmppConnection, Type.error, "I am bad"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testCreateChat() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertNotNull(XMPPUtil.createChat(xmppConnection, "tom", null));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testSendMessage() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		Chat chat=XMPPUtil.createChat(xmppConnection, "tom", null);
		assertTrue(XMPPUtil.sendMessage(chat, "hello"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testGetAllRosterEntry() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		List<RosterEntry> rosterEntries=XMPPUtil.getAllRosterEntry(xmppConnection);
		assertNotNull(rosterEntries);
		assertTrue(rosterEntries.size()>0);
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testGetAllRosterGroup() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		List<RosterGroup> rosterGroups=XMPPUtil.getAllRosterGroup(xmppConnection);
		assertNotNull(rosterGroups);
		assertTrue(rosterGroups.size()>0);
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testGetRosterEntrysForGroup() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		List<RosterEntry> rosterEntries=XMPPUtil.getRosterEntrysForGroup(xmppConnection, "friends");
		assertNotNull(rosterEntries);
		assertTrue(rosterEntries.size()>0);
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testGetUserVCard() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertNotNull(XMPPUtil.getUserVCard(xmppConnection, "ranrandemo"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testGetUserImage() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertNotNull(XMPPUtil.getUserImage(xmppConnection,"ranrandemo"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testAddGroup() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertTrue(XMPPUtil.addGroup(xmppConnection, "familys"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testRemoveGroup() {
		fail("Smack Not yet implemented");
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testAddUserNoGroup() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertTrue(XMPPUtil.addUserNoGroup(xmppConnection, "tom", "mytom"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testAddUserHaveGroup() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertTrue(XMPPUtil.addUserHaveGroup(xmppConnection, "tom", "mytom", "friends"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testRemoveUser() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertTrue(XMPPUtil.removeUser(xmppConnection, "liuqiang"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testSearchUsers() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		List<HashMap<String, String>> users=XMPPUtil.searchUsers(xmppConnection, "liuqiang");
		assertNotNull(users);
		assertTrue(users.size()>0);
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testChangeStateMessage() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "tom", "123"));
		XMPPUtil.changeStateMessage(xmppConnection,"bad");
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testChangeUserImage() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertTrue(XMPPUtil.changeUserImage(xmppConnection,new File("E:\\项目\\temp2\\RaskRoadaAndGeology\\pixChange\\bin\\Debug\\Images\\propertyquery.png")));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testDeleteAccount() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "testuser", "123"));
		assertTrue(XMPPUtil.deleteAccount(xmppConnection));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testChangePassword() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "updatepassworduser", "123"));
		assertTrue(XMPPUtil.changePassword(xmppConnection,"1234"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testGetHostRooms() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertNotNull(XMPPUtil.getHostRooms(xmppConnection));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testCreateRoom() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertNotNull(XMPPUtil.createRoom(xmppConnection,"firstUserChat",null,null));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testJoinMultiUserChat() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertNotNull(XMPPUtil.createRoom(xmppConnection,"firstUserChat",null,null));
		XMPPConnection xmppConnection2 = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection2, "tom", "123"));
		assertNotNull(XMPPUtil.joinMultiUserChat(xmppConnection2, "firstUserChat", null,null));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
		XMPPUtil.relaseXMPPConnection(xmppConnection2);
	}

	@Test
	public void testFindUsersInMultiUserChat() {
		fail("not write code enough");
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		//assertNotNull(XMPPUtil.findUsersInMultiUserChat(multiUserChat)(xmppConnection,"firstUserChat",null));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	@Test
	public void testSendFileToUser() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		XMPPUtil.sendFileToUser(xmppConnection, "tom","E:\\项目\\temp2\\RaskRoadaAndGeology\\pixChange\\bin\\Debug\\Images\\propertyquery.png","测试发送文件");
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	//@Test
	public void testGetOfflnMessage() {
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		Map<String,List<HashMap<String, String>>> offlnMessages=XMPPUtil.getOfflnMessage(xmppConnection);
		assertNotNull(offlnMessages);
		assertTrue(offlnMessages.size()>0);
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}

	//@Test
	public void testIsUserOnLine() {
		fail("Servcie Not have plugin");
		XMPPConnection xmppConnection = providerConnection();
		assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
		assertEquals(1,XMPPUtil.isUserOnLine("ranrandemo"));
		XMPPUtil.relaseXMPPConnection(xmppConnection);
	}
	
	private XMPPConnection providerConnection() {
		XMPPConnection xmppConnection=XMPPUtil.createXMPPConnection();
		assertNotNull(xmppConnection);
		assertTrue(XMPPUtil.connectServer(xmppConnection));
		return xmppConnection;
	}
}
