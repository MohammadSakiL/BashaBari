package com.example.bashabari;

public class BillInfo {
    private String month,homeRent,waterBill,gasBill,otherBill,totalBill,phoneNumber;

    public BillInfo() {
    }

    public BillInfo(String month, String homeRent, String waterBill, String gasBill, String otherBill, String totalBill, String phoneNumber) {
        this.month = month;
        this.homeRent = homeRent;
        this.waterBill = waterBill;
        this.gasBill = gasBill;
        this.otherBill = otherBill;
        this.totalBill = totalBill;
        this.phoneNumber = phoneNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHomeRent() {
        return homeRent;
    }

    public void setHomeRent(String homeRent) {
        this.homeRent = homeRent;
    }

    public String getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(String waterBill) {
        this.waterBill = waterBill;
    }

    public String getGasBill() {
        return gasBill;
    }

    public void setGasBill(String gasBill) {
        this.gasBill = gasBill;
    }

    public String getOtherBill() {
        return otherBill;
    }

    public void setOtherBill(String otherBill) {
        this.otherBill = otherBill;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(String totalBill) {
        this.totalBill = totalBill;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
