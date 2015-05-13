package theintelligentminds.messenger.GUI_Tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import theintelligentminds.messenger.LoginActivity;
import theintelligentminds.messenger.R;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

  private Solo my_solo_;

  public LoginActivityTest() {
    super(LoginActivity.class);
  }

  public void setUp() throws Exception {
    super.setUp();
    my_solo_ = new Solo(getInstrumentation(), getActivity());

  }

  public void tearDown() throws Exception {

  }

  public void testLogin() {
    my_solo_.enterText((EditText) my_solo_.getView(R.id.textfieldUserName), "TestUserJoe");
    my_solo_.enterText((EditText) my_solo_.getView(R.id.textfieldPassword), "admin");
    my_solo_.clickOnButton("Login");
    try {
      my_solo_.getText("User not found!");
    }
    catch (Exception e) {
      my_solo_.getText("Login succesful!");
    }

  }

}