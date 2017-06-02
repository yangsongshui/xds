package com.pgt.xds.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.pgt.xds.R;


/**
 * 耗时请求对话框
 * Created by zheng on 2017/3/29.
 */
public class RequestDialog extends Dialog{

    private static RequestDialog requestDialog;
    private static TextView dialog_message;
    private static String myMessage;

    public RequestDialog(Context context, int themeResId) {
        super(context, R.style.requestDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_dialog_layout);
        initView();
        setCanceledOnTouchOutside(false);
    }
    private void initView(){
        dialog_message = (TextView) findViewById(R.id.request_dialog_message);
        if (!TextUtils.isEmpty(myMessage)){
            dialog_message.setText(myMessage);
        }
    }
    /**
     * 显示对话框
     * @param context
     */
    public static void show(Context context){
        if (requestDialog == null) {
            requestDialog = new RequestDialog(context, 0);
        }
        requestDialog.show();
    }
    /**
     * 显示信息对话框
     * @param context
     */
    public static void show(Context context,String message){
        myMessage = message;
        if (requestDialog == null) {
            requestDialog = new RequestDialog(context, 0);
        }
        requestDialog.show();
    }

    /**
     * 关闭对话框
     * @param context
     */
    public static void dismiss(Context context){
        if (requestDialog != null){
            requestDialog.dismiss();
            myMessage = null;
            requestDialog = null;
        }
    }
}
