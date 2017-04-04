/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import loyola.entitys.QuestionEntity;
import loyola.entitys.UserLoyolaEntity;

/**
 *
 * @author hipnosapo
 */
@Stateless
public class QuestionBean implements QuestionBeanLocal {
    
    @EJB
    private UserBeanLocal userBeanLocal;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertQuestion(QuestionEntity question) {
        entityManager.persist(question);
    }

    @Override
    public List getAllQuestions() {
        return entityManager.createQuery("select i FROM " + QuestionEntity.ENTITY_NAME +
                " i ORDER BY i."+ QuestionEntity.COL_QUESTION_NAME).getResultList();
    }
    
    

    @Override
    public void deleteQuestion(QuestionEntity question) {
        List users = userBeanLocal.getAllUsers();
        UserLoyolaEntity user = null;
        UserLoyolaEntity obtained = null;
        for (int i = 0; i < users.size(); i++) {
            user = (UserLoyolaEntity)users.get(i);
            //obtained = entityManager.find(UserLoyolaEntity.class, user.getId());
            //obtained.getAnsweredQuestions().remove(question);
            user.getAnsweredQuestions().remove(question);
        }
        Object toBeRemoved = entityManager.merge(question);
        entityManager.remove(toBeRemoved);
    }  

    @Override
    public void updateQuestion(QuestionEntity question) {
        QuestionEntity obtained = entityManager.find(QuestionEntity.class, question.getId());
        obtained.setAnswer1(question.getAnswer1());
        obtained.setAnswer2(question.getAnswer2());
        obtained.setAnswer3(question.getAnswer3());
        obtained.setCategory(question.getCategory());
        obtained.setQuestion(question.getQuestion());
        obtained.setRightAnswer(question.getRightAnswer());
    }

    @Override
    public List getQuestionsByCategory(long id,String category) {
        UserLoyolaEntity user = (UserLoyolaEntity) entityManager.createQuery("select u FROM " + UserLoyolaEntity.ENTITY_NAME + " u WHERE "+
                "u.id=" + id).getResultList().get(0);
        
        List questions = entityManager.createQuery("select q FROM " + QuestionEntity.ENTITY_NAME + " q WHERE "+
                "q." + QuestionEntity.COL_CATEGORY_NAME + "='" + category + "'").getResultList();
        for (int i = 0; i < user.getAnsweredQuestions().size(); i++) {
            questions.remove(user.getAnsweredQuestions().get(i));
        }
        return questions;
    }

    @Override
    public List getChampQuestions() {
        return entityManager.createQuery("select i FROM " + QuestionEntity.ENTITY_NAME + " i"+
                " WHERE i." + QuestionEntity.COL_CATEGORY_NAME + "='" + QuestionEntity.CAT_CHAMP_KEY + "'"+
                " ORDER BY i."+ QuestionEntity.COL_QUESTION_NAME).getResultList();
    }
    
}
