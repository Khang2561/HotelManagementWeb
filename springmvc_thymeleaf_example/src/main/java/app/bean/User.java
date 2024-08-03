package app.bean;

public class User {
	private String userName;
	   private String passWord;
	   private String fullName;
	   private String authorizationID;

	   public User(String userName, String passWord, String fullName, String authorizationID) {
	      this.userName = userName;
	      this.passWord = passWord;
	      this.fullName = fullName;
	      this.authorizationID = authorizationID;
	   }

	   public String getUserName() {
	      return this.userName;
	   }

	   public void setUserName(String userName) {
	      this.userName = userName;
	   }

	   public String getPassWord() {
	      return this.passWord;
	   }

	   public void setPassWord(String passWord) {
	      this.passWord = passWord;
	   }

	   public String getFullName() {
	      return this.fullName;
	   }

	   public void setFullName(String fullName) {
	      this.fullName = fullName;
	   }

	   public String getAuthorizationID() {
	      return this.authorizationID;
	   }

	   public void setAuthorizationID(String authorizationID) {
	      this.authorizationID = authorizationID;
	   }

	   public String toString() {
	      return "User [userName=" + this.userName + ", passWord=" + this.passWord + ", fullName=" + this.fullName + ", authorizationID=" + this.authorizationID + "]";
	   }
}
