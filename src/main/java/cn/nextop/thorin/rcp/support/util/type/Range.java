package cn.nextop.thorin.rcp.support.util.type;

/**
 * @author Jingqi Xu
 * @param <T>
 */
public class Range<T extends Comparable<? super T>> {
	//
	private T value1;
	private T value2;
	private Bound bound1;
	private Bound bound2;
	
	//
	public static enum Bound {
		//
		OPEN, CLOSED;
		
		//
		public boolean isOpen() {
			return this == OPEN;
		}
		
		public static Bound parse(boolean open) {
			return open ? OPEN :  CLOSED;
		}
		
		private <T extends Comparable<? super T>> boolean contains(T v1, T v2) {
			if(v1 == null) return true;
			if(v2 == null) return false;
			final int r = v1.compareTo(v2);
			if(r != 0) return r < 0; else return this == CLOSED;
		}
		
		private <T extends Comparable<? super T>> boolean contains(T v1, T v2, Bound b2) {
			if(v1 == null) return true;
			if(v2 == null) return false;
			final int r = v1.compareTo(v2);
			if(r != 0) return r < 0; else return this == CLOSED && b2 == CLOSED;
		}
	}

	/**
	 * 
	 */
	public Range() {
	}
	
	public Range(T v1, T v2, Bound b1, Bound b2) {
		this.value1 = v1;
		this.value2 = v2;
		this.bound1 = b1;
		this.bound2 = b2;
	}
	
	/**
	 * 
	 */
	public T getValue1() {
		return value1;
	}

	public void setValue1(T value) {
		this.value1 = value;
	}

	public T getValue2() {
		return value2;
	}

	public void setValue2(T value) {
		this.value2 = value;
	}

	public Bound getBound1() {
		return bound1;
	}

	public void setBound1(Bound bound) {
		this.bound1 = bound;
	}

	public Bound getBound2() {
		return bound2;
	}

	public void setBound2(Bound bound) {
		this.bound2 = bound;
	}

	@Override
	public String toString() {
		return "Range{" +
				"value1=" + value1 +
				", value2=" + value2 +
				", bound1=" + bound1 +
				", bound2=" + bound2 +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Range<?> range = (Range<?>) o;

		if (!value1.equals(range.value1)) return false;
		if (!value2.equals(range.value2)) return false;
		if (bound1 != range.bound1) return false;
		return bound2 == range.bound2;
	}

	@Override
	public int hashCode() {
		int result = value1.hashCode();
		result = 31 * result + value2.hashCode();
		result = 31 * result + bound1.hashCode();
		result = 31 * result + bound2.hashCode();
		return result;
	}

	/**
	 * 
	 */
	public boolean isValid() {
		if(value1 == null && value2 == null) return false;
		if(value1 == null || value2 == null) return true;
		final int r = this.value2.compareTo(this.value1);
		if(r < 0) return false; else if(r > 0) return true;
		return bound1 == Bound.CLOSED && bound2 == Bound.CLOSED;
	}
	
	public final boolean contains(final T value) {
		if(!this.bound1.contains(this.value1, value)) return false;
		if(!this.bound2.contains(value, this.value2)) return false;
		return true;
	}
	
	public final boolean contains(final Range<T> range) {
		return contains(range.getValue1()) && contains(range.getValue2());
	}
	
	public final boolean isOverlap(final Range<T> rhs) {
		if(!this.bound1.contains(this.value1, rhs.value2, rhs.bound2)) return false;
		if(!this.bound2.contains(rhs.value1, this.value2, rhs.bound1)) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public static <T extends Comparable<? super T>> Range<T> open(T lower, T upper) {
		return new Range<T>(lower, upper, Bound.OPEN, Bound.OPEN);
	}
	
	public static <T extends Comparable<? super T>> Range<T> closed(T lower, T upper) {
		return new Range<T>(lower, upper, Bound.CLOSED, Bound.CLOSED);
	}
	
	public static <T extends Comparable<? super T>> Range<T> openClosed(T lower, T upper) {
		return new Range<T>(lower, upper, Bound.OPEN, Bound.CLOSED);
	}
	
	public static <T extends Comparable<? super T>> Range<T> closedOpen(T lower, T upper) {
		return new Range<T>(lower, upper, Bound.CLOSED, Bound.OPEN);
	}
}
