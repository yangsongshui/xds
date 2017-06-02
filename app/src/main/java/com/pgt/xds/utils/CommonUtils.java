package com.pgt.xds.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.pgt.xds.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公共工具类
 * Created by zheng on 2017/3/29.
 */
public class CommonUtils {
    private static List<Activity> list = new ArrayList<Activity>();

    public static void remove(Activity activity) {
        list.remove(activity);
    }

    public static void add(Activity activity) {
        list.add(activity);
    }

    public static void finishProgram() {
        for (int i = 0; i < list.size(); i++) {
            Activity activity = list.get(i);
            activity.finish();
        }
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        boolean isNum = str.matches("[0-9]+");
        return isNum;
    }

    /**
     * 网络请求异常处理
     * @param context
     * @param throwable
     */
    public static void serviceError(Context context,Throwable throwable){
        String temp = throwable.getMessage();
        String errorMessage = temp;
        if(throwable instanceof SocketTimeoutException){//服务器响应超时
            errorMessage = context.getResources().getString(R.string.socket_timeout_error);
        }else if(throwable instanceof ConnectException){//网络连接异常，请检查网络
            errorMessage = context.getResources().getString(R.string.connect_error);
        }else if(throwable instanceof RuntimeException){//运行时错误
            errorMessage = context.getResources().getString(R.string.runtime_error);
        }else if(throwable instanceof UnknownHostException){//无法解析主机，请检查网络连接
            errorMessage = context.getResources().getString(R.string.unknown_host_please_check_network);
        }else if(throwable instanceof UnknownServiceException){//未知的服务器错误
            errorMessage = context.getResources().getString(R.string.unknown_error);
        }
        hintDialog(context,errorMessage);
    }
    public static void onFailure(Context context,int code,String TAG){
        if (code == 101 && context != null){
            Log.i(TAG, "onResponse:http code="+code+"-----token错误");//token错误
            //跳转登录界面重新登录
            //context.startActivity(new Intent(context, LoginActivity.class));
        }else if (code == 102 && context != null){
            Log.i(TAG, "onResponse:http code="+code+"-----token过期");//token过期
            //跳转登录界面重新登录
            //context.startActivity(new Intent(context, LoginActivity.class));
        }else if (code == 201 && context != null){//缺少参数
            Toast.makeText(context,context.getResources().getString(R.string.missing_parameter),Toast.LENGTH_LONG).show();
        }else if (code == 202 && context != null){//数据不存在
            Toast.makeText(context,context.getResources().getString(R.string.data_null),Toast.LENGTH_LONG).show();
        }else if (code == 203 && context != null){//数据已存在
            Toast.makeText(context,context.getResources().getString(R.string.data_already_existing),Toast.LENGTH_LONG).show();
        }else if (code == 500 && context != null){//服务器异常
            Toast.makeText(context,context.getResources().getString(R.string.service_error),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 检查当前网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetwork(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 无网络连接提示弹出对话框
     * @param context
     */
    public static void networkDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.network_hint));
        builder.setMessage(context.getResources().getString(R.string.network_message));
        builder.setPositiveButton(context.getResources().getString(R.string.countersign), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    /**
     * 无网络连接提示弹出对话框并且结束界面
     * @param activity
     */
    public static void networkDialog(final Activity activity, final boolean isFinish){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.network_hint));
        builder.setMessage(activity.getResources().getString(R.string.network_message));
        builder.setPositiveButton(activity.getResources().getString(R.string.countersign), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isFinish){
                    activity.finish();
                }
            }
        });
        builder.create().show();
    }
    /**
     * 提示对话框
     * @param message
     */
    public static void hintDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.network_hint));
        builder.setMessage(message);
        builder.setPositiveButton(context.getResources().getString(R.string.countersign), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    /**
     * Double类型保留两位小数
     * @param value
     * @return
     */
    public static String DoubleRetainTwo(double value){
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }
    /**图片按比例大小压缩方法**/
    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
    /**质量压缩**/
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }
    //将Bitmap保存到系统图库
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "bicycle");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        return file.getAbsolutePath();
    }
    /**MD5加密算法**/
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
