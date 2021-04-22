package others.exception;


public class MyExceptions01 extends RuntimeException {
    String message;
    public MyExceptions01(){
        super();
    }

    @Override
    public String getMessage() {
        this.message = super.getMessage();
        return this.message;
    }

    public void doMessage(){
        System.out.println(this.message+"自定义异常输出");
    }
}
