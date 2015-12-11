package com.fpmil.developer.dictionary.ety;

/**
 * Created by THANHBINH on 11/13/2015.
 */

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Select;
import com.fpmil.developer.dictionary.ety.entities.Word;

public class Controller {
    public ArrayList<Word> getItems()
    {
        List<Word> vocabs = new Select().from(Word.class).orderBy("origin asc limit 30").execute();
        return (ArrayList<Word>) vocabs;
    }
    public ArrayList<Word> getFavourites()
    {
        List<Word> vocabs = new Select().from(Word.class).where("favourite=1").orderBy("origin asc limit 30").execute();
        return (ArrayList<Word>) vocabs;
    }
    public ArrayList<Word> search(String query)
    {
        List<Word> vocabs = new Select().from(Word.class).where("origin like '" + query+ "%'").orderBy("origin asc limit 30").execute();
        return (ArrayList<Word>)vocabs;
    }
    public ArrayList<Word> search(String query,int from, int end)
    {
        List<Word> vocabs = new Select().from(Word.class).where("origin like '" + query+ "%'").orderBy("origin asc limit "+from+","+end).execute();
        return (ArrayList<Word>)vocabs;
    }

    public Word getItem(long id)
    {
        return new Select()
                .from(Word.class)
                .where("Id = ?", id)
                .executeSingle();
    }
}