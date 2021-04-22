package others.exception;

/**
 * 异常测试
 *
 * 异常是指程序在运行过程中发生的一些不正常事件
 * Java中的异常处理关键字
 *
 *
 * @author fengjirong 2020/06/02 17:29
 */
public class ExceptionTest {

	public static void main(String[] args) throws MyExceptions01 {
		/*int[] arr = {1,2,3};
		try {
			for(int j = 4; j<5; j++){
				System.out.println(arr[j]);
			}
		} catch (ArrayIndexOutOfBoundsException  e){
			new MyException(e.getMessage()).getNum();
		}*/
		try{
			System.out.println(1/0);
		} catch (RuntimeException e){
			//使用自定义异常时，需要使用父类捕获
			 new MyExceptions01().doMessage();
		} finally {
			System.out.println("finally已执行");
		}
	}
}
