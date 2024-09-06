package response;

public class SuccessResponse<T> extends BaseResponse<T> {
    public SuccessResponse(String message) {
        super("failed", message, null);
    }
    public SuccessResponse(String message, T data) {
        super("failed", "Success", data);
    }
}
