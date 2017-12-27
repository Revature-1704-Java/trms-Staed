package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.Request;

class RequestTest {
	private LocalDate now;
	private Period time;
	private Request a;
	
	@BeforeEach
	void init() {
		now = LocalDate.now();
		time = Period.ZERO.plusDays(1);
		a = new Request(0, "john@gmail.com", 4, 1, 0, 50.3f,
				now.plusDays(7), time, now);
	}
	
	@DisplayName("Request Constructor")
	@Test
	void constructorTest() {
		Request b = null;
		assertNull(b);
		b = new Request(5, "sdfa@gmail.com", 6, 2, 4, 1f,
				now.plusDays(2), time.minusDays(1), now);
		assertNotNull(b);
	}
	
	@DisplayName("Request Getters and Setters")
	@Test
	void getterSetterTest() {
		assertEquals(0, a.getId());
		a.setId(1);
		assertEquals(1, a.getId());
		
		assertEquals("john@gmail.com", a.getEmail());
		a.setEmail("nhoj@gmail.com");
		assertEquals("nhoj@gmail.com", a.getEmail());
		
		assertEquals(4, a.getEventTypeId());
		a.setEventTypeId(2);
		assertEquals(2, a.getEventTypeId());
		
		assertEquals(1, a.getFormatId());
		a.setFormatId(2);
		assertEquals(2, a.getFormatId());
		
		assertEquals(0, a.getState());
		a.setState(5);
		assertEquals(5, a.getState());
		
		assertEquals(50.3f, a.getCost());
		a.setCost(20.5f);
		assertEquals(20.5f, a.getCost());
		
		assertEquals(now.plusDays(7), a.getEventDate());
		a.setEventDate(now.plusDays(3));
		assertEquals(now.plusDays(3), a.getEventDate());
		
		assertEquals(time, a.getTimeMissed());
		a.setTimeMissed(time.plusMonths(1));
		assertEquals(time.plusMonths(1), a.getTimeMissed());
		
		assertEquals(now, a.getLastReviewed());
		a.setLastReviewed(now.minusDays(2));
		assertEquals(now.minusDays(2), a.getLastReviewed());
	}
	
	@DisplayName("Request toString")
	@Test
	void toStringTest() {
		String expected = "Request [ Id: 0, Employee Email: john@gmail.com, "
				+ "Event Type Id: 4, Grade Format Id: 1, State: 0, "
				+ "Cost: 50.3, Event Date: " + now.plusDays(7)
				+ ", Work Time Missed: " + time + ", Date of Last Review: "
				+ now;
		assertEquals(expected, a.toString());
	}
}
