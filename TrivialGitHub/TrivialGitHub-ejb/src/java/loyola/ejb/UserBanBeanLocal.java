/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.ejb;

import java.util.List;
import javax.ejb.Local;
import loyola.entitys.UsersBan;

/**
 *
 * @author hipnosapo
 */
@Local
public interface UserBanBeanLocal {

    void banUser(UsersBan user);

    void unbanUser(UsersBan user);

    List getBanedUsers();

    public boolean isBannedUser(String user);
    
}
