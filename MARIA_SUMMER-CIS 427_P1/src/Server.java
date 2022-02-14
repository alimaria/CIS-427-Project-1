import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

    private static ArrayList<String> logins = new ArrayList<>();
    static boolean loggedIn = false;
    static String user = "";
    static BufferedWriter bw = null;

    public static void main(String args[]) {
        readData();
        try {
            ServerSocket ss = new ServerSocket(9034);
            Socket s = ss.accept();
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            String str = "";
            dout.writeUTF("Please type 'LOGIN' followed by your username, space, and then your password. Then hit enter.");
            while (true) {
                str = din.readUTF();
                if (loggedIn == false && str.startsWith("LOGIN"))
                {
                    String u = str.split(" ")[1];
                    String p = str.split(" ")[2];
                    if (logins.contains(u + " " + p)) {
                        loggedIn = true;
                        user = u;
                        dout.writeUTF("SUCCESS\nPlease type 'SOLVE' followed by -c or -r (for circle or rectangle), and then the number and hit enter!");
                        dout.flush();
                    } else {
                        dout.writeUTF("FAILURE: Please provide correct username and password. Try again");
                        dout.flush();
                    }
                    continue;
                }
                if (loggedIn) {
                    if (str.equalsIgnoreCase("SHUTDOWN")) {
                        dout.writeUTF("200 ok");
                        dout.flush();
                        din.close();
                        s.close();
                        ss.close();
                        System.exit(0);
                    }
                    else if (str.equalsIgnoreCase("LOGOUT")) {
                        dout.writeUTF("200 ok");
                        dout.flush();
                    }
                    else if (str.startsWith("SOLVE")) {
                        bw = new BufferedWriter(new FileWriter(user + "_solutions.txt", true));
                        String[] shape = str.split(" ");
                        if (shape[1].equals("-c")) {  //CIRCLE
                            if (shape.length == 3) {
                                int radius = Integer.parseInt(shape[2]);
                                double circum = 2 * Math.PI * radius;
                                double area = Math.PI * radius * radius;

                                dout.writeUTF("Circle’s circumference is " + String.format("%.2f", circum)
                                        + " and area is " + String.format("%.2f", area));
                              //  dout.flush();
                                bw.write("radius " + radius + ": Circle’s circumference is " + String.format("%.2f", circum)
                                        + " and area is " + String.format("%.2f", area));
                                bw.newLine();
                                bw.close();
                            } else {
                                dout.writeUTF("Error: No radius found");
                                dout.flush();
                                bw.write("Error: No radius found");
                                bw.newLine();
                                bw.close();
                            }
                        } else if (shape[1].equals("-r")) {    //RECT with 1 value
                            if (shape.length == 3) {
                                int length = Integer.parseInt(shape[2]);
                                double perimeter = 2 * (length + length);
                                double area = length * length;

                                dout.writeUTF("Rectangle’s perimeter is " + String.format("%.2f", perimeter)
                                        + " and area is " + String.format("%.2f", area));
                                dout.flush();
                                bw.write("sides " + length + " " + length + ": Rectangle’s perimeter is " + String.format("%.2f", perimeter)
                                        + " and area is " + String.format("%.2f", area));
                                bw.newLine();
                                bw.close();
                            } else if (shape.length == 4) {
                                int length = Integer.parseInt(shape[2]);
                                int width = Integer.parseInt(shape[3]);
                                double perimeter = 2 * (length + width);
                                double area = length * width;

                                dout.writeUTF("Rectangle’s perimeter is " + String.format("%.2f", perimeter)
                                        + " and area is " + String.format("%.2f", area));
                                dout.flush();
                                bw.write("sides " + length + " " + width + ": Rectangle’s perimeter is " + String.format("%.2f", perimeter)
                                        + " and area is " + String.format("%.2f", area));
                                bw.newLine();
                                bw.close();
                            } else {
                                dout.writeUTF("Error: No sides found");
                                dout.flush();
                                bw.write("Error: No sides found");
                                bw.newLine();
                                bw.close();
                            }
                        }

                    } else if (str.startsWith("LIST")) {
                        if (str.equals("LIST")) {
                            Scanner sc = new Scanner(new File(user + "_solutions.txt"));
                            String result = user + "\n";
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                result += "\t" + line + "\n";
                            }
                            dout.writeUTF(result);
                            dout.flush();
                        } else if (str.split(" ").length == 2) {
                            if (user.equals("root")) {
                                String result = "";
                                for (int a = 0; a < logins.size(); a++) {
                                    String user = logins.get(a).split(" ")[0];
                                    result += user + "\n";
                                    File f = new File(user + "_solutions.txt");
                                    if(f.exists()){
                                        Scanner sc = new Scanner(f);
                                        while (sc.hasNextLine()) {
                                            String line = sc.nextLine();
                                            result += "\t" + line + "\n";
                                        }
                                    }
                                    else{
                                        result += "\tNo interactions yet\n";
                                    }
                                }
                                dout.writeUTF(result);
                                dout.flush();
                            } else {
                                dout.writeUTF("Error: you are not the root user");
                                dout.flush();
                            }
                        }
                    }
                    else {
                       dout.writeUTF("300 invalid command!");
                        dout.flush();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    public static void readData() {
        try {
            Scanner in = new Scanner(new File("C:\\Users\\salra\\IdeaProjects\\JavaProject1\\logins.txt"));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                logins.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

}
