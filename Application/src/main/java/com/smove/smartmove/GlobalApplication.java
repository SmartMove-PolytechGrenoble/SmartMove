package com.smove.smartmove;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

/**
 * Created by clem on 02/02/18.
 * This class extends Application, and therefore all variables set up here are accessible globally
 */

public class GlobalApplication extends Application {

    private ArrayList<BluetoothDevice> bluetoothDevices;

    public void setBluetoothDevices(ArrayList<BluetoothDevice> bluetoothDevices){
        this.bluetoothDevices = bluetoothDevices;
    }

    public ArrayList<BluetoothDevice> getBluetoothDevices() {
        return this.bluetoothDevices;
    }
}
