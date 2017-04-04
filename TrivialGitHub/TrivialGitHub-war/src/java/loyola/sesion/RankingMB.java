/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.sesion;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import loyola.ejb.UserBeanLocal;
import loyola.entitys.GroupEntity;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
@Named(value = "rankingMB")
@ViewScoped
@ManagedBean(eager=true)
public class RankingMB implements Serializable {

    @EJB
    private UserBeanLocal userBeanLocal;
    private List usersList; 
    private int colorIndex = 0;
    private float currentPoints = 0;
    List<GroupEntity> groupEntitys;
    private String[] cssColors = {
        "#fff9c4",
        "#fff59d",
        "#fff176",
        "#ffee58",
        "#ffeb3b",
        "#fdd835",
        "#fbc02d",
        "#f9a825",
        "#ffecb3",
        "#ffe082",
        "#ffd54f",
        "#ffca28",
        "#ffc107",
        "#ffb300",
        "#ffa000",
        "#ff8f00",
        "#ef6c00",
        "#f57c00",
        "#fb8c00",
        "#ff9800",
        "#ffa726",
        "#ffb74d",
        "#ffcc80",
        "#ffe0b2",
        "#ffccbc",
        "#ffab91",
        "#ff8a65",
        "#ff7043",
        "#ff5722",
        "#f4511e",
        "#e64a19",
        "#d84315",
        "#00acc1",
        "#0097a7"
    };
    
    @PostConstruct
    public void init() {
        setUsersList(userBeanLocal.getRankUserList());
        groupEntitys = new ArrayList<>();
        
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_4TO_A, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_4TO_B, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_5TO_A, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_5TO_B, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_6TO_A, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_6TO_B, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_7TO_A, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_7TO_B, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_COMP_3A, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_COMP_3B, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_COMP_3C, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_COMP_3D, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_DANCE, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_10, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_3, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_4, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_5, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_6, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_7, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_8, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_ENGLISH_9, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_GUITAR, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_PLASTIC, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_TEATHER, 0,0));
        groupEntitys.add(new GroupEntity(GroupEntity.GROUP_FLAUTA, 0,0));
    }
    public RankingMB() {
    }

    public List getUsersList() {
        setUsersList(userBeanLocal.getRankUserList());
        return usersList;
    }
    
    public List getUsersOnlineList() {
       return UsersOnline.getOnlineUsers();
    }
    
    public List getFirstUsersList() {
        setUsersList(getUsersList());
        if(usersList!=null&&usersList.size()>3){
            return usersList.subList(0, 3);
        }
        return usersList;
    }
    
//    public List<GroupEntity> getGroupsList() {
//        
//        for (int i = 0; i < 24; i++) {
//            groupEntitys.get(i).setPoints(userBeanLocal.getTotalGroupPoints(groupEntitys.get(i).getGroupName()));
//        }
//        groupEntitys.sort(new Comparator<GroupEntity>() {
//
//            @Override
//            public int compare(GroupEntity t, GroupEntity t1) {
//                if(t.getPoints()==t1.getPoints())
//                    return 1;
//                if(t.getPoints()>t1.getPoints())
//                    return -1;
//                return 1;
//                           
//            }
//        });
//        return groupEntitys;
//    }
    public List<GroupEntity> getFirstGroupsList() {
        
        for (int i = 0; i < 24; i++) {
            groupEntitys.get(i).setPoints((int)userBeanLocal.getTotalGroupPoints(groupEntitys.get(i).getGroupName()));
        }
        groupEntitys.sort(new Comparator<GroupEntity>() {

            @Override
            public int compare(GroupEntity t, GroupEntity t1) {
                if(t.getPoints()==t1.getPoints())
                    return 1;
                if(t.getPoints()>t1.getPoints())
                    return -1;
                return 1;
                           
            }
        });
        return groupEntitys.subList(0,4);
    }
    
    public int getGroupPos(GroupEntity g){
        return groupEntitys.indexOf(g)+1;
    }

    public void setUsersList(List usersList) {
        this.usersList = usersList;
    }
    
    public int puesto(UserLoyolaEntity userLoyolaEntity){
        return usersList.indexOf(userLoyolaEntity)+1;
    }
    
    public int champRank(UserLoyolaEntity userLoyolaEntity){
        return UsersOnline.getOnlineUsers().indexOf(userLoyolaEntity)+1;
    }
    
    public String CSSColor(UserLoyolaEntity userLoyolaEntity){
        String out = "";
        if(currentPoints == userLoyolaEntity.getPoints()){
            return cssColors[colorIndex];
        }
        else{
            colorIndex++;
            if (colorIndex == cssColors.length || usersList.indexOf(userLoyolaEntity) == 0) {
                colorIndex = 0;
            }
            out = cssColors[colorIndex];
        }
        currentPoints = userLoyolaEntity.getPoints();
        return out;
    }
    
    
}
