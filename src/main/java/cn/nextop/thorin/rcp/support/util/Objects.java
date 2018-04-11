package cn.nextop.thorin.rcp.support.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Jingqi Xu
 */
@SuppressWarnings({"unchecked"})
public final class Objects {
	//
	public static final Object NULL = new Object();
	public static final Object[] EMPTY = new Object[0];
	
	//
	private static final Set<Class<?>> PRIMITIVES_AND_WRAPPERS = new HashSet<Class<?>>();
	static {
		PRIMITIVES_AND_WRAPPERS.add(boolean.class);
		PRIMITIVES_AND_WRAPPERS.add(Boolean.class);
		PRIMITIVES_AND_WRAPPERS.add(byte.class);
		PRIMITIVES_AND_WRAPPERS.add(Byte.class);
		PRIMITIVES_AND_WRAPPERS.add(char.class);
		PRIMITIVES_AND_WRAPPERS.add(Character.class);
		PRIMITIVES_AND_WRAPPERS.add(double.class);
		PRIMITIVES_AND_WRAPPERS.add(Double.class);
		PRIMITIVES_AND_WRAPPERS.add(float.class);
		PRIMITIVES_AND_WRAPPERS.add(Float.class);
		PRIMITIVES_AND_WRAPPERS.add(int.class);
		PRIMITIVES_AND_WRAPPERS.add(Integer.class);
		PRIMITIVES_AND_WRAPPERS.add(long.class);
		PRIMITIVES_AND_WRAPPERS.add(Long.class);
		PRIMITIVES_AND_WRAPPERS.add(short.class);
		PRIMITIVES_AND_WRAPPERS.add(Short.class);
	}
	
	/**
	 * 
	 */
	public static <T> T cast(Object obj) {
		return (T)obj;
	}
	
	public static boolean isEquals(Object lhs, Object rhs) {
		if(lhs == rhs) return true;
		else if(lhs == null && rhs == null) return true;
		else if(lhs == null || rhs == null) return false;
		else return lhs.equals(rhs);
	}
	
	/**
	 * 
	 */
	public static boolean isArray(Object array) {
		if(array == null) return false;
		return array.getClass().isArray();
	}
	
	public static boolean isSerializable(Object obj) {
		return obj instanceof Serializable;
	}
	
	public static boolean isPrimitiveOrWrapper(Object obj) {
		if(obj == null) return false;
		return PRIMITIVES_AND_WRAPPERS.contains(obj.getClass());
	}
	
	public static boolean isArrayOfPrimitives(Object array) {
		if (array == null) return false;
		final Class<?> clazz = array.getClass();
		return clazz.isArray() && clazz.getComponentType().isPrimitive();
	}
	
	public static boolean isArrayOfPrimitivesOrWrappers(Object array) {
		if (array == null) return false;
		final Class<?> clazz = array.getClass();
		return clazz.isArray() && PRIMITIVES_AND_WRAPPERS.contains(clazz);
	}
	
	/**
	 * 
	 */
	public static Object getDefaultValue(final Class<?> clazz) {
		if(clazz == null || !clazz.isPrimitive()) return null;
		if(void.class == clazz) return null;
		if(boolean.class == clazz) return Boolean.FALSE;
		if(byte.class == clazz) return Byte.valueOf((byte) 0);
		if(short.class == clazz) return Short.valueOf((short) 0);
		if(int.class == clazz) return Integer.valueOf(0);
		if(long.class == clazz) return Long.valueOf(0L);
		if(char.class == clazz) return Character.valueOf((char) 0);
		if(double.class == clazz) return Double.valueOf(0d);
		if(float.class == clazz) return Float.valueOf(0);
		throw new RuntimeException("assertion failed, should not reach here, clazz: " + clazz);
	}
}
