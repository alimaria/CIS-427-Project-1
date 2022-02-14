https://github.com/alimaria/CIS-427-Project-1.git

Maria Ali and Summer Alrayyashi's Project 1 

Commands Implemented:
LOGIN:
//while loop, checks if "LOGIN" is correctly done (based on usernames/password in "logins.txt" folder)
            while (true) {
                str = din.readUTF();
                if (loggedIn == false && str.startsWith("LOGIN")) {
                    String u = str.split(" ")[1];
                    String p = str.split(" ")[2];
                    if (logins.contains(u + " " + p)) {
                        loggedIn = true;
                        user = u;
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
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
SOLVE:
//SOLVE command
                    else if (str.startsWith("SOLVE")) {
                        bw = new BufferedWriter(new FileWriter(user + "_solutions.txt", true)); //creates a text file with username where every input is appended
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

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

LIST:
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
                        } else {
                                        result += "\tNo interactions yet\n";
}

LIST -all:
  //else if statement to show what ever user has done, but only to root user
                        else if (str.split(" ").length == 2) {
                            if (user.equals("root")) {
                                String result = "";
                                for (int a = 0; a < logins.size(); a++) {
                                    String user = logins.get(a).split(" ")[0];
                                    result += user + "\n";
                                    File f = new File(user + "_solutions.txt");
                                    if (f.exists()) {
                                        Scanner sc = new Scanner(f);
                                        while (sc.hasNextLine()) {
                                            String line = sc.nextLine();
                                            result += "\t" + line + "\n";
                                        }
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

SHUTDOWN:
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
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

LOGOUT:
 //LOGOUT command
                    else if (str.equalsIgnoreCase("LOGOUT")) {
                        dout.writeUTF("200 ok");
                        dout.flush();

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

HOW TO BUILD AND RUN:
In order to run the program we must run the server before running the client or we would not get a proper connection. When building it with all the commands implemented, we have decided to run it all through an if statement. Where once one command has been implemented, it will continue to the next or determine if it will be continuing through with another command. For example: With the SOLVE command, it gives you the if statement of whether you have input the -c command otherwise the else statement will give you the -r command and the following output of the circumference or perimeter. With the LIST statement, it gives you the if statement of whether you want the interactions from the current user or all. With the SHUTDOWN and LOGOUT command, the if statement automatically runs one and if not then the other based on the input.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

BUGS:
The known bugs we have found in our project is we have been running the client before the server and kept getting a connection refused error. However, we realized the issue and fixed that. Another bug is once inputting LOGOUT or SHUTDOWN, the “200 ok” does not seem to come up, however the system does logout and shutdown. Also, when inputting a failed LOGIN, and then trying to shutdown or logout, nothing happens. 

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

OUTPUTS:
Outputs for all commands from the client side are found in the 'outputs.pdf' file right above this read me file! Please check that out for an organized showcasing of all the commands. Server and Client source codes can be found in the src folder!


