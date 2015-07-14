describe('Unit: UsersController', function() {
  beforeEach(module('taskbookApp'));

  var $httpBackend, tasks, users, current_user, ctrl, scope, currentUser;

  beforeEach(inject(function($injector, CurrentUserService) {
    $httpBackend = $injector.get('$httpBackend');

    users = angular.copy(constants.USERS);
    tasks = constants.TASKS;
    current_user = constants.CURRENT_USER;
    currentUser = CurrentUserService;

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(current_user);

    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    $httpBackend.whenGET(constants.USERS_API + '?name_query=&page=0&per_page=10&sort=-rating')
        .respond({
          content: users,
          total: users.length,
          pageable: { page: 0 }
        });

    $httpBackend.whenGET('partials/countries.json').
        respond([
          "Russian Federation",
          "Belarus",
          "Kazakhstan",
          "Ukraine"
        ]);
  }));//<- beforeEach httpBackend

  beforeEach(inject(function($rootScope, $controller) {
    scope = $rootScope.$new();
    ctrl = $controller('UsersController', {$scope: scope});
  }));

  it('creates "users" model with data from back-end', function() {
    expect(scope.users).toEqualData([]);

    $httpBackend.flush();

    expect(scope.users).toEqualData(users);
  });

  describe('function: change()', function(){
    it('nullifies scope.updated_user', function() {
      scope.updated_user = 'test';

      scope.change();

      expect(scope.updated_user).toEqual('');
    });
  });//<- change()

  describe('function: changeRole()', function(){
    describe('when user is not authorized', function(){
      it('does not perform delete request', function(){
        scope.users = constants.USERS;
        currentUser.setUser(scope.users[2]); //roles: USER
        currentUser.addRole('MODERATOR'); //roles: USER, MODERATOR
        var roles = scope.users[1].roles;
        scope.changeRole(scope.users[1]);
        $httpBackend.flush();

        expect(scope.users[1].roles).toEqualData(roles);

        currentUser.setUser(constants.USERS[0]); //set admin as current user
      });
    });

    describe('when user is admin', function () {
      beforeEach(function(){
        scope.updated_user = 'test';
      });

      it('shows correct updated_user on sucess', function() {
        $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(200);
        $httpBackend.flush();

        scope.changeRole(scope.users[0]);
        $httpBackend.flush();

        expect(scope.updated_user).toEqual('alex');
      });

      it('nullifies updated_user on error', function() {
        $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(400);
        $httpBackend.flush();

        scope.changeRole(scope.users[0]);
        $httpBackend.flush();

        expect(scope.updated_user).toEqual('');
      });
    });//<- when admin
  });//<- changeRole()

  describe('function: banUser()', function(){
    describe('when user is not authorized', function(){
      it('does not perform delete request', function(){
        scope.users = constants.USERS;
        currentUser.setUser(scope.users[2]); //roles: USER
        currentUser.addRole('MODERATOR'); //roles: USER, MODERATOR
        scope.users[1].isEnabled = true;
        scope.banUser(scope.users[1]);
        $httpBackend.flush();

        expect(scope.users[1].isEnabled).toBe(true);

        currentUser.setUser(constants.USERS[0]); //set admin as current user
      });
    });

    describe('when user is admin', function(){
      beforeEach(function(){
        $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(200);
        $httpBackend.flush();
      });

      it('changes isEnabled from false to true', function() {
        scope.users[0].isEnabled = true;

        scope.banUser(scope.users[0]);
        $httpBackend.flush();

        expect(scope.users[0].isEnabled).toEqual(false);
      });

      it('changes isEnabled from true to false', function() {
        scope.users[0].isEnabled = false;

        scope.banUser(scope.users[0]);
        $httpBackend.flush();

        expect(scope.users[0].isEnabled).toEqual(true);
      });
    });//<- when admin
  });//<- banUser()

  describe('function: deleteUser()', function() {
    describe('when user is not authorized', function(){
      it('does not perform delete request', function(){
        scope.users = constants.USERS;
        currentUser.setUser(scope.users[2]); //roles: USER
        currentUser.addRole('MODERATOR'); //roles: USER, MODERATOR
        scope.deleteUser(scope.users[1]);
        $httpBackend.flush();

        expect(scope.users.length).toBe(3);

        currentUser.setUser(constants.USERS[0]); //set admin as current user
      });
    });

    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenDELETE(constants.USERS_API + '/sashok').respond(200);
        $httpBackend.flush();
        _.remove(users, users[1]);
      });

      it('deletes user and reloads users from server', function() {
        expect(scope.users.length).toBe(3);

        scope.deleteUser(scope.users[1]);
        $httpBackend.flush();

        expect(scope.users.length).toBe(2);
      });
    });//<- deleteUser() on success

    describe('on response with error', function() {
      beforeEach(function() {
        $httpBackend.whenDELETE(constants.USERS_API + '/sashok').respond(401);
      });

      it('does not delete user from scope', function() {
        scope.deleteUser(scope.users[1]);
        $httpBackend.flush();

        expect(scope.users.length).toBe(3);
      });
    });//<- deleteUser() on error
  });//<- deleteUser()
});//<- UsersController