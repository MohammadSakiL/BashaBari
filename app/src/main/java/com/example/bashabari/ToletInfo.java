package com.example.bashabari;

public class ToletInfo {
    private String image,price,type,flat_no,location,contact_number,key;

    public ToletInfo() {
    }

    public ToletInfo(String image, String price, String type, String flat_no, String location, String contact_number, String key) {
        this.image = image;
        this.price = price;
        this.type = type;
        this.flat_no = flat_no;
        this.location = location;
        this.contact_number = contact_number;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
