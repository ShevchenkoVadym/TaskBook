package ru.javarush.taskbook.web.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import ru.javarush.taskbook.logger.LoggerWrapper;
import ru.javarush.taskbook.model.*;
import ru.javarush.taskbook.model.enums.LifeStage;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.model.enums.TestEnvironment;
import ru.javarush.taskbook.service.*;
import ru.javarush.taskbook.util.CurrentUser;
import ru.javarush.taskbook.web.rest.TaskSortingOptions.TaskSort;
import ru.javarush.taskbook.web.rest.exceptions.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by ilyakhromov on 09.02.15.
 */
@RestController
@RequestMapping(value = "/rest/api/v1/tasks")
public class TaskRESTful {

    private static final LoggerWrapper LOG = LoggerWrapper.get(TaskRESTful.class);

    private static final String TASK_DEFAULT_FIELDS_NAMES = "id, version, author, tags, lastModifiedBy, approvalDate, " +
            "lifeStage, level, taskName, condition, templateCode, rating, votersAmount, likedBy, dislikedBy, " +
            "averageAttempts, successfulAttempts, failureAttempts, creationDate";
    private static final String TASK_DEFAULT_SORT = "-rating";
    private static final String TASK_DEFAULT_PAGE = "0";
    private static final String TASK_DEFAULT_PER_PAGE = "10";
    private static final String TASK_DEFAULT_LEVELS = "";
    private static final String TASK_DEFAULT_TAGS = "";
    private static List<Role> allowedRoles = new ArrayList<Role>() {{
        add(Role.ADMIN);
        add(Role.MODERATOR);
    }};

    private String version = "v1";

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/api")
    public String getVersion(){ return version; }

    @RequestMapping(value = "",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTasks(@RequestHeader(value = "host") String host,
                                   @RequestParam(value = "page", required = false, defaultValue = TASK_DEFAULT_PAGE) int page,
                                   @RequestParam(value = "per_page", required = false, defaultValue = TASK_DEFAULT_PER_PAGE) int perPage,
                                   @RequestParam(value = "sort", required = false, defaultValue = TASK_DEFAULT_SORT) String sort,
                                   @RequestParam(value = "fields", required = false, defaultValue = TASK_DEFAULT_FIELDS_NAMES) List<String> fields,
                                   @RequestParam(value = "levels", required = false, defaultValue = TASK_DEFAULT_LEVELS) List<String> levels,
                                   @RequestParam(value = "tags", required = false, defaultValue = TASK_DEFAULT_TAGS) List<String> tags,
                                   @RequestParam(value = "username", required = false, defaultValue = "") String username,
                                   @RequestParam(value = "inverse", required = false, defaultValue = "false") boolean inverse){

        validateFields(fields, true);// validate fields and erase field "sourceCode"

        Sort sortOption = TaskSort.getTaskSortForName(sort).getSortingOption();
        if(sortOption==null)throw new SortingOptionIsInvalidException(sort); // Trow exception if sort parameters are invalid

        //If username is provided then get solved tasks ids
        List<String> ids = new ArrayList<>();
        if(!username.equals("")){
            ids = userTaskService.getAllTaskIdSuccessTaskForUser(username);
        }

        //Get all statuses when user is admin or moderator
        //or only approved ones when user is regular
        LifeStage lifeStage = LifeStage.APPROVED;
        if (CurrentUser.get() != null && !Collections.disjoint(CurrentUser.get().getRoles(), allowedRoles)) {
            lifeStage = null;
        }

        PageRequest pageRequest = new PageRequest(page,perPage,sortOption);
        Page<Task> tasksPage = taskService.findAll(pageRequest, fields, levels, tags, lifeStage, ids, username, inverse);

        // If content is absent the exception would not be thrown.
        // The status would be switched on 204 - empty body

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("all tasks in pages requested. %n " +
                                             "current page number = %d, items per page = %d, %n" +
                                             "overall pages = %d, sorting = %s",page,perPage,tasksPage.getTotalPages(),sort);
        httpHeaders.set("request",headerMessage);

        httpHeaders.set("Link", formHttpHeaderLink(tasksPage,host,sort,null));// API for Pagination first, last, previous and next links

        return tasksPage.hasContent()?(new ResponseEntity(tasksPage, httpHeaders, HttpStatus.OK)):
                                      (new ResponseEntity("", httpHeaders, HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTaskById(@PathVariable("id")String id,
                                      @RequestParam(value = "fields", required = false, defaultValue = TASK_DEFAULT_FIELDS_NAMES)List<String> fields
                                     ){

        validateID(id);//validate id string. throw exception if incorrect or does not exist
        validateFields(fields,false);//validate fields first

        Task task = taskService.findById(id,fields); //get task
        if(task == null)throw new NoSuchTaskException(id);
        checkUserPrivileges(task); // hide sourceCode if current user not an admin or author of the task

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("Task with id = %s requested",id);
        httpHeaders.set("request",headerMessage);

        return new ResponseEntity(task, httpHeaders, HttpStatus.OK); // 200 success;
    }

    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteTaskById(@PathVariable(value = "id")String id){

        validateID(id);//validate id string. throw exception if incorrect or does not exist

        if(!taskService.isTaskExists(id))throw new NoSuchTaskException(id);
        taskService.delete(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("delete Task with id = %s",id);
        httpHeaders.set("request",headerMessage);
        ResponseEntity responseEntity = new ResponseEntity("", httpHeaders, HttpStatus.NO_CONTENT);//status 204 - no body
        return responseEntity;
    }

    @RequestMapping(value = "/{id}/vote",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeTaskRating(@PathVariable(value="id") String id,
                                           @RequestParam(value = "username", required = true) String username,
                                           @RequestParam(value = "delta", required = true) Integer delta){
        Task task = taskService.findById(id, new ArrayList<>());
        User voter = userService.getByName(username);
        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage;

        if (task == null) {
            headerMessage = String.format("No such task: %s", id);
            httpHeaders.set("request",headerMessage);
            return new ResponseEntity(headerMessage, httpHeaders, HttpStatus.NOT_FOUND);
        } else if (voter == null) {
            headerMessage = String.format("No such user: %s", username);
            httpHeaders.set("request",headerMessage);
            return new ResponseEntity(headerMessage, httpHeaders, HttpStatus.NOT_FOUND);
        } else if (task.getLikedBy().contains(username) || task.getDislikedBy().contains(username)) {
            headerMessage = "You can vote only one time";
            httpHeaders.set("request",headerMessage);
            return new ResponseEntity(headerMessage, httpHeaders, HttpStatus.UNAUTHORIZED);
        } else {
            if (delta > 0) {
                taskService.updateTaskRatingInc(id);
                taskService.updateTaskLikedBy(id, username);
            } else {
                taskService.updateTaskRatingDec(id);
                taskService.updateTaskDislikedBy(id, username);

                //Если рейтинг опускается до -10 отправляем задачу на аппрув
                if(task.getRating() == -9) {

                    taskService.updateTaskSimpleField(id,"lifeStage","VERIFICATION");
                    reduceRating(task, userService.getByName(task.getAuthor()), new double[]{0.15, 0.25, 0.35, 0.45});
                }
            }

            taskService.updateTaskVotersAmountInc(id);

            headerMessage = String.format("Rating is now %d", task.getRating());
            httpHeaders.set("request",headerMessage);
            return new ResponseEntity("", httpHeaders, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createTask(@RequestBody Task task){

        User cur = CurrentUser.get();
        HttpHeaders httpHeaders = new HttpHeaders();

        if (!cur.isEnabled()) {
            String headerMessage = "Unauthorized!";
            httpHeaders.set("request", headerMessage);
            return new ResponseEntity(headerMessage, httpHeaders, HttpStatus.UNAUTHORIZED);
        }

        // Если пользователь не админ или модератор, то отправляем на премодерацию
        if (Collections.disjoint(allowedRoles, cur.getRoles()))
            {
                task.setLifeStage(LifeStage.VERIFICATION);
                List<String> emails = userService.getAdminModerEmails();
                if (emails.size() > 0) {
                    String[] toEmailArray = new String[emails.size()];
                    toEmailArray = emails.toArray(toEmailArray);
                    String text = String.format("Добавлена новая задача пользователем %s \n" +
                                                "Название задачи: %s ", task.getAuthor(), task.getTaskName());

                    try {
                        LOG.info("Trying to send a 'new task added' email");
                        mailService.sendEmailNewTaskAdded(toEmailArray, text);
                    } catch (MailException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        else task.setLifeStage(LifeStage.APPROVED);

        task.setCreationDate(new Date());
        task.setVotersAmount(0);
        task.setLikedBy(new ArrayList<>());
        task.setDislikedBy(new ArrayList<>());

        TesterAnswerWrapper testerAnswerWrapper = taskService.validateAndSave(task); // make an attempt to save the task.
                                                                            // If task has not been compiled then task would not been saved
        String headerMessage = String.format("save Task. Task Id = %s", testerAnswerWrapper.getTask().getId());
        httpHeaders.set("request",headerMessage);

        LOG.info(testerAnswerWrapper.getMap().get("compilation"));
        LOG.info(testerAnswerWrapper.getMap().get("tests"));

        return testerAnswerWrapper.getTask().getId()==null ? // if no id found then task has not been saved
                new ResponseEntity(testerAnswerWrapper, httpHeaders, HttpStatus.UNPROCESSABLE_ENTITY)://status 422 - Server can not compile provided task
                new ResponseEntity(testerAnswerWrapper, httpHeaders, HttpStatus.CREATED);//status 201 - created successfully

    }

    @RequestMapping(value = "/test",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testTask(@RequestBody Task task,
                                   @RequestParam(value = "statistic", required = false, defaultValue = "false") boolean statistic){

        TesterAnswerWrapper testerAnswerWrapper = taskService.testAndWrap(task, statistic);

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("test Task. Task Id = %s", testerAnswerWrapper.getTask().getId());
        httpHeaders.set("request",headerMessage);

           return  new ResponseEntity(testerAnswerWrapper, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/tags",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTags(HttpServletRequest request){

        List<String> tags =taskService.getAllTags();
        checkNullOrEmpty(tags, request.getRequestURI());

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("Get All tags");
        httpHeaders.set("request",headerMessage);

        return new ResponseEntity(tags, httpHeaders, HttpStatus.OK);//status 200;
    }

    @RequestMapping(value = "/tags/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTagsByUser(@PathVariable("name") String name){
        validateName(name);
        LOG.info("Get All " + name + "'s tags");
        List<String> ids = userTaskService.getAllTaskIdSuccessTaskForUser(name);

        Map<String, Integer> tags = taskService.findTagsByUser(name, ids);

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("Get All User's tags");
        httpHeaders.set("request",headerMessage);

        return new ResponseEntity<>(tags, httpHeaders, HttpStatus.OK);//status 200;
    }

    @RequestMapping(value = "/levels",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllLevels(HttpServletRequest request){

        List<TaskLevel> levels =taskService.getAllLevels();
        checkNullOrEmpty(levels, request.getRequestURI());

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("Get All levels");
        httpHeaders.set("request",headerMessage);

        return new ResponseEntity(levels, httpHeaders, HttpStatus.OK);//status 200;
    }

    @RequestMapping(value = "/environment",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTestEnvironment(HttpServletRequest request){

        List<TestEnvironment> environment =taskService.getAllTestEnvironment();
        checkNullOrEmpty(environment, request.getRequestURI());

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("Get all test environment");
        httpHeaders.set("request",headerMessage);

        return new ResponseEntity(environment, httpHeaders, HttpStatus.OK);//status 200;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateTask(@PathVariable(value = "id")String id, @RequestBody Task task){

        validateID(id);
        HttpHeaders httpHeaders = new HttpHeaders();

        // Если пользователь не админ и не модератор
        if (Collections.disjoint(allowedRoles, CurrentUser.get().getRoles())) {
            Task principal = taskService.findById(id, new ArrayList<>());

            // И пользователь не автор задачи
            if (!principal.getAuthor().equals(CurrentUser.get().getUsername())) {
                return new ResponseEntity("Недостаточно прав для совершения данного действия.", httpHeaders, HttpStatus.UNAUTHORIZED);
            }
        }

        //Если задача не проходит тестирование
        TesterAnswerWrapper testerAnswerWrapper = taskService.testAndWrap(task, false);
        if (!testerAnswerWrapper.getMap().get("compilation").equals("OK") || !testerAnswerWrapper.getMap().get("tests").equals("OK")) {
            return new ResponseEntity(testerAnswerWrapper, httpHeaders, HttpStatus.EXPECTATION_FAILED);
        }

        StringBuilder sb = new StringBuilder("Update Task. Task Id = ")
        .append(id)
        .append(". Updated fields were: ");

        Task t = null;//this task would be returned as a result of updatenew ResponseEntity("Task with id" + id + "was not updated because of unknown reason", httpHeaders, HttpStatus.BAD_REQUEST); //400 task was not updated;

        if(task != null) {
            String[] tags = task.getTags().toArray(new String[task.getTags().size()]);
            t = taskService.updateTaskCollectionField(id, "tags", tags, false);

            task = taskService.ensureUniqueTaskName(task);

            Map<String, String> fields = new HashMap<>();
            fields.put("sourceCode", task.getSourceCode());
            fields.put("tests", task.getTests());
            fields.put("templateCode", task.getTemplateCode());
            fields.put("taskName", task.getTaskName());
            fields.put("testEnvironment", task.getTestEnvironment().toString());
            fields.put("condition", task.getCondition());

            if(!Collections.disjoint(CurrentUser.get().getRoles(), allowedRoles)) {
                fields.put("level", task.getLevel().name());
                fields.put("lifeStage", task.getLifeStage().name());
            }

            validateFields(new ArrayList<>(fields.keySet()),false);//validate fields
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                sb.append(entry.getKey()).append(", ");

                String fieldName = entry.getKey();

                sb.append(fieldName).append(", ");

                if(Task.getCollectionFieldsNames().contains(fieldName)){

                    t = taskService.updateTaskCollectionField(id,fieldName,entry.getValue().split(","),false);
                    //t = taskService.updateTaskCollectionField(id,fieldName,entry.getValue().toArray(new String[(entry.getValue().size())]));
                    continue;
                }

                String fieldValue = entry.getValue();

                if(Task.getVotersAmountFieldName().equals(fieldName)){
                    if("++".equals(fieldValue)){
                        t = taskService.updateTaskVotersAmountInc(id);
                        t = taskService.updateTaskSimpleField(id,Task.getAverageAttemptsFieldName(), getValueForAverageAttemptsField(t));
                        continue;
                    }
                }else
                if(Task.getSuccessfulAttemptsFieldName().equals(fieldName)){
                    if("++".equals(fieldValue)){
                        t = taskService.updateTaskSuccessfulAttemptsInc(id);
                        t = taskService.updateTaskSimpleField(id,Task.getAverageAttemptsFieldName(), getValueForAverageAttemptsField(t));
                        continue;
                    }
                }else
                if(Task.getFailureAttemptsFieldName().equals(fieldName)){
                    if("++".equals(fieldValue)){
                        t = taskService.updateTaskFailureAttemptsInc(id);
                        t = taskService.updateTaskSimpleField(id,Task.getAverageAttemptsFieldName(), getValueForAverageAttemptsField(t));
                        continue;
                    }
                }
                t = taskService.updateTaskSimpleField(id, fieldName, fieldValue);
            }
        }
        httpHeaders.set("request",sb.toString());

        checkUserPrivileges(t);//if current user is admin or author then return task with source code

        return t!=null?
                new ResponseEntity(t, httpHeaders, HttpStatus.OK): // 200 success
                new ResponseEntity("Task with id" + id + "was not updated because of unknown reason", httpHeaders, HttpStatus.BAD_REQUEST); //400 task was not updated;
    }

    @RequestMapping(value = "/user/{name}/added",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTasksByAuthor(@PathVariable("name")String name,
                                           @RequestHeader(value = "host") String host,
                                           @RequestParam(value = "page", required = false, defaultValue = TASK_DEFAULT_PAGE) int page,
                                           @RequestParam(value = "per_page", required = false, defaultValue = TASK_DEFAULT_PER_PAGE) int perPage,
                                           @RequestParam(value = "sort", required = false, defaultValue = TASK_DEFAULT_SORT) String sort,
                                           @RequestParam(value = "fields", required = false, defaultValue = TASK_DEFAULT_FIELDS_NAMES)List<String> fields
    ){
        validateName(name);
        validateFields(fields,true); // validate fields and erase field "sourceCode"

        Sort sortOption = TaskSort.getTaskSortForName(sort).getSortingOption();
        if(sortOption==null)throw new SortingOptionIsInvalidException(sort); // Trow exception if sort parameters are invalid

        PageRequest pageRequest = new PageRequest(page,perPage,sortOption);

        Page<Task> tasksPage = taskService.findByAuthor(name, pageRequest, fields);

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("%s's Tasks in pages are requested. %n " +
                "current page number = %d, items per page = %d, %n" +
                "overall pages = %d, sorting = %s",name,page,perPage,tasksPage.getTotalPages(),sort);

        httpHeaders.set("request",headerMessage);

        httpHeaders.set("Link", formHttpHeaderLink(tasksPage,host,sort,name));// in any case links would be generated.

        // the Page object can't be null but, it may not have content.
        // There are a lot of reasons for that: from bag request to actual content absence in the DB.
        // At this stage the reason can not be determined,
        // so in case of empty page the answer would be response entity with 204 status.
        return tasksPage.hasContent() ?
                new ResponseEntity(tasksPage, httpHeaders, HttpStatus.OK):
                new ResponseEntity(null, httpHeaders, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value= "/user/{name}/solved_ids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTasksIdSuccessTaskForUser(@PathVariable("name") String name){
        List<String> tasksId = userTaskService.getAllTaskIdSuccessTaskForUser(name);

        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage = String.format("all tasks id solved by %s in pages requested", userService.getByName(name).getUsername());

        httpHeaders.set("response", httpMessage);

        return !tasksId.isEmpty() ? (new ResponseEntity(tasksId, httpHeaders, HttpStatus.OK)) :
                (new ResponseEntity("", httpHeaders, HttpStatus.NO_CONTENT));
    }

    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/

    private String formHttpHeaderLink(Page page, String host, String sort, String author){

        int currenPage = page.getNumber();
        int totalPages = page.getTotalPages();
        int perPage = page.getNumberOfElements();
        page.getSort();

        String authorPart = "";
        if(author!=null)authorPart = "/user/{"+author+'}';

        StringBuilder sb = new StringBuilder();
        sb.append("<https://"+host+"/rest/api/v1/tasks"+authorPart+"?page="+((currenPage+1)>(totalPages-1)?currenPage:currenPage+1)+"&per_page="+perPage+"&sort="+sort+">");
        sb.append("; rel=\"next\", ");
        sb.append("<https://"+host+"/rest/api/v1/tasks"+authorPart+"?page="+((currenPage-1)<0?currenPage:currenPage-1)+"&per_page="+perPage+"&sort="+sort+">");
        sb.append("; rel=\"prev\", ");
        sb.append("<https://"+host+"/rest/api/v1/tasks"+authorPart+"?page=0&per_page="+perPage+"&sort="+sort+">");
        sb.append("; rel=\"first\", ");
        sb.append("<https://"+host+"/rest/api/v1/tasks"+authorPart+"?page="+(totalPages-1)+"&per_page="+perPage+"&sort="+sort+">");
        sb.append("; rel=\"last\"");

        return sb.toString();
    }

    private String getValueForAverageAttemptsField(Task t) {
        if(t.getSuccessfulAttempts()==0)return "0";
        return ""+(((double)(t.getFailureAttempts()+t.getSuccessfulAttempts()))/(t.getSuccessfulAttempts()));
    }

    private void validateFields(List<String> fields, boolean hideFields){//true - hide source code from user and validate rest fields
        if(fields!=null) {
            if(hideFields) fields.remove("sourceCode");
            for(String field : fields){

                if(!Task.getSimpleFieldsNames().contains(field) &&
                        !Task.getCollectionFieldsNames().contains(field))throw new NoSuchTaskFieldException(field);

                if(!field.matches("[a-zA-Z]+"))throw new IncorrcetRequestedFieldException(field);
            }
        }
    }

    private void validateName(String name){//true - hide source code from user and validate rest fields
        if(name!=null){
            if(!name.matches("[a-zA-Z0-9]+"))throw new IncorrcetRequestedNameException(name);
        }
    }

    private void checkUserPrivileges(Task task){//Check current user privileges.
                                                // if he is not an Admin or author of the task then sourceCode must be hidden
        if(task!=null) {
            if(CurrentUser.get()!=null) {
                if (!(
                        !Collections.disjoint(allowedRoles, CurrentUser.get().getRoles()) || //admin or mod ?
                                (CurrentUser.get().getUsername().equals(task.getAuthor())) //author?
                )) {
                    task.setSourceCode("");// if yes erase source code
                }
            }else task.setSourceCode("");// if yes erase source code
        }

    }

    private boolean validateID(String id){
        if (id.matches("[0-9A-Fa-f]+")){
            if(taskService.isTaskExists(id)) return true;
            throw new NoSuchTaskException(id);
        }
        throw new IncorrectTaskIdException(id);
    }

    private <T> T checkNullOrEmpty(T o, String URI){

        if(o == null)throw new RequestedDataIsAbsentOrNotAvailableException(URI);
        if(o instanceof List){
            if(((List) o).isEmpty())throw new RequestedDataIsAbsentOrNotAvailableException(URI);
        }
        return o;
    }

    public static String getTaskDefaultFieldsNames() {
        return TASK_DEFAULT_FIELDS_NAMES;
    }

    public void reduceRating(Task task, User user, double[] array)
    {
        switch(task.getLevel())
        {
            case BEGINNER:{
                user.setRating(new BigDecimal(user.getRating() - array[0]).setScale(2, RoundingMode.UP).doubleValue());
                userService.update(user);
                break;
            }
            case JUNIOR:{
                user.setRating(new BigDecimal(user.getRating() - array[1]).setScale(2, RoundingMode.UP).doubleValue());
                userService.update(user);
                break;
            }
            case MIDDLE:{
                user.setRating(new BigDecimal(user.getRating() - array[2]).setScale(2, RoundingMode.UP).doubleValue());
                userService.update(user);
                break;
            }
            case SENIOR:{
                user.setRating(new BigDecimal(user.getRating() - array[3]).setScale(2, RoundingMode.UP).doubleValue());
                userService.update(user);
                break;
            }
        }
    }
}
