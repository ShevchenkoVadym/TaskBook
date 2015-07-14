/**
 * Created by prima on 04.04.15.
 */
describe('Unit: NotFoundController', function() {
    beforeEach(module('taskbookApp'));

    var ctrl, scope, $httpBackend;

    beforeEach(inject(function($injector, $rootScope, $controller) {
        $httpBackend = $injector.get('$httpBackend');

        $httpBackend.whenGET(constants.USERS_API + '/current_user').respond({});

        scope = $rootScope.$new();
        ctrl = $controller('NotFoundController', {$scope: scope});
    }));

    it('compiles', function() {
        expect(scope.openContactForm).toBeDefined();
    });

});