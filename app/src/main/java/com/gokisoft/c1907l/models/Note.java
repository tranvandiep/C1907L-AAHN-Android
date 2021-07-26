package com.gokisoft.c1907l.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Diep.Tran on 7/26/21.
 */

public class Note {
    private int _id;
    private String noidung;
    private boolean quantrong;
    private Date ngaytao;

    public Note() {
    }

    public Note(int _id, String noidung, boolean quantrong, Date ngaytao) {
        this._id = _id;
        this.noidung = noidung;
        this.quantrong = quantrong;
        this.ngaytao = ngaytao;
    }

    public Note(int _id, String noidung, int quantrong, String ngaytao) {
        this._id = _id;
        this.noidung = noidung;
        this.quantrong = (quantrong == 1)?true:false;
        setNgayTaoByString(ngaytao);
    }

    public Note(String noidung, boolean quantrong, Date ngaytao) {
        this.noidung = noidung;
        this.quantrong = quantrong;
        this.ngaytao = ngaytao;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public boolean isQuantrong() {
        return quantrong;
    }

    public void setQuantrong(boolean quantrong) {
        this.quantrong = quantrong;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getStrDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return simpleDateFormat.format(ngaytao);
    }

    public void setNgayTaoByString(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            ngaytao = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
