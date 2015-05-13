package messenger.theintelligentminds.messenger;

import android.app.Application;

/**
 * Created by Chris_1909 on 06.05.2015.
 */
public class UserData extends Application{
    private String FirstName_;
    private String LastName_;

    public String getFirstName()
    {
        return this.FirstName_;
    }
    public void setFirstName(String FirstName)
    {
        this.FirstName_ = FirstName;
    }
}
