package theintelligentminds.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.User;

import android.widget.TextView;

/**
 * Created by Chris_1909 on 29.04.2015.
 */
public class Profile extends ActionBarActivity {
  private ConnectionProvider provider = ConnectionProvider.getInstance();
  private TextView lastName;
  private TextView firstName;
  private TextView eMail;
  private TextView gender;
  private TextView aboutMe;
  private Button edit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile);

    edit = (Button) findViewById(R.id.buttonEdit);
    lastName = (TextView) findViewById(R.id.editTextLastname);
    firstName = (TextView) findViewById(R.id.editTextFirstname);
    eMail = (TextView) findViewById(R.id.viewTextMail);
    gender = (TextView) findViewById(R.id.viewTextGender);
    aboutMe = (TextView) findViewById(R.id.editTextAbout_Me);

    AsyncProfileInformation asyncProInfos = new AsyncProfileInformation();
    asyncProInfos.execute();

    edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Profile.this, EditProfile.class));
        }
    });
  }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
            case R.id.friendlist:
                intent = new Intent(Profile.this, FriendView.class);
                break;
            case R.id.options:
                intent = new Intent(Profile.this, Options.class);
                break;
            default:
                intent = null;
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    class AsyncProfileInformation extends AsyncTask<String,Void,User> {
        @Override
        protected User doInBackground(String... strings) {
            User user = provider.getUserInformation();
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            lastName.setText(user.getLastName());
            firstName.setText(user.getFirstName());
            eMail.setText(user.getEmail());
            gender.setText(user.getGender());
            aboutMe.setText(user.getAboutMe());
        }
    }

}
