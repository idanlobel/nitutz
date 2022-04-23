package BusinessLayer.Responses;

public abstract class Response<T> {

    String msg;
    public Response(String msg){

    }
    public abstract boolean isError();
    public abstract T getValue();
}
