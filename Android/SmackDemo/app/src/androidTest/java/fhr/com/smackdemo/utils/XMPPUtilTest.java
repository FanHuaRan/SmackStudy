package fhr.com.smackdemo.utils;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static  org.junit.Assert.*;
/**
 * XMPP协议辅助测试类
 * 基于ASMACK开发包
 * 参考：http://blog.csdn.net/h7870181/article/details/12500231
 * @author fhr
 * @date 2017/04/21
 */
@RunWith(AndroidJUnit4.class)
public class XMPPUtilTest  {
    @Test
    public void testGetXMPPConnection(){
         XMPPConnection xmppConnection=XMPPUtil.createXMPPConnection(null);
         assertNotNull(xmppConnection);
    }
    @Test
    public void testGetXMPPConnectionFull(){

    }
    @Test
    public void testConnectServer(){
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.connectServer(xmppConnection));
    }
    @Test
    public void testRelaseXMPPConnection(){
        XMPPConnection xmppConnection = getFullXMPPConnection();
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }
    @Test
    public void testRegist(){
        XMPPConnection xmppConnection= getFullXMPPConnection();
        Map<String, String> attributes=new HashMap<String,String>(){
            {
                put("name", "fhr");
                put("first ","f");
                put("last ","hr");
                put("email ","834171100@qq.com");
                put("city","Chengdu");
                put("text ","test");
            }
        };
        assertTrue(XMPPUtil.regist(xmppConnection,"ranrandemo","admin123",attributes));
    }
    @Test
    public void testLoginXMPPConnectionStringString() {
        XMPPConnection xmppConnection= getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection,"ranrandemo", "admin123"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testLoginStringString() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertNotNull(XMPPUtil.login("ranrandemo", "admin123",null));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testSetPresence() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertTrue(XMPPUtil.setPresence(xmppConnection, Presence.Type.error, "I am bad"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testCreateChat() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertNotNull(XMPPUtil.createChat(xmppConnection, "tom", null));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testSendMessage() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        Chat chat=XMPPUtil.createChat(xmppConnection, "tom", null);
        assertTrue(XMPPUtil.sendMessage(chat, "hello"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testGetAllRosterEntry() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        List<RosterEntry> rosterEntries=XMPPUtil.getAllRosterEntry(xmppConnection);
        assertNotNull(rosterEntries);
        assertTrue(rosterEntries.size()>0);
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testGetAllRosterGroup() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        List<RosterGroup> rosterGroups=XMPPUtil.getAllRosterGroup(xmppConnection);
        assertNotNull(rosterGroups);
        assertTrue(rosterGroups.size()>0);
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testGetRosterEntrysForGroup() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        List<RosterEntry> rosterEntries=XMPPUtil.getRosterEntrysForGroup(xmppConnection, "friends");
        assertNotNull(rosterEntries);
        assertTrue(rosterEntries.size()>0);
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testGetUserVCard() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertNotNull(XMPPUtil.getUserVCard(xmppConnection, "ranrandemo"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testGetUserImage() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertNotNull(XMPPUtil.getUserImage(xmppConnection,"ranrandemo"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testAddGroup() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertTrue(XMPPUtil.addGroup(xmppConnection, "familys"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testRemoveGroup() {
        fail("Smack Not yet implemented");
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testAddUserNoGroup() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertTrue(XMPPUtil.addUserNoGroup(xmppConnection, "tom", "mytom"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testAddUserHaveGroup() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertTrue(XMPPUtil.addUserHaveGroup(xmppConnection, "tom", "mytom", "friends"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testRemoveUser() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertTrue(XMPPUtil.removeUser(xmppConnection, "liuqiang"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testSearchUsers() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        List<HashMap<String, String>> users=XMPPUtil.searchUsers(xmppConnection, "liuqiang");
        assertNotNull(users);
        assertTrue(users.size()>0);
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testChangeStateMessage() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "tom", "123"));
        XMPPUtil.changeStateMessage(xmppConnection,"bad");
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testChangeUserImage() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertTrue(XMPPUtil.changeUserImage(xmppConnection,new File("/storage/emulated/0/Download/myimage.jpg")));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testDeleteAccount() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "testuser", "123"));
        assertTrue(XMPPUtil.deleteAccount(xmppConnection));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testChangePassword() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "updatepassworduser", "123"));
        assertTrue(XMPPUtil.changePassword(xmppConnection,"1234"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testGetHostRooms() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertNotNull(XMPPUtil.getHostRooms(xmppConnection));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testCreateRoom() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertNotNull(XMPPUtil.createRoom(xmppConnection,"firstUserChat",null,null));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testJoinMultiUserChat() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertNotNull(XMPPUtil.createRoom(xmppConnection,"firstUserChat",null,null));
        XMPPConnection xmppConnection2 = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection2, "tom", "123"));
        assertNotNull(XMPPUtil.joinMultiUserChat(xmppConnection2, "firstUserChat", null,null));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
        XMPPUtil.relaseXMPPConnection(xmppConnection2);
    }

    @Test
    public void testFindUsersInMultiUserChat() {
        fail("not write code enough");
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        //assertNotNull(XMPPUtil.findUsersInMultiUserChat(multiUserChat)(xmppConnection,"firstUserChat",null));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @Test
    public void testSendFileToUser() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        XMPPUtil.sendFileToUser(xmppConnection, "tom","/storage/emulated/0/Download/myimage.jpg","测试发送文件");
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    //@Test
    public void testGetOfflnMessage() {
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        Map<String,List<HashMap<String, String>>> offlnMessages=XMPPUtil.getOfflnMessage(xmppConnection);
        assertNotNull(offlnMessages);
        assertTrue(offlnMessages.size()>0);
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    //@Test
    public void testIsUserOnLine() {
        fail("Servcie Not have plugin");
        XMPPConnection xmppConnection = getFullXMPPConnection();
        assertTrue(XMPPUtil.login(xmppConnection, "ranrandemo", "admin123"));
        assertEquals(1,XMPPUtil.isUserOnLine("ranrandemo"));
        XMPPUtil.relaseXMPPConnection(xmppConnection);
    }

    @NonNull
    private XMPPConnection getFullXMPPConnection() {
        XMPPConnection xmppConnection = XMPPUtil.createXMPPConnection(null);
        assertNotNull(xmppConnection);
        assertTrue(XMPPUtil.connectServer(xmppConnection));
        return xmppConnection;
    }
}
