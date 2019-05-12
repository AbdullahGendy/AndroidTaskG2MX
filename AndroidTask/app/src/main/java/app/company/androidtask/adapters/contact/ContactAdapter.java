package app.company.androidtask.adapters.contact;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import app.company.androidtask.R;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private Context context;
    private List<Contact> itemList;


    public ContactAdapter(Context context, List<Contact> itemList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

        holder.contactNameTextView.setText(itemList.get(listPosition).getName());
        holder.contactNumberTextView.setText(itemList.get(listPosition).getPhone());
        holder.callContactLinearLayout.setOnClickListener(v -> {
            AlertDialog.Builder CallDialogBuilder;
            AlertDialog CallDialog;

            CallDialogBuilder = new AlertDialog.Builder(context);
            @SuppressLint("InflateParams")
            View mView = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_call, null);

            CallDialogBuilder.setView(mView);
            CallDialog = CallDialogBuilder.create();
            Window window = CallDialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            Objects.requireNonNull(CallDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            CallDialog.show();
            TextView phoneNumberTextView = mView.findViewById(R.id.phone_number_text_view);
            TextView cancelTextView = mView.findViewById(R.id.cancel_text_view);
            TextView callTextView = mView.findViewById(R.id.call_text_view);
            phoneNumberTextView.setText("Do You Want To Call " + itemList.get(listPosition).getName() + "?");
            callTextView.setOnClickListener(v2 -> {
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + itemList.get(listPosition).getPhone()));
                context.startActivity(intent1);
                CallDialog.dismiss();
            });
            cancelTextView.setOnClickListener(v3 -> {
                CallDialog.dismiss();
            });
        });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contactNameTextView;
        TextView contactNumberTextView;
        LinearLayout callContactLinearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.contact_name_text_view);
            contactNumberTextView = itemView.findViewById(R.id.contact_number_text_view);
            callContactLinearLayout = itemView.findViewById(R.id.call_contact_linear_layout);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
