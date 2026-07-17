package io.github.reinamarlon.promea.server;

/**
 * Contract for a server that can serve Promea dashboards.
 * Implementations are responsible for starting an HTTP server,
 * exposing the {@code /api/dashboard/{name}} endpoint,
 * and serving static assets (HTML, CSS, JS).
 */
public interface Server {
    /** Start the server. */
    void start();

    /** Stop the server. */
    void stop();
}
