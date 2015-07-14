/**
 * Created by prima on 25.03.15.
 */
describe('Unit: RegistrationController', function() {
    beforeEach(module('taskbookApp'));

    var ctrl, scope, $httpBackend, users, tasks;

    beforeEach(inject(function($injector, $rootScope, $controller) {
        $httpBackend = $injector.get('$httpBackend');

        $httpBackend.whenGET(constants.USERS_API + '/current_user').respond({});

        scope = $rootScope.$new();
        ctrl = $controller('RegistrationController', {$scope: scope});
    }));

    it('exists', function() {
        expect(scope.signUp).toBeDefined();
    });

});