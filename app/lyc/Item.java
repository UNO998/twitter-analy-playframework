package lyc;

import java.util.Date;
import java.util.Objects;


public class Item {
    public Item(UserBase user, String text, Date created_time){
        this.user = user;
        this.text = text;
        this.created_time = created_time;
    }

    public Item(Item other){
        this.user = other.user;
        this.text = new String(other.text);
        this.created_time = other.created_time;
    }

    public long getUser_id(){
        return user.getUser_id();
    }

    public String getUser_name(){
        return user.getUser_name();
    }

    public String getUser_link(){
        return user.getUser_link();
    }


    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setCreated_time(Date date){
        this.created_time = date;
    }

    public Date getCreated_time(){
        return created_time;
    }

    public UserBase getUser_profile() {
        return user;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(user, text, created_time);
    }

    private UserBase user;
    private String text;
    private Date created_time;
}
