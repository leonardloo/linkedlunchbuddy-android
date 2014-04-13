package com.linkedlunchbuddy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkedlunchbuddy.R;

@SuppressWarnings("rawtypes")
public class ProfileListViewAdapter extends ArrayAdapter {
    Activity context;
    String[] items;
    String[] itemValues;
    boolean[] arrows;
    int layoutId;
    int textId;
    int imageId;
 
    @SuppressWarnings("unchecked")
	ProfileListViewAdapter(Activity context, int layoutId, int textId, int imageId, String[] items, String[] itemValues, boolean[] arrows)
    {
        super(context, layoutId, items);
 
        this.context = context;
        this.items = items;
        this.itemValues = itemValues;
        this.arrows = arrows;
        this.layoutId = layoutId;
        this.textId = textId;
        this.imageId = imageId;
    }
 
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(layoutId, null);
        TextView label=(TextView)row.findViewById(textId);
        TextView subtitle = (TextView)row.findViewById(R.id.profileSubtitle);
 
        label.setText(items[pos]);
        subtitle.setText(itemValues[pos]);
         
        if (arrows[pos])
        {
         ImageView icon=(ImageView)row.findViewById(imageId); 
            icon.setImageResource(R.drawable.ic_arrow);
            LayoutParams params = icon.getLayoutParams();
            // Might have to change according to size of screen
            params.width = 40;
            icon.setLayoutParams(params);
        }   
 
        return(row);
    }
}
