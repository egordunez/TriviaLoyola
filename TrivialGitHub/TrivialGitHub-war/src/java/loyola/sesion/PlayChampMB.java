/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.sesion;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import loyola.ejb.QuestionBeanLocal;
import loyola.entitys.QuestionEntity;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
@Named(value = "playChampMB")
@SessionScoped
public class PlayChampMB implements Serializable {
    
    private String [] congratulations = {
        "Ya eres un tilín mas culto",
        "Sigue así, vas muy bien",
        "Vamos, a por la siguiente",
        "No pares, sigue, sigue",
        "Ya estas más cerca del primer puesto",
        "Has aprendido algo hoy"
    };
    private List questions;
    private int points;
    private QuestionEntity questionSelected;
    
    @EJB
    private QuestionBeanLocal questionBeanLocal;
    private SesionAdminMB sesionAdminMB;
    
    private Random random;

    private boolean disableAnswer1;
    private boolean disableAnswer2;
    private boolean disableAnswer3;
    
    private boolean wrongAnswerVisivility;
    private boolean rightAnswerVisivility;
    
    @PostConstruct
    public void init() {
        if (sesionAdminMB == null) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            sesionAdminMB = (SesionAdminMB) FacesContext.getCurrentInstance().
                    getApplication().getELResolver().getValue(elContext, null, "sesionAdminMB");
        } 
        if(random==null){
            random = new Random();
        }
    }
    public PlayChampMB() {
    }
    
    
    public void rigthAnswer(){
        points+=10;
        setPoints();
    }
    public void wrongAnswer(){
        if(!(points<=5))
            points-=5;
        setPoints();
    }

    public List getQuestions() {
        if(questions!=null) {
            return questions;
        } else {
            setQuestions(questionBeanLocal.getChampQuestions());
            return questions;
        }   
    }

    public void setQuestions(List questions) {
        this.questions = questions;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public QuestionEntity getQuestionSelected() {
        return questionSelected;
    }

    public void setQuestionSelected(QuestionEntity questionSelected) {
        this.questionSelected = questionSelected;
    }

    public boolean isDisableAnswer1() {
        return disableAnswer1;
    }

    public void setDisableAnswer1(boolean disableAnswer1) {
        this.disableAnswer1 = disableAnswer1;
    }

    public boolean isDisableAnswer2() {
        return disableAnswer2;
    }

    public void setDisableAnswer2(boolean disableAnswer2) {
        this.disableAnswer2 = disableAnswer2;
    }

    public boolean isDisableAnswer3() {
        return disableAnswer3;
    }

    public void setDisableAnswer3(boolean disableAnswer3) {
        this.disableAnswer3 = disableAnswer3;
    }

    public boolean isWrongAnswerVisivility() {
        return wrongAnswerVisivility;
    }

    public void setWrongAnswerVisivility(boolean wrongAnswerVisivility) {
        this.wrongAnswerVisivility = wrongAnswerVisivility;
    }

    public boolean isRightAnswerVisivility() {
        return rightAnswerVisivility;
    }

    public void setRightAnswerVisivility(boolean rightAnswerVisivility) {
        this.rightAnswerVisivility = rightAnswerVisivility;
    }
    
    public void restarQuestion() {
        disableAnswer1 = false;
        disableAnswer2 = false;
        disableAnswer3 = false;

        wrongAnswerVisivility = false;
        rightAnswerVisivility = false;
        questionSelected = null;
    }
    
    public void playQuestionChamp(QuestionEntity questionSelected) throws IOException{
        setQuestionSelected(questionSelected);
        questions.remove(questionSelected);
        sesionAdminMB.playQuestionChamp();
    }
    
    public int questionNumber(QuestionEntity question){
        return questions.indexOf(question)+1;
    }
    
    public void checkAnswer(int selected) throws IOException{
        //rigth
        if(selected == questionSelected.getRightAnswer()){
            rigthAnswer();
            setRightAnswerVisivility(true);
        }//wrong
        else{
            wrongAnswer();
            disableAnswer(selected);
            setWrongAnswerVisivility(true);
        }
    }
    
    private void disableAnswer(int number){
        if(number==1){
            setDisableAnswer1(true);
        }
        if(number==2){
            setDisableAnswer2(true);
        }
        if(number==3){
            setDisableAnswer3(true);
        }
    }
    
    public void closeWrongAnswerDialog(){
        setWrongAnswerVisivility(false);
    }
    
    public void closeRightAnswerDialog() throws IOException{
        setRightAnswerVisivility(false);
        restarQuestion();
        sesionAdminMB.playingChamp();
    }
    
    public String getCongratulation(){
        return congratulations[(int)(random.nextDouble()*6)];
    }
    
    public void goPlayingChamp() throws IOException{
        sesionAdminMB.playingChamp();
    }
    
    private void setPoints(){
        ArrayList<UserLoyolaEntity> list = UsersOnline.getOnlineUsers();
        for (int i = 0; i < list.size(); i++) {
            if(sesionAdminMB.getcurrentUser().getId()==list.get(i).getId()){
                list.get(i).setPoints(points);
            }
        }
        UsersOnline.sort();
    }
    
    public void goBackplayingChamp() throws IOException{
        restarQuestion();
        sesionAdminMB.playingChamp();
    }
}
