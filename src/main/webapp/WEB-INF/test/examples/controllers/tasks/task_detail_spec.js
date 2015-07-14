describe('Unit: TaskDetailController', function() {
  var ctrl, scope, $httpBackend, modalService, currentUser;

  beforeEach(module('taskbookApp'));

  beforeEach(inject(function($injector, $rootScope, $controller, CurrentUserService, ModalService) {
    $httpBackend = $injector.get('$httpBackend');

    currentUser = CurrentUserService;
    modalService = ModalService;

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);

    $httpBackend.whenGET(constants.TASKS_API + '/1?fields=id&fields=tests&fields=templateCode&fields=taskName&fields=tags&fields=testEnvironment&fields=condition&fields=author&fields=level&fields=rating&fields=likedBy&fields=dislikedBy&fields=votersAmount&fields=lifeStage')
        .respond(constants.TASKS[0]);

    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    scope = $rootScope.$new();
    ctrl = $controller('TaskDetailController', {$scope: scope, $routeParams: {id: 1} });
  }));

  it('creates "task" model with data from back-end', function() {
    $httpBackend.flush();
    expect(scope.task.id).toEqual('1');
  });

  it('initializes settings for ui-codemirror', function() {
    expect(scope.editorOptions).toEqual({
      lineWrapping : true,
      lineNumbers: true,
      tabSize: 2,
      smartIndent: true,
      mode: "text/x-java",
      extraKeys: { "Ctrl-Space": "autocomplete" },
      height: 'dynamic',
      autofocus: true
    });
  });

  describe('function: check()', function() {
    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.TASKS_API + '/test?statistic=true').respond(200, { map: { compilation: 'OK', tests: 'OK' }} );
      });

      it('initializes compilation result', function() {
        spyOn(modalService, 'openTaskTestResult');

        scope.check(scope.task.templateCode);
        $httpBackend.flush();

        expect(modalService.openTaskTestResult).toHaveBeenCalled();
      });

      it('shows preloader', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.check(scope.task.templateCode);

        expect(scope.show_loader).toBeTruthy();
      });

      it('hides preloader on response', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.check(scope.task.templateCode);
        $httpBackend.flush();

        expect(scope.show_loader).toBeFalsy();
      });
    });//<- check() on success

    describe('on response with error', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.TASKS_API + '/test?statistic=true').respond(401, { map: { compilation: 'FAILURE', tests: 'FAILURE' }} );
      });

      it('initializes compilation result', function() {
        spyOn(modalService, 'openTaskTestResult');

        scope.check(scope.task.templateCode);
        $httpBackend.flush();

        expect(modalService.openTaskTestResult).toHaveBeenCalledWith({ compilation : 'FAILURE', tests : 'FAILURE' });
      });

      it('shows preloader', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.check(scope.task.templateCode);

        expect(scope.show_loader).toBeTruthy();
      });

      it('hides preloader on response', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.check(scope.task.templateCode);
        $httpBackend.flush();

        expect(scope.show_loader).toBeFalsy();
      });
    });//<- check() on error
  });//<- check()

  describe('voting', function(){
    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.TASKS_API + '/1/vote?delta=1&username=alex').respond(404);
        $httpBackend.whenPOST(constants.TASKS_API + '/1/vote?delta=-1&username=alex').respond(500);
        $httpBackend.flush();
      });

      it('changes task likedBy stats', function() {
        expect(scope.task.likedBy).toEqualData([]);

        scope.vote(1);
        $httpBackend.flush();

        expect(scope.task.likedBy).toEqualData([]);
      });

      it('changes task rating', function() {
        var before = scope.task.rating;

        scope.vote(-1);
        $httpBackend.flush();

        expect(scope.task.rating).toEqual(before);
      });
    });//<- check() on success

    describe('on response with error', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.TASKS_API + '/1/vote?delta=1&username=alex').respond(200);
        $httpBackend.whenPOST(constants.TASKS_API + '/1/vote?delta=-1&username=alex').respond(200);
        $httpBackend.flush();
      });

      it('changes task likedBy stats', function() {
        expect(scope.task.likedBy).toEqualData([]);

        scope.vote(1);
        $httpBackend.flush();

        expect(scope.task.likedBy).toEqualData([currentUser.getUsername()]);
      });

      it('changes task rating', function() {
        var before = scope.task.rating;

        scope.vote(-1);
        $httpBackend.flush();

        expect(scope.task.rating).toEqual(before - 1);
      });
    });//<- vote() on error
  });//<- vote()
});//<- TaskDetailController