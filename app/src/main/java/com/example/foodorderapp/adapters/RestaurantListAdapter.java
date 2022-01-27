package com.example.foodorderapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.model.RestaurantModel;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<RestaurantModel> mRestaurantsList;
    private RestaurantListClickListener clickListener;

    public RestaurantListAdapter(List<RestaurantModel> list,RestaurantListClickListener clickListener) {
        this.mRestaurantsList = list;
        this.clickListener = clickListener;
    }
    public void updateData(List<RestaurantModel> list){
        this.mRestaurantsList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder holder, int position) {
        holder.restaurantName.setText(mRestaurantsList.get(position).getName());
        holder.restaurantAddress.setText("Address :"+mRestaurantsList.get(position).getAddress());
        holder.restaurantHours.setText("Today's hours :"+mRestaurantsList.get(position).getHours().getTodaysHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(mRestaurantsList.get(position));
            }
        });
        Glide.with(holder.thumbImage).load(mRestaurantsList.get(position).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return mRestaurantsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView restaurantName,restaurantAddress,restaurantHours;
        ImageView thumbImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantAddress = itemView.findViewById(R.id.restaurantAddress);
            restaurantHours = itemView.findViewById(R.id.restaurantHours);
            thumbImage = itemView.findViewById(R.id.thumbImage);
        }
    }

    public interface RestaurantListClickListener{
        public void onItemClick(RestaurantModel restaurantModel);
    }
}
