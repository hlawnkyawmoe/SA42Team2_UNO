
package model;

/**
 *
 * @author Olive
 */
public class User {
   
    private int userId;
    private String userName;
    private String email;
    private String password;

    public User(){
    }
    
    public User(int UID, String UName, String Uemail, String Upassword ){
        this.userId = UID;
        this.userName = UName;
        this.email = Uemail;
        this.password = Upassword;
        }

        /**
         * @return the userId
         */
        public int getUserId() {
            return userId;
        }

        /**
         * @param userId the userId to set
         */
        public void setUserId(int userId) {
            this.userId = userId;
        }

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * @param userName the userName to set
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email the email to set
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }
}
