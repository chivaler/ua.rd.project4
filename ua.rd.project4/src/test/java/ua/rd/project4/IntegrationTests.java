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
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.jsp.JettyJspServlet;
import ua.rd.project4.controller.MainController;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IntegrationTests {
    private static Server server;
    private static URI serverUri;

    @BeforeClass
    public static void startJetty() throws Exception {
        // Create Server
        server = new Server();
        server.setStopAtShutdown(true);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(0); // auto-bind to available port
        server.addConnector(connector);

//        ServletContextHandler context = new ServletContextHandler();
////        ServletHolder defaultServ = new ServletHolder("default", DefaultServlet.class);
//        ServletHolder defaultServ = new ServletHolder("default", MainController.class);
//        defaultServ.setInitParameter("resourceBase", System.getProperty("user.dir"));
//        defaultServ.setInitParameter("dirAllowed", "true");
//        context.addServlet(defaultServ, "/");
//        server.setHandler(context);

        String rootPath = MainController.class.getClassLoader().getResource(".").toString();
//        WebAppContext webAppContext = new WebAppContext();
        WebAppContext webAppContext = new WebAppContext(rootPath + "../../src/main/webapp", "");
//        webAppContext.setContextPath("/");
//        webAppContext.setResourceBase("src/main/webapp");
//        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webAppContext);

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
    public void doGet_Abracadabra() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/abracadabra").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.NOT_FOUND_404));
    }

    @Test
    public void doGet_insufficientPermissions() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/Controller?command=INVOICES").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.FORBIDDEN_403));
    }

    @Test
    public void doGet_insufficientPermissions_Cars() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/Controller?command=CARS").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.FORBIDDEN_403));
    }

    @Test
    public void doGet_insufficientPermissions_Admin() throws Exception {
        HttpURLConnection http = (HttpURLConnection) serverUri.resolve("/Controller?command=ADMIN").toURL().openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.FORBIDDEN_403));
    }

}