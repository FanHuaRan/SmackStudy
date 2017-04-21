package fhr.com.smackdemo.utils;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.AdHocCommandDataProvider;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;
import org.jivesoftware.smackx.search.UserSearchManager;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * XMPP协议辅助类
 * 基于ASMACK开发包
 * 参考：http://blog.csdn.net/h7870181/article/details/12500231
 * @author fhr
 * @date 2017/04/21
 */
public class XMPPUtil {
    /**
     * 服务器地址
     */
    private static  final  String SERVERADDRESS="";
    /**
     * 端口
     */
    private static  final  int PORT=0;
    /**
     * 服务器名称
     */
    private static  final  String SERVERNAME="";

    static {
        //配置相关资源
        configureProviderConnection(ProviderManager.getInstance());
    }

    /**
     * 工厂模式获取连接对象 还没有连接服务器
     * @param connectionListener 连接监听器
     * @return
     */
    public static XMPPConnection getXMPPConnection(ConnectionListener connectionListener){
        return getXMPPConnection(30,false,false,true, ConnectionConfiguration.SecurityMode.disabled,connectionListener);
    }

    /**
     * 工厂模式获取连接对象 还没有连接服务器
     * @param connectionTimeOut 连接超时的时间
     * @param reconnectionAllowed 是否准许重连接
     * @param isPresence 是否在线
     * @param debugEnable 是否调试
     * @param securityMode 安全模式
     * @param connectionListener 连接监听器
     * @return
     */
    public static XMPPConnection getXMPPConnection(int connectionTimeOut,boolean reconnectionAllowed, boolean isPresence, boolean debugEnable,
                                                         ConnectionConfiguration.SecurityMode securityMode,ConnectionListener connectionListener){
        //设置是否开启DEBUG模式
        XMPPConnection.DEBUG_ENABLED=debugEnable;
        //设置连接地址、端口
        ConnectionConfiguration configuration=new ConnectionConfiguration(SERVERADDRESS,PORT);
        //设置服务器名称
        configuration.setServiceName(SERVERNAME);
        //设置是否需要SAS验证
        configuration.setSASLAuthenticationEnabled(false);
        //设置安全类型
        configuration.setSecurityMode(securityMode);
        //设置用户状态
        configuration.setSendPresence(isPresence);
        //设置是否准许重连接
        configuration.setReconnectionAllowed(reconnectionAllowed);
        //实例化连接对象
        XMPPConnection xmppConnection=new XMPPConnection(configuration);
        //添加连接监听器
        if(connectionListener!=null){
            xmppConnection.addConnectionListener(connectionListener);
        }
        return xmppConnection;
    }

    /**
     * 连接服务器 true为连接成功，false为连接失败
     * @param xmppConnection
     * @return
     */
    public static boolean connectServer(XMPPConnection xmppConnection){
       try{
           xmppConnection.connect();
           return  true;
       }catch (Exception e){
           Log.e("connectServer",e.getMessage());
           return  false;
       }
    }
    /**
     *关闭连接
     * @param xmppConnection
     */
    public static  void closeConnection(XMPPConnection xmppConnection){
        if(xmppConnection!=null){
            if(xmppConnection.isConnected()){
                xmppConnection.disconnect();
            }
            xmppConnection=null;
        }
    }

    /**
     * 注册用户
     * @param xmppConnection
     * @param userName
     * @param password
     * @return 1、注册成功 0、服务器没有返回结果2、这个账号已经存在3、注册失败
     */
    public static  int regist(XMPPConnection xmppConnection,String userName,String password){
        Registration registration=new Registration();
        registration.setType(IQ.Type.SET);
        registration.setTo(xmppConnection.getServiceName());
        registration.setUsername(userName);
        registration.setPassword(password);
        // 这边addAttribute不能为空，否则出错。所以做个标志是android手机创建的吧！！！！！
        registration.addAttribute("android", "fhr");
        PacketFilter filter = new AndFilter(new PacketIDFilter(
                registration.getPacketID()), new PacketTypeFilter(IQ.class));
        PacketCollector collector = xmppConnection.createPacketCollector(
                filter);
        xmppConnection.sendPacket(registration);
        IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        // Stop queuing results停止请求results（是否成功的结果）
        collector.cancel();
        if (result == null) {
            Log.e("regist", "No response from server.");
            return 0;
        } else if (result.getType() == IQ.Type.RESULT) {
            Log.v("regist", "regist success.");
            return 1;
        } else {
            if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
                Log.e("regist", "IQ.Type.ERROR: "
                        + result.getError().toString());
                return 2;
            } else {
                Log.e("regist", "IQ.Type.ERROR: "
                        + result.getError().toString());
                return 3;
            }
        }
    }
    /**
     * 登录
     * @param xmppConnection
     * @param userName
     * @param password
     * @return
     */
    public static  boolean login(XMPPConnection xmppConnection,String userName,String password){
        try{
            xmppConnection.login(userName,password);
            return  true;
        }catch (Exception e){
            Log.e("login",e.getMessage());
            return  false;
        }
    }
    /**
     * 登录
     * 返回XMPPConnection则登录成功
     * 返回null则登录失败
     * @param userName
     * @param password
     * @param connectionListener
     * @return
     */
    public  static  XMPPConnection login(String userName,String password,ConnectionListener connectionListener){
        XMPPConnection connection=getXMPPConnection(connectionListener);
        if(connectServer(connection)&&login(connection,userName,password)){
            return  connection;
        }
        return null;
    }

    /**
     * 设置用户状态
     * @param xmppConnection
     * @param type
     * @return
     */
    public  static boolean  setPresence(XMPPConnection xmppConnection,Presence.Type type){
        Presence presence=new Presence(type);
        try{
            xmppConnection.sendPacket(presence);
            return true;
        }catch (Exception e){
            Log.e("setPresence",e.getMessage());
            return  false;
        }
    }

    /**
     * 获取所有分组组合
     * @param xmppConnection
     * @return
     */
    public static List<RosterGroup> getGroups(XMPPConnection xmppConnection){
        List<RosterGroup> rosterGroups=new ArrayList<>();
        Iterator<RosterGroup> i =xmppConnection.getRoster().getGroups().iterator();
        while (i.hasNext()) {
            rosterGroups.add(i.next());
        }
        return rosterGroups;
    }

    /**
     * 获取某个组里面的所有好友
     * @param xmppConnection
     * @param groupName 组名
     * @return
     */
    public static List<RosterEntry> getEntriesByGroup(XMPPConnection xmppConnection, String groupName) {
        List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
        RosterGroup rosterGroup = xmppConnection.getRoster().getGroup(groupName);
        Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
        Iterator<RosterEntry> i = rosterEntry.iterator();
        while (i.hasNext()) {
            Entrieslist.add(i.next());
        }
        return Entrieslist;
    }

    /**
     * 获取所有好友信息
     * @param xmppConnection
     * @return
     */
    public static List<RosterEntry> getAllEntries(XMPPConnection xmppConnection) {
        List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
        Collection<RosterEntry> rosterEntry = xmppConnection.getRoster().getEntries();
        Iterator<RosterEntry> i = rosterEntry.iterator();
        while (i.hasNext()) {
            Entrieslist.add(i.next());
        }
        return Entrieslist;
    }

    /**
     * 获取用户VCard信息
     * @param xmppConnection
     * @param user
     * @return
     */
    public static VCard getUserVCard(XMPPConnection xmppConnection,String user) {
        try {
            VCard vcard = new VCard();
            vcard.load(xmppConnection,user);
            return  vcard;
        } catch (XMPPException e) {
            e.printStackTrace();
            Log.e("getUserVCard",e.getMessage());
            return  null;
        }
    }

    /**
     * 获取用户头像信息
     * @param xmppConnection
     * @param user
     * @return
     */
    public static Drawable getUserImage(XMPPConnection xmppConnection, String user) {
        ByteArrayInputStream bais = null;
        try {
            VCard vcard = new VCard();
            // 加入这句代码，解决No VCard for
            ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp", new org.jivesoftware.smackx.provider.VCardProvider());
            if (user == null || user.equals("")) {
                return null;
            }
            vcard.load(xmppConnection, user + "@" + xmppConnection.getServiceName());
            if (vcard == null || vcard.getAvatar() == null){
                return null;
            }
            bais = new ByteArrayInputStream(vcard.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getUserImage",e.getMessage());
            return null;
        }
        Drawable bitmapDrawable=new BitmapDrawable(bais);
        return  bitmapDrawable;
    }
    /**
     * 添加一个分组
     * @param xmppConnection
     * @param groupName
     * @return
     */
    public static boolean addGroup(XMPPConnection xmppConnection,String groupName) {
        try {
            xmppConnection.getRoster().createGroup(groupName);
            return true;
        } catch (Exception e) {
            Log.e("addGroup",e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除分组
     * @param xmppConnection
     * @param groupName
     * @return
     */
    public static boolean removeGroup(XMPPConnection xmppConnection,String groupName) {
        return true;
    }

    /**
     * 添加好友 无分组
     * @param xmppConnection
     * @param userName 用户名
     * @param name 备注名
     * @return
     */
    public static boolean addUserNoGroup(XMPPConnection xmppConnection,String userName, String name) {
        try {
            xmppConnection.getRoster().createEntry(userName, name, null);
            return true;
        } catch (Exception e) {
            Log.e("addUser",e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加好友 有分组
     * @param xmppConnection
     * @param userName 用户名
     * @param name 备注名
     * @param groupName 分组名
     * @return
     */
    public static boolean addUserHaveGroup(XMPPConnection xmppConnection,String userName, String name, String groupName) {
        try {
            Presence subscription = new Presence(Presence.Type.subscribed);
            subscription.setTo(userName);
            userName += "@" + xmppConnection.getServiceName();
            xmppConnection.sendPacket(subscription);
            xmppConnection.getRoster().createEntry(userName, name, new String[] { groupName });
            return true;
        } catch (Exception e) {
            Log.e("addUser",e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除好友
     * @param xmppConnection
     * @param userName
     * @return
     */
    public static boolean removeUser(XMPPConnection xmppConnection,String userName) {
        try {
            RosterEntry entry = null;
            if (userName.contains("@")){
                entry = xmppConnection.getRoster().getEntry(
                        userName + "@" + xmppConnection.getServiceName());
            }else{
                entry = xmppConnection.getRoster().getEntry(
                        userName + "@" + xmppConnection.getServiceName());
            }
            if (entry == null){
                entry = xmppConnection.getRoster().getEntry(userName);
            }
            xmppConnection.getRoster().removeEntry(entry);
            return true;
        } catch (Exception e) {
            Log.e("removeUser",e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询用户
     * @param xmppConnection
     * @param userName
     * @return
     * @throws XMPPException
     */
    public static List<HashMap<String, String>> searchUsers(XMPPConnection xmppConnection,String userName) {
        HashMap<String, String> user = null;
        List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
        try {
            UserSearchManager usm = new UserSearchManager(xmppConnection);
            Form searchForm = usm.getSearchForm(xmppConnection.getServiceName());
            Form answerForm = searchForm.createAnswerForm();
            answerForm.setAnswer("userAccount", true);
            answerForm.setAnswer("userPhote", userName);
            ReportedData data = usm.getSearchResults(answerForm, "search" + xmppConnection.getServiceName());
            Iterator<ReportedData.Row> it = data.getRows();
            ReportedData.Row row = null;
            while (it.hasNext()) {
                user = new HashMap<String, String>();
                row = it.next();
                user.put("userAccount", row.getValues("userAccount").next().toString());
                user.put("userPhote", row.getValues("userPhote").next().toString());
                results.add(user);
            }
        } catch (XMPPException e) {
            Log.e("searchUsers",e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 修改心情
     * @param xmppConnection
     * @param status
     */
    public static void changeStateMessage(XMPPConnection xmppConnection,String status) {
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus(status);
        xmppConnection.sendPacket(presence);
    }
     /**
     * 修改用户头像
     * @param xmppConnection
     * @param file
     */
     public static boolean changeUserImage(XMPPConnection xmppConnection,File file) {
        try {
            VCard vcard = new VCard();
            vcard.load(xmppConnection);
            byte[] bytes= MyFileUtil.getFileBytes(file);
            String encodedImage = StringUtils.encodeBase64(bytes);
            vcard.setAvatar(bytes, encodedImage);
            vcard.setEncodedImage(encodedImage);
            vcard.setField("PHOTO", "<TYPE>image/jpg</TYPE><BINVAL>" + encodedImage + "</BINVAL>", true);
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    vcard.getAvatar());
            vcard.save(xmppConnection);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除当前用户
     * @param xmppConnection
     * @return
     */
    public static boolean deleteAccount(XMPPConnection xmppConnection) {
        try {
            xmppConnection.getAccountManager().deleteAccount();
            return true;
        } catch (XMPPException e) {
            Log.e("deleteAccount",e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改当前用户的密码
     * @param xmppConnection
     * @param password
     * @return
     */
    public static boolean changePassword(XMPPConnection xmppConnection,String password){
        try {
            xmppConnection.getAccountManager().changePassword(password);
            return true;
        } catch (XMPPException e) {
            Log.e("changePassword",e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取用户的所有聊天室
     * @param xmppConnection
     * @return
     */
    public static List<HostedRoom>  getHostRooms(XMPPConnection xmppConnection){
        List<HostedRoom> roominfos = new ArrayList<HostedRoom>();
        try {
            new ServiceDiscoveryManager(xmppConnection);
            Collection<HostedRoom> hostrooms = MultiUserChat.getHostedRooms(xmppConnection,xmppConnection.getServiceName());
            for (HostedRoom entry : hostrooms) {
                roominfos.add(entry);
                Log.i("room", "名字：" + entry.getName() + " - ID:" + entry.getJid());
            }
            Log.i("room", "服务会议数量:" + roominfos.size());
        } catch (XMPPException e) {
            Log.e("getHostRooms",e.getMessage());
            e.printStackTrace();
        }
        return roominfos;
    }

    /**
     * 创建聊天室
     * @param xmppConnection
     * @param roomName 房间名
     * @param password 房间密码
     * @return
     */
    public static MultiUserChat createRoom(XMPPConnection xmppConnection,String roomName,String password){
        MultiUserChat muc = null;
        try {
            // 创建一个MultiUserChat
            muc = new MultiUserChat(xmppConnection, roomName + "@conference." + xmppConnection.getServiceName());
            // 创建聊天室
            muc.create(roomName);
            // 获得聊天室的配置表单
            Form form = muc.getConfigurationForm();
            // 根据原始表单创建一个要提交的新表单。
            Form submitForm = form.createAnswerForm();
            // 向要提交的表单添加默认答复
            for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
                FormField field = (FormField) fields.next();
                if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
                    // 设置默认值作为答复
                    submitForm.setDefaultAnswer(field.getVariable());
                }
            }
            // 设置聊天室的新拥有者
            List<String> owners = new ArrayList<String>();
            owners.add(xmppConnection.getUser());// 用户JID
            submitForm.setAnswer("muc#roomconfig_roomowners", owners);
            // 设置聊天室是持久聊天室，即将要被保存下来
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            // 房间仅对成员开放
            submitForm.setAnswer("muc#roomconfig_membersonly", false);
            // 允许占有者邀请其他人
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
            if (password!=null&&!password.equals("")) {
                // 进入是否需要密码
                submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);
                // 设置进入密码
                submitForm.setAnswer("muc#roomconfig_roomsecret", password);
            }
            // 登录房间对话
            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
            // 仅允许注册的昵称登录
            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
            // 允许使用者修改昵称
            submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
            // 允许用户注册房间
            submitForm.setAnswer("x-muc#roomconfig_registration", false);
            // 发送已完成的表单（有默认值）到服务器来配置聊天室
            muc.sendConfigurationForm(submitForm);
        } catch (XMPPException e) {
            Log.e("createRomm",e.getMessage());
            e.printStackTrace();
            return null;
        }
        return muc;
    }

    /**
     * 加入聊天室
     * @param xmppConnection
     * @param roomName
     * @param password
     * @return
     */
    public static  MultiUserChat joinMultiUserChat(XMPPConnection xmppConnection,String roomName,String password){
        try {
            // 使用XMPPConnection创建一个MultiUserChat窗口
            MultiUserChat muc = new MultiUserChat(xmppConnection, roomName+ "@conference." + xmppConnection.getServiceName());
            // 聊天室服务将会决定要接受的历史记录数量
            DiscussionHistory history = new DiscussionHistory();
            history.setMaxChars(0);
            // history.setSince(new Date());
            // 用户加入聊天室
            muc.join(xmppConnection.getUser(), password, history, SmackConfiguration.getPacketReplyTimeout());
            Log.i("MultiUserChat", "会议室【"+roomName+"】加入成功........");
            return muc;
        } catch (XMPPException e) {
            Log.e("MultiUserChat", "会议室【"+roomName+"】加入失败........");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询聊天室所有成员的用户名
     * @param multiUserChat
     * @return
     */
    public  static List<String> findUsersInMultiUserChat( MultiUserChat multiUserChat){
        List<String> listUser = new ArrayList<String>();
        Iterator<String> it = multiUserChat.getOccupants();
        // 遍历出聊天室人员名称
        while (it.hasNext()) {
            // 聊天室成员名字
            String name = StringUtils.parseResource(it.next());
            listUser.add(name);
        }
        return listUser;
    }

    /**
     * 向其他用户发送文件
     * @param xmppConnection
     * @param toUser 接受文件的用户名
     * @param fileName 文件路径
     * @param remark 备注
     * @return
     */
    public static boolean sendFileToUser(XMPPConnection xmppConnection,String toUser,String fileName,String remark){
        // 创建文件传输管理器
        FileTransferManager manager = new FileTransferManager(xmppConnection);
        // 创建输出的文件传输
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(toUser);
        // 发送文件
        try {
            transfer.sendFile(new File(fileName), remark);
            return  true;
        } catch (XMPPException e) {
            e.printStackTrace();
            return  false;
        }
    }

    /**
     * 获取用户的所有离线消息
     * @param xmppConnection
     * @return
     */
    public Map<String, List<HashMap<String, String>>> getOfflnMessage(XMPPConnection xmppConnection) {
        Map<String, List<HashMap<String, String>>> offlineMsgs = null;
        try {
            OfflineMessageManager offlineManager = new OfflineMessageManager(xmppConnection);
            Iterator<Message> it = offlineManager.getMessages();
            offlineMsgs = new HashMap<String, List<HashMap<String, String>>>();
            while (it.hasNext()) {
                Message message = it.next();
                String fromUser = StringUtils.parseName(message.getFrom());
                HashMap<String, String> histrory = new HashMap<String, String>();
                histrory.put("useraccount",StringUtils.parseName(xmppConnection.getUser()));
                histrory.put("friendaccount", fromUser);
                histrory.put("info", message.getBody());
                histrory.put("type", "left");
                if (offlineMsgs.containsKey(fromUser)) {
                    offlineMsgs.get(fromUser).add(histrory);
                } else {
                    List<HashMap<String, String>> temp = new ArrayList<HashMap<String, String>>();
                    temp.add(histrory);
                    offlineMsgs.put(fromUser, temp);
                }
            }
            offlineManager.deleteMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offlineMsgs;
    }

    /**
     * 利用WEBAPI判断OpenFire用户的状态 strUrl :
     * url格式 - http://my.openfire.com:9090/plugins/presence/status?jid=[user]@SERVER_NAME&type=xml
     * 说明 ：必须要求 OpenFire加载 presence 插件，同时设置任何人都可以访问
     * @param user
     * @return  0 - 用户不存在; 1 - 用户在线; 2 - 用户离线
     */
    public static int isUserOnLine(String user) {
        String url = "http://"+SERVERADDRESS+":9090/plugins/presence/status?" + "jid="+ user +"@"+ SERVERNAME +"&type=xml";
        int shOnLineState = 0; // 不存在
        try {
            URL oUrl = new URL(url);
            URLConnection oConn = oUrl.openConnection();
            if (oConn != null) {
                BufferedReader oIn = new BufferedReader(new InputStreamReader(oConn.getInputStream()));
                if (null != oIn) {
                    String strFlag = oIn.readLine();
                    oIn.close();
                    if (strFlag.indexOf("type=\"unavailable\"") >= 0) {
                        shOnLineState = 2;
                    }
                    if (strFlag.indexOf("type=\"error\"") >= 0) {
                        shOnLineState = 0;
                    } else if (strFlag.indexOf("priority") >= 0 || strFlag.indexOf("id=\"") >= 0) {
                        shOnLineState = 1;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("isUserOnLine",e.getMessage());
            e.printStackTrace();
        }
        return shOnLineState;
    }

    /**
     * 加入相关资源
     * @param pm
     */
    private static void configureProviderConnection(ProviderManager pm) {
        // Private Data Storage
        pm.addIQProvider("query", "jabber:iq:private", new PrivateDataManager.PrivateDataIQProvider());
        // Time
        try {
            pm.addIQProvider("query", "jabber:iq:time", Class.forName("org.jivesoftware.smackx.packet.Time"));
        } catch (ClassNotFoundException e) {
            Log.w("TestClient", "Can't load class for org.jivesoftware.smackx.packet.Time");
        }
        // Roster Exchange
        pm.addExtensionProvider("x", "jabber:x:roster", new RosterExchangeProvider());
        // Message Events
        pm.addExtensionProvider("x", "jabber:x:event", new MessageEventProvider());
        // Chat State
        pm.addExtensionProvider("active", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("composing", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("paused", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("inactive", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
        pm.addExtensionProvider("gone", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
        // XHTML
        pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im", new XHTMLExtensionProvider());
        // Group Chat Invitations
        pm.addExtensionProvider("x", "jabber:x:conference", new GroupChatInvitation.Provider());
        // Service Discovery # Items
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());
        // Service Discovery # Info
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",new DiscoverInfoProvider());
        // Data Forms
        pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
        // MUC User
        pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user", new MUCUserProvider());
        // MUC Admin
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin", new MUCAdminProvider());
        // MUC Owner
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner", new MUCOwnerProvider());
        // Delayed Delivery
        pm.addExtensionProvider("x", "jabber:x:delay", new DelayInformationProvider());
        // Version
        try {
            pm.addIQProvider("query", "jabber:iq:version", Class.forName("org.jivesoftware.smackx.packet.Version"));
        } catch (ClassNotFoundException e) {
            // Not sure what's happening here.
        }
        // VCard
        pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
        // Offline Message Requests
        pm.addIQProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageRequest.Provider());
        // Offline Message Indicator
        pm.addExtensionProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageInfo.Provider());
        // Last Activity
        pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
        // User Search
        pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
        // SharedGroupsInfo
        pm.addIQProvider("sharedgroup", "http://www.jivesoftware.org/protocol/sharedgroup", new SharedGroupsInfo.Provider());
        // JEP-33: Extended Stanza Addressing
        pm.addExtensionProvider("addresses", "http://jabber.org/protocol/address", new MultipleAddressesProvider());
        // FileTransfer
        pm.addIQProvider("si", "http://jabber.org/protocol/si", new StreamInitiationProvider());
        pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams", new BytestreamsProvider());
        // Privacy
        pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());
        pm.addIQProvider("command", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider());
        pm.addExtensionProvider("malformed-action", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.MalformedActionError());
        pm.addExtensionProvider("bad-locale", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadLocaleError());
        pm.addExtensionProvider("bad-payload", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadPayloadError());
        pm.addExtensionProvider("bad-sessionid", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadSessionIDError());
        pm.addExtensionProvider("session-expired", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.SessionExpiredError());
    }
}
