describe('Unit: LoginController, Service: CurrentUserService', function() {
  
  beforeEach(module('taskbookApp'));

  var example, ctrl, scope, rootScope, $httpBackend, currentUser;

  beforeEach(inject(function($injector, $rootScope, $controller, CurrentUserService) {
    $httpBackend = $injector.get('$httpBackend');
      
    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(401, {});
    $httpBackend.whenGET('partials/home.html').respond(200);
    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    example = constants.CURRENT_USER;
 
    scope = $rootScope.$new();
    rootScope = $rootScope;
    currentUser = CurrentUserService;
    ctrl = $controller('LoginController', {$scope: scope});
  }));

  describe('function: recoverPasswd(email)', function() {
    var message;

    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.USERS_API + '/recover?email=test@example.com').respond(200, { data: message });
      });

      it('removes error message', function() {
        scope.formError = "Error";

        scope.recoverPassword('test@example.com');
        $httpBackend.flush();

        expect(scope.formError).toBeUndefined();
      });

      it('shows loader', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.recoverPassword('test@example.com');
        expect(scope.show_loader).toBeTruthy();
        $httpBackend.flush();

        expect(scope.show_loader).toBeFalsy();
      });
    });//<-- recoverPasswd() on success

    describe ('on response with error', function() {
      beforeEach(function(){
        message = "Пользователь с таким e-mail не найден";
        $httpBackend.whenPOST(constants.USERS_API + '/recover?email=test@example.com').respond(400, message);
      });

      it('removes info message', function() {
        scope.formInfo = "Info";

        scope.recoverPassword('test@example.com');
        $httpBackend.flush();

        expect(scope.formInfo).toBeUndefined();
      });

      it('shows correct error message', function() {
        expect(scope.formError).toBeUndefined();

        scope.recoverPassword('test@example.com');
        $httpBackend.flush();

        expect(scope.formError).toEqual(message);
      });

      it('shows and hides loader', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.recoverPassword('test@example.com');
        expect(scope.show_loader).toBeTruthy();
        $httpBackend.flush();

        expect(scope.show_loader).toBeFalsy();
      });
    });//<-- recoverPasswd() on error
  });//<-- recoverPasswd()

  describe('function: login(user)', function() {
    beforeEach(function(){
      scope.user_attempt = {
        username: "test",
        password: "12345"
      };
    });

    describe('on successful response', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.USERS_API + '/login').respond(200);
        $httpBackend.whenPOST('j_spring_security_check').respond(200);
        $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(example);
        
        $httpBackend.flush();
      });

      it('removes error message', function() {
        scope.formError = "Error";

        scope.signIn(scope.user_attempt);
        $httpBackend.flush();

        expect(scope.formError).toBeUndefined();
      });

      it('reloads current user', function() {
        expect(scope.currentUser.isLoggedIn()).toBe(false);

        scope.signIn(scope.user_attempt);
        $httpBackend.flush();

        expect(scope.currentUser.isLoggedIn()).toBe(true);
      });

      it('shows and hides loader', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.signIn(scope.user_attempt);
        expect(scope.show_loader).toBeTruthy();
        $httpBackend.flush();

        expect(scope.show_loader).toBeFalsy();
      });
    });//<-- login() on success

    describe ('when login and password are incorrect', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.USERS_API + '/login').respond(417, 'Неверная комбинация логина и пароля.');
        $httpBackend.whenPOST('j_spring_security_check').respond(401);
        $httpBackend.flush();
      });

      it('shows error message', function() {
        expect(scope.formError).toBeUndefined();

        scope.signIn(scope.user_attempt);
        $httpBackend.flush();

        expect(scope.formError).toEqual("Неверная комбинация логина и пароля.");
      });

      it('shows and hides loader', function(){
        expect(scope.show_loader).toBeFalsy();

        scope.signIn(scope.user_attempt);
        expect(scope.show_loader).toBeTruthy();
        $httpBackend.flush();

        expect(scope.show_loader).toBeFalsy();
      });
    });

    describe ('when account is blocked', function() {
      beforeEach(function(){
        $httpBackend.whenPOST(constants.USERS_API + '/login').respond(401, 'Аккаунт заблокирован.');
        $httpBackend.whenPOST('j_spring_security_check').respond(401);
        $httpBackend.flush();
      });

      it('shows error message', function() {
        expect(scope.formError).toBeUndefined();

        scope.signIn(scope.user_attempt);
        $httpBackend.flush();

        expect(scope.formError).toEqual("Аккаунт заблокирован.");
        expect(scope.show_loader).toBeFalsy();
      });
    });//<-- login() on error
  });//<-- login()

  describe('function: logout(message)', function() {
    beforeEach(function(){
      currentUser.setUser(example);
    });

    it('reloads_current_user', function() {
      expect(currentUser.getUsername()).toEqual('alex');

      $httpBackend.whenGET(constants.USERS_API + 'current_user').respond(401, {});
      $httpBackend.whenGET('/j_spring_security_logout').respond(200);
      $httpBackend.flush();

      scope.logout('До свидания!');
      $httpBackend.flush();

      expect(currentUser.getUsername()).toBeUndefined();
      expect(scope.isLoggedIn()).toBe(false);
    });
  });//<-- logout()

  describe('function: equalsTo(user)', function() {
    beforeEach(function(){
      currentUser.setUser(example);
    });

    it('shows if current_user is equal to comparable user', function() {
      expect(currentUser.equalsTo('aLeX')).toBe(true);
      expect(currentUser.equalsTo('mary')).toBe(false);
    });
  });//<-- equalsTo()

  describe('function: hasRole(role)', function() {
    beforeEach(function(){
      currentUser.setUser(example);
    });

    it('shows if current_user has appropriate role', function() {
      //example user is admin
      expect(currentUser.isAdmin()).toBe(true);
      expect(currentUser.hasRole(['user'])).toBe(false);
      expect(currentUser.isModerator()).toBe(false);
    });

    it('returns false is current_user is undefined', function() {
      currentUser.logout();

      expect(currentUser.isAdmin()).toBe(false);
      expect(currentUser.hasRole(['user'])).toBe(false);
      expect(currentUser.isModerator()).toBe(false);
    });
  });//<-- hasRole()
});//<-- LoginController