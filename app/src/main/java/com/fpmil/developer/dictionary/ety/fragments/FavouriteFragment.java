package com.fpmil.developer.dictionary.ety.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fpmil.developer.dictionary.ety.Controller;
import com.fpmil.developer.dictionary.ety.MeaningActivity;
import com.fpmil.developer.dictionary.ety.R;
import com.fpmil.developer.dictionary.ety.adapter.WordAdapter;
import com.fpmil.developer.dictionary.ety.entities.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THANHBINH on 11/13/2015.
 */
public class FavouriteFragment extends Fragment {
    private ListView listView;
    final static Controller c = new Controller();
    private WordAdapter adapter = null;
    private ArrayList<Word> words;

    public FavouriteFragment() {
        // Required empty public constructor
        init();
    }
    private void init(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new ItemClickHandler());
        updateListView();

        final Button btn_refresh = (Button)view.findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListView();
            }
        });

        return view;
    }
    private void updateListView(){
        words = c.getFavourites();
        adapter = new WordAdapter(getActivity(), words);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private class ItemClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            // TODO Auto-generated method stub
            Word word = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), MeaningActivity.class);
            Bundle b = new Bundle();
            b.putLong("id",word.getId());
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}