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
                final String item = (String) adapterView.getItemAtPosition(i);
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
            case R.id.add_friends:
                intent = new Intent(FriendView.this, AddFriend.class);
                break;
            case R.id.show_profile:
                intent = new Intent(FriendView.this, Profile.class);
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
            ArrayList<User> friendList = provider.getContacts();
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
}
