package others.Annotation;

/**
 * @author fengjirong
 * @date 2021/1/12 16:43
 */
public enum EnumTest {

    /**
     * TRUE
     * */
    TRUE("是", "Y"),
    /**
     * FALSE
     * */
    FALSE("否", "N");

    /**
     * name
     * */
    private String name;

    /**
     * flag
     * */
    private String flag;

    EnumTest(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }

    @Override
    public String toString(){
        return this.name + this.flag;
    }

    public String value(){
        return this.flag;
    }
}
