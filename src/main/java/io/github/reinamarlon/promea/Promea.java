package io.github.reinamarlon.promea;

@Deprecated(forRemoval = false)
public final class Promea {

    private Promea() {
    }

    public static io.github.reinamarlon.promea.api.Promea create() {
        return io.github.reinamarlon.promea.api.Promea.create();
    }
}
