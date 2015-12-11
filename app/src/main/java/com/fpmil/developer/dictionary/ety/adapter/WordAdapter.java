package com.fpmil.developer.dictionary.ety.adapter;

/**
 * Created by THANHBINH on 11/13/2015.
 */

import java.util.ArrayList;
import java.util.List;

import com.fpmil.developer.dictionary.ety.R;
import com.fpmil.developer.dictionary.ety.entities.Word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WordAdapter extends ArrayAdapter<Word>{
    private List<Word> words;
    private static class ViewHolder {
        TextView origin;
    }
    public WordAdapter(Context context, ArrayList<Word> words) {
        super(context, R.layout.word_item, words);
        this.words = words;
    }
    public void addItems(List<Word> newItems) {
        if (null == newItems || newItems.size() <= 0) {
            return;
        }

        if (null == words) {
            words = new ArrayList<Word>();
        }

        words.addAll(newItems);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Word vocab = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.word_item, parent, false);
            viewHolder.origin = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.origin.setText(vocab.origin);
        // Return the completed view to render on screen
        return convertView;
    }
}