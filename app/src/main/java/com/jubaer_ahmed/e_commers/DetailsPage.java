package com.jubaer_ahmed.e_commers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DetailsPage extends AppCompatActivity {

    ArrayList<SlideModel> imageList = new ArrayList<>();
    ImageSlider imageSlider;
    ProgressDialog progressDialog;
    ImageView backButton;

    TextView textTitle,textRatings,textStokes,textPrice,textDiscount,textDescription,warrantyInfo,shippingInfo,textBrand;
    Button addToCart;


    public static String imageSliderArray = "";
    public static String title = "";
    public static String price = "";
    public static String rating = "";
    public static String stock = "";
    public static String discount = "";
    public static String description = "";
    public static String warranty = "";
    public static String shipping = "";
    public static String brand = "";
    public static String thumbnail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        imageSlider = findViewById(R.id.image_slider);
        progressDialog = new ProgressDialog(DetailsPage.this);
        backButton = findViewById(R.id.back_button);
        addToCart = findViewById(R.id.addToCart);
        textTitle = findViewById(R.id.textTitle);
        textRatings = findViewById(R.id.textRatings);
        textStokes = findViewById(R.id.textStokes);
        textPrice = findViewById(R.id.textPrice);
        textDiscount = findViewById(R.id.textDiscount);
        textDescription = findViewById(R.id.textDescription);
        warrantyInfo = findViewById(R.id.warrantyInfo);
        shippingInfo = findViewById(R.id.shippingInfo);
        textBrand = findViewById(R.id.textBrand);

        textTitle.setText(title);
        textTitle.setSelected(true);

        textBrand.setText(brand);
        textRatings.setText(rating+"⭐");
        textStokes.setText("Available stock: "+stock);
        textPrice.setText("$"+price);
        textDiscount.setText("/discount: "+discount+"%");
        textDescription.setText(description);
        warrantyInfo.setText(warranty);
        shippingInfo.setText(shipping);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ShowDialogBox();
            }
        });

        imageSlider();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void imageSlider() {
        try {
            JSONArray jsonArray = new JSONArray(imageSliderArray);

            if (jsonArray.length() > 0) {
                JSONArray imageLinkArray = jsonArray.getJSONArray(0);
                for (int i = 0; i < imageLinkArray.length(); i++) {
                    String imageLink = imageLinkArray.getString(i);
                    imageList.add(new SlideModel(imageLink, null));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            hideProgress();
        }

        imageSlider.setImageList(imageList);
    }

    private void showProgress(String message) {
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    private void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private void ShowDialogBox() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(DetailsPage.this);

        View mView = getLayoutInflater().inflate(R.layout.order_dialog_, null);
        alert.setView(mView);

        ImageView Thumbnail = mView.findViewById(R.id.thumbnailImg);
        TextView titleTxt = mView.findViewById(R.id.titleTxt);
        TextView priceTxt = mView.findViewById(R.id.priceTxt);

        titleTxt.setText(title);
        priceTxt.setText("$" + price);
        Glide.with(this).load(thumbnail).into(Thumbnail);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        mView.findViewById(R.id.confirmBTN).setOnClickListener(v -> {
            Toast.makeText(DetailsPage.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

            alertDialog.dismiss();

            // Navigate to History Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HistoryFragment())
                    .addToBackStack(null)
                    .commit();
        });

        mView.findViewById(R.id.cancekBTN).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }



}