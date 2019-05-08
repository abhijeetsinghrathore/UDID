package com.example.abhijeetsingh.udid;

public class USER {

    public String mfirstname;
    public String mlastname;
    public String mdob;
    public String mState;
    public String mCity;
    public String maddress;

    public String mPhone;
    public String mToken;


    public USER()
    {}

    public USER(String firstname,String lastname,String dob,String state,String city, String address,String phone,String token)
    {
        this.mfirstname=firstname;
        this.mlastname=lastname;
        this.mState=state;
        this.mCity=city;
        this.maddress=address;
        this.mdob=dob;
        this.mPhone=phone;
        this.mToken=token;
    }
}
