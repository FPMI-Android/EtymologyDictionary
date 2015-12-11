package com.fpmil.developer.dictionary.ety.entities;

/**
 * Created by THANHBINH on 11/13/2015.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="ety")
public class Word extends Model{

    @Column(name="Id")
    public long Id;
    @Column(name="origin")
    public String origin;
    @Column(name="definition")
    public String definition;
    @Column(name="favourite")
    public int favourite;
    @Override
    public String toString() {
        return origin;
    }
}