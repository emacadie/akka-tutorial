package info.shelfunit.testing;

import info.shelfunit.testing.MyActor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;


/**
 *
 * @author ericm
 */
public class MyActorTest {

    private String username;
    private String hibernateUserName;

    public MyActorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        UUID uuid = UUID.randomUUID();
        // this.setUsername( uuid.toString() );
        UUID hibernateUuid = UUID.randomUUID();
        // this.setHibernateUserName( hibernateUuid.toString() );
    } // end method setUp

    @After
    public void tearDown() {
    }

    @Test
    public void demonstrateTestActorRef() {
	ActorSystem system = ActorSystem.create("PiSystem");
	final Props props = new Props(MyActor.class);
	System.out.println( "props.dispatcher(): " + props.dispatcher() );
	// orig:
	final TestActorRef<MyActor> ref = TestActorRef.create(system, props, "testA");
	
	// final TestActorRef<MyActor> ref = TestActorRef.apply(props, system);
	final MyActor actor = ref.underlyingActor();
	assertTrue(actor.testMe());
	// system.shutdown();
	assertTrue( 1 == 1 );
    }


} // end class MyActorTest