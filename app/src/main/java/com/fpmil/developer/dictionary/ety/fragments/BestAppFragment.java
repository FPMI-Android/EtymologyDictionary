package com.fpmil.developer.dictionary.ety.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.fpmil.developer.dictionary.ety.Config;
import com.fpmil.developer.dictionary.ety.Controller;
import com.fpmil.developer.dictionary.ety.MeaningActivity;
import com.fpmil.developer.dictionary.ety.R;
import com.fpmil.developer.dictionary.ety.adapter.AppAdapter;
import com.fpmil.developer.dictionary.ety.entities.App;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by THANHBINH on 11/13/2015.
 */
public class BestAppFragment extends Fragment {
    private ListView listView;
    final static Controller c = new Controller();
    private AppAdapter adapter = null;

    private ProgressBar progressBar;
    private LoadApp myTask;

    public BestAppFragment() {
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
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_moreapps);

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
        myTask = new LoadApp();
        myTask.execute();
    }
    private class ItemClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            // TODO Auto-generated method stub
            App app = adapter.getItem(position);

            final String appPackageName = app.url; // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        myTask.cancel(true);
        super.onDestroy();
    }
    class LoadApp extends AsyncTask<Void, Void, ArrayList<App>> {
        private boolean success = false;
        @Override
        protected ArrayList<App> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<App> result = new ArrayList<App>();
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(Config.URL_MOREAPPS).build();
                Response response = client.newCall(request).execute();
                Reader reader = response.body().charStream();
                Gson GSON = new Gson();
                App apps[] = GSON.fromJson(reader, App[].class);
                for (int i = 0; i < apps.length; i++) {
                    result.add(apps[i]);
                }
                success = true;
            } catch (Exception e) {
                // TODO: handle exception
                success = false;
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<App> result) {
            // TODO Auto-generated method stub
            if(success){
                adapter = new AppAdapter(getActivity(), result);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}