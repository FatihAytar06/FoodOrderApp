package com.example.foodorderapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.model.Menu;
import com.example.foodorderapp.model.RestaurantModel;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    private List<Menu> menuList;
    private MenuListClickListener clickListener;

    public MenuListAdapter(List<Menu> menuList, MenuListClickListener clickListener) {
        this.menuList = menuList;
        this.clickListener = clickListener;
    }
    public void updateData(List<Menu> list){
        this.menuList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price : $"+String.valueOf(menuList.get(position).getPrice()));
        holder.addToCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menuList.get(position);
                menu.setTotalInCart(1);
                clickListener.OnAddToCartClickListener(menu);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCardButton.setVisibility(View.GONE);
                holder.tvCount.setText(menu.getTotalInCart()+"");


            }
        });
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menuList.get(position);
                int total = menu.getTotalInCart();
                total--;
                if (total>0){
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total+"");
                }
                else{
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCardButton.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                    clickListener.onRemoveFromCartClick(menu);

                }

            }
        });
        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menuList.get(position);
                int total = menu.getTotalInCart();
                total++;
                if (total<=10){
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total+"");
                }
                else {
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCardButton.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                    clickListener.onRemoveFromCartClick(menu);

                }





            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.OnAddToCartClickListener(menuList.get(position));
            }
        });
        Glide.with(holder.thumbImage).load(menuList.get(position).getUrl()).into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView menuName,menuPrice,addToCardButton,tvCount;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        LinearLayout addMoreLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            menuPrice = itemView.findViewById(R.id.menuPrice);
            addToCardButton = itemView.findViewById(R.id.addToCardButton);
            thumbImage = itemView.findViewById(R.id.thumbImage);
            imageMinus = itemView.findViewById(R.id.imageMinus);
            imageAddOne = itemView.findViewById(R.id.imageAddOne);
            tvCount = itemView.findViewById(R.id.tvCount);

            addMoreLayout = itemView.findViewById(R.id.addMoreLayout);
        }
    }

    public interface MenuListClickListener{
        public void OnAddToCartClickListener(Menu menu);
        public void onUpdateCartClick(Menu menu);
        public void onRemoveFromCartClick(Menu menu);
    }
}
