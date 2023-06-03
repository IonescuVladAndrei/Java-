package packTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testFlowJUnit {

	@Test
	void testareMetode() throws InterruptedException {
		testFlow server1 = new testFlow();
		assertFalse("mail oferta expirata" == server1.testare(1));
		
		testFlow server2 = new testFlow();
		assertTrue("plata invalida" == server2.testare(2));
		
		testFlow server3 = new testFlow();
		assertTrue("mail confirmare" == server3.testare(3));
	}

}
