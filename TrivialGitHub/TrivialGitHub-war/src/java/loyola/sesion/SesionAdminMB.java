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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import loyola.ejb.UserBanBean;
import loyola.ejb.UserBanBeanLocal;
import loyola.ejb.UserBeanLocal;
import loyola.entitys.QuestionEntity;
import loyola.entitys.UserLoyolaEntity;
import loyola.entitys.UsersBan;
import loyola.security.TriviaFilter;

/**
 *
 * @author hipnosapo
 */
@Named(value = "sesionAdminMB")
@SessionScoped
public class SesionAdminMB implements Serializable {

    private String errorMessage;
    private String user;
    private String pass;
    private String role = "";
    private String fullName = "";
    private String currentCategory = "";
    private boolean autenticatedUser = false;
    private boolean autenticatedAdmin = false;
    private int wrongAnswerCounter;
    private UserLoyolaEntity userLoyolaEntity = null;
    
    @EJB
    private UserBeanLocal userBeanLocal;
    @EJB
    private UserBanBeanLocal userBanBeanLocal;
    
    @PostConstruct
    public void init() {
        wrongAnswerCounter = 0;
    }
    
    @PreDestroy
    private void destroy(){
        UsersOnline.removeOnlineUser(userLoyolaEntity);
    }
    
    public SesionAdminMB() {
    }

    public boolean isAutenticatedUser() {
        return autenticatedUser;
    }

    public void setAutenticatedUser(boolean autenticatedUser) {
        this.autenticatedUser = autenticatedUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isAutenticatedAdmin() {
        return autenticatedAdmin;
    }

    public void setAutenticatedAdmin(HttpServletRequest httpRequest,boolean autenticatedAdmin) {
        this.autenticatedAdmin = autenticatedAdmin;
        httpRequest.getSession().setAttribute(TriviaFilter.IS_LOGED_ADMIN_KEY, autenticatedUser);
    }

    public UserLoyolaEntity getUserLoyolaEntity() {
        return userLoyolaEntity;
    }

    public void setUserLoyolaEntity(UserLoyolaEntity userLoyolaEntity) {
        this.userLoyolaEntity = userLoyolaEntity;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public int getWrongAnswerCounter() {
        return wrongAnswerCounter;
    }

    public void setWrongAnswerCounter(HttpServletRequest httpRequest) throws IOException {
        this.wrongAnswerCounter = wrongAnswerCounter+1;
        if(wrongAnswerCounter==5){
            UsersBan ub = new UsersBan();
            ub.setNameBan(userLoyolaEntity.getFullName());
            ub.setUserBan(userLoyolaEntity.getUserName());
            userBanBeanLocal.banUser(ub);
            logout(httpRequest);
        }
    }
    
    

    public void autenticateUser(HttpServletRequest httpRequest) throws IOException{
        if (user != null && pass != null) {
            userLoyolaEntity = userBeanLocal.getLogedUser(user, pass);
            if(userBanBeanLocal.isBannedUser(user)){
                setErrorMessage("Baneado, consulte a su profesor");
            }
            else if (userLoyolaEntity != null&&
                    userLoyolaEntity.getPass().equals(pass)&&
                    userLoyolaEntity.getUserName().equals(user)) {
                setAutenticatedUser(true);
                httpRequest.getSession().setAttribute(TriviaFilter.IS_LOGED_USER_KEY, autenticatedUser);
                UsersOnline.setOnlineUser(userLoyolaEntity);
                role = userLoyolaEntity.getRoleName();
                fullName = userLoyolaEntity.getFullName();
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + TriviaFilter.INDEX_PATH);
                setErrorMessage("");
                if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
                    setAutenticatedAdmin(httpRequest,true);
                }
            } 
            else {
                setErrorMessage("Usuario o clave incorrectos");
            }
        }       
    }
    
    public void logout(HttpServletRequest httpRequest) throws IOException{
        UsersOnline.removeOnlineUser(userLoyolaEntity);
        autenticatedUser = false;
        httpRequest.getSession().invalidate();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.LOGIN_PATH);
    }
    
    
    public void play() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAY_PATH);
    }
    
    public void playChampionship() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAY_CHAMP_PATH);
    }
    
    public void playCategory(int cat) throws IOException {
        currentCategory = QuestionEntity.getCategoryKey(cat);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAY_CATEGORY_PATH);
    }
    
    public void backToQuestionsByCategory() throws IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAY_CATEGORY_PATH);
    }
    
    public void ranking(HttpServletRequest httpRequest) throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.RANKING_PATH);
    }
    
    public void playQuestionChamp() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAY_QUESTION_CHAMP_PATH);
    }
    
    public void playingChamp() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.PLAYING_CHAMP_PATH);
    }
    
    public void settings(HttpServletRequest httpRequest) throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.SETTINGS_PATH);
        }
    }
    
    public void adminUsers() throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.ADMIN_USERS_PATH);
        }
    }
    
    public void bannedUsers() throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.BANNED_USERS_PATH);
        }
    }
    
    public void adminQuestions() throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.ADMIN_QUESTIONS_PATH);
        }
    }
    
    public void spectator() throws IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + TriviaFilter.CHAMP_ESPECT_PATH);
    }
    
    public void adminChampionship() throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.CHAMPIONSHIP_PATH);
        }
    }
    
    public void loadFromExel() throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.LOAD_FROM_XLS_PATH);
        }
    }
    
    public void loadUserFromExel() throws IOException{
        if(role.equals(UserLoyolaEntity.ROLE_ADMIN)){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + TriviaFilter.LOAD_USER_FROM_XLS_PATH);
        }
    }
    
    public UserLoyolaEntity getUpdatedLogedUser(){
        userLoyolaEntity = userBeanLocal.getLogedUser(user, pass);
        return userLoyolaEntity;
    }
    public UserLoyolaEntity getcurrentUser(){
        return userLoyolaEntity;
    }
}
