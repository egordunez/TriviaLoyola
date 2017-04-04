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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import loyola.ejb.UserBeanLocal;
import loyola.entitys.GroupEntity;
import loyola.entitys.UserLoyolaEntity;
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
@Named(value = "usersAdminMB")
@ViewScoped
@ManagedBean(eager=true)
public class UsersAdminMB implements Serializable {

    private String [] alfaNumeric = {"a","b","c","d","e","f",
        "g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
        "1","2","3","4","5","6","7","8","9","0",};
    private boolean addUserDialogVisivility = false;
    private boolean deleteUserDialogVisivility = false;
    //private boolean passwordsMatch = false;
    private boolean isInsert = false;
    private String fullName;
    private String pass;
    private String posibleUserName;
    private String fullNameTextHint="Nombre completo";
    private String roleSelected;
    private String groupSelected;
    private List<String> posiblesRoles;
    private List<String> posiblesGroups;
    private UserLoyolaEntity userLoyolaEntity;
    private Random random;
    private UploadedFile excelUploadedFile;
    private File excelFile;
    private List usersList;
    private String filterValue;
    
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
        setUsersList(userBeanLocal.getAllUsers()); 
        random = new Random();
        
        posiblesRoles = new ArrayList<>();
        posiblesGroups = new ArrayList<>();
        
        posiblesRoles.add(UserLoyolaEntity.ROLE_INVITED);
        posiblesRoles.add(UserLoyolaEntity.ROLE_ADMIN);

        posiblesGroups.add(GroupEntity.GROUP_4TO_A);
        posiblesGroups.add(GroupEntity.GROUP_4TO_B);
        posiblesGroups.add(GroupEntity.GROUP_5TO_A);
        posiblesGroups.add(GroupEntity.GROUP_5TO_B);
        posiblesGroups.add(GroupEntity.GROUP_6TO_A);
        posiblesGroups.add(GroupEntity.GROUP_6TO_B);
        posiblesGroups.add(GroupEntity.GROUP_7TO_A);
        posiblesGroups.add(GroupEntity.GROUP_7TO_B);
        posiblesGroups.add(GroupEntity.GROUP_COMP_3A);
        posiblesGroups.add(GroupEntity.GROUP_COMP_3B);
        posiblesGroups.add(GroupEntity.GROUP_COMP_3C);
        posiblesGroups.add(GroupEntity.GROUP_COMP_3D);
        posiblesGroups.add(GroupEntity.GROUP_DANCE);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_10);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_3);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_4);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_5);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_6);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_7);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_8);
        posiblesGroups.add(GroupEntity.GROUP_ENGLISH_9);
        posiblesGroups.add(GroupEntity.GROUP_GUITAR);
        posiblesGroups.add(GroupEntity.GROUP_PLASTIC);
        posiblesGroups.add(GroupEntity.GROUP_TEATHER);
	posiblesGroups.add(GroupEntity.GROUP_FLAUTA);
        posiblesGroups.add("Admin");
        
        excelFile = new File("excelFile");
    }
    
    public UsersAdminMB() {
    }

    public boolean isAddUserDialogVisivility() {
        return addUserDialogVisivility;
    }

    public void setAddUserDialogVisivility(boolean addUserDialogVisivility) {
        this.addUserDialogVisivility = addUserDialogVisivility;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPosibleUserName() {
        return posibleUserName;
    }

    public void setPosibleUserName(String posibleUserName) {
        this.posibleUserName = posibleUserName;
    }

    public List getUsersList() {
        if(filterValue==null||filterValue.equals("")){
            setUsersList(userBeanLocal.getAllUsers()); 
        }
        return usersList;
    }

    public void setUsersList(List usersList) {
        this.usersList = usersList;
    }

    public boolean isIsInsert() {
        return isInsert;
    }

    public void setIsInsert(boolean isInsert) {
        this.isInsert = isInsert;
    }

    public UserLoyolaEntity getUserLoyolaEntity() {
        return userLoyolaEntity;
    }

    public void setUserLoyolaEntity(UserLoyolaEntity userLoyolaEntity) {
        this.userLoyolaEntity = userLoyolaEntity;
    }

    public String getFullNameTextHint() {
        return fullNameTextHint;
    }

    public void setFullNameTextHint(String fullNameTextHint) {
        this.fullNameTextHint = fullNameTextHint;
    }

    public boolean isDeleteUserDialogVisivility() {
        return deleteUserDialogVisivility;
    }

    public void setDeleteUserDialogVisivility(boolean deleteUserDialogVisivility) {
        this.deleteUserDialogVisivility = deleteUserDialogVisivility;
    }

    public String getRoleSelected() {
        return roleSelected;
    }

    public void setRoleSelected(String roleSelected) {
        this.roleSelected = roleSelected;
    }

    public List<String> getPosiblesRoles() {
        return posiblesRoles;
    }

    public void setPosiblesRoles(List<String> posiblesRoles) {
        this.posiblesRoles = posiblesRoles;
    }

    public String getGroupSelected() {
        return groupSelected;
    }

    public void setGroupSelected(String groupSelected) {
        this.groupSelected = groupSelected;
    }

    public List<String> getPosiblesGroups() {
        return posiblesGroups;
    }

    public void setPosiblesGroups(List<String> posiblesGroups) {
        this.posiblesGroups = posiblesGroups;
    }

    public UploadedFile getExcelUploadedFile() {
        return excelUploadedFile;
    }

    public void setExcelUploadedFile(UploadedFile excelUploadedFile) {
        this.excelUploadedFile = excelUploadedFile;
    }

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
    
    
    
    public void addUser(){
        setAddUserDialogVisivility(true);
        setIsInsert(true);
        posibleUserName="";
        fullName="";
        pass="";
    }
    
    public void cancelAddUser(){
        setAddUserDialogVisivility(false);
        setUserLoyolaEntity(null);
    }
    
    public void confirmAddUser(){
        if (fullName.split(" ").length >=3 ) {
            if(isInsert){
                userBeanLocal.insertUser(new UserLoyolaEntity(fullName,
                        generateUser(true),
                        pass,
                        roleSelected,
                        groupSelected,0,0));
            }
            else{
                userLoyolaEntity.setFullName(fullName);
                userLoyolaEntity.setPass(pass);
                userLoyolaEntity.setUserName(generateUser(true));
                userLoyolaEntity.setRoleName(roleSelected);
                userLoyolaEntity.setGroup(groupSelected);
                userBeanLocal.updateUser(userLoyolaEntity);
                userLoyolaEntity = null;
            }
            setAddUserDialogVisivility(false);
        }
        else{
            setFullName("");
            setFullNameTextHint("Nombre y dos Apellidos");
        }
    }
    
    public String generateUser(boolean check){
        String [] splitName = fullName.toLowerCase().split(" ");
        if(splitName.length>=3){
            posibleUserName = splitName[0].substring(0, 1) + splitName[1];
            if(check && userBeanLocal.userNameExist(posibleUserName)){
                posibleUserName = splitName[0].substring(0, 1) + splitName[2];
            }
            if(check && userBeanLocal.userNameExist(posibleUserName)){
                posibleUserName = splitName[0].substring(0, 1) + splitName[1]+userBeanLocal.getLastId();
            }
        }
        generatePass();
        posibleUserName = posibleUserName.replace("ñ", "n");
        posibleUserName = posibleUserName.replace("á", "a");
        posibleUserName = posibleUserName.replace("é", "e");
        posibleUserName = posibleUserName.replace("í", "i");
        posibleUserName = posibleUserName.replace("ó", "o");
        posibleUserName = posibleUserName.replace("ú", "u");
        return posibleUserName;
    }
    
    public void generateUserFalse(){
        String [] splitName = fullName.toLowerCase().split(" ");
        if (splitName.length >= 3) {
            posibleUserName = splitName[0].substring(0, 1) + splitName[1];
            if (userBeanLocal.userNameExist(posibleUserName)) {
                posibleUserName = splitName[0].substring(0, 1) + splitName[2];
            }
            if (userBeanLocal.userNameExist(posibleUserName)) {
                posibleUserName = splitName[0].substring(0, 1) + splitName[1] + userBeanLocal.getLastId();
            }
            generatePass();
            posibleUserName = posibleUserName.replace("ñ", "n");
            posibleUserName = posibleUserName.replace("á", "a");
            posibleUserName = posibleUserName.replace("é", "e");
            posibleUserName = posibleUserName.replace("í", "i");
            posibleUserName = posibleUserName.replace("ó", "o");
            posibleUserName = posibleUserName.replace("ú", "u");
        }
    }
    
    public String generateUser(String name){
        String [] splitName = name.toLowerCase().split(" ");
        if(splitName.length>=3){
            name = splitName[0].substring(0, 1) + splitName[1];
            name = replaceRareChars(name);
            if(userBeanLocal.userNameExist(name)){
                name = splitName[0].substring(0, 1) + splitName[2];
                name = replaceRareChars(name);
            }
            if(userBeanLocal.userNameExist(name)){
                name = splitName[0].substring(0, 1) + splitName[1] + userBeanLocal.getLastId();
            }
        }
        name = replaceRareChars(name);
        return name;
    }
    
    private String replaceRareChars(String name){
        name = name.replace("ñ", "n");
        name = name.replace("á", "a");
        name = name.replace("é", "e");
        name = name.replace("í", "i");
        name = name.replace("ó", "o");
        name = name.replace("ú", "u");
        return name;
    }
    
    public void generatePass() {
        String [] splitName = fullName.toLowerCase().split(" ");
        if(splitName.length>=2 && pass.equals("") && isInsert){
            String posiblePass = (splitName[0] + ".").toLowerCase();
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            pass = posiblePass;
        }
    }
    
    public String generatePass(String name) {
        String [] splitName = name.toLowerCase().split(" ");
        if(splitName.length>=2){
            String posiblePass = (splitName[0] + ".").toLowerCase();
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            posiblePass += alfaNumeric[(int)(random.nextDouble()*36)];
            return posiblePass;
        }
        return "";
    }
    
    public void editUser(UserLoyolaEntity userLoyolaEntity){
        setAddUserDialogVisivility(true);
        setIsInsert(false);
        pass = userLoyolaEntity.getPass();
        fullName = userLoyolaEntity.getFullName();
        posibleUserName = userLoyolaEntity.getUserName();
        setUserLoyolaEntity(userLoyolaEntity);
    }
    
    public void deleteUser(UserLoyolaEntity userLoyolaEntity){
        setDeleteUserDialogVisivility(true);
        setUserLoyolaEntity(userLoyolaEntity);
    }
    
    public void confirmDeleteUser(){
        userBeanLocal.deleteUser(userLoyolaEntity);
        setDeleteUserDialogVisivility(false);
    }
    
    public void cancelDeleteUser(){
        setDeleteUserDialogVisivility(false);
        setUserLoyolaEntity(null);
    }
    
    public void change(){
        System.err.println(roleSelected);
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
            String n = "";
            String g = "";
            UserLoyolaEntity userLoyolaEntity = null;
            try{
                for (int i = 1; i < countRows; i++) {
                row = sheet.getRow(i);
                n = row.getCell(0).getStringCellValue();
                g = row.getCell(1).getStringCellValue();
                userLoyolaEntity = new UserLoyolaEntity(n,
                        generateUser(n),generatePass(n),UserLoyolaEntity.ROLE_INVITED,g,0,0);
                userBeanLocal.insertUser(userLoyolaEntity);
            }
            }catch(NullPointerException e){
                System.err.println(e.getMessage());
            }
            sesionAdminMB.adminUsers();
        } catch (IOException ex) {
            try {
                sesionAdminMB.adminUsers();
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
    
    public void onFilterChangedText(){
        buildFilteredList();
    }
    
    private void buildFilteredList() {
        if (filterValue!=null && filterValue.length() > 2) {
            setUsersList(userBeanLocal.getFilteredList(filterValue));
        }
    }
}
