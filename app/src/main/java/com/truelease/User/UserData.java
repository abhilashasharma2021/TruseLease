package com.truelease.User;

import android.content.Context;

import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;

public class UserData {

    public static String getUserID(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_ID);
    }


    public static String getRegID(Context context) {
        return SharedHelper.getKey(context, AppConstats.REG_ID);
    }

    public static String getUserEmail(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_EMAIL);
    }


    public static String getUserMobileNumber(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_MOBILE);
    }


    public static String getUserName(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_NAME);
    }


    public static String getUserAddress(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_ADDRESS);
    }


    public static String getUserIntro(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_INTRO);
    }

    public static String getUserImage(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_IMAGE);
    }

    public static String getProvider(Context context) {
        return SharedHelper.getKey(context, AppConstats.PROVIDER);
    }


    public static String getUserPath(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_PATH);
    }


    public static String getVerifyDoc(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_DRIVING_LICENSE_FRONT);
    }


    public static String getDLicBack(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_DRIVING_LICENSE_BACK);
    }


    public static String getPassport(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_PASSPORT);
    }


    public static String getAadharCard(Context context) {
        return SharedHelper.getKey(context, AppConstats.USER_AADHAR);
    }


    public static String getRefferalCOde(Context context) {
        return SharedHelper.getKey(context, AppConstats.REFERAL_CODE);
    }


    public static String getVerificationStatus(Context context) {
        return SharedHelper.getKey(context, AppConstats.VERIFICATION);
    }


    public static String getCategoryID(Context context) {
        return SharedHelper.getKey(context, AppConstats.CATEGORY_ID);
    }


    public static String getSubCategoryID(Context context) {
        return SharedHelper.getKey(context, AppConstats.SUB_CATEGORY_ID);
    }

    public static String getProductID(Context context) {
        return SharedHelper.getKey(context, AppConstats.PRODUCT_ID);
    }


    public static String getAddProductTitle(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_TITLE);
    }


    public static String getAddProductDesc(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_DESCRIPTION);
    }


    public static String getAddProductAddress(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_ADDRESS);
    }


    public static String getAddProductCategoryId(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_CATEGORY_ID);
    }


    public static String getAddProductSubCatId(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_SUB_CATEGORY_ID);
    }


    public static String getAddProductCondition(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_CONDITION);
    }


    public static String getAddProductTag(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_TAG);
    }


    public static String getAddProductPrice(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_PRICE);
    }


    public static String getAddProductDeposit(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_DEPOSIT);
    }


    public static String getAddProductMarketPrice(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_MARKET_PRICE);
    }

    public static String getAddProductMinPeriod(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_MIN_PERIOD);
    }

    public static String getAddProductMaxPeriod(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_MAX_PERIOD);
    }

    public static String getAddProductBrand(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_BRAND_ID);
    }

    public static String getAddProductPower(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_POWER);
    }

    public static String getAddProductWeight(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_WEIGHT);
    }

    public static String getAddProductColor(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_COLOR);
    }

    public static String getAddProductHeight(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_HEIGHT);
    }

    public static String getAddProductWidth(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_WIDTH);
    }

    public static String getAddProductLength(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_LENGTH);
    }

    public static String getAddProductLatitude(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_LATITUDE);
    }

    public static String getAddProductLongitude(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_PRODUCT_LONGITUDE);
    }

    public static String getAddDate(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_DATE);
    }


    public static String getAddTime(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_TIME);
    }

    public static String getAddDeliveryPrice(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_DELIVERY_PRICE);
    }

    public static String getAddDeliveryType(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_DELIVERY_TYPE);
    }

    public static String getAddCurrencyCode(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_CURRENCY_CODE);
    }


    public static String getAddDurationOfProduct(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_DURATION_OF_PRODUCT);
    }


    //map

    public static String getIneed(Context context) {
        return SharedHelper.getKey(context, AppConstats.I_NEED);
    }

    public static String getIneedLat(Context context) {
        return SharedHelper.getKey(context, AppConstats.SEARCH_LOCATION_LAT);
    }

    public static String getIneedLng(Context context) {
        return SharedHelper.getKey(context, AppConstats.SEARCH_LOCATION_LNG);
    }


    public static String getCityID(Context context) {
        return SharedHelper.getKey(context, AppConstats.CITY_ID);
    }


    public static String getCityName(Context context) {
        return SharedHelper.getKey(context, AppConstats.CITY_NAME);
    }


    public static String getTotalAmount(Context context) {
        return SharedHelper.getKey(context, AppConstats.TOTAL_AMOUNT);
    }

    public static String getLocation(Context context) {
        return SharedHelper.getKey(context, AppConstats.ADD_LOCATION);
    }


    //productDetail

    public static String getReceiverID(Context context) {
        return SharedHelper.getKey(context, AppConstats.OWNER_ID);
    }



    public static String getMyOrderOwnerID(Context context) {
        return SharedHelper.getKey(context, AppConstats.CHAT_OWNER_ID_MY_ORDER);
    }


    public static String getChatMsgSenderID(Context context) {
        return SharedHelper.getKey(context, AppConstats.CHAT_MSG_SENDER_ID);
    }


    public static String getChatClickedOn(Context context) {
        return SharedHelper.getKey(context, AppConstats.CHAT_CLICKED_ON);
    }


    public static String getMapClicked(Context context) {
        return SharedHelper.getKey(context, AppConstats.MAP_CLICKED);
    }


    public static String getMapCity(Context context) {
        return SharedHelper.getKey(context, AppConstats.MAP_CITY);
    }

    public static String getStartDate(Context context) {
        return SharedHelper.getKey(context, AppConstats.START_DATE);
    }

    public static String getEndDate(Context context) {
        return SharedHelper.getKey(context, AppConstats.END_DATE);
    }

    public static String getPrice(Context context) {
        return SharedHelper.getKey(context, AppConstats.PRICE);
    }


    public static String getTotalPrice(Context context) {
        return SharedHelper.getKey(context, AppConstats.TOTAL_PRICE);
    }

    public static String getCartId(Context context) {
        return SharedHelper.getKey(context, AppConstats.CART_ID);
    }


}
