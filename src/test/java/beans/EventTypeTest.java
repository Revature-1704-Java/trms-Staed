package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.EventType;
import com.staed.stores.FieldValueWrapper;

class EventTypeTest {
	@DisplayName("EventType Constructor")
	@Test
	void constructorTest() {
		EventType a = new EventType(5, "Higgs", 60);
		
		assertNotNull(a);
	}
	
	@DisplayName("EventType Getters and Setters")
	@Test
	void getterSetterTest() {
		EventType b = new EventType(3, "Clap", 30);
		
		assertEquals(3, b.getId());
		b.setId(2);
		assertEquals(2, b.getId());
		
		assertEquals("Clap", b.getName());
		b.setName("Mayne");
		assertEquals("Mayne", b.getName());
		
		assertEquals(30, b.getCompensation());
		b.setCompensation(80);
		assertEquals(80, b.getCompensation());
	}
	
	@DisplayName("EventType toFieldValueWrappers")
	@Test
	void fieldValueTest() {
		EventType b = new EventType(3, "Party", 99);
		List<FieldValueWrapper> list = b.toFieldValueWrappers();
		
		int fields = 0;
		Iterator<FieldValueWrapper> iter = list.iterator();
		while(iter.hasNext()) {
			FieldValueWrapper tmp = iter.next();
			if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 3) {
				fields++;
			} else if (tmp.get().getValue().getClass() == String.class && ((String) tmp.get().getValue()).equals("Party")) {
				fields++;
			} else if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 99) {
				fields++;
			}
		}
		
		assertEquals(3, fields);
	}
}
