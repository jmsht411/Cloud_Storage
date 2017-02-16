package com.example.fcy.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fcy on 17-2-12.
 */

public class DocumentInfoAdapter extends ArrayAdapter<DirectoryInfo> {
    private int resourceId;

    public DocumentInfoAdapter(Context context, int resource, int textViewResourceId, List<DirectoryInfo> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DirectoryInfo directoryInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView documentImage =(ImageView) view.findViewById(R.id.directory_image);
        TextView documentName = (TextView) view.findViewById(R.id.directory_name);
        TextView modifyTime = (TextView) view.findViewById(R.id.modify_time);
        documentImage.setImageResource(directoryInfo.getM_iconName());
        documentName.setText(directoryInfo.getM_fileName());
        modifyTime.setText(directoryInfo.getM_fileModifyTime());
        return view;
    }
}
