package theintelligentminds.messenger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.Message;
import theintelligentminds.messenger.emoji.EmojiconTextView;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

	private EmojiconTextView chatText;
	private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
	private LinearLayout singleMessageContainer;

	@Override
	public void add(ChatMessage object) {
		chatMessageList.add(object);
		super.add(object);
	}

    public void refreshFromMessagesList(TreeSet<Message> messages){
        ArrayList<ChatMessage> freshMessages = new ArrayList<>();
        for(Message message:messages){
            boolean mine = ConnectionProvider.getInstance().whoAmI().equals(message.senderEmail);
            freshMessages.add(new ChatMessage(mine, message.text));
        }
        chatMessageList = freshMessages;
    }

	public ChatArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.chatMessageList.size();
	}

	public ChatMessage getItem(int index) {
		return this.chatMessageList.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
		}
		singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
		ChatMessage chatMessageObj = getItem(position);

		chatText = (EmojiconTextView) row.findViewById(R.id.singleMessage);
		chatText.setText(chatMessageObj.message);
		chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_s_a : R.drawable.bubble_w_a);
        chatText.setTextColor(chatMessageObj.left ? Color.WHITE : Color.BLACK);
		singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}