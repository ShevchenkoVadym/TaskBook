'use strict';

describe('services', function() {
  // load modules
  beforeEach(module('taskbookApp'));
  
  // Test service availability
  it('checks the existence of User service', inject(function(UserService) {
    expect(UserService).toBeDefined();
  }));

  it('checks the existence of CurrentUser service', inject(function(CurrentUserService){
    expect(CurrentUserService).toBeDefined();
  }));

  it('checks the existence of Task service', inject(function(TaskService) {
    expect(TaskService).toBeDefined();
  }));

  it('checks the existence of Country factory', inject(function(Country) {
    expect(Country).toBeDefined();
  }));

  describe('flash', function() {
    it('contains empty message', inject(function(flash) {
      expect(flash.getMessage()).toEqual('');
    }));

    it('checks the existence of FLash factory', inject(function(flash) {
      expect(flash).toBeDefined();
    }));
  });

  describe('UploadService', function() {
    it('checks the existence of fileUpload factory', inject(function(UploadService) {
      expect(UploadService).toBeDefined();
    }));

    it('returns responsePromise', inject(function(UploadService) {
      var responsePromiseExample = { $$state : { status : 0 }, success : Function, error : Function };

      expect(UploadService.send({})).toEqualData(responsePromiseExample);
    }));
  });

  describe('MessageService', function() {
    it('checks the existence of MessageService', inject(function(MessageService) {
      expect(MessageService).toBeDefined();
    }));
  });

  describe('AuthorizationService', function() {
    it('checks the existence of AuthorizationService', inject(function(AuthorizationService) {
      expect(AuthorizationService).toBeDefined();
    }));

    var currentUser, location, task, authorize;

    describe('Task pages authorization', function(){
      beforeEach(inject(function(CurrentUserService, _$location_, AuthorizationService){
        currentUser = CurrentUserService;
        location = _$location_;
        authorize = AuthorizationService;
      }));

      describe('task creators', function(){
        it('authorizes admins', inject(function(){
          currentUser.setUser(constants.USERS[0]);

          expect(authorize.taskCreatorsOnly()).toBe('authorized');
        }));

        it('authorizes mods', inject(function(){
          currentUser.setUser(constants.USERS[1]);

          expect(authorize.taskCreatorsOnly()).toBe('authorized');
        }));

        it('does not authorize users', inject(function(){
          currentUser.setRoles(['USER']);

          expect(authorize.taskCreatorsOnly()).not.toBe('authorized');
        }));

        it('does not authorize guests', inject(function(){
          currentUser.setUser(null);

          expect(authorize.taskCreatorsOnly()).not.toBe('authorized');
        }));

        it('does not authorize blocked users', inject(function(){
          currentUser.setUser(constants.USERS[2]);

          expect(authorize.taskCreatorsOnly()).not.toBe('authorized');
        }));
      });//<-- task creators

      describe('task editors', function(){
        beforeEach(function(){
          task = { author: 'sashok' };
        });

        it('authorizes admins', inject(function(){
          currentUser.setUser(constants.USERS[0]);

          expect(authorize.taskEditorsOnly(task.author)).toBe('authorized');
        }));

        it('authorizes mods', inject(function(){
          currentUser.setUser(constants.USERS[1]);

          expect(authorize.taskEditorsOnly(task.author)).toBe('authorized');
        }));

        it('authorizes task authors', inject(function(){
          currentUser.setUser(constants.USERS[2]);

          expect(authorize.taskEditorsOnly(task.author)).toBe('authorized');
        }));

        it('does not authorize users', inject(function(){
          currentUser.setRoles(['USER']);

          expect(authorize.taskEditorsOnly(task.author)).not.toBe('authorized');
        }));

        it('does not authorize guests', inject(function(){
          currentUser.setUser(null);

          expect(authorize.taskEditorsOnly(task.author)).not.toBe('authorized');
        }));
      });//<-- task editors
    });//<-- task pages authorization
  });//<-- authorization service
});