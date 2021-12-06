package com.vvpcollege.vvp.placementappfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notty extends AppCompatActivity {

    Button sendButt;
    EditText mtitle;
    EditText mdesc;
    String title,message;

    String url="http://fcm.googleapis.com/fcm/send";
    RequestQueue requestQueue;
    JsonObjectRequest jsObjRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notty);
        sendButt = (Button) findViewById(R.id.sendButt);
        mtitle = (EditText) findViewById(R.id.title);
        mdesc = (EditText) findViewById(R.id.desc);
        getSupportActionBar().hide();

        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Notty.this, ""+mdesc.getText().toString()+mtitle.getText().toString(), Toast.LENGTH_SHORT).show();
                title=mtitle.getText().toString();
                message=mdesc.getText().toString();
                sendFCMPush(title,message);

            }
        });
    }
    private void sendFCMPush(String tit,String mess) {
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(this);
        final String SERVER_KEY = "AAAA1UJBOxU:APA91bE4pSRVnuSTJHWiHLdQWMoiawkmqre1wzLES9ni1OYlhtdo-vufUumc4qnzVuOAO2-3VEoXkaL0f92-iDojNDeOUopILpUgvR1mfkTxeJz4yzHn_LtJu5wS0Cx-syUBLGPSKgUS";
        //String msg = mtitle.getText().toString();
        //String title = mdesc.getText().toString();
        //String token = FCM_TOKEN;
        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;
        try {
            obj = new JSONObject();
            objData = new JSONObject();
            objData.put("body", mess);
            objData.put("title", tit);
            objData.put("sound", "default");
            objData.put("icon", "@drawable/ic_copyright_black_24dp.xml"); //icon_name
            objData.put("priority", "high");
            dataobjData = new JSONObject();
            dataobjData.put("text", mess);
            dataobjData.put("title", tit);
            obj.put("to","/topics/news"); ///////////topic change karvano ///////
            //obj.put("priority", "high");
            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("return here>>", obj.toString());
            Log.e("True1", "true1--"+mess+tit+obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsObjRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, obj,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("True", response + "");
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("False", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }
}
