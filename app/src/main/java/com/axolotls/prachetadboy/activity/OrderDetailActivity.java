package com.axolotls.prachetadboy.activity;

import static com.axolotls.prachetadboy.helper.ApiConfig.disableSwipe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.axolotls.prachetadboy.R;
import com.axolotls.prachetadboy.adapter.ItemsAdapter;
import com.axolotls.prachetadboy.helper.ApiConfig;
import com.axolotls.prachetadboy.helper.AppController;
import com.axolotls.prachetadboy.helper.Constant;
import com.axolotls.prachetadboy.helper.Session;
import com.axolotls.prachetadboy.model.OrderList;

@SuppressLint("SetTextI18n")
public class OrderDetailActivity extends AppCompatActivity {

    TextView tvNote, tvDate, tvOrderID, tvName, tvSellerPhoneNumber, tvSellerName, tvPhone, tvAddress, tvDeliveryTime, tvItemTotal, tvTaxAmt, tvPCAmount, tvWallet, tvFinalTotal, tvPaymentMethod, tvDiscountAmount, tvDeliveryCharge;

    Button btnDeliveryStatus, btnGetDirection;
    SwipeRefreshLayout SwipeRefresh;
    Toolbar toolbar;
    Activity activity;
    RelativeLayout lyt_order_detail;
    String latitude, longitude, s_latitude, s_longitude;
    OrderList orderList;
    int position_ = 0;
    ArrayList<String> arrayList_;
    TextView tvSellerAddress;

    int checkedItem;
    String otp;
    String[] updatedStatus;
    RecyclerView recyclerView;
    String itemIds = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = findViewById(R.id.toolbar);
        activity = OrderDetailActivity.this;

        orderList = (OrderList) getIntent().getSerializableExtra(Constant.ITEM);
        position_ = getIntent().getIntExtra(Constant.POSITION, 0);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.order_item_number) + orderList.getId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDate = findViewById(R.id.tvDate);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvName = findViewById(R.id.tvName);
        tvNote = findViewById(R.id.tvNote);
        tvSellerAddress = findViewById(R.id.tvSellerAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvSellerPhoneNumber = findViewById(R.id.tvSellerPhoneNumber);
        tvSellerName = findViewById(R.id.tvSellerName);
        tvAddress = findViewById(R.id.tvAddress);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        findViewById(R.id.nestedScrollView).setNestedScrollingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        tvDeliveryTime = findViewById(R.id.tvDeliveryTime);
        tvItemTotal = findViewById(R.id.tvItemTotal);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        btnDeliveryStatus = findViewById(R.id.btnDeliveryStatus);
        tvTaxAmt = findViewById(R.id.tvTaxAmt);
        tvPCAmount = findViewById(R.id.tvPCAmount);
        tvFinalTotal = findViewById(R.id.tvFinalTotal);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        tvWallet = findViewById(R.id.tvWallet);
        tvDiscountAmount = findViewById(R.id.tvDiscountAmount);
        lyt_order_detail = findViewById(R.id.lyt_order_detail);
        SwipeRefresh = findViewById(R.id.SwipeRefresh);
        btnGetDirection = findViewById(R.id.btnGetDirection);

        if (AppController.isConnected(activity)) {
            getOrderData(orderList);
        } else {
            setSnackBar(activity, getString(R.string.no_internet_message), getString(R.string.retry), Color.RED);
        }

        SwipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        SwipeRefresh.setOnRefreshListener(() -> {
            if (AppController.isConnected(activity)) {
                getOrderData(orderList);
                SwipeRefresh.setRefreshing(false);
                disableSwipe(SwipeRefresh);
            } else {
                setSnackBar(activity, getString(R.string.no_internet_message), getString(R.string.retry), Color.RED);
            }
            SwipeRefresh.setRefreshing(false);
        });

    }

    public void getOrderData(OrderList orderList) {

        for (int i = 0; i < orderList.getItems().size(); i++) {
            if (i != orderList.getItems().size()) {
                itemIds = itemIds + orderList.getItems().get(i).getId() + ",";
            } else {
                itemIds = itemIds + orderList.getItems().get(i).getId();
            }
        }

        tvOrderID.setText(getString(R.string.order_id) + orderList.getId());
        tvDate.setText(getString(R.string.order_on) + orderList.getDate_added());
        otp = orderList.getOtp();
        tvName.setText(getString(R.string._name) + orderList.getUser_name());
        tvNote.setText(orderList.getOrder_note().equals("") ? "-" : orderList.getOrder_note());
        tvPhone.setText(orderList.getMobile());
        tvSellerPhoneNumber.setText(orderList.getSeller_mobile());
        tvSellerName.setText(orderList.getSeller_name());
        tvSellerAddress.setText(orderList.getSeller_address());
        tvAddress.setText(orderList.getAddress());
        btnDeliveryStatus.setText(AppController.toTitleCase(orderList.getItems().get(0).getActive_status()));
        s_latitude = orderList.getSeller_latitude();
        s_longitude = orderList.getSeller_longitude();

        tvDeliveryTime.setText(getString(R.string.delivery_by) + orderList.getDelivery_time());

        tvDeliveryCharge.setText(new Session(activity).getData(Constant.CURRENCY) + orderList.getDelivery_charge());
        if (orderList.getLatitude().equals("0") && orderList.getLongitude().equals("0")) {
            btnGetDirection.setVisibility(View.GONE);
        } else {
            latitude = orderList.getLatitude();
            longitude = orderList.getLongitude();
        }
        tvItemTotal.setText(new Session(activity).getData(Constant.CURRENCY) + orderList.getTotal());
        tvPCAmount.setText(new Session(activity).getData(Constant.CURRENCY) + orderList.getPromo_discount());
        tvDiscountAmount.setText(new Session(activity).getData(Constant.CURRENCY) + orderList.getDiscount());
        tvWallet.setText(new Session(activity).getData(Constant.CURRENCY) + orderList.getWallet_balance());
        tvFinalTotal.setText(new Session(activity).getData(Constant.CURRENCY) + orderList.getFinal_total());

        tvPaymentMethod.setText(getString(R.string.via) + orderList.getPayment_method().toUpperCase());

        lyt_order_detail.setVisibility(View.VISIBLE);

        arrayList_ = new ArrayList<>();
        arrayList_.add(Constant.AWAITING_PAYMENT);
        arrayList_.add(Constant.RECEIVED);
        arrayList_.add(Constant.PROCESSED);
        arrayList_.add(Constant.SHIPPED);
        arrayList_.add(Constant.DELIVERED);
        arrayList_.add(Constant.CANCELLED);
        arrayList_.add(Constant.RETURNED);

        recyclerView.setAdapter(new ItemsAdapter(activity, orderList.getItems()));

    }

    public void Confirm_OTP() {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(OrderDetailActivity.this);
        LayoutInflater inflater = (LayoutInflater) OrderDetailActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_otp_confirm_request, null);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(true);
        final androidx.appcompat.app.AlertDialog dialog = alertDialog.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvDialogConfirm = dialogView.findViewById(R.id.tvDialogConfirm);
        TextView tvDialogCancel = dialogView.findViewById(R.id.tvDialogCancel);
        final EditText edtOTP = dialogView.findViewById(R.id.edtOTP);

        tvDialogConfirm.setOnClickListener(v -> {
            if (!edtOTP.getText().toString().isEmpty() || edtOTP.getText().toString().equals("0") || edtOTP.getText().toString().length() >= 6) {
                if (checkedItem <= 3) {
                    if (checkedItem == 3) {
                        if (edtOTP.getText().toString().equals(otp)) {
                            ChangeOrderStatus(activity, updatedStatus[checkedItem].toLowerCase());
                            dialog.dismiss();
                        } else {
                            Toast.makeText(activity, getString(R.string.otp_not_matched), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.can_not_update_order), Toast.LENGTH_SHORT).show();
                }
            } else {
                edtOTP.setError(getString(R.string.alert_otp));
            }
        });

        tvDialogCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void OnBtnClick(View view) {

        if (AppController.isConnected(activity)) {
            int id = view.getId();
            if (id == R.id.btnCallCustomer) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    if (ContextCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        callIntent.setData(Uri.parse("tel:" + tvPhone.getText().toString().trim()));
                        startActivity(callIntent);
                    }
                } catch (Exception ignored) {

                }
            } else if (id == R.id.btnGetDirection) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage(R.string.map_open_message);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        getString(R.string.yes),
                        (dialog, id15) -> {
//                                com.google.android.apps.maps
                            try {
                                Uri googleMapIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                activity.startActivity(mapIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                AlertDialog.Builder builder112 = new AlertDialog.Builder(activity);
                                builder112.setMessage("Please install google map first.");
                                builder112.setCancelable(true);

                                builder112.setPositiveButton(
                                        getString(R.string.ok),
                                        (dialog14, id151) -> dialog14.cancel());

                                AlertDialog alert11 = builder112.create();
                                alert11.show();
                            }
                        });

                builder1.setNegativeButton(
                        getString(R.string.no),
                        (dialog, id13) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();


            } else if (id == R.id.btnCallToSeller) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    if (ContextCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        callIntent.setData(Uri.parse("tel:" + tvSellerPhoneNumber.getText().toString().trim()));
                        startActivity(callIntent);
                    }
                } catch (Exception ignored) {

                }
            } else if (id == R.id.btnGetSellerDirection) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage(R.string.map_open_message);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        getString(R.string.yes),
                        (dialog, id14) -> {
//                                com.google.android.apps.maps
                            try {
                                Uri googleMapIntentUri = Uri.parse("google.navigation:q=" + s_latitude + "," + s_longitude + "");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                activity.startActivity(mapIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                AlertDialog.Builder builder11 = new AlertDialog.Builder(activity);
                                builder11.setMessage("Please install google map first.");
                                builder11.setCancelable(true);
                                builder11.setPositiveButton(
                                        getString(R.string.ok),
                                        (dialog12, id12) -> dialog12.cancel());

                                AlertDialog alert11 = builder11.create();
                                alert11.show();
                            }
                        });

                builder1.setNegativeButton(
                        getString(R.string.no),
                        (dialog, id1) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();


            } else if (id == R.id.btnDeliveryStatus) {
                // setup the alert builder
                updatedStatus = new String[6];

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.update_status);// add a radio button list

                updatedStatus = new String[]{Constant.RECEIVED, Constant.PROCESSED, Constant.SHIPPED, Constant.DELIVERED, Constant.CANCELLED, Constant.RETURNED};

                switch (btnDeliveryStatus.getText().toString()) {
                    case Constant.RECEIVED:
                        checkedItem = 0;
                        break;
                    case Constant.PROCESSED:
                        checkedItem = 1;
                        break;
                    case Constant.SHIPPED:
                        checkedItem = 2;
                        break;
                    case Constant.DELIVERED:
                        checkedItem = 3;
                        break;
                    case Constant.CANCELLED:
                        checkedItem = 4;
                        break;
                    case Constant.RETURNED:
                        checkedItem = 5;
                        break;
                }

                builder.setSingleChoiceItems(updatedStatus, checkedItem, (dialog, which) -> {
                    checkedItem = which;
                    updatedStatus[0] = updatedStatus[which];
                });
                builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                    Constant.CLICK = true;

                    final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(activity);
                    // Setting Dialog Message
                    alertDialog.setMessage(R.string.change_order_status_msg);
                    alertDialog.setCancelable(false);
                    final androidx.appcompat.app.AlertDialog alertDialog1 = alertDialog.create();

                    // Setting OK Button
                    alertDialog.setPositiveButton(R.string.yes, (dialog1, which1) -> {
                        if (otp.equals("0")) {
                            ChangeOrderStatus(activity, (updatedStatus[0].toLowerCase()));
                        } else {
                            if (checkedItem == 3) {
                                Confirm_OTP();
                            } else {
                                ChangeOrderStatus(activity, (updatedStatus[0].toLowerCase()));
                            }
                        }

                        btnDeliveryStatus.setText(AppController.toTitleCase(updatedStatus[0]));
                    });
                    alertDialog.setNegativeButton(R.string.no, (dialog13, which12) -> alertDialog1.dismiss());
                    // Showing Alert Message
                    alertDialog.show();

                });

                builder.setNegativeButton(R.string.cancel, null);// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else {
            setSnackBar(activity, getString(R.string.no_internet_message), getString(R.string.retry), Color.RED);
        }

    }

    public void ChangeOrderStatus(final Activity activity, final String status) {
        if (!itemIds.equals("")) {
            if (AppController.isConnected(activity)) {
                Map<String, String> params = new HashMap<>();
                params.put(Constant.BULK_STATUS_UPDATE, Constant.GetVal);
                params.put(Constant.ITEM_IDS, itemIds);
                params.put(Constant.STATUS, status);

                ApiConfig.RequestToVolley((result, response) -> {
                    if (result) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean(Constant.ERROR)) {
                                setSnackBar(activity, jsonObject.getString(Constant.MESSAGE), getString(R.string.ok), Color.GREEN);
                            } else {
                                setSnackBar(activity, jsonObject.getString(Constant.MESSAGE), getString(R.string.ok), Color.RED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, activity, Constant.MAIN_URL, params, true);
            } else {
                setSnackBar(activity, getString(R.string.no_internet_message), getString(R.string.retry), Color.RED);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void setSnackBar(final Activity activity, String message, String action, int color) {
        final Snackbar snackBar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackBar.setAction(action, view -> {
            getOrderData(orderList);
            snackBar.dismiss();
        });

        snackBar.setActionTextColor(color);
        View snackBarView = snackBar.getView();
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(5);
        snackBar.show();
    }

}