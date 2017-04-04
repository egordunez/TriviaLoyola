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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import loyola.ejb.UserBeanLocal;
import loyola.entitys.QuestionEntity;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
@Named(value = "playQuestionMB")
@SessionScoped
@ManagedBean(eager=true)
public class PlayQuestionMB implements Serializable {
    
    private String [] congratulations = {
        "Ya eres un tilín mas culto",
        "Sigue así, vas muy bien",
        "Vamos, a por la siguiente",
        "No pares, sigue, sigue",
        "Ya estas más cerca del primer puesto",
        "Has aprendido algo hoy"
    };
    private Random random;

    private boolean disableAnswer1;
    private boolean disableAnswer2;
    private boolean disableAnswer3;
    
    private boolean wrongAnswerVisivility;
    private boolean rightAnswerVisivility;
    private QuestionEntity questionSelected;
    private float pointsObtained;
    
    @EJB
    private UserBeanLocal userBeanLocal;
    private SesionAdminMB sesionAdminMB;
    
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
        pointsObtained = 0;
    }
    
    public void restarQuestion() {
        disableAnswer1 = false;
        disableAnswer2 = false;
        disableAnswer3 = false;

        wrongAnswerVisivility = false;
        rightAnswerVisivility = false;
        questionSelected = null;
        
        pointsObtained = 0;
    }
    
    
    public PlayQuestionMB() {
    }
    
    public boolean isWrongAnswerVisivility() {
        return wrongAnswerVisivility;
    }

    public void setWrongAnswerVisivility(boolean wrongAnswerVisivility) {
        this.wrongAnswerVisivility = wrongAnswerVisivility;
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
    
    public boolean isRightAnswerVisivility() {
        return rightAnswerVisivility;
    }

    public void setRightAnswerVisivility(boolean rightAnswerVisivility) {
        this.rightAnswerVisivility = rightAnswerVisivility;
    }
    
    public QuestionEntity getQuestionSelected() {
        return questionSelected;
    }

    public void setQuestionSelected(QuestionEntity questionSelected) {
        this.questionSelected = questionSelected;
    }

    public float getPointsObtained() {
        return pointsObtained;
    }

    public void setPointsObtained(float pointsObtained) {
        this.pointsObtained = pointsObtained;
    }
    
    public void checkAnswer(int selected,HttpServletRequest httpRequest) throws IOException{
        UserLoyolaEntity userLoyolaEntity = new UserLoyolaEntity();
        userLoyolaEntity.setId(sesionAdminMB.getUserLoyolaEntity().getId());
        
        //rigth
        if(selected == questionSelected.getRightAnswer()){
            pointsObtained(true);
            userLoyolaEntity.setUserRigth(1);
            userLoyolaEntity.setPoints(pointsObtained);
            ArrayList<QuestionEntity> questionEntitys = new ArrayList<>();
            questionEntitys.add(questionSelected);
            userLoyolaEntity.setAnsweredQuestions(questionEntitys);
            setRightAnswerVisivility(true);
        }//wrong
        else{
            userLoyolaEntity.setPoints(0);
            userLoyolaEntity.setUserRigth(0);
            disableAnswer(selected);
            setWrongAnswerVisivility(true);
            sesionAdminMB.setWrongAnswerCounter(httpRequest);
        }
        userBeanLocal.playUpdate(userLoyolaEntity);
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
        sesionAdminMB.backToQuestionsByCategory();
    }
    
    public String getCongratulation(){
        return congratulations[(int)(random.nextDouble()*6)];
    }
    
    public String pointsObtained(boolean right){
        UserLoyolaEntity user = sesionAdminMB.getUpdatedLogedUser();
        double t = 0;
        double r = 0;
        double points = 0;
        int divisor;
        t = user.getUserTry() + 1;
        if(right){
            r = user.getUserRigth() + 1;
        }
        divisor = (int) (t - r + 1);
        points = r * 2 / divisor;
        DecimalFormat df = new DecimalFormat("###.##");
        pointsObtained = Float.parseFloat(df.format(points));
        return df.format(points);
    }  
    
    
}
