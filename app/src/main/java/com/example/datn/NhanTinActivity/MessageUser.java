package com.example.datn.NhanTinActivity;

import java.util.Date;

public class MessageUser {
    private String mText;
    private String mSender;
    private Date mDate;
    private String mDateString;

    public MessageUser() {

    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmDateString() {
        return mDateString;
    }

    public void setmDateString(String mDateString) {
        this.mDateString = mDateString;
    }

    public MessageUser(String mText, String mSender, Date mDate, String mDateString) {
        this.mText = mText;
        this.mSender = mSender;
        this.mDate = mDate;
        this.mDateString = mDateString;
    }
}
