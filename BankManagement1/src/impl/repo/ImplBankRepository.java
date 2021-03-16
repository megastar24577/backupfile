/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl.repo;

import entities.Accounts;
import impl.dao.ImpDao;
import inter.dao.IBankDao;
import inter.repo.InterBankRepository;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ultils.MyUltils;

/**
 *
 * @author megas
 */
public class ImplBankRepository extends ArrayList<Accounts> implements InterBankRepository{
    private String id ="";
    private int position = -1;
    private String fName = "user.dat", fName1 ="bank.dat";
    private IBankDao IDao;
    
    
    //Constructor to load File
    public ImplBankRepository(){
        ArrayList<Accounts> listAcc = new ArrayList<>();
        IDao = new ImpDao();
        listAcc = IDao.loadAccountsFromFile(fName, fName1);
        for(Accounts acc : listAcc){
            this.add(acc);
        }
    }
    
    //Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    
    
    //Method
    @Override
    public int create(Accounts newAcc) {
        try {
            this.add(newAcc);
            return 1;
        } catch (Exception ex) {
            return 0;
        }
    }
    
    @Override
    public int saveToFile() {
        try {
            IDao.writeToFile(this, fName, fName1);
            return 1;
        } catch (IOException ex) {
            Logger.getLogger(ImplBankRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public void removeAccount(String id) {
        this.remove(new Accounts(id));
    }

    @Override
    public void setPassword(String password) {
        for(Accounts acc : this){
            if(acc.getId().equals(this.getId())){
                try {
                    acc.setPassword(MyUltils.toHexString(MyUltils.getSHA(password)));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ImplBankRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public ArrayList<Accounts> read() {
        return this;
    }

    


}
