package com.example.datn.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.adapter.ListImageDetailAdapter;
import com.example.datn.model.ResultPopular;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class FragmentDetailApartment extends Fragment implements OnMapReadyCallback {
    Bundle bundle = new Bundle();
    private GoogleMap mMap;
    MapView mapView;
    ResultPopular resultPopular;
    TextView tv_detail_apartment_createBy, tv_detail_apartment_name, tv_detail_apartment_address,
            tv_detail_apartment_desciption, tv_detail_apartment_price, tv_detail_apartment_bed,
            tv_detail_apartment_shower, tv_detail_apartment_square, tv_detail_square_result, tv_detail_year_result;
    ImageView image_detail_apartment;
    RecyclerView rcv_listimage_detail;
    Button btn_detail_contact;
    ArrayList<String> listImage;
    ListImageDetailAdapter detailAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_apartment, container, false);
        bundle.putString("Callback", "Home");
        resultPopular = (ResultPopular) getArguments().getSerializable("dataApartment");
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map_apartment);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentDetailApartment.this).navigate(R.id.action_fragmentIndex_to_fragmentDaddy, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;
    }

    public void initView(View view) {
        tv_detail_apartment_address = view.findViewById(R.id.tv_detail_apartment_address);
        tv_detail_apartment_createBy = view.findViewById(R.id.tv_detail_apartment_createby);
        tv_detail_apartment_desciption = view.findViewById(R.id.tv_detail_apartment_desciption);
        tv_detail_apartment_name = view.findViewById(R.id.tv_detail_apartment_name);
        tv_detail_apartment_price = view.findViewById(R.id.tv_detail_apartment_price);
        image_detail_apartment = view.findViewById(R.id.image_detail_apartment);
        tv_detail_apartment_bed = view.findViewById(R.id.tv_detail_apartment_bed);
        tv_detail_apartment_shower = view.findViewById(R.id.tv_detail_apartment_shower);
        tv_detail_apartment_square = view.findViewById(R.id.tv_detail_apartment_square);
        btn_detail_contact = view.findViewById(R.id.btn_detail_contact);
        rcv_listimage_detail = view.findViewById(R.id.rcv_detail_listimage);
        tv_detail_square_result = view.findViewById(R.id.tv_detail_square);
        tv_detail_year_result = view.findViewById(R.id.tv_detail_year);
    }

    public void initData() {
        if (resultPopular.getCreateBy().equals("admin")) {
            tv_detail_apartment_createBy.setText("King Mall");
        } else {
            tv_detail_apartment_createBy.setText("Shoper");
        }
        tv_detail_apartment_address.setText(resultPopular.getAddress());
        tv_detail_apartment_desciption.setText(resultPopular.getDescription());
        tv_detail_apartment_name.setText(resultPopular.getName());
        DecimalFormat formatter = new DecimalFormat("#,##");
        double formatPrice = Double.parseDouble(resultPopular.getPrice()) / 10000;
        tv_detail_apartment_price.setText(formatter.format(formatPrice) + "tr");
        Glide.with(getActivity()).load(resultPopular.getPhotos().get(0)).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.ic_error_img).into(image_detail_apartment);
        tv_detail_apartment_bed.setText(resultPopular.getSumBedroom() + " Gi?????ng");
        tv_detail_apartment_shower.setText(resultPopular.getSumToilet() + " Nh?? t???m");
        tv_detail_apartment_square.setText(resultPopular.getSqrt() + "m2");
        tv_detail_square_result.setText(resultPopular.getSqrt() + "m2");
        ArrayList<Integer> listYear = new ArrayList<>();
        for (int i = 2012; i<2022;i++){
            listYear.add(i);
        }
        Random random = new Random();
        int randomYear = random.nextInt(listYear.size());
        tv_detail_year_result.setText(listYear.get(randomYear)+"");
        btn_detail_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+resultPopular.getContactPhoneNumber()));
                startActivity(intent);
            }
        });
        listImage = new ArrayList<String>();
        for (int i = 0; i < resultPopular.getPhotos().size(); i++) {
            listImage.add(resultPopular.getPhotos().get(i));
        }
        Log.i("TAG", "sizeImage: " + resultPopular.getPhotos().size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(0);
        rcv_listimage_detail.setLayoutManager(layoutManager);
        detailAdapter = new ListImageDetailAdapter(getActivity(), resultPopular.getPhotos());
        rcv_listimage_detail.setAdapter(detailAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        Log.i("TAG", "onMapReady: " + resultPopular.getLongitude() + resultPopular.getLatitude());
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        resultPopular = (ResultPopular) getArguments().getSerializable("dataApartment");
        LatLng locationApartment = new LatLng(Double.parseDouble(resultPopular.getLatitude()), Double.parseDouble(resultPopular.getLongitude()));
        MarkerOptions marker = new MarkerOptions().title(resultPopular.getName());
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_location_maker);
        marker.icon(BitmapDescriptorFactory.fromBitmap(bitmapIcon));
        googleMap.addMarker(marker.position(locationApartment));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationApartment));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
