/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl.dao;

import entities.Accounts;
import inter.dao.IBankDao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 *
 * @author megas
 */

public class ImpDao implements IBankDao{
    
    @Override
    public void writeToFile(ArrayList<Accounts> listAcc, String fName, String fName1) throws FileNotFoundException, IOException {
        if(listAcc.isEmpty()){
            System.out.println("No account has been created! Please create one!");
        }else{
            try{
                //SAVE TO USER.DAT
                FileOutputStream fos = new FileOutputStream(fName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for(Accounts acc : listAcc){
                    Accounts acc1 = new Accounts(acc.getId(), acc.getPassword());
                    oos.writeObject(acc1);
                }
                //SAVE TO BANK.DAT
                fos = new FileOutputStream(fName1);
                oos = new ObjectOutputStream(fos);
                for(Accounts acc: listAcc){
                    Accounts acc2 = new Accounts(acc.getId(), acc.getPassword(), acc.getName(), acc.getBalance());
                    oos.writeObject(acc2);
                }
                fos.close();
                oos.close();
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
    }


    @Override
    public ArrayList<Accounts> loadAccountsFromFile(String fName, String fName1){
        ArrayList<Accounts> accList = new ArrayList();
        Accounts acc1;
        Accounts acc2;
        try{//load from user.dat
            FileInputStream fis = new FileInputStream(fName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(fis.available() >0){
                acc1 = (Accounts)ois.readObject();
                accList.add(acc1);
            }//load from bank.dat
            fis = new FileInputStream(fName1);
            ois = new ObjectInputStream(fis);
            while(fis.available()>0){
                acc2= (Accounts) ois.readObject();
                for(int i=0; i<accList.size(); i++){
                    if(accList.get(i).getId().equals(acc2.getId())){
                        accList.get(i).setName(acc2.getName());
                        accList.get(i).setBalance(acc2.getBalance());
                        break;
                    }
                }
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return accList;
    }



}
