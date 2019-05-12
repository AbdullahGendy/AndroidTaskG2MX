package app.company.androidtask.adapters.FacebookFriends;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import app.company.androidtask.R;


public class FacebookFriendsAdapter extends RecyclerView.Adapter<FacebookFriendsAdapter.ViewHolder> {


    private Context context;
    private List<FacebookFriend> itemList;


    public FacebookFriendsAdapter(Context context, List<FacebookFriend> itemList) {
        this.context = context;
        this.itemList = itemList;


    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facebook_friend, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

       /* Picasso.with(context)
                .load(itemList.get(listPosition).getFriendImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.facebookFriendsImageView);*/

        holder.facebookFriendNameTextView.setText(itemList.get(listPosition).getFriendName());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView facebookFriendsImageView;
        TextView facebookFriendNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            facebookFriendsImageView = itemView.findViewById(R.id.facebook_friends_image_view);
            facebookFriendNameTextView = itemView.findViewById(R.id.facebook_friend_name_text_view);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
