package com.zakaria.xoxo.courier.ReadActivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zakaria.xoxo.courier.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView name, email, number, cash, address, state, zip, role,id, time;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        //for onclock
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return false;
            }
        });

        name = itemView.findViewById(R.id.textView_name);
        email = itemView.findViewById(R.id.textView_email);
        number = itemView.findViewById(R.id.textView_phone);
        cash = itemView.findViewById(R.id.textView_cash);
        address = itemView.findViewById(R.id.textView_address);
        state = itemView.findViewById(R.id.textView_state);
        zip = itemView.findViewById(R.id.textView_zip);
        role = itemView.findViewById(R.id.textView_role);
        id = itemView.findViewById(R.id.textView_id);
        time = itemView.findViewById(R.id.textView_time);
    }

    private ViewHolder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }


}
