package com.example.foodorderapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.model.Menu;

import java.util.List;

public class PlaceYourOrderAdapter extends RecyclerView.Adapter<PlaceYourOrderAdapter.ViewHolder> {
    private List<Menu> menuList;

    public PlaceYourOrderAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }
    public void updateData(List<Menu> list){
        this.menuList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceYourOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order_recycler_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceYourOrderAdapter.ViewHolder holder, int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price :"+String.valueOf(menuList.get(position).getPrice()));
        holder.menuQty.setText("Qty : "+menuList.get(position).getTotalInCart());

        Glide.with(holder.thumbImage).load(menuList.get(position).getUrl()).into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView menuName,menuPrice,menuQty;
        ImageView thumbImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            menuPrice = itemView.findViewById(R.id.menuPrice);
            menuQty = itemView.findViewById(R.id.menuQty);
            thumbImage = itemView.findViewById(R.id.thumbImage);
        }
    }
}
