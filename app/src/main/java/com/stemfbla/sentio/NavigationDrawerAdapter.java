package com.stemfbla.sentio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    public MyViewHolder[] holderArray = new MyViewHolder[3];
    int counter = 0;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    public void clearItems() {
        for(int a = 0; a < 3; a++)
            holderArray[a].cNone();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holderArray[counter++] = holder;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        if(position == 0) holder.itemView.setBackgroundColor(Color.parseColor("#E8E8E8"));
        switch(position) {
            case 0:
                holder.icon.setImageResource(R.drawable.ic_action_home);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.ic_action_calendar_day);
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.ic_action_pin);
                break;
        }
    }

    @Override public int getItemCount() { return data.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.rowText);
            icon = (ImageView) itemView.findViewById(R.id.rowIcon);
            this.itemView = itemView;
        }

        public void cNone() {
            int[] attrs = new int[] {R.attr.selectableItemBackground};
            TypedArray ta = context.obtainStyledAttributes(attrs);
            Drawable drawableFromTheme = ta.getDrawable(0);
            ta.recycle();
            itemView.setBackgroundDrawable(drawableFromTheme);
        }
    }
}
