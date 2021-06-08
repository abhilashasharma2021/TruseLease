package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

;

public class OffersModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("offerID")
        @Expose
        private String offerID;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("offer_end_date")
        @Expose
        private String offerEndDate;
        @SerializedName("offer_start_date")
        @Expose
        private String offerStartDate;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("path")
        @Expose
        private String path;

        public String getOfferID() {
            return offerID;
        }

        public void setOfferID(String offerID) {
            this.offerID = offerID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOfferEndDate() {

            return offerEndDate;
        }

        public void setOfferEndDate(String offerEndDate) {
            this.offerEndDate = offerEndDate;
        }

        public String getOfferStartDate() {
            return offerStartDate;
        }

        public void setOfferStartDate(String offerStartDate) {
            this.offerStartDate = offerStartDate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }

}