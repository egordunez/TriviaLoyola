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
public class QuestionEntity implements Serializable {
    
    public static String CAT_CIENCIA="Ciencias";
    public static String CAT_HISTORIA="Historia";
    public static String CAT_MATES="Matemáticas";
    public static String CAT_LENGUAS="Lenguas";
    public static String CAT_DEPORTE = "Deportes";
    public static String CAT_ARTE = "Arte";
    public static String CAT_GEO = "Geografía";
    public static String CAT_SPIRI = "Espiritualidad";
    
    public static String CAT_CIENCIA_KEY ="CAT_CIENCIA_KEY";
    public static String CAT_HISTORIA_KEY ="CAT_HISTORIA_KEY";
    public static String CAT_MATES_KEY ="CAT_MATES_KEY";
    public static String CAT_LENGUAS_KEY ="CAT_LENGUAS_KEY";
    public static String CAT_DEPORTE_KEY = "CAT_DEPORTE_KEY";
    public static String CAT_ARTE_KEY ="CAT_ARTE_KEY";
    public static String CAT_GEO_KEY = "CAT_GEO_KEY";
    public static String CAT_SPIRI_KEY = "CAT_SPIRI_KEY";
    public static String CAT_CHAMP_KEY = "CAT_CHAMP_KEY";
    
    public static final String ENTITY_NAME = "QuestionEntity";
    public static final String COL_QUESTION_NAME = "question";
    public static final String COL_CATEGORY_NAME = "category";
    public static final String COL_ID_NAME = "id";
    
    private String category;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private int rightAnswer;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getQuestion() {
        return question;
    }
    
    public String getQuestionSummary() {
        if(question.length()>28)
            return question.substring(0,25)+"...";
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof QuestionEntity)) {
            return false;
        }
        QuestionEntity other = (QuestionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loyola.entitys.QuestionEntity[ id=" + id + " ]";
    }
    
    public static String getCategoryName(String catkey) {
        if (catkey.equals(CAT_CIENCIA_KEY)) {
            return CAT_CIENCIA;
        } else if (catkey.equals(CAT_ARTE_KEY)) {
            return CAT_ARTE;
        } else if (catkey.equals(CAT_DEPORTE_KEY)) {
            return CAT_DEPORTE;
        } else if (catkey.equals(CAT_GEO_KEY)) {
            return CAT_GEO;
        } else if (catkey.equals(CAT_HISTORIA_KEY)) {
            return CAT_HISTORIA;
        } else if (catkey.equals(CAT_LENGUAS_KEY)) {
            return CAT_LENGUAS;
        } else if (catkey.equals(CAT_MATES_KEY)) {
            return CAT_MATES;
        }else if (catkey.equals(CAT_SPIRI_KEY)) {
            return CAT_SPIRI;
        }
        else return null;
    }
    
    public static String getCategoryKey(String cat) {
        if (cat.equals(CAT_CIENCIA)) {
            return CAT_CIENCIA_KEY;
        } else if (cat.equals(CAT_ARTE)) {
            return CAT_ARTE_KEY;
        } else if (cat.equals(CAT_DEPORTE)) {
            return CAT_DEPORTE_KEY;
        } else if (cat.equals(CAT_GEO)) {
            return CAT_GEO_KEY;
        } else if (cat.equals(CAT_HISTORIA)) {
            return CAT_HISTORIA_KEY;
        } else if (cat.equals(CAT_LENGUAS)) {
            return CAT_LENGUAS_KEY;
        } else if (cat.equals(CAT_MATES)) {
            return CAT_MATES_KEY;
        }else if (cat.equals(CAT_SPIRI)) {
            return CAT_SPIRI_KEY;
        }
        else return null;
    }
    
    public static String getCategoryKey(int cat) {
        if (cat==1) {
            return CAT_CIENCIA_KEY;
        } else if (cat==2) {
            return CAT_HISTORIA_KEY;
        } else if (cat==3) {
            return CAT_MATES_KEY;
        } else if (cat==4) {
            return CAT_LENGUAS_KEY;
        } else if (cat==5) {
            return CAT_DEPORTE_KEY;
        } else if (cat==6) {
            return CAT_ARTE_KEY;
        } else if (cat==7) {
            return CAT_GEO_KEY;   
        }else if (cat==0) {
            return CAT_SPIRI_KEY;
        }
        else return null;
    }
    
}
