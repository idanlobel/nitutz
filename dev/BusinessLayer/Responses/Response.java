package BusinessLayer.Responses;

public abstract class Response<T> {

    String msg;
    public Response(String msg){
        this.msg=msg;
    }
    public abstract boolean isError();
    public abstract T getValue();

    public String getMsg() {
        return msg;
    }
}
