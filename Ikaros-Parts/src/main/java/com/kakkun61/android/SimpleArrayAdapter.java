package com.kakkun61.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SimpleArrayAdapter<T> extends ArrayAdapter<T> {
    private final LayoutInflater mInflater;
    private int mResource;
    private int mDropDownResource;
    private Mapper<T> mMapper;

    public SimpleArrayAdapter(Context context, int resource, T[] objects, Mapper<T> mapper) {
        super(context, resource, objects);
        mResource = mDropDownResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMapper = mapper;
    }

    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
        mDropDownResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mDropDownResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View view;
        TextView text;

        if (convertView == null) {
            view = mInflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        T item = getItem(position);
        mMapper.map(item, view);

        return view;
    }

    public static interface Mapper<T> {
        void map(T item, View view);
    }
}
