import java.util.HashMap;

public class IDandPasswords {

	HashMap<String, String> loginInfo = new HashMap<String, String>();
	
	IDandPasswords() {
		loginInfo.put("ang5", "password");
		loginInfo.put("sbotan7", "password123");
		loginInfo.put("ifelix3", "reallybigpassword");
		loginInfo.put("araul11", "pswrd");
	}
	
	protected HashMap getLoginInfo() {
		return loginInfo;
	}
}
