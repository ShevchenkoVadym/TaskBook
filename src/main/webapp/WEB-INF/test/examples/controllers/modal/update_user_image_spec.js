/**
 * Created by prima on 07.03.15.
 */
describe('Unit: UpdateUserImageController', function() {
  var ctrl, scope, $httpBackend, currentUser;

  beforeEach(module('taskbookApp'));

  beforeEach(inject(function($injector, $rootScope, $controller, $location, CurrentUserService) {
    $httpBackend = $injector.get('$httpBackend');

    currentUser = CurrentUserService;

    $httpBackend.whenGET(constants.USERS_API + '/current_user').respond(constants.CURRENT_USER);
    $httpBackend.whenGET('data/messages.json').respond(constants.MESSAGES);

    scope = $rootScope.$new();
    scope.user = constants.USERS[0];
    scope.closeThisDialog = function(){};
    ctrl = $controller('UpdateUserImageController', {$scope: scope});
  }));

  it('exists', function(){
    expect(scope.deleteImage).toBeDefined();
    expect(scope.uploadUserImage).toBeDefined();
  });

  describe('uploadUserImage', function(){
    describe('when not valid', function(){

      it('verifies file existence', function(){
        scope.uploadUserImage();
        $httpBackend.flush();
        expect(scope.formError).toBeDefined();
      });

      it('accepts jpg/jpeg, png, gif', function(){
        $httpBackend.whenPOST('http://deviantsart.com').respond({ "url" : "http://deviantsart.com/3j4s90a.jpg" });
        $httpBackend.whenPUT('rest/api/v1/users/alex').respond(200);

        var correct = ['img.jpg', 'img.jpeg', 'img.png', 'img.gif'];
        var file = { name: '' };

        _.each(correct, function(filename){
          file.name = filename;
          scope.uploadUserImage(file);
          expect(scope.formError).not.toBeDefined();
        });
      });

      it('rejects incorrect formats', function(){
        var incorrect = ['img.txt', 'img', 'img.js', 'img.exe'];
        var file = { name: '' };

        _.each(incorrect, function(filename){
          file.name = filename;
          scope.uploadUserImage(file);
          expect(scope.formError).toBeDefined();
        });
      });
    });

    describe('on success', function(){
      var file = {name: 'test.png'};

      beforeEach(function(){
        $httpBackend.whenPOST('http://deviantsart.com').respond({ "url" : "http://deviantsart.com/3j4s90a.jpg" });
        $httpBackend.whenPUT('rest/api/v1/users/alex').respond(200);
      });

      it('updates user image', function(){
        scope.uploadUserImage(file);
        $httpBackend.flush();
        expect(scope.user.imageUrl).toEqual('http://deviantsart.com/3j4s90a.jpg');
      });

      it('shows loader', function(){
        expect(scope.show_loader).toBeFalsy();
        scope.uploadUserImage(file);
        expect(scope.show_loader).toBeTruthy();
      });

      it('hides loader on response', function(){
        expect(scope.show_loader).toBeFalsy();
        scope.uploadUserImage(file);
        $httpBackend.flush();

        setTimeout(function(){
          expect(scope.show_loader).toBeFalsy();
        }, 3000);
      });
    });//<- uploaderUserImage() success

    describe('on error', function(){
      var file = {name: 'test.png'};

      beforeEach(function(){
        $httpBackend.whenPOST('http://deviantsart.com').respond(401);
      });

      it('hides loader on response', function(){
        expect(scope.show_loader).toBeFalsy();
        scope.uploadUserImage(file);
        $httpBackend.flush();
        expect(scope.show_loader).toBeFalsy();
      });
    });//<- uploaderUserImage() success
  });//<- uplloadUserImage()

  describe('delete image', function(){
     describe('on successful response', function(){
       beforeEach(function(){
         $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(200);
       });

       it('removes user image', function(){
         scope.user.imageUrl = 'test.png';
         scope.deleteImage();
         $httpBackend.flush();
         expect(scope.user.imageUrl).toEqual('');
       });

       it('removes formError', function(){
         scope.formError = 'test';
         scope.deleteImage();
         expect(scope.formError).toBeUndefined();
       });

       it('shows loader', function(){
         scope.deleteImage();
         expect(scope.show_loader).toBeTruthy();
       });

       it('hides loader on response', function(){
         scope.deleteImage();
         $httpBackend.flush();
         expect(scope.show_loader).toBeFalsy();
       });
     });//<- deleteImage() on success

    describe('on response with failure', function(){
      beforeEach(function(){
        $httpBackend.whenPUT(constants.USERS_API + '/alex').respond(404);
      });

      it('does not remove user image', function(){
        scope.user.imageUrl = 'test.png';
        scope.deleteImage();
        $httpBackend.flush();
        expect(scope.user.imageUrl).toEqual('test.png');
      });

      it('removes formError', function(){
        scope.formError = 'test';
        scope.deleteImage();
        expect(scope.formError).toBeUndefined();
      });

      it('shows loader', function(){
        scope.deleteImage();
        expect(scope.show_loader).toBeTruthy();
      });

      it('hides loader on response', function(){
        scope.deleteImage();
        $httpBackend.flush();
        expect(scope.show_loader).toBeFalsy();
      });
    });//<- deleteImage() on error
  });//<- deleteImage()
});//<-- UpdateUserImageController