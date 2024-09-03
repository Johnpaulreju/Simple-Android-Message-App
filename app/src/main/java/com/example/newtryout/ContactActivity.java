package com.example.newtryout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ListView contactsListView = findViewById(R.id.contactsListView);
        SearchView contactSearchView = findViewById(R.id.contactSearchView);

        contacts = new ArrayList<>();
        adapter = new ContactAdapter(this, R.layout.list_item_contact, contacts);
        contactsListView.setAdapter(adapter);

        // Fetch contacts
        fetchContacts();

        // Implementing the search functionality
        contactSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void fetchContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            if (nameIndex >= 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(nameIndex);
                    contacts.add(name);
                }
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private class ContactAdapter extends ArrayAdapter<String> {

        private Context context;
        private int resource;
        private ArrayList<String> contacts;

        public ContactAdapter(Context context, int resource, ArrayList<String> contacts) {
            super(context, resource, contacts);
            this.context = context;
            this.resource = resource;
            this.contacts = contacts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resource, parent, false);
            }

            TextView contactNameTextView = convertView.findViewById(R.id.contactNameTextView);
            Button inviteButton = convertView.findViewById(R.id.inviteButton);
            Button chatButton = convertView.findViewById(R.id.chatButton);

            String contactName = contacts.get(position);
            contactNameTextView.setText(contactName);

            inviteButton.setOnClickListener(v -> {
                String inviteText = "Join me on this awesome app!";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, inviteText);
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            });

            chatButton.setOnClickListener(v -> {
                Intent chatIntent = new Intent(ContactActivity.this, ChatActivity.class);
                chatIntent.putExtra("contactName", contactName);
                startActivity(chatIntent);
            });

            return convertView;
        }
    }
}
