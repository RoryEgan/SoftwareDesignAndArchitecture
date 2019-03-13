package com.cs4125.bikerentalapp.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs4125.bikerentalapp.R;
import com.cs4125.bikerentalapp.model.entity.AbstractBikeInfo;
import com.cs4125.bikerentalapp.model.entity.BikeInfo;
import com.cs4125.bikerentalapp.model.entity.LocationKnown;
import com.cs4125.bikerentalapp.model.entity.LocationUnknown;

public class BikeInformationFragment extends Fragment {

    private int id;
    private String type;
    private String node;
    private String position;
    private AbstractBikeInfo bikeInfo;

    public BikeInformationFragment() {
    }

    @SuppressLint("ValidFragment")
    public BikeInformationFragment(int ID, String type, String node, String position) {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bike_information, container, false);

        configureUiItems(v);
        return v;
    }

    private void configureUiItems(View v){

        this.id =getArguments().getInt("id");
        this.type=getArguments().getString("type");
        this.node=getArguments().getString("node");
        this.position=getArguments().getString("position");

        int n;
        if(node.contains("."))
            n = Integer.parseInt(node.substring(0,node.indexOf(".")));
        else
            n = Integer.parseInt(node);
        if(n>0){
            bikeInfo = new BikeInfo(type,node,new LocationKnown());
        }
        else{
            bikeInfo = new BikeInfo(type,position,new LocationUnknown());
        }

        TextView textArea;
        textArea = v.findViewById(R.id.textArea);
        textArea.setText(bikeInfo.showMessage());
    }
}