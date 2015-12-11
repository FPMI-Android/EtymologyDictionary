package com.fpmil.developer.dictionary.ety.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpmil.developer.dictionary.ety.Controller;
import com.fpmil.developer.dictionary.ety.EndlessListView;
import com.fpmil.developer.dictionary.ety.MainActivity;
import com.fpmil.developer.dictionary.ety.R;
import com.fpmil.developer.dictionary.ety.adapter.WordAdapter;
import com.fpmil.developer.dictionary.ety.entities.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THANHBINH on 11/13/2015.
 */
public class VocabularyFragment extends Fragment {
    private EndlessListView endlessListView;
    final static Controller c = new Controller();
    private WordAdapter adapter = null;
    private ArrayList<Word> vocabs;
    private TextView textSearch;
    private int count = 0;
    private final int ITEMS_SHOW = 30;

    private int mCount;
    private boolean mHaveMoreDataToLoad;

    public VocabularyFragment() {
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
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);

        mCount = 0;
        mHaveMoreDataToLoad = true;
        vocabs = c.getItems();
        adapter = new WordAdapter(getActivity(), vocabs);
        endlessListView = (EndlessListView) view.findViewById(R.id.lvDemo);
        endlessListView.setAdapter(adapter);
        endlessListView.setOnLoadMoreListener(loadMoreListener);

        endlessListView.setOnItemClickListener(new ItemClickHandler());
        textSearch = (TextView)view.findViewById(R.id.textSearch);
        textSearch.addTextChangedListener(new EditextEventHandler());

        mCount = 0;
        mHaveMoreDataToLoad = true;

        return view;
    }
    private class ItemClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            // TODO Auto-generated method stub
            Word word = adapter.getItem(position);
            MainActivity activity = (MainActivity)getActivity();
            activity.openMeaning(word);
        }
    }
    private void updateListView(){
        String text = textSearch.getText().toString();
        vocabs = c.search(text);
        adapter.clear();
        adapter.addAll(vocabs);
        adapter.notifyDataSetChanged();
    }
    private class EditextEventHandler implements TextWatcher{
        final long DELAY = 50;
        @Override
        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            // When user changed the Text
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mCount = 0;
                    updateListView();
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
        }
    }
    private void loadMoreData() {
        new LoadMore().execute((Void) null);
    }

    private EndlessListView.OnLoadMoreListener loadMoreListener = new EndlessListView.OnLoadMoreListener() {

        @Override
        public boolean onLoadMore() {
            if (true == mHaveMoreDataToLoad) {
                loadMoreData();
            } else {
                Toast.makeText(getActivity(), "No more data to load",
                        Toast.LENGTH_SHORT).show();
            }

            return mHaveMoreDataToLoad;
        }
    };

    private class LoadMore extends AsyncTask<Void, Void, List<Word>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCount++;
        }

        @Override
        protected List<Word> doInBackground(Void... params) {
            List<Word> list = c.search(textSearch.getText().toString(),mCount * ITEMS_SHOW, ITEMS_SHOW);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Word> result) {
            super.onPostExecute(result);

            adapter.addItems(result);
            endlessListView.loadMoreCompleat();
            mHaveMoreDataToLoad = mCount < 10;
        }
    }
}
