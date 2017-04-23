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

}