package info.shelfunit.akka.separate;

public final class Result {
    private final double value;

    public Result(double value) {
	this.value = value;
    }

    public double getValue() {
	return value;
    }
}
