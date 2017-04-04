/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import loyola.entitys.GroupEntity;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
@Local
public interface UserBeanLocal {

    void insertUser(UserLoyolaEntity user);

    boolean userNameExist(String username);

    long getLastId();

    UserLoyolaEntity getLogedUser(String user, String pass);

    List getAllUsers();

    void updateUser(UserLoyolaEntity user);

    void deleteUser(UserLoyolaEntity user);

    List getRankUserList();

    void playUpdate(UserLoyolaEntity user);

    double getTotalGroupPoints(String group);

    UserLoyolaEntity getUserById(long id);

    List getFilteredList(String filterValue);
    
}
