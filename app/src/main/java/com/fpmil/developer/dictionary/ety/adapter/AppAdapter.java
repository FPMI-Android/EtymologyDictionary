package com.fpmil.developer.dictionary.ety.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpmil.developer.dictionary.ety.R;
import com.fpmil.developer.dictionary.ety.entities.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THANHBINH on 12/5/2015.
 */
public class AppAdapter extends ArrayAdapter<App> {
    private List<App> apps;
    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView description;
    }
    public AppAdapter(Context context, ArrayList<App> apps) {
        super(context, R.layout.word_item, apps);
        this.apps = apps;
    }
    public void addItems(List<App> newItems) {
        if (null == newItems || newItems.size() <= 0) {
            return;
        }

        if (null == apps) {
            apps = new ArrayList<App>();
        }

        apps.addAll(newItems);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        App app = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.app_item, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.app_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.app_name);
            viewHolder.description = (TextView) convertView.findViewById(R.id.app_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.image.setBackgroundResource(app.idImage);
        viewHolder.name.setText(app.name);
        viewHolder.description.setText(app.description);
        // Return the completed view to render on screen
        return convertView;
    }
}