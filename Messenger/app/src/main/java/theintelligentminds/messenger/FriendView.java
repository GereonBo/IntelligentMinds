package theintelligentminds.messenger;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.User;


public class FriendView extends ActionBarActivity {

    private ListView friendListView;
    private ArrayList<User> friendList;
    private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_view);

        friendListView = (ListView) findViewById(R.id.friendListView);

        AsyncDBAccessGetFriends asyncGetFriends = new AsyncDBAccessGetFriends();
        asyncGetFriends.execute();

        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = friendList.get(i);
                AsyncDBAccessStartCommunication asyncStartCommunication = new AsyncDBAccessStartCommunication();
                asyncStartCommunication.execute(user);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = null;

        switch(item.getItemId())
        {
            case R.id.add_friend:
                intent = new Intent(FriendView.this, AddFriend.class);
                break;
            case R.id.options:
                intent = new Intent(FriendView.this, Options.class);
                break;
            default:
                intent = null;
        }

        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    class AsyncDBAccessGetFriends extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... strings) {
            friendList = provider.getContacts();
            return friendList;
        }

        @Override
        protected void onPostExecute(ArrayList<User> friendList) {
            super.onPostExecute(friendList);

            ArrayAdapter<User> listViewAdapter = new ArrayAdapter<User>(FriendView.this,
                    R.layout.friend_view_row, friendList);

            friendListView.setAdapter(listViewAdapter);
        }
    }

    class AsyncDBAccessStartCommunication extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            Intent intent = new Intent(FriendView.this, ChatBubbleActivity.class);
            intent.putExtra("USER_EMAIL", users[0].getEmail());
            startActivity(intent);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
        }
    }
}
