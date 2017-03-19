package Models;

/**
 * Created by magomed on 17.03.17.
 */
public class Response<T> {
    private T response;

    public Response(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
