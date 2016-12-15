package ua.rd.project4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URI;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IntegrationTests {
    private static Server server;
    private static URI serverUri;

    @BeforeClass
    public static void startJetty() throws Exception {
        // Create Server
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(0); // auto-bind to available port
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler();
        ServletHolder defaultServ = new ServletHolder("default", DefaultServlet.class);
        defaultServ.setInitParameter("resourceBase", System.getProperty("user.dir"));
        defaultServ.setInitParameter("dirAllowed", "true");
        context.addServlet(defaultServ, "/");
        server.setHandler(context);

        // Start Server
        server.start();

        // Determine Base URI for Server
        String host = connector.getHost();
        if (host == null) {
            host = "localhost";
        }
        int port = connector.getLocalPort();
        serverUri = new URI(String.format("http://%s:%d/", host, port));
    }

    @AfterClass
    public static void stopJetty() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doGet_Root() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.OK_200));
    }

    @Test
    public void doGet_Abracadabra() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/abracadabra").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.NOT_FOUND_404));
    }

    @Test
    public void doGet_Controller() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/Controller").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.OK_200));
    }

    @Test
    public void doGet_insufficientPermissions() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/Controller?command=INVOICES").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.FORBIDDEN_403));
    }
}