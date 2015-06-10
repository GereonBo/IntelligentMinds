package theintelligentminds.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.Message;
import theintelligentminds.messenger.emoji.Emojicon;
import theintelligentminds.messenger.emoji.EmojiconEditText;
import theintelligentminds.messenger.emoji.EmojiconGridFragment;
import theintelligentminds.messenger.emoji.EmojiconsFragment;

public class ChatBubbleActivity extends FragmentActivity  implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EmojiconEditText chatText;
    private Button buttonSend;
    private ImageView buttonEmo;
    private LinearLayout emoPanel;
    private String receiver;
    boolean popupShown = false;
    Timer autoUpdateTimer;
    private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);

    Intent intent;
    private boolean side = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = getIntent().getStringExtra("USER_EMAIL");

        Intent i = getIntent();
        setContentView(R.layout.activity_chat);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonEmo = (ImageView) findViewById(R.id.imageButton);

        listView = (ListView) findViewById(R.id.listView1);
        emoPanel = (LinearLayout) findViewById(R.id.emojilayout);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EmojiconEditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AsyncSendMessageTask().execute();
                    return true;
                }

                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new AsyncSendMessageTask().execute();
            }
        });
        buttonEmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popUpEmos();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        autoUpdateTimer = new Timer();
        autoUpdateTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                autoUpdateMessages();
            }
        }, 0, 5000);
    }


    private void autoUpdateMessages()
    {
        final TreeSet<Message> messages = provider.getMessagesBySenderAndReceiverSorted(receiver);
        runOnUiThread(new Runnable() {
            public void run() {
                chatArrayAdapter.refreshFromMessagesList(messages);
                chatArrayAdapter.notifyDataSetInvalidated();
                listView.invalidate();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
            });
    }

    private boolean popUpEmos(){
        if(popupShown) {
            emoPanel.setVisibility(View.GONE);
        }else{
            emoPanel.setVisibility(View.VISIBLE);
        }
        popupShown = !popupShown;
        return true;
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(chatText, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(chatText);
    }




    class AsyncSendMessageTask extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            if (chatText == null || chatText.getText() == null ||
                    chatText.getText().toString().equals("")) {
                return true;
            }

            return provider.sendMessage(receiver,chatText.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if(success) {
                chatArrayAdapter.add(new ChatMessage(false, chatText.getText().toString()));
            }else{
                chatArrayAdapter.add(new ChatMessage(false, "Message failed to send: " + chatText.getText().toString()));
            }
            chatText.setText("");

        }
    }
}

