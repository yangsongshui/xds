package com.pgt.xds.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;
import com.pgt.xds.utils.Log;

import java.util.List;

/**
 * Created by ys on 2017/6/28.
 */

public class EMGroupService extends Service {
    public static final String TAG = EMGroupService.class.getName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate() executed");
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        //通过此接口设置回调，获得联系人变化
        EMClient.getInstance().contactManager().setContactListener(contactListener);
        //群变动事件监听
        EMClient.getInstance().groupManager().addGroupChangeListener(groupChangeListener);
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(emConnectionListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() executed");
        //服务被销毁 移除监听
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        EMClient.getInstance().contactManager().removeContactListener(contactListener);
        EMClient.getInstance().groupManager().removeGroupChangeListener(groupChangeListener);
        EMClient.getInstance().removeConnectionListener(emConnectionListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
    EMContactListener contactListener = new EMContactListener() {
        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            //好友请求被同意
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            //好友请求被拒绝
        }
    };
    EMGroupChangeListener groupChangeListener = new EMGroupChangeListener() {
        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
            //接收到群组加入邀请
        }

        @Override
        public void onRequestToJoinReceived(String groupId, String groupName, String applicant, String reason) {
            //用户申请加入群
        }

        @Override
        public void onRequestToJoinAccepted(String groupId, String groupName, String accepter) {
            //加群申请被同意
        }

        @Override
        public void onRequestToJoinDeclined(String groupId, String groupName, String decliner, String reason) {
            //加群申请被拒绝
        }

        @Override
        public void onInvitationAccepted(String groupId, String invitee, String reason) {
            //群组邀请被同意
        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee, String reason) {
            //群组邀请被拒绝
        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            //群成员退出通知
        }

        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            //群解散通知
        }

        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            //接收邀请时自动加入到群组的通知
        }

        @Override
        public void onMuteListAdded(String groupId, List<String> mutes, long muteExpire) {
            //成员禁言的通知
        }

        @Override
        public void onMuteListRemoved(String groupId, List<String> mutes) {
            //成员从禁言列表里移除通知
        }

        @Override
        public void onAdminAdded(String groupId, String administrator) {
            //增加管理员的通知
        }

        @Override
        public void onAdminRemoved(String groupId, String administrator) {
            //增加管理员的通知
        }

        @Override
        public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
            //群所有者变动通知
        }
    };

    //实现ConnectionListener接口
    EMConnectionListener emConnectionListener=new EMConnectionListener() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int errorCode) {
            if (errorCode == EMError.USER_REMOVED) {
                // 显示帐号已经被移除
            } else if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                // 显示帐号在其他设备登录
            } else {
                if (NetUtils.hasNetwork(EMGroupService.this)){
                    //连接不到聊天服务器
                } else{
                    //当前网络不可用，请检查网络设置
                }

            }
        }
    };

}
