package com.example.datn.Model;

public class DonHang {
    int maDH,soLuong,tongTien,trangthai,useName;
    String maSP,diachi,sdt,tinnhan,thoigian;

    public int getUseName() {
        return useName;
    }

    public void setUseName(int useName) {
        this.useName = useName;
    }

    public DonHang(int maDH, int soLuong, int tongTien, int trangthai, int useName, String maSP, String diachi, String sdt, String tinnhan, String thoigian) {
        this.maDH = maDH;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.trangthai = trangthai;
        this.useName = useName;
        this.maSP = maSP;
        this.diachi = diachi;
        this.sdt = sdt;
        this.tinnhan = tinnhan;
        this.thoigian = thoigian;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }



    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTinnhan() {
        return tinnhan;
    }

    public void setTinnhan(String tinnhan) {
        this.tinnhan = tinnhan;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
}
