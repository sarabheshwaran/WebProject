package uub.debug;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPIConnection {

    public static void main(String[] args) {
        // Set your Twitter API credentials
        String consumerKey = "19X5tu1BS5QyJs1L2XIi2xKN4";
        String consumerSecret = "5cK88KGDlKMUnhiEuqGcamZESWhg6Q03feYITBLnT1ItKqBHL4";
        String accessToken = "1459511966528000001-eSRYyhjUp0ixqcQXUu2GW2vZisKRXk";
        String accessTokenSecret = "czNrfpl8b7e0YLHBsVM9KiRJCE6vKwBOYHBvdW3xyc8hV";

        // Set up Twitter configuration
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret);

        // Create a Twitter instance
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();

        try {
        	
//        	
//            // Get the authenticated user's profile
            User user = twitter.verifyCredentials();
            System.out.println("Name: " + user.getName());
            System.out.println("Screen Name: " + user.getScreenName());
            System.out.println("Description: " + user.getDescription());
            System.out.println(user.getCreatedAt());
            // and so on, you can access other information about the user as well
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}

