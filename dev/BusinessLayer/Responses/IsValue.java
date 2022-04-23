package BusinessLayer.Responses;

public class IsValue<T> extends Response{
    private final T object;
    public IsValue(T object, String msg) {
        super(msg);
        this.object=object;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public T getValue() {
        return object;
    }


}
