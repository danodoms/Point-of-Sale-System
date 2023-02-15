/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bsit.pos_system;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Neqxy
 */
public class POS_JFrame extends javax.swing.JFrame {
    /**
     * Creates new form POS_JFrame
     */
    int maxQuantity = 0;
    public POS_JFrame() {
        initComponents();
        displayToTable();
        displaySalesHistory();
        displayAccountsTable();
        //displayDailySalesComboBox();
        adminDashboard();
       

    }
        public String loggedInAccount = "";
        
        public void total(){
            double total = 0;
            double grandTotal =0;
            
            //int row = purchase_table.getSelectedRow();
            int rowCount = purchase_table.getRowCount();
            int columnCount = purchase_table.getColumnCount();

            
            for(int a = 0; a <rowCount; a++){
               double quantity = Double.parseDouble(purchase_table.getValueAt(a,1)+"");
               double price =  Double.parseDouble(purchase_table.getValueAt(a,2)+"");
               total=quantity*price;
               purchase_table.setValueAt( String.format("%,.2f", total),a,3);
               grandTotal+=total;
            }
            
            
            //sets text for payment button
            if(grandTotal==0){
                payBtn.setText("Pay");
            }else{
                payBtn.setText("Pay "+"(Php "+String.format("%,.2f", grandTotal)+""+")");
            }
            
            
            
            finalTotalField.setText("Php "+String.format("%,.2f", grandTotal)+"");
            System.out.println("Total method success");
//            return grandTotal;
            
            
        }
    
        public void displayToTable(){
        DefaultTableModel model = (DefaultTableModel)product_table.getModel();
        DefaultTableModel adminTable = (DefaultTableModel)adminProductTable.getModel();
        DefaultTableModel restoreTable = (DefaultTableModel)restoreProductTable.getModel();
        try {
            model.setRowCount(0);
            adminTable.setRowCount(0);
            restoreTable.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line = null;
            int count = 0;
            
                while( (line = reader.readLine()) !=null ){
                    String[] lineData = line.split(";");
                    count++;
                    if(lineData[5].equals("1")){
                        model.addRow(new Object[] {lineData[0],lineData[1],lineData[2],lineData[3]});
                        adminTable.addRow(new Object[] {lineData[0],lineData[1],lineData[2],lineData[3],lineData[4]});
                    }else if(lineData[5].equals("0")){
                        restoreTable.addRow(new Object[] {lineData[0],lineData[1],lineData[2],lineData[3],lineData[4]});
                    }
                   
                }
             
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
         
        
      public void searchToTable(String search){
        DefaultTableModel model = (DefaultTableModel)product_table.getModel();
        try {
            model.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line = null;
            int count = 0;
            
                while( (line = reader.readLine()) !=null ){
                    String[] lineData = line.split(";");
                    count++;
                    if(lineData[0].toLowerCase().contains(search) || lineData[1].toLowerCase().contains(search) || 
                            lineData[2].toLowerCase().contains(search) ){
                          model.addRow(new Object[] {lineData[0],lineData[1],lineData[2],lineData[3]});
                    }     
                }
                
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
      
      
      public void displaySalesHistory(){
         DefaultTableModel model = (DefaultTableModel)salesTable.getModel();
        try {
            model.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("sales.txt"));
            String line = null;
            int count = 0;
            double totalSales = 0.0;
            
                while( (line = reader.readLine()) !=null ){
                    String[] lineData = line.split(";");
                    count++;
                    for(int a=0; a<lineData.length; a++){
                        if(a==0){
                            //displays date
                            model.addRow(new Object[] {lineData[0]});
                            
                        }else if(a==1){
                            //displays customer name
                            model.addRow(new Object[] {"Customer Name: " + lineData[1]});
                            
                        }else if(a==lineData.length-2){      
                            //displays total, and calculates total
                            model.addRow(new Object[] {"Total: " + lineData[a]});
                            totalSales += Double.parseDouble(lineData[a]+"");
                        }else if(a==lineData.length-1){
                            //displays cashier plus adds space
                            model.addRow(new Object[] {"Cashier: " + lineData[a]});
                            model.addRow(new Object[] {""});
                        }else{
                            //displays products purchased
                            model.addRow(new Object[] {lineData[a]});
                            
                        }
                       
                    }      
                }
                reader.close();
                
                totalSalesField.setText(totalSales+"");
                
                
                
                
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
      }
      
      public void userDisplaySalesHistory(JTable table, String username){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        try {
            model.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("sales.txt"));
            String line = null;
            int count = 0;
            double totalSales = 0.0;
            
                while( (line = reader.readLine()) !=null ){
                    String[] lineData = line.split(";");
                    count++;
                    
                    if(lineData[lineData.length-1].equals(username)){
                        for(int a=0; a<lineData.length; a++){
                            if(a==0){
                                //displays date
                                model.addRow(new Object[] {lineData[0]});

                            }else if(a==1){
                                //displays customer name
                                model.addRow(new Object[] {"Customer Name: " + lineData[1]});

                            }else if(a==lineData.length-2){      
                                //displays total, and calculates total
                                model.addRow(new Object[] {"Total: " + lineData[a]});
                                totalSales += Double.parseDouble(lineData[a]+"");
                            }else if(a==lineData.length-1){
                                //displays cashier plus adds space
                                model.addRow(new Object[] {"Cashier: " + lineData[a]});
                                model.addRow(new Object[] {""});
                            }else{
                                //displays products purchased
                                model.addRow(new Object[] {lineData[a]});
                            }
                        } 
                    }
                         
                }
                reader.close();
                
                
                userTotalSales.setText("Php "+String.format("%,.2f", totalSales));
              

                
                
                
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
      }
      
      public void stockModifier(String tableName, String operation, int value){
          
          
          try{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            
            int selectedRow = 0;
            String productID = "";
            String productName = "";

            
            if(tableName.equals("product_table")){
                selectedRow = product_table.getSelectedRow();
                productID = product_table.getValueAt(selectedRow,0)+"";

            }else if(tableName.equals("purchase_table")){
                selectedRow = purchase_table.getSelectedRow();
                productName = purchase_table.getValueAt(selectedRow,0)+"";
            }
           
            ArrayList<String> data = new ArrayList<>();
            
            String line = reader.readLine();
                boolean found = false;
                int count=0;
                while( line != null ){
                    data.add(line+'\n');
                    String[] arrayData = data.get(count).split(";");
                    int currentStock = Integer.parseInt(arrayData[3]+"");
                    
                    
                    //debug
//                    for(int x=0; x<4; x++){
//                        System.out.println(arrayData[x]);
//                    }
                    
                    if(tableName.equals("product_table")){
                        if(arrayData[0].equals(productID)){
                            found = true;
                            if(found==true){
                                maxQuantity = currentStock;
                            }
                            
                            int newStock = Integer.parseInt(arrayData[3].trim());
                            if(operation.equals("decrease")){
                                if(currentStock-value>=0){
                                    newStock-=value;
                                }else if(value>currentStock){
                                    newStock=0;
                                }
                                
                            }else if(operation.equals("increase")){
                                newStock+=value;
                            }
                            data.set(count, (arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+newStock+""+";"+arrayData[4]+";"+"1"+'\n'));
                        }
                        count++;
                        line=reader.readLine();
                    }else if(tableName.equals("purchase_table")){
                         if(arrayData[1].equals(productName)){
                            found = true;
                            if(found==true){
                                maxQuantity = currentStock;
                            }
                             
                            int newStock = Integer.parseInt(arrayData[3].trim());
                            if(operation.equals("decrease")){
                                if(currentStock-value>=0){
                                    newStock-=value;
                                }else if(value>currentStock){
                                    newStock=0;
                                }
                                
                            }else if(operation.equals("increase")){
                                newStock+=value;
                            }
                            data.set(count, (arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+newStock+""+";"+arrayData[4]+";"+"1"+'\n'));
                        }
                        count++;
                        line=reader.readLine();
                    }
                    
                    
                }
            
                FileWriter writer = new FileWriter("products.txt");
                for(int a=0; a<data.size(); a++){
                    //System.out.println(data.get(a));
                    writer.write(data.get(a));
                }
                writer.close();
                displayToTable();

          }catch(Exception ex){
           Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println(ex);
            ex.printStackTrace();
          }
      }
      
      
       public int maxQuantity(){
          int maxQuantity=0;
          try{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            
            int selectedRow = 0;
            selectedRow = purchase_table.getSelectedRow();
            String productName = purchase_table.getValueAt(selectedRow,0)+"";
            ArrayList<String> data = new ArrayList<>();
            String line = reader.readLine();
            
            int count=0;
            while( line != null ){
                data.add(line+'\n');
                String[] arrayData = data.get(count).split(";");
                int currentStock = Integer.parseInt(arrayData[3]+"");

                if(arrayData[1].equals(productName)){   
                       maxQuantity = Integer.parseInt(arrayData[3]);
                }
                
                count++;
                line=reader.readLine();
            }
                    
            }catch(Exception ex){
                Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
                    
            return maxQuantity;
      }
       
       
      
      public void customStockModifier(String tableName, int value){
          try{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            
            int selectedRow = 0;
            String productID = "";
            String productName = "";
            
            if(tableName.equals("product_table")){
                selectedRow = product_table.getSelectedRow();
                productID = product_table.getValueAt(selectedRow,0)+"";
            }else if(tableName.equals("purchase_table")){
                selectedRow = purchase_table.getSelectedRow();
                productName = purchase_table.getValueAt(selectedRow,0)+"";
            }
           
            ArrayList<String> data = new ArrayList<>();
            
            String line = reader.readLine();
                int count=0;
                while( line != null ){
                    data.add(line+'\n');
                    String[] arrayData = data.get(count).split(";");
                    
                    //debug
                    for(int x=0; x<4; x++){
                        System.out.println(arrayData[x]);
                    }
                    
                    if(tableName.equals("product_table")){
                        if(arrayData[0].equals(productID)){
                            int newStock = Integer.parseInt(arrayData[3].trim());
                            newStock=value;
                            data.set(count, (arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+newStock+""+";"+arrayData[4]+";"+"1"+'\n'));
                        }
                        count++;
                        line=reader.readLine();
                    }else if(tableName.equals("purchase_table")){
                         if(arrayData[1].equals(productName)){
                            int newStock = Integer.parseInt(arrayData[3].trim());
                            newStock=value;
                            data.set(count, (arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+newStock+""+";"+arrayData[4]+";"+"1"+'\n'));
                        }
                        count++;
                        line=reader.readLine();
                    } 
                }
            
                FileWriter writer = new FileWriter("products.txt");
                for(int a=0; a<data.size(); a++){
                    System.out.println(data.get(a));
                    writer.write(data.get(a));
                }
                writer.close();
                displayToTable();

          }catch(Exception ex){
           Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println(ex);
            ex.printStackTrace();
          }
      }
      
      public void dailySalesBuilder(){
          //displayDailySalesComboBox();
          try{
              BufferedReader reader = new BufferedReader(new FileReader("sales.txt"));
              String line = reader.readLine();
              int count = 0;
              String previousDate = "";
              ArrayList<String> dailySalesFinalData = new ArrayList<>();
              while( line != null ){
                String[] lineData = line.split(";");
                
                //fetches all products and places it to productAndQuantity
                //then splits the productAndQuantity into separate variables
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> quantity = new ArrayList<>();
                for(int a=0; a<lineData.length-2; a++){
                    if(a>=2 && a!=lineData.length){
                        String[] productAndQuantity = lineData[a].split(" × ");
                        name.add(productAndQuantity[0]);
                        quantity.add(productAndQuantity[1]);
                    }
                }
                 
                if(lineData[0].equals(previousDate)){
                    String[] dailySalesArray = dailySalesFinalData.get(dailySalesFinalData.size()-1).split(";");
                    String newDailySalesData = lineData[0];
                    
                    //checks and updates all quantities for the same products purchased on the same date
                    for(int dailySalesIndex = 1; dailySalesIndex<dailySalesArray.length; dailySalesIndex+=2){      
                        for(int x = 0;  x<name.size(); x++){
                            if(dailySalesArray[dailySalesIndex].equals(name.get(x))){
                               dailySalesArray[dailySalesIndex+1] = (Integer.parseInt(dailySalesArray[dailySalesIndex+1]) + Integer.parseInt(quantity.get(x)))+"";
                               newDailySalesData = newDailySalesData.concat(";" + dailySalesArray[dailySalesIndex] + ";" + dailySalesArray[dailySalesIndex+1]);
                            }      
                        }    
                    }
                    
                    //re-adds all products that are not updated on previous loop
                    for(int dailySalesIndex = 1; dailySalesIndex<dailySalesArray.length; dailySalesIndex+=2){
                        if(!(newDailySalesData.contains(dailySalesArray[dailySalesIndex]))){
                            newDailySalesData = newDailySalesData.concat(";" + dailySalesArray[dailySalesIndex] + ";" + dailySalesArray[dailySalesIndex+1]);
                        }
                    }
                    
                    //re-adds all products that are not updated on previous loop
                    for(int x = 0;  x<name.size(); x++){
                        if(!(newDailySalesData.contains(name.get(x)))){
                            newDailySalesData = newDailySalesData.concat(";" + name.get(x) + ";" + quantity.get(x));                   
                        }
                    }
                    
                    
                    dailySalesFinalData.set(dailySalesFinalData.size()-1, newDailySalesData);
                    
                }else{
                    String dailySalesLineData = lineData[0];
                    for(int b=0; b<name.size(); b++){
                        dailySalesLineData = dailySalesLineData.concat(";" + name.get(b) + ";" + quantity.get(b));
                    }
                    dailySalesFinalData.add(dailySalesLineData);
                }

                previousDate = lineData[0];
                count++;
                line=reader.readLine();
              }
              reader.close();
               FileWriter dailySales_writer = new FileWriter("dailySales.txt");
                

            for(int k=0; k<dailySalesFinalData.size(); k++){
                dailySales_writer.write(dailySalesFinalData.get(k)+'\n');
            }
            dailySales_writer.close();
            displayDailySalesComboBox();
          }catch(Exception e){
              System.out.println(e);
               e.printStackTrace();
          }
          
      }
      
      public void displayDailySalesComboBox(){
        DefaultTableModel dailySalesTable_model = (DefaultTableModel)dailySalesTable.getModel();
        
        try {
            dailySalesComboBox.removeAllItems();
            dailySalesTable_model.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("dailySales.txt"));
            String line = reader.readLine();
            
            while(line != null){
                String[] lineData = line.split(";");
                dailySalesComboBox.addItem(lineData[0]);
                line = reader.readLine();
            }
            
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
      }
      
    public void receiptGenerator(){
        LocalDate date = LocalDate.now();
                              receiptPane.setText(""+'\n');
        receiptPane.setText(receiptPane.getText()+"\t\tReceipt\t\t"+'\n');
        receiptPane.setText(receiptPane.getText()+"\t\t"+date+"\t\t"+'\n');
        receiptPane.setText(receiptPane.getText()+""+'\n');
        receiptPane.setText(receiptPane.getText()+"-----------------------------------------------------------------------------------------"+'\n');
        receiptPane.setText(receiptPane.getText()+" Name\t\tPrice\tQty\tTotal Price"+'\n');
        receiptPane.setText(receiptPane.getText()+"-----------------------------------------------------------------------------------------"+'\n');
        
        
        int rowCount = purchase_table.getRowCount();
        int columnCount = purchase_table.getColumnCount();

        for(int a = 0; a <rowCount; a++){
           String name = purchase_table.getValueAt(a,0)+"";
                if(name.length()>=10){
                    name = name.substring(0,9)+"...";
                }
                
           String quantity = purchase_table.getValueAt(a,1)+"";
           String price =  purchase_table.getValueAt(a,2)+"";
           String subtotal = purchase_table.getValueAt(a,3)+"";
           
           receiptPane.setText(receiptPane.getText()+name+"\t\t"+price+"\t"+quantity+"\t"+subtotal+'\n');
        }  
           double cash = 0.00;
           
           if (!(cashField.getText().equals(""))){
               cash = Double.parseDouble(cashField.getText());
           }
               
           
           receiptPane.setText(receiptPane.getText()+"-----------------------------------------------------------------------------------------"+'\n');
           receiptPane.setText(receiptPane.getText()+"Total\t\t\t\t"+finalTotalField.getText().replace("Php ","")+'\n');
           receiptPane.setText(receiptPane.getText()+"Cash\t\t\t\t"+String.format("%,.2f",cash)+'\n');
           receiptPane.setText(receiptPane.getText()+"Change\t\t\t\t"+changeField.getText()+'\n');
           receiptPane.setText(receiptPane.getText()+'\n'+"\t\tTHANK YOU!\t\t"+'\n');
        
    }
    
    public void setColor(JPanel panel){
        panel.setBackground(new Color(85,152,240));
    }
    
    public void resetColor(JPanel panel){
        panel.setBackground(new Color(47,128,237));
    }
      
    public void adminDashboard(){
        LocalDateTime date = LocalDateTime.now();  
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedDate = date.format(dateFormat);  
        
        ArrayList<Double> dailySales = new ArrayList<>();
        ArrayList<Double> monthlySales = new ArrayList<>();
        int transactionsToday = 0;
        
        double totalSales = Double.parseDouble(totalSalesField.getText().toString().replace("Php ", "").replace(",",""));
        adminTotalSales.setText("Php "+String.format("%,.2f", totalSales));
        totalSalesField.setText("Php "+String.format("%,.2f", totalSales));
        
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sales.txt"));
            String line = reader.readLine();
            String previousDate = "";
            String previousMonth = "";
            double salesToday = 0.0;
            double salesThisMonth = 0.0;
            int count = 0;
            
            while(line != null){
                String[] lineData = line.split(";");

                String[] calendar = lineData[0].split(" ");
                String month = calendar[0];
                String year = calendar[2];
                
                String[] currentCalendar = formattedDate.split(" ");
                String currentMonth = currentCalendar[0];
                String currentYear = currentCalendar[2];
                
                //adds all sales if current line has the current month and year
                if(month.equals(currentMonth) && year.equals(currentYear)){
                    salesThisMonth += Double.parseDouble(lineData[lineData.length-2]);
                }
                
                
                //increments transactionsTodal in 1 if the date in txt matches the current date
                //also calculates sales that are on the same date as the current date
                if(lineData[0].equals(formattedDate)){
                    transactionsToday++;
                    salesToday += Double.parseDouble(lineData[lineData.length-2]);
                }
                
                //calculates all total with the same date
                if(lineData[0].equals(previousDate)){
                    double newValue = Double.parseDouble(lineData[lineData.length-2]) + dailySales.get(dailySales.size()-1); 
                    dailySales.set(dailySales.size()-1, newValue);
                }else{
                    dailySales.add(Double.parseDouble(lineData[lineData.length-2]));
                }
                
                
                //calculates all total with the same month
                if(month.equals(previousMonth)){
                    double newValue = Double.parseDouble(lineData[lineData.length-2]) + monthlySales.get(monthlySales.size()-1); 
                    monthlySales.set(monthlySales.size()-1, newValue);
                }else{
                    monthlySales.add(Double.parseDouble(lineData[lineData.length-2]));
                }
                
                
                
                previousDate = lineData[0];
                previousMonth = month;
               
                line = reader.readLine();
                count++;
            }
            reader.close();
        
        //adds all daily sales and finds the mean
        double averageDailySales = 0;
        for (Double value : dailySales){
            averageDailySales += value;
        }
        averageDailySales = averageDailySales/dailySales.size();
        averageDailySalesLabel.setText("Php "+String.format("%,.2f", averageDailySales));
        
        
        //adds all monthly sales and finds the mean
        double averageMonthlySales = 0;
        for (Double value : monthlySales){
            averageMonthlySales += value;
        }
        averageMonthlySales = averageMonthlySales/monthlySales.size();
        averageMonthlySalesLabel.setText("Php "+String.format("%,.2f", averageMonthlySales));
            
        salesThisMonthLabel.setText("Php "+String.format("%,.2f", salesThisMonth));
        salesTodayLabel.setText("Php "+String.format("%,.2f", salesToday));
        
        
            BufferedReader dailySales_reader = new BufferedReader(new FileReader("dailySales.txt"));
            String dailySales_line = dailySales_reader.readLine();
            ArrayList<String> product = new ArrayList<>();
            ArrayList<Integer> quantity = new ArrayList<>();
            
            while(dailySales_line != null){
                String[] lineData = dailySales_line.split(";");
                
                for(int a=1; a<lineData.length; a+=2){
                    boolean exists = false;
                    int index = 0;
                    String searchProduct = lineData[a];
                    
                    
                    for(int b=0; b<product.size(); b++){
                        if(product.get(b).equals(searchProduct)){
                            exists = true;
                            index = b;
                            break;
                        } 
                    }
                    
                    
                    if(exists){
                        int oldQuantity = quantity.get(index);
                        int newQuantity = Integer.parseInt(lineData[a+1]) + oldQuantity;
                        
                        quantity.set(index, newQuantity);
                    }else{
                        product.add(lineData[a]);
                        quantity.add(Integer.parseInt(lineData[a+1]));
                    }
                    
                }
                
                dailySales_line = dailySales_reader.readLine();
            }
            dailySales_reader.close();
            
            ArrayList<Integer> sortedQuantity = new ArrayList<>();
            sortedQuantity.addAll(quantity);
            Collections.sort(sortedQuantity, Collections.reverseOrder());
            
            int first = sortedQuantity.get(0);
            int second = 0;
            int third = 0;
            boolean notFound = true;
           
            for(int r=0; r<sortedQuantity.size() && notFound; r++){
                if(sortedQuantity.get(r) != first){
                     second = sortedQuantity.get(r);
                     notFound = false;
                }
            }
            notFound = true;
            
            for(int r=0; r<sortedQuantity.size() && notFound; r++){
                if(sortedQuantity.get(r) != first && sortedQuantity.get(r) != second){
                     notFound = false;
                     third = sortedQuantity.get(r);
                }
            }

            
            String firstPlace = "";
            String secondPlace = "";
            String thirdPlace = "";
            
            for(int j=0; j<quantity.size(); j++){
                if(quantity.get(j) == first){
                    if(firstPlace.equals("")){
                        firstPlace = product.get(j);
                    }else{
                        firstPlace += ", "+product.get(j);
                    }
                }
                
                
                if(quantity.get(j) == second){
                    if(secondPlace.equals("")){
                        secondPlace = product.get(j);
                    }else{
                        secondPlace += ", "+product.get(j);
                    }
                }
                
                
                if(quantity.get(j) == third){
                    if(thirdPlace.equals("")){
                        thirdPlace = product.get(j);
                    }else{
                        thirdPlace += ", "+product.get(j);
                    }
                }
            }
            
            adminFirstPlace.setText(firstPlace);
            adminSecondPlace.setText(secondPlace);
            adminThirdPlace.setText(thirdPlace);
            
            int test = 0;
            
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        adminTransactionsToday.setText(transactionsToday+"");   
        
        
        
        
        int productCount = 0;
        int outOfStockProducts = 0;
        DefaultTableModel table = (DefaultTableModel)adminProductTable.getModel();
        int rowCount = table.getRowCount();
        for(int e=0; e<rowCount; e++){
            int currentStock = Integer.parseInt(table.getValueAt(e,3)+"");
            
            if(currentStock > 0){
                productCount++;
            }else{
                outOfStockProducts++;
                productCount++;
            }
        }
        
        productCountLabel.setText(productCount+"");
        outOfStockProductsLabel.setText(outOfStockProducts+"");
    }
    
    public void userDashboard(){
        LocalDateTime date = LocalDateTime.now();  
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedDate = date.format(dateFormat);  
        
        
        int transactionsToday = 0;
        
//        double totalSales = Double.parseDouble(totalSalesField.getText().toString());
//        adminTotalSales.setText("Php "+String.format("%,.2f", totalSales));
        double salesToday = 0.0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sales.txt"));
            String line = reader.readLine();

            while(line != null){
                String[] lineData = line.split(";");
                if(lineData[0].equals(formattedDate) && lineData[lineData.length-1].equals(loggedInAccount)){
                    transactionsToday++;
                    salesToday += Double.parseDouble(lineData[lineData.length-2]); 
                }
                line = reader.readLine();
            }
            reader.close();
            
            userSalesToday.setText("Php "+String.format("%,.2f", salesToday));
            userTransactionsToday.setText(transactionsToday+"");
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
          
        
        
       
       
    }
    
    public void displayAccountsTable(){
         DefaultTableModel accounts_table = (DefaultTableModel)accountsTable.getModel();
         DefaultTableModel recoverAccount_table = (DefaultTableModel)recoverAccountTable.getModel();
        try {
            accounts_table.setRowCount(0);
            recoverAccount_table.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            String line = null;
            int count = 0;
            
                while( (line = reader.readLine()) !=null ){
                    String[] lineData = line.split(";");
                    count++;
                    if(lineData[6].equals("1")){
                        accounts_table.addRow(new Object[] {lineData[0],lineData[2],lineData[3],lineData[4],lineData[5]});
                    }else if(lineData[6].equals("0")){
                        recoverAccount_table.addRow(new Object[] {lineData[0],lineData[2],lineData[3],lineData[4],lineData[5]});
                    }
                }
             
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        generalPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        LoginBtn = new javax.swing.JButton();
        showPasswordCheckBox = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        loginNotifLabel = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        addProductBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        qtyField = new javax.swing.JTextField();
        minusBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        payBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchase_table = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        product_table = new javax.swing.JTable();
        logOutBtn = new javax.swing.JButton();
        selectCategoryComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btn_manageProducts = new javax.swing.JPanel();
        adminAddProductButton = new javax.swing.JLabel();
        btn_salesHistory = new javax.swing.JPanel();
        salesHistBtn = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btn_restoreProducts = new javax.swing.JPanel();
        restoreProductBtn = new javax.swing.JLabel();
        adminLogOutBtn = new javax.swing.JLabel();
        btn_adminDashboard = new javax.swing.JPanel();
        adminDashboardLabel = new javax.swing.JLabel();
        btn_accounts = new javax.swing.JPanel();
        restoreProductBtn1 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        btn_recoverAccount = new javax.swing.JPanel();
        restoreProductBtn2 = new javax.swing.JLabel();
        adminPane = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        adminProductTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        adminProductIDField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        newProductNameField = new javax.swing.JTextField();
        newProductPriceField = new javax.swing.JTextField();
        newProductStockField = new javax.swing.JTextField();
        updateProductBtn = new javax.swing.JButton();
        adminAddProductBtn = new javax.swing.JButton();
        adminRemoveProductBtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        productCategoryComboBox = new javax.swing.JComboBox<>();
        adminAddProductButton1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        salesTable = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        totalSalesField = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        dailySalesTable = new javax.swing.JTable();
        dailySalesComboBox = new javax.swing.JComboBox<>();
        adminAddProductButton2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        restoreProductTable = new javax.swing.JTable();
        restoreBtn = new javax.swing.JButton();
        adminAddProductButton3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        adminAddProductButton6 = new javax.swing.JLabel();
        adminTransactionsToday = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        adminAddProductButton8 = new javax.swing.JLabel();
        salesThisMonthLabel = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        adminAddProductButton9 = new javax.swing.JLabel();
        salesTodayLabel = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        adminAddProductButton10 = new javax.swing.JLabel();
        adminTotalSales = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        adminAddProductButton12 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        adminAddProductButton14 = new javax.swing.JLabel();
        adminFirstPlace = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        adminAddProductButton15 = new javax.swing.JLabel();
        adminSecondPlace = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        adminAddProductButton16 = new javax.swing.JLabel();
        adminThirdPlace = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        adminAddProductButton13 = new javax.swing.JLabel();
        productCountLabel = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        adminAddProductButton18 = new javax.swing.JLabel();
        averageMonthlySalesLabel = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        adminAddProductButton19 = new javax.swing.JLabel();
        averageDailySalesLabel = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        adminAddProductButton21 = new javax.swing.JLabel();
        outOfStockProductsLabel = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        adminThirdPlace1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        accountsTable = new javax.swing.JTable();
        adminUsernameField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        adminLastNameField = new javax.swing.JTextField();
        adminFirstNameField = new javax.swing.JTextField();
        adminMidNameField = new javax.swing.JTextField();
        adminAccTypeComboBox = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        adminSaveCredsBtn = new javax.swing.JButton();
        adminDeactivateBtn = new javax.swing.JButton();
        adminChangePassBtn = new javax.swing.JButton();
        adminRegisterAccBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        currentPasswordField = new javax.swing.JPasswordField();
        showCurrentPassCheckBox = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        newPasswordField = new javax.swing.JPasswordField();
        confirmPasswordField = new javax.swing.JPasswordField();
        showNewPassCheckBox = new javax.swing.JCheckBox();
        showConfirmPassCheckBox = new javax.swing.JCheckBox();
        changePasswordBtn = new javax.swing.JButton();
        changePassReturnBtn = new javax.swing.JLabel();
        changePassUsernameLabel = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        changePassErrorDisplay = new javax.swing.JTextArea();
        changePassReturnBtn1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        passwordRegister = new javax.swing.JPasswordField();
        showRegisterPassCheckBox = new javax.swing.JCheckBox();
        confirmPassRegister = new javax.swing.JPasswordField();
        showRegisterConfirmPassCheckBox = new javax.swing.JCheckBox();
        usernameRegister = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        firstNameRegister = new javax.swing.JTextField();
        midNameRegister = new javax.swing.JTextField();
        lastNameRegister = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        registerBtn = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        accTypeRegister = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        registerReturnBtn = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        registerErrorDisplay = new javax.swing.JTextArea();
        changePassUsernameLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        recoverAccountTable = new javax.swing.JTable();
        adminRecoverAccBtn = new javax.swing.JButton();
        fourPayment = new javax.swing.JPanel();
        finalTotalField = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        finalPayBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cashField = new javax.swing.JTextField();
        changeLabel = new javax.swing.JLabel();
        changeField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        receiptPane = new javax.swing.JTextPane();
        printReceiptBtn = new javax.swing.JButton();
        sixPaymentSuccess = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        returnBtn1 = new javax.swing.JButton();
        sixUserDashboard = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        adminAddProductButton11 = new javax.swing.JLabel();
        userTotalSales = new javax.swing.JLabel();
        welcomeUserLabel = new javax.swing.JLabel();
        userSalesHistBtn = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        adminAddProductButton7 = new javax.swing.JLabel();
        userTransactionsToday = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        userSalesToday = new javax.swing.JLabel();
        adminAddProductButton20 = new javax.swing.JLabel();
        userLogoutBtn = new javax.swing.JLabel();
        pointOfSalesBtn = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        welcomeUserLabel2 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        userSalesTable = new javax.swing.JTable();
        userDashboardReturnBtn = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        newPassUserLabel = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        confirmNewPassField = new javax.swing.JPasswordField();
        showNewPassField = new javax.swing.JCheckBox();
        newPassContinueBtn = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        newPassField = new javax.swing.JPasswordField();
        showConfirmNewPassField = new javax.swing.JCheckBox();
        jScrollPane13 = new javax.swing.JScrollPane();
        newPassErrorDisplay = new javax.swing.JTextArea();
        newPassUserLabel1 = new javax.swing.JLabel();
        newPassUserLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setPreferredSize(new java.awt.Dimension(1080, 720));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        generalPane.setMinimumSize(new java.awt.Dimension(960, 720));
        generalPane.setPreferredSize(new java.awt.Dimension(1000, 720));

        jPanel2.setBackground(new java.awt.Color(47, 128, 237));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 56)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(47, 128, 237));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Welcome");

        usernameField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        usernameField.setForeground(new java.awt.Color(51, 51, 51));
        usernameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameField.setToolTipText("Username");
        usernameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        usernameField.setSelectionColor(new java.awt.Color(47, 128, 237));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        passwordField.setForeground(new java.awt.Color(51, 51, 51));
        passwordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        passwordField.setSelectionColor(new java.awt.Color(47, 128, 237));

        LoginBtn.setBackground(new java.awt.Color(47, 128, 237));
        LoginBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        LoginBtn.setForeground(new java.awt.Color(255, 255, 255));
        LoginBtn.setText("Login");
        LoginBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 4));
        LoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBtnActionPerformed(evt);
            }
        });

        showPasswordCheckBox.setBackground(new java.awt.Color(252, 252, 252));
        showPasswordCheckBox.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showPasswordCheckBox.setForeground(new java.awt.Color(153, 153, 153));
        showPasswordCheckBox.setText("Show Password");
        showPasswordCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckBoxActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setText("Username");

        jLabel24.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("Password");

        loginNotifLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        loginNotifLabel.setForeground(new java.awt.Color(102, 102, 102));
        loginNotifLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(212, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loginNotifLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LoginBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showPasswordCheckBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(153, 153, 153))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(showPasswordCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loginNotifLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );

        jPanel32.setBackground(new java.awt.Color(47, 128, 237));

        jLabel25.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("bluPOS");

        jLabel27.setFont(new java.awt.Font("Tw Cen MT", 1, 30)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(247, 247, 247));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Manage your");

        jLabel28.setFont(new java.awt.Font("Tw Cen MT", 1, 30)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(247, 247, 247));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Shop Conveniently");

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setPreferredSize(new java.awt.Dimension(0, 4));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(68, 68, 68))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(163, 163, 163))
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        generalPane.addTab("1 Login", jPanel2);

        jPanel3.setBackground(new java.awt.Color(244, 244, 242));
        jPanel3.setPreferredSize(new java.awt.Dimension(1000, 500));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setForeground(new java.awt.Color(0, 153, 153));

        addProductBtn.setBackground(new java.awt.Color(252, 252, 252));
        addProductBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        addProductBtn.setForeground(new java.awt.Color(51, 51, 51));
        addProductBtn.setText("Add");
        addProductBtn.setBorder(null);
        addProductBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(47, 128, 237));
        jLabel2.setText("Order Details");

        addBtn.setBackground(new java.awt.Color(252, 252, 252));
        addBtn.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        addBtn.setForeground(new java.awt.Color(51, 51, 51));
        addBtn.setText("+");
        addBtn.setBorder(null);
        addBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBtn.setEnabled(false);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        qtyField.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        qtyField.setForeground(new java.awt.Color(51, 51, 51));
        qtyField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));
        qtyField.setEnabled(false);
        qtyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyFieldActionPerformed(evt);
            }
        });
        qtyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qtyFieldKeyReleased(evt);
            }
        });

        minusBtn.setBackground(new java.awt.Color(252, 252, 252));
        minusBtn.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        minusBtn.setForeground(new java.awt.Color(51, 51, 51));
        minusBtn.setText("-");
        minusBtn.setBorder(null);
        minusBtn.setBorderPainted(false);
        minusBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minusBtn.setEnabled(false);
        minusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusBtnActionPerformed(evt);
            }
        });

        removeBtn.setBackground(new java.awt.Color(252, 252, 252));
        removeBtn.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        removeBtn.setForeground(new java.awt.Color(51, 51, 51));
        removeBtn.setText("X");
        removeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeBtn.setEnabled(false);
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        payBtn.setBackground(new java.awt.Color(47, 128, 237));
        payBtn.setFont(new java.awt.Font("Tw Cen MT", 1, 30)); // NOI18N
        payBtn.setForeground(new java.awt.Color(255, 255, 255));
        payBtn.setText("Pay");
        payBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 4));
        payBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        payBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBtnActionPerformed(evt);
            }
        });

        purchase_table.setBackground(new java.awt.Color(252, 252, 252));
        purchase_table.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        purchase_table.setForeground(new java.awt.Color(39, 50, 52));
        purchase_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Quantity", "Price", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchase_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        purchase_table.setGridColor(new java.awt.Color(255, 255, 255));
        purchase_table.setRowHeight(30);
        purchase_table.setSelectionBackground(new java.awt.Color(47, 128, 237));
        purchase_table.getTableHeader().setReorderingAllowed(false);
        purchase_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchase_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(purchase_table);
        if (purchase_table.getColumnModel().getColumnCount() > 0) {
            purchase_table.getColumnModel().getColumn(1).setPreferredWidth(7);
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(payBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(addProductBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(37, 37, 37))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(minusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addProductBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(payBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        jPanel3.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 0, -1, -1));

        jPanel13.setBackground(new java.awt.Color(246, 246, 246));

        product_table.setAutoCreateRowSorter(true);
        product_table.setBackground(new java.awt.Color(252, 252, 252));
        product_table.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        product_table.setForeground(new java.awt.Color(39, 50, 52));
        product_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Name", "Price", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        product_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        product_table.setGridColor(new java.awt.Color(255, 255, 255));
        product_table.setRowHeight(30);
        product_table.setSelectionBackground(new java.awt.Color(47, 128, 237));
        product_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        product_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        product_table.getTableHeader().setReorderingAllowed(false);
        product_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                product_tableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                product_tableMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(product_table);
        if (product_table.getColumnModel().getColumnCount() > 0) {
            product_table.getColumnModel().getColumn(0).setPreferredWidth(3);
        }

        logOutBtn.setBackground(new java.awt.Color(252, 252, 252));
        logOutBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        logOutBtn.setForeground(new java.awt.Color(51, 51, 51));
        logOutBtn.setText("Home");
        logOutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });

        selectCategoryComboBox.setBackground(new java.awt.Color(252, 252, 252));
        selectCategoryComboBox.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        selectCategoryComboBox.setForeground(new java.awt.Color(51, 51, 51));
        selectCategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Products", "Essentials", "Food", "Clothing", "Toys" }));
        selectCategoryComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectCategoryComboBoxMouseClicked(evt);
            }
        });
        selectCategoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCategoryComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Search");

        searchField.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        searchField.setForeground(new java.awt.Color(51, 51, 51));
        searchField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 191, 191)));
        searchField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                        .addComponent(logOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(selectCategoryComboBox)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logOutBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 19, -1, 735));

        generalPane.addTab("2 Check Out", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(47, 128, 237));

        btn_manageProducts.setBackground(new java.awt.Color(47, 128, 237));
        btn_manageProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_manageProducts.setPreferredSize(new java.awt.Dimension(308, 48));
        btn_manageProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_manageProductsMouseClicked(evt);
            }
        });

        adminAddProductButton.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminAddProductButton.setForeground(new java.awt.Color(255, 255, 255));
        adminAddProductButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton.setLabelFor(btn_manageProducts);
        adminAddProductButton.setText("Products");
        adminAddProductButton.setFocusable(false);
        adminAddProductButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_manageProductsLayout = new javax.swing.GroupLayout(btn_manageProducts);
        btn_manageProducts.setLayout(btn_manageProductsLayout);
        btn_manageProductsLayout.setHorizontalGroup(
            btn_manageProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_manageProductsLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(adminAddProductButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_manageProductsLayout.setVerticalGroup(
            btn_manageProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_manageProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_salesHistory.setBackground(new java.awt.Color(47, 128, 237));
        btn_salesHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salesHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salesHistoryMouseClicked(evt);
            }
        });

        salesHistBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        salesHistBtn.setForeground(new java.awt.Color(255, 255, 255));
        salesHistBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salesHistBtn.setText("Sales History");
        salesHistBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesHistBtnMouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon("D:\\Programming Files\\Java Projects\\NetBeans\\POS_System\\src\\main\\java\\assets\\icons8-total-sales-30.png")); // NOI18N

        javax.swing.GroupLayout btn_salesHistoryLayout = new javax.swing.GroupLayout(btn_salesHistory);
        btn_salesHistory.setLayout(btn_salesHistoryLayout);
        btn_salesHistoryLayout.setHorizontalGroup(
            btn_salesHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_salesHistoryLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salesHistBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_salesHistoryLayout.setVerticalGroup(
            btn_salesHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesHistBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(btn_salesHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15))
        );

        btn_restoreProducts.setBackground(new java.awt.Color(47, 128, 237));
        btn_restoreProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_restoreProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_restoreProductsMouseClicked(evt);
            }
        });

        restoreProductBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        restoreProductBtn.setForeground(new java.awt.Color(255, 255, 255));
        restoreProductBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        restoreProductBtn.setText("Restore Products");
        restoreProductBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restoreProductBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_restoreProductsLayout = new javax.swing.GroupLayout(btn_restoreProducts);
        btn_restoreProducts.setLayout(btn_restoreProductsLayout);
        btn_restoreProductsLayout.setHorizontalGroup(
            btn_restoreProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_restoreProductsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(restoreProductBtn)
                .addGap(57, 57, 57))
        );
        btn_restoreProductsLayout.setVerticalGroup(
            btn_restoreProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(restoreProductBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        );

        adminLogOutBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminLogOutBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminLogOutBtn.setText("Log Out");
        adminLogOutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminLogOutBtnMouseClicked(evt);
            }
        });

        btn_adminDashboard.setBackground(new java.awt.Color(47, 128, 237));
        btn_adminDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_adminDashboard.setPreferredSize(new java.awt.Dimension(308, 48));
        btn_adminDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_adminDashboardMouseClicked(evt);
            }
        });

        adminDashboardLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminDashboardLabel.setForeground(new java.awt.Color(255, 255, 255));
        adminDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminDashboardLabel.setLabelFor(btn_manageProducts);
        adminDashboardLabel.setText("Dashboard");
        adminDashboardLabel.setFocusable(false);
        adminDashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminDashboardLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_adminDashboardLayout = new javax.swing.GroupLayout(btn_adminDashboard);
        btn_adminDashboard.setLayout(btn_adminDashboardLayout);
        btn_adminDashboardLayout.setHorizontalGroup(
            btn_adminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_adminDashboardLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(adminDashboardLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_adminDashboardLayout.setVerticalGroup(
            btn_adminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_adminDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_accounts.setBackground(new java.awt.Color(47, 128, 237));
        btn_accounts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_accounts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_accountsMouseClicked(evt);
            }
        });

        restoreProductBtn1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        restoreProductBtn1.setForeground(new java.awt.Color(255, 255, 255));
        restoreProductBtn1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        restoreProductBtn1.setText("Accounts");
        restoreProductBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restoreProductBtn1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_accountsLayout = new javax.swing.GroupLayout(btn_accounts);
        btn_accounts.setLayout(btn_accountsLayout);
        btn_accountsLayout.setHorizontalGroup(
            btn_accountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_accountsLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(restoreProductBtn1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_accountsLayout.setVerticalGroup(
            btn_accountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(restoreProductBtn1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel38.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("POINT OF SALE SYSTEM");

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setPreferredSize(new java.awt.Dimension(0, 4));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );

        jLabel39.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("bluPOS");

        btn_recoverAccount.setBackground(new java.awt.Color(47, 128, 237));
        btn_recoverAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_recoverAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_recoverAccountMouseClicked(evt);
            }
        });

        restoreProductBtn2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        restoreProductBtn2.setForeground(new java.awt.Color(255, 255, 255));
        restoreProductBtn2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        restoreProductBtn2.setText("Recover Account");
        restoreProductBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restoreProductBtn2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_recoverAccountLayout = new javax.swing.GroupLayout(btn_recoverAccount);
        btn_recoverAccount.setLayout(btn_recoverAccountLayout);
        btn_recoverAccountLayout.setHorizontalGroup(
            btn_recoverAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_recoverAccountLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(restoreProductBtn2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_recoverAccountLayout.setVerticalGroup(
            btn_recoverAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(restoreProductBtn2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(adminLogOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel38)
                                .addComponent(jLabel39)))
                        .addGap(82, 82, 82))))
            .addComponent(btn_adminDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
            .addComponent(btn_manageProducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
            .addComponent(btn_salesHistory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_restoreProducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_accounts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_recoverAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addGap(54, 54, 54)
                .addComponent(btn_adminDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_manageProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_salesHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_restoreProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_accounts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_recoverAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(adminLogOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -1, 310, 740));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        adminProductTable.setBackground(new java.awt.Color(252, 252, 252));
        adminProductTable.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminProductTable.setForeground(new java.awt.Color(51, 51, 51));
        adminProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Name", "Price", "Stock", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        adminProductTable.setGridColor(new java.awt.Color(252, 252, 252));
        adminProductTable.setRowHeight(30);
        adminProductTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        adminProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminProductTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(adminProductTable);
        if (adminProductTable.getColumnModel().getColumnCount() > 0) {
            adminProductTable.getColumnModel().getColumn(0).setPreferredWidth(2);
            adminProductTable.getColumnModel().getColumn(2).setPreferredWidth(4);
            adminProductTable.getColumnModel().getColumn(3).setPreferredWidth(5);
            adminProductTable.getColumnModel().getColumn(4).setPreferredWidth(6);
        }

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Product ID");

        adminProductIDField.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminProductIDField.setForeground(new java.awt.Color(51, 51, 51));
        adminProductIDField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminProductIDFieldMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Name");

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Price");

        jLabel11.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Stock");

        newProductNameField.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        newProductNameField.setForeground(new java.awt.Color(51, 51, 51));

        newProductPriceField.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        newProductPriceField.setForeground(new java.awt.Color(51, 51, 51));

        newProductStockField.setBackground(new java.awt.Color(252, 252, 252));
        newProductStockField.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        newProductStockField.setForeground(new java.awt.Color(51, 51, 51));

        updateProductBtn.setBackground(new java.awt.Color(252, 252, 252));
        updateProductBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        updateProductBtn.setForeground(new java.awt.Color(51, 51, 51));
        updateProductBtn.setText("Save");
        updateProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProductBtnActionPerformed(evt);
            }
        });

        adminAddProductBtn.setBackground(new java.awt.Color(252, 252, 252));
        adminAddProductBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminAddProductBtn.setForeground(new java.awt.Color(51, 51, 51));
        adminAddProductBtn.setText("Add Product");
        adminAddProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminAddProductBtnActionPerformed(evt);
            }
        });

        adminRemoveProductBtn.setBackground(new java.awt.Color(252, 252, 252));
        adminRemoveProductBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminRemoveProductBtn.setForeground(new java.awt.Color(51, 51, 51));
        adminRemoveProductBtn.setText("Remove");
        adminRemoveProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRemoveProductBtnActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Category");

        productCategoryComboBox.setBackground(new java.awt.Color(252, 252, 252));
        productCategoryComboBox.setEditable(true);
        productCategoryComboBox.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        productCategoryComboBox.setForeground(new java.awt.Color(51, 51, 51));
        productCategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Essentials", "Food", "Clothing", "Toys" }));
        productCategoryComboBox.setBorder(null);

        adminAddProductButton1.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        adminAddProductButton1.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton1.setLabelFor(btn_manageProducts);
        adminAddProductButton1.setText("Manage Products");
        adminAddProductButton1.setFocusable(false);
        adminAddProductButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adminProductIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(newProductNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(adminAddProductBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newProductPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newProductStockField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(updateProductBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(productCategoryComboBox, 0, 127, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminRemoveProductBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adminAddProductButton1)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 182, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(adminAddProductButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminProductIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newProductNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newProductPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newProductStockField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminAddProductBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminRemoveProductBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateProductBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(116, 116, 116))
        );

        adminPane.addTab("1 Manage Product", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        salesTable.setBackground(new java.awt.Color(252, 252, 252));
        salesTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        salesTable.setForeground(new java.awt.Color(51, 51, 51));
        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "History"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        salesTable.setRowHeight(30);
        salesTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        jScrollPane5.setViewportView(salesTable);

        jLabel12.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Total Sales");

        totalSalesField.setEditable(false);
        totalSalesField.setBackground(new java.awt.Color(255, 255, 255));
        totalSalesField.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        totalSalesField.setForeground(new java.awt.Color(51, 51, 51));
        totalSalesField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalSalesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalSalesFieldActionPerformed(evt);
            }
        });

        dailySalesTable.setBackground(new java.awt.Color(252, 252, 252));
        dailySalesTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        dailySalesTable.setForeground(new java.awt.Color(51, 51, 51));
        dailySalesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dailySalesTable.setRowHeight(30);
        dailySalesTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        jScrollPane7.setViewportView(dailySalesTable);
        if (dailySalesTable.getColumnModel().getColumnCount() > 0) {
            dailySalesTable.getColumnModel().getColumn(0).setPreferredWidth(12);
            dailySalesTable.getColumnModel().getColumn(1).setPreferredWidth(2);
        }

        dailySalesComboBox.setBackground(new java.awt.Color(252, 252, 252));
        dailySalesComboBox.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        dailySalesComboBox.setForeground(new java.awt.Color(102, 102, 102));
        dailySalesComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dailySalesComboBoxMouseClicked(evt);
            }
        });
        dailySalesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailySalesComboBoxActionPerformed(evt);
            }
        });

        adminAddProductButton2.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        adminAddProductButton2.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton2.setLabelFor(btn_manageProducts);
        adminAddProductButton2.setText("Sales History");
        adminAddProductButton2.setFocusable(false);
        adminAddProductButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adminAddProductButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalSalesField, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(dailySalesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(adminAddProductButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dailySalesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(totalSalesField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        adminPane.addTab("2 Sales Hist", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        restoreProductTable.setBackground(new java.awt.Color(252, 252, 252));
        restoreProductTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        restoreProductTable.setForeground(new java.awt.Color(51, 51, 51));
        restoreProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Name", "Price", "Stock", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        restoreProductTable.setRowHeight(30);
        restoreProductTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        jScrollPane6.setViewportView(restoreProductTable);

        restoreBtn.setBackground(new java.awt.Color(47, 128, 237));
        restoreBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        restoreBtn.setForeground(new java.awt.Color(255, 255, 255));
        restoreBtn.setText("Restore Product");
        restoreBtn.setBorder(null);
        restoreBtn.setMargin(new java.awt.Insets(6, 14, 6, 14));
        restoreBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreBtnActionPerformed(evt);
            }
        });

        adminAddProductButton3.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        adminAddProductButton3.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton3.setLabelFor(btn_manageProducts);
        adminAddProductButton3.setText("Restore Products");
        adminAddProductButton3.setFocusable(false);
        adminAddProductButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adminAddProductButton3)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                    .addComponent(restoreBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(adminAddProductButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(restoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );

        adminPane.addTab("3 Restore Products", jPanel9);

        jPanel14.setBackground(new java.awt.Color(239, 239, 239));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));

        adminAddProductButton6.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton6.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton6.setLabelFor(btn_manageProducts);
        adminAddProductButton6.setText("Transactions Today");
        adminAddProductButton6.setFocusable(false);
        adminAddProductButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton6MouseClicked(evt);
            }
        });

        adminTransactionsToday.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        adminTransactionsToday.setForeground(new java.awt.Color(47, 128, 237));
        adminTransactionsToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminTransactionsToday.setLabelFor(btn_manageProducts);
        adminTransactionsToday.setText("0");
        adminTransactionsToday.setFocusable(false);
        adminTransactionsToday.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminTransactionsTodayMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(adminAddProductButton6)
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addComponent(adminTransactionsToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminTransactionsToday, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));

        adminAddProductButton8.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton8.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton8.setLabelFor(btn_manageProducts);
        adminAddProductButton8.setText("This Month's Sales");
        adminAddProductButton8.setFocusable(false);
        adminAddProductButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton8MouseClicked(evt);
            }
        });

        salesThisMonthLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        salesThisMonthLabel.setForeground(new java.awt.Color(47, 128, 237));
        salesThisMonthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salesThisMonthLabel.setLabelFor(btn_manageProducts);
        salesThisMonthLabel.setText("0");
        salesThisMonthLabel.setFocusable(false);
        salesThisMonthLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesThisMonthLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(salesThisMonthLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salesThisMonthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));

        adminAddProductButton9.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton9.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton9.setLabelFor(btn_manageProducts);
        adminAddProductButton9.setText("Sales Today");
        adminAddProductButton9.setFocusable(false);
        adminAddProductButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton9MouseClicked(evt);
            }
        });

        salesTodayLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        salesTodayLabel.setForeground(new java.awt.Color(47, 128, 237));
        salesTodayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salesTodayLabel.setLabelFor(btn_manageProducts);
        salesTodayLabel.setText("0");
        salesTodayLabel.setFocusable(false);
        salesTodayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTodayLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(adminAddProductButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(salesTodayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salesTodayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(241, 246, 253));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));

        adminAddProductButton10.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton10.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton10.setLabelFor(btn_manageProducts);
        adminAddProductButton10.setText("Total Sales");
        adminAddProductButton10.setFocusable(false);
        adminAddProductButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton10MouseClicked(evt);
            }
        });

        adminTotalSales.setFont(new java.awt.Font("Tw Cen MT", 0, 46)); // NOI18N
        adminTotalSales.setForeground(new java.awt.Color(47, 128, 237));
        adminTotalSales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminTotalSales.setLabelFor(btn_manageProducts);
        adminTotalSales.setText("0.00");
        adminTotalSales.setFocusable(false);
        adminTotalSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminTotalSalesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(adminAddProductButton10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(adminTotalSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton10)
                .addGap(18, 18, 18)
                .addComponent(adminTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(47, 128, 237));
        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminAddProductButton12.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminAddProductButton12.setForeground(new java.awt.Color(255, 255, 255));
        adminAddProductButton12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton12.setLabelFor(btn_manageProducts);
        adminAddProductButton12.setText("Top Products");
        adminAddProductButton12.setFocusable(false);
        adminAddProductButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton12)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(47, 128, 237));
        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminAddProductButton14.setBackground(new java.awt.Color(51, 51, 51));
        adminAddProductButton14.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminAddProductButton14.setForeground(new java.awt.Color(105, 164, 241));
        adminAddProductButton14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton14.setLabelFor(btn_manageProducts);
        adminAddProductButton14.setText("1");
        adminAddProductButton14.setFocusable(false);
        adminAddProductButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton14MouseClicked(evt);
            }
        });

        adminFirstPlace.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminFirstPlace.setForeground(new java.awt.Color(255, 255, 255));
        adminFirstPlace.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminFirstPlace.setLabelFor(btn_manageProducts);
        adminFirstPlace.setText("Product");
        adminFirstPlace.setFocusable(false);
        adminFirstPlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminFirstPlaceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminFirstPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton14)
                    .addComponent(adminFirstPlace))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(47, 128, 237));
        jPanel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminAddProductButton15.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminAddProductButton15.setForeground(new java.awt.Color(105, 164, 241));
        adminAddProductButton15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton15.setLabelFor(btn_manageProducts);
        adminAddProductButton15.setText("2");
        adminAddProductButton15.setFocusable(false);
        adminAddProductButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton15MouseClicked(evt);
            }
        });

        adminSecondPlace.setBackground(new java.awt.Color(47, 128, 237));
        adminSecondPlace.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminSecondPlace.setForeground(new java.awt.Color(255, 255, 255));
        adminSecondPlace.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminSecondPlace.setLabelFor(btn_manageProducts);
        adminSecondPlace.setText("Product");
        adminSecondPlace.setFocusable(false);
        adminSecondPlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminSecondPlaceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminSecondPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton15)
                    .addComponent(adminSecondPlace))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel24.setBackground(new java.awt.Color(47, 128, 237));
        jPanel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminAddProductButton16.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminAddProductButton16.setForeground(new java.awt.Color(105, 164, 241));
        adminAddProductButton16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton16.setLabelFor(btn_manageProducts);
        adminAddProductButton16.setText("3");
        adminAddProductButton16.setFocusable(false);
        adminAddProductButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton16MouseClicked(evt);
            }
        });

        adminThirdPlace.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminThirdPlace.setForeground(new java.awt.Color(255, 255, 255));
        adminThirdPlace.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminThirdPlace.setLabelFor(btn_manageProducts);
        adminThirdPlace.setText("Product");
        adminThirdPlace.setFocusable(false);
        adminThirdPlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminThirdPlaceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminThirdPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton16)
                    .addComponent(adminThirdPlace))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));

        adminAddProductButton13.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton13.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton13.setLabelFor(btn_manageProducts);
        adminAddProductButton13.setText("Number of Products");
        adminAddProductButton13.setFocusable(false);
        adminAddProductButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton13MouseClicked(evt);
            }
        });

        productCountLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        productCountLabel.setForeground(new java.awt.Color(47, 128, 237));
        productCountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        productCountLabel.setLabelFor(btn_manageProducts);
        productCountLabel.setText("0");
        productCountLabel.setFocusable(false);
        productCountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productCountLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel29.setBackground(new java.awt.Color(47, 128, 237));
        jPanel29.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminAddProductButton18.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton18.setForeground(new java.awt.Color(255, 255, 255));
        adminAddProductButton18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton18.setLabelFor(btn_manageProducts);
        adminAddProductButton18.setText("Average Monthly Sales");
        adminAddProductButton18.setFocusable(false);
        adminAddProductButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton18MouseClicked(evt);
            }
        });

        averageMonthlySalesLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        averageMonthlySalesLabel.setForeground(new java.awt.Color(255, 255, 255));
        averageMonthlySalesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageMonthlySalesLabel.setLabelFor(btn_manageProducts);
        averageMonthlySalesLabel.setText("0");
        averageMonthlySalesLabel.setFocusable(false);
        averageMonthlySalesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                averageMonthlySalesLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(averageMonthlySalesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(averageMonthlySalesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel30.setBackground(new java.awt.Color(47, 128, 237));
        jPanel30.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminAddProductButton19.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton19.setForeground(new java.awt.Color(255, 255, 255));
        adminAddProductButton19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton19.setLabelFor(btn_manageProducts);
        adminAddProductButton19.setText("Average Daily Sales");
        adminAddProductButton19.setFocusable(false);
        adminAddProductButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton19MouseClicked(evt);
            }
        });

        averageDailySalesLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        averageDailySalesLabel.setForeground(new java.awt.Color(255, 255, 255));
        averageDailySalesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageDailySalesLabel.setLabelFor(btn_manageProducts);
        averageDailySalesLabel.setText("0");
        averageDailySalesLabel.setFocusable(false);
        averageDailySalesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                averageDailySalesLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton19, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(averageDailySalesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(averageDailySalesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));

        adminAddProductButton21.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAddProductButton21.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminAddProductButton21.setLabelFor(btn_manageProducts);
        adminAddProductButton21.setText("Out of Stock Products");
        adminAddProductButton21.setFocusable(false);
        adminAddProductButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton21MouseClicked(evt);
            }
        });

        outOfStockProductsLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        outOfStockProductsLabel.setForeground(new java.awt.Color(47, 128, 237));
        outOfStockProductsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outOfStockProductsLabel.setLabelFor(btn_manageProducts);
        outOfStockProductsLabel.setText("0");
        outOfStockProductsLabel.setFocusable(false);
        outOfStockProductsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outOfStockProductsLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminAddProductButton21, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(outOfStockProductsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outOfStockProductsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel33.setBackground(new java.awt.Color(47, 128, 237));
        jPanel33.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 128, 237), 5, true));

        adminThirdPlace1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminThirdPlace1.setForeground(new java.awt.Color(255, 255, 255));
        adminThirdPlace1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminThirdPlace1.setLabelFor(btn_manageProducts);
        adminThirdPlace1.setText("bluPOS");
        adminThirdPlace1.setFocusable(false);
        adminThirdPlace1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminThirdPlace1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminThirdPlace1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminThirdPlace1)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(58, 58, 58))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel20, jPanel22, jPanel23, jPanel24});

        adminPane.addTab("4 Dashboard", jPanel14);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        accountsTable.setBackground(new java.awt.Color(252, 252, 252));
        accountsTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        accountsTable.setForeground(new java.awt.Color(51, 51, 51));
        accountsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Account Type", "First Name", "Middle Name", "Last Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountsTable.setRowHeight(30);
        accountsTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        accountsTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        accountsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountsTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(accountsTable);

        adminUsernameField.setEditable(false);
        adminUsernameField.setBackground(new java.awt.Color(255, 255, 255));
        adminUsernameField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminUsernameField.setForeground(new java.awt.Color(51, 51, 51));
        adminUsernameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        adminUsernameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminUsernameFieldMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Username");

        adminLastNameField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminLastNameField.setForeground(new java.awt.Color(51, 51, 51));
        adminLastNameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        adminLastNameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminLastNameFieldMouseClicked(evt);
            }
        });

        adminFirstNameField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminFirstNameField.setForeground(new java.awt.Color(51, 51, 51));
        adminFirstNameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        adminFirstNameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminFirstNameFieldMouseClicked(evt);
            }
        });

        adminMidNameField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminMidNameField.setForeground(new java.awt.Color(51, 51, 51));
        adminMidNameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        adminMidNameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminMidNameFieldMouseClicked(evt);
            }
        });

        adminAccTypeComboBox.setBackground(new java.awt.Color(252, 252, 252));
        adminAccTypeComboBox.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminAccTypeComboBox.setForeground(new java.awt.Color(51, 51, 51));
        adminAccTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Employee" }));
        adminAccTypeComboBox.setBorder(null);
        adminAccTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminAccTypeComboBoxActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Account Type");

        jLabel18.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("First Name");

        jLabel19.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Middle Name");

        jLabel20.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Last Name");

        adminSaveCredsBtn.setBackground(new java.awt.Color(252, 252, 252));
        adminSaveCredsBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminSaveCredsBtn.setForeground(new java.awt.Color(51, 51, 51));
        adminSaveCredsBtn.setText("Save");
        adminSaveCredsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminSaveCredsBtnActionPerformed(evt);
            }
        });

        adminDeactivateBtn.setBackground(new java.awt.Color(252, 252, 252));
        adminDeactivateBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminDeactivateBtn.setForeground(new java.awt.Color(51, 51, 51));
        adminDeactivateBtn.setText("Deactivate");
        adminDeactivateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDeactivateBtnActionPerformed(evt);
            }
        });

        adminChangePassBtn.setBackground(new java.awt.Color(252, 252, 252));
        adminChangePassBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminChangePassBtn.setForeground(new java.awt.Color(51, 51, 51));
        adminChangePassBtn.setText("Change Password");
        adminChangePassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminChangePassBtnActionPerformed(evt);
            }
        });

        adminRegisterAccBtn.setBackground(new java.awt.Color(47, 128, 237));
        adminRegisterAccBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminRegisterAccBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminRegisterAccBtn.setText("Register");
        adminRegisterAccBtn.setBorder(null);
        adminRegisterAccBtn.setMargin(new java.awt.Insets(6, 14, 6, 14));
        adminRegisterAccBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRegisterAccBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(adminRegisterAccBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminUsernameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminAccTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(adminSaveCredsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adminFirstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(adminMidNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(adminLastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(adminChangePassBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adminDeactivateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(168, 168, 168))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adminUsernameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(adminMidNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adminLastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adminFirstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(adminAccTypeComboBox, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(adminDeactivateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adminChangePassBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adminSaveCredsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(adminRegisterAccBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(190, 190, 190))
        );

        adminPane.addTab("5 Accs", jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 153));
        jLabel21.setText("Current Password");

        currentPasswordField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        currentPasswordField.setForeground(new java.awt.Color(51, 51, 51));
        currentPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));

        showCurrentPassCheckBox.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showCurrentPassCheckBox.setForeground(new java.awt.Color(153, 153, 153));
        showCurrentPassCheckBox.setText("Show");
        showCurrentPassCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCurrentPassCheckBoxActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setText("New Password");

        jLabel23.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 153, 153));
        jLabel23.setText("Confirm Password");

        newPasswordField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        newPasswordField.setForeground(new java.awt.Color(51, 51, 51));
        newPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));

        confirmPasswordField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        confirmPasswordField.setForeground(new java.awt.Color(51, 51, 51));
        confirmPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));

        showNewPassCheckBox.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showNewPassCheckBox.setForeground(new java.awt.Color(153, 153, 153));
        showNewPassCheckBox.setText("Show");
        showNewPassCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showNewPassCheckBoxActionPerformed(evt);
            }
        });

        showConfirmPassCheckBox.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showConfirmPassCheckBox.setForeground(new java.awt.Color(153, 153, 153));
        showConfirmPassCheckBox.setText("Show");
        showConfirmPassCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showConfirmPassCheckBoxActionPerformed(evt);
            }
        });

        changePasswordBtn.setBackground(new java.awt.Color(47, 128, 237));
        changePasswordBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        changePasswordBtn.setForeground(new java.awt.Color(255, 255, 255));
        changePasswordBtn.setText("Change Password");
        changePasswordBtn.setBorder(null);
        changePasswordBtn.setMargin(new java.awt.Insets(6, 14, 6, 14));
        changePasswordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordBtnActionPerformed(evt);
            }
        });

        changePassReturnBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        changePassReturnBtn.setForeground(new java.awt.Color(47, 128, 237));
        changePassReturnBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changePassReturnBtn.setText("return");
        changePassReturnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePassReturnBtnMouseClicked(evt);
            }
        });

        changePassUsernameLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        changePassUsernameLabel.setForeground(new java.awt.Color(47, 128, 237));
        changePassUsernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changePassUsernameLabel.setLabelFor(btn_manageProducts);
        changePassUsernameLabel.setText("Username");
        changePassUsernameLabel.setFocusable(false);
        changePassUsernameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePassUsernameLabelMouseClicked(evt);
            }
        });

        changePassErrorDisplay.setEditable(false);
        changePassErrorDisplay.setBackground(new java.awt.Color(255, 255, 255));
        changePassErrorDisplay.setColumns(20);
        changePassErrorDisplay.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        changePassErrorDisplay.setForeground(new java.awt.Color(102, 102, 102));
        changePassErrorDisplay.setRows(5);
        changePassErrorDisplay.setBorder(null);
        changePassErrorDisplay.setFocusable(false);
        jScrollPane10.setViewportView(changePassErrorDisplay);

        changePassReturnBtn1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        changePassReturnBtn1.setForeground(new java.awt.Color(47, 128, 237));
        changePassReturnBtn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changePassReturnBtn1.setText("Reset Password");
        changePassReturnBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePassReturnBtn1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(changePassReturnBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane10)
                    .addComponent(changePassUsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                    .addComponent(currentPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newPasswordField)
                    .addComponent(confirmPasswordField)
                    .addComponent(changePasswordBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(changePassReturnBtn1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showCurrentPassCheckBox)
                    .addComponent(showNewPassCheckBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(showConfirmPassCheckBox))
                .addGap(312, 312, 312))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(changePassUsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showCurrentPassCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(currentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showNewPassCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showConfirmPassCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changePasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changePassReturnBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changePassReturnBtn1)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        adminPane.addTab("6 Change Pass", jPanel5);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        passwordRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        passwordRegister.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));

        showRegisterPassCheckBox.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showRegisterPassCheckBox.setForeground(new java.awt.Color(153, 153, 153));
        showRegisterPassCheckBox.setText("Show");
        showRegisterPassCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showRegisterPassCheckBoxActionPerformed(evt);
            }
        });

        confirmPassRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        confirmPassRegister.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));

        showRegisterConfirmPassCheckBox.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showRegisterConfirmPassCheckBox.setForeground(new java.awt.Color(153, 153, 153));
        showRegisterConfirmPassCheckBox.setText("Show");
        showRegisterConfirmPassCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showRegisterConfirmPassCheckBoxActionPerformed(evt);
            }
        });

        usernameRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        usernameRegister.setForeground(new java.awt.Color(51, 51, 51));
        usernameRegister.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        usernameRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameRegisterMouseClicked(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(51, 0, 255));
        jLabel26.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Username");

        firstNameRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        firstNameRegister.setForeground(new java.awt.Color(51, 51, 51));
        firstNameRegister.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        firstNameRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstNameRegisterMouseClicked(evt);
            }
        });

        midNameRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        midNameRegister.setForeground(new java.awt.Color(51, 51, 51));
        midNameRegister.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        midNameRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                midNameRegisterMouseClicked(evt);
            }
        });

        lastNameRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        lastNameRegister.setForeground(new java.awt.Color(51, 51, 51));
        lastNameRegister.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 181, 181)));
        lastNameRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lastNameRegisterMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Last Name");

        registerBtn.setBackground(new java.awt.Color(47, 128, 237));
        registerBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        registerBtn.setForeground(new java.awt.Color(255, 255, 255));
        registerBtn.setText("Register");
        registerBtn.setBorder(null);
        registerBtn.setMargin(new java.awt.Insets(6, 14, 6, 14));
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("First Name");

        jLabel31.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Middle Name");

        jLabel32.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Confirm Password");

        jLabel33.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Password");

        accTypeRegister.setBackground(new java.awt.Color(252, 252, 252));
        accTypeRegister.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        accTypeRegister.setForeground(new java.awt.Color(51, 51, 51));
        accTypeRegister.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "Admin" }));
        accTypeRegister.setBorder(null);
        accTypeRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accTypeRegisterActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Account Type");

        registerReturnBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        registerReturnBtn.setForeground(new java.awt.Color(47, 128, 237));
        registerReturnBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        registerReturnBtn.setText("return");
        registerReturnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerReturnBtnMouseClicked(evt);
            }
        });

        registerErrorDisplay.setEditable(false);
        registerErrorDisplay.setBackground(new java.awt.Color(255, 255, 255));
        registerErrorDisplay.setColumns(20);
        registerErrorDisplay.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        registerErrorDisplay.setForeground(new java.awt.Color(102, 102, 102));
        registerErrorDisplay.setRows(5);
        registerErrorDisplay.setBorder(null);
        registerErrorDisplay.setFocusable(false);
        jScrollPane11.setViewportView(registerErrorDisplay);

        changePassUsernameLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 46)); // NOI18N
        changePassUsernameLabel1.setForeground(new java.awt.Color(47, 128, 237));
        changePassUsernameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changePassUsernameLabel1.setLabelFor(btn_manageProducts);
        changePassUsernameLabel1.setText("Create Account");
        changePassUsernameLabel1.setFocusable(false);
        changePassUsernameLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePassUsernameLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accTypeRegister, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameRegister)
                    .addComponent(lastNameRegister)
                    .addComponent(confirmPassRegister)
                    .addComponent(registerBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordRegister)
                    .addComponent(midNameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameRegister, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerReturnBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane11)
                    .addComponent(changePassUsernameLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showRegisterConfirmPassCheckBox)
                    .addComponent(showRegisterPassCheckBox))
                .addGap(302, 302, 302))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(changePassUsernameLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(midNameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastNameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showRegisterPassCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordRegister, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmPassRegister, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showRegisterConfirmPassCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accTypeRegister, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(registerReturnBtn)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );

        adminPane.addTab("7 Register", jPanel11);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        recoverAccountTable.setBackground(new java.awt.Color(252, 252, 252));
        recoverAccountTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        recoverAccountTable.setForeground(new java.awt.Color(51, 51, 51));
        recoverAccountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Account Type", "First Name", "Middle Name", "Last Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recoverAccountTable.setRowHeight(30);
        recoverAccountTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        recoverAccountTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        recoverAccountTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recoverAccountTableMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(recoverAccountTable);

        adminRecoverAccBtn.setBackground(new java.awt.Color(47, 128, 237));
        adminRecoverAccBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        adminRecoverAccBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminRecoverAccBtn.setText("Recover");
        adminRecoverAccBtn.setBorder(null);
        adminRecoverAccBtn.setMargin(new java.awt.Insets(6, 14, 6, 14));
        adminRecoverAccBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRecoverAccBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(adminRecoverAccBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adminRecoverAccBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        adminPane.addTab("8 Recover Acc", jPanel10);

        jPanel4.add(adminPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, -27, 910, 720));

        generalPane.addTab("3 Admin", jPanel4);

        fourPayment.setBackground(new java.awt.Color(255, 255, 255));

        finalTotalField.setEditable(false);
        finalTotalField.setBackground(new java.awt.Color(241, 246, 253));
        finalTotalField.setFont(new java.awt.Font("Tw Cen MT", 0, 48)); // NOI18N
        finalTotalField.setForeground(new java.awt.Color(47, 128, 237));
        finalTotalField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        finalTotalField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 229, 247), 5, true));
        finalTotalField.setFocusable(false);
        finalTotalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalTotalFieldActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(249, 251, 255));
        jComboBox1.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "GCash" }));
        jComboBox1.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Payment Method");

        finalPayBtn.setBackground(new java.awt.Color(47, 128, 237));
        finalPayBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        finalPayBtn.setForeground(new java.awt.Color(255, 255, 255));
        finalPayBtn.setText("Pay");
        finalPayBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 4));
        finalPayBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        finalPayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalPayBtnActionPerformed(evt);
            }
        });

        returnBtn.setBackground(new java.awt.Color(249, 252, 255));
        returnBtn.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        returnBtn.setForeground(new java.awt.Color(102, 102, 102));
        returnBtn.setText("<");
        returnBtn.setBorder(null);
        returnBtn.setBorderPainted(false);
        returnBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Enter Cash");

        cashField.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        cashField.setForeground(new java.awt.Color(51, 51, 51));
        cashField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cashField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFieldActionPerformed(evt);
            }
        });
        cashField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cashFieldKeyReleased(evt);
            }
        });

        changeLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        changeLabel.setForeground(new java.awt.Color(102, 102, 102));
        changeLabel.setText("Change");

        changeField.setEditable(false);
        changeField.setBackground(new java.awt.Color(255, 255, 255));
        changeField.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        changeField.setForeground(new java.awt.Color(51, 51, 51));
        changeField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        changeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Customer Name");

        nameField.setFont(new java.awt.Font("Tw Cen MT", 0, 22)); // NOI18N
        nameField.setForeground(new java.awt.Color(51, 51, 51));
        nameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameFieldKeyReleased(evt);
            }
        });

        receiptPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        receiptPane.setFocusable(false);
        jScrollPane8.setViewportView(receiptPane);

        printReceiptBtn.setBackground(new java.awt.Color(249, 252, 255));
        printReceiptBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        printReceiptBtn.setForeground(new java.awt.Color(102, 102, 102));
        printReceiptBtn.setText("Print Receipt");
        printReceiptBtn.setBorder(null);
        printReceiptBtn.setBorderPainted(false);
        printReceiptBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printReceiptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printReceiptBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fourPaymentLayout = new javax.swing.GroupLayout(fourPayment);
        fourPayment.setLayout(fourPaymentLayout);
        fourPaymentLayout.setHorizontalGroup(
            fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fourPaymentLayout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fourPaymentLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fourPaymentLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fourPaymentLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cashField, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fourPaymentLayout.createSequentialGroup()
                        .addComponent(changeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(changeField, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(finalPayBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(finalTotalField, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fourPaymentLayout.createSequentialGroup()
                        .addComponent(returnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printReceiptBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        fourPaymentLayout.setVerticalGroup(
            fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fourPaymentLayout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fourPaymentLayout.createSequentialGroup()
                        .addComponent(finalTotalField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fourPaymentLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel3))
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cashField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(12, 12, 12)
                        .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fourPaymentLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(changeLabel))
                            .addComponent(changeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(fourPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fourPaymentLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(finalPayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(returnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fourPaymentLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(printReceiptBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(152, 152, 152))
        );

        generalPane.addTab("4 Payment", fourPayment);

        sixPaymentSuccess.setBackground(new java.awt.Color(255, 255, 255));
        sixPaymentSuccess.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(47, 128, 237));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Payment Success!");
        sixPaymentSuccess.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 256, 1000, -1));

        returnBtn1.setBackground(new java.awt.Color(47, 128, 237));
        returnBtn1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        returnBtn1.setForeground(new java.awt.Color(255, 255, 255));
        returnBtn1.setText("Return to Check Out");
        returnBtn1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 4));
        returnBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBtn1ActionPerformed(evt);
            }
        });
        sixPaymentSuccess.add(returnBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 330, 370, 51));

        generalPane.addTab("5 Payment Success", sixPaymentSuccess);

        sixUserDashboard.setBackground(new java.awt.Color(255, 255, 255));
        sixUserDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel26.setBackground(new java.awt.Color(241, 246, 253));
        jPanel26.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 229, 247), 5, true));

        adminAddProductButton11.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        adminAddProductButton11.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton11.setLabelFor(btn_manageProducts);
        adminAddProductButton11.setText("Total Sales");
        adminAddProductButton11.setFocusable(false);
        adminAddProductButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton11MouseClicked(evt);
            }
        });

        userTotalSales.setFont(new java.awt.Font("Tw Cen MT", 0, 40)); // NOI18N
        userTotalSales.setForeground(new java.awt.Color(47, 128, 237));
        userTotalSales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userTotalSales.setLabelFor(btn_manageProducts);
        userTotalSales.setText("0");
        userTotalSales.setFocusable(false);
        userTotalSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTotalSalesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(adminAddProductButton11)
                        .addGap(0, 240, Short.MAX_VALUE))
                    .addComponent(userTotalSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton11)
                .addGap(18, 18, 18)
                .addComponent(userTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        sixUserDashboard.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, -1));

        welcomeUserLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        welcomeUserLabel.setForeground(new java.awt.Color(47, 128, 237));
        welcomeUserLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeUserLabel.setLabelFor(btn_manageProducts);
        welcomeUserLabel.setText("Welcome User");
        welcomeUserLabel.setFocusable(false);
        welcomeUserLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                welcomeUserLabelMouseClicked(evt);
            }
        });
        sixUserDashboard.add(welcomeUserLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 114, 660, 90));

        userSalesHistBtn.setBackground(new java.awt.Color(47, 128, 237));
        userSalesHistBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        userSalesHistBtn.setForeground(new java.awt.Color(255, 255, 255));
        userSalesHistBtn.setText("Sales History");
        userSalesHistBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 5));
        userSalesHistBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userSalesHistBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSalesHistBtnActionPerformed(evt);
            }
        });
        sixUserDashboard.add(userSalesHistBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 269, 120));

        jPanel27.setBackground(new java.awt.Color(241, 246, 253));
        jPanel27.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(241, 246, 253), 5, true));

        adminAddProductButton7.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminAddProductButton7.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton7.setLabelFor(btn_manageProducts);
        adminAddProductButton7.setText("Transactions Today");
        adminAddProductButton7.setFocusable(false);
        adminAddProductButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton7MouseClicked(evt);
            }
        });

        userTransactionsToday.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        userTransactionsToday.setForeground(new java.awt.Color(47, 128, 237));
        userTransactionsToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userTransactionsToday.setLabelFor(btn_manageProducts);
        userTransactionsToday.setText("0");
        userTransactionsToday.setFocusable(false);
        userTransactionsToday.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTransactionsTodayMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userTransactionsToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(adminAddProductButton7)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userTransactionsToday)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        sixUserDashboard.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, -1, 120));

        jPanel28.setBackground(new java.awt.Color(241, 246, 253));
        jPanel28.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(241, 246, 253), 5, true));

        userSalesToday.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        userSalesToday.setForeground(new java.awt.Color(47, 128, 237));
        userSalesToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userSalesToday.setLabelFor(btn_manageProducts);
        userSalesToday.setText("0");
        userSalesToday.setFocusable(false);
        userSalesToday.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userSalesTodayMouseClicked(evt);
            }
        });

        adminAddProductButton20.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        adminAddProductButton20.setForeground(new java.awt.Color(47, 128, 237));
        adminAddProductButton20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminAddProductButton20.setLabelFor(btn_manageProducts);
        adminAddProductButton20.setText("Sales Today");
        adminAddProductButton20.setFocusable(false);
        adminAddProductButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminAddProductButton20MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userSalesToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(adminAddProductButton20)
                        .addGap(0, 79, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminAddProductButton20)
                .addGap(12, 12, 12)
                .addComponent(userSalesToday, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        sixUserDashboard.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, -1, -1));

        userLogoutBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        userLogoutBtn.setForeground(new java.awt.Color(47, 128, 237));
        userLogoutBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLogoutBtn.setLabelFor(btn_manageProducts);
        userLogoutBtn.setText("Log out");
        userLogoutBtn.setFocusable(false);
        userLogoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userLogoutBtnMouseClicked(evt);
            }
        });
        sixUserDashboard.add(userLogoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 530, -1, 36));

        pointOfSalesBtn.setBackground(new java.awt.Color(47, 128, 237));
        pointOfSalesBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        pointOfSalesBtn.setForeground(new java.awt.Color(255, 255, 255));
        pointOfSalesBtn.setText("Point of Sales");
        pointOfSalesBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 5));
        pointOfSalesBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pointOfSalesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointOfSalesBtnActionPerformed(evt);
            }
        });
        sixUserDashboard.add(pointOfSalesBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, 269, 167));

        generalPane.addTab("6 User Dashboard", sixUserDashboard);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcomeUserLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 55)); // NOI18N
        welcomeUserLabel2.setForeground(new java.awt.Color(47, 128, 237));
        welcomeUserLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeUserLabel2.setLabelFor(btn_manageProducts);
        welcomeUserLabel2.setText("Welcome User");
        welcomeUserLabel2.setFocusable(false);
        welcomeUserLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                welcomeUserLabel2MouseClicked(evt);
            }
        });
        jPanel19.add(welcomeUserLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 660, 104));

        userSalesTable.setBackground(new java.awt.Color(252, 252, 252));
        userSalesTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        userSalesTable.setForeground(new java.awt.Color(51, 51, 51));
        userSalesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "History"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userSalesTable.setRowHeight(30);
        userSalesTable.setSelectionBackground(new java.awt.Color(47, 128, 237));
        jScrollPane9.setViewportView(userSalesTable);

        jPanel19.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 660, 359));

        userDashboardReturnBtn.setBackground(new java.awt.Color(47, 128, 237));
        userDashboardReturnBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        userDashboardReturnBtn.setForeground(new java.awt.Color(255, 255, 255));
        userDashboardReturnBtn.setText("Return");
        userDashboardReturnBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 148, 239), 5));
        userDashboardReturnBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userDashboardReturnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userDashboardReturnBtnActionPerformed(evt);
            }
        });
        jPanel19.add(userDashboardReturnBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 660, 38));

        generalPane.addTab("7 User Sales History", jPanel19);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        newPassUserLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        newPassUserLabel.setForeground(new java.awt.Color(47, 128, 237));
        newPassUserLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPassUserLabel.setLabelFor(btn_manageProducts);
        newPassUserLabel.setText("by your Administrator");
        newPassUserLabel.setFocusable(false);
        newPassUserLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newPassUserLabelMouseClicked(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(153, 153, 153));
        jLabel35.setText("Confirm Password");

        confirmNewPassField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        confirmNewPassField.setForeground(new java.awt.Color(51, 51, 51));
        confirmNewPassField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confirmNewPassField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        confirmNewPassField.setSelectionColor(new java.awt.Color(47, 128, 237));

        showNewPassField.setBackground(new java.awt.Color(252, 252, 252));
        showNewPassField.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showNewPassField.setForeground(new java.awt.Color(153, 153, 153));
        showNewPassField.setText("Show");
        showNewPassField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showNewPassFieldActionPerformed(evt);
            }
        });

        newPassContinueBtn.setBackground(new java.awt.Color(47, 128, 237));
        newPassContinueBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        newPassContinueBtn.setForeground(new java.awt.Color(255, 255, 255));
        newPassContinueBtn.setText("Continue");
        newPassContinueBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 161, 255), 4));
        newPassContinueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPassContinueBtnActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(153, 153, 153));
        jLabel36.setText("New Password");

        newPassField.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        newPassField.setForeground(new java.awt.Color(51, 51, 51));
        newPassField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        newPassField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        newPassField.setSelectionColor(new java.awt.Color(47, 128, 237));

        showConfirmNewPassField.setBackground(new java.awt.Color(252, 252, 252));
        showConfirmNewPassField.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        showConfirmNewPassField.setForeground(new java.awt.Color(153, 153, 153));
        showConfirmNewPassField.setText("Show");
        showConfirmNewPassField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showConfirmNewPassFieldActionPerformed(evt);
            }
        });

        newPassErrorDisplay.setEditable(false);
        newPassErrorDisplay.setBackground(new java.awt.Color(255, 255, 255));
        newPassErrorDisplay.setColumns(20);
        newPassErrorDisplay.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        newPassErrorDisplay.setForeground(new java.awt.Color(102, 102, 102));
        newPassErrorDisplay.setRows(5);
        newPassErrorDisplay.setBorder(null);
        newPassErrorDisplay.setFocusable(false);
        jScrollPane13.setViewportView(newPassErrorDisplay);

        newPassUserLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        newPassUserLabel1.setForeground(new java.awt.Color(153, 153, 153));
        newPassUserLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPassUserLabel1.setLabelFor(btn_manageProducts);
        newPassUserLabel1.setText("Enter New Password to Continue");
        newPassUserLabel1.setFocusable(false);
        newPassUserLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newPassUserLabel1MouseClicked(evt);
            }
        });

        newPassUserLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        newPassUserLabel2.setForeground(new java.awt.Color(47, 128, 237));
        newPassUserLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPassUserLabel2.setLabelFor(btn_manageProducts);
        newPassUserLabel2.setText("Your Password has been reset");
        newPassUserLabel2.setFocusable(false);
        newPassUserLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newPassUserLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newPassUserLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newPassUserLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane13)
                    .addComponent(confirmNewPassField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(newPassContinueBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(newPassField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(newPassUserLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showNewPassField, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(showConfirmNewPassField, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                .addGap(280, 280, 280))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(newPassUserLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newPassUserLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newPassUserLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showNewPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmNewPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showConfirmNewPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(newPassContinueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        generalPane.addTab("8 New Password", jPanel36);

        getContentPane().add(generalPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1080, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void returnBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBtn1ActionPerformed
        // TODO add your handling code here:
        generalPane.setSelectedIndex(1);
    }//GEN-LAST:event_returnBtn1ActionPerformed

    private void cashFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashFieldKeyReleased
        // TODO add your handling code here:
 
        boolean isDouble = true;
        boolean isInteger = true;

        try{
            double cashDouble = Double.parseDouble(cashField.getText());
        }catch(Exception a){
            isDouble = false;
        }

        try{
            int cashInteger = Integer.parseInt(cashField.getText());
        }catch(Exception a){
            isInteger = false;
        }
        
        if(isDouble == false && isInteger == false){
            cashField.setText("");
        }

        
        
        
        double grandTotal = Double.parseDouble(finalTotalField.getText().replace("Php ","").replace(",",""));
        
        double cash = 0.00;
        
        if(!(cashField.getText().equals(""))){
            cash = Double.parseDouble(cashField.getText());
        }
            
        
        
        
        System.out.println("Cash: "+cash);
        if(cash == 0.00){
            finalPayBtn.setText("Pay");
            changeField.setText("");
        }else if(cash<grandTotal){
//            changeField.setVisible(false);
//            changeLabel.setVisible(false);
//            insufficientLabel.setVisible(true);
            changeField.setText("");
            finalPayBtn.setText("Insufficient Funds!");
            //nameNotifLabel.setVisible(true);
        }else{
//            changeField.setVisible(true);
//            changeLabel.setVisible(true);
          //changeField.setText((cash-grandTotal)+"");
          double change = cash-grandTotal;
            changeField.setText(String.format("%,.2f",change));
//            insufficientLabel.setVisible(false);
            //ameNotifLabel.setVisible(false);
            finalPayBtn.setText("Pay");
        }
        receiptGenerator();
    }//GEN-LAST:event_cashFieldKeyReleased

    private void cashFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cashFieldActionPerformed

    private void returnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBtnActionPerformed
        // TODO add your handling code here:
        generalPane.setSelectedIndex(1);
    }//GEN-LAST:event_returnBtnActionPerformed

    private void finalPayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalPayBtnActionPerformed
        // TODO add your handling code here:
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> quantity = new ArrayList<String>();
        DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
        boolean dailySalesIsModified = false;
        ArrayList<String> lineData = new ArrayList<>();
        
        for(int count = 0; count < model.getRowCount(); count++){
            name.add(model.getValueAt(count, 0).toString());
            quantity.add(model.getValueAt(count, 1).toString()); 
        }
        

        double grandTotal = Double.parseDouble(finalTotalField.getText().replace("Php ", "").replace(",",""));
        double cash = Double.parseDouble(cashField.getText());
        
        //date    
        LocalDateTime date = LocalDateTime.now();  
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedDate = date.format(dateFormat);  
        
        if(cash>=grandTotal && grandTotal>0){
            try{
                FileWriter sales_writer = new FileWriter("sales.txt",true);
                
                //writes date and customer name to sales.txt
                if(nameField.getText().equals("")){
                    sales_writer.write(formattedDate+";"+"Customer"+";");
                }else{
                    sales_writer.write(formattedDate+";"+nameField.getText()+";");
                }
                
                //loops in n times depeding on the row count of purchased_table
                for(int x=0; x<model.getRowCount(); x++){
                    //writes all products to sales.txt
                    sales_writer.write(name.get(x)+" × "+quantity.get(x)+";");
                    
                    //INSERT dailySales reconstructor section BELOW THIS
                }
                
                //dailySales reconstructor section
//                    BufferedReader reader = new BufferedReader(new FileReader("dailySales.txt"));
//                    String line = reader.readLine();
//                    int count=0;
//                    String newData = "";
//                    
//                    //checks if dailySales.txt has content before allowing modification of values
//                    if(line != null){
//                        dailySalesIsModified = true;
//                        while(line != null){                        
//                            lineData.add(line);
//                            String[] arrayData = lineData.get(count).split(";");
//                                
//                            //checks if current date already exists on dailySales.txt
//                            if(arrayData[0].equals(formattedDate)){
//                                
//                                //loops the current line and checks if product name exists
//                                //if it exists, then add old quantity and new quantity
//                                boolean found = false;
//                                newData = formattedDate;
//                                
//                                for(int x=0; x<model.getRowCount(); x++){
//                                    for(int a=1; a<arrayData.length; a+=2){
//                                        if(arrayData[a].equals(name.get(x))){
//                                            found = true;
//                                            int newQuantity = Integer.parseInt(quantity.get(x)) + Integer.parseInt(arrayData[a+1]);
//                                            arrayData[a+1] = newQuantity+"".trim();
//                                            newData = newData.concat(";"+arrayData[a]+";"+arrayData[a+1]);
//                                            lineData.set(count, newData);
//                                        }else{
//                                            newData = newData.concat(";"+arrayData[a]+";"+arrayData[a+1]);
//                                            lineData.set(count, newData);
//                                        }
//                                    }
//                                    if(found == false){
//                                        newData = newData.concat(";"+name.get(x)+";"+quantity.get(x));
//                                        lineData.set(count, newData);
//                                    }
//                                }
//                                    
//                                
//                                
//                            }
//                            String dataFromTable = formattedDate;
//                            for(int k=0; k<model.getRowCount(); k++){
//                                    dataFromTable = dataFromTable.concat(";"+name.get(k)+";"+quantity.get(k));
//                            }
//                            lineData.add(dataFromTable);
//                            //write lineData to dailySales.txt
//                            //or move up algo to enter if statement
//                            count++;
//                            line=reader.readLine();
//                        }
//                    }
//                    reader.close();
//                
//                //writes to dailySales.txt
//                FileWriter dailySales_writer = new FileWriter("dailySales.txt");
//                
//                
//                if(dailySalesIsModified==true){
//                    //error detected below or around this block
//                    for(int k=0; k<model.getRowCount(); k++){
//                        dailySales_writer.write(lineData.get(k)+'\n');
//                    }
//                }else if(dailySalesIsModified==false){
//                    
//                    dailySales_writer.write(formattedDate+";");
//                    for(int j=0; j<model.getRowCount(); j++){
//                        //writes all products to dailySales.txt
//                        if(j==model.getRowCount()-1){
//                            dailySales_writer.write(name.get(j)+";"+quantity.get(j)+'\n');
//                        }else{
//                            dailySales_writer.write(name.get(j)+";"+quantity.get(j)+";");
//                        }
//                    }
//                }

                
                //writes grandtotal to the end of sales.txt
                sales_writer.write(grandTotal+""+";"+loggedInAccount+'\n');
                
                sales_writer.close();
                //dailySales_writer.close();
            }catch(Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
            

            model.setRowCount(0);
            total();
            nameField.setText("");
            cashField.setText("");
            changeField.setText("");
            generalPane.setSelectedIndex(4);
            dailySalesBuilder();
            displaySalesHistory();
            userDisplaySalesHistory(userSalesTable, loggedInAccount);
            userDashboard();
            adminDashboard();
            
        }
    }//GEN-LAST:event_finalPayBtnActionPerformed

    private void adminLogOutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminLogOutBtnMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
        model.setRowCount(0);
        total();
        nameField.setText("");
        cashField.setText("");
        changeField.setText("");
        generalPane.setSelectedIndex(0);
        adminPane.setSelectedIndex(0);
    }//GEN-LAST:event_adminLogOutBtnMouseClicked

    private void salesHistBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesHistBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_salesHistBtnMouseClicked
    
    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginBtnActionPerformed
        // TODO add your handling code here:
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        boolean usernameExists = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            String line = null;

            while( (line = reader.readLine()) !=null ){
                String[] lineData = line.split(";");
                
                //checks if employee has reset password and then redirects to generalPane.setSelectedIndex(7)
                if(username.equals(lineData[0]) && lineData[1].equals("0000") && password.equals("0000") && lineData[2].equals("Employee") && lineData[6].equals("1")){
                    loggedInAccount = lineData[0];
                    welcomeUserLabel.setText("Welcome "+loggedInAccount);
                    welcomeUserLabel2.setText("Welcome "+loggedInAccount);
                    generalPane.setSelectedIndex(7);
                    usernameField.setText("");
                    passwordField.setText("");
                    loginNotifLabel.setText("");
                    userDisplaySalesHistory(userSalesTable, loggedInAccount);
                    userDashboard();
                    username = "";
                    password = "";
                    
                //checks if employee has the right password and then redirects to generalPane.setSelectedIndex(5)
                }else if(username.equals(lineData[0]) && password.equals(lineData[1]) && lineData[2].equals("Employee") && lineData[6].equals("1")){
                    loggedInAccount = lineData[0];
                    welcomeUserLabel.setText("Welcome "+loggedInAccount);
                    welcomeUserLabel2.setText("Welcome "+loggedInAccount);
                    generalPane.setSelectedIndex(5);
                    usernameField.setText("");
                    passwordField.setText("");
                    loginNotifLabel.setText("");
                    userDisplaySalesHistory(userSalesTable, loggedInAccount);
                    userDashboard();
                    username = "";
                    password = "";
                
                //checks if admin has correct password and then redirects to  adminPane.setSelectedIndex(3)
                }else if(username.equals(lineData[0]) && password.equals(lineData[1]) && lineData[2].equals("Admin") && lineData[6].equals("1")){
                    loggedInAccount = lineData[0];
                    generalPane.setSelectedIndex(2);
                    adminPane.setSelectedIndex(3);
                    setColor(btn_adminDashboard);
                    resetColor(btn_salesHistory);
                    resetColor(btn_restoreProducts);
                    resetColor(btn_manageProducts);
                    resetColor(btn_accounts);
                    resetColor(btn_recoverAccount);
 
                    usernameField.setText("");
                    passwordField.setText("");
                    loginNotifLabel.setText("");
                    username = "";
                    password = "";
                    dailySalesBuilder();
                    adminDashboard();
                }else if(username.equals(lineData[0]) && lineData[6].equals("1")){
                    usernameExists = true;
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        if(usernameExists == false && !(username.equals("")) && !(password.equals(""))){
            loginNotifLabel.setText("Username does not exist");
        }else if(password.equals("") && !(username.equals(""))){
            loginNotifLabel.setText("Password cannot be empty");
        }else if(!(username.equals("")) && !(password.equals(""))){
            loginNotifLabel.setText("Username and password does not match");
        }
    }//GEN-LAST:event_LoginBtnActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void restoreProductBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restoreProductBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_restoreProductBtnMouseClicked

    private void adminAddProductButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButtonMouseClicked

    private void btn_manageProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_manageProductsMouseClicked
        // TODO add your handling code here:
        setColor(btn_manageProducts);
        resetColor(btn_salesHistory);
        resetColor(btn_restoreProducts);
        resetColor(btn_adminDashboard);
        resetColor(btn_accounts);
        resetColor(btn_recoverAccount);
        adminPane.setSelectedIndex(0);
    }//GEN-LAST:event_btn_manageProductsMouseClicked

    private void btn_salesHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesHistoryMouseClicked
        // TODO add your handling code here:
        //dailySalesBuilder();
        setColor(btn_salesHistory);
        resetColor(btn_manageProducts);
        resetColor(btn_restoreProducts);
        resetColor(btn_adminDashboard);
        resetColor(btn_accounts);
        resetColor(btn_recoverAccount);
        adminPane.setSelectedIndex(1);
    }//GEN-LAST:event_btn_salesHistoryMouseClicked

    private void btn_restoreProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_restoreProductsMouseClicked
        // TODO add your handling code here:
        setColor(btn_restoreProducts);
        resetColor(btn_manageProducts);
        resetColor(btn_salesHistory);
        resetColor(btn_adminDashboard);
        resetColor(btn_accounts);
        resetColor(btn_recoverAccount);
        adminPane.setSelectedIndex(2);
    }//GEN-LAST:event_btn_restoreProductsMouseClicked

    private void nameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameFieldKeyReleased
        // TODO add your handling code here:
        boolean isDouble = true;
        boolean isInteger = true;

        try{
            double cashDouble = Double.parseDouble(nameField.getText());
        }catch(Exception a){
            isDouble = false;
        }

        try{
            int cashInteger = Integer.parseInt(nameField.getText());
        }catch(Exception a){
            isInteger = false;
        }
        
        if(isDouble == true && isInteger == true){
            nameField.setText("");
        }
        
        
            

   
    }//GEN-LAST:event_nameFieldKeyReleased

    private void finalTotalFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalTotalFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finalTotalFieldActionPerformed

    private void printReceiptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printReceiptBtnActionPerformed
        // TODO add your handling code here:
        try{
            receiptPane.print();
            
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_printReceiptBtnActionPerformed

    private void changeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_changeFieldActionPerformed

    private void adminDashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminDashboardLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminDashboardLabelMouseClicked

    private void btn_adminDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_adminDashboardMouseClicked
        // TODO add your handling code here:
        setColor(btn_adminDashboard);
        resetColor(btn_salesHistory);
        resetColor(btn_restoreProducts);
        resetColor(btn_manageProducts);
        resetColor(btn_accounts);
        resetColor(btn_recoverAccount);
        adminPane.setSelectedIndex(3);
    }//GEN-LAST:event_btn_adminDashboardMouseClicked

    private void restoreProductBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restoreProductBtn1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_restoreProductBtn1MouseClicked

    private void btn_accountsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_accountsMouseClicked
        // TODO add your handling code here:
        setColor(btn_accounts);
        resetColor(btn_manageProducts);
        resetColor(btn_salesHistory);
        resetColor(btn_restoreProducts);
        resetColor(btn_adminDashboard);
        resetColor(btn_recoverAccount);
        adminPane.setSelectedIndex(4);
    }//GEN-LAST:event_btn_accountsMouseClicked

    private void showPasswordCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordCheckBoxActionPerformed
        // TODO add your handling code here:
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setEchoChar((char)0); 
        } else {
            passwordField.setEchoChar('*');
        }
    }//GEN-LAST:event_showPasswordCheckBoxActionPerformed

    private void welcomeUserLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_welcomeUserLabel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_welcomeUserLabel2MouseClicked

    private void userDashboardReturnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userDashboardReturnBtnActionPerformed
        // TODO add your handling code here:
        generalPane.setSelectedIndex(5);
    }//GEN-LAST:event_userDashboardReturnBtnActionPerformed

    private void pointOfSalesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointOfSalesBtnActionPerformed
        // TODO add your handling code here:
        generalPane.setSelectedIndex(1);
    }//GEN-LAST:event_pointOfSalesBtnActionPerformed

    private void userLogoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userLogoutBtnMouseClicked
        // TODO add your handling code here:
        generalPane.setSelectedIndex(0);
    }//GEN-LAST:event_userLogoutBtnMouseClicked

    private void adminAddProductButton20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton20MouseClicked

    private void userSalesTodayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userSalesTodayMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_userSalesTodayMouseClicked

    private void userTransactionsTodayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTransactionsTodayMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_userTransactionsTodayMouseClicked

    private void adminAddProductButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton7MouseClicked

    private void userSalesHistBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSalesHistBtnActionPerformed
        // TODO add your handling code here:
        generalPane.setSelectedIndex(6);

    }//GEN-LAST:event_userSalesHistBtnActionPerformed

    private void welcomeUserLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_welcomeUserLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_welcomeUserLabelMouseClicked

    private void userTotalSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTotalSalesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_userTotalSalesMouseClicked

    private void adminAddProductButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton11MouseClicked

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        // TODO add your handling code here:
        String search = searchField.getText().toLowerCase();
        searchToTable(search);
    }//GEN-LAST:event_searchFieldKeyReleased

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void selectCategoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCategoryComboBoxActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)product_table.getModel();
        String selectedCategory = selectCategoryComboBox.getSelectedItem().toString();

        try {
            model.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line = null;
            int count = 0;

            while( (line = reader.readLine()) !=null ){
                String[] lineData = line.split(";");
                count++;

                if(selectedCategory.equals("All Products")){
                    displayToTable();
                }else if(lineData[5].equals("1") && lineData[4].equals(selectedCategory)){
                    model.addRow(new Object[] {lineData[0],lineData[1],lineData[2],lineData[3]});
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_selectCategoryComboBoxActionPerformed

    private void selectCategoryComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectCategoryComboBoxMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_selectCategoryComboBoxMouseClicked

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBtnActionPerformed
        // TODO add your handling code heer:

        int response = JOptionPane.showConfirmDialog(this, "Confirm Action", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(response == JOptionPane.YES_OPTION){
            DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
            model.setRowCount(0);
            total();
            nameField.setText("");
            cashField.setText("");
            changeField.setText("");
            generalPane.setSelectedIndex(5);
        }

    }//GEN-LAST:event_logOutBtnActionPerformed

    private void product_tableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_product_tableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_product_tableMouseEntered

    private void product_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_product_tableMouseClicked
        // TODO add your handling code here:

        int productRow = product_table.getSelectedRow();
        int column = product_table.getColumnCount();
        int productStock = Integer.parseInt(product_table.getValueAt(productRow, 3)+"");

        if(productStock<=0){
            addProductBtn.setText("Out of Stock");
        }else{
            addProductBtn.setText("Add");
        }
        
        purchase_table.clearSelection();
        qtyField.setText("1");
        addProductBtn.setEnabled(true);

    }//GEN-LAST:event_product_tableMouseClicked

    private void purchase_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchase_tableMouseClicked
        // TODO add your handling code here:
        int row = purchase_table.getSelectedRow();
        Object quantity = purchase_table.getValueAt(row, 1);
        addBtn.setEnabled(true);
        qtyField.setEnabled(true);
        minusBtn.setEnabled(true);
        removeBtn.setEnabled(true);
        addProductBtn.setEnabled(false);

        product_table.clearSelection();
        qtyField.setText(quantity+"");
    }//GEN-LAST:event_purchase_tableMouseClicked

    private void payBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBtnActionPerformed
        // TODO add your handling code here:
        int rowCount = purchase_table.getRowCount();
        if(rowCount>0){
            generalPane.setSelectedIndex(3);
        }
        receiptGenerator();
    }//GEN-LAST:event_payBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
        int selectedRow = purchase_table.getSelectedRow();
        int value = Integer.parseInt(purchase_table.getValueAt(selectedRow,1)+"");
        stockModifier("purchase_table","increase",value);
        model.removeRow(purchase_table.getSelectedRow());
        total();
    }//GEN-LAST:event_removeBtnActionPerformed

    private void minusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusBtnActionPerformed
        // TODO add your handling code here:

        int value = Integer.parseInt(qtyField.getText());
        if(value==1){
            value--;
            stockModifier("purchase_table","increase",1);
            DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
            model.removeRow(purchase_table.getSelectedRow());
            total();
        }else if(value>=1){
            value--;
            total();
        }
        qtyField.setText(value+"");

        int row = purchase_table.getSelectedRow();
        purchase_table.setValueAt(value,row, 1);
        stockModifier("purchase_table","increase",1);
        total();
    }//GEN-LAST:event_minusBtnActionPerformed

    private void qtyFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyFieldKeyReleased
        // TODO add your handling code here:
        int selectedRow = purchase_table.getSelectedRow();
        int quantity = Integer.parseInt(qtyField.getText());;
        int purchasedQuantity = Integer.parseInt(purchase_table.getValueAt(selectedRow,1)+"");
        int maxQuantity = maxQuantity()+purchasedQuantity;

        System.out.println("New Quantityyyyyyyyyyyyyyyyyy: "+quantity+"");
        System.out.println("Raw Max Quantityyyyyyyyyyyyyyyyyy: "+maxQuantity()+"");
        System.out.println("Purchased Quantityyyyyyyyyyyyyyyyyy: "+purchasedQuantity+"");
        System.out.println("Max Quantityyyyyyyyyyyyyyyyyy: "+maxQuantity+"");
        System.out.println("Max Quantityyyyyyyyyyyyyyyyyy: "+maxQuantity+"");
        System.out.println("Max Quantityyyyyyyyyyyyyyyyyy: "+maxQuantity+"");
        System.out.println();

        //quantity = Integer.parseInt(qtyField.getText());
        //stockModifier("purchase_table","increase",prevQuantity);

        if(quantity==0){
            DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
            stockModifier("purchase_table","increase",purchasedQuantity);
            model.removeRow(purchase_table.getSelectedRow());
            total();

            System.out.println("Quantity==0");
            System.out.println("Quantity==0");
            System.out.println("Quantity==0");
            System.out.println();

        }else if(quantity>maxQuantity){
            quantity = maxQuantity;
            purchase_table.setValueAt(quantity,selectedRow,1);
            stockModifier("purchase_table","decrease",maxQuantity);
            qtyField.setText(quantity+"");
            total();

            System.out.println("Quantity > Max Quantity");
            System.out.println("Quantity > Max Quantity");
            System.out.println("Quantity > Max Quantity");
            System.out.println();

        }else{
            stockModifier("purchase_table","decrease",quantity-purchasedQuantity);
            purchase_table.setValueAt(quantity,selectedRow,1);
            total();

            System.out.println("Else Block");
            System.out.println("Else Block");
            System.out.println("Else Block");
            System.out.println();
        }
    }//GEN-LAST:event_qtyFieldKeyReleased

    private void qtyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyFieldActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
        int value = Integer.parseInt(qtyField.getText());
        value++;
        qtyField.setText(value+"");

        int row = purchase_table.getSelectedRow();
        purchase_table.setValueAt(value,row, 1);
        stockModifier("purchase_table","decrease",1);
        total();
    }//GEN-LAST:event_addBtnActionPerformed

    private void addProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtnActionPerformed
        // TODO add your handling code here:
        int rowCount = purchase_table.getRowCount();
        int productRow = product_table.getSelectedRow();
        int column = product_table.getColumnCount();
        String productName = product_table.getValueAt(productRow, 1)+"";
        int productStock = Integer.parseInt(product_table.getValueAt(productRow, 3)+"");

        if(productStock>0){
            addProductBtn.setText("Add");
            Object[] lineData = new Object[column];

            for(int i = 0; i < column; i++) {
                lineData[i] = product_table.getValueAt(productRow, i);
                //            System.out.println("lineData[i]: "+lineData[i]);
                //            System.out.println("product ID: "+productName);
            }

            boolean exists = false;
            for(int a=0; a<rowCount; a++){
                if(purchase_table.getRowCount()>0){
                    if(productName.equals(purchase_table.getValueAt(a,0)+"") ){
                        int value = Integer.parseInt(purchase_table.getValueAt(a,1)+"");
                        //System.out.println("row a column 1: "+value);

                        value++;
                        purchase_table.setValueAt(value,a, 1);
                        exists = true;
                        break;
                    }
                }
            }

            if(exists==false){
                DefaultTableModel model = (DefaultTableModel)purchase_table.getModel();
                model.addRow(new Object[] {lineData[1],"1",lineData[2]});
                //System.out.println("Pass: else block");
            }
            stockModifier("product_table","decrease",1);
            //System.out.println("purchase table row 0 column 0: "+purchase_table.getValueAt(0,0)+"");
            total();
            product_table.changeSelection(productRow, 1,false,false);
        }else{
            addProductBtn.setText("Out of Stock");
        }
    }//GEN-LAST:event_addProductBtnActionPerformed

    private void restoreProductBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restoreProductBtn2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_restoreProductBtn2MouseClicked

    private void btn_recoverAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_recoverAccountMouseClicked
        // TODO add your handling code here:
        setColor(btn_recoverAccount);
        resetColor(btn_accounts);
        resetColor(btn_manageProducts);
        resetColor(btn_salesHistory);
        resetColor(btn_restoreProducts);
        resetColor(btn_adminDashboard);
        adminPane.setSelectedIndex(7);
    }//GEN-LAST:event_btn_recoverAccountMouseClicked

    private void adminRecoverAccBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRecoverAccBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = recoverAccountTable.getSelectedRow();
        String username = recoverAccountTable.getValueAt(selectedRow,0)+"";

        ArrayList<String> lineData = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            String line = reader.readLine();
            int count=0;
            while( line != null ){
                lineData.add(line+'\n');
                String[] arrayData = lineData.get(count).split(";");
                if(arrayData[0].equals(username)){
                    lineData.set(count,arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+arrayData[3]+";"+arrayData[4]+";"+arrayData[5]+";"+"1"+'\n');
                }
                count++;
                line=reader.readLine();
            }
            reader.close();
            //debugger
            for(int b=0; b<lineData.size(); b++){
                System.out.print(lineData.get(b));
            }

            FileWriter writer = new FileWriter("accounts.txt");
            for(int a=0; a<lineData.size(); a++){
                writer.write(lineData.get(a));
            }
            writer.close();
            displayAccountsTable();

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_adminRecoverAccBtnActionPerformed

    private void recoverAccountTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recoverAccountTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_recoverAccountTableMouseClicked

    private void changePassUsernameLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePassUsernameLabel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_changePassUsernameLabel1MouseClicked

    private void registerReturnBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerReturnBtnMouseClicked
        // TODO add your handling code here:
        adminPane.setSelectedIndex(4);
    }//GEN-LAST:event_registerReturnBtnMouseClicked

    private void accTypeRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accTypeRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accTypeRegisterActionPerformed

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        // TODO add your handling code here:

        String username = usernameRegister.getText();
        String firstName = firstNameRegister.getText();
        String middleName = midNameRegister.getText();
        String lastName = lastNameRegister.getText();
        String password = String.valueOf(passwordRegister.getPassword());
        String confirmPassword = String.valueOf(confirmPassRegister.getPassword());
        String accType = accTypeRegister.getSelectedItem().toString();

        boolean usernameExists = false;

        try {
            BufferedReader file_reader = new BufferedReader(new FileReader("accounts.txt"));
            String data = file_reader.readLine();
            while( data != null ){
                String[] arrayData = data.split(";");
                if(arrayData[0].equals(username)){
                    usernameExists = true;
                }
                data = file_reader.readLine();
            }
            file_reader.close();

            //Password Validator
            Pattern upperCasePattern = Pattern.compile("[A-Z ]");
            Pattern lowerCasePattern = Pattern.compile("[a-z ]");
            Pattern specialCharPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Pattern digitCasePattern = Pattern.compile("[0-9 ]");

            Matcher matcherUpperCase = upperCasePattern.matcher(password);
            Matcher matcherLowerCase = lowerCasePattern.matcher(password);
            Matcher matcherSpecialChar = specialCharPattern.matcher(password);
            Matcher matcherNumber = digitCasePattern.matcher(password);

            boolean hasEightCharacters = false;
            boolean hasUpperCase = matcherUpperCase.find();
            boolean hasLowerCase = matcherLowerCase.find();
            boolean hasSpecialCharacter = matcherSpecialChar.find();
            boolean hasNumber = matcherNumber.find();
            boolean passwordIsConfirmed = false;
            boolean completeFields = true;

            ArrayList<String> textErrors = new ArrayList<>();

            if(usernameExists){
                textErrors.add("• Username already exists");
            }

            if(!(password.equals(""))){
                if(password.length()>=8){
                    hasEightCharacters = true;
                }else{
                    textErrors.add("• Password must at least contain eight characters");
                }

                if(hasUpperCase == false){
                    textErrors.add("• Password must contain Upper case letter/s");
                }

                if(hasLowerCase == false){
                    textErrors.add("• Password must contain Lower case letter/s");
                }

                if(hasSpecialCharacter == false){
                    textErrors.add("• Password must contain Special Character/s");
                }

                if(hasNumber == false){
                    textErrors.add("• Password must contain Number/s");
                }

                if(password.equals(confirmPassword)){
                    passwordIsConfirmed = true;
                }else{
                    textErrors.add("• Confirm Password");
                }
            }else{
                textErrors.add("• Password cannot be empty");
            }

            if(firstName.equals("") || middleName.equals("") || lastName.equals("")){
                completeFields = false;
                textErrors.add("• Incomplete fields");
            }

            if(hasEightCharacters && hasUpperCase && hasLowerCase && completeFields
                && hasSpecialCharacter && hasNumber && passwordIsConfirmed && usernameExists == false){

                registerErrorDisplay.setText("");

                FileWriter writer = new FileWriter("accounts.txt",true);
                writer.write(username+";"+password+";"+accType+";"+firstName+";"+middleName+";"+lastName+";"+"1"+'\n');
                writer.close();

                usernameRegister.setText("");
                firstNameRegister.setText("");
                midNameRegister.setText("");
                midNameRegister.setText("");
                lastNameRegister.setText("");
                passwordRegister.setText("");
                confirmPassRegister.setText("");
                displayAccountsTable();

            }else{
                registerErrorDisplay.setText("");
                for(int x=0; x<textErrors.size(); x++){
                    registerErrorDisplay.setText(registerErrorDisplay.getText()+textErrors.get(x)+'\n');
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }

    }//GEN-LAST:event_registerBtnActionPerformed

    private void lastNameRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lastNameRegisterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameRegisterMouseClicked

    private void midNameRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midNameRegisterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_midNameRegisterMouseClicked

    private void firstNameRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstNameRegisterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameRegisterMouseClicked

    private void usernameRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameRegisterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameRegisterMouseClicked

    private void showRegisterConfirmPassCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showRegisterConfirmPassCheckBoxActionPerformed
        // TODO add your handling code here:
        if (showRegisterConfirmPassCheckBox.isSelected()) {
            confirmPassRegister.setEchoChar((char)0);
        } else {
            confirmPassRegister.setEchoChar('*');
        }
    }//GEN-LAST:event_showRegisterConfirmPassCheckBoxActionPerformed

    private void showRegisterPassCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showRegisterPassCheckBoxActionPerformed
        // TODO add your handling code here:
        if (showRegisterPassCheckBox.isSelected()) {
            passwordRegister.setEchoChar((char)0);
        } else {
            passwordRegister.setEchoChar('*');
        }
    }//GEN-LAST:event_showRegisterPassCheckBoxActionPerformed

    private void changePassReturnBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePassReturnBtn1MouseClicked
        // TODO add your handling code here:
        //int response = JOptionPane.showConfirmDialog(null,"MESSAGE","TITLE",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);

        int response = JOptionPane.showConfirmDialog(this, "Password will be reset to '0000', proceed?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(response == JOptionPane.YES_OPTION){
            int selectedRow = accountsTable.getSelectedRow();

            String username = accountsTable.getValueAt(selectedRow,0)+"";
            String firstName = adminFirstNameField.getText();
            String middleName = adminMidNameField.getText();
            String lastName = adminLastNameField.getText();

            ArrayList<String> lineData = new ArrayList<>();

            if(!(username.equals("")) && !(firstName.equals("")) && !(middleName.equals("")) && !(lastName.equals(""))){
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
                    String line = reader.readLine();
                    int count=0;
                    while( line != null ){
                        lineData.add(line+'\n');
                        String[] arrayData = lineData.get(count).split(";");
                        if(arrayData[0].equals(username)){
                            lineData.set(count,arrayData[0]+";"+"0000"+";"+arrayData[2]+";"+arrayData[3]+";"+arrayData[4]+";"+arrayData[5]+";"+"1"+'\n');
                        }
                        count++;
                        line=reader.readLine();
                    }
                    reader.close();

                    FileWriter writer = new FileWriter("accounts.txt");
                    for(int a=0; a<lineData.size(); a++){
                        writer.write(lineData.get(a));
                    }
                    writer.close();
                    displayAccountsTable();

                } catch (Exception ex) {
                    Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_changePassReturnBtn1MouseClicked

    private void changePassUsernameLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePassUsernameLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_changePassUsernameLabelMouseClicked

    private void changePassReturnBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePassReturnBtnMouseClicked
        // TODO add your handling code here:
        adminPane.setSelectedIndex(4);
    }//GEN-LAST:event_changePassReturnBtnMouseClicked

    private void changePasswordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = accountsTable.getSelectedRow();

        String username = accountsTable.getValueAt(selectedRow,0)+"";
        String oldPassword = "";
        String currentPassword = String.valueOf(currentPasswordField.getPassword());
        String newPassword = String.valueOf(newPasswordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String type = adminAccTypeComboBox.getSelectedItem().toString();
        String firstName = adminFirstNameField.getText();
        String middleName = adminMidNameField.getText();
        String lastName = adminLastNameField.getText();

        try {
            BufferedReader file_reader = new BufferedReader(new FileReader("accounts.txt"));
            String data = file_reader.readLine();
            while( data != null ){
                String[] arrayData = data.split(";");
                if(arrayData[0].equals(username)){
                    oldPassword = arrayData[1];
                }
                data = file_reader.readLine();
            }
            file_reader.close();

            //Password Validator
            Pattern upperCasePattern = Pattern.compile("[A-Z ]");
            Pattern lowerCasePattern = Pattern.compile("[a-z ]");
            Pattern specialCharPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Pattern digitCasePattern = Pattern.compile("[0-9 ]");

            Matcher matcherUpperCase = upperCasePattern.matcher(newPassword);
            Matcher matcherLowerCase = lowerCasePattern.matcher(newPassword);
            Matcher matcherSpecialChar = specialCharPattern.matcher(newPassword);
            Matcher matcherNumber = digitCasePattern.matcher(newPassword);

            boolean isEqualToCurrentPassword = false;
            boolean hasEightCharacters = false;
            boolean hasUpperCase = matcherUpperCase.find();
            boolean hasLowerCase = matcherLowerCase.find();
            boolean hasSpecialCharacter = matcherSpecialChar.find();
            boolean hasNumber = matcherNumber.find();
            boolean passwordIsConfirmed = false;

            ArrayList<String> textErrors = new ArrayList<>();

            if(!(currentPassword.equals(""))){
                if(currentPassword.equals(oldPassword)){
                    isEqualToCurrentPassword = true;
                }else{
                    textErrors.add("• Current Password is inccorect");
                }
            }else{
                textErrors.add("• Current Password cannot be empty");
            }

            if(!(newPassword.equals(""))){
                if(newPassword.length()>=8){
                    hasEightCharacters = true;
                }else{
                    textErrors.add("• Password must at least contain eight characters");
                }

                if(hasUpperCase == false){
                    textErrors.add("• Password must contain Upper case letter/s");
                }

                if(hasLowerCase == false){
                    textErrors.add("• Password must contain Lower case letter/s");
                }

                if(hasSpecialCharacter == false){
                    textErrors.add("• Password must contain Special Character/s");
                }

                if(hasNumber == false){
                    textErrors.add("• Password must contain Number/s");
                }

                if(newPassword.equals(confirmPassword)){
                    passwordIsConfirmed = true;
                }else{
                    textErrors.add("• Confirm Password");
                }
            }else{
                textErrors.add("• New Password cannot be empty");
            }

            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            ArrayList<String> lineData = new ArrayList<>();
            if(isEqualToCurrentPassword && hasEightCharacters && hasUpperCase && hasLowerCase
                && hasSpecialCharacter && hasNumber && passwordIsConfirmed){
                String line = reader.readLine();
                int count =0;
                while( line != null ){
                    lineData.add(line+'\n');
                    String[] arrayData = lineData.get(count).split(";");
                    if(arrayData[0].equals(username)){
                        lineData.set(count, username+";"+newPassword+";"+type+";"+firstName+";"+middleName+";"+lastName+";"+"1"+'\n');
                    }
                    count++;
                    line=reader.readLine();
                }
                reader.close();
                changePassErrorDisplay.setText("");

                FileWriter writer = new FileWriter("accounts.txt");
                for(int a=0; a<lineData.size(); a++){
                    writer.write(lineData.get(a));
                }
                writer.close();
                displayAccountsTable();

                currentPasswordField.setText("");
                newPasswordField.setText("");
                confirmPasswordField.setText("");

            }else{
                changePassErrorDisplay.setText("");
                for(int x=0; x<textErrors.size(); x++){

                    changePassErrorDisplay.setText(changePassErrorDisplay.getText()+textErrors.get(x)+'\n');
                }
            }

            //adminUsernameField.setText("");
            //adminFirstNameField.setText("");
            //adminMidNameField.setText("");
            //adminLastNameField.setText("");

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }

    }//GEN-LAST:event_changePasswordBtnActionPerformed

    private void showConfirmPassCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showConfirmPassCheckBoxActionPerformed
        // TODO add your handling code here:
        if (showConfirmPassCheckBox.isSelected()) {
            confirmPasswordField.setEchoChar((char)0);
        } else {
            confirmPasswordField.setEchoChar('*');
        }
    }//GEN-LAST:event_showConfirmPassCheckBoxActionPerformed

    private void showNewPassCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showNewPassCheckBoxActionPerformed
        // TODO add your handling code here:
        if (showNewPassCheckBox.isSelected()) {
            newPasswordField.setEchoChar((char)0);
        } else {
            newPasswordField.setEchoChar('*');
        }
    }//GEN-LAST:event_showNewPassCheckBoxActionPerformed

    private void showCurrentPassCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCurrentPassCheckBoxActionPerformed
        // TODO add your handling code here:
        if (showCurrentPassCheckBox.isSelected()) {
            currentPasswordField.setEchoChar((char)0);
        } else {
            currentPasswordField.setEchoChar('*');
        }
    }//GEN-LAST:event_showCurrentPassCheckBoxActionPerformed

    private void adminRegisterAccBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRegisterAccBtnActionPerformed
        // TODO add your handling code here:
        adminPane.setSelectedIndex(6);
    }//GEN-LAST:event_adminRegisterAccBtnActionPerformed

    private void adminChangePassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminChangePassBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = accountsTable.getSelectedRow();
        String username = accountsTable.getValueAt(selectedRow,0)+"";

        if(!(username.equals(""))){
            changePassUsernameLabel.setText(username);
            adminPane.setSelectedIndex(5);
        }

    }//GEN-LAST:event_adminChangePassBtnActionPerformed

    private void adminDeactivateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDeactivateBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = accountsTable.getSelectedRow();

        String username = accountsTable.getValueAt(selectedRow,0)+"";
        String firstName = adminFirstNameField.getText();
        String middleName = adminMidNameField.getText();
        String lastName = adminLastNameField.getText();

        ArrayList<String> lineData = new ArrayList<>();

        if(!(firstName.equals("")) && !(middleName.equals("")) && !(lastName.equals(""))){
            try {
                BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
                String line = reader.readLine();
                int count=0;
                while( line != null ){
                    lineData.add(line+'\n');
                    String[] arrayData = lineData.get(count).split(";");
                    if(arrayData[0].equals(username)){
                        lineData.set(count,arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+arrayData[3]+";"+arrayData[4]+";"+arrayData[5]+";"+"0"+'\n');
                    }
                    count++;
                    line=reader.readLine();
                }
                reader.close();
                //debugger
                for(int b=0; b<lineData.size(); b++){
                    System.out.print(lineData.get(b));
                }

                FileWriter writer = new FileWriter("accounts.txt");
                for(int a=0; a<lineData.size(); a++){
                    writer.write(lineData.get(a));
                }
                writer.close();
                displayAccountsTable();

                adminUsernameField.setText("");
                adminFirstNameField.setText("");
                adminMidNameField.setText("");
                adminLastNameField.setText("");

            } catch (Exception ex) {
                Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_adminDeactivateBtnActionPerformed

    private void adminSaveCredsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminSaveCredsBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = accountsTable.getSelectedRow();

        String username = accountsTable.getValueAt(selectedRow,0)+"";
        String type = adminAccTypeComboBox.getSelectedItem().toString();
        String firstName = adminFirstNameField.getText();
        String middleName = adminMidNameField.getText();
        String lastName = adminLastNameField.getText();

        int rowCount = adminProductTable.getRowCount();
        ArrayList<String> lineData = new ArrayList<>();

        if(!(username.equals("")) && !(firstName.equals("")) && !(middleName.equals("")) && !(lastName.equals(""))){
            try {
                BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
                String line = reader.readLine();
                int count=0;
                while( line != null ){
                    lineData.add(line+'\n');
                    String[] arrayData = lineData.get(count).split(";");
                    if(arrayData[0].equals(username)){
                        lineData.set(count, username+";"+arrayData[1]+";"+type+";"+firstName+";"+middleName+";"+lastName+";"+"1"+'\n');
                    }
                    count++;
                    line=reader.readLine();
                }
                reader.close();

                //debugger
                //                for(int b=0; b<lineData.size(); b++){
                    //                    System.out.print(lineData.get(b));
                    //                }

                FileWriter writer = new FileWriter("accounts.txt");
                for(int a=0; a<lineData.size(); a++){
                    writer.write(lineData.get(a));
                }
                writer.close();
                displayAccountsTable();

                adminUsernameField.setText("");
                adminFirstNameField.setText("");
                adminMidNameField.setText("");
                adminLastNameField.setText("");

            } catch (Exception ex) {
                Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_adminSaveCredsBtnActionPerformed

    private void adminAccTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminAccTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAccTypeComboBoxActionPerformed

    private void adminMidNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminMidNameFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminMidNameFieldMouseClicked

    private void adminFirstNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminFirstNameFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminFirstNameFieldMouseClicked

    private void adminLastNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminLastNameFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminLastNameFieldMouseClicked

    private void adminUsernameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminUsernameFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminUsernameFieldMouseClicked

    private void accountsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsTableMouseClicked
        // TODO add your handling code here:

        int selectedRow = accountsTable.getSelectedRow();
        adminUsernameField.setText(accountsTable.getValueAt(selectedRow, 0)+"");
        adminAccTypeComboBox.setSelectedItem(accountsTable.getValueAt(selectedRow, 1)+"");
        adminFirstNameField.setText(accountsTable.getValueAt(selectedRow, 2)+"");
        adminMidNameField.setText(accountsTable.getValueAt(selectedRow, 3)+"");
        adminLastNameField.setText(accountsTable.getValueAt(selectedRow, 4)+"");

    }//GEN-LAST:event_accountsTableMouseClicked

    private void adminThirdPlace1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminThirdPlace1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminThirdPlace1MouseClicked

    private void outOfStockProductsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outOfStockProductsLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_outOfStockProductsLabelMouseClicked

    private void adminAddProductButton21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton21MouseClicked

    private void averageDailySalesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_averageDailySalesLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_averageDailySalesLabelMouseClicked

    private void adminAddProductButton19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton19MouseClicked

    private void averageMonthlySalesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_averageMonthlySalesLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_averageMonthlySalesLabelMouseClicked

    private void adminAddProductButton18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton18MouseClicked

    private void productCountLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productCountLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_productCountLabelMouseClicked

    private void adminAddProductButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton13MouseClicked

    private void adminThirdPlaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminThirdPlaceMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminThirdPlaceMouseClicked

    private void adminAddProductButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton16MouseClicked

    private void adminSecondPlaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminSecondPlaceMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminSecondPlaceMouseClicked

    private void adminAddProductButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton15MouseClicked

    private void adminFirstPlaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminFirstPlaceMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminFirstPlaceMouseClicked

    private void adminAddProductButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton14MouseClicked

    private void adminAddProductButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton12MouseClicked

    private void adminTotalSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminTotalSalesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminTotalSalesMouseClicked

    private void adminAddProductButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton10MouseClicked

    private void salesTodayLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTodayLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_salesTodayLabelMouseClicked

    private void adminAddProductButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton9MouseClicked

    private void salesThisMonthLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesThisMonthLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_salesThisMonthLabelMouseClicked

    private void adminAddProductButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton8MouseClicked

    private void adminTransactionsTodayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminTransactionsTodayMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminTransactionsTodayMouseClicked

    private void adminAddProductButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton6MouseClicked

    private void adminAddProductButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton3MouseClicked

    private void restoreBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = restoreProductTable.getSelectedRow();

        String productID = restoreProductTable.getValueAt(selectedRow,0)+"";
        ArrayList<String> lineData = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line = reader.readLine();
            int count=0;
            while( line != null ){
                lineData.add(line+'\n');
                String[] arrayData = lineData.get(count).split(";");
                if(arrayData[0].equals(productID)){
                    lineData.set(count,arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+arrayData[3]+";"+arrayData[4]+";"+"1"+'\n');
                }
                count++;
                line=reader.readLine();
            }
            reader.close();
            //debugger
            for(int b=0; b<lineData.size(); b++){
                System.out.print(lineData.get(b));
            }

            FileWriter writer = new FileWriter("products.txt");
            for(int a=0; a<lineData.size(); a++){
                writer.write(lineData.get(a));
            }
            writer.close();
            displayToTable();

            adminProductIDField.setText("");
            newProductNameField.setText("");
            newProductPriceField.setText("");
            newProductStockField.setText("");

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_restoreBtnActionPerformed

    private void adminAddProductButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton2MouseClicked

    private void dailySalesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailySalesComboBoxActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_dailySalesComboBoxActionPerformed

    private void dailySalesComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dailySalesComboBoxMouseClicked
        // TODO add your handling code here:
        try {
            System.out.println("dailySalesComboBoxActionPerformed ///////////////////////////////////////////////////////");
            System.out.println();
            String selectedDate = dailySalesComboBox.getSelectedItem().toString();
            DefaultTableModel dailySalesTable_model = (DefaultTableModel)dailySalesTable.getModel();
            dailySalesTable_model.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("dailySales.txt"));
            String line = null;
            int count = 0;

            while( (line = reader.readLine()) !=null ){
                String[] lineData = line.split(";");
                count++;
                //dailySalesComboBox.addItem(lineData[0]);

                if(lineData[0].equals(selectedDate)){
                    for(int a=1; a<lineData.length-1; a+=2){
                        dailySalesTable_model.addRow(new Object[] {lineData[a],lineData[a+1]});
                    }
                }

            }
            reader.close();

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_dailySalesComboBoxMouseClicked

    private void totalSalesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalSalesFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalSalesFieldActionPerformed

    private void adminAddProductButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminAddProductButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adminAddProductButton1MouseClicked

    private void adminRemoveProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRemoveProductBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = adminProductTable.getSelectedRow();

        String productID = adminProductTable.getValueAt(selectedRow,0)+"";
        String name = newProductNameField.getText();
        String price = newProductPriceField.getText();
        int rowCount = adminProductTable.getRowCount();
        ArrayList<String> lineData = new ArrayList<>();

        if(!(name.equals("")) && !(price.equals(""))){
            try {
                BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
                String line = reader.readLine();
                int count=0;
                while( line != null ){
                    lineData.add(line+'\n');
                    String[] arrayData = lineData.get(count).split(";");
                    if(arrayData[0].equals(productID)){
                        lineData.set(count,arrayData[0]+";"+arrayData[1]+";"+arrayData[2]+";"+arrayData[3]+";"+arrayData[4]+";"+"0"+'\n');
                    }
                    count++;
                    line=reader.readLine();
                }
                reader.close();
                //debugger
                for(int b=0; b<lineData.size(); b++){
                    System.out.print(lineData.get(b));
                }

                FileWriter writer = new FileWriter("products.txt");
                for(int a=0; a<lineData.size(); a++){
                    writer.write(lineData.get(a));
                }
                writer.close();
                displayToTable();

                adminProductIDField.setText("");
                newProductNameField.setText("");
                newProductPriceField.setText("");
                newProductStockField.setText("");
                //newProductCategoryField.setText("");

            } catch (Exception ex) {
                Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_adminRemoveProductBtnActionPerformed

    private void adminAddProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminAddProductBtnActionPerformed
        // TODO add your handling code here:
        adminProductIDField.setEditable(true);
        adminAddProductBtn.setText("Add Product");

        String productID = adminProductIDField.getText();
        String name = newProductNameField.getText();
        String price = newProductPriceField.getText();
        String stock = newProductStockField.getText();
        String category = productCategoryComboBox.getSelectedItem().toString();
        int rowCount = adminProductTable.getRowCount();

        ArrayList<String> lineData = new ArrayList<>();

        boolean productID_exists = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line = reader.readLine();
            int count=0;
            while( line != null ){
                lineData.add(line+'\n');
                String[] arrayData = lineData.get(count).split(";");
                if(arrayData[0].equals(productID)){
                    productID_exists=true;
                    adminAddProductBtn.setText("Product ID already exists");
                }
                count++;
                line=reader.readLine();
            }
            reader.close();
            //debugger
            for(int b=0; b<lineData.size(); b++){
                System.out.print(lineData.get(b));
            }

            if(productID_exists==false && !(productID.equals("")) && !(name.equals("")) && !(price.equals(""))){
                FileWriter writer = new FileWriter("products.txt",true);

                writer.write(productID+";"+name+";"+price+";"+stock+";"+category+";"+"1"+'\n');

                writer.close();
                displayToTable();

                adminProductIDField.setText("");
                newProductNameField.setText("");
                newProductPriceField.setText("");
                newProductStockField.setText("");
                //newProductCategoryField.setText("");
            }

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_adminAddProductBtnActionPerformed

    private void updateProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProductBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = adminProductTable.getSelectedRow();

        String productID = adminProductTable.getValueAt(selectedRow,0)+"";
        String name = newProductNameField.getText();
        String price = newProductPriceField.getText();
        String stock = newProductStockField.getText();
        String category = productCategoryComboBox.getSelectedItem().toString();
        int rowCount = adminProductTable.getRowCount();
        ArrayList<String> lineData = new ArrayList<>();

        if(!(name.equals("")) && !(price.equals(""))){
            try {
                BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
                String line = reader.readLine();
                int count=0;
                while( line != null ){
                    lineData.add(line+'\n');
                    String[] arrayData = lineData.get(count).split(";");
                    if(arrayData[0].equals(productID)){
                        lineData.set(count, (productID+"")+";"+name+";"+price+";"+stock+";"+category+";"+"1"+'\n');
                    }
                    count++;
                    line=reader.readLine();
                }
                reader.close();

                //debugger
                //                for(int b=0; b<lineData.size(); b++){
                    //                    System.out.print(lineData.get(b));
                    //                }

                FileWriter writer = new FileWriter("products.txt");
                for(int a=0; a<lineData.size(); a++){
                    writer.write(lineData.get(a));
                }
                writer.close();
                displayToTable();

                adminProductIDField.setText("");
                newProductNameField.setText("");
                newProductPriceField.setText("");
                newProductStockField.setText("");
                //                newProductCategoryField.setText("");

            } catch (Exception ex) {
                Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_updateProductBtnActionPerformed

    private void adminProductIDFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminProductIDFieldMouseClicked
        // TODO add your handling code here:
        adminAddProductBtn.setText("Add Product");
    }//GEN-LAST:event_adminProductIDFieldMouseClicked

    private void adminProductTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminProductTableMouseClicked
        // TODO add your handling code here:
        //adminProductIDField.setEditable(false);
        adminAddProductBtn.setText("Add Product");
        int selectedRow = adminProductTable.getSelectedRow();
        adminProductIDField.setText(adminProductTable.getValueAt(selectedRow, 0)+"");
        newProductNameField.setText(adminProductTable.getValueAt(selectedRow, 1)+"");
        newProductPriceField.setText(adminProductTable.getValueAt(selectedRow, 2)+"");
        newProductStockField.setText(adminProductTable.getValueAt(selectedRow, 3)+"");
        productCategoryComboBox.setSelectedItem(adminProductTable.getValueAt(selectedRow, 4)+"");
    }//GEN-LAST:event_adminProductTableMouseClicked

    private void newPassUserLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newPassUserLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_newPassUserLabelMouseClicked

    private void showNewPassFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showNewPassFieldActionPerformed
        // TODO add your handling code here:
        if (showNewPassField.isSelected()) {
            newPassField.setEchoChar((char)0); 
        } else {
            newPassField.setEchoChar('*');
        }
    }//GEN-LAST:event_showNewPassFieldActionPerformed

    private void newPassContinueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPassContinueBtnActionPerformed
        // TODO add your handling code here:

        String username = loggedInAccount;
        String newPassword = String.valueOf(newPassField.getPassword());
        String confirmPassword = String.valueOf(confirmNewPassField.getPassword());
//        String firstName = adminFirstNameField.getText();
//        String middleName = adminMidNameField.getText();
//        String lastName = adminLastNameField.getText();

        try {
//            BufferedReader file_reader = new BufferedReader(new FileReader("accounts.txt"));
//            String data = file_reader.readLine();
//            while( data != null ){
//                String[] arrayData = data.split(";");
//                if(arrayData[0].equals(username)){
//                    oldPassword = arrayData[1];
//                }
//                data = file_reader.readLine();
//            }
//            file_reader.close();

            //Password Validator
            Pattern upperCasePattern = Pattern.compile("[A-Z ]");
            Pattern lowerCasePattern = Pattern.compile("[a-z ]");
            Pattern specialCharPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Pattern digitCasePattern = Pattern.compile("[0-9 ]");

            Matcher matcherUpperCase = upperCasePattern.matcher(newPassword);
            Matcher matcherLowerCase = lowerCasePattern.matcher(newPassword);
            Matcher matcherSpecialChar = specialCharPattern.matcher(newPassword);
            Matcher matcherNumber = digitCasePattern.matcher(newPassword);

            boolean hasEightCharacters = false;
            boolean hasUpperCase = matcherUpperCase.find();
            boolean hasLowerCase = matcherLowerCase.find();
            boolean hasSpecialCharacter = matcherSpecialChar.find();
            boolean hasNumber = matcherNumber.find();
            boolean passwordIsConfirmed = false;

            ArrayList<String> textErrors = new ArrayList<>();


            if(!(newPassword.equals(""))){
                if(newPassword.length()>=8){
                    hasEightCharacters = true;
                }else{
                    textErrors.add("• Password must at least contain eight characters");
                }

                if(hasUpperCase == false){
                    textErrors.add("• Password must contain Upper case letter/s");
                }

                if(hasLowerCase == false){
                    textErrors.add("• Password must contain Lower case letter/s");
                }

                if(hasSpecialCharacter == false){
                    textErrors.add("• Password must contain Special Character/s");
                }

                if(hasNumber == false){
                    textErrors.add("• Password must contain Number/s");
                }

                if(newPassword.equals(confirmPassword)){
                    passwordIsConfirmed = true;
                }else{
                    textErrors.add("• Confirm Password");
                }
            }else{
                textErrors.add("• New Password cannot be empty");
            }

            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            ArrayList<String> lineData = new ArrayList<>();
            if(hasEightCharacters && hasUpperCase && hasLowerCase
                && hasSpecialCharacter && hasNumber && passwordIsConfirmed){
                String line = reader.readLine();
                int count =0;
                while( line != null ){
                    lineData.add(line+'\n');
                    String[] arrayData = lineData.get(count).split(";");
                    if(arrayData[0].equals(username)){
                        lineData.set(count, arrayData[0]+";"+newPassword+";"+arrayData[2]+";"+arrayData[3]+";"+arrayData[4]+";"+arrayData[4]+";"+"1"+'\n');
                    }
                    count++;
                    line=reader.readLine();
                }
                reader.close();
                generalPane.setSelectedIndex(5);
                newPassErrorDisplay.setText("");

                FileWriter writer = new FileWriter("accounts.txt");
                for(int a=0; a<lineData.size(); a++){
                    writer.write(lineData.get(a));
                }
                writer.close();
                displayAccountsTable();

                newPassField.setText("");
                confirmNewPassField.setText("");

            }else{
                newPassErrorDisplay.setText("");
                for(int x=0; x<textErrors.size(); x++){

                    newPassErrorDisplay.setText(newPassErrorDisplay.getText()+textErrors.get(x)+'\n');
                }
            }

            //adminUsernameField.setText("");
            //adminFirstNameField.setText("");
            //adminMidNameField.setText("");
            //adminLastNameField.setText("");

        } catch (Exception ex) {
            Logger.getLogger(POS_JFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_newPassContinueBtnActionPerformed

    private void showConfirmNewPassFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showConfirmNewPassFieldActionPerformed
        // TODO add your handling code here:
        if (showConfirmNewPassField.isSelected()) {
            confirmNewPassField.setEchoChar((char)0); 
        } else {
            confirmNewPassField.setEchoChar('*');
        }
    }//GEN-LAST:event_showConfirmNewPassFieldActionPerformed

    private void newPassUserLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newPassUserLabel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_newPassUserLabel1MouseClicked

    private void newPassUserLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newPassUserLabel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_newPassUserLabel2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(POS_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POS_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POS_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POS_JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POS_JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LoginBtn;
    private javax.swing.JComboBox<String> accTypeRegister;
    private javax.swing.JTable accountsTable;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addProductBtn;
    private javax.swing.JComboBox<String> adminAccTypeComboBox;
    private javax.swing.JButton adminAddProductBtn;
    private javax.swing.JLabel adminAddProductButton;
    private javax.swing.JLabel adminAddProductButton1;
    private javax.swing.JLabel adminAddProductButton10;
    private javax.swing.JLabel adminAddProductButton11;
    private javax.swing.JLabel adminAddProductButton12;
    private javax.swing.JLabel adminAddProductButton13;
    private javax.swing.JLabel adminAddProductButton14;
    private javax.swing.JLabel adminAddProductButton15;
    private javax.swing.JLabel adminAddProductButton16;
    private javax.swing.JLabel adminAddProductButton18;
    private javax.swing.JLabel adminAddProductButton19;
    private javax.swing.JLabel adminAddProductButton2;
    private javax.swing.JLabel adminAddProductButton20;
    private javax.swing.JLabel adminAddProductButton21;
    private javax.swing.JLabel adminAddProductButton3;
    private javax.swing.JLabel adminAddProductButton6;
    private javax.swing.JLabel adminAddProductButton7;
    private javax.swing.JLabel adminAddProductButton8;
    private javax.swing.JLabel adminAddProductButton9;
    private javax.swing.JButton adminChangePassBtn;
    private javax.swing.JLabel adminDashboardLabel;
    private javax.swing.JButton adminDeactivateBtn;
    private javax.swing.JTextField adminFirstNameField;
    private javax.swing.JLabel adminFirstPlace;
    private javax.swing.JTextField adminLastNameField;
    private javax.swing.JLabel adminLogOutBtn;
    private javax.swing.JTextField adminMidNameField;
    private javax.swing.JTabbedPane adminPane;
    private javax.swing.JTextField adminProductIDField;
    private javax.swing.JTable adminProductTable;
    private javax.swing.JButton adminRecoverAccBtn;
    private javax.swing.JButton adminRegisterAccBtn;
    private javax.swing.JButton adminRemoveProductBtn;
    private javax.swing.JButton adminSaveCredsBtn;
    private javax.swing.JLabel adminSecondPlace;
    private javax.swing.JLabel adminThirdPlace;
    private javax.swing.JLabel adminThirdPlace1;
    private javax.swing.JLabel adminTotalSales;
    private javax.swing.JLabel adminTransactionsToday;
    private javax.swing.JTextField adminUsernameField;
    private javax.swing.JLabel averageDailySalesLabel;
    private javax.swing.JLabel averageMonthlySalesLabel;
    private javax.swing.JPanel btn_accounts;
    private javax.swing.JPanel btn_adminDashboard;
    private javax.swing.JPanel btn_manageProducts;
    private javax.swing.JPanel btn_recoverAccount;
    private javax.swing.JPanel btn_restoreProducts;
    private javax.swing.JPanel btn_salesHistory;
    private javax.swing.JTextField cashField;
    private javax.swing.JTextField changeField;
    private javax.swing.JLabel changeLabel;
    private javax.swing.JTextArea changePassErrorDisplay;
    private javax.swing.JLabel changePassReturnBtn;
    private javax.swing.JLabel changePassReturnBtn1;
    private javax.swing.JLabel changePassUsernameLabel;
    private javax.swing.JLabel changePassUsernameLabel1;
    private javax.swing.JButton changePasswordBtn;
    private javax.swing.JPasswordField confirmNewPassField;
    private javax.swing.JPasswordField confirmPassRegister;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JPasswordField currentPasswordField;
    private javax.swing.JComboBox<String> dailySalesComboBox;
    private javax.swing.JTable dailySalesTable;
    private javax.swing.JButton finalPayBtn;
    private javax.swing.JTextField finalTotalField;
    private javax.swing.JTextField firstNameRegister;
    private javax.swing.JPanel fourPayment;
    private javax.swing.JTabbedPane generalPane;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField lastNameRegister;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JLabel loginNotifLabel;
    private javax.swing.JTextField midNameRegister;
    private javax.swing.JButton minusBtn;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton newPassContinueBtn;
    private javax.swing.JTextArea newPassErrorDisplay;
    private javax.swing.JPasswordField newPassField;
    private javax.swing.JLabel newPassUserLabel;
    private javax.swing.JLabel newPassUserLabel1;
    private javax.swing.JLabel newPassUserLabel2;
    private javax.swing.JPasswordField newPasswordField;
    private javax.swing.JTextField newProductNameField;
    private javax.swing.JTextField newProductPriceField;
    private javax.swing.JTextField newProductStockField;
    private javax.swing.JLabel outOfStockProductsLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPasswordField passwordRegister;
    private javax.swing.JButton payBtn;
    private javax.swing.JButton pointOfSalesBtn;
    private javax.swing.JButton printReceiptBtn;
    private javax.swing.JComboBox<String> productCategoryComboBox;
    private javax.swing.JLabel productCountLabel;
    private javax.swing.JTable product_table;
    private javax.swing.JTable purchase_table;
    private javax.swing.JTextField qtyField;
    private javax.swing.JTextPane receiptPane;
    private javax.swing.JTable recoverAccountTable;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextArea registerErrorDisplay;
    private javax.swing.JLabel registerReturnBtn;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton restoreBtn;
    private javax.swing.JLabel restoreProductBtn;
    private javax.swing.JLabel restoreProductBtn1;
    private javax.swing.JLabel restoreProductBtn2;
    private javax.swing.JTable restoreProductTable;
    private javax.swing.JButton returnBtn;
    private javax.swing.JButton returnBtn1;
    private javax.swing.JLabel salesHistBtn;
    private javax.swing.JTable salesTable;
    private javax.swing.JLabel salesThisMonthLabel;
    private javax.swing.JLabel salesTodayLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JComboBox<String> selectCategoryComboBox;
    private javax.swing.JCheckBox showConfirmNewPassField;
    private javax.swing.JCheckBox showConfirmPassCheckBox;
    private javax.swing.JCheckBox showCurrentPassCheckBox;
    private javax.swing.JCheckBox showNewPassCheckBox;
    private javax.swing.JCheckBox showNewPassField;
    private javax.swing.JCheckBox showPasswordCheckBox;
    private javax.swing.JCheckBox showRegisterConfirmPassCheckBox;
    private javax.swing.JCheckBox showRegisterPassCheckBox;
    private javax.swing.JPanel sixPaymentSuccess;
    private javax.swing.JPanel sixUserDashboard;
    private javax.swing.JTextField totalSalesField;
    private javax.swing.JButton updateProductBtn;
    private javax.swing.JButton userDashboardReturnBtn;
    private javax.swing.JLabel userLogoutBtn;
    private javax.swing.JButton userSalesHistBtn;
    private javax.swing.JTable userSalesTable;
    private javax.swing.JLabel userSalesToday;
    private javax.swing.JLabel userTotalSales;
    private javax.swing.JLabel userTransactionsToday;
    private javax.swing.JTextField usernameField;
    private javax.swing.JTextField usernameRegister;
    private javax.swing.JLabel welcomeUserLabel;
    private javax.swing.JLabel welcomeUserLabel2;
    // End of variables declaration//GEN-END:variables
}
