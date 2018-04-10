package lyc;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is a factory which is responsible
 * for making fake connection, create account, etc.
 */
public class FakeConnectionFactory implements AccountFactory{
	@Override
    public Connection createAccount(String []auths) {
    	return new FakeConnection();
    }

	/**
	 * This class implements from Connection,
	 * which is responsible for configure related parameters,
	 * aims to make a fake connection.
	 */
    public static class FakeConnection implements Connection{
    	static final public Date date = new Date();
    	static private int counter = 1;
    	private List<Item> result;
    	private UserBase user;
    	private Item sample;

		/**
		 * Constructor
		 * Create instance of user.
		 * Create sample
		 */
    	public FakeConnection(){
    		result = new ArrayList<Item>();
    		user = TwitUserFactory.getInstance().getOrCreateUser(123, "mock_user", "mock_user");
    		sample = new Item(user, "hello world ", date);
    	}

		/**
		 * Get a list of Item based on the keyword.
		 * @param keyword the keyword that you use to search posts
		 * @return A list of Items if the keyword is valid, otherwise null.
		 */
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

		/**
		 * Get a list of Item based on the keyword and count.
		 * @param keyword the keyword that you use to search posts
		 * @param max_cnt maximum number of posts returned
		 * @return A list of Items.
		 */
    	public List<Item> SearchPost(String keyword, int max_cnt){
    		return SearchPost(keyword);
    	}

		/**
		 * Get the twitter's homeline.
		 * @return Null
		 */
    	public List<Item> getSelfHomeLine(){
    		return null;
    	}

		/**
		 * Get homeline by user's id
		 * @param id the user's id
		 * @return A list of Items
		 */
    	public List<Item> getHomeLineById(long id){
    		return result;
    	}

		/**
		 * Get current user object
		 * @return A UserBase object
		 */
    	public UserBase getCurrentUser(){
    		return user;
    	}
    }
}