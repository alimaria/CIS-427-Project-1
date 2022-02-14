import java.net.*;
import java.io.*;

public class Client {

    public static void main(String args[]) {
        try {
            Socket s = new Socket("localhost", 9034);
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String command = "", response = "";
            while (true) {
                command = br.readLine();
                dout.writeUTF(command);
                dout.flush();
                response = din.readUTF();
                if (response.equalsIgnoreCase("200 ok")) {
                    dout.close();
                    s.close();
                    System.exit(0);
                }
                System.out.println(response);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
}

