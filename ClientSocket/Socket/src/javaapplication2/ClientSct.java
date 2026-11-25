/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
   import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class JavaApplication2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);

   
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server!2");
        BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        String res  = input.readLine();
        System.out.println(res);
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        output.println("Hello from the client2!");
        String message;
        while(true){
            output.println(sc.nextLine());
            String con = sc.nextLine();
            if(con.toLowerCase().equals("exit")){socket.close();break;}
            
        }
        
    }

    
}
