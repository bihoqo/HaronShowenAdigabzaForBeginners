package com.mastegoane.adigabzaforbeginners.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DialogPlusAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    public DialogPlusAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
//        ViewHolder viewHolder;
//        View view = convertView;
//
//        if (view == null) {
//            view = mLayoutInflater.inflate(R.layout.simple_list_item, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
//            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        Context context = parent.getContext();
//        switch (position) {
//            case 0:
//                viewHolder.textView.setText(context.getString(R.string.google_plus_title));
//                viewHolder.imageView.setImageResource(R.drawable.ic_google_plus_icon);
//                break;
//            case 1:
//                viewHolder.textView.setText(context.getString(R.string.google_maps_title));
//                viewHolder.imageView.setImageResource(R.drawable.ic_google_maps_icon);
//                break;
//            default:
//                viewHolder.textView.setText(context.getString(R.string.google_messenger_title));
//                viewHolder.imageView.setImageResource(R.drawable.ic_google_messenger_icon);
//                break;
//        }
//
//        return view;
    }

//    static class ViewHolder {
//        TextView textView;
//        ImageView imageView;
//    }
}
