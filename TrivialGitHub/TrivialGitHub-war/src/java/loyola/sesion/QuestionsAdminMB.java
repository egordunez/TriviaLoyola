/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.sesion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import loyola.ejb.QuestionBeanLocal;
import loyola.entitys.QuestionEntity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author hipnosapo
 */
@Named(value = "questionsAdminMB")
@ViewScoped
@ManagedBean(eager=true)
public class QuestionsAdminMB implements Serializable {

    private boolean deleteDialogVisivility;
    private boolean deleteQuestionChampVisivility;
    private boolean addDialogVisivility;
    private boolean addQuestionChampVisivility;
    private boolean isInsert;
    private QuestionEntity questionSelected;
    private List questionsList;
    private String rigthQuestion;
    private List<String> posiblesRigthQuestion = new ArrayList<String>();
    private String selectedCategory;
    private List<String> categories = new ArrayList<String>();
    private UploadedFile excelUploadedFile;
    private File excelFile;
    
    private SesionAdminMB sesionAdminMB;
    
    @PostConstruct
    public void init() {
        if (sesionAdminMB == null) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            sesionAdminMB = (SesionAdminMB) FacesContext.getCurrentInstance().
                    getApplication().getELResolver().getValue(elContext, null, "sesionAdminMB");
        } 
        posiblesRigthQuestion.add("Primera");
        posiblesRigthQuestion.add("Segunda");
        posiblesRigthQuestion.add("Tercera");
        
        categories.add(QuestionEntity.CAT_CIENCIA);
        categories.add(QuestionEntity.CAT_ARTE);
        categories.add(QuestionEntity.CAT_DEPORTE);
        categories.add(QuestionEntity.CAT_GEO);
        categories.add(QuestionEntity.CAT_HISTORIA);
        categories.add(QuestionEntity.CAT_LENGUAS);
        categories.add(QuestionEntity.CAT_MATES);
        categories.add(QuestionEntity.CAT_SPIRI);
        
        excelFile = new File("excelFile");
    }
    
    @EJB
    private QuestionBeanLocal questionBeanLocal;
    
    public QuestionsAdminMB() {
    }

    public boolean isDeleteDialogVisivility() {
        return deleteDialogVisivility;
    }

    public void setDeleteDialogVisivility(boolean deleteDialogVisivility) {
        this.deleteDialogVisivility = deleteDialogVisivility;
    }

    public QuestionEntity getQuestionSelected() {
        return questionSelected;
    }

    public void setQuestionSelected(QuestionEntity questionSelected) {
        this.questionSelected = questionSelected;
    }

    public List getQuestionsList() {
        if(selectedCategory==null){
            selectedCategory = categories.get(0);
        }
        setQuestionsList(
                questionBeanLocal.getQuestionsByCategory(
                sesionAdminMB.getUserLoyolaEntity().getId(),
                QuestionEntity.getCategoryKey(selectedCategory)));  
        return questionsList;
    }
    
    public List getQuestionsChampList() {
        setQuestionsList(questionBeanLocal.getChampQuestions());
        return questionsList;
    }

    public void setQuestionsList(List questionsList) {
        this.questionsList = questionsList;
    }

    public boolean isAddDialogVisivility() {
        return addDialogVisivility;
    }

    public void setAddDialogVisivility(boolean addDialogVisivility) {
        this.addDialogVisivility = addDialogVisivility;
    }

    public boolean isIsInsert() {
        return isInsert;
    }

    public void setIsInsert(boolean isInsert) {
        this.isInsert = isInsert;
    }

    public String getRigthQuestion() {
        return rigthQuestion;
    }

    public void setRigthQuestion(String rigthQuestion) {
        this.rigthQuestion = rigthQuestion;
    }

    public List<String> getPosiblesRigthQuestion() {
        return posiblesRigthQuestion;
    }

    public void setPosiblesRigthQuestion(List<String> posiblesRigthQuestion) {
        this.posiblesRigthQuestion = posiblesRigthQuestion;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public UploadedFile getExcelUploadedFile() {
        return excelUploadedFile;
    }

    public void setExcelUploadedFile(UploadedFile excelUploadedFile) {
        this.excelUploadedFile = excelUploadedFile;
    }

    public boolean isAddQuestionChampVisivility() {
        return addQuestionChampVisivility;
    }

    public void setAddQuestionChampVisivility(boolean addQuestionChampVisivility) {
        this.addQuestionChampVisivility = addQuestionChampVisivility;
    }

    public boolean isDeleteQuestionChampVisivility() {
        return deleteQuestionChampVisivility;
    }

    public void setDeleteQuestionChampVisivility(boolean deleteQuestionChampVisivility) {
        this.deleteQuestionChampVisivility = deleteQuestionChampVisivility;
    }

    public void fileUploadListener(FileUploadEvent event){
        excelUploadedFile = event.getFile();
    }
    
    public void updateFromExcel() {
        try {
            setFileFromUploadFile();
            POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(excelFile));
            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            int countRows = sheet.getPhysicalNumberOfRows();
            String q = "";
            String r1 = "";
            String r2 = "";
            String r3 = "";
            String cat = "";
            int r = -1;
            QuestionEntity questionEntity = null;
            for (int i = 1; i < countRows; i++) {
                row = sheet.getRow(i);
                q = row.getCell(0).getStringCellValue();
                try{
                    r1 = row.getCell(1).getStringCellValue();
                }catch(IllegalStateException e){
                    r1 = ((int)row.getCell(1).getNumericCellValue())+"";
                }
                try{
                    r2 = row.getCell(2).getStringCellValue();
                }catch(IllegalStateException e){
                    r2 = ((int)row.getCell(2).getNumericCellValue())+"";
                }
                try{
                    r3 = row.getCell(3).getStringCellValue();
                }catch(IllegalStateException e){
                    r3 = ((int)row.getCell(3).getNumericCellValue())+"";
                }
                cat = row.getCell(5).getStringCellValue();
                r = (int) row.getCell(4).getNumericCellValue();
                questionEntity = new QuestionEntity();
                questionEntity.setAnswer1(r1);
                questionEntity.setAnswer2(r2);
                questionEntity.setAnswer3(r3);
                questionEntity.setCategory(cat);
                questionEntity.setQuestion(q);
                questionEntity.setRightAnswer(r);
                questionBeanLocal.insertQuestion(questionEntity);
            }
            sesionAdminMB.adminQuestions();
        } catch (IOException ex) {
            try {
                sesionAdminMB.adminQuestions();
            } catch (IOException ex1) {
                Logger.getLogger(QuestionsAdminMB.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    private void setFileFromUploadFile() throws FileNotFoundException, IOException{
       
            FileOutputStream outputStream = new FileOutputStream(excelFile);
        InputStream dbFileIS = excelUploadedFile.getInputstream();
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = dbFileIS.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        dbFileIS.close();
        outputStream.close();
        
    }
    
    public void addQuestion(){
        questionSelected = new QuestionEntity();
        addDialogVisivility = true;
        isInsert=true;
    }
    
    public void addQuestionChamp(){
        questionSelected = new QuestionEntity();
        addQuestionChampVisivility = true;
        isInsert=true;
    }
    
    public void editQuestion(QuestionEntity questionSelected){
        setQuestionSelected(questionSelected);
        addDialogVisivility = true;
        isInsert=false;
    }
    
    public void editQuestionChamp(QuestionEntity questionSelected){
        setQuestionSelected(questionSelected);
        addQuestionChampVisivility = true;
        isInsert=false;
    }
    
    public void confirmAddQuestion() {
        if (questionSelected != null) {
            questionSelected.setRightAnswer(posiblesRigthQuestion.indexOf(rigthQuestion)+1);
            questionSelected.setCategory(QuestionEntity.getCategoryKey(selectedCategory));
            if (isInsert) {
                questionBeanLocal.insertQuestion(questionSelected);
            } else {
                questionBeanLocal.updateQuestion(questionSelected);
            }
            addDialogVisivility = false;
        }
    }
    
    public void confirmAddQuestionChamp() {
        if (questionSelected != null) {
            questionSelected.setRightAnswer(posiblesRigthQuestion.indexOf(rigthQuestion)+1);
            questionSelected.setCategory(QuestionEntity.CAT_CHAMP_KEY);
            if (isInsert) {
                questionBeanLocal.insertQuestion(questionSelected);
            } else {
                questionBeanLocal.updateQuestion(questionSelected);
            }
            addQuestionChampVisivility = false;
        }
    }
    
    public void cancelAddQuestion(){
        addDialogVisivility = false;
        addQuestionChampVisivility=false;
    }
    
    public void deleteQuestion(QuestionEntity questionSelected){
        setQuestionSelected(questionSelected);
        deleteDialogVisivility = true;
    }
    
    public void deleteQuestionChamp(QuestionEntity questionSelected){
        setQuestionSelected(questionSelected);
        deleteQuestionChampVisivility = true;
    }
    
    public void confirmDeleteQuestion(){
        questionBeanLocal.deleteQuestion(questionSelected);
        questionSelected = null;
        deleteQuestionChampVisivility = false;
        deleteDialogVisivility = false;
    }
    public void cancelDeleteQuestion(){
        deleteDialogVisivility = false;
        deleteQuestionChampVisivility = false;
    }
    
    public void change(){
        System.err.println(rigthQuestion);
    }
    
    public void changeFilter(){
        
        setQuestionsList(
                questionBeanLocal.getQuestionsByCategory(
                sesionAdminMB.getUserLoyolaEntity().getId(),
                QuestionEntity.getCategoryKey(selectedCategory)));  
    }
}
