/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.sesion;

import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import loyola.ejb.QuestionBeanLocal;
import loyola.entitys.QuestionEntity;
import loyola.security.TriviaFilter;

/**
 *
 * @author hipnosapo
 */
@Named(value = "questionsByCategoryMB")
@ViewScoped
@ManagedBean(eager=true)
public class QuestionsByCategoryMB implements Serializable {
    

    private String pageTitle;
    private String categoryKey;
    private List questions;
    private List solvedQuestions;
    
    @EJB
    private QuestionBeanLocal questionBeanLocal;
    
    private PlayQuestionMB playQuestionMB;
    private SesionAdminMB sesionAdminMB;
    
    @PostConstruct
    public void init() {
        if (playQuestionMB == null) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            playQuestionMB = (PlayQuestionMB) FacesContext.getCurrentInstance().
                    getApplication().getELResolver().getValue(elContext, null, "playQuestionMB");
        }
        if (sesionAdminMB == null) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            sesionAdminMB = (SesionAdminMB) FacesContext.getCurrentInstance().
                    getApplication().getELResolver().getValue(elContext, null, "sesionAdminMB");
        }  
        //setQuestions(questionBeanLocal.getQuestionsByCategory(sesionAdminMB.getUpdatedLogedUser().getId(),categoryKey));
    }
    
    public QuestionsByCategoryMB() {
    }

    public String getPageTitle() {
        if(sesionAdminMB!=null){
            categoryKey = sesionAdminMB.getCurrentCategory();
            setPageTitle(QuestionEntity.getCategoryName(categoryKey));
        }
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List getQuestions() {
        categoryKey = sesionAdminMB.getCurrentCategory();
        setQuestions(questionBeanLocal.getQuestionsByCategory(sesionAdminMB.getUpdatedLogedUser().getId(),categoryKey));
        return questions;
    }

    public void setQuestions(List questions) {
        this.questions = questions;
    }

    public String getCategory() {
        setCategory(sesionAdminMB.getCurrentCategory());
        return categoryKey;
    }

    public void setCategory(String category) {
        this.categoryKey = category;
    }
    
    public void playQuestion(QuestionEntity questionSelected) throws IOException{
        playQuestionMB.restarQuestion();
        playQuestionMB.setQuestionSelected(questionSelected);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAY_QUESTION_PATH);
    }
    
    public int questionNumber(QuestionEntity questionEntity){
        return questions.indexOf(questionEntity)+1;
    }
    
//    public boolean isAvailableQuestion(int id){
//        solvedQuestions = sesionAdminMB.getUpdatedLogedUser().getAnsweredQuestions();
//        for (Object solvedQuestion : solvedQuestions) {
//            if(((QuestionEntity)solvedQuestion).getId()==id){
//                return false;
//            }
//        }
//        return true;
//    }
       
}
