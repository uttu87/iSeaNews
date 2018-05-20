package com.iseasoft.iseanews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iseasoft.iseanews.entity.MenuEntity;
import com.iseasoft.iseanews.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

    private AdapterListener listener;
    private List<MenuEntity> menuEntityList;

    public MenuAdapter(List<MenuEntity> menuEntityList, AdapterListener listener){
        this.menuEntityList = menuEntityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_menu, null);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MenuViewHolder menuViewHolder = (MenuViewHolder) holder;

        final MenuEntity menuEntity = menuEntityList.get(position);
        String title = menuEntity.getTitle();

        menuViewHolder.tvItemMenu.setText(title);

        if(menuEntity.isSelected()){
            menuViewHolder.rlItemMenu.setBackgroundResource(R.color.colorPrimary);
        } else {
            menuViewHolder.rlItemMenu.setBackgroundResource(android.R.color.white);
        }

        menuViewHolder.rlItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClickListener(menuEntity, position, menuViewHolder);
                }
            }
        });

    }

    private class MenuViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rlItemMenu;
        TextView tvItemMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);

            rlItemMenu = (RelativeLayout) itemView.findViewById(R.id.rl_item_menu);
            tvItemMenu = (TextView) itemView.findViewById(R.id.tv_item_menu);
        }
    }

    @Override
    public int getItemCount() {
        return menuEntityList.size();
    }
}
