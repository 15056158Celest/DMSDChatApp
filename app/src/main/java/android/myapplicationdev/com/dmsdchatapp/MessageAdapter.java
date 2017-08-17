package android.myapplicationdev.com.dmsdchatapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15056158 on 17/8/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    Context context;
    int layoutResourceId;
    ArrayList<Message> messagesList;

    public MessageAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Message> postList) {
        super(context, resource, postList);
        this.layoutResourceId = resource;
        this.context = context;
        this.messagesList = postList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        MessagesHolder holder = null;

        if (row == null ){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new MessagesHolder();

            holder.tvName = (TextView) row.findViewById(R.id.tvName);
            holder.tvTime = (TextView) row.findViewById(R.id.tvTime);
            holder.tvMessage = (TextView) row.findViewById(R.id.tvMessage);
            holder.etText = (EditText) row.findViewById(R.id.etText);

            row.setTag(holder);

        } else {
            holder = (MessagesHolder) row.getTag();
        }

        Message message = messagesList.get(position);
        holder.etText.setEnabled(false);
        holder.tvName.setText(message.getMessageUser());
        holder.tvTime.setText(message.getMessageTime());
        holder.tvMessage.setText(message.getMessageText());
        holder.etText.setText(message.getMessageText());

        return row;
    }
    static class MessagesHolder {
        TextView tvName, tvTime,tvMessage;
        EditText etText;
    }
}
