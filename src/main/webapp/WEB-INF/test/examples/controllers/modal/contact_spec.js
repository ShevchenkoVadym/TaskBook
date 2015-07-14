/**
 * Created by prima on 07.03.15.
 */
describe('Unit: ContactController', function() {
  var ctrl, scope, $httpBackend, currentUser;

  beforeEach(module('taskbookApp'));

  beforeEach(inject(function($injector, $rootScope, $controller, $location, CurrentUserService) {
    $httpBackend = $injector.get('$httpBackend');

    currentUser = CurrentUserService;

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);
    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    scope = $rootScope.$new();
    scope.user = constants.USERS[0];
    ctrl = $controller('ContactController', {$scope: scope});
  }));

  it('exists', function(){
    expect(scope.submit).toBeDefined();
  });

  describe('submit()', function(){
    describe('on successful response', function(){
      beforeEach(function(){
        $httpBackend.whenPOST(constants.USERS_API + '/feedback').respond(200);
      });

      it('evaluates result', function(){
        expect(scope.result.done).toBeFalsy();
        scope.submit();
        $httpBackend.flush();
        expect(scope.result.success).toBeTruthy();
        expect(scope.result.done).toBeTruthy();
      });

      it('shows loader', function(){
        scope.submit();
        expect(scope.sendInProgress).toBeTruthy();
      });

      it('hides loader on response', function(){
        scope.submit();
        $httpBackend.flush();
        expect(scope.sendInProgress).toBeFalsy();
      });
    });//<- deleteImage() on success

    describe('on response with failure', function(){
      beforeEach(function(){
        $httpBackend.whenPOST(constants.USERS_API + '/feedback').respond(404);
      });

      it('evaluates result', function(){
        expect(scope.result.done).toBeFalsy();
        scope.submit();
        $httpBackend.flush();
        expect(scope.result.success).toBeFalsy();
        expect(scope.result.done).toBeTruthy();
      });

      it('shows loader', function(){
        scope.submit();
        expect(scope.sendInProgress).toBeTruthy();
      });

      it('hides loader on response', function(){
        scope.submit();
        $httpBackend.flush();
        expect(scope.sendInProgress).toBeFalsy();
      });
    });//<- deleteImage() on error
  });//<- deleteImage()
});//<-- ContactController