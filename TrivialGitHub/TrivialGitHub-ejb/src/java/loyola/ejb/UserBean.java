/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.ejb;

import java.text.DecimalFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
@Stateless
public class UserBean implements UserBeanLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertUser(UserLoyolaEntity user) {
        entityManager.persist(user);
    }

    @Override
    public boolean userNameExist(String username) {
        List result = entityManager.createQuery(
                "select i from " + UserLoyolaEntity.ENTITY_NAME + " i "
                        + "where i." + UserLoyolaEntity.USERNAME_COL_NAME + " ='" + username + "'").getResultList();
        return !result.isEmpty();
    }

    @Override
    public long getLastId() {
        return entityManager.createQuery("select i FROM " + UserLoyolaEntity.ENTITY_NAME + " i").getResultList().size();
    }

    @Override
    public UserLoyolaEntity getLogedUser(String user, String pass) {
        
        List result =  entityManager.createQuery(
                "select i from " + UserLoyolaEntity.ENTITY_NAME + " i "
                        + "where i." + UserLoyolaEntity.USERNAME_COL_NAME + " ='" + user + "' AND i."+
                        UserLoyolaEntity.PASS_COL_NAME+ " ='" + pass + "'").getResultList();
        if(result.size()>0){
            return (UserLoyolaEntity) result.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public List getAllUsers() {
        return entityManager.createQuery("select i FROM " + UserLoyolaEntity.ENTITY_NAME +
                " i ORDER BY i."+UserLoyolaEntity.FULLNAME_COL_NAME).getResultList();
    }

    
    
    @Override
    public void updateUser(UserLoyolaEntity user) {
        UserLoyolaEntity obtained = entityManager.find(UserLoyolaEntity.class, user.getId());
        obtained.setFullName(user.getFullName());
        obtained.setPass(user.getPass());
        obtained.setUserName(user.getUserName());
        obtained.setRoleName(user.getRoleName());
        obtained.setGroup(user.getGroup());
    }

    @Override
    public void deleteUser(UserLoyolaEntity user) {
        Object toBeRemoved = entityManager.merge(user);
        entityManager.remove(toBeRemoved);
    }

    @Override
    public List getRankUserList() {
        return entityManager.createQuery("select i FROM " + UserLoyolaEntity.ENTITY_NAME +
                " i ORDER BY i." + UserLoyolaEntity.POINTS_COL_NAME + " DESC").getResultList();
    }
    
    

    @Override
    public void playUpdate(UserLoyolaEntity user) {
        UserLoyolaEntity obtained = entityManager.find(UserLoyolaEntity.class, user.getId());
        obtained.setUserTry(obtained.getUserTry() + 1);
        //float points = obtained.getPoints();
        //right
        if(user.getUserRigth()>0){
            obtained.setUserRigth(obtained.getUserRigth()+1);
            DecimalFormat df = new DecimalFormat("###.##");
            //points = Float.parseFloat(df.format(obtained.getPoints() + user.getPoints()));
            obtained.setPoints(Float.parseFloat(df.format(obtained.getPoints() + user.getPoints())));
        }
        double r =obtained.getUserRigth();
        double t = obtained.getUserTry();
        double ave = r/t*1000;
        obtained.setAve((int) ave);
        if(user.getAnsweredQuestions()!=null){
            obtained.getAnsweredQuestions().add(user.getAnsweredQuestions().get(0));
        }
    }

    @Override
    public double getTotalGroupPoints(String group) {
        
        try{
            double val = (double) entityManager.createQuery("select SUM(i." + UserLoyolaEntity.POINTS_COL_NAME+") FROM " + UserLoyolaEntity.ENTITY_NAME +
                    " i WHERE i." + UserLoyolaEntity.GROUP_COL_NAME + "='" + group + "'").getSingleResult();
            return val;
        }catch(NullPointerException e){
            return 0;
        }      
    }

    @Override
    public UserLoyolaEntity getUserById(long id) {
        return (UserLoyolaEntity) entityManager.createQuery("select i FROM " + UserLoyolaEntity.ENTITY_NAME +
                " i WHERE i.id" + "=" + id +
                " ORDER BY i." + UserLoyolaEntity.POINTS_COL_NAME + " DESC").getSingleResult();
    }

    @Override
    public List getFilteredList(String filterValue) {
        return entityManager.createQuery("select i FROM " + UserLoyolaEntity.ENTITY_NAME +" i "+
                "WHERE LOWER(i." + UserLoyolaEntity.FULLNAME_COL_NAME + ") like '%" + filterValue.toLowerCase() + "%'"+
                "OR LOWER(i." + UserLoyolaEntity.USERNAME_COL_NAME + ") like '%" + filterValue.toLowerCase() + "%'"+
                " ORDER BY i."+
                UserLoyolaEntity.FULLNAME_COL_NAME).getResultList();
    } 
}
