package theintelligentminds.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import at.intelligentminds.client.ConnectionProvider;

/**
 * Created by Chris_1909 on 07.06.2015.
 */
public class Options extends Activity {
    private Button logout;
    private Button add_friend;
    private Button friendlist;
    private Button profile;

    private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        // Parse.initialize(this, "app-id", "client-key");

        add_friend = (Button) findViewById(R.id.buttonAddFriend);
        friendlist = (Button) findViewById(R.id.buttonFriendlist);
        profile = (Button) findViewById(R.id.buttonProfile);
        logout = (Button) findViewById(R.id.buttonLogout);

        friendlist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Options.this, FriendView.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Options.this, Profile.class);
                startActivity(intent);
            }
        });

        add_friend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Options.this, AddFriend.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncDBAccess async = new AsyncDBAccess();
                async.execute();
            }
        });
    }

    class AsyncDBAccess extends AsyncTask<Boolean, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            Boolean result = provider.performLogout();

            return result;
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            new AlertDialog.Builder(Options.this).setTitle("Logout").setMessage("Logout successful")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Options.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }).show();

        }
    }
}
