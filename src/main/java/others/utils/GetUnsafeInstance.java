package others.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 获取unsafe实例
 *
 * @author fengjirong 2020/06/04 10:38
 */
public class GetUnsafeInstance {

	/**
	 * 通过反射获取unsafe实例
	 *
	 * @author fengjirong 2020-06-04 10:39
	 * @return 
	 */
	public static Unsafe getUnsafeInstance() {
		try {
			Class<?> clazz = Unsafe.class;
			Field f = clazz.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			Unsafe unsafe = (Unsafe) f.get(clazz);
			return unsafe;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

}
