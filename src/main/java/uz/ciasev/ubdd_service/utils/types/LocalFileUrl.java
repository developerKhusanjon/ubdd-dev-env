package uz.ciasev.ubdd_service.utils.types;

//import uz.ciasev.ubdd_service.utils.serializer.LocalFileUrlSerializer;

//@JsonSerialize(using = LocalFileUrlSerializer.class)
public class LocalFileUrl extends ApiUrl {

    public static String FILES_WEB_URL = "/files/";

    public LocalFileUrl(String uri) {
        super(FILES_WEB_URL + uri);
    }

    public static LocalFileUrl ofNullable(String uri) {
        if (uri == null || "".equals(uri)) return null;
        return new LocalFileUrl(uri);
    }
}
