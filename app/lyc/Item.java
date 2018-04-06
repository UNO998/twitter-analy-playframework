package lyc;

import java.util.Date;

public class Item {
    public Item(UserBase user, String text, Date created_time){
        this.user = user;
        this.text = text;
        this.created_time = created_time;
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

    public String getRef_user() {
        return ref_user;
    }

    public void setText(String text){
        this.text = text;
    }
    public void setRef_user(String ref_user){
        this.ref_user = ref_user;
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

    private UserBase user;
    private String text;
    private String ref_user;
    private Date created_time;
}
