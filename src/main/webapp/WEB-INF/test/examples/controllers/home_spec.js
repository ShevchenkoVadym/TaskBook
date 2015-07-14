describe('Unit: HomePageController', function() {
  beforeEach(module('taskbookApp'));

  var ctrl, scope, $httpBackend, users, tasks;

  beforeEach(inject(function($injector, $rootScope, $controller) {
    $httpBackend = $injector.get('$httpBackend');

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond({});
    
    $httpBackend.whenGET(constants.USERS_API + '?page=0&per_page=10').
          respond(constants.USERS_OBJECT);
      
    $httpBackend.whenGET(constants.TASKS_API + '?page=0&per_page=10').
          respond(constants.TASKS_OBJECT);

    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    scope = $rootScope.$new();
    ctrl = $controller('HomePageController', {$scope: scope});
  }));

  it('creates "users"', function() {
    expect(scope.users).toEqualData([]);
    
    $httpBackend.flush();

    expect(scope.users).toEqualData(constants.USERS);
  });

  it('creates "tasks"', function() {
    expect(scope.tasks).toEqualData([]);

    $httpBackend.flush();

    expect(scope.tasks).toEqualData(constants.TASKS);
  });

});