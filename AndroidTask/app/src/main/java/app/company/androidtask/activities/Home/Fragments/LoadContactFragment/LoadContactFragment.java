package app.company.androidtask.activities.Home.Fragments.LoadContactFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.company.androidtask.R;
import app.company.androidtask.adapters.contact.Contact;
import app.company.androidtask.adapters.contact.ContactAdapter;


public class LoadContactFragment extends Fragment {
    private static final int REQUEST_READ_CONTACTS = 99;
    View view;
    RecyclerView contactRecyclerView;
    TextView failureTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_load_contact, container, false);
        failureTextView = view.findViewById(R.id.failure_text_view);
        if (permissionAlreadyGranted()) {
            readContacts();
        } else
            requestPermission();
        return view;
    }


    private boolean permissionAlreadyGranted() {
        int result = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_READ_CONTACTS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);
                if (!showRationale) {
                    AlertDialog.Builder CallDialogBuilder;
                    AlertDialog CallDialog;

                    CallDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    @SuppressLint("InflateParams")
                    View mView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dialog_request_permission, null);

                    CallDialogBuilder.setView(mView);
                    CallDialog = CallDialogBuilder.create();
                    Window window = CallDialog.getWindow();
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                    }
                    Objects.requireNonNull(CallDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    CallDialog.show();
                    TextView cancelTextView = mView.findViewById(R.id.cancel_text_view);
                    TextView okTextView = mView.findViewById(R.id.ok_text_view);
                    okTextView.setOnClickListener(v2 -> requestPermission());
                    cancelTextView.setOnClickListener(v3 -> {
                        CallDialog.dismiss();
                        contactRecyclerView.setVisibility(View.GONE);
                        failureTextView.setVisibility(View.VISIBLE);
                    });

                }
            }
        }
    }

    private void readContacts() {
        ContactAdapter contactAdapter = new ContactAdapter(getActivity(), getContacts());
        contactRecyclerView = view.findViewById(R.id.contact_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactRecyclerView.setAdapter(contactAdapter);
    }

    private List<Contact> getContacts() {
        List<Contact> contactList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = Objects.requireNonNull(getContext()).getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                contactList.add(new Contact(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            }
        }
        return contactList;
    }
}
