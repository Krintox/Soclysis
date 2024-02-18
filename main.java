import java.util.*;

class Post {
    String content;
    List<String> comments;
    int likes;
    int shares;

    public Post(String content) {
        this.content = content;
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.shares = 0;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void like() {
        likes++;
    }

    public void share() {
        shares++;
    }
}

class User {
    String name;
    List<User> friends;
    List<Post> posts;

    public User(String name) {
        this.name = name;
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void addFriend(User friend) {
        friends.add(friend);
        friend.friends.add(this);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
        friend.friends.remove(this);
    }

    public void addPost(String postContent) {
        posts.add(new Post(postContent));
    }

    public void likePost(int postIndex) {
        if (postIndex >= 0 && postIndex < posts.size()) {
            posts.get(postIndex).like();
        }
    }

    public void sharePost(int postIndex) {
        if (postIndex >= 0 && postIndex < posts.size()) {
            posts.get(postIndex).share();
        }
    }

    public void subscribe(User user) {
        if (!friends.contains(user)) {
            friends.add(user);
            user.friends.add(this);
        }
    }

    public void commentOnPost(int postIndex, String comment) {
        if (postIndex >= 0 && postIndex < posts.size()) {
            posts.get(postIndex).addComment(comment);
        }
    }

    public List<User> getMutualFriends(User other) {
        List<User> mutualFriends = new ArrayList<>();
        for (User friend : friends) {
            if (other.friends.contains(friend)) {
                mutualFriends.add(friend);
            }
        }
        return mutualFriends;
    }

    public int getFriendCount() {
        return friends.size();
    }
}

class SocialNetwork {
    Map<String, User> users;

    public SocialNetwork() {
        users = new HashMap<>();
    }

    public void addUser(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new User(name));
        }
    }

    public void removeUser(String name) {
        if (users.containsKey(name)) {
            User user = users.get(name);
            for (User friend : user.friends) {
                friend.friends.remove(user);
            }
            users.remove(name);
        }
    }

    public void addFriendship(String userName1, String userName2) {
        if (users.containsKey(userName1) && users.containsKey(userName2)) {
            User user1 = users.get(userName1);
            User user2 = users.get(userName2);
            user1.addFriend(user2);
        }
    }

    public void removeFriendship(String userName1, String userName2) {
        if (users.containsKey(userName1) && users.containsKey(userName2)) {
            User user1 = users.get(userName1);
            User user2 = users.get(userName2);
            user1.removeFriend(user2);
        }
    }

    public int getUserCount() {
        return users.size();
    }

    public Map<String, Integer> getFollowersCount() {
        Map<String, Integer> followersCount = new HashMap<>();
        for (User user : users.values()) {
            followersCount.put(user.name, user.getFriendCount());
        }
        return followersCount;
    }
}

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocialNetwork socialNetwork = new SocialNetwork();

        System.out.println("Welcome to the Social Network Analysis Tool!");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. Add Friendship");
            System.out.println("4. Remove Friendship");
            System.out.println("5. Analyze Followers");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the user to add:");
                    String addUser = scanner.nextLine();
                    socialNetwork.addUser(addUser);
                    System.out.println("User " + addUser + " added.");
                    break;
                case 2:
                    System.out.println("Enter the name of the user to remove:");
                    String removeUser = scanner.nextLine();
                    socialNetwork.removeUser(removeUser);
                    System.out.println("User " + removeUser + " removed.");
                    break;
                case 3:
                    System.out.println("Enter the names of users to form friendship (separated by space):");
                    String[] addFriendship = scanner.nextLine().split(" ");
                    socialNetwork.addFriendship(addFriendship[0], addFriendship[1]);
                    System.out.println("Friendship formed between " + addFriendship[0] + " and " + addFriendship[1] + ".");
                    break;
                case 4:
                    System.out.println("Enter the names of users to remove friendship (separated by space):");
                    String[] removeFriendship = scanner.nextLine().split(" ");
                    socialNetwork.removeFriendship(removeFriendship[0], removeFriendship[1]);
                    System.out.println("Friendship removed between " + removeFriendship[0] + " and " + removeFriendship[1] + ".");
                    break;
                case 5:
                    Map<String, Integer> followersCount = socialNetwork.getFollowersCount();
                    System.out.println("Followers count:");
                    for (Map.Entry<String, Integer> entry : followersCount.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
    }
}
