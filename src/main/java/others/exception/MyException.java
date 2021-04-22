package others.exception;

/**
 * 自定义异常类
 *
 * @author fengjirong 2020/06/03 14:18
 */
public class MyException extends Exception{

	private String num;

	public MyException(String num){
		this.num = num;
	}

	public void getNum(){
		System.out.println("出问题的下标为："+this.num);
	}


}
