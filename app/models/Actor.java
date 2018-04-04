package models;

/**
 * This class define the tweet's structure.
 * Each tweet contains its text and the user's information.
 */
public class Actor {
    private String text;
    private User user;

    /**
     * Constructor
     */

    public Actor() {
    }

    /**
     * Constructor with text
     * @param text The text from the tweet
     */
    public Actor(String text) {
        this.text = text;
    }

    /**
     * Constructor
     * @param text The text from the tweet
     * @param user The user who publish the tweet
     */
    public Actor(String text, User user) {
        this.text = text;
        this.user = user;
    }

    /**
     * Get the text
     * @return Return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text
     * @param text The text need to be set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the user's information
     * @return Return user's information (models.User)
     */

    public User getUser() {
        return user;
    }

    /**
     * Set the user.
     *
     * @param user The user's information (models.User) need to be set.
     */

    public void setUser(User user) {
        this.user = user;
    }
}
