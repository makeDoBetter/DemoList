package others.generic;

/**
 * 泛型类
 *
 * 有界的类型参数:
 * 可能有时候，你会想限制那些被允许传递到一个类型参数的类型种类范围。
 * 例如，一个操作数字的方法可能只希望接受Number或者Number子类的实例。这就是有界类型参数的目的。
 * 要声明一个有界的类型参数，首先列出类型参数的名称，后跟extends关键字，最后紧跟它的上界
 *
 * @author fengjirong 2020/06/05 14:57
 */
public class Box<T extends Object> {
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
