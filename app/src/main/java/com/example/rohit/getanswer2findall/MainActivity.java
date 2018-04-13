package com.example.rohit.getanswer2findall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    JSONArray jsonArray;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;
    Button button;
    static final String api="http://192.168.0.110:9000/answer/2/FindAll\n";
    static final String key="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YWFhMjc4MWZlMTAzNDdmMWZlYzEyNDAiLCJhZG1pbklkIjoiamF5IiwicGFzc3dvcmQiOiJqYXkxMjMiLCJpYXQiOjE1MjM2MTE5Njd9.5-RMgiiQ_HceDYxVoVDXM0WKefUgvD4QBxz5HVBAv48";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button);
        requestQueue=Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                strock();
            }
        });

    }

    private void strock()
    {
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, api, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    jsonArray=response.getJSONArray("doc");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String _id=jsonObject.getString("_id");
                        String answer=jsonObject.getString("answer");
                        String ncode=jsonObject.getString("ncode");
                        String __v=jsonObject.getString("__v");

                        textView.append("\n"+"id = "+_id+"\n"+"answer = "+answer+"\n"+"ncode = "+ncode+"\n"+"v = "+__v);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                        Log.d("Cause of error is",""+error);
                    }
                })
        {
            @Override
            public Map<String,String> getHeaders()
            {
                HashMap<String,String> hashMap=new HashMap<String,String>();
                hashMap.put("authorization",key);
                return hashMap;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
