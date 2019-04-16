package com.cs4125.bikerentalapp.view;


import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4125.bikerentalapp.R;
import com.cs4125.bikerentalapp.model.commands.Command;
import com.cs4125.bikerentalapp.model.commands.Rent;
import com.cs4125.bikerentalapp.model.commands.Return;
import com.cs4125.bikerentalapp.model.entity.RentReturnDetails;
import com.cs4125.bikerentalapp.model.invokers.Invoker;
import com.cs4125.bikerentalapp.model.receivers.Bike;
import com.cs4125.bikerentalapp.model.receivers.Vehicle;
import com.cs4125.bikerentalapp.repository.BikeRepository;
import com.cs4125.bikerentalapp.sl.ServiceLocator;
import com.cs4125.bikerentalapp.viewmodel.RentViewModel;
import com.cs4125.bikerentalapp.viewmodel.ReturnViewModel;
import com.cs4125.bikerentalapp.web.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {
    private String input = "";
    private Vehicle vehicle;
    private int rentOrReturn;
    private String vehicleType;
    private int vehicleId;
    private RentReturnDetails rentReturnDetails;
    private Invoker invoker;

    public ConfirmationFragment() {
    }

    @SuppressLint("ValidFragment")
    public ConfirmationFragment(int ROrR, String in) {
        rentOrReturn = ROrR;
        input = in;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_confirm_rent, container, false);
        configureUiItems(v);

        return v;
    }

    private void configureUiItems(View v){

        createInvoker();

        TextView idText;
        TextView typeText;
        Button confirm = v.findViewById(R.id.confirmBtn);
        idText = v.findViewById(R.id.idText);
        typeText = v.findViewById(R.id.typeText);
        idText.setText("Vehicle ID: " + vehicleId);
        typeText.setText("Vehicle Type: "+ vehicleType);
        confirm.setOnClickListener(view1 -> setBikeStatus());
    }

    public void createInvoker(){
        String[] data = input.split(",");
        getDetails(data);
        makeVehicle();

        Command command;
        if(rentOrReturn==1){
            command = new Rent(vehicle);
            invoker = new Invoker(command);
        }else{
            command = new Return(vehicle);
            invoker = new Invoker(command);
        }
    }

    private void makeVehicle(){
        //Can extend this later to accommodate more vehicle types
        vehicle = new Bike(rentReturnDetails, ServiceLocator.get(BikeRepository.class));
    }

    public void getDetails(String[] data){
            vehicleId = Integer.parseInt(data[0]);
            vehicleType = data[7];
            rentReturnDetails = new RentReturnDetails
                    .Builder()
                    .setVehicleId(Integer.parseInt(data[0]))
                    .setUserId(27)
                    .setStudentCardId(Integer.parseInt(data[1]) )
                    .setOrderId(Integer.parseInt(data[2]))
                    .setLatitude(Integer.parseInt(data[3]))
                    .setLongitude(Integer.parseInt(data[4]))
                    .setAmountPaid(Integer.parseInt(data[5]))
                    .setNodeId(Integer.parseInt(data[6]))
                    .build();
    }



    private void setBikeStatus(){
        LiveData<ResponseBody> liveResponse =  invoker.executeCommand();
        liveResponse.observe(this, this::observeResponse);
    }

    private void observeResponse(@Nullable ResponseBody response){
        if(response == null) {
            System.out.println("NULL_RESPONSE");
        }

        if(response != null) {
            if (response.getResponseCode() == 200) {
                showToast("rentBike/returnBike Successful");
            } else {
                showToast("rentBike/returnBike Failed");
            }
        }
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}