package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BottomSheetSearchProductModel {

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


    public static class Datum {

        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("categoryID")
        @Expose
        private String categoryID;
        @SerializedName("sub_categoryID")
        @Expose
        private String subCategoryID;
        @SerializedName("cityID")
        @Expose
        private String cityID;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("rent_per_month")
        @Expose
        private String rentPerMonth;
        @SerializedName("price_type")
        @Expose
        private String priceType;
        @SerializedName("deliver_in")
        @Expose
        private String deliverIn;
        @SerializedName("tenure")
        @Expose
        private String tenure;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("address_of_product")
        @Expose
        private String addressOfProduct;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("long")
        @Expose
        private String _long;
        @SerializedName("power")
        @Expose
        private String power;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("length")
        @Expose
        private String length;
        @SerializedName("width")
        @Expose
        private String width;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("min_period")
        @Expose
        private String minPeriod;
        @SerializedName("max_period")
        @Expose
        private String maxPeriod;
        @SerializedName("conditionID")
        @Expose
        private String conditionID;
        @SerializedName("brandID")
        @Expose
        private String brandID;
        @SerializedName("deposit")
        @Expose
        private String deposit;
        @SerializedName("market_price")
        @Expose
        private String marketPrice;
        @SerializedName("favorite")
        @Expose
        private String favorite;
        @SerializedName("strtotime")
        @Expose
        private String strtotime;
        @SerializedName("image")
        @Expose
        private List<Image> image = null;

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(String categoryID) {
            this.categoryID = categoryID;
        }

        public String getSubCategoryID() {
            return subCategoryID;
        }

        public void setSubCategoryID(String subCategoryID) {
            this.subCategoryID = subCategoryID;
        }

        public String getCityID() {
            return cityID;
        }

        public void setCityID(String cityID) {
            this.cityID = cityID;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getRentPerMonth() {
            return rentPerMonth;
        }

        public void setRentPerMonth(String rentPerMonth) {
            this.rentPerMonth = rentPerMonth;
        }

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

        public String getDeliverIn() {
            return deliverIn;
        }

        public void setDeliverIn(String deliverIn) {
            this.deliverIn = deliverIn;
        }

        public String getTenure() {
            return tenure;
        }

        public void setTenure(String tenure) {
            this.tenure = tenure;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddressOfProduct() {
            return addressOfProduct;
        }

        public void setAddressOfProduct(String addressOfProduct) {
            this.addressOfProduct = addressOfProduct;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLong() {
            return _long;
        }

        public void setLong(String _long) {
            this._long = _long;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getMinPeriod() {
            return minPeriod;
        }

        public void setMinPeriod(String minPeriod) {
            this.minPeriod = minPeriod;
        }

        public String getMaxPeriod() {
            return maxPeriod;
        }

        public void setMaxPeriod(String maxPeriod) {
            this.maxPeriod = maxPeriod;
        }

        public String getConditionID() {
            return conditionID;
        }

        public void setConditionID(String conditionID) {
            this.conditionID = conditionID;
        }

        public String getBrandID() {
            return brandID;
        }

        public void setBrandID(String brandID) {
            this.brandID = brandID;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getStrtotime() {
            return strtotime;
        }

        public void setStrtotime(String strtotime) {
            this.strtotime = strtotime;
        }

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
        }



        public class Image {

            @SerializedName("imageID")
            @Expose
            private String imageID;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("ProductID")
            @Expose
            private String productID;
            @SerializedName("path")
            @Expose
            private String path;

            public String getImageID() {
                return imageID;
            }

            public void setImageID(String imageID) {
                this.imageID = imageID;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getProductID() {
                return productID;
            }

            public void setProductID(String productID) {
                this.productID = productID;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

        }

    }

}
