package lyc;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class FakeConnectionFactory implements AccountFactory{
	@Override
    public Connection createAccount(String []auths) {
    	return new FakeConnection();
    }


    public static class FakeConnection implements Connection{
    	static final public Date date = new Date();
    	static private int counter = 1;
    	private List<Item> result;
    	private UserBase user;
    	private Item sample;

    	public FakeConnection(){
    		result = new ArrayList<Item>();
    		user = TwitUserFactory.getInstance().getOrCreateUser(123, "mock_user", "mock_user");
    		sample = new Item(user, "hello world ", date);
    	}


    	public List<Item> SearchPost(String keyword){
    		if(keyword.equals("hello")) {
    			Item new_obj = new Item(sample);
    			new_obj.setText(new_obj.getText() + String.valueOf(counter));
    			result.add(new_obj);

    			counter++;
    			return result;
    		}

    		return null;
    	}

    	public List<Item> SearchPost(String keyword, int max_cnt){
    		return SearchPost(keyword);
    	}

    	public List<Item> getSelfHomeLine(){
    		return null;
    	}

    	public List<Item> getHomeLineById(long id){
    		return result;
    	}

    	public UserBase getCurrentUser(){
    		return user;
    	}
    }
}