/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksys;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;


/**
 *
 * @author Arpit GUPTA
 */
public class BankSys{

    
    /**
     * @param args the command line arguments
     */
    
      
    public static void main(String[] args) throws IOException, SQLException {
        // TODO code application logic here
        Db db = new Db();        
        Connection con = db.getConnection();
        PreparedStatement stmt = null;
        
        Account a=new Account();
        Bank b = new Bank();
        int AccNo = 0;
        String accno;
        boolean quit=false;
        do
        {
            System.out.println("1: Create a new account");
            System.out.println("2: Saving Account");
            System.out.println("3: Cureent Account");
            int n=a.s.nextInt();
            switch(n)
            {
                case 1:
                a.create(); 
                break;
                case 2:
                    System.out.println("***************SAVING ACCOUNT**********************");
                    do{
                        System.out.println("1: Credit");
                        System.out.println("2: Debit");
                        System.out.println("3: MiniStatement");
                        System.out.println("4: Print the Passbook(Account summary)");
                        System.out.println("5: Quit");//quiting will delete all the existing files
                        System.out.println("6: Delete an account");// will delete only the file of current account number
                        int p=a.s.nextInt();
                        switch(p)
                        {

                            case 1:
                            System.out.println("Enter the Account no to credited from");
                            AccNo=a.s.nextInt();
                            b.credit(AccNo);
                            break;
                            case 2:
                            System.out.println("Enter the Account no to be debited  from");
                            AccNo=a.s.nextInt();
                            b.debit(AccNo);
                            break;
                            case 3:
                            System.out.println("Enter the Account no you want the mini statement of");
                            accno=a.s.next();
                            a.ministatement(accno);
                            break;
                            case 4:
                            System.out.println("Enter the Account no you want the Account Summary of");
                            accno=a.s.next();
                            a.AccountSummary(accno);
                            break;
                            case 5:
                                                        
                            File files =new File("F:\\languages\\Internship_Internity\\files");
                            File[] file=files.listFiles();
                            for(File i:file)
                                i.delete();
                            quit=true;
                            String query1 = "DELETE FROM customer";
                            stmt=con.prepareStatement(query1);
                            stmt.executeUpdate(query1);
                            
                            String query2 = "DELETE FROM Account_Details";
                            stmt=con.prepareStatement(query2);
                            stmt.executeUpdate(query2);
                            
                            String query3 = "DELETE FROM transaction";
                            stmt=con.prepareStatement(query3);
                            stmt.executeUpdate(query3);
                            
                            break;
                            case 6:
                            System.out.println("Enter the Account no you want to delete");
                            AccNo=a.s.nextInt();
                            if(Bank.hm.containsKey(AccNo))
                            {
                                Object v=Bank.hm.get(AccNo);
                                a=(Account)v;
                                String AccHolderName=a.AccHolderName;
                                
                                query1 = "Delete from Customer where(Select C_id from Customer where (Select Acno from transaction where Acno = ?) )";
                                stmt.setInt(1,AccNo);
                                stmt=con.prepareStatement(query1);
                                stmt.executeUpdate(query1);

                                query2 = "DELETE FROM Account_Details where AcNo=?";
                                stmt.setInt(1,AccNo);
                                stmt=con.prepareStatement(query2);
                                stmt.executeUpdate(query2);

                                query3 = "DELETE FROM transaction where Acno=?";
                                stmt.setInt(1,AccNo);
                                stmt=con.prepareStatement(query3);
                                stmt.executeUpdate(query3);
                                
                                File file1 = new File("F:\\languages\\Internship_Internity\\files\\"+AccNo+"_"+AccHolderName+".txt");
                                if(file1.delete())
                                    System.out.println("Account Deleted");
                                else
                                System.err.println("Error while Deleting an account");
                            }
                            break;
                            default:
                                System.out.print("invalid option");
                        }
                    }while(!quit);

                break;
                case 3:
                    System.out.println("***************CURRENT ACCOUNT**********************");
                    do{
                        System.out.println("1: Credit");
                        System.out.println("2: Debit");
                        System.out.println("3: MiniStatement");
                        System.out.println("4: Print the Passbook(Account summary)");
                        System.out.println("5: Quit");//quiting will delete all the existing files
                        System.out.println("6: Delete an account");// will delete only the file of current account number
                        int q=a.s.nextInt();
                        switch(q)
                        {
                            case 1:
                            System.out.println("Enter the Account no to credited from");
                            AccNo=a.s.nextInt();
                            b.credit(AccNo);
                            break;
                            case 2:
                            System.out.println("Enter the Account no to be debited  from");
                            AccNo=a.s.nextInt();
                            b.debit(AccNo);
                            break;
                            case 3:
                            System.out.println("Enter the Account no you want the mini statement of");
                            accno=a.s.next();
                            a.ministatement(accno);
                            break;
                            case 4:
                            System.out.println("Enter the Account no you want the Account Summary of");
                            accno=a.s.next();
                            a.AccountSummary(accno);
                            break;
                            case 5:
                                
                            String query1 = "DELETE FROM customer";
                            stmt=con.prepareStatement(query1);
                            stmt.executeUpdate(query1);
                            
                            String query2 = "DELETE FROM Account_Details";
                            stmt=con.prepareStatement(query2);
                            stmt.executeUpdate(query2);
                            
                            String query3 = "DELETE FROM transaction";
                            stmt=con.prepareStatement(query3);
                            stmt.executeUpdate(query3);
                            
                            File files =new File("F:\\languages\\Internship_Internity\\files\\");
                            File[] file=files.listFiles();
                            for(File i:file)
                                i.delete();
                            quit=true;
                            break;
                            case 6:
                            
                            query1 = "Delete from Customer where(Select C_id from Customer where (Select Acno from transaction where Acno = ?) )";
                            stmt.setInt(1,AccNo);
                            stmt=con.prepareStatement(query1);
                            stmt.executeUpdate(query1);

                            query2 = "DELETE FROM Account_Details where AcNo=?";
                            stmt.setInt(1,AccNo);
                            stmt=con.prepareStatement(query2);
                            stmt.executeUpdate(query2);

                            query3 = "DELETE FROM transaction where Acno=?";
                            stmt.setInt(1,AccNo);
                            stmt=con.prepareStatement(query3);
                            stmt.executeUpdate(query3);    
                                
                            System.out.println("Enter the Account no you want to delete");
                            AccNo=a.s.nextInt();
                            if(Bank.hm.containsKey(AccNo))
                            {
                                Object v=Bank.hm.get(AccNo);
                                a=(Account)v;
                                String AccHolderName=a.AccHolderName;
                                File file1 = new File("F:\\languages\\Internship_Internity\\files\\"+AccNo+"_"+AccHolderName+".txt");
                                if(file1.delete())
                                    System.out.println("Account Deleted");
                                else
                                System.err.println("Error while Deleting an account");
                            }
                            break;
                            default:
                                System.out.print("invalid option");
                        }
                    }while(!quit);

                break;
                default:
                    System.out.print("invalid option");
            }
        }while(!quit);
        
        System.out.println("\nThanks");
    }

      
}
