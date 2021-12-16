public class UserInfo {
	private String aesop_username = "${{ secrets.aesop_username }}";
	private String aesop_password = "${{ secrets.aesop_password }}";

	String getUserName () {
		return aesop_username;
	}

	String getPassword () {
		return aesop_password;
	}
}
