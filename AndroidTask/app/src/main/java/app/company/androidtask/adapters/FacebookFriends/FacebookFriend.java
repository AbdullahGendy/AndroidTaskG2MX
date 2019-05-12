package app.company.androidtask.adapters.FacebookFriends;


public class FacebookFriend {
    private String friendName;
    private String friendImageUrl;

    public FacebookFriend(String friendName, String friendImageUrl) {
        this.friendName = friendName;
        this.friendImageUrl = friendImageUrl;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendImageUrl() {
        return friendImageUrl;
    }

    public void setFriendImageUrl(String friendImageUrl) {
        this.friendImageUrl = friendImageUrl;
    }
}
