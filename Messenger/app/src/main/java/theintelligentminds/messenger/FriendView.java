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


public class FriendView extends ActionBarActivity {

    private ListView friendListView;
    private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_view);

        friendListView = (ListView) findViewById(R.id.friendListView);

        ArrayList<String> testStrings = new ArrayList<String>();
        testStrings.add("item 1");
        testStrings.add("item 2");
        testStrings.add("item 3");
        testStrings.add("item 4");

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_view_row,
                testStrings);

        friendListView.setAdapter(listViewAdapter);

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

    class AsyncDBAccess extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            //String friends = provider.getFriends();
            //ArrayList<User> userList = provider.searchAccounts("Thomas");

            /*for(int i = 0; i <  userList.length(); i++) {
                try {
                    JSONObject messageObject = userList.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(String authToken) {
            super.onPostExecute(authToken);

        }
    }
}
