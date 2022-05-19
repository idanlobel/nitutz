package SuppliersModule.BusinessLayer.Responses;

public class IsError extends Response{

    public IsError(String msg) {
        super(msg);
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
