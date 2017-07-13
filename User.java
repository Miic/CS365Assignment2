import java.util.HashSet;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Set;
import java.util.Stack;

public class User extends Observable implements Entity {

	private int ID;
	private String name;
	private Stack<Message> feed;
	private Set<User> followers;
	private Set<User> following;
	private int msgSent;
	
	public User(String name) {
		ID = nextID;
		nextID++;
		userList.put(name, this);
		this.name = name;
		feed = new Stack<Message>();
		followers = new HashSet<User>();
		following = new HashSet<User>();
	}
	
	public void pushMessage(Message message) {
		feed.add(message);
		MenuWindow.getInstance().isPositiveMessage(message.getMessage());
		msgSent++;
		for(User e : followers) {
			e.update(this, message);
		}
	}
	
	public int getFollowingCount() {
		return following.size();
	}
	
	public int getFollowerCount() {
		return followers.size();
	}
	
	protected Set<User> getFollowing() {
		return following;
	}
	
	protected Stack<Message> getFeed() {
		return feed;
	}
	
	public void follow(User followe) {
		following.add(followe);
		followe.followers.add(this);
	}
	
	@Override
	public int getID() {
		return ID;
	}

	public void update(Observable o, Object arg) {
		feed.add((Message) arg);
		MenuWindow.getInstance().updateUserScreen(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMessageCount() {
		return msgSent;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private static Hashtable<String, User> userList = new Hashtable<String, User>();
	private static int nextID = 0;
	
	public static int getUserCount() {
		return userList.size();
	}
	
	public static boolean checkUserExists(String name) {
		return userList.containsKey(name);
	}
	
	public static User getUser(String name) {
		return userList.get(name);
	}
	
	protected static Hashtable<String, User> getUserList() {
		return userList;
		
	}
	
}