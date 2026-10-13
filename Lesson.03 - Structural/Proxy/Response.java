/** The result of a remote call. */
public record Response(Object value, String error) {

    public static Response ok(Object value)   { return new Response(value, null); }
    public static Response fail(String error) { return new Response(null, error); }

    public boolean hasError() { return error != null; }
}
