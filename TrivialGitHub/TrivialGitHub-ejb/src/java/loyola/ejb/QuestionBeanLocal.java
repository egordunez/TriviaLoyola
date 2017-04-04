/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.ejb;

import java.util.List;
import javax.ejb.Local;
import loyola.entitys.QuestionEntity;

/**
 *
 * @author hipnosapo
 */
@Local
public interface QuestionBeanLocal {

    void insertQuestion(QuestionEntity parameter);

    List getAllQuestions();

    void deleteQuestion(QuestionEntity question);

    void updateQuestion(QuestionEntity question);

    List getQuestionsByCategory(long id,String category);

    List getChampQuestions();
    
}
