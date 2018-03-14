package clayburn.familymap.model;


import java.util.Objects;

/**
 * AuthToken represents a token pertaining to a particular user session.
 * @author Zach Clayburn
 */
public class AuthToken {

    /**
     * A unique token allowing a logged in user to access login-required APIs
     */
    private String authToken;

    /**
     * The unique userName that the auth token is tied to
     */
    private String userName;

    /**
     * Creates a new AuthToken object representing a row from the authToken table
     * @param authToken A unique token allowing a logged in user to access login-required APIs
     * @param userName The unique userName that the auth token is tied to
     */
    public AuthToken(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Creates a String out ot the AuthToken
     * @return The String containing the AuthToken's fields
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthToken{");
        sb.append("authToken='").append(authToken).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Compares the given AuthToken object with another object o
     * @param o The object to be compared
     * @return False if o is null, not an AuthToken or if it's fields don't match, true otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken) &&
                Objects.equals(userName, authToken1.userName);
    }

    /**
     * Generate a hash code for the given AuthToken
     * @return The AuthToken's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName);
    }
}
