package lyc;

public class TwitUserImpl extends UserBase {
    public TwitUserImpl(long user_id, String user_name, String user_screenName) {
        super(user_id, user_name, user_screenName);

        user_link = "www.twitter.com/" + user_screenName;
    }


    @Override
    public boolean equals(Object other){
        if(other == this)
            return true;
        if( !(other instanceof TwitUserImpl) )
            return false;

        TwitUserImpl right = (TwitUserImpl) other;
        return super.equals(right);
    }

}