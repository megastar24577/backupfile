/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter.dao;

import entities.Accounts;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author megas
 */
public interface IBankDao {
    public void writeToFile(ArrayList<Accounts> listAcc,String fName, String fName1) throws FileNotFoundException, IOException;
    public ArrayList<Accounts> loadAccountsFromFile(String fName, String fName1);
}
