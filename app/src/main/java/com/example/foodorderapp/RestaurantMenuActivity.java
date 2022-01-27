package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapp.adapters.MenuListAdapter;
import com.example.foodorderapp.model.Menu;
import com.example.foodorderapp.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    private RecyclerView recyclerView;
    private MenuListAdapter menuListAdapter;
    private List<Menu> menuList;
    private List<Menu> itemsInCartList;
    private int totalItemsInCart =0;
    TextView buttonCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        RestaurantModel restaurantModel = getIntent().getParcelableExtra("RestaurantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurantModel.getName());
        actionBar.setSubtitle(restaurantModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        menuList = restaurantModel.getMenus();
        initRecyclerView();

        buttonCheckOut = findViewById(R.id.buttunCheckOut);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemsInCartList !=null && itemsInCartList.size() <= 0){
                    Toast.makeText(RestaurantMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurantModel.setMenus(itemsInCartList);
                Intent i = new Intent(RestaurantMenuActivity.this,PlaceYourOrderActivity.class);
                i.putExtra("RestaurantModel",restaurantModel);
                startActivityForResult(i,1000);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        menuListAdapter = new MenuListAdapter(menuList,this);
        recyclerView.setAdapter(menuListAdapter);
    }


    @Override
    public void OnAddToCartClickListener(Menu menu) {
        if (itemsInCartList==null)
            itemsInCartList = new ArrayList<>();

        itemsInCartList.add(menu);
        totalItemsInCart = 0;

        for (Menu m: itemsInCartList){
            totalItemsInCart = totalItemsInCart+menu.getTotalInCart();
        }

        buttonCheckOut.setText("Checkout ("+totalItemsInCart+") items");

    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)){
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index,menu);

            totalItemsInCart = 0;

            for (Menu m: itemsInCartList){
                totalItemsInCart = totalItemsInCart+menu.getTotalInCart();
            }

            buttonCheckOut.setText("Checkout ("+totalItemsInCart+") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)){

            itemsInCartList.remove(menu);

            totalItemsInCart = 0;

            for (Menu m: itemsInCartList){
                totalItemsInCart = totalItemsInCart+menu.getTotalInCart();
            }

            buttonCheckOut.setText("Checkout ("+totalItemsInCart+") items");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                //do nothing

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1000 && resultCode == RESULT_OK){
            finish();
        }
    }
}