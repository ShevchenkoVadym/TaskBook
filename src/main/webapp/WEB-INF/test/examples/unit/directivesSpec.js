'use strict';

describe('directives', function() {
  var scope, $httpBackend, current_user;

  //include app module
  beforeEach(module('taskbookApp'));

  //setup back-end
  beforeEach(inject(function($injector) {
    current_user = constants.CURRENT_USER;

    $httpBackend = $injector.get('$httpBackend');

    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);
      
    $httpBackend.whenGET(constants.USERS_API + '/current_user').
          respond(current_user);
  }));

  describe('ng-confirm-click', function() {
    var elementConfirmClick;

    beforeEach(inject(function($rootScope, $compile) {
      scope = $rootScope.$new();
      scope.element = { id: "1" };

      elementConfirmClick =
          '<input type="button" ng-confirm-click="Удалить элемент {{element.id}}?" value="Delete" />';

      elementConfirmClick = $compile(elementConfirmClick)(scope);
      scope.$digest();

      spyOn(window, 'confirm').and.returnValue(true);
    }));
    
    it("calls dialog with defined message", function() {
      elementConfirmClick.click();

      expect(window.confirm).toHaveBeenCalledWith('Удалить элемент ' + scope.element.id + '?');
    });

  });

  describe('equals', function() {
    var elementEquals1, elementEquals2;

    beforeEach(inject(function($rootScope, $compile) {
      scope = $rootScope.$new();

      elementEquals1 =
          '<input type="password" ng-model="user.password" equals="{{password_verify}}" id="password" />';

      elementEquals2 =
          '<input type="password" ng-model="password_verify" equals="{{user.password}}" id="verify" />';

      elementEquals1 = $compile(elementEquals1)(scope);
      elementEquals2 = $compile(elementEquals2)(scope);
      scope.$digest();
    }));
    
    it("applies ng-invalid property when inputs are not equal", function() {
      scope.user = { password: "123" };
      scope.password_verify = "1234";

      scope.$digest();

      expect(elementEquals1.hasClass('ng-invalid-equals')).toBe(true);
      expect(elementEquals2.hasClass('ng-invalid-equals')).toBe(true);
    });

    it("applies ng-valid property when inputs are equal", function() {
      scope.user = { password: "test" };
      scope.password_verify = "test";

      scope.$digest();

      expect(elementEquals1.hasClass('ng-valid-equals')).toBe(true);
      expect(elementEquals2.hasClass('ng-valid-equals')).toBe(true);
    });

  });

});
