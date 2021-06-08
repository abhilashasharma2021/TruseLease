package com.truelease.ApiData;

import com.truelease.RetrofitModel.AboutUsModel;
import com.truelease.RetrofitModel.AddCartModel;
import com.truelease.RetrofitModel.AddToFavoritesModel;
import com.truelease.RetrofitModel.AgreementModel;
import com.truelease.RetrofitModel.AwesomeItemModel;
import com.truelease.RetrofitModel.BookingHistoryModel;
import com.truelease.RetrofitModel.BookingModel;
import com.truelease.RetrofitModel.BottomSheetSearchProductModel;
import com.truelease.RetrofitModel.BrandModel;
import com.truelease.RetrofitModel.ChangePasswordModel;
import com.truelease.RetrofitModel.ChatWithAdminModel;
import com.truelease.RetrofitModel.CitiesModel;
import com.truelease.RetrofitModel.CollectionCategoryModel;
import com.truelease.RetrofitModel.ContactUsModel;
import com.truelease.RetrofitModel.CurrencyModel;
import com.truelease.RetrofitModel.DeleteBookingModel;
import com.truelease.RetrofitModel.DeleteChatsModel;
import com.truelease.RetrofitModel.DeleteFromCartModel;
import com.truelease.RetrofitModel.DeleteNotificationModel;
import com.truelease.RetrofitModel.DeletePostModel;
import com.truelease.RetrofitModel.EditProfileModel;
import com.truelease.RetrofitModel.FavoriteData;
import com.truelease.RetrofitModel.ForgotPasswordModel;
import com.truelease.RetrofitModel.LoginData;
import com.truelease.RetrofitModel.MyChatModel;
import com.truelease.RetrofitModel.MyPostModel;
import com.truelease.RetrofitModel.MyRentingItemModel;
import com.truelease.RetrofitModel.NeedHelpModel;
import com.truelease.RetrofitModel.NewItemsModel;
import com.truelease.RetrofitModel.NewNotificationModel;
import com.truelease.RetrofitModel.NotificationModel;
import com.truelease.RetrofitModel.OffersModel;
import com.truelease.RetrofitModel.ProductRemove;
import com.truelease.RetrofitModel.SendMessageModel;
import com.truelease.RetrofitModel.ShowAdminChatModel;
import com.truelease.RetrofitModel.ShowCartModel;
import com.truelease.RetrofitModel.ShowCategoriesData;
import com.truelease.RetrofitModel.ShowChatModel;
import com.truelease.RetrofitModel.ShowConditionsModel;
import com.truelease.RetrofitModel.ShowHelpModel;
import com.truelease.RetrofitModel.ShowNotiStatusModel;
import com.truelease.RetrofitModel.ShowProductsModel;
import com.truelease.RetrofitModel.ShowWalletAmountModel;
import com.truelease.RetrofitModel.SignupData;
import com.truelease.RetrofitModel.SocialLoginModel;
import com.truelease.RetrofitModel.StripeResponse;
import com.truelease.RetrofitModel.SubCategoriesModel;
import com.truelease.RetrofitModel.TermAndConditionModel;
import com.truelease.RetrofitModel.TransactionData;
import com.truelease.RetrofitModel.UpdateQuantityModel;
import com.truelease.RetrofitModel.VerificationStatusModel;
import com.truelease.RetrofitModel.WishListModel;
import com.truelease.RetrofitModel.WithdrawRequestModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonInterface {

    @FormUrlEncoded
    @POST(API.signUp)
    Call<SignupData> userRegistration(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.login)
    Call<LoginData> login(@FieldMap Map<String, String> params);

    @GET(API.show_category)
    Call<ShowCategoriesData> showCategories();

    @GET(API.show_city)
    Call<CitiesModel> showCities();

    @FormUrlEncoded
    @POST(API.showSubCategory)
    Call<SubCategoriesModel> showSubCategory(@FieldMap Map<String, String> params);

    @GET(API.showOffers)
    Call<OffersModel> showOffers();


    @GET(API.tc)
    Call<TermAndConditionModel> showTermAndCondition();

    @FormUrlEncoded
    @POST(API.showProduct)
    Call<ShowProductsModel> showProduct(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.show_product_detail)
    Call<ShowProductsModel> showProductDetail(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.needHelp)
    Call<NeedHelpModel> help(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.add_to_favorite)
    Call<FavoriteData> favorite(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.addToCart)
    Call<AddCartModel> addToCart(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.showCart)
    Call<ShowCartModel> showCart(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.deleteFromCart)
    Call<DeleteFromCartModel> deleteFromCart(@FieldMap Map<String, String> params);

    @GET(API.showCollection)
    Call<CollectionCategoryModel> collectionCategory();


    @FormUrlEncoded
    @POST(API.searchProduct)
    Call<BottomSheetSearchProductModel> searchProduct(@FieldMap Map<String, String> params);


    @GET(API.showCurrency)
    Call<CurrencyModel> showCurrencies();

    @FormUrlEncoded
    @POST(API.updateQuantity)
    Call<UpdateQuantityModel> updateQuantity(@FieldMap Map<String, String> params);


    @POST(API.updateProfile)
    Call<EditProfileModel> updateProf(@Body MultipartBody image);

    @FormUrlEncoded
    @POST(API.sendMessage)
    Call<SendMessageModel> sendMsg(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.showChat)
    Call<ShowChatModel> showChats(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.addToFavorite)
    Call<AddToFavoritesModel> addToFavorite(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.showsWishList)
    Call<WishListModel> showWishList(@Field("userID") String userID);


    @FormUrlEncoded
    @POST(API.removeFromWishList)
    Call<ProductRemove> removeFromWishList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.showProduct)
    Call<MyPostModel> showPosts(@Field("userID") String userID);


    @FormUrlEncoded
    @POST(API.deleteItem)
    Call<DeletePostModel> deleteMyPost(@Field("productID") String productID);

    @GET(API.showBrands)
    Call<BrandModel> showBrands();


    @FormUrlEncoded
    @POST(API.showMyChats)
    Call<MyChatModel> showMyChats(@Field("userID") String userID);

    @GET(API.showConditions)
    Call<ShowConditionsModel> showConditions();

    @FormUrlEncoded
    @POST(API.showAppChatStatus)
    Call<ShowNotiStatusModel> showNotiStatus(@Field("userID") String userID);

    @GET(API.showHelp)
    Call<ShowHelpModel> showHelp();

    @FormUrlEncoded
    @POST(API.socialLogin)
    Call<SocialLoginModel> socialLogin(@FieldMap Map<String, String> params);

    @GET(API.showAwesomeItem)
    Call<AwesomeItemModel> showAwesomeItems();

    @FormUrlEncoded
    @POST(API.forgotPassword)
    Call<ForgotPasswordModel> forgotPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.changePassword)
    Call<ChangePasswordModel> changePassword(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.support)
    Call<ChatWithAdminModel> support(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.showSupportChat)
    Call<ShowAdminChatModel> showSupportChat(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(API.verificationStatus)
    Call<VerificationStatusModel> showVerificationStatus(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(API.login)
    Observable<LoginData> slogin(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST(API.itemBooking)
    Call<BookingModel> booking(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST(API.myRentedItems)
    Call<MyRentingItemModel> showRentedItems(@Field("userID") String userID, @Field("type") String type);

    @GET(API.showNewItems)
    Call<NewItemsModel> showNewItem();

    @GET(API.showAgreement)
    Call<AgreementModel> showAgreement();


    @FormUrlEncoded
    @POST(API.myRentedItems)
    Call<MyRentingItemModel> changeItemStatus(@FieldMap Map<String, String> param);

    @GET(API.contactUs)
    Call<ContactUsModel> showAdminContact();


    @FormUrlEncoded
    @POST(API.showAmount)
    Call<ShowWalletAmountModel> showAmount(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(API.showNotification)
    Call<NotificationModel> showNotifications(@Field("userID") String userID);


    @FormUrlEncoded
    @POST(API.withdrawRequest)
    Call<WithdrawRequestModel> sendWithdrawRequest(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST(API.bookingHistory)
    Call<BookingHistoryModel> bookingHistory(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(API.showWallet)
    Call<TransactionData> showWallet(@Field("userID") String userID);


    @GET(API.aboutUs)
    Call<AboutUsModel> aboutUS();

    @FormUrlEncoded
    @POST(API.deleteNotification)
    Call<DeleteNotificationModel> deleteNotification(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST(API.newNotifications)
    Call<NewNotificationModel> showNewNoti(@Field("userID") String userID);


    @FormUrlEncoded
    @POST(API.deleteBooking)
    Call<DeleteBookingModel> deleteBooking(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST(API.deleteChat)
    Call<DeleteChatsModel> deleteChat(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST(API.updateMobile)
    Call<SocialLoginModel> updateMobile(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST(API.stripeResponse)
    Call<StripeResponse> stripeResponse(@FieldMap Map<String, String> param);


}
