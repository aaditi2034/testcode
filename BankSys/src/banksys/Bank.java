/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksys;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author all
 */
public class Bank {
    
    Db db = new Db();        
    Connection con = db.getConnection();
    PreparedStatement stmt;
        
    Scanner s=new Scanner(System.in);
    int i=0;
    String AccHolderName;
    int AccNo;
    int t_id=0;
    double AccountBalance;
    Account a=new Account();
    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Date dateobj=new Date();
    public static HashMap<Integer,Object> hm =new HashMap<>();
      
       
    void credit(int AccNo) throws IOException, SQLException
    {    
        if(hm.containsKey(AccNo))
        {
            Object v=hm.get(AccNo);
            t_id=t_id+1;
            a=(Account)v;
            System.out.print("Enter the amount you want credit :");
            double amount=s.nextDouble();
            if(amount>0)
            {
                a.AccountBalance=a.AccountBalance+amount;
                Account.miniS.add(AccNo+"#"+df.format(dateobj)+"#"+amount+"#Credited#"+a.AccountBalance);
                
                               
                try{
                    System.out.println("enterining in tranaction........");
                    
                    stmt = con.prepareStatement("select c_id from account_details where acno=?");
                    stmt.setInt(1, AccNo);
                    ResultSet rs = stmt.executeQuery();
                    int ab = rs.getInt(2);
                    
                    System.out.println("CID IS "+ ab);
                    
                    
                    stmt = con.prepareStatement("insert into transaction values(?,?,?,?,?,?)");
                    stmt.setInt(1,t_id);
                    stmt.setInt(2,AccNo);
                    stmt.setInt(3,ab);
                    stmt.setString(4,df.format(dateobj));
                    stmt.setString(5,"Credit");
                    stmt.setDouble(6,amount);
                    
                    stmt.executeUpdate();
                
                    System.out.println("Values inserted....");
                    
                    
                }catch(Exception e){System.out.println("Not inserted value due to "+e.getMessage());}
                
                try{
                    FileWriter file = new FileWriter("F:\\languages\\Internship_Internity\\files\\"+AccNo+"_"+a.AccHolderName+".txt",true);
                    file.append(df.format(dateobj)+"  "+amount+"  credited   " +a.AccountBalance);
                    file.append(System.getProperty( "line.separator" ));
                    file.close();
                }catch(Exception e){
                System.out.print(e);
            }
           
            System.out.println("Do you want the receipt\n1:Yes\n2:No");
            int n=s.nextInt();
            if(n==1)
                System.out.println("Account Number: "+AccNo+"\nAccount holder name:  "+a.AccHolderName+"\nAmount credited :"+amount+"\nAccount Balance :"  +a.AccountBalance);
            System.out.println("Have a GOOD DAY..!!");
        }
        else
           System.out.println("please enter the valid ammount");
    }
    else 
        System.out.println("please enter the valid account number");
}
    
    void debit(int AccNo) throws SQLException
    {   
        if(hm.containsKey(AccNo))
        {  
            Object v=hm.get(AccNo);
            a=(Account)v;
            System.out.print("Enter the amount you want debited :");
            double amount=s.nextDouble();
        
            if(amount<a.AccountBalance && amount<30000)
            {   
                a.AccountBalance=a.AccountBalance-amount;
          
                Account.miniS.add(AccNo+"#"+df.format(dateobj)+"#"+amount+"#Debited#"+a.AccountBalance);
                
                stmt = con.prepareStatement("select c_id from account_details where acno=?");
                stmt.setInt(1, AccNo);
                ResultSet rs = stmt.executeQuery();
                int ab = rs.getInt(2);
                
                try{
                    stmt = con.prepareStatement("select c_id from account_details where acno=?");
                    stmt.setInt(1, AccNo);
                    rs = stmt.executeQuery();
                    ab = rs.getInt(2);

                    
                    stmt = con.prepareStatement("insert into transaction values(?,?,?,?,?,?)");
                    stmt.setInt(1,t_id);
                    stmt.setInt(2,AccNo);
                    stmt.setInt(3,ab);
                    stmt.setString(4,df.format(dateobj));
                    stmt.setString(5,"Debit");
                    stmt.setDouble(6,amount);
                    
                    stmt.executeUpdate();
                
                }catch(Exception e){}
                
                
                try{
                    FileWriter file = new FileWriter("F:\\languages\\Internship_Internity\\files\\"+AccNo+"_"+a.AccHolderName+".txt",true);
                    file.append(df.format(dateobj)+"  "+amount+"  debited   " +a.AccountBalance);
                    file.append(System.getProperty( "line.separator" ));
                    file.close();
                }catch(Exception e){
                System.out.print(e);
                }
                System.out.println("Do you want the receipt\n1:Yes\n2:No");
                int n=s.nextInt();
                if(n==1)
                    System.out.println("Account Number: "+AccNo+"\nAccount holder name:  "+a.AccHolderName+"\nAmount Debited :"+amount+"\nAccount Balance :"  +a.AccountBalance);
                System.out.println("Have a GOOD DAY..!!");
            }
            else
                System.out.println("please enter the valid ammount");
        }
        else
            System.out.println("please enter the valid account number");
    }
    
}
