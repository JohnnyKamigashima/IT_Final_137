import jdk.jfr.ContentType;

public class BaseConfig {
    protected static String baseUrl = "https://petstore.swagger.io/v2";
    protected String petUploadImageEndpoint = "/uploadImage";
    protected static String petEndpoint = "/pet";
    protected static String storeEndpoint = "/store/order";
    protected String inventoryEndpoint = "/store/inventory";
    protected String createUsersWithArrayInputEndpoint = "/createUsersWithArrayInput";
    protected String userEndpoint = "/user";
    protected String createUsersWithListInputEndpoint = "/createUsersWithListInput";
    protected String loginUserEndpoint = "/loginUser";
    protected String logoutUserEndpoint = "/logoutUser";
    protected String createUserEndpoint = "/createUser";

    protected static String contentType = "application/json";
}
