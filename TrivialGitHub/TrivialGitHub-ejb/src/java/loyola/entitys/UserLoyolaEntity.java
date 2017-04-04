/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hipnosapo
 */
@Entity
public class UserLoyolaEntity implements Serializable {
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_INVITED = "ROLE_INVITED";
    
    public static final String ENTITY_NAME = "UserLoyolaEntity";
    public static final String FULLNAME_COL_NAME = "fullName";
    public static final String USERNAME_COL_NAME = "userName";
    public static final String PASS_COL_NAME = "pass";
    public static final String ROLE_COL_NAME = "roleName";
    public static final String POINTS_COL_NAME = "points";
    public static final String QUESTIONS_COL_NAME = "answeredQuestions";
    public static final String GROUP_COL_NAME = "userGroup";
    
    @NotNull
    private String fullName;
    @NotNull
    private String userName;
    @NotNull
    private String pass;
    @NotNull
    private String roleName;
    private String userGroup;
    private int userTry;
    private int userRigth;
    private float points;
    private int ave;
    @Lob
    private byte[] icon;
    
    @OneToMany
    private List<QuestionEntity> answeredQuestions;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLoyolaEntity(){}
    
    public UserLoyolaEntity(
            String fullName,
            String userName,
            String pass,
            String roleName,
            String group,
            int userTry,
            float points){
        this.fullName=fullName;
        this.userName=userName;
        this.pass=pass;
        this.roleName=roleName;
        this.userTry=userTry;
        this.points=points;
        this.userGroup = group;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getUserTry() {
        return userTry;
    }

    public void setUserTry(int userTry) {
        this.userTry = userTry;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public int getUserRigth() {
        return userRigth;
    }

    public void setUserRigth(int userRigth) {
        this.userRigth = userRigth;
    }

    public int getAve() {
        return ave;
    }

    public void setAve(int ave) {
        this.ave = ave;
    }

    public String getGroup() {
        return userGroup;
    }

    public void setGroup(String group) {
        this.userGroup = group;
    }
    
    public List<QuestionEntity> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<QuestionEntity> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public int calcAve(){
        return userRigth/userTry*1000;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLoyolaEntity)) {
            return false;
        }
        UserLoyolaEntity other = (UserLoyolaEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loyola.entitys.UserLoyolaEntity[ id=" + id + " ]";
    }
    
}
