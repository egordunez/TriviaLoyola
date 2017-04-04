/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import loyola.entitys.UsersBan;

/**
 *
 * @author hipnosapo
 */
@Stateless
public class UserBanBean implements UserBanBeanLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void banUser(UsersBan user) {
        entityManager.persist(user);
    }

    @Override
    public void unbanUser(UsersBan user) {
        Object toBeRemoved = entityManager.merge(user);
        entityManager.remove(toBeRemoved);
    }

    @Override
    public List getBanedUsers() {
        return entityManager.createQuery("select i FROM " + UsersBan.ENTITY_NAME +
                " i ORDER BY i."+ UsersBan.COL_USER_NAME).getResultList();
    }
    
    public boolean isBannedUser(String user){
        List users = getBanedUsers();
        int count = users.size();
        
        for (int i = 0; i < count; i++) {
            if(((UsersBan)users.get(i)).getUserBan().equals(user)){
                return true;
            }
        }
        return false;
    }
    
}
