package com.wsq.ebottle.fragment;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wsq.ebottle.R;
import com.wsq.ebottle.adapter.BlueToothListAdapter;
import com.wsq.ebottle.utils.ToastUtils;

import java.util.Arrays;

public class DeviceManagerFragment extends Fragment {
    private static final String TAG = "DeviceManagerFragment";

    private RadioGroup radioGroup;
    private BluetoothAdapter bluetoothAdapter = null;
    //30秒之后停止扫描
    private static final long SCAN_PERIOD = 10 * 1000;
    private boolean scanning;
    private Handler handler = new Handler();
    private BlueToothListAdapter adapter;
    private ListView listDevice;
    private BluetoothDevice device = null;
    private DeviceListener deviceListener = null;

    OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {
            switch (arg1) {
                case R.id.radio_nurse_bottle:
                    nurseBottleDevice();
                    break;
                case R.id.radio_water_bottle:
                    waterBottleDevice();
                    break;
            }
        }
    };


    OnItemClickListener itemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            device = adapter.getDevice(arg2);
            Log.i("wsq", "device=" + device.getName());
            deviceListener.onDevice(device);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_manager, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView() {
        radioGroup = (RadioGroup) getView().findViewById(R.id.radio_group_bottle);
        radioGroup.setOnCheckedChangeListener(changeListener);

        listDevice = (ListView) getView().findViewById(R.id.list_device);
        adapter = new BlueToothListAdapter(getActivity());
        listDevice.setAdapter(adapter);
        listDevice.setOnItemClickListener(itemClickListener);

        nurseBottleDevice();
    }


    //搜索奶瓶设备
    protected void nurseBottleDevice() {
        SearchBluetooth();
        //开始扫描BLE设备
        scanLeDevice(true);
    }

    //搜索水瓶
    protected void waterBottleDevice() {
        SearchBluetooth();
        //开始扫描BLE设备
        scanLeDevice(true);
    }

    @SuppressLint("NewApi")
    private void scanLeDevice(final boolean enable) {
        Log.i("wsq", "device--scan");
        if (enable) {
            handler.postDelayed(new Runnable() {

                @SuppressLint("NewApi")
                @Override
                public void run() {
                    bluetoothAdapter.stopLeScan(scanCallback);
                    scanning = false;
                }
            }, SCAN_PERIOD);
            bluetoothAdapter.startLeScan(scanCallback);
            scanning = true;

        } else {
            bluetoothAdapter.stopLeScan(scanCallback);
            scanning = false;
        }
    }


    @SuppressLint("NewApi")
    private BluetoothAdapter.LeScanCallback scanCallback = new LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord) {

            //eBottle-B12
            if (device.getName() != null) {
                final String name = device.getName();
                if (name != null || name.length() > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (name.startsWith("eBottle")) {
                                Log.i(TAG, "run:  name=" + name);
                                Log.i(TAG, "run:  name=" + name);
                                Log.i(TAG, "run: address=" + device.getAddress());
                                Log.i(TAG, "run: scanRecord=" + Arrays.toString(scanRecord));
                                adapter.addDevice(device);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }
    };


    //搜索蓝牙
    @SuppressLint("NewApi")
    private void SearchBluetooth() {
        // 判断是否支持BLE
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.show("该手机不支持BLE");
            return;
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) {
            ToastUtils.show("你的手机不支持蓝牙", Toast.LENGTH_SHORT);
            //开启蓝牙
            bluetoothAdapter.enable();
            return;
        }


    }


    public interface DeviceListener {
        void onDevice(BluetoothDevice device);
    }

    public void setOnDeviceLister(DeviceListener deviceListener) {
        this.deviceListener = deviceListener;
        Log.i("wsq", "setOnDevice");

    }

}
