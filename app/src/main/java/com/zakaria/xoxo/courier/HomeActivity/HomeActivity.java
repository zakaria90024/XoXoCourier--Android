package com.zakaria.xoxo.courier.HomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zakaria.xoxo.courier.MainActivity.MainActivity;
import com.zakaria.xoxo.courier.R;
import com.zakaria.xoxo.courier.ReadActivity.ReadActivity;

public class HomeActivity extends AppCompatActivity {
    public Button btn_deliber, btn_paymeny, btn_info;
    private LinearLayout item_contact;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.menu_info:
                item_contact = (LinearLayout) findViewById(R.id.contect_id);
                //for diaglog show
                Dialog myDialog  = new Dialog(HomeActivity.this);
                myDialog.setContentView(R.layout.dialog_contact);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                break;
            // action with ID action_settings was selected
            case R.id.menu_page:

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/140443846894938"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/Muhammad.MdZakaria")));
                }
                break;

            case R.id.menu_rateus:
                Toast.makeText(this, "Rate Us Clicked !", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_about:
                Toast.makeText(this, "About Clicked !", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_deliber = findViewById(R.id.btn_deliber);
        btn_paymeny = findViewById(R.id.btn_update);
        btn_info = findViewById(R.id.btn_update2);

        btn_deliber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new  Intent (HomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btn_paymeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new  Intent (HomeActivity.this, ReadActivity.class);
                startActivity(intent);

            }
        });
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new  Intent (HomeActivity.this, ReadActivity.class);
                startActivity(intent);

            }
        });


    }
}
