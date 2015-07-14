describe('Unit: TaskEditController', function() {
  var ctrl, scope, $httpBackend, tasks, taskObject, currentUser, responseExample, modalService, loaderSpy;

  beforeEach(module('taskbookApp'));

  beforeEach(inject(function($injector, $rootScope, $controller, $routeParams, CurrentUserService, ModalService, LoaderService) {
    $httpBackend = $injector.get('$httpBackend');

    tasks = constants.TASKS;
    taskObject = constants.TASKS_OBJECT;
    currentUser = CurrentUserService;
    currentUser.setUser(constants.USERS[0]);
    modalService = ModalService;
    responseExample = {
        "id":"54fab8fd44aeb9b63302cbcb",
        "version":7,
        "author":"Populator",
        "tags":["конструкторы","класс"],
        "authorityName":"alex",
        "creationDate":1425717501625,
        "lastModifiedBy":"Populator",
        "lastModificationDate":1425743969873,
        "approvalDate":1419984001001,
        "lifeStage":"APPROVED",
        "level":"BEGINNER",
        "taskName":"Конструкторы для Rectangle",
        "condition":"condition",
        "sourceCode":"source",
        "templateCode":"template",
        "tests":"tests",
        "rating":42,
        "votersAmount":4,
        "averageAttempts":88.53,
        "successfulAttempts":12,
        "failureAttempts":1,
        "testEnvironment":"JAVA_JUNIT",
        "topLevelCommentsId":null
    };
    loaderSpy = spyOn(LoaderService, 'toggle');

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);

    $httpBackend.whenGET(constants.TASKS_API).respond(taskObject);

    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    $httpBackend.whenGET(constants.TASKS_API + '/54fab8fd44aeb9b63302cbcb?fields=id&fields=sourceCode&fields=tests&fields=templateCode&fields=taskName&fields=tags&fields=testEnvironment&fields=condition&fields=level&fields=author&fields=lifeStage')
        .respond(responseExample);

    $httpBackend.whenGET(constants.TASKS_API + '/1').respond(tasks[0]);

    $httpBackend.whenGET('partials/modals/task-test-result.html').respond(200);

    $httpBackend.whenGET(constants.TASKS_API + '/tags').respond(200);

    scope = $rootScope.$new();
    ctrl = $controller('TaskEditController', {$scope: scope, $routeParams: {id: '54fab8fd44aeb9b63302cbcb'}});
  }));

  it('gets task instance', function() {
    $httpBackend.flush();

    expect(scope.task).toBeDefined();
    expect(scope.task_state).toEqual('existed');
  });

  it('toggles global loader', function(){
     $httpBackend.flush();

     expect(loaderSpy.calls.count()).toBe(2);
  });

  describe('function: publish()', function() {
    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPUT(constants.TASKS_API + '/54fab8fd44aeb9b63302cbcb')
            .respond(responseExample, { task: responseExample, map: { compilation: 'OK', tests: 'OK' }} );

        $httpBackend.flush();
      });

      it('initializes compilation result', function() {
        spyOn(modalService, 'openTaskTestResult');

        scope.publish(scope.task);
        $httpBackend.flush();

        expect(modalService.openTaskTestResult).toHaveBeenCalled();
      });
    });//<- publish() on success

    describe('on response with error', function() {
      beforeEach(function(){
        $httpBackend.whenPUT(constants.TASKS_API + '/54fab8fd44aeb9b63302cbcb').respond(400, { map: { compilation: 'OK', tests: 'OK' }});
        $httpBackend.flush();
      });

      it('initializes compilation result', function() {
        spyOn(modalService, 'openTaskTestResult');

        scope.publish(scope.task);
        $httpBackend.flush();

        expect(modalService.openTaskTestResult).toHaveBeenCalled();
      });
    });//<- publish() on error
  });//<- publish()

  describe('function: check()', function() {
    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.TASKS_API + '/test?statistic=false').respond(200, { map: { compilation: 'OK', tests: 'OK' }} );
        $httpBackend.flush();
      });

      it('initializes compilation result', function() {
        spyOn(modalService, 'openTaskTestResult');

        scope.check(scope.task);
        $httpBackend.flush();

        expect(modalService.openTaskTestResult).toHaveBeenCalled();
      });

    });//<- check() on success

    describe('on response with error', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.TASKS_API + '/test?statistic=false').respond(400, { map: { compilation: 'FAILURE', tests: 'FAILURE' }});
        $httpBackend.flush();
      });

      it('initializes compilation result', function() {
        spyOn(modalService, 'openTaskTestResult');

        scope.check(scope.task);
        $httpBackend.flush();

        expect(modalService.openTaskTestResult).toHaveBeenCalledWith({ compilation : 'FAILURE', tests : 'FAILURE' });
      });
    });//<- check() on error
  });//<- check()
});//<- TaskEditController