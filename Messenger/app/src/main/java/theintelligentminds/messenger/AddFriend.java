package theintelligentminds.messenger;

import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.User;


public class AddFriend extends ActionBarActivity {
    private ListView addFriendsListView;
    private ConnectionProvider provider = ConnectionProvider.getInstance();
    private ArrayList<User> newFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        addFriendsListView = (ListView) findViewById(R.id.addFriendsListView);

        addFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("id: ", l + "");
                Log.v("pos: ", i + "");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                AsyncDBAccessSearchFriends asyncSearchFriends = new AsyncDBAccessSearchFriends();
                asyncSearchFriends.execute(s);
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    class AsyncDBAccessSearchFriends extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... strings) {
            newFriends = provider.searchAccounts(strings[0]);
            return newFriends;
        }

        @Override
        protected void onPostExecute(ArrayList<User> newFriends) {
            super.onPostExecute(newFriends);

            ArrayAdapter<User> listViewAdapter = new ArrayAdapter<User>(AddFriend.this, R.layout.friend_view_row, newFriends);
            addFriendsListView.setAdapter(listViewAdapter);
        }
    }

    class AsyncDBAccessAddFriend extends AsyncTask<User, Void, boolean> {
        @Override
        protected boolean doInBackground(User... users) {
            newFriends = provider.addContact(users[0].email);
            return newFriends;
        }

        @Override
        protected void onPostExecute(boolean successful_added) {
            super.onPostExecute(successful_added);

            ArrayAdapter<User> listViewAdapter = new ArrayAdapter<User>(AddFriend.this, R.layout.friend_view_row, newFriends);
            addFriendsListView.setAdapter(listViewAdapter);
        }
    }
}
