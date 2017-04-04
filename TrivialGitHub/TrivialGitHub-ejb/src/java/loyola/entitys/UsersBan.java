/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.entitys;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author hipnosapo
 */
@Entity
public class UsersBan implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String ENTITY_NAME = "UsersBan";
    public static String COL_USER_NAME = "nameBan";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String userBan;
    private String nameBan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserBan() {
        return userBan;
    }

    public void setUserBan(String userBan) {
        this.userBan = userBan;
    }

    public String getNameBan() {
        return nameBan;
    }

    public void setNameBan(String nameBan) {
        this.nameBan = nameBan;
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
        if (!(object instanceof UsersBan)) {
            return false;
        }
        UsersBan other = (UsersBan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loyola.entitys.UsersBan[ id=" + id + " ]";
    }
    
}
