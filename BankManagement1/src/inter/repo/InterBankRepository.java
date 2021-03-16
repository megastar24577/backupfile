/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter.repo;

import entities.Accounts;
import java.util.ArrayList;

/**
 *
 * @author megas
 */
public interface InterBankRepository{

    public int create(Accounts newAcc);
    
    public int saveToFile();

    public void removeAccount(String id);
    
    public void setPassword(String password);
    
    public ArrayList<Accounts> read();
}
