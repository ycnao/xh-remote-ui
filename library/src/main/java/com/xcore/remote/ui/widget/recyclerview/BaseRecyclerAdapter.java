package com.xcore.remote.ui.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    //    private String TAG = "BaseRecyclerAdapter";
    private Context context;
    private List<T> dataList;
    private int layoutId;
    private RecyclerView recyclerView;
    private OnItemClickListener listener;//
    private OnItemLongClickListener longClickListener;//

    public interface OnItemClickListener {
        void onItemClick(BaseRecyclerHolder holder, RecyclerView parent, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseRecyclerHolder holder, RecyclerView parent, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void setData(List<T> list) {
        clearData();
        if (list != null) {
            dataList.addAll(list);
        }
    }

    public void refreshDate(List<T> mDatas) {
        this.dataList = mDatas;
        this.notifyDataSetChanged();
    }

    public void refreshItemDate(BaseRecyclerHolder holder, List<T> list, T mDatas, int index) {
        this.dataList = list;
        bindView(holder,mDatas,index);
        //notifyItemChanged(index,"refresh");
    }
    public void addData(List<T> list) {
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        if (list != null && list.size() > 0) {
            this.dataList.addAll(list);
        }
    }

    public void clearData() {
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        dataList.clear();
    }

    public List<T> getDataList() {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        return dataList;
    }

    public void insertItem(T item, int position) {
        dataList.add(position, item);
//        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        dataList.remove(position);
//        notifyItemRemoved(position+2);
    }
    public BaseRecyclerAdapter(Context context, List<T> dataList, int layoutId) {
        this.context = context;
        this.dataList = dataList;
        this.layoutId = layoutId;
    }
    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseRecyclerHolder(context, view);
    }
    @Override
    public void onBindViewHolder(final BaseRecyclerHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (listener != null && v != null && recyclerView != null) {
                int position1 = recyclerView.getChildAdapterPosition(v);
                listener.onItemClick(holder,recyclerView, v, position1);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null && v != null && recyclerView != null) {
                int position12 = recyclerView.getChildAdapterPosition(v);
                longClickListener.onItemLongClick(holder,recyclerView, v, position12);
                return true;
            }
            return false;
        });
        bindView(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    public abstract void bindView(BaseRecyclerHolder holder, T item, int position);

}
