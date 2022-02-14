import java.net.*;
import java.io.*;
import java.util.*;

//server class to create socket/port connection with client, as well as all the commands to be executed by client
public class Server {
    
    //variables
    private static ArrayList<String> logins = new ArrayList<>();
    static boolean loggedIn = false;
    static String user = "";
    static BufferedWriter bw = null;

    //main
    public static void main(String args[]) {
        readData();
        try {
            ServerSocket ss = new ServerSocket(9034);
            Socket s = ss.accept();
            DataInputStream din = new DataInputStream(s.getInputStream()); //input variable
            DataOutputStream dout = new DataOutputStream(s.getOutputStream()); //output variable
            String str = "";
            dout.writeUTF("Please type 'LOGIN' followed by your username, space, and then your password. Then hit enter."); //message to client before commands code for login
            
            //while loop, checks if "LOGIN" is correctly done (based on usernames/password in "logins.txt" folder)
            while (true) {
                str = din.readUTF();
                if (loggedIn == false && str.startsWith("LOGIN"))
                {
                    String u = str.split(" ")[1];
                    String p = str.split(" ")[2];
                    if (logins.contains(u + " " + p)) {
                        loggedIn = true;
                        user = u;
                        dout.writeUTF("SUCCESS\nPlease type 'SOLVE' followed by -c or -r (for circle or rectangle), and then the number and hit enter!"); //output message after sucessful login, instructing user on next steps
                        dout.flush();
                    } else {
                        dout.writeUTF("FAILURE: Please provide correct username and password. Try again"); //if login attempt with an account not in logins.txt
                        dout.flush();
                    }
                    continue;
                }
                //shutdown command, system closes if "SHUTDOWN" is entered
                if (loggedIn) {
                    if (str.equalsIgnoreCase("SHUTDOWN")) {
                        dout.writeUTF("200 ok");
                        dout.flush();
                        din.close();
                        s.close();
                        ss.close();
                        System.exit(0);
                    }
                    //LOGOUT command
                    else if (str.equalsIgnoreCase("LOGOUT")) {
                        dout.writeUTF("200 ok");
                        dout.flush();
                    }
                    //SOLVE command
                    else if (str.startsWith("SOLVE")) {
                        bw = new BufferedWriter(new FileWriter(user + "_solutions.txt", true)); //creats a text file with username where every input is appended
                        String[] shape = str.split(" ");
                        //code for -c, circle, includes circumference + area formula and output messages
                        if (shape[1].equals("-c")) {  
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
                        } 
                        //code for -r, rectangle, includes what to do if only one number is entered, how to find area, and perimetet + messages to client
                        else if (shape[1].equals("-r")) {    
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

                    } 
                    //LIST command, outputs everything client logged in has ever done if they write "LIST"
                    else if (str.startsWith("LIST")) {
                        if (str.equals("LIST")) {
                            Scanner sc = new Scanner(new File(user + "_solutions.txt"));
                            String result = user + "\n";
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                result += "\t" + line + "\n";
                            }
                            dout.writeUTF(result);
                            dout.flush();
                        } 
                        "else if statement to show what every user has done, but only to root user
                        else if (str.split(" ").length == 2) {
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
                            }
                            //output if another client tries to list all 
                            else {
                                dout.writeUTF("Error: you are not the root user");
                                dout.flush();
                            }
                        }
                    }
                    //invalide command statement
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

    //function to read data from logins.txt file
    public static void readData() {
        try {
            //directory to computer, directory has to be changed 
            //if being ran on another computer to the directory of that logins.txt file
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
