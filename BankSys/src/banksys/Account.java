/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksys;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Arpit GUPTA
 */
public class Account {
    
    //Connection Created
    Db db = new Db();        
    Connection con = db.getConnection();
    PreparedStatement stmt;
    
    
    // DEclaration of Variables used
    Scanner s=new Scanner(System.in);
    int c_id;
    String Address;
    String mobile;
    String gender;
    int age;
    String AdhharNo;
    String AccHolderName;
    int AccNo;
    double AccountBalance;
    String type;
    int i=0;
    Account[] a=new Account[5];
    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Date dateobj=new Date();
    Random r = new Random();
    
    public Account()
    {
        AccHolderName="";
        AccNo=0;
        AccountBalance=0;
        Address="";
        mobile="";
        age=10;
        AdhharNo="200100021786";
        c_id = 0;
        type="";
    }
    
    public Account(String Name,int AcNo,double AccBalance,String Address,String mobile,int age,String AdhharNo,String type,int c_id)
    {
        AccHolderName=Name;
        AccNo=AcNo;
        AccountBalance=AccBalance;
        Address=Address;
        mobile=mobile;
        age=age;
        AdhharNo="";
        type=type;
        c_id=c_id;
    }
    
    //crete() method that created the new customer in  bank and creates its file and entry in database 
    
    public void create() throws IOException
    {
        if(i<10)
        {try{
            System.out.println("Enter you name");
            AccHolderName=s.next();      
            
            System.out.println("Type of Account:\n Press 's' for Saving Account \n Press 'c' for Current Account ");
            type=s.next();
            if(type.equals('s')){
                type = "SavingAccount";
            }
            if(type.equals('c')){
                type="CurrentAccount";
            }
            
            System.out.println("Address: ");
            Address=s.next();
            
            System.out.println("Mobile: ");
            mobile=s.next();
            
            System.out.println("Age: ");
            age = s.nextInt();
            
            System.out.println("Gender: ");
            gender = s.next();
                        
            System.out.println("adhaar No: ");
            AdhharNo=s.next();
            
            System.out.println("*******************************************************************");
            
            AccNo=r.nextInt(100);
            System.out.println("Your account number is:"+AccNo);
            
            c_id = r.nextInt(100);
            System.out.println("Your customer id is:"+c_id);
            
            System.out.println("*******************************************************************");
            
            System.out.println("Enter an opening balance:");
            AccountBalance=s.nextDouble();     
          
            //creating file of customer
            
            a[i]=new Account(AccHolderName,AccNo,AccountBalance,Address,mobile,age,AdhharNo,type,c_id);
            Bank.hm.put(AccNo,a[i]);
            File file=new File("F:\\languages\\Internship_Internity\\files\\"+AccNo+"_"+AccHolderName+".txt");
                 
            try{
                if(file.createNewFile())
                { 
                    System.out.print("Account is created\n");
                    System.out.println("Customer ID: "+c_id+"\nAccount Number: "+AccNo+"\nAccount holder name:  "+a[i].AccHolderName+"\nAccount Type: "+type+"\nAccount Balance :"  +a[i].AccountBalance+"\nAccount creation date :" +df.format(dateobj));   
                }   
            }catch(IOException e){System.out.print(e);}
            
            i++;
            
            //Data entry in database
            try{
                //System.out.print("loading in dastabse");
               
                stmt = con.prepareStatement("insert into Customer values(?,?,?,?,?,?,?,?)");
                stmt.setInt(1,c_id);
                stmt.setString(2,AccHolderName);
                stmt.setString(3,Address);
                stmt.setString(4,mobile);
                stmt.setString(5,gender);
                stmt.setInt(6,age);
                stmt.setString(7,df.format(dateobj));
                stmt.setString(8,AdhharNo);
                
                stmt.executeUpdate();
                
            }catch(Exception e){System.out.println("Error : "+e.getMessage());}
            
            try{
                stmt = con.prepareStatement("insert into Account_Details values(?,?,?,?,?)");
                stmt.setInt(1,AccNo);
                stmt.setInt(2,c_id);
                stmt.setString(3,df.format(dateobj));
                stmt.setString(4,type);
                stmt.setDouble(5,AccountBalance);
                
                stmt.executeUpdate();
                
            }catch(Exception e){System.out.println("Error : "+e.getMessage());}

        }catch(Exception e){}}
        else
            System.out.print("user limit exceeded");
    
    }
    
    public static ArrayList<String> miniS=new ArrayList<>();
    
    // Ministatement() method
    
    void  ministatement(String AccNo)
    {
        int size=miniS.size();
        int count=0;
        for(int k=size-1;k>=0;k--)
        {
           String s1=miniS.get(k);
           if(s1.startsWith(AccNo) && count<5)
           {
                String[] arrSplit = s1.split("#"); 
                for(int j=0;j<arrSplit.length;j++)
                {
                    System.out.print(arrSplit[j]+" ");
                }
                System.out.print("\n");
                count++;
            }
        }
    }
    
    // AccountSummary() method
    
    void AccountSummary(String AccNo)
    {
        int size=miniS.size();
        for(int k=0;k<size;k++)
        {
           String s1=miniS.get(k);
           if(s1.startsWith(AccNo))
           {
                String[] arrSplit = s1.split("#");
                for (String arrSplit1 : arrSplit) 
                   System.out.print(arrSplit1 + " ");
                System.out.print("\n");
            } 
        }
    }
}

