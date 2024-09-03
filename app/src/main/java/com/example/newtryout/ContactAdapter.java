package com.example.newtryout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<String> contactList;
    private Context context;

    public ContactAdapter(Context context, List<String> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        String contactName = contactList.get(position);
        holder.contactNameTextView.setText(contactName);

        holder.inviteButton.setOnClickListener(v -> {
            String inviteText = "Join me on this awesome app!";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, inviteText);
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        holder.chatButton.setOnClickListener(v -> {
            Intent chatIntent = new Intent(context, ChatActivity.class);
            chatIntent.putExtra("contactName", contactName);
            context.startActivity(chatIntent);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView contactNameTextView;
        Button inviteButton, chatButton;

        ContactViewHolder(View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView);
            inviteButton = itemView.findViewById(R.id.inviteButton);
            chatButton = itemView.findViewById(R.id.chatButton);
        }
    }
}
