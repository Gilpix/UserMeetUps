package com.kulartist.events;




public class Events {
    private String evt_id;
    private String name;
    private String location;
    private String sTime;

    public Events(String evt_id, String name, String location, String sTime, String eTime, int uId) {
        this.evt_id = evt_id;
        this.name = name;
        this.location = location;
        this.sTime = sTime;
        this.eTime = eTime;
        this.uId = uId;
    }


    public String getEvt_id() {
        return evt_id;
    }

    public void setEvt_id(String evt_id) {
        this.evt_id = evt_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    private String eTime;
    private int uId;



}