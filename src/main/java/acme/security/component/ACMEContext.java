package acme.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by cristianosanchez on 16/06/16.
 */

public class ACMEContext implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ACMEContext.class);

	private final String username;

	private final boolean isAdmin;

	public ACMEContext() {
		this("anonymous", false);
	}

	public ACMEContext(String username, boolean isAdmin) {
		this.username = username;
		this.isAdmin = isAdmin;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	@Override
	public String toString() {
		return "ACMEContext{" +
				"username='" + username + '\'' +
				", isAdmin=" + isAdmin +
				'}';
	}
}
