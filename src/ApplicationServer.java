import java.rmi.registry.*;

public class ApplicationServer {
    public static void main(String[] args) {
        int registryPort = 20345;
        System.out.println("RMIRegistry port = " + registryPort);

        try {
            ApplicationHandlerImpl handler = new ApplicationHandlerImpl();

            Registry registry = LocateRegistry.createRegistry(registryPort);
            System.out.println("RMI Registry created on port " + registryPort);

            String name = "ApplicationHandler";
            //Bind the remote object to the registr
            registry.rebind(name, handler);
            System.out.println("ApplicationHandler server is running...");
            System.out.println("Service bound to name: " + name);
        } catch (Exception e) {
            System.err.println("Application Server exception: " + e);
            e.printStackTrace();
        }
    }
}