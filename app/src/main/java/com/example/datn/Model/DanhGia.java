package com.example.datn.Model;

public class DanhGia {
    private int iD;
    private int maSV;
    private int danhGiaUser;
    private String nhanXet;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public int getDanhGiaUser() {
        return danhGiaUser;
    }

    public void setDanhGiaUser(int danhGiaUser) {
        this.danhGiaUser = danhGiaUser;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public DanhGia(int iD, int maSV, int danhGiaUser, String nhanXet) {
        this.iD = iD;
        this.maSV = maSV;
        this.danhGiaUser = danhGiaUser;
        this.nhanXet = nhanXet;
    }
}
