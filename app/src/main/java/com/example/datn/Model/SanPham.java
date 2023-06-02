package com.example.datn.Model;


public class SanPham {

    private String maSP ;
    private String maTL ;
    private String tenSP ;
    private String anhSP ;
    private float donGia;
    private int soLuong ;
    private String matoSP;
    private int xetDuyet;
    private int maSV;

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaTL() {
        return maTL;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMatoSP() {
        return matoSP;
    }

    public void setMatoSP(String matoSP) {
        this.matoSP = matoSP;
    }

    public int getXetDuyet() {
        return xetDuyet;
    }

    public void setXetDuyet(int xetDuyet) {
        this.xetDuyet = xetDuyet;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public SanPham(String maSP, String maTL, String tenSP, String anhSP, float donGia, int soLuong, String matoSP, int xetDuyet, int maSV) {
        this.maSP = maSP;
        this.maTL = maTL;
        this.tenSP = tenSP;
        this.anhSP = anhSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.matoSP = matoSP;
        this.xetDuyet = xetDuyet;
        this.maSV = maSV;
    }

    public SanPham(){}



}
