package beans;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.beans.Attachment;
import com.staed.stores.FieldValueWrapper;

class AttachmentTest {
	private Attachment a, b;
	
	@BeforeEach
	void init() {
		a = new Attachment("hoopa.txt", 1, "A good file");
		b = new Attachment("jaja.json", 2, 1, "Makes you laugh");
	}

	@DisplayName("Attachment Constructor")
	@Test
	void constructorTest() {
		assertNotNull(a);
		assertNotNull(b);
	}
	
	@DisplayName("Attachment Getters and Setters")
	@Test
	void getterSetterTest() {
		assertEquals("jaja.json", b.getFilename());
		b.setFilename("nolaughs.json");
		assertEquals("nolaughs.json", b.getFilename());
		
		assertEquals(2, b.getRequestId());
		b.setRequestId(4);
		assertEquals(4, b.getRequestId());
		
		assertEquals(1, b.getApprovedAtState());
		b.setApprovedAtState(0);
		assertEquals(0, b.getApprovedAtState());
		
		assertEquals("Makes you laugh", b.getDescription());
		b.setDescription("No fun zone");
		assertEquals("No fun zone", b.getDescription());
	}
	
	@DisplayName("Attachment toString")
	@Test
	void toStringTest() {
		String expected = "Attachment [filename=hoopa.txt, requestId=1, "
				+ "approvedAtState=0, description=A good file]";
		assertEquals(expected, a.toString());
	}
	
	@DisplayName("Attachment toFieldValueWrappers")
	@Test
	void fieldValueTest() {
		Attachment b = new Attachment("hh", 2, 1, "jk");
		List<FieldValueWrapper> list = b.toFieldValueWrappers();
		
		int fields = 0;
		Iterator<FieldValueWrapper> iter = list.iterator();
		while(iter.hasNext()) {
			FieldValueWrapper tmp = iter.next();
			if (tmp.get().getValue().getClass() == String.class && ((String) tmp.get().getValue()).equals("hh")) {
				fields++;
			} else if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 2) {
				fields++;
			} else if (tmp.get().getValue().getClass() == Integer.class && (Integer) tmp.get().getValue() == 1) {
				fields++;
			} else if (tmp.get().getValue().getClass() == String.class && ((String) tmp.get().getValue()).equals("jk")) {
				fields++;
			}
		}
		
		assertEquals(4, fields);
	}
}
