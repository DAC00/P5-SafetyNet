package com.opcr.safetynet_alert.model;

import java.util.List;

public class FireStationCoverage {

    private int adultCount;

    private int childCount;

    private List<PersonAddressPhone> listPersonAddressPhoneList;

    public FireStationCoverage(int adultCount, int childCount, List<PersonAddressPhone> listPersonAddressPhoneList) {
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.listPersonAddressPhoneList = listPersonAddressPhoneList;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public List<PersonAddressPhone> getListPersonAddressPhoneList() {
        return listPersonAddressPhoneList;
    }

    public void setListPersonAddressPhoneList(List<PersonAddressPhone> listPersonAddressPhoneList) {
        this.listPersonAddressPhoneList = listPersonAddressPhoneList;
    }
}
