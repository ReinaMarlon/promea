package io.github.reinamarlon.promea.theme;

public final class Theme {

    private final String name;
    private final ThemeMode mode;

    private Theme(String name, ThemeMode mode) {
        this.name = name;
        this.mode = mode;
    }

    public static Theme system() {
        return new Theme("promea", ThemeMode.SYSTEM);
    }

    public static Theme light() {
        return new Theme("promea", ThemeMode.LIGHT);
    }

    public static Theme dark() {
        return new Theme("promea", ThemeMode.DARK);
    }

    public static Theme of(String name, ThemeMode mode) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("theme name cannot be blank");
        }

        return new Theme(name, mode == null ? ThemeMode.SYSTEM : mode);
    }

    public String getName() {
        return name;
    }

    public ThemeMode getMode() {
        return mode;
    }
}
