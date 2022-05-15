package src.TransportationsAndWorkersModule.Service.ServiceWorkers;

public class Response<T> extends Resp{
    public T value;
    private Response(T value, String msg)
    {
        super(msg);
        this.value = value;
    }
    public static <T> Response<T> FromValue(T value)
    {
        return new Response<T>(value, null);
    }
    public static <T> Response<T> FromError(String msg)
    {
        return new Response<T>(null, msg);
    }
}
