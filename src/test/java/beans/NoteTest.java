package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.Note;

class NoteTest {
	private LocalDate now;
	
	@BeforeEach
	void init() {
		now = LocalDate.now();
	}
	
	@DisplayName("Note Constructor")
	@Test
	void constructorTest() {
		Note b = new Note(0, 1, "john@gmail.com", now, 10, "Clap");
		Note c = new Note(1, 1, "hooman@gmail.com", now.plusDays(5), "Shake");
		
		assertNotNull(b);
		assertNotNull(c);
		assertEquals(-1, c.getNewAmount());
	}
	
	@DisplayName("Note Getters and Setters")
	@Test
	void getterSetterTest() {
		Note b = new Note(0, 1, "john@gmail.com", now, 10, "Clap");
		
		assertEquals(0, b.getId());
		b.setId(2);
		assertEquals(2, b.getId());
		
		assertEquals(1, b.getRequestId());
		b.setRequestId(0);
		assertEquals(0, b.getRequestId());
		
		assertEquals("john@gmail.com", b.getManagerEmail());
		b.setManagerEmail("mayne@gmail.com");
		assertEquals("mayne@gmail.com", b.getManagerEmail());
		
		assertEquals(now, b.getTimeActedOn());
		b.setTimeActedOn(now.plusDays(3));
		assertEquals(now.plusDays(3), b.getTimeActedOn());
		
		assertEquals(10, b.getNewAmount());
		b.setNewAmount(0);
		assertEquals(0, b.getNewAmount());
		
		assertEquals("Clap", b.getReason());
		b.setReason("Winging");
		assertEquals("Winging", b.getReason());
	}
	
	@DisplayName("Note toString")
	@Test
	void toStringTest() {
		Note b = new Note(0, 1, "john@gmail.com", now, 10, "Clap");
		String expected = "Note [id=0, requestId=1, "
				+ "managerEmail=john@gmail.com, timeActedOn=" + now
				+ ", newAmount=10.0, reason=Clap]";
		assertEquals(expected, b.toString());
	}
}
