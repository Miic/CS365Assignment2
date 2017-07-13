
public class TextMessage implements Message {
	
	private String header;
	private int userId;
	private String message;
	
	public TextMessage(User user, String message) {
		userId = user.getID();
		header = user.getName();
		this.message = message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;

	}

	@Override
	public String getMessage() {
		return message;
		
	}

	public int getUserId() {
		return userId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
}
