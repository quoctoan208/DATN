package com.example.datn.Momo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn.R;
import com.example.datn.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class ThanhToanMomoActivity extends AppCompatActivity {

    Button btnpay;

    private String amount = "10000";// Giá tiền mặc định
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "QuocToan";// Tên đối tác ở momo
    private String merchantCode = "SCB01";//mã đối tác ở momo
    private String merchantNameLabel = "QuocToan";
    private String description = "Thanh toán dịch vụ mua hàng online";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btn_pay);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        anhxa();
        onclick();
    }

    private void onclick() {
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maDH= "DH1Test";
                requestPayment(maDH);
            }
        });
    }

    //Get token through MoMo app
    private void requestPayment(String maDonHang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer Giá tiền thanh toán
        eventValue.put("orderId", maDonHang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", maDonHang); //gán nhãn = Mã đơn hàng

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId", merchantCode + "merchant_billId_" + System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }

    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE

                    Log.d("Thành công", data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    //Có thể tạo thêm cột trạng thái thanh toán rôi add Token - Trạng thái vào
                    Toast.makeText(this, "Toản đã thanh toán đơn hàng", Toast.LENGTH_SHORT).show();

                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if (env == null) {
                        env = "app";
                    }

                    if (token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("message", "Không thành công");
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";
                    Log.d("message", "Không thành công");
                } else if (data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("message", "Không thành công");
                } else {
                    //TOKEN FAIL
                    Log.d("message", "Không thành công");
                }
            } else {
                Log.d("message" , "Không thành công");
            }
        } else {
            Log.d("message" , "Không thành công");
        }
    }

    private void anhxa() {
        btnpay = findViewById(R.id.btnPay);
    }
}
