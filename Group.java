import java.util.HashSet;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Set;

public class Group implements Entity {

	private int groupID;
	private String groupName;
	
	private Set<User> members;
	
	public Group(String groupName) {
		groupID = nextID;
		nextID++;
		this.groupName = groupName;
		groupList.put(groupName, this);
		members = new HashSet<User>();
	}
	
	public void addMember(User user) {
		members.add(user);
	}
	
	public void removeMember(User user) {
		members.remove(user);
	}
	
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getID() {
		return groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getGroupSize() {
		return members.size();
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	/////////////////////////////////////////////////////
	
	private static Hashtable<String, Group> groupList = new Hashtable<String, Group>();
	private static int nextID = 0;
	
	public static int getGroupCount() {
		return groupList.size();
	}
	
	public static boolean checkGroupExists(String name){
		return groupList.containsKey(name);
	}
	
	public static Group getGroup(String name) {
		return groupList.get(name);
	}
	
	
}
