package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionData {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<TransactionData.Datum> data = null;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransactionData.Datum> getData() {
        return data;
    }

    public void setData(List<TransactionData.Datum> data) {
        this.data = data;
    }


    public static class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("cradit_amount")
        @Expose
        private String craditAmount;
        @SerializedName("debit_amount")
        @Expose
        private String debitAmount;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("pay_through")
        @Expose
        private String payThrough;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getCraditAmount() {
            return craditAmount;
        }

        public void setCraditAmount(String craditAmount) {
            this.craditAmount = craditAmount;
        }

        public String getDebitAmount() {
            return debitAmount;
        }

        public void setDebitAmount(String debitAmount) {
            this.debitAmount = debitAmount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPayThrough() {
            return payThrough;
        }

        public void setPayThrough(String payThrough) {
            this.payThrough = payThrough;
        }

    }

}