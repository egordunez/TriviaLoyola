/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.sesion;

import java.util.ArrayList;
import java.util.Comparator;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
public class UsersOnline {
    
    public static ArrayList<UserLoyolaEntity> onlineUsers = new ArrayList();
    public static boolean inUse = false;

    public static ArrayList getOnlineUsers() {
        return onlineUsers;
    }

    public static void setOnlineUser(UserLoyolaEntity onlineUser) {
        while (inUse) {            
            System.err.println("En uso");
        }
        inUse = true;
        onlineUser.setPoints(0);
        for (int i = 0; i < onlineUsers.size(); i++) {
            if(onlineUser.getId()==onlineUsers.get(i).getId()){
                onlineUsers.remove(onlineUser);
                break;
            }
        }
        UsersOnline.onlineUsers.add(onlineUser);
        inUse = false;
    }
    
    public static void sort(){
        onlineUsers.sort(new Comparator<UserLoyolaEntity>() {
            @Override
            public int compare(UserLoyolaEntity t, UserLoyolaEntity t1) {
                if(t.getPoints()==t1.getPoints())
                    return 1;
                if(t.getPoints()>t1.getPoints())
                    return -1;
                return 1;
            }
        });
    }
    
    public static void removeOnlineUser(UserLoyolaEntity onlineUser) {
        while (inUse) {            
            System.err.println("En uso");
        }
        inUse = true;
        UsersOnline.onlineUsers.remove(onlineUser);
        inUse = false;
    }

    public static boolean isInUse() {
        while (inUse) {            
            System.err.println("En uso");
        }
        return inUse;
    }    
}
