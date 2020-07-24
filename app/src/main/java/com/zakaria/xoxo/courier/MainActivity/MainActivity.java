package com.zakaria.xoxo.courier.MainActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.zakaria.xoxo.courier.HomeActivity.HomeActivity;
import com.zakaria.xoxo.courier.R;
import com.zakaria.xoxo.courier.ReadActivity.ReadActivity;
import com.zakaria.xoxo.courier.main.data.remote.APIService;
import com.zakaria.xoxo.courier.main.data.remote.ApiUtils;
import com.zakaria.xoxo.courier.main.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    EditText edname, edemail, ednumber, edcash, edaddress, edstate, edzip, edrole;
    String name, email, number, cash, address, state, zip, role;
    Integer id;
    TextView tres;
    private APIService mAPIService;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "201848149", true);
        setContentView(R.layout.activity_main);

        mAPIService = ApiUtils.getAPIService();
        edname = findViewById(R.id.editText_name);
        edemail = findViewById(R.id.editText_email);
        ednumber = findViewById(R.id.editText_number);
        edcash = findViewById(R.id.editText_cash);
        edaddress = findViewById(R.id.editText_address);
        edstate = findViewById(R.id.editText_state);
        edzip = findViewById(R.id.editText_zip);
        edrole = findViewById(R.id.editText_role);
        tres = findViewById(R.id.textView_id);


        //Actionbar text change
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update your order");

        //for get intent data====================
        //===================================
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getInt("id");
            name = bundle.getString("name");
            email= bundle.getString("email");
            number= bundle.getString("number");
            cash= bundle.getString("cash");
            address= bundle.getString("address");
            state= bundle.getString("state");
            zip= bundle.getString("zip");
            role= bundle.getString("role");

            //old data set in edittext
            edname.setText(name);
            edemail.setText(email);
            ednumber.setText(number);
            edcash.setText(cash);
            edaddress.setText(address);
            edstate.setText(state);
            edzip.setText(zip);
            edrole.setText(role);


        }else{
            actionBar.setTitle("Add Order");
        }
        //progress diaglog
        pd = new ProgressDialog(this);



        Button submitBtn = (Button) findViewById(R.id.btnSubmit);

        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    String name, email, number, cash, address, state, zip, role;

                    Integer id;
                    id = bundle.getInt("id");

                    name = edname.getText().toString();
                    email = edemail.getText().toString();
                    number = ednumber.getText().toString();
                    cash = edcash.getText().toString();
                    address = edaddress.getText().toString();
                    state = edstate.getText().toString();
                    zip = edzip.getText().toString();
                    role = edrole.getText().toString();

                    //function call to update
                    updateData(id, name, email, number, cash, address, state, zip, role);

                }else {

                    name = edname.getText().toString();
                    email = edemail.getText().toString();
                    number = ednumber.getText().toString() ;
                    cash = edcash.getText().toString();
                    address = edaddress.getText().toString();
                    state = edstate.getText().toString();
                    zip = edzip.getText().toString();
                    role = edrole.getText().toString();

                    sendPost(name, email, number, cash, address, state, zip, role);

//                if(TextUtils.isEmpty(name) ||TextUtils.isEmpty(email) ) {
//                    edname.setError("Please fillup all field");
//                    return;
//                }else {
//
//                }

                }



            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(setIntent);
        StartAppAd.onBackPressed(this);
        finish();
    }


    private void updateData(int id, String name, String email, String number, String cash, String address, String state, String zip, String role) {
        //Toast.makeText(MainActivity.this, "\nOrdered "+id+"\nname"+name+"\nemail"+email , Toast.LENGTH_SHORT).show();
        mAPIService.putPost(id, name, email, number, cash, address, state, zip, role).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(MainActivity.this, "Succesfull Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Succesfull Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendPost(String name, String email, String number, String cash, String address, String state, String zip, String role) {

        mAPIService.savePost(name, email, number, cash, address, state, zip, role).enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(MainActivity.this, "post submit success" , Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
                    tres.setText("Connected");
                    Toast.makeText(MainActivity.this, "submit success" +response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tres.setText("Connected");
                Toast.makeText(MainActivity.this, "Successfully Ordered" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ViewJosonBtn(View view) {
        Intent myIntent = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(myIntent);
        finish();
    }

}
