package com.example.razorpayinteration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    EditText editText;
    Button bt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.mainActivityEditText);
        bt = findViewById(R.id.mainActivityButton);
        tv = findViewById(R.id.mainActivityText);

        Checkout.preload(getApplicationContext());


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {

        String s = editText.getText().toString();
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_lhi3rBXR94wkXB");

        checkout.setImage(R.drawable.adventure);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Santosh Pay");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(s)*100));//pass amount in currency subunits
            options.put("prefill.email", "sk8996825@gmail.com");
            options.put("prefill.contact", "9555869918");
            //    JSONObject retryObj = new JSONObject();
            //    retryObj.put("enabled", true);
            //    retryObj.put("max_count", 4);
            //    options.put("retry", retryObj);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
            Toast.makeText(activity, "Error in starting Razorpay Checkout"+e, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        tv.setText("Successful payment Id : "+s);
        tv.setTextColor(Color.GREEN);
    }

    @Override
    public void onPaymentError(int i, String s) {
        tv.setText("Failed and cause is : "+s);
        tv.setTextColor(Color.RED);
    }
}


