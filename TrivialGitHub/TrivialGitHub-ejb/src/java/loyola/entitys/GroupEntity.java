/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.entitys;

/**
 *
 * @author hipnosapo
 */
public class GroupEntity {
    
    
    public static final String GROUP_4TO_A = "4to A";
    public static final String GROUP_5TO_A = "5to A";
    public static final String GROUP_6TO_A = "6to A";
    public static final String GROUP_7TO_A = "7mo A";
    public static final String GROUP_4TO_B = "4to B";
    public static final String GROUP_5TO_B = "5to B";
    public static final String GROUP_6TO_B = "6to B";
    public static final String GROUP_7TO_B = "7mo B";
    public static final String GROUP_ENGLISH_3 = "Ingles 3ro";
    public static final String GROUP_ENGLISH_4 = "Ingles 4to";
    public static final String GROUP_ENGLISH_5 = "Ingles 5to";
    public static final String GROUP_ENGLISH_6 = "Ingles 6to";
    public static final String GROUP_ENGLISH_7 = "Ingles 7mo";
    public static final String GROUP_ENGLISH_8 = "Ingles 8vo";
    public static final String GROUP_ENGLISH_9 = "Ingles 9no";
    public static final String GROUP_ENGLISH_10 = "Ingles 10mo";
    public static final String GROUP_COMP_3A = "Computacion 3A";
    public static final String GROUP_COMP_3B = "Computacion 3B";
    public static final String GROUP_COMP_3C = "Computacion 3C";
    public static final String GROUP_COMP_3D = "Computacion 3D";
    public static final String GROUP_GUITAR = "Guitarra";
    public static final String GROUP_TEATHER = "Teatro";
    public static final String GROUP_DANCE = "Danza";
    public static final String GROUP_PLASTIC = "Artes Plasticas";
    public static final String GROUP_FLAUTA = "Flauta";
    
    private String groupName;
    private double points;
    private int pos;

    public GroupEntity(String groupName, double points, int pos) {
        this.groupName = groupName;
        this.points = points;
        this.pos = pos;
    }

    
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
    
}
