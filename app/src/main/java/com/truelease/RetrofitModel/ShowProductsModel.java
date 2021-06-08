package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowProductsModel {

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

        @SerializedName("availability_date")
        @Expose
        private String availability_date;

        @SerializedName("availability_time")
        @Expose
        private String availability_time;

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

        @SerializedName("price_type")
        @Expose
        private String price_type;


        @SerializedName("rent_status")
        @Expose
        private String rent_status;


        @SerializedName("condition")
        @Expose
        private String condition;
        @SerializedName("brandID")
        @Expose
        private String brandID;
        @SerializedName("deposit")
        @Expose
        private String deposit;
        @SerializedName("market_price")
        @Expose
        private String marketPrice;
        @SerializedName("strtotime")
        @Expose
        private String strtotime;
        @SerializedName("user_info")
        @Expose
        private UserInfo userInfo;

        @SerializedName("cart_status")
        @Expose
        private String cart_status;

        @SerializedName("favorite_status")
        @Expose
        private String favorite_status;


        @SerializedName("delivery_type")
        @Expose
        private String delivery_type;

        @SerializedName("image")
        @Expose
        private List<Image> image = null;
        @SerializedName("city_detail")
        @Expose
        private List<CityDetail> cityDetail = null;

        @SerializedName("category_detail")
        @Expose
        private List<CategoryDetail> categoryDetail = null;
        @SerializedName("sub_category_detail")
        @Expose
        private List<SubCategoryDetail> subCategoryDetail = null;
        @SerializedName("brand_detail")
        @Expose
        private List<BrandDetail> brandDetail = null;
        @SerializedName("condition_detail")
        @Expose
        private List<ConditionDetail> conditionDetail = null;

        @SerializedName("currency_detail")
        @Expose
        private List<CurrencyDetail> currencyDetail = null;

        public String getAvailability_date() {
            return availability_date;
        }

        public String getAvailability_time() {
            return availability_time;
        }

        public List<CurrencyDetail> getCurrencyDetail() {
            return currencyDetail;
        }

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

        public List<CategoryDetail> getCategoryDetail() {
            return categoryDetail;
        }

        public List<SubCategoryDetail> getSubCategoryDetail() {
            return subCategoryDetail;
        }

        public List<BrandDetail> getBrandDetail() {
            return brandDetail;
        }

        public List<ConditionDetail> getConditionDetail() {
            return conditionDetail;
        }

        public String getPrice_type() {
            return price_type;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
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

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
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

        public String getStrtotime() {
            return strtotime;
        }

        public void setStrtotime(String strtotime) {
            this.strtotime = strtotime;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
        }

        public String getCart_status() {
            return cart_status;
        }

        public void setCart_status(String cart_status) {
            this.cart_status = cart_status;
        }

        public String getFavorite_status() {
            return favorite_status;
        }

        public String getRent_status() {
            return rent_status;
        }

        public List<CityDetail> getCityDetail() {
            return cityDetail;
        }

        public class UserInfo {

            @SerializedName("userID")
            @Expose
            private String userID;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("mobile")
            @Expose
            private String mobile;
            @SerializedName("DOB")
            @Expose
            private String dOB;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("profile_image")
            @Expose
            private String profileImage;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("strtotime")
            @Expose
            private String strtotime;
            @SerializedName("path")
            @Expose
            private String path;


            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getDOB() {
                return dOB;
            }

            public void setDOB(String dOB) {
                this.dOB = dOB;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStrtotime() {
                return strtotime;
            }

            public void setStrtotime(String strtotime) {
                this.strtotime = strtotime;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

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


        public class BrandDetail {

            @SerializedName("brandID")
            @Expose
            private String brandID;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("path")
            @Expose
            private String path;

            public String getBrandID() {
                return brandID;
            }

            public void setBrandID(String brandID) {
                this.brandID = brandID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

        }


        public class CategoryDetail {

            @SerializedName("categoryID")
            @Expose
            private String categoryID;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("path")
            @Expose
            private String path;

            public String getCategoryID() {
                return categoryID;
            }

            public String getName() {
                return name;
            }


            public String getImage() {
                return image;
            }

            public String getPath() {
                return path;
            }


        }


        public class SubCategoryDetail {

            @SerializedName("sub_categoryID")
            @Expose
            private String subCategoryID;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("categoryID")
            @Expose
            private String categoryID;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("path")
            @Expose
            private String path;

            public String getSubCategoryID() {
                return subCategoryID;
            }


            public String getName() {
                return name;
            }


            public String getCategoryID() {
                return categoryID;
            }


            public String getImage() {
                return image;
            }


            public String getPath() {
                return path;
            }


        }


        public class ConditionDetail {

            @SerializedName("conditionID")
            @Expose
            private String conditionID;
            @SerializedName("condition")
            @Expose
            private String condition;
            @SerializedName("productID")
            @Expose
            private String productID;
            @SerializedName("path")
            @Expose
            private String path;

            public String getConditionID() {
                return conditionID;
            }


            public String getCondition() {
                return condition;
            }


            public String getProductID() {
                return productID;
            }


            public String getPath() {
                return path;
            }


        }


        public class CurrencyDetail {

            @SerializedName("currency_code_id")
            @Expose
            private String currencyCodeId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("code")
            @Expose
            private String code;
            @SerializedName("symbol")
            @Expose
            private String symbol;
            @SerializedName("path")
            @Expose
            private String path;

            public String getCurrencyCodeId() {
                return currencyCodeId;
            }


            public String getName() {
                return name;
            }


            public String getCode() {
                return code;
            }


            public String getSymbol() {
                return symbol;
            }


            public String getPath() {
                return path;
            }


        }


        public class CityDetail {

            @SerializedName("cityID")
            @Expose
            private String cityID;
            @SerializedName("city")
            @Expose
            private String city;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("path")
            @Expose
            private String path;

            public String getCityID() {
                return cityID;
            }


            public String getCity() {
                return city;
            }


            public String getImage() {
                return image;
            }


            public String getPath() {
                return path;
            }


        }

    }

}
