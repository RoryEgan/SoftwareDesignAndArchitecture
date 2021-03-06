package com.cs4125.bikerentalapp.model.db_entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Node {
    @PrimaryKey
    public int nodeId;
    public double longitude;
    public double latitude;

    public Node(){

    }

    public Node(int nodeId, double latitude, double longitude){
        this.nodeId = nodeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
