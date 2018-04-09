package lyc;

import java.util.List;
import java.util.Objects;

/**
 * An abstract class which defines user information.
 */
public abstract class UserBase {

    protected long user_id;
    protected String user_link;
    protected String user_name;
    protected String user_screenName;

    /**
     * Constructor
     * @param user_id The ID of user.
     * @param user_name The name of user.
     * @param user_screenName The screen name of user.
     */
    protected UserBase(long user_id, String user_name, String user_screenName) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_screenName = user_screenName;
    }

    /**
     * Get user id.
     * @return The id of user.
     */
    public long getUser_id() {
        return user_id;
    }

    /**
     * Set user id
     * @param user_id user id
     */
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    /**
     * Get link of the user.
     * @return The link of user.
     */
    public String getUser_link() {
        return user_link;
    }

    /**
     * Setter for the link of user.
     * @param user_link The link of the user.
     */
    public void setUser_link(String user_link) {
        this.user_link = user_link;
    }

    /**
     * Get the name of user.
     * @return The string type name of user.
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * Setter for the user_name
     * @param user_name The name of the user.
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * Get the screen name of the user
     * @return The string of screen name of the user.
     */
    public String getUser_screenName() {
        return user_screenName;
    }

    /**
     * Setter for the screen name.
     * @param user_screenName A string of the screen name of user.
     */
    public void setUser_screenName(String user_screenName) {
        this.user_screenName = user_screenName;
    }

    /**
     * Override the equals method. To compare the all information of UserBase.
     * @param other The object including UserBase
     * @return If equals, return true, otherwise false.
     */
    @Override
    public boolean equals(Object other){
        if(other == this)
            return true;
        if( !(other instanceof UserBase) )
            return false;

        UserBase right = (UserBase) other;
        return ( (user_id==right.user_id) && user_screenName.equals(right.user_screenName)
                && user_name.equals(right.user_name) && user_link.equals(right.user_link));
    }

    /**
     * Override the hashchode method.
     * @return return the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(user_id, user_link, user_name, user_screenName);
    }
}
