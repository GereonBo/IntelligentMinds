package theintelligentminds.messenger;

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
    ArrayList<String> friendsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        addFriendsListView = (ListView) findViewById(R.id.addFriendsListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_view_row, friendsList);

        addFriendsListView.setAdapter(listViewAdapter);

        addFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String item = (String) adapterView.getItemAtPosition(i);
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
                Log.d("Test", "Query submit: " + s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<User> userList = null;
                try {
                    userList = provider.searchAccounts(s);
                } catch(Exception e) {
                    Log.d("Error!!!!: ", e.getStackTrace().toString());
                }

                ArrayAdapter<User> listViewAdapter = new ArrayAdapter<User>(AddFriend.this, R.layout.friend_view_row, userList);
                addFriendsListView.setAdapter(listViewAdapter);
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

/*
    class AsyncDBAccess extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONArray users = provider.searchAccounts(strings[0]);

            return null;
        }

        @Override
        protected void onPostExecute(final String authToken) {
            super.onPostExecute(authToken);

            if (authToken.equals("")) {
                new AlertDialog.Builder(LoginActivity.this).setTitle("Login").setMessage("Login failed")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
            else {
                new AlertDialog.Builder(LoginActivity.this).setTitle("Login").setMessage("Login successful")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(LoginActivity.this, FriendView.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
*/

}
