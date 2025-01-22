package com.jubaer_ahmed.e_commers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.bumptech.glide.Glide;

public class HomeFragment extends Fragment {

     ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
     ProgressDialog progressDialog;
     GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = view.findViewById(R.id.gridView);
        progressDialog = new ProgressDialog(getContext());

        MyAdapter myAdapter = new MyAdapter();
        gridView.setAdapter(myAdapter);

        fetchProductData();

        return view;
    }

    private void fetchProductData() {

        showProgress("Loading...");
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://dummyjson.com/products", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgress();

                        try {
                            JSONArray jsonArray = response.getJSONArray("products");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String price = jsonObject.getString("price");
                                String thumbnail = jsonObject.getString("thumbnail");
                                String rating = jsonObject.getString("rating");
                                String description = jsonObject.getString("description");
                                String stock = jsonObject.getString("stock");
                                String warranty = jsonObject.getString("warrantyInformation");
                                String shipping = jsonObject.getString("shippingInformation");
                                String discount = jsonObject.getString("discountPercentage");

                                String[] images = new String[]{jsonObject.getString("images")};
                                String imagesArray = Arrays.toString(images);


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("title", title);
                                hashMap.put("price", price);
                                hashMap.put("thumbnail", thumbnail);
                                hashMap.put("rating",rating);
                                hashMap.put("images",imagesArray);
                                hashMap.put("description",description);
                                hashMap.put("stock",stock);
                                hashMap.put("warrantyInformation",warranty);
                                hashMap.put("shippingInformation",shipping);
                                hashMap.put("discountPercentage",discount);
                                arrayList.add(hashMap);
                            }

                            MyAdapter myAdapter = (MyAdapter) gridView.getAdapter();
                            myAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.product_design, parent, false);

            ImageView productImage = view.findViewById(R.id.imageProduct);
            TextView productTitle = view.findViewById(R.id.titleProduct);
            TextView productPrice = view.findViewById(R.id.priceProduct);
            TextView productRating = view.findViewById(R.id.ratingProduct);
            LinearLayout itemLayout = view.findViewById(R.id.itemLay);

            HashMap<String, String> hashMap = arrayList.get(position);

            Glide.with(getContext()).load(hashMap.get("thumbnail")).into(productImage);
            productTitle.setText(hashMap.get("title"));
            productPrice.setText("$"+hashMap.get("price"));
            productRating.setText(hashMap.get("rating")+"★");

            String price = hashMap.get("price");
            String title = hashMap.get("title");
            String rating = hashMap.get("rating");
            String images = hashMap.get("images");
            String description = hashMap.get("description");
            String stock = hashMap.get("stock");
            String warranty = hashMap.get("warrantyInformation");
            String shipping = hashMap.get("shippingInformation");
            String discount = hashMap.get("discountPercentage");
            String thumbnail = hashMap.get("thumbnail");


            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DetailsPage.imageSliderArray = images;
                    DetailsPage.price = price;
                    DetailsPage.discount = discount;
                    DetailsPage.description = description;
                    DetailsPage.stock = stock;
                    DetailsPage.title = title;
                    DetailsPage.rating = rating;
                    DetailsPage.warranty = warranty;
                    DetailsPage.shipping = shipping;
                    DetailsPage.thumbnail = thumbnail;

                    Intent intent = new Intent(getActivity(), DetailsPage.class);
                    startActivity(intent);
                }
            });


            return view;
        }
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
}
