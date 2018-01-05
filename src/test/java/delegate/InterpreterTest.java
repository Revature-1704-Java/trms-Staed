package delegate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.staed.delegate.Interpreter;

class InterpreterTest {
	@DisplayName("Interpreter Constructor")
	@Test
	void constructorTest() {
		Interpreter b = new Interpreter();
		
		assertNotNull(b);
	}
	
	@DisplayName("Interpreter Login")
	@Test
	void loginTest() {
		Interpreter c = new Interpreter();
		JsonObject obj = c.login("down@smash.com", "smashing");
		assertNotNull(obj);
		assertNotNull(obj.get("content"));
		assertNotNull(obj.get("success"));
	}
	
	@DisplayName("Interpreter Display")
	@Test
	void displayTest() {
		Interpreter d = new Interpreter();
		JsonObject obj2 = d.display("down@smash.com");
		assertNotNull(obj2);
		assertNotNull(obj2.get("content"));
		assertNotNull(obj2.get("success"));
	}
}
