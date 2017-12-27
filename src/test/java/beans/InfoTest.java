package beans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.Info;

class InfoTest {
	private Info a;
	
	@BeforeEach
	void init() {
		a = new Info(1, "Party", "Main Office", "Christmas");
	}
	
	@DisplayName("Info Constructor")
	@Test
	void constructorTest() {
		Info b = new Info(1, "Party", "Main Office", "Christmas");
		assertNotNull(b);
	}
	
	@DisplayName("Info Getters and Setters")
	@Test
	void getterSetterTest() {
		assertEquals(1, a.getRequestId());
		a.setRequestId(2);
		assertEquals(2, a.getRequestId());
		
		assertEquals("Party", a.getDescription());
		a.setDescription("Yarty");
		assertEquals("Yarty", a.getDescription());
		
		assertEquals("Main Office", a.getLocation());
		a.setLocation("Neverland");
		assertEquals("Neverland", a.getLocation());
		
		assertEquals("Christmas", a.getJustification());
		a.setJustification("Because I can");
		assertEquals("Because I can", a.getJustification());	
	}
	
	@DisplayName("Info toString")
	@Test
	void toStringTest() {
		String expected = "RequestInfo [requestId=1, description=Party, "
				+ "location=Main Office, justification=Christmas]";
		assertEquals(expected, a.toString());
	}
}
