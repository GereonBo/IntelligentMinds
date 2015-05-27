package theintelligentminds.messenger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import at.intelligentminds.client.ConnectionProvider;


public class FriendView extends ActionBarActivity {

    private ListView friendView;
    private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendView = (ListView) findViewById(R.id.friendView);

        setContentView(R.layout.friend_view);
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
