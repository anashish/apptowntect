/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.helper.ItemTouchHelperAdapter;
import tech.town.app.com.apptowntech.helper.ItemTouchHelperViewHolder;
import tech.town.app.com.apptowntech.helper.OnStartDragListener;
import tech.town.app.com.apptowntech.model.HomeCategory;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    //private final List<String> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;
    private List<HomeCategory> stList;
    private OnChecked onClickListener;


    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener, List<HomeCategory> stList,OnChecked onClickListener) {
        mDragStartListener = dragStartListener;
        this.stList=stList;
        this.onClickListener=onClickListener;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_custom_news_list, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textView.setText(stList.get(position).getCName());

        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

       /* if(stList.get(position).isSelected()) {
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }*/
        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setChecked(stList.get(position).isSelected());

       /* holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                stList.get(position).setSelected(b);
                onClickListener.onSelected(b,position);

            }
        });*/
    }

    @Override
    public void onItemDismiss(int position) {
        stList .remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(stList, fromPosition, toPosition);
        stList.get(toPosition).setSelected(true);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return stList.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView textView;
        public final CheckBox checkBox;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView_categoryName);
            checkBox = (CheckBox) itemView
                    .findViewById(R.id.checkbox_news_select);
            handleView = (ImageView) itemView.findViewById(R.id.imageView2);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
    public interface OnChecked {
        void onSelected(boolean b,int pos);
    }
    public void notifydatachange(List<HomeCategory> list){
        stList=list;
        notifyDataSetChanged();
    }
}
