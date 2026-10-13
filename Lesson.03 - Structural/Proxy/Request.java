/** A serializable representation of a remote method call. */
public record Request(String method, Object[] args) { }
