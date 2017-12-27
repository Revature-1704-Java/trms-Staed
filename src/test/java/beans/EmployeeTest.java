package beans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.Employee;

class EmployeeTest {
	private Employee a;
	
	@BeforeEach
	void init() {
		a = new Employee("hehe@gmail.com", "digu2", "Lorem ipsum", 1, null,
				"bradstar@gmail.com", null);
	}

	@DisplayName("Employee Constructor")
	@Test
	void constructorTest() {
		Employee b = new Employee("bradstar@gmail.com", "itsmedio",
				"Dio Bradstar", 2, null, null, null);
		
		assertNotNull(b);
	}
	
	@DisplayName("Employee Getters and Setters")
	@Test
	void getterSetterTest() {
		assertEquals("hehe@gmail.com", a.getEmail());
		a.setEmail("dlgh@gmail.com");
		assertEquals("dlgh@gmail.com", a.getEmail());
		
		assertEquals("digu2", a.getPassword());
		a.setPassword("dudu");
		assertEquals("dudu", a.getPassword());
		
		assertEquals("Lorem ipsum", a.getName());
		a.setName("Donald Duck");
		assertEquals("Donald Duck", a.getName());
		
		assertEquals(1, a.getTypeId());
		a.setTypeId(2);
		assertEquals(2, a.getTypeId());
		
		assertNull(a.getSuperEmail());
		a.setSuperEmail("email@gmail.com");
		assertEquals("email@gmail.com", a.getSuperEmail());
		
		assertEquals("bradstar@gmail.com", a.getHeadEmail());
		a.setHeadEmail("xoxo@gmail.com");
		assertEquals("xoxo@gmail.com", a.getHeadEmail());
		
		assertNull(a.getBenCoEmail());
		a.setBenCoEmail("houdini@gmail.com");
		assertEquals("houdini@gmail.com",a.getBenCoEmail());
	}
	
	@DisplayName("Employee toString")
	@Test
	void toStringTest() {
		String expected = "Employee [ Email: hehe@gmail.com, Name: Lorem ipsum"
				+ ", TypeId: 1, Super Email: null, Head Email: "
				+ "bradstar@gmail.com, BenCo Email: null]";
		assertEquals(expected, a.toString());
	}
}
