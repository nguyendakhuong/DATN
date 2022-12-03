package com.example.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.ItemClickListener;
import com.example.datn.R;
import com.example.datn.model.ListRecyclerApartmentHome;
import com.example.datn.model.ResultApartment;

import java.util.List;

public class ListOfListHomeApdapter extends RecyclerView.Adapter<ListOfListHomeApdapter.ViewHolder> {
    private Context context;
    private List<ListRecyclerApartmentHome> listRecycleViews;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Double userLongitude,userLatitude;
    public ListOfListHomeApdapter(Context context, List<ListRecyclerApartmentHome> listRecycleViews,
                                  Double userLongitude,Double userLatitude) {
        this.context = context;
        this.listRecycleViews = listRecycleViews;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_home_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListRecyclerApartmentHome listRecyclerApartmentHome = listRecycleViews.get(position);
        holder.tv_title_list.setText(listRecyclerApartmentHome.getListTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(listRecyclerApartmentHome.getListresult().size());
        ApartmentItemHomeAdapter apartmentItemHomeAdapter = new ApartmentItemHomeAdapter(
                context, listRecyclerApartmentHome.getListresult(), userLongitude, userLatitude,
                listRecyclerApartmentHome.getViewType());
        holder.rcv_listoflist.setLayoutManager(layoutManager);
        holder.rcv_listoflist.setAdapter(apartmentItemHomeAdapter);
        holder.rcv_listoflist.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        if (this.listRecycleViews != null) {
            return this.listRecycleViews.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rcv_listoflist;
        TextView tv_title_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rcv_listoflist = itemView.findViewById(R.id.rcv_listoflist);
            tv_title_list = itemView.findViewById(R.id.tv_title_list);
        }
    }
    {
    }
}