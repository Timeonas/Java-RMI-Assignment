import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    // Default RMI registry port
    private static final int RMI_PORT = 1099;
    // Name to bind the service in RMI registry
    private static final String SERVICE_NAME = "ApplicationHandler";

    public static void main(String[] args) {
        try {
            // Create the service implementation
            ApplicationHandlerImpl handler = new ApplicationHandlerImpl();

            // Create or get the RMI registry
            Registry registry;
            try {
                // Try to create registry
                registry = LocateRegistry.createRegistry(RMI_PORT);
                System.out.println("RMI Registry created on port " + RMI_PORT);
            } catch (Exception e) {
                // If registry already exists, get it
                registry = LocateRegistry.getRegistry(RMI_PORT);
                System.out.println("Using existing RMI Registry on port " + RMI_PORT);
            }

            // Bind the remote object to the registry
            registry.rebind(SERVICE_NAME, handler);
            System.out.println("ApplicationHandler server is running...");
            System.out.println("Service bound to name: " + SERVICE_NAME);

        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}