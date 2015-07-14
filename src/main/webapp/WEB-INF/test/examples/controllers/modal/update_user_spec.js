/**
 * Created by prima on 02.04.15.
 */
describe('Unit: UpdateUserController', function() {
  var ctrl, scope, $httpBackend, currentUser, User;

  beforeEach(module('taskbookApp'));

  beforeEach(inject(function($injector, $rootScope, $controller, $location, CurrentUserService, UserService) {
    $httpBackend = $injector.get('$httpBackend');

    currentUser = CurrentUserService;
    User = UserService;

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);
    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    scope = $rootScope.$new();
    scope.user = constants.USERS[0];
    scope.closeThisDialog = function(){};
    ctrl = $controller('UpdateUserController', {$scope: scope});
  }));

  it('exists', function(){
    expect(scope.updateUser).toBeDefined();
  });

  describe('function: updateUser()', function() {

    describe('when not authorized', function(){
      beforeEach(function(){
        currentUser.setUser(null);
      });

      it('returns from function', function(){
        spyOn(User, 'update');

        scope.updateUser(scope.user);
        $httpBackend.flush();

        expect(User.update).not.toHaveBeenCalled();
      });
    });

    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(200);
        $httpBackend.flush();
      });

      it('removes error message', function() {
        scope.formError = "Error";

        scope.updateUser(scope.user);
        $httpBackend.flush();

        expect(scope.formError).toBeUndefined();
      });
    });//<- updateUser() on success

    describe('on response with error', function() {
      it('shows correct error message', function() {
        $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(406, 'Email already exists');
        $httpBackend.flush();

        scope.updateUser(scope.user);
        $httpBackend.flush();

        expect(scope.formError).toEqual('Email already exists');
      });
    });//<- updateUser() on error
  });//<- updateUser()
});//<-- UpdateUserController