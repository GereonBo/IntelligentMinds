package theintelligentminds.messenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class AddFriend extends ActionBarActivity {
  private SearchView searchView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_friend);

    searchView = (SearchView) findViewById(R.id.searchView);
    searchView.setQueryHint("SearchView");

    // *** setOnQueryTextFocusChangeListener ***
    searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub

        Toast.makeText(getBaseContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
      }
    });

    // *** setOnQueryTextListener ***
    searchView.setOnQueryTextListener(new OnQueryTextListener() {

      @Override
      public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub

        Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();

        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        // TODO Auto-generated method stub

        Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
        return false;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_add_friend, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    // noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
