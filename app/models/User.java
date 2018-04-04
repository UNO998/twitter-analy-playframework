package models;

import java.util.List;

/**
 * This class stores the user's information including id, 10 recent tweets, name and etc.
 */
public class User {
    private List<String> texts;
    private final String id;
    private final String name;
    private final String screen_name;
    private final String description;
    private final String profile_image_url;
    private final int followers;
    private final int posts;
    private final int friends;
    private final String created_at;

    /**
     * Constructor
     * @param id User's ID number
     * @param name User's name
     * @param screen_name User's screen name
     * @param description User's personal description
     * @param profile_image_url The url about the user's image of profile
     * @param followers The number of the user's followers
     * @param posts The number of posts about the user
     * @param friends The number of the friends about the user
     * @param created_at The time when the user was created
     */
    public User(String id, String name, String screen_name, String description, String profile_image_url, int followers, int posts, int friends, String created_at) {
        this.id = id;
        this.name = name;
        this.screen_name = screen_name;
        this.description = description;
        this.profile_image_url = profile_image_url;
        this.followers = followers;
        this.posts = posts;
        this.friends = friends;
        this.created_at = created_at;
    }

    /**
     * Get recent 10 tweets
     * @return A list of tweeets
     */
    public List<String> getTexts() {
        return texts;
    }

    /**
     * Set recent 10 tweets
     * @param texts The List of tweets
     */
    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    /**
     * Get the id of the user
     * @return The id of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description about the user
     * @return the description of the user
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the url of the image of the profile
     * @return The url of the image of the profile
     */
    public String getProfile_image_url() {
        return profile_image_url;
    }

    /**
     * Get the screen name of the user
     * @return the screen name of the user
     */
    public String getScreen_name() {
        return screen_name;
    }

    /**
     * Get the number of followers of the user
     * @return the number of followers of the user
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * Get the post times of the user
     * @return the post times of the user
     */
    public int getPosts() {
        return posts;
    }

    /**
     * Get the number of friends of the user
     * @return the number of friends of the user
     */
    public int getFriends() {
        return friends;
    }

    /**
     * Get the time when the user was created
     * @return the time when the user was created
     */

    public String getCreated_at() {
        String[] times = this.created_at.split(" ");
        String format = times[0] + " " + times[1] + " "  + times[2] + " " + times[5];
        return format;
    }
}
