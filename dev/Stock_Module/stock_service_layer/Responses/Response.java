package Stock_Module.stock_service_layer.Responses;

public abstract class Response<T> {

    String msg;
    public abstract boolean isError();
    public Response(String msg){
        this.msg=msg;
    }
    public abstract T getValue();

    public String getMsg() {
        return msg;
    }
}
