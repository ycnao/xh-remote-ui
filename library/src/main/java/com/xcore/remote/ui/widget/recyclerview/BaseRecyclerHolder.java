package com.xcore.remote.ui.widget.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private Context context;
    private SparseArray<View> views;


    public BaseRecyclerHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        views = new SparseArray<>();
    }

    public SparseArray<View> getViews() {
        return views;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public BaseRecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(drawableId);
        return this;
    }

    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public BaseRecyclerHolder setImageByUrl(int viewId, String url) {
        return this;
    }
}
