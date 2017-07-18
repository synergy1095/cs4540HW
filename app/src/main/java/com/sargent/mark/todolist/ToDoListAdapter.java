package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        //added long click handler method and added new arguments for finished and category
        void onItemClick(int pos, String description, String duedate, long id, int finished, String category);
        void onItemLongClick(int pos, String description, String duedate, long id, int finished, String category);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView descr;
        TextView cat;
        TextView due;
        LinearLayout linearLayout;
        String duedate;
        String description;
        String category;
        int finished;
        long id;


        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            cat = (TextView) view.findViewById(R.id.item_category);
            linearLayout = (LinearLayout) view.findViewById(R.id.linLayout);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));

            //added value retrieval for finished
            finished = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_FINISHED));
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));

            //set text for text views
            descr.setText(description);
            due.setText(duedate);
            cat.setText(category);

            //set background of layout depending on value
            if(finished == 1){
                linearLayout.setBackgroundColor(Color.DKGRAY);
            } else {
                linearLayout.setBackgroundColor(Contract.primaryColor);
            }
            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, id, finished, category);
        }

        @Override
        public boolean onLongClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemLongClick(pos, description, duedate, id, (finished + 1) % 2 , category);
            return true;
        }
    }

}
