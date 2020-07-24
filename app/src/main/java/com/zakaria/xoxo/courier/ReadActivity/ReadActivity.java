package com.zakaria.xoxo.courier.ReadActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.zakaria.xoxo.courier.HomeActivity.HomeActivity;
import com.zakaria.xoxo.courier.MainActivity.MainActivity;
import com.zakaria.xoxo.courier.R;
import com.zakaria.xoxo.courier.main.data.remote.APIService;
import com.zakaria.xoxo.courier.main.data.remote.ApiUtils;
import com.zakaria.xoxo.courier.main.data.remote.RetrofitClient;
import com.zakaria.xoxo.courier.main.model.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter adapter;
    ProgressDialog pd;
    List<Post> modelList = new ArrayList<>();
    APIService apiInterface;
    private StartAppAd startAppAd = new StartAppAd(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        //for startapps ads
        StartAppSDK.init(this, "201848149", true);
        setContentView(R.layout.activity_read);
        recyclerView = findViewById(R.id.recyclerId);


        //progress diaglog
        pd = new ProgressDialog(this);

        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new CustomAdapter(ReadActivity.this, modelList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getAllUser();
        //deleteData(23);
        //serchData("");

    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(ReadActivity.this, HomeActivity.class);
        startActivity(setIntent);
        StartAppAd.onBackPressed(this);
        finish();
    }


    public void getAllUser(){
        //set title progress diaglog
        pd.setTitle("Loading Data...");
        //for show progress diaglog
        pd.show();

        Call<List<Post>> userList = ApiUtils.getAPIService().getpost();
        userList.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                List<Post> posts = response.body();
                adapter.setData(posts);
                recyclerView.setAdapter(adapter);

                pd.dismiss();
                //show data


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

    }

    public void deleteData(int index) {
        //set title progress diaglog
        pd.setTitle("Deleting Data...");
        //for show progress diaglog
        pd.show();


        Call<Void> deleteCall = ApiUtils.getAPIService().deletePost(index);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                pd.dismiss();
                Toast.makeText(ReadActivity.this, "Delete Successfull", Toast.LENGTH_LONG).show();
                getAllUser();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(ReadActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                getAllUser();

            }
        });
    }

    //for add search aption

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //influte menu main xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        //MenuItem item = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(

                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when we press search button
                serchData(query);//function call with string entered searchview as parameter

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called as and when type a single latter
                serchData(newText);
                return false;
            }
        });

        return true;
    }

    private void serchData(String key) {
//        //set title of progress bar
//        pd.setTitle("Searching....");
//        //shoe progress bar when click
//        pd.show();
        apiInterface = ApiUtils.getAPIService();

//        APIService apiService = ApiUtils.getAPIService().
        Call<List<Post>> call = apiInterface.getUsers(key);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                pd.dismiss();
                //set data from list
                modelList = response.body();
                //adapter
                adapter = new CustomAdapter(ReadActivity.this, modelList);
                //set adapter to recycler
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(ReadActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle other menu item click here
        if (item.getItemId() == R.id.action_settings){
            getAllUser();
            //Toast.makeText(ListActivity.this, "setting", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
