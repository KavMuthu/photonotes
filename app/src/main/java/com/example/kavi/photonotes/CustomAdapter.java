package com.example.kavi.photonotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Kavi on 2/3/16.
 */
public class CustomAdapter extends ArrayAdapter<PhotoCaption> {

    Context context;
    private final List<PhotoCaption> captions;


    public CustomAdapter(Context context, int layout, List<PhotoCaption> captions) {
        super(context, R.layout.row_layout, captions);
        this.context = context;
        this.captions = captions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoCaption photoCaption = captions.get(position);
        //the rwo layout is inflated
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_layout, parent, false);

        //caption
        TextView textView = (TextView) view.findViewById(R.id.captionText);
        textView.setText(photoCaption.getCaption());
        this.notifyDataSetChanged();

        return view;

    }

}
