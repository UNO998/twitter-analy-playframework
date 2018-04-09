package lyc;

import java.util.Date;
import java.util.Objects;

/**
 * class store the tweets information
 */
public class Item {
    /**
     * constructor
     * @param user user information
     * @param text tweets text
     * @param created_time create date
     */
    public Item(UserBase user, String text, Date created_time){
        this.user = user;
        this.text = text;
        this.created_time = created_time;
    }

	
    /**
     * copy constructer
     * @return other the right value
     */
    public Item(Item other){
        this.user = other.user;
        this.text = new String(other.text);
        this.created_time = other.created_time;
    }

    /**
     * get user id information
     * @return user id
     */
    public long getUser_id(){
        return user.getUser_id();
    }

    /**
     * get user name information
     * @return user name
     */
    public String getUser_name(){
        return user.getUser_name();
    }

    /**
     * get user profile information
     * @return user link
     */
    public String getUser_link(){
        return user.getUser_link();
    }

    /**
     * get tweets text
     * @return tweets text
     */
    public String getText() {
        return text;
    }

    /**
     * setter
     * @param text tweet text
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * set create time
     * @param date time
     */
    public void setCreated_time(Date date){
        this.created_time = date;
    }

    /**
     * get create time
     * @return create time
     */
    public Date getCreated_time(){
        return created_time;
    }

    /**
     * get user profile
     * @return user profile
     */
    public UserBase getUser_profile() {
        return user;
    }

    /**
     * judge equal
     * @param other object
     * @return boolean
     */
    @Override
    public boolean equals(Object other){
        if(other == this)
            return true;
        if( !(other instanceof Item) )
            return false;

        Item right = (Item) other;
        return (user.equals(right.user) && text.equals(right.text) 
            && created_time.equals(right.created_time));
    }

    /**
     * get hash code
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, text, created_time);
    }

    private UserBase user;
    private String text;
    private Date created_time;
}
