package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.GradingFormat;
import com.staed.stores.FieldValueWrapper;

class GradingFormatTest {
	@DisplayName("GradingFormat Constructor")
	@Test
	void constructorTest() {
		GradingFormat a = new GradingFormat(5, "Dance", 60);
		
		assertNotNull(a);
	}
	
	@DisplayName("GradingFormat Getters and Setters")
	@Test
	void getterSetterTest() {
		GradingFormat b = new GradingFormat(3, "Jumping", 30);
		
		assertEquals(3, b.getId());
		b.setId(2);
		assertEquals(2, b.getId());
		
		assertEquals("Jumping", b.getType());
		b.setType("Crawling");
		assertEquals("Crawling", b.getType());
		
		assertEquals(30, b.getCutoff());
		b.setCutoff(10);
		assertEquals(10, b.getCutoff());
	}
	
	@DisplayName("GradingFormat toFieldValueWrappers")
	@Test
	void fieldValueTest() {
		GradingFormat b = new GradingFormat(3, "Jumping", 30);
		List<FieldValueWrapper> list = b.toFieldValueWrappers();
		
		int fields = 0;
		Iterator<FieldValueWrapper> iter = list.iterator();
		while(iter.hasNext()) {
			FieldValueWrapper tmp = iter.next();
			if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 3) {
				fields++;
			} else if (tmp.get().getValue().getClass() == String.class && ((String) tmp.get().getValue()).equals("Jumping")) {
				fields++;
			} else if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 30) {
				fields++;
			}
		}
		
		assertEquals(3, fields);
	}
}
