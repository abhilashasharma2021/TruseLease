package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingHistoryModel {

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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("sellerID")
        @Expose
        private String sellerID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("pay_through")
        @Expose
        private String payThrough;
        @SerializedName("payment_time")
        @Expose
        private String paymentTime;
        @SerializedName("booking_from")
        @Expose
        private String bookingFrom;
        @SerializedName("booking_upto")
        @Expose
        private String bookingUpto;
        @SerializedName("product_days")
        @Expose
        private String productDays;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("commition")
        @Expose
        private String commition;
        @SerializedName("delevery_charges")
        @Expose
        private String deleveryCharges;
        @SerializedName("total_price")
        @Expose
        private String totalPrice;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("full_address")
        @Expose
        private String fullAddress;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("cityID")
        @Expose
        private String cityID;
        @SerializedName("rent_status")
        @Expose
        private String rentStatus;
        @SerializedName("withdraw_status")
        @Expose
        private String withdrawStatus;
        @SerializedName("product_detail")
        @Expose
        private ProductDetail productDetail;

        @SerializedName("seller_detail")
        @Expose
        private SellerDetail sellerDetail;


        @SerializedName("bookingID")
        @Expose
        private String bookingID;

        public String getId() {
            return id;
        }


        public String getUserID() {
            return userID;
        }


        public String getSellerID() {
            return sellerID;
        }


        public String getProductID() {
            return productID;
        }


        public String getPayThrough() {
            return payThrough;
        }


        public String getPaymentTime() {
            return paymentTime;
        }


        public String getBookingFrom() {
            return bookingFrom;
        }


        public String getBookingUpto() {
            return bookingUpto;
        }


        public String getProductDays() {
            return productDays;
        }


        public String getPrice() {
            return price;
        }


        public String getCommition() {
            return commition;
        }


        public String getDeleveryCharges() {
            return deleveryCharges;
        }


        public String getTotalPrice() {
            return totalPrice;
        }


        public String getName() {
            return name;
        }


        public String getEmail() {
            return email;
        }


        public String getMobile() {
            return mobile;
        }


        public String getFullAddress() {
            return fullAddress;
        }


        public String getLat() {
            return lat;
        }


        public String getLng() {
            return lng;
        }


        public String getCityID() {
            return cityID;
        }


        public String getRentStatus() {
            return rentStatus;
        }


        public String getWithdrawStatus() {
            return withdrawStatus;
        }


        public ProductDetail getProductDetail() {
            return productDetail;
        }


        public SellerDetail getSellerDetail() {
            return sellerDetail;
        }


        public String getBookingID() {
            return bookingID;
        }

        public class ProductDetail {

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
            @SerializedName("tags")
            @Expose
            private String tags;
            @SerializedName("currency_code_id")
            @Expose
            private String currencyCodeId;
            @SerializedName("currency")
            @Expose
            private String currency;
            @SerializedName("delivery_type")
            @Expose
            private String deliveryType;
            @SerializedName("pickup_add")
            @Expose
            private String pickupAdd;
            @SerializedName("pickup_lat")
            @Expose
            private String pickupLat;
            @SerializedName("pickup_lng")
            @Expose
            private String pickupLng;
            @SerializedName("availability_date")
            @Expose
            private String availabilityDate;
            @SerializedName("availability_time")
            @Expose
            private String availabilityTime;
            @SerializedName("delivery_charge")
            @Expose
            private String deliveryCharge;
            @SerializedName("delete_status")
            @Expose
            private String deleteStatus;
            @SerializedName("rent_status")
            @Expose
            private String rentStatus;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("product_images")
            @Expose
            private String productImages;

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

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getCurrencyCodeId() {
                return currencyCodeId;
            }

            public void setCurrencyCodeId(String currencyCodeId) {
                this.currencyCodeId = currencyCodeId;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(String deliveryType) {
                this.deliveryType = deliveryType;
            }

            public String getPickupAdd() {
                return pickupAdd;
            }

            public void setPickupAdd(String pickupAdd) {
                this.pickupAdd = pickupAdd;
            }

            public String getPickupLat() {
                return pickupLat;
            }

            public void setPickupLat(String pickupLat) {
                this.pickupLat = pickupLat;
            }

            public String getPickupLng() {
                return pickupLng;
            }

            public void setPickupLng(String pickupLng) {
                this.pickupLng = pickupLng;
            }

            public String getAvailabilityDate() {
                return availabilityDate;
            }

            public void setAvailabilityDate(String availabilityDate) {
                this.availabilityDate = availabilityDate;
            }

            public String getAvailabilityTime() {
                return availabilityTime;
            }

            public void setAvailabilityTime(String availabilityTime) {
                this.availabilityTime = availabilityTime;
            }

            public String getDeliveryCharge() {
                return deliveryCharge;
            }

            public void setDeliveryCharge(String deliveryCharge) {
                this.deliveryCharge = deliveryCharge;
            }

            public String getDeleteStatus() {
                return deleteStatus;
            }

            public void setDeleteStatus(String deleteStatus) {
                this.deleteStatus = deleteStatus;
            }

            public String getRentStatus() {
                return rentStatus;
            }

            public void setRentStatus(String rentStatus) {
                this.rentStatus = rentStatus;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getProductImages() {
                return productImages;
            }

            public void setProductImages(String productImages) {
                this.productImages = productImages;
            }

        }


        public class SellerDetail {

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
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("DOB")
            @Expose
            private String dOB;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("lat")
            @Expose
            private String lat;
            @SerializedName("lng")
            @Expose
            private String lng;
            @SerializedName("brief_intro")
            @Expose
            private String briefIntro;
            @SerializedName("profile_image")
            @Expose
            private String profileImage;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("strtotime")
            @Expose
            private String strtotime;
            @SerializedName("refferal_code")
            @Expose
            private String refferalCode;
            @SerializedName("reffers_code")
            @Expose
            private String reffersCode;
            @SerializedName("regID")
            @Expose
            private String regID;
            @SerializedName("authID")
            @Expose
            private String authID;
            @SerializedName("auth_provider")
            @Expose
            private String authProvider;
            @SerializedName("verification_documents")
            @Expose
            private String verificationDocuments;
            @SerializedName("varification_document_type")
            @Expose
            private String varificationDocumentType;
            @SerializedName("D_license_front")
            @Expose
            private String dLicenseFront;
            @SerializedName("D_license_back")
            @Expose
            private String dLicenseBack;
            @SerializedName("paspot")
            @Expose
            private String paspot;
            @SerializedName("adhar_card")
            @Expose
            private String adharCard;
            @SerializedName("profile_status")
            @Expose
            private String profileStatus;
            @SerializedName("wallet")
            @Expose
            private String wallet;

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

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
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

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getBriefIntro() {
                return briefIntro;
            }

            public void setBriefIntro(String briefIntro) {
                this.briefIntro = briefIntro;
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

            public String getRefferalCode() {
                return refferalCode;
            }

            public void setRefferalCode(String refferalCode) {
                this.refferalCode = refferalCode;
            }

            public String getReffersCode() {
                return reffersCode;
            }

            public void setReffersCode(String reffersCode) {
                this.reffersCode = reffersCode;
            }

            public String getRegID() {
                return regID;
            }

            public void setRegID(String regID) {
                this.regID = regID;
            }

            public String getAuthID() {
                return authID;
            }

            public void setAuthID(String authID) {
                this.authID = authID;
            }

            public String getAuthProvider() {
                return authProvider;
            }

            public void setAuthProvider(String authProvider) {
                this.authProvider = authProvider;
            }

            public String getVerificationDocuments() {
                return verificationDocuments;
            }

            public void setVerificationDocuments(String verificationDocuments) {
                this.verificationDocuments = verificationDocuments;
            }

            public String getVarificationDocumentType() {
                return varificationDocumentType;
            }

            public void setVarificationDocumentType(String varificationDocumentType) {
                this.varificationDocumentType = varificationDocumentType;
            }

            public String getDLicenseFront() {
                return dLicenseFront;
            }

            public void setDLicenseFront(String dLicenseFront) {
                this.dLicenseFront = dLicenseFront;
            }

            public String getDLicenseBack() {
                return dLicenseBack;
            }

            public void setDLicenseBack(String dLicenseBack) {
                this.dLicenseBack = dLicenseBack;
            }

            public String getPaspot() {
                return paspot;
            }

            public void setPaspot(String paspot) {
                this.paspot = paspot;
            }

            public String getAdharCard() {
                return adharCard;
            }

            public void setAdharCard(String adharCard) {
                this.adharCard = adharCard;
            }

            public String getProfileStatus() {
                return profileStatus;
            }

            public void setProfileStatus(String profileStatus) {
                this.profileStatus = profileStatus;
            }

            public String getWallet() {
                return wallet;
            }

            public void setWallet(String wallet) {
                this.wallet = wallet;
            }

        }

    }

}