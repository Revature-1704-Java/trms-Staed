package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.Note;
import com.staed.stores.FieldValueWrapper;

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
	
	@DisplayName("Note toFieldValueWrappers")
	@Test
	void fieldValueTest() {
		LocalDate g = LocalDate.now();
		Note b = new Note(3, 2, "hh", g, 5.0f, "nn");
		List<FieldValueWrapper> list = b.toFieldValueWrappers();
		
		int fields = 0;
		Iterator<FieldValueWrapper> iter = list.iterator();
		while(iter.hasNext()) {
			FieldValueWrapper tmp = iter.next();
			if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 3) {
				fields++;
			} else if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 2) {
				fields++;
			} else if (tmp.get().getValue().getClass() == String.class && ((String) tmp.get().getValue()).equals("hh")) {
				fields++;
			} else if (tmp.get().getValue().getClass() == Float.class && ((Float) tmp.get().getValue()) == 5.0f) {
				fields++;
			} else if (tmp.get().getValue().getClass() == String.class && ((String) tmp.get().getValue()).equals("nn")) {
				fields++;
			}
		}
		
		assertEquals(5, fields);
	}
}
