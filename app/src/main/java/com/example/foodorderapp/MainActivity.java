package com.example.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodorderapp.adapters.RestaurantListAdapter;
import com.example.foodorderapp.model.RestaurantModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener {
    RecyclerView recyclerView;
    private RestaurantListAdapter restaurantListAdapter;
    private List<RestaurantModel> restaurantModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");

         restaurantModelList = getRestaurantData();

        initRecyclerView();
    }
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        restaurantListAdapter = new RestaurantListAdapter(restaurantModelList,this);
        recyclerView.setAdapter(restaurantListAdapter);

    }

    private List<RestaurantModel> getRestaurantData(){
        InputStream is = getResources().openRawResource(R.raw.restaurant);
        Writer writer =new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            int n;
            while(( n =reader.read(buffer)) != -1){
                writer.write(buffer,0,n);
            }

        }
        catch (Exception e){

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels = gson.fromJson(jsonStr,RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);
        return restList;
    }

    @Override
    public void onItemClick(RestaurantModel restaurantModel) {
        Intent intent = new Intent(MainActivity.this,RestaurantMenuActivity.class);
        intent.putExtra("RestaurantModel",restaurantModel);
        startActivity(intent);
    }
}