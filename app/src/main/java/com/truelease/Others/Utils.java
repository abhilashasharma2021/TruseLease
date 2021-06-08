package com.truelease.Others;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class Utils {


    public static String getAddress(Context context, double lat, double lng) {

        String myAddress = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);

            if (addressList != null) {

                myAddress = addressList.get(0).getAddressLine(0);

            } else {
                Toast.makeText(context, addressList.toString() + "null", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            Log.e("xsklks", "msg ::" + e.getMessage(), e);
        }

        return myAddress;
    }


    public static String getPincode(Context context, double lat, double lng) {

        String pincode = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);

            if (addressList != null) {

                if (addressList.get(0).getPostalCode().equals("") || addressList.get(0).getPostalCode() == null) {
                    pincode = "";
                }else {
                    pincode = addressList.get(0).getPostalCode();
                }

            } else {
                Toast.makeText(context, addressList.toString() + "null", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            Log.e("xsklks", "msg ::" + e.getMessage(), e);
        }


        return pincode;
    }



    public static String getState(Context context, double lat, double lng) {

        String state = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);

            if (addressList != null) {

                if (addressList.get(0).getAdminArea().equals("") || addressList.get(0).getAdminArea() == null) {
                    state = "";
                }else {
                    state = addressList.get(0).getAdminArea();
                }

            } else {
                Toast.makeText(context, addressList.toString() + "null", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            Log.e("xsklks", "msg ::" + e.getMessage(), e);
        }


        return state;
    }





    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

}
