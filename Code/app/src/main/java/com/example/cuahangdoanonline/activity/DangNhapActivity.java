package com.example.cuahangdoanonline.activity;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangdoanonline.R;
import com.example.cuahangdoanonline.ultil.Server;


import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    Button bttdangnhap,bttdangky;
    EditText   edtTenDN,edtMK;
    private TextView txtvcanhbao;
    public  int count=0;
    public static int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        bttdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DangKyActivity.class));
            }
        });

        bttdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tendn=edtMK.getText().toString().trim();
                String mk=edtTenDN.getText().toString().trim();
                if(tendn.length()>0 && mk.length()>0){
                    kiemtrataikhoan(); //mở khóa
                }else{

                    txtvcanhbao.setText("!!! Vui Lòng Điền Đầy Đủ Thông Tin");
                }
            }
        });


    }
    public  void kiemtrataikhoan(){
        final RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.kiemtrataikhoan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("error")) {
                    txtvcanhbao.setText("Tài Khoản Hoặc Mật Khẩu Không Đúng!!! ");
                }else{
                    int value=Integer.parseInt(response);
                    id=value;
                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangNhapActivity.this,"Lỗi Xảy Ra",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("taikhoan",edtTenDN.getText().toString().trim());
                params.put("matkhau",edtMK.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void anhxa(){
        txtvcanhbao=(TextView)findViewById(R.id.textviewcanhbaodangnhap);
        bttdangky=(Button)findViewById(R.id.btn_dangky);
        bttdangnhap=(Button)findViewById(R.id.btn_dangnhap);
        edtMK=(EditText)findViewById(R.id.txt_password);
        edtTenDN=(EditText)findViewById(R.id.txt_email);
    }
}
