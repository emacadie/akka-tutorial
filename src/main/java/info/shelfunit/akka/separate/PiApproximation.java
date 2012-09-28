package info.shelfunit.akka.separate;

import akka.util.Duration;


    public final class PiApproximation {
	private final double pi;
	private final Duration duration;

	public PiApproximation(double pi, Duration duration) {
	    this.pi = pi;
	    this.duration = duration;
	}

	public double getPi() {
	    return pi;
	}

	public Duration getDuration() {
	    return duration;
	}
    }