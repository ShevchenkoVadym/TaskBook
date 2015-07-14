/**
 * Created by prima on 27.03.15.
 */
describe('Unit: AboutController', function() {
    beforeEach(module('taskbookApp'));

    var ctrl, scope, $httpBackend, users, tasks;

    beforeEach(inject(function($injector, $rootScope, $controller) {
        $httpBackend = $injector.get('$httpBackend');

        $httpBackend.whenGET(constants.USERS_API + '/current_user').respond({});
        $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);
        $httpBackend.whenGET('data/developers.json').respond(['test']);
        $httpBackend.whenGET(constants.TASKS_API + '?page=0&per_page=30').respond(constants.TASKS_OBJECT);

        scope = $rootScope.$new();
        ctrl = $controller('AboutController', {$scope: scope});
    }));

    it('compiles', function() {
        expect(scope.openContactForm).toBeDefined();
    });

    it('gets tasks and developers', function(){
        expect(scope.tasks).toEqualData([]);
        expect(scope.developers).toEqualData([]);

        $httpBackend.flush();

        expect(scope.tasks).toEqualData(constants.TASKS);
        expect(scope.developers).toEqualData(['test']);
    });

});