package cn.nextop.thorin.rcp.support.util.type;

/**
 * 
 * @author Jingqi Xu
 * @param <T>
 */
public final class Pair<T> {
	//
	private T v1;
	private T v2;
	
	/**
	 * 
	 */
	public Pair() {
	}
	
	public Pair(T v1, T v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public String toString() {
		return "Pair{" +
				"v1=" + v1 +
				", v2=" + v2 +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Pair<?> pair = (Pair<?>) o;

		if (v1 != null ? !v1.equals(pair.v1) : pair.v1 != null) return false;
		return v2 != null ? v2.equals(pair.v2) : pair.v2 == null;
	}

	@Override
	public int hashCode() {
		int result = v1 != null ? v1.hashCode() : 0;
		result = 31 * result + (v2 != null ? v2.hashCode() : 0);
		return result;
	}

	/**
	 * 
	 */
	public T getV1() {
		return v1;
	}

	public void setV1(T v1) {
		this.v1 = v1;
	}
	
	public T getV2() {
		return v2;
	}

	public void setV2(T v2) {
		this.v2 = v2;
	}

	/**
	 * 
	 */
	public synchronized void swap() {
		T backup = this.v1;
		this.v1 = this.v2;
		this.v2 = backup;
	}
	
	/**
	 * 
	 */
	public static void swap(Pair<?> p) {
		doSwap(p); // Nothing but capture the <?>
	}
	
	private static <T> void doSwap(Pair<T> p) {
		synchronized(p) {
			T backup = p.v1;
			p.v1 = p.v2;
			p.v2 = backup;
		}
	}

}
