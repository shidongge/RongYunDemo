package us.mifeng.rongyundemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by shido on 2017/5/30.
 */

public class ConversationActivity extends FragmentActivity {
    private String token1 = "p9aCUoWYGiGLoKm8JlO+ngcib5jVOjHULGyHSxsDPZqR8YMQ7bCrpdF6V89hawOM0PgEX+khxEdCnfmy3QUdDA==";//账号111111
    private String token2 = "r21J4kyuBLLfEnAC81U5nNorMIHXzoxzb3OVMDyNFVIz5HW0EIqHT5+OEELd9DPqUFDB8nttr3MXO/GPAWiDjQ==";//账号222222

    private TextView mTitle;
    private RelativeLayout mBack;

    private String mTargetId;



    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        Intent intent = getIntent();

        setActionBar();

        getIntentDate(intent);

        isReconnect(intent);
    }
    private void isReconnect(Intent intent){
        //程序切到后台，收到消息后点击进入,会执行这里
        if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
            reconnect(token1);
        } else {
            enterFragment(mConversationType, mTargetId);
        }
    }
    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {

        mTargetId = intent.getData().getQueryParameter("targetId");
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        enterFragment(mConversationType, mTargetId);
        setActionBarTitle(mTargetId);
    }

    /**
     * 设置 actionbar title
     */
    private void setActionBarTitle(String targetid) {
        mTitle.setText(targetid);
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }


    /**
     * 设置 actionbar 事件
     */
    private void setActionBar() {

        mTitle = (TextView) findViewById(R.id.txt1);
        mBack = (RelativeLayout) findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {

        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {

                    enterFragment(mConversationType, mTargetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }


}
