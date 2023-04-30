import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(10314);) {
            // Read the bytes
            // Deserialize using ObjectInputStream
            // process the data that is sent here in the server to the methods in the server.
            // can create another response method if there are any exceptions in the REmote Method.
            
            Socket client = null;
            while ((client = server.accept()) != null) {
                InputStream is = client.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                RemoteMethod rm = (RemoteMethod) ois.readObject();

                String method = rm.getMethodName();
                Object arr[] = rm.getArguments();
                Object ret = null;

                if (method.equals("add")) {
                    ret = add((int) arr[0], (int) arr[1]);
                } else if (method.equals("divide")) {
                    ret = divide((int) arr[0], (int) arr[1]);
                } else {
                    ret = echo((String) arr[0]);
                }

                RemoteMethod serial = new RemoteMethod(method, new Object[]{ret});
                OutputStream os = client.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(serial);

                oos.flush();
                ois.close();
                oos.close();
                is.close();
                os.close();
                client.close();
            }
            server.close();
        } catch (Exception err) {
            err.printStackTrace();
        } 
    }
    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}