import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    // Connect to server
    // create an instance of the remote method
    // RemoteMethod add = new RemoteMethod("add", newObjects[]{lhs, rhs});
    // ObjectOutputStream to serialize the add instance
    // Send that serialized data to the output stream
    // OutputStream os = socket.getOutputStream();

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        int sum = 0;
        try {
            Socket socket = new Socket("localhost", PORT);
            RemoteMethod add = new RemoteMethod("add", new Object[]{lhs, rhs});

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
     
            oos.writeObject(add);
            oos.flush();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            RemoteMethod addRM = (RemoteMethod) ois.readObject();
            Object[] arr = addRM.getArguments();
            sum = (int) arr[0];

            socket.close();
            oos.close();
            ois.close();
            os.close();
            is.close();
        } catch (Exception err) {
            if (err.getMessage().equals("Connection refused")) {
                System.out.println("Server is not running");
            } else {
                err.printStackTrace();
            }
        }
        return sum;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom) {
        int quotient = 0;
        
        if (denom==0) {
            throw new ArithmeticException();
        }

        try {
            Socket socket = new Socket("localhost", PORT);
            RemoteMethod divide = new RemoteMethod("divide", new Object[]{num, denom});

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
     
            oos.writeObject(divide);
            oos.flush();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            RemoteMethod divideRM = (RemoteMethod) ois.readObject();
            Object[] arr = divideRM.getArguments();
            quotient = (int) arr[0];

            socket.close();
            oos.close();
            ois.close();
            os.close();
            is.close();
        } catch (Exception err) {
            if (err.getMessage().equals("Connection refused")) {
                System.out.println("Server is not running");
            } else {
                err.printStackTrace();
            }
        }
        return quotient;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        String output = "";
        try {
            Socket socket = new Socket("localhost", PORT);
            RemoteMethod echo = new RemoteMethod("echo", new Object[]{message});

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
     
            oos.writeObject(echo);
            oos.flush();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            RemoteMethod echoRM = (RemoteMethod) ois.readObject();
            Object[] arr = echoRM.getArguments();
            output = (String) arr[0];
            
            socket.close();
            oos.close();
            ois.close();
            os.close();
            is.close();
        } catch (Exception err) {
            if (err.getMessage().equals("Connection refused")) {
                System.out.println("Server is not running");
            } else {
                err.printStackTrace();
            }
        }
        return output;
    }

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello").equals("You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");
        
        System.out.println(" Finished");
    }
}