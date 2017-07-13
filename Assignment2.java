import java.awt.EventQueue;

public class Assignment2 {
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuWindow window = MenuWindow.getInstance();
					window.setVisibility(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
