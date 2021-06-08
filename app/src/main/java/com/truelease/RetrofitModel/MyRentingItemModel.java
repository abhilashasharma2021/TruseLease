package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyRentingItemModel {

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

        @SerializedName("product_images")
        @Expose
        private ProductImages productImages;


        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;

        @SerializedName("bookingID")
        @Expose
        private String bookingID;


        @SerializedName("booking_from")
        @Expose
        private String booking_from;



        @SerializedName("booking_upto")
        @Expose
        private String booking_upto;



        public String getBookingID() {
            return bookingID;
        }

        public String getProductID() {
            return productID;
        }


        public String getUserID() {
            return userID;
        }


        public String getCategoryID() {
            return categoryID;
        }


        public String getSubCategoryID() {
            return subCategoryID;
        }

        public String getCityID() {
            return cityID;
        }


        public String getProduct() {
            return product;
        }


        public String getRentPerMonth() {
            return rentPerMonth;
        }


        public String getPriceType() {
            return priceType;
        }


        public String getDeliverIn() {
            return deliverIn;
        }


        public String getTenure() {
            return tenure;
        }


        public String getDescription() {
            return description;
        }


        public String getAddressOfProduct() {
            return addressOfProduct;
        }


        public String getLat() {
            return lat;
        }


        public String getLong() {
            return _long;
        }


        public String getPower() {
            return power;
        }


        public String getHeight() {
            return height;
        }


        public String getLength() {
            return length;
        }


        public String getWidth() {
            return width;
        }


        public String getWeight() {
            return weight;
        }


        public String getColor() {
            return color;
        }


        public String getMinPeriod() {
            return minPeriod;
        }


        public String getMaxPeriod() {
            return maxPeriod;
        }


        public String getConditionID() {
            return conditionID;
        }


        public String getBrandID() {
            return brandID;
        }


        public String getDeposit() {
            return deposit;
        }


        public String getMarketPrice() {
            return marketPrice;
        }

        public String getFavorite() {
            return favorite;
        }


        public String getStrtotime() {
            return strtotime;
        }


        public String getTags() {
            return tags;
        }


        public String getCurrencyCodeId() {
            return currencyCodeId;
        }


        public String getCurrency() {
            return currency;
        }


        public String getDeliveryType() {
            return deliveryType;
        }

        public String getPickupAdd() {
            return pickupAdd;
        }


        public String getPickupLat() {
            return pickupLat;
        }


        public String getPickupLng() {
            return pickupLng;
        }


        public String getAvailabilityDate() {
            return availabilityDate;
        }


        public String getAvailabilityTime() {
            return availabilityTime;
        }


        public String getDeliveryCharge() {
            return deliveryCharge;
        }


        public String getDeleteStatus() {
            return deleteStatus;
        }


        public String getRentStatus() {
            return rentStatus;
        }


        public ProductImages getProductImages() {
            return productImages;
        }


        public UserDetail getUserDetail() {
            return userDetail;
        }


        public String getBooking_from() {
            return booking_from;
        }

        public String getBooking_upto() {
            return booking_upto;
        }

        public class ProductImages {

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

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getImage() {
                return image;
            }

            public String getProductID() {
                return productID;
            }

        }


        public class UserDetail {

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

            public String getUserID() {
                return userID;
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


            public String getPassword() {
                return password;
            }


            public String getDOB() {
                return dOB;
            }


            public String getAddress() {
                return address;
            }


            public String getLat() {
                return lat;
            }


            public String getLng() {
                return lng;
            }


            public String getBriefIntro() {
                return briefIntro;
            }


            public String getProfileImage() {
                return profileImage;
            }


            public String getStatus() {
                return status;
            }


            public String getStrtotime() {
                return strtotime;
            }


            public String getRefferalCode() {
                return refferalCode;
            }


            public String getReffersCode() {
                return reffersCode;
            }


            public String getRegID() {
                return regID;
            }


            public String getAuthID() {
                return authID;
            }


            public String getAuthProvider() {
                return authProvider;
            }


            public String getVerificationDocuments() {
                return verificationDocuments;
            }


            public String getVarificationDocumentType() {
                return varificationDocumentType;
            }


            public String getDLicenseFront() {
                return dLicenseFront;
            }


            public String getDLicenseBack() {
                return dLicenseBack;
            }


            public String getPaspot() {
                return paspot;
            }


            public String getAdharCard() {
                return adharCard;
            }


            public String getProfileStatus() {
                return profileStatus;
            }


        }

    }

}
