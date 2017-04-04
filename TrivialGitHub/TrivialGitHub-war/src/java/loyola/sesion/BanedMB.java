/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.sesion;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import loyola.entitys.UsersBan;
import javax.ejb.EJB;
import loyola.ejb.UserBanBeanLocal;

/**
 *
 * @author hipnosapo
 */
@Named(value = "banedMB")
@ViewScoped
public class BanedMB {

    
    @EJB
    private UserBanBeanLocal userBanBeanLocal;
    
    private List banedUsers;
    
    
    @PostConstruct
    public void init() {
        banedUsers = userBanBeanLocal.getBanedUsers();
    }
    public BanedMB() {
    }

    public List getBanedUsers() {
        return banedUsers;
    }

    public void setBanedUsers(List banedUsers) {
        this.banedUsers = banedUsers;
    }
    
    public void unBanUser(UsersBan user){
        userBanBeanLocal.unbanUser(user);
        banedUsers.remove(user);
    }
    
}
