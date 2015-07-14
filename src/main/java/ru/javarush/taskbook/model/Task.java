package ru.javarush.taskbook.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


import org.hibernate.type.DateType;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.javarush.taskbook.model.enums.LifeStage;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.model.enums.TestEnvironment;

@Document(collection = "task")
@CompoundIndexes({
       @CompoundIndex(name = "name_rating", def = "{'author': 1, 'rating': -1}")
})

public class Task implements Serializable{

    @Transient
    private static final List<String> simpleFields;
    @Transient
    private static final List<String> collectionFields;
    static {
        simpleFields = new ArrayList<>();
        collectionFields = new ArrayList<>();

        for(Field field : Arrays.asList(Task.class.getDeclaredFields())){
            if(Iterable.class.isAssignableFrom(field.getType()))collectionFields.add(field.getName());
            else simpleFields.add(field.getName());
        }
    }
    @Transient
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
    @Version
    private Integer version;
    @CreatedBy
	private String author;
	private Set<String> tags;
	private String authorityName;
    @Indexed(direction = IndexDirection.DESCENDING) // index to find new tasks
	@CreatedDate
    private Date creationDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private Date lastModificationDate;
	private Date approvalDate;
	private LifeStage lifeStage = LifeStage.DRAFT;
    private TaskLevel level;
    private String taskName;
	private String condition;
	private String sourceCode;
    private String templateCode;
	private String tests;
    @Indexed(direction = IndexDirection.DESCENDING) // index to sort out max rating
	private int rating;
    @Indexed(direction = IndexDirection.DESCENDING) // index to find tasks with max voters amount
    private int votersAmount;
    private List<String> likedBy;
    private List<String> dislikedBy;
	private double averageAttempts;
    private int successfulAttempts;
    private int failureAttempts;
    private TestEnvironment testEnvironment;
    //TODO refactor comment section using @DBRef annotation
    private ArrayList<String> topLevelCommentsId;

    /**********************************************************************
    *      When you are going to change Task be sure that
    *      you have not set up a function "setId" or a constructor
    *      with "id" field because ID is appointed by MongoDB
    *      and possible attempts to change it manually
    *      may cause general DB error.!!!!
    /**********************************************************************/

    public Task(){}

    public Task(String name){
        this.taskName = name;
    }
/*
    public Task(Integer version, String author, Set<String> tags,
                String authorityName, Date creationDate,
                Date lastModificationDate, Date approvalDate,
                LifeStage lifeStage, TaskLevel level,
                String taskName, String condition,
                String sourceCode, String templateCode, String tests,
                double rating, int votersAmount,
                double averageAttempts, int successfulAttempts,
                int failureAttempts, TestEnvironment testEnvironment,
                ArrayList<String> topLevelCommetsId) {

        this.version = version;
        this.author = author;
        if(tags != null)this.tags = tags;
            else this.tags = new HashSet<String>();
        this.authorityName = authorityName;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.approvalDate = approvalDate;
        this.lifeStage = lifeStage;
        this.level = level;
        this.taskName = taskName;
        this.condition = condition;
        this.sourceCode = sourceCode;
        this.templateCode = templateCode;
        this.tests = tests;
        this.rating = rating;
        this.votersAmount = votersAmount;
        this.averageAttempts = averageAttempts;
        this.successfulAttempts = successfulAttempts;
        this.failureAttempts = failureAttempts;
        this.testEnvironment = testEnvironment;
        if(topLevelCommetsId != null)this.topLevelCommentsId = topLevelCommetsId;
            else this.topLevelCommentsId = new ArrayList<String>();
    }
*/
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Task setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Task setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public Task setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
        return this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Task setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public Task setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
        return this;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public Task setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
        return this;
    }

    public LifeStage getLifeStage() {
        return lifeStage;
    }

    public TaskLevel getLevel() {
        return level;
    }

    public Task setLevel(TaskLevel level) {
        this.level = level;
        return this;
    }

    public Task setLifeStage(LifeStage lifeStage) {
        this.lifeStage = lifeStage;
        return this;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public Task setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public Task setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
        return this;
    }

    public String getTemplateCode() { return templateCode; }

    public Task setTemplateCode(String templateCode) { this.templateCode = templateCode;  return this;}

    public String getTests() {
        return tests;
    }

    public Task setTests(String tests) {
        this.tests = tests;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Task setRating(int rating) {
        if(rating < 0)return this;
        this.rating = rating;
        return this;
    }

    public int getVotersAmount() {
        return votersAmount;
    }

    public Task setVotersAmount(int votersAmount) {
        if(votersAmount < 0)return this;
        this.votersAmount = votersAmount;
        return this;
    }

    public List<String> getLikedBy() { return likedBy; }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public void setDislikedBy(List<String> dislikedBy) {
        this.dislikedBy = dislikedBy;
    }

    public List<String> getDislikedBy() { return dislikedBy; }

    public double getAverageAttempts() {
        return averageAttempts;
    }

    public Task setAverageAttempts(double averageAttempts) {
        if(averageAttempts < 0) return this;
        this.averageAttempts = averageAttempts;
        return this;
    }

    public int getSuccessfulAttempts() {
        return successfulAttempts;
    }

    public Task setSuccessfulAttempts(int successfulAttempts) {
        if(successfulAttempts < 0)return this;
        this.successfulAttempts = successfulAttempts;
        return this;
    }

    public int getFailureAttempts() {
        return failureAttempts;
    }

    public Task setFailureAttempts(int failureAttempts) {
        if(failureAttempts < 0)return this;
        this.failureAttempts = failureAttempts;
        return this;
    }

    public TestEnvironment getTestEnvironment() {
        return testEnvironment;
    }

    public void setTestEnvironment(TestEnvironment testEnvironment) {
        this.testEnvironment = testEnvironment;
    }

    public ArrayList<String> getTopLevelCommentsId() {
        return topLevelCommentsId;
    }

    public Task setTopLevelCommentsId(ArrayList<String> topLevelCommentsId) {
        this.topLevelCommentsId = topLevelCommentsId;
        return this;
    }

    public void ensureUniqueTaskName(List<Task> tasks){
        String originalName = this.getTaskName();
        int count = 1;

        if (Character.isDigit(originalName.charAt(originalName.length() - 1)) && originalName.charAt(originalName.length() - 2) == ' '){
            count = Integer.parseInt(originalName.substring(originalName.length() - 1));
            originalName = originalName.substring(0, originalName.length() - 2);
        }

        List<String> names = tasks.stream().map(Task::getTaskName).collect(Collectors.toList());

        while(names.contains(this.getTaskName())){
            this.setTaskName(originalName + " " + String.valueOf(count++));
        }
    }

    /*************************************************************
     *
     *  next methods are used to build REST api
     *  they must comply with Task fields
     *
     *  "id='" + id + '\'' +
     ", version=" + version +
     ", author='" + author + '\'' +
     ", tags=" + tags +
     ", authorityName='" + authorityName + '\'' +
     ", creationDate=" + creationDate +
     ", lastModifiedBy='" + lastModifiedBy + '\'' +
     ", lastModificationDate=" + lastModificationDate +
     ", approvalDate=" + approvalDate +
     ", lifeStage=" + lifeStage +
     ", level=" + level +
     ", taskName='" + taskName + '\'' +
     ", condition='" + condition + '\'' +
     ", sourceCode='" + sourceCode + '\'' +
     ", templateCode='" + templateCode + '\'' +
     ", tests='" + tests + '\'' +
     ", rating=" + rating +
     ", votersAmount=" + votersAmount +
     ", averageAttempts=" + averageAttempts +
     ", successfulAttempts=" + successfulAttempts +
     ", failureAttempts=" + failureAttempts +
     ", testEnvironment=" + testEnvironment +
     ", topLevelCommentsId=" + topLevelCommentsId +
     *************************************************************/

    public static List<String> getSimpleFieldsNames(){
        return simpleFields;
    }
    public static List<String> getCollectionFieldsNames(){
        return collectionFields;
    }
    public static String getVotersAmountFieldName(){
        return "votersAmount";
    }
    public static String getAverageAttemptsFieldName(){
        return "averageAttempts";
    }
    public static String getSuccessfulAttemptsFieldName(){
        return "successfulAttempts";
    }
    public static String getFailureAttemptsFieldName(){
        return "failureAttempts";
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (Double.compare(task.averageAttempts, averageAttempts) != 0) return false;
        if (failureAttempts != task.failureAttempts) return false;
        if (Integer.compare(task.rating, rating) != 0) return false;
        if (successfulAttempts != task.successfulAttempts) return false;
        if (votersAmount != task.votersAmount) return false;
        if (approvalDate != null ? !approvalDate.equals(task.approvalDate) : task.approvalDate != null) return false;
        if (author != null ? !author.equals(task.author) : task.author != null) return false;
        if (authorityName != null ? !authorityName.equals(task.authorityName) : task.authorityName != null)
            return false;
        if (condition != null ? !condition.equals(task.condition) : task.condition != null) return false;
        if (creationDate != null ? !creationDate.equals(task.creationDate) : task.creationDate != null) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (lastModificationDate != null ? !lastModificationDate.equals(task.lastModificationDate) : task.lastModificationDate != null)
            return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(task.lastModifiedBy) : task.lastModifiedBy != null)
            return false;
        if (level != task.level) return false;
        if (lifeStage != task.lifeStage) return false;
        if (sourceCode != null ? !sourceCode.equals(task.sourceCode) : task.sourceCode != null) return false;
        if (tags != null ? !tags.equals(task.tags) : task.tags != null) return false;
        if (taskName != null ? !taskName.equals(task.taskName) : task.taskName != null) return false;
        if (templateCode != null ? !templateCode.equals(task.templateCode) : task.templateCode != null) return false;
        if (testEnvironment != task.testEnvironment) return false;
        if (tests != null ? !tests.equals(task.tests) : task.tests != null) return false;
        if (topLevelCommentsId != null ? !topLevelCommentsId.equals(task.topLevelCommentsId) : task.topLevelCommentsId != null)
            return false;
        if (version != null ? !version.equals(task.version) : task.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (authorityName != null ? authorityName.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModificationDate != null ? lastModificationDate.hashCode() : 0);
        result = 31 * result + (approvalDate != null ? approvalDate.hashCode() : 0);
        result = 31 * result + (lifeStage != null ? lifeStage.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (sourceCode != null ? sourceCode.hashCode() : 0);
        result = 31 * result + (templateCode != null ? templateCode.hashCode() : 0);
        result = 31 * result + (tests != null ? tests.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + votersAmount;
        temp = Double.doubleToLongBits(averageAttempts);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + successfulAttempts;
        result = 31 * result + failureAttempts;
        result = 31 * result + (testEnvironment != null ? testEnvironment.hashCode() : 0);
        result = 31 * result + (topLevelCommentsId != null ? topLevelCommentsId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                ", authorityName='" + authorityName + '\'' +
                ", creationDate=" + creationDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModificationDate=" + lastModificationDate +
                ", approvalDate=" + approvalDate +
                ", lifeStage=" + lifeStage +
                ", level=" + level +
                ", taskName='" + taskName + '\'' +
                ", condition='" + condition + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", tests='" + tests + '\'' +
                ", rating=" + rating +
                ", votersAmount=" + votersAmount +
                ", likedBy=" + likedBy +
                ", dislikedBy=" + dislikedBy +
                ", averageAttempts=" + averageAttempts +
                ", successfulAttempts=" + successfulAttempts +
                ", failureAttempts=" + failureAttempts +
                ", testEnvironment=" + testEnvironment +
                ", topLevelCommentsId=" + topLevelCommentsId +
                '}';
    }
}
