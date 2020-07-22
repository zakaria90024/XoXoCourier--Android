package com.zakaria.xoxo.courier.ReadActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zakaria.xoxo.courier.MainActivity.MainActivity;
import com.zakaria.xoxo.courier.R;
import com.zakaria.xoxo.courier.main.model.Post;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ReadActivity readActivity;
    public List<Post> posts;

    public CustomAdapter(ReadActivity readActivity, List<Post> posts) {
        this.readActivity = readActivity;
        this.posts = posts;
    }
    public void setData(List<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //for passing/inflate layoute
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        //handle item click here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(readActivity);
                String[] option = {"Update", "Delete"};
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            //update is called
                            Integer id = posts.get(position).getId();
                            String name = posts.get(position).getName();
                            String email = posts.get(position).getEmail();
                            String number = posts.get(position).getNumber();
                            String cash = posts.get(position).getCash();
                            String address = posts.get(position).getAddress();
                            String state = posts.get(position).getState();
                            String zip = posts.get(position).getZip();
                            String role = posts.get(position).getRole();
                            String time = posts.get(position).getCreatedAt();

                            //Intent to start activity
                            Intent intent = new Intent(readActivity, MainActivity.class);
                            //put data in intent
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("number", number);
                            intent.putExtra("cash", cash);
                            intent.putExtra("address", address);
                            intent.putExtra("state", state);
                            intent.putExtra("zip", zip);
                            intent.putExtra("role", role);
                            intent.putExtra("time", time);
                            //start activity
                            readActivity.startActivity(intent);

                        }
                        if (which == 1) {
                            Post post = posts.get(position);
                            Integer id = post.getId();
                            readActivity.deleteData(id);
                        }
                    }
                }).create().show();

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //name, email, number, cash, address, state, zip, role;
        Post post = posts.get(position);

        String name = post.getName();
        String email = post.getEmail();
        String number = post.getNumber();
        String cash = post.getCash();
        String address = post.getAddress();
        String state = post.getState();
        String zip = post.getZip();
        String role = post.getRole();
        Integer id = post.getId();
        String time = post.getCreatedAt();

        holder.name.setText("Name: "+name);
        holder.email.setText("Email: "+email);
        holder.number.setText("Number: "+number);
        holder.cash.setText("TAKA: "+cash);
        holder.address.setText("Address: "+address);
        holder.state.setText("State: "+state);
        holder.zip.setText("Zip Code: "+zip);
        holder.role.setText("Order Role: "+role);
        holder.id.setText("ID"+id);
        holder.time.setText(" "+time);
    }

    @Override
    public int getItemCount()  {
        return posts.size();
    }
}
