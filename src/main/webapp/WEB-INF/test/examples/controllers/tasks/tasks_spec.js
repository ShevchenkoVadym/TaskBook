/**
 * Created by prima on 07.03.15.
 */
describe('Unit: TasksController', function() {
  var ctrl, scope, $httpBackend, location, tasks, tasksObject, currentUser;

  beforeEach(module('taskbookApp'));

  beforeEach(inject(function($injector, $rootScope, $controller, $location, CurrentUserService) {
    $httpBackend = $injector.get('$httpBackend');
    location = $location;

    tasks = angular.copy(constants.TASKS);
    tasksObject = constants.TASKS_OBJECT;
    currentUser = CurrentUserService;

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);

    $httpBackend.whenGET(constants.TASKS_API + '?levels=junior&page=0&per_page=10&sort=-rating&tags=example&tags=bar').respond(tasksObject);

    $httpBackend.whenGET(constants.TASKS_API + '/tags').respond(200, ['классы', 'конструкторы', 'циклы']);
    $httpBackend.whenGET(constants.TASKS_API + '/levels').respond(200, ['BEGINNER', 'JUNIOR', 'MIDDLE']);
    $httpBackend.whenGET(constants.TASKS_API + '/user/alex/solved_ids').respond([constants.TASKS[0].id]);

    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    scope = $rootScope.$new();
    ctrl = $controller('TasksController', {$scope: scope, $location: location.search({tags: ['example', 'bar'], levels: 'junior'})});
  }));

  it('initializes tasks, tags, levels with backend data', function() {
    expect(scope.tasks).toEqualData([]);
    expect(scope.tags).toEqualData([]);
    expect(scope.levels).toEqualData([]);
    expect(scope.multipleTags.tags).toEqualData(['example', 'bar']);
    expect(scope.multipleLevels.levels).toEqualData(['junior']);

    $httpBackend.flush();

    expect(scope.tasks).toEqualData(tasks);
    expect(scope.tags).toEqualData(['классы', 'конструкторы', 'циклы']);
    expect(scope.levels).toEqualData(['BEGINNER', 'JUNIOR', 'MIDDLE']);
  });

  it('initializes multiple tasks, tags with $location params', function() {
    expect(scope.multipleTags.tags).toEqualData(['example', 'bar']);
    expect(scope.multipleLevels.levels).toEqualData(['junior']);
  });

  describe('function: deleteTask()', function() {
    describe('when is not authorized', function () {
      beforeEach(function(){
        $httpBackend.whenDELETE(constants.TASKS_API + '/1').respond(200);
        $httpBackend.flush();
      });

      it('does not delete task', function(){
        currentUser.setUser(constants.USERS[2]); //roles: USER
        currentUser.addRole('MODERATOR'); //roles: USER, MODERATOR
        scope.deleteTask(scope.tasks[0]);

        expect(scope.tasks.length).toBe(3);

        currentUser.setUser(constants.USERS[0]); //set admin as current user
      });
    });

    describe('when authorized', function(){
      describe('on successful response', function() {
        beforeEach(function(){
          $httpBackend.whenDELETE(constants.TASKS_API + '/1').respond(200);
          $httpBackend.flush();
          _.remove(tasksObject.content, tasks[0]);
        });

        it('deletes task from scope', function() {
          expect(scope.tasks.length).toBe(3);

          scope.deleteTask(scope.tasks[0]);
          $httpBackend.flush();

          expect(scope.tasks.length).toBe(2);
        });
      });//<- deleteTask() on success

      describe('on response with error', function() {
        beforeEach(function(){
          $httpBackend.whenDELETE(constants.TASKS_API + '/1').respond(401);
          $httpBackend.flush();

          it('does not delete task from scope', function() {
            scope.deleteTask(scope.tasks[0]);
            $httpBackend.flush();

            expect(scope.tasks).toEqualData(tasks);
          });
        });
      });//<- deleteTask() on error
    });//<- when authorized
  });//<- deleteTask()
});//<-- TasksController