package fhr.com.smackdemo.utils;

import android.support.test.runner.AndroidJUnit4;

import org.jivesoftware.smack.XMPPConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
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
         XMPPConnection xmppConnection=XMPPUtil.getXMPPConnection(null);
         assertNotNull(xmppConnection);
    }
    @Test
    public void testGetXMPPConnectionFull(){

    }
    @Test
    public void testConnectServer(){
        XMPPConnection xmppConnection=XMPPUtil.getXMPPConnection(null);
        assertNotNull(xmppConnection);
        boolean isConnect=XMPPUtil.connectServer(xmppConnection);
        assertTrue(isConnect);
    }
    @Test
    public void testCloseConnection(){

    }
    @Test
    public void testRegist(){

    }
    @Test
    public void testLogin(){
//        XMPPConnection xmppConnection=XMPPUtil.getXMPPConnection(null);
//        assertNotNull(xmppConnection);
//        boolean isConnect=XMPPUtil.connectServer(xmppConnection);
//        assertTrue(isConnect);
//        boolean isLogin=XMPPUtil.login(xmppConnection,"ranrandemo","admin123");
//        assertTrue(isLogin);
    }
    @Test
    public void testLoginFull(){

    }
    @Test
    public void testSetPresence(){

    }
    @Test
    public void testGetEntriesByGroup(){

    }
    @Test
    public void testGetAllEntries(){

    }
    @Test
    public void testGetUserVCard(){

    }
    @Test
    public void testGetUserImage(){

    }
    @Test
    public void testAddGroup(){

    }
    @Test
    public void testRemoveGroup(){

    }
    @Test
    public void testAddUser(){

    }
    @Test
    public void testAddUserNoGroup(){

    }
    @Test
    public void testAddUserHaveGroup(){

    }
    @Test
    public void testRemoveUser(){

    }
    @Test
    public void testSearchUsers(){

    }
    @Test
    public void testChangeStateMessage(){

    }
    @Test
    public void testChangeUserImage(){

    }
    @Test
    public void testDeleteAccount(){

    }
    @Test
    public void testChangePassword(){

    }
    @Test
    public void testGetHostRooms(){

    }
    @Test
    public void testCreateRoom(){

    }
    @Test
    public void testJoinMultiUserChat(){

    }
    @Test
    public void testFindUsersInMultiUserChat(){

    }
    @Test
    public void testSendFileToUser(){

    }
    @Test
    public void testGetOfflnMessage(){

    }
    @Test
    public void testIsUserOnLine(){

    }
}
