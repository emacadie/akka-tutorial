package info.shelfunit.akka.separate;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.RoundRobinRouter;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;

public class PiRunner {

    public static void main(String[] args) {
	PiRunner pi = new PiRunner();
	pi.calculate(4, 10000, 10000);
    }

    public void calculate(
			  final int nrOfWorkers, 
			  final int nrOfElements, 
			  final int nrOfMessages) {
    
	// Create an Akka system
	ActorSystem system = ActorSystem.create("PiSystem");
	Class systemClass = system.getClass();
	System.out.println( "system is a " + systemClass.getName() );

	// create the result listener, which will print the result and shutdown the system
	final ActorRef listener = system.actorOf(new Props(Listener.class), "listener");
	System.out.println( "listener is a " + listener.getClass().getName() );
	// create the master
	ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {
		public UntypedActor create() {
		    return new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener);
		}
	    }), "master");

	// start the calculation
	System.out.println( "master is a " + master.getClass().getName() );
	master.tell(new Calculate(), master);

    }
}
