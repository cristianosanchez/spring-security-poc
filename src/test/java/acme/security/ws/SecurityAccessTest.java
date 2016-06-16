package acme.security.ws;

import acme.security.App;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebIntegrationTest(randomPort = true)
public class SecurityAccessTest {

	private static final Logger logger = LoggerFactory.getLogger(SecurityAccessTest.class);

	@Value("${local.server.port}")
	private int port;

	@Test
	public void adminAuthorizedTest() throws IOException {
		Response response =
				given()
						.auth().basic("admin", "admin@123")
						.and().contentType("application/json")
				.when()
						.get(resource("admin/"));
//		.then().
//				assertThat().
//				statusCode(is(HttpStatus.OK.value()));


		// Get all headers
		Headers allHeaders = response.getHeaders();

		// Get a single header value:
		String headerName = response.getHeader("headerName");

		// Get all cookies as simple name-value pairs
		Map<String, String> allCookies = response.getCookies();

		// Get a single cookie value:
		String cookieValue = response.getCookie("cookieName");

		// Get status line
		String statusLine = response.getStatusLine();

		// Get status code
		int statusCode = response.getStatusCode();
		assertThat(statusCode, is(200));

	}

	@Test
	public void guestAuthorizedTest() throws IOException {
				given()
						.auth().basic("user", "user@123")
						.and().contentType("application/json")
				.when()
						.get(resource("guest/"))
				.then()
					.assertThat()
					.statusCode(is(HttpStatus.OK.value()));
	}
	//testar FORBIDDEN(403, "Forbidden")


	@Test
	public void guestNotAuthorizedTest() throws IOException {
		given()
				.auth().basic("user", "invalid_password")
				.and().contentType("application/json")
				.when()
				.get(resource("guest/"))
				.then()
				.assertThat()
				.statusCode(is(HttpStatus.UNAUTHORIZED.value()));
	}

	private String resource(String resource) {
		String url = String.format("http://localhost:%d/%s", this.port, resource);
		logger.debug(String.format("URL para serviço: %s", url));
		return url;
	}
}
