package com.pgt.xds.club;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.pgt.xds.R;
import com.pgt.xds.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SetTroopsActivity extends BaseActivity implements TextWatcher {
    private static final int RESULT = 2;
    @BindView(R.id.set_name_ed)
    EditText setNameEd;
    @BindView(R.id.troops_introduce_ed)
    EditText troopsIntroduceEd;
    @BindView(R.id.set_btn)
    TextView setBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_troops;
    }

    @Override
    protected void initView() {
        setBtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        setNameEd.addTextChangedListener(this);
        troopsIntroduceEd.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.set_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.set_btn:
                newTroops(setNameEd.getText().toString(), troopsIntroduceEd.getText().toString(), "加入我们吧");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String phone = setNameEd.getText().toString().trim();
        String password = troopsIntroduceEd.getText().toString().trim();
        if (phone.length() > 0 && password.length() > 0) {
            setBtn.setEnabled(true);
            setBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        } else {
            setBtn.setEnabled(false);
            setBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }

    /**
     * 创建群组
     *
     * @param groupName 群组名称
     * @param desc      群组简介
     *                  allMembers 群组初始成员，如果只有自己传空数组即可
     * @param reason    邀请成员加入的reason
     *                  option     群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupManager.EMGroupStyle}
     *                  option.inviteNeedConfirm表示邀请对方进群是否需要对方同意，默认是需要用户同意才能加群的。
     *                  option.extField创建群时可以为群组设定扩展字段，方便个性化订制。
     * @return 创建好的group
     * @throws HyphenateException
     */
    private void newTroops(String groupName, String desc, String reason) {

        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
        option.maxUsers = 200;
        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;

        try {
            EMClient.getInstance().groupManager().createGroup(groupName, desc, null, reason, option);
            setResult(RESULT);
            finish();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
}
