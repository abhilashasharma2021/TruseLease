package com.truelease.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.truelease.ApiData.API;
import com.truelease.Others.AppConstats;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.User.UserData;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class UploadDocumentActivity extends AppCompatActivity {

    ImageView imageDoc, back, imageDoc2;
    Button btnUpload;
    String strImgFront = "", strImgBack = "",strImage="",strImage1="";
    private static final String IMAGE_DIRECTORY = "/True Lease";
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    private final int GALLERY2 = 3;
    private final int CAMERA2 = 4;
    LinearLayout linearF, linearB;

    TextView showDocs,txtF;

    File f, f2;

    AutoCompleteTextView licenseType;
    String[] lType = {"Driving License", "Passport", "Aadhar card"};
    String strLicenseType = "";
    String dType = "";

    public static final int CODE_IMG_GALLERY = 1111;
    public static final int CODE_IMG_GALLERY2 = 1112;
    public static final String CROP_IMAGE = "Cropped_Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        imageDoc = findViewById(R.id.imageDoc);
        btnUpload = findViewById(R.id.btnUpload);
        back = findViewById(R.id.back);
        licenseType = findViewById(R.id.licenseType);
        imageDoc2 = findViewById(R.id.imageDoc2);
        linearF = findViewById(R.id.linearF);
        linearB = findViewById(R.id.linearB);
        showDocs = findViewById(R.id.showDocs);
        txtF = findViewById(R.id.txtF);

      //  imageDoc.setOnClickListener(v -> showPictureDialog());
        imageDoc2.setOnClickListener(v -> startActivityForResult(new Intent()
                .setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*"), CODE_IMG_GALLERY2));

        imageDoc.setOnClickListener(view -> startActivityForResult(new Intent()
                .setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*"), CODE_IMG_GALLERY));
        back.setOnClickListener(v -> finish());

        showDocs.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), DocumentsActivity.class)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UploadDocumentActivity.this, android.R.layout.simple_list_item_1, lType);
        licenseType.setAdapter(adapter);


        licenseType.setOnItemClickListener((parent, view, position, rowId) -> {
            strLicenseType = (String) parent.getItemAtPosition(position);
            if (strLicenseType.equals("Driving License")) {
                linearB.setVisibility(View.VISIBLE);
                linearF.setVisibility(View.VISIBLE);
                txtF.setVisibility(View.VISIBLE);
                dType = "1";

            } else if (strLicenseType.equals("Passport")) {
                linearB.setVisibility(View.GONE);
                linearF.setVisibility(View.VISIBLE);
                txtF.setVisibility(View.GONE);

                dType = "2";

            } else {
                linearB.setVisibility(View.GONE);
                linearF.setVisibility(View.VISIBLE);
                txtF.setVisibility(View.GONE);
                dType = "3";
            }

        });


        btnUpload.setOnClickListener(v -> {

            if (strLicenseType.equals("")) {
                Toast.makeText(UploadDocumentActivity.this, "Please select your license type", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("skxs", dType);
                Log.e("skxs", strImage);
                Log.e("skxs", strImage1);
                if (dType.equals("1")) {


                    if (strImage.equals("") || strImage1.equals("")) {
                        Toast.makeText(this, "Please select both the images", Toast.LENGTH_SHORT).show();
                    } else {
                        updatePrf("1");
                    }


                } else if (dType.equals("2")) {
                    uploadPassport("2");
                } else if (dType.equals("3")) {

                    uploadAadhar("3");
                }

            }
        });


    }


    private void startCrop(@NonNull Uri uri) {

        String destinationFileName = CROP_IMAGE;
        destinationFileName += ".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getExternalCacheDir().getAbsolutePath(), destinationFileName)));
        uCrop.withAspectRatio(1, 1)
                .withMaxResultSize(600, 600)
                .withOptions(getOptions())
                .start(this);


    }


    private UCrop.Options getOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(40);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarTitle("Crop Image");
        return options;

    }



    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    private void showPictureDialog2() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary2();
                                break;
                            case 1:
                                takePhotoFromCamera2();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary2() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY2);
    }

    private void takePhotoFromCamera2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA2);
    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    strImgFront = path;
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageDoc.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageDoc.setImageBitmap(thumbnail);
            String path = saveImage(thumbnail);
            strImgFront = path;
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }


        if (requestCode == GALLERY2) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage2(bitmap);

                    strImgBack = path;
                    File file = new File(path);

                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageDoc2.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA2) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageDoc2.setImageBitmap(thumbnail);
            String path = saveImage2(thumbnail);
            strImgBack = path;
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }


    }

*/



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_IMG_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            imageDoc.setImageURI(imageUri);
            if (imageUri != null) {
                startCrop(imageUri);
            } else {
                startCrop(imageUri);
            }
            strImage = String.valueOf(imageUri);
            Log.e("gchgchgchcgfxfgf", strImage+"f1" );
           // f=new File(strImage);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                saveImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {


            if (requestCode==CODE_IMG_GALLERY){
                Uri imageUriResultCrop = UCrop.getOutput(data);
                File directory = new File(imageUriResultCrop.getEncodedPath());
                String strFileName = directory.toString();
                Log.e("imageUri", strFileName+CODE_IMG_GALLERY);
               // imageDoc.setImageURI(imageUriResultCrop);

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriResultCrop);
                    Log.e("hehhshshsh", bitmap.toString());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String strImageBitmap = Base64.encodeToString(b, Base64.DEFAULT);

                    // imgPrf.setImageURI(imageUriResultCrop);
                    imageDoc.setImageBitmap(bitmap);
                } catch (IOException ignored) {
                    Log.e("chfccffxgxfg", ignored.getMessage() );
                }



            }

        }



        if (requestCode == CODE_IMG_GALLERY2 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            imageDoc2.setImageURI(imageUri);
            if (imageUri != null) {
                startCrop(imageUri);
            } else {
                startCrop(imageUri);
            }
            strImage1 = String.valueOf(imageUri);
            Log.e("gchgchgchcgfxfgf", strImage1+"f2" );
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                saveImage2(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {


            if (requestCode==CODE_IMG_GALLERY2){
                Uri imageUriResultCrop = UCrop.getOutput(data);
                File directory = new File(imageUriResultCrop.getEncodedPath());
                String strFileName = directory.toString();
                Log.e("imageUri", strFileName);

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriResultCrop);
                    Log.e("hehhshshsh", bitmap.toString());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String strImageBitmap = Base64.encodeToString(b, Base64.DEFAULT);

                    // imgPrf.setImageURI(imageUriResultCrop);
                    imageDoc.setImageBitmap(bitmap);
                } catch (IOException ignored) {

                }




            }


        }






    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();


            return f.getAbsolutePath();


        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public String saveImage2(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f2 = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f2.createNewFile();
            FileOutputStream fo = new FileOutputStream(f2);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f2.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            return f2.getAbsolutePath();


        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void updatePrf(String documentType, File dBack) {


        com.truelease.Others.MyDialog.DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, UploadDocumentActivity.this);

        AndroidNetworking.upload(API.BASE_URL + API.updateProfile)
                .addMultipartParameter("userID", UserData.getUserID(UploadDocumentActivity.this))
                .addMultipartParameter("document_type", documentType)
                .addMultipartFile("D_license_front", f)
                .addMultipartFile("D_license_back", dBack)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("ssjResponse::", documentType);
                        Log.e("ssjResponse::", UserData.getUserID(getApplicationContext()));
                        Log.e("ssjResponse::", response.toString());


                        try {
                            if (response.getString("result").equals("true")) {


                                dialogInterface.hideDialog();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String path = jsonObject.getString("path");
                                String verification_documents = jsonObject.getString("verification_documents");
                                String D_license_front = jsonObject.getString("D_license_front");
                                String D_license_back = jsonObject.getString("D_license_back");

                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, D_license_front);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, D_license_back);


                                Glide.with(UploadDocumentActivity.this).load(path + D_license_front).into(imageDoc);
                                Glide.with(UploadDocumentActivity.this).load(path + D_license_back).into(imageDoc2);
                                startActivity(new Intent(UploadDocumentActivity.this, UploadDocumentActivity.class));
                                Animatoo.animateShrink(UploadDocumentActivity.this);
                                finish();


                            } else {
                                dialogInterface.hideDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialogInterface.hideDialog();
                            Log.e("lkskdl", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialogInterface.hideDialog();
                        Log.e("lkskdl", anError.getMessage());
                    }
                });
    }


    public void updatePrf(String documentType) {
        Log.e("hchgchgghgcgh", f+"");
        Log.e("hchgchgghgcgh", f2+"");

        com.truelease.Others.MyDialog.DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, UploadDocumentActivity.this);

        AndroidNetworking.upload(API.BASE_URL + API.updateProfile)
                .addMultipartParameter("userID", UserData.getUserID(UploadDocumentActivity.this))
                .addMultipartParameter("document_type", documentType)
                .addMultipartFile("D_license_front", f)
                .addMultipartFile("D_license_back", f2)
                .setTag("verification")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("ssjResponse::", documentType);
                        Log.e("ssjResponse::", UserData.getUserID(getApplicationContext()));
                        Log.e("ssjResponse::", response.toString());


                        try {
                            if (response.getString("result").equals("true")) {


                                dialogInterface.hideDialog();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String path = jsonObject.getString("path");
                                String verification_documents = jsonObject.getString("verification_documents");
                                String D_license_front = jsonObject.getString("D_license_front");
                                String D_license_back = jsonObject.getString("D_license_back");

                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, D_license_front);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, D_license_back);


                                Glide.with(UploadDocumentActivity.this).load(path + D_license_front).into(imageDoc);
                                Glide.with(UploadDocumentActivity.this).load(path + D_license_back).into(imageDoc2);
                                startActivity(new Intent(UploadDocumentActivity.this, UploadDocumentActivity.class));
                                Animatoo.animateShrink(UploadDocumentActivity.this);
                                finish();


                            } else {
                                dialogInterface.hideDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialogInterface.hideDialog();
                            Log.e("lkskdl", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialogInterface.hideDialog();
                        Log.e("lkskdl", anError.getMessage());
                    }
                });
    }


    public void uploadPassport(String documentType) {
        Log.e("fjjvnbbmbvm", f+"" );


        com.truelease.Others.MyDialog.DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, UploadDocumentActivity.this);
        AndroidNetworking.upload(API.BASE_URL + API.updateProfile)
                .addMultipartParameter("userID", UserData.getUserID(getApplicationContext()))
                .addMultipartParameter("document_type", documentType)
                .addMultipartFile("paspot", f)
                .setTag("fgfdg")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("result").equals("true")) {


                                dialogInterface.hideDialog();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String path = jsonObject.getString("path");
                                String passport = jsonObject.getString("paspot");


                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, passport);


                                Glide.with(UploadDocumentActivity.this).load(path + passport).into(imageDoc);
                                startActivity(new Intent(UploadDocumentActivity.this, UploadDocumentActivity.class));
                                Animatoo.animateShrink(UploadDocumentActivity.this);
                                finish();


                            } else {
                                dialogInterface.hideDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialogInterface.hideDialog();
                            Log.e("lkskdl", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialogInterface.hideDialog();
                        Log.e("lkskdl", anError.getMessage());
                    }
                });
    }


    public void uploadAadhar(String documentType) {


        com.truelease.Others.MyDialog.DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, UploadDocumentActivity.this);
        AndroidNetworking.upload(API.BASE_URL + API.updateProfile)
                .addMultipartParameter("userID", UserData.getUserID(getApplicationContext()))
                .addMultipartParameter("document_type", documentType)
                .addMultipartFile("adhar_card", f)
                .setTag("adhar_card")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("result").equals("true")) {


                                dialogInterface.hideDialog();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String adhar_card = jsonObject.getString("adhar_card");

                                Toast.makeText(UploadDocumentActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();

                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, adhar_card);


                                startActivity(new Intent(UploadDocumentActivity.this, UploadDocumentActivity.class));



                            } else {
                                dialogInterface.hideDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialogInterface.hideDialog();
                            Log.e("lkskdl", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialogInterface.hideDialog();
                        Log.e("lkskdl", anError.getMessage());
                    }
                });
    }


}