package Stock_Module.stock_service_layer.Responses;

public class IsOk<T> extends Response<T> {
    private final T object;
    public IsOk(T object, String msg) {
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
