describe('Unit: UserProfileController', function() {
  beforeEach(module('taskbookApp'));

  var $httpBackend, tasks, users, ctrl, scope, currentUser, taskObject, User, loaderSpy;

  beforeEach(inject(function($injector, CurrentUserService, UserService, LoaderService) {
    $httpBackend = $injector.get('$httpBackend');

    users = angular.copy(constants.USERS);
    tasks = constants.TASKS;
    currentUser = CurrentUserService;
    currentUser.setUser(constants.CURRENT_USER);
    taskObject = constants.TASKS_OBJECT;
    User = UserService;
    loaderSpy = spyOn(LoaderService, 'toggle');

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);
    $httpBackend.whenGET(constants.USERS_API + '/alex').respond(constants.USERS[0]);
    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    $httpBackend.whenGET(constants.TASKS_API + '/user/alex/added?fields=id&fields=taskName&fields=rating&fields=author&fields=level&fields=creationDate&page=0&per_page=10&sort=-rating')
        .respond(taskObject);

    $httpBackend.whenGET(constants.TASKS_API + '?fields=id&fields=taskName&fields=rating&fields=author&fields=level&fields=creationDate&page=0&per_page=10&sort=-rating&username=alex')
        .respond(taskObject);

    $httpBackend.whenGET(constants.USERS_API + '/alex').respond(users[0]);

    $httpBackend.whenGET(constants.USERS_API + '/alex/tags').respond(200);

    $httpBackend.whenGET('data/countries.json')
          .respond([
            "Russian Federation",
            "Belarus",
            "Kazakhstan",
            "Ukraine"
          ]);
  }));//<- beforeEach httpBackend

  beforeEach(inject(function($rootScope, $controller) {
    scope = $rootScope.$new();
    ctrl = $controller('UserProfileController', {$scope: scope, $routeParams: {username: 'alex'}});
  }));

  it('creates "user" model with appropriate data from back-end', function() {
    expect(scope.user).toBeUndefined;

    $httpBackend.flush();

    expect(scope.user.username).toEqual('alex');
  });

  it('toggles global loader', function(){
    $httpBackend.flush();

    expect(loaderSpy.calls.count()).toBe(2);
  });

  it('creates "tasksSolved" model with appropriate data from back-end', function() {
    expect(scope.tasksSolved).toEqualData([]);

    $httpBackend.flush();

    expect(scope.tasksSolved).toEqualData(tasks);
  });

    it('creates "tasksAdded" model with appropriate data from back-end', function() {
      expect(scope.tasksAdded).toEqualData([]);

      $httpBackend.flush();

      expect(scope.tasksAdded).toEqualData(tasks);
    });

  it('creates "countries" model with appropriate data from back-end', function() {
    expect(scope.countries).toEqualData([]);

    $httpBackend.flush();

    expect(scope.countries).toEqualData([
          "Russian Federation",
          "Belarus",
          "Kazakhstan",
          "Ukraine"
        ]);
  });
});//<- UserProfileController