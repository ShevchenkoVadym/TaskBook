(function() {
  'use strict';
  angular.module('taskbookApp').constant('HOST', '').constant('BASE_EDITOR', {
    lineWrapping: true,
    lineNumbers: true,
    tabSize: 2,
    smartIndent: true,
    mode: 'text/x-java',
    height: 'dynamic',
    extraKeys: {
      "Ctrl-Space": "autocomplete"
    }
  });

  'use strict';

  angular.module('taskbookApp').controller('AboutController', [
    '$scope', '$location', 'ModalService', 'TaskService', 'Developers', function($scope, $location, ModalService, TaskService, Developers) {
      var getLastTaskCount, randomTask;
      $scope.tasks = [];
      $scope.developers = [];
      Developers.query({}, function(res) {
        return $scope.developers = _.shuffle(res);
      });
      TaskService.query({
        page: 0,
        per_page: 30
      }, function(res) {
        $scope.totalTasks = res.total || 0;
        return $scope.tasks = res.content || [];
      });
      getLastTaskCount = function() {
        var last;
        last = String($scope.totalTasks).slice(-1);
        if (last === 1) {
          return 1;
        } else if (_.contains([2, 3, 4], last)) {
          return 2;
        } else {
          return 3;
        }
      };
      randomTask = function() {
        var task;
        if (!$scope.tasks.length) {
          return $location.path('tasks');
        } else {
          task = $scope.tasks[Math.floor(Math.random() * $scope.tasks.length)];
          return $location.path('tasks/' + task.id);
        }
      };
      $scope.getLastTaskCount = getLastTaskCount;
      $scope.randomTask = randomTask;
      return $scope.openContactForm = ModalService.openContactForm;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('HomePageController', [
    '$scope', 'UserService', 'TaskService', function($scope, userService, taskService) {
      var defaultValues;
      defaultValues = {
        users: [],
        tasks: []
      };
      angular.extend($scope, defaultValues);
      userService.query({
        page: 0,
        per_page: 10
      }, function(object) {
        return $scope.users = object.content;
      });
      return taskService.query({
        page: 0,
        per_page: 10
      }, (function(object) {
        return $scope.tasks = object.content;
      }), null);
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('LoginController', [
    '$scope', '$http', '$location', '$route', 'ngDialog', 'UserService', 'CurrentUserService', 'MessageService', function($scope, $http, $location, $route, ngDialog, userService, currentUser, message) {
      var recoverPassword, signIn;
      $scope.show_loader = false;
      signIn = function(userDetails) {
        $scope.show_loader = true;
        return currentUser.login(userDetails, (function(res) {
          $scope.formError = void 0;
          ngDialog.closeAll();
          currentUser.setUser(res);
          if ($location.path() === '/login') {
            return $location.url('/users/' + userDetails.username);
          } else if ($location.path() === '/users/' + userDetails.username) {
            return setTimeout((function() {
              return $route.reload();
            }), 1000);
          }
        }), function(error) {
          console.warn(error);
          if (error.status === 503) {
            return $scope.formError = message.get('server_unavailable');
          } else {
            return $scope.formError = error.data;
          }
        }).$promise["finally"](function() {
          return $scope.show_loader = false;
        });
      };
      recoverPassword = function(userEmail) {
        $scope.show_loader = true;
        return userService.recover({
          email: userEmail
        }, (function() {
          $scope.formError = void 0;
          return $scope.formInfo = message.get('started_password_recovery');
        }), function(error) {
          console.warn(error);
          $scope.formInfo = void 0;
          if (error.status === 503) {
            $scope.formError = message.get('server_unavailable');
          } else {
            $scope.formError = error.data;
          }
        }).$promise["finally"](function() {
          return $scope.show_loader = false;
        });
      };
      $scope.signIn = signIn;
      return $scope.recoverPassword = recoverPassword;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('ContactController', [
    '$scope', 'UserService', 'MessageService', 'CurrentUserService', function($scope, userService, message, currentUser) {
      var defaultValues, submit;
      defaultValues = {
        sendInProgress: false,
        result: {
          message: '',
          success: false,
          done: false
        },
        formData: {}
      };
      submit = function() {
        var feedback;
        $scope.sendInProgress = true;
        feedback = {
          requester: $scope.formData.inputEmail,
          subject: $scope.formData.inputSubject,
          message: $scope.formData.inputMessage
        };
        return userService.sendFeedback(feedback).$promise.then((function() {
          return $scope.result = {
            message: message.get('feedback_success'),
            success: true,
            done: true
          };
        }), function() {
          return $scope.result = {
            message: message.get('feedback_error'),
            success: false,
            done: true
          };
        })["finally"](function() {
          return $scope.sendInProgress = false;
        });
      };
      angular.extend($scope, defaultValues);
      if (currentUser.isLoggedIn()) {
        $scope.formData.inputEmail = currentUser.getEmail();
      }
      return $scope.submit = submit;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('UpdateUserImageController', [
    '$scope', '$http', 'UserService', 'MessageService', 'UploadService', function($scope, $http, User, message, uploadService) {
      var deleteImage, uploadUserImage;
      uploadUserImage = function(file) {
        var reg;
        $scope.show_loader = true;
        reg = /^.*\.(jpg|jpeg|gif|png)$/;
        if (file === void 0 || !reg.test(file.name.toLowerCase())) {
          $scope.formError = message.get('incorrect_img_format') || 'error';
          $scope.show_loader = false;
          return $scope.userImg = void 0;
        } else {
          return uploadService.send(file).success(function(res) {
            $scope.user.imageUrl = res.url;
            return User.update({
              id: $scope.user.username
            }, $scope.user, (function() {
              return $scope.formError = void 0;
            }), function(error) {
              return console.warn(error);
            }).$promise["finally"](function() {
              return $scope.show_loader = false;
            });
          }).error(function(err) {
            console.warn(err);
            $scope.formError = message.get('incorrect_img_format') || 'error';
            return $scope.show_loader = false;
          });
        }
      };
      deleteImage = function() {
        var temp;
        temp = $scope.user.imageUrl;
        $scope.formError = void 0;
        $scope.show_loader = true;
        $scope.user.imageUrl = '';
        return User.update({
          id: $scope.user.username
        }, $scope.user, (function() {
          $scope.user.imageUrl = '';
          return $scope.closeThisDialog();
        }), function(error) {
          $scope.user.imageUrl = temp;
          $scope.formError = message.get('unknown_error');
          return console.warn(error);
        }).$promise["finally"](function() {
          return $scope.show_loader = false;
        });
      };
      $scope.uploadUserImage = uploadUserImage;
      return $scope.deleteImage = deleteImage;
    }
  ]);


  /**
   * Created by prima on 02.04.15.
   */

  angular.module('taskbookApp').controller('UpdateUserController', [
    '$q', '$scope', 'UserService', 'MessageService', 'CurrentUserService', function($q, $scope, User, message, currentUser) {
      var bcrypt, crypt, performUpdate, updateUser;
      bcrypt = new bCrypt;
      updateUser = function(userDetails, current_password, new_password) {
        var salt;
        if (!currentUser.equalsTo($scope.user.username)) {
          return;
        }
        if (current_password !== void 0 && current_password.length > 0) {
          if (new_password !== void 0 && new_password.length > 0) {
            salt = null;
            crypt(current_password, currentUser.getPasswordHash()).then(function(pass) {
              if (pass === currentUser.getPasswordHash()) {
                salt = bcrypt.gensalt(10);
                return crypt(new_password, salt).then(function(pass) {
                  userDetails.password = pass;
                  performUpdate(userDetails);
                });
              } else {
                $scope.formError = message.get('incorrect_current_password');
                return console.log($scope.formError);
              }
            });
          } else {
            $scope.formError = message.get('empty_new_password');
            console.log($scope.formError);
          }
        } else {
          performUpdate(userDetails);
        }
      };
      crypt = function(password, salt) {
        var deferred, encrypt;
        deferred = $q.defer();
        encrypt = function(call) {
          return bcrypt.hashpw(password, salt, function(data) {
            call(data);
          });
        };
        encrypt(function(pass) {
          return deferred.resolve(pass);
        });
        return deferred.promise;
      };
      performUpdate = function(userDetails) {
        return User.update({
          id: userDetails.username
        }, userDetails, (function() {
          $scope.formError = void 0;
          return $scope.closeThisDialog();
        }), function(error) {
          console.warn(error);
          if (error.status === 503) {
            return $scope.formError = message.get('server_unavailable');
          } else {
            return $scope.formError = error.data;
          }
        });
      };
      $scope.updateUser = updateUser;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('NotFoundController', [
    '$scope', 'ModalService', function($scope, ModalService) {
      return $scope.openContactForm = ModalService.openContactForm;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('PlaygroundController', ['$scope', function($scope) {}]);

  'use strict';

  angular.module('taskbookApp').controller('RegistrationController', [
    '$scope', '$location', 'ngDialog', 'UserService', 'MessageService', function($scope, $location, ngDialog, User, message) {
      var signUp;
      signUp = function(newUserDetails) {
        var bcrypt, newUser, next, salt;
        newUser = angular.copy(newUserDetails);
        bcrypt = new bCrypt;
        salt = bcrypt.gensalt(10);
        next = function() {
          return User.save(newUser).$promise.then((function() {
            var msg;
            $scope.formError = void 0;
            ngDialog.closeAll();
            msg = message.get('successful_reg');
            if ($location.path() === '/login') {
              $scope.formNewUser.$setPristine();
              $scope.new_user = '';
              $scope.password_verify = '';
              return $scope.formInfo = msg;
            } else {
              $scope.flash.clearQueue();
              $scope.flash.setMessage(msg);
              return $location.url('/login');
            }
          }), function(error) {
            console.warn(error);
            $scope.formInfo = void 0;
            if (error.status === 503) {
              return $scope.formError = message.get('server_unavailable');
            } else {
              return $scope.formError = error.data;
            }
          });
        };
        if (google.loader.ClientLocation) {
          newUser.country = google.loader.ClientLocation.address.country;
        } else {
          newUser.country = '';
        }
        return bcrypt.hashpw(newUser.password, salt, function(data) {
          newUser.password = data;
          return next();
        });
      };
      return $scope.signUp = signUp;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('TaskDetailController', [
    '$scope', '$routeParams', '$location', 'TaskService', 'ngDialog', 'CurrentUserService', 'MessageService', 'AuthorizationService', 'ModalService', 'BASE_EDITOR', function($scope, $routeParams, $location, taskService, ngDialog, currentUser, message, authorize, ModalService, BASE_EDITOR) {
      var check, defaultValues, deleteTask, editorsOnly, hasDisliked, hasLiked, vote;
      defaultValues = {
        task: {},
        editorOptions: angular.copy(BASE_EDITOR),
        disqus_ready: false,
        show_loader: false,
        condStyle: {
          'min-height': '30px'
        }
      };
      angular.extend($scope, defaultValues);
      $scope.editorOptions.autofocus = true;
      taskService.get({
        id: $routeParams.id,
        fields: ['id', 'tests', 'templateCode', 'taskName', 'tags', 'testEnvironment', 'condition', 'author', 'level', 'rating', 'likedBy', 'dislikedBy', 'votersAmount', 'lifeStage']
      }, (function(task) {
        var length;
        if (task.lifeStage !== 'APPROVED') {
          authorize.taskEditorsOnly(task.author);
        }
        $scope.task = task;
        $scope.disqus_url = 'http://javapractice.disqus.com/' + task.id;
        $scope.disqus_ready = true;
        length = $scope.task.condition.length / 50;
        length = length + ($scope.task.condition.match(/\n/g) || []).length;
        length = Math.ceil(length);
        return $scope.condStyle = {
          'min-height': length * 11 + 'px'
        };
      }), function(error) {
        console.warn(error);
        $scope.flash.clearQueue();
        $scope.flash.setMessage(message.get('task_not_found'));
        return $location.path('/tasks');
      });
      check = function(template) {
        var result;
        $scope.show_loader = true;
        $scope.task.sourceCode = template;
        result = void 0;
        return taskService.check({
          statistic: true
        }, $scope.task, (function(res) {
          result = res.map;
          return result.random = Math.floor((Math.random() * 3) + 1);
        }), function(error) {
          console.warn(error);
          return result = error.data.map || {
            compilation: 'NO_ANSWER',
            answer: error.data
          };
        }).$promise["finally"](function() {
          ModalService.openTaskTestResult(result);
          return $scope.show_loader = false;
        });
      };
      hasLiked = function() {
        return _.contains($scope.task.likedBy, currentUser.getUsername());
      };
      hasDisliked = function() {
        return _.contains($scope.task.dislikedBy, currentUser.getUsername());
      };
      vote = function(delta) {
        return taskService.vote({
          id: $scope.task.id,
          username: currentUser.getUsername(),
          delta: delta
        }, {}, (function() {
          $scope.task.rating = $scope.task.rating + delta;
          if (delta > 0) {
            $scope.task.likedBy.push(currentUser.getUsername());
          } else {
            $scope.task.dislikedBy.push(currentUser.getUsername());
          }
        }), function(error) {
          return console.warn(error);
        });
      };
      deleteTask = function(task) {
        if (!currentUser.isAdmin()) {
          return;
        }
        return taskService.destroy({
          id: task.id
        }, (function() {
          $scope.flash.clearQueue();
          $scope.flash.setMessage(message.get('successful_task_delete'));
          return $location.path('/tasks');
        }), null);
      };
      editorsOnly = function() {
        return currentUser.isAdmin() || currentUser.isModerator() || $scope.task.author === currentUser.getUsername();
      };
      $scope.isEnabled = currentUser.getEnabled;
      $scope.isNonReadOnly = currentUser.getNonReadOnly;
      $scope.taskEditorsOnly = editorsOnly;
      $scope.check = check;
      $scope.hasLiked = hasLiked;
      $scope.hasDisliked = hasDisliked;
      $scope.vote = vote;
      $scope.deleteTask = deleteTask;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('TaskEditController', [
    '$scope', '$routeParams', '$location', 'TaskService', 'ngDialog', 'CurrentUserService', 'AuthorizationService', 'ModalService', 'LoaderService', 'BASE_EDITOR', function($scope, $routeParams, $location, taskService, ngDialog, currentUser, authorize, ModalService, loaderService, BASE_EDITOR) {
      var check, defaultValues, fields, publish, task_id;
      loaderService.toggle();
      defaultValues = {
        show_global_loader: false,
        task_state: 'new',
        show_publish_loader: false,
        show_test_loader: false,
        editorOptions: BASE_EDITOR,
        libs: ['JAVA_JUNIT', 'JAVA_TESTNG'],
        levels: ['BEGINNER', 'JUNIOR', 'MIDDLE', 'SENIOR'],
        lifeStages: ['DRAFT', 'VERIFICATION', 'APPROVED', 'REJECTED', 'HIDDEN'],
        tags: []
      };
      angular.extend($scope, defaultValues);
      fields = ['id', 'sourceCode', 'tests', 'templateCode', 'taskName', 'tags', 'testEnvironment', 'condition', 'level', 'author', 'lifeStage'];
      task_id = $routeParams.id;
      taskService.tagsQuery({}, function(res) {
        return $scope.tags = res;
      }, function(error) {
        return console.warn(error);
      });
      if (!task_id) {
        $scope.task = {
          author: currentUser.getUsername(),
          testEnvironment: 'JAVA_JUNIT',
          level: 'BEGINNER',
          lifeStage: currentUser.isAdmin() || currentUser.isModerator() ? 'APPROVED' : 'VERIFICATION',
          tags: [],
          topLevelCommentsId: []
        };
        loaderService.toggle();
      } else {
        $scope.show_global_loader = true;
        taskService.get({
          id: task_id,
          fields: fields
        }, (function(task) {
          authorize.taskEditorsOnly(task.author);
          $scope.task = task;
          return $scope.task_state = 'existed';
        }), function(error) {
          console.warn(error);
          return $location.path('/tasks');
        }).$promise["finally"](function() {
          return loaderService.toggle();
        });
      }
      publish = function(task) {
        var result;
        $scope.show_publish_loader = true;
        result = void 0;
        if (!task.id) {
          return taskService.save(task, (function(res) {
            $scope.task = res.task;
            result = res.map;
            return result.statusCode = 201;
          }), function(error) {
            console.warn(error);
            return result = {
              compilation: 'NO_ANSWER',
              answer: error.data,
              statusCode: error.status
            };
          }).$promise["finally"](function() {
            ModalService.openTaskTestResult(result);
            return $scope.show_publish_loader = false;
          });
        } else {
          result = void 0;
          return taskService.update(task, (function(res) {
            $scope.task = res;
            return result = {
              compilation: 'OK',
              tests: 'OK',
              statusCode: 200
            };
          }), function(error) {
            console.warn(error);
            return result = error.data.map || {
              compilation: 'NO_ANSWER',
              answer: error.data
            };
          }).$promise["finally"](function() {
            ModalService.openTaskTestResult(result);
            return $scope.show_publish_loader = false;
          });
        }
      };
      check = function(task) {
        var result;
        $scope.show_test_loader = true;
        $scope.message = void 0;
        result = void 0;
        return taskService.check({
          statistic: false
        }, task, (function(res) {
          result = res.map;
          return result.random = Math.floor(Math.random() * 3 + 1);
        }), function(error) {
          console.warn(error);
          return result = error.data.map || {
            compilation: 'NO_ANSWER',
            answer: error.data
          };
        }).$promise["finally"](function() {
          ModalService.openTaskTestResult(result);
          return $scope.show_test_loader = false;
        });
      };
      $scope.check = check;
      $scope.publish = publish;
      $scope.isAdmin = currentUser.isAdmin;
      return $scope.isRegular = currentUser.isRegular;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('TasksController', [
    '$q', '$scope', '$location', 'TaskService', 'CurrentUserService', function($q, $scope, $location, taskService, currentUser) {
      var defaultValues, deleteTask, init, isSolved, pageChanged, requestTasks, sortBy;
      defaultValues = {
        tasks: [],
        itemsPerPage: 10,
        currentPage: 1,
        tags: [],
        levels: [],
        predicate: 'rating',
        reverse: true,
        filter: {},
        multipleTags: {
          tags: []
        },
        multipleLevels: {
          levels: []
        },
        solvedIds: [],
        solvedCriteria: 'all'
      };
      init = function() {
        var level_params, levels, queryPromises, tag_params, tags;
        tags = void 0;
        levels = void 0;
        tag_params = $location.search().tags;
        level_params = $location.search().levels;
        if (tag_params !== 'undefined') {
          if (typeof tag_params === 'string') {
            tags = [tag_params];
          } else {
            tags = tag_params;
          }
          $scope.multipleTags.tags = tags;
        }
        if (level_params !== 'undefined') {
          if (typeof level_params === 'string') {
            levels = [level_params];
          } else {
            levels = level_params;
          }
          $scope.multipleLevels.levels = levels;
        }
        queryPromises = [
          taskService.levelsQuery({}, (function(res) {
            return $scope.levels = res;
          }), function(error) {
            return console.warn(error);
          }).$promise, taskService.tagsQuery({}, (function(res) {
            return $scope.tags = res;
          }), function(error) {
            return console.warn(error);
          }).$promise
        ];
        return $q.all(queryPromises).then(function() {
          requestTasks($scope.currentPage, $scope.itemsPerPage);
          if (currentUser.isLoggedIn()) {
            return taskService.solvedByUserIds({
              name: currentUser.getUsername()
            }, (function(res) {
              return $scope.solvedIds = res;
            }), function(error) {
              return console.warn(error);
            });
          }
        });
      };
      angular.extend($scope, defaultValues);
      requestTasks = function(pageNum, perPage) {
        var inverse, sort, username;
        sort = $scope.reverse ? '-' + $scope.predicate : $scope.predicate;
        inverse = null;
        username = currentUser.getUsername();
        switch ($scope.solvedCriteria) {
          case 'solved':
            inverse = false;
            break;
          case 'not_solved':
            inverse = true;
            break;
          default:
            username = null;
        }
        return taskService.query({
          page: pageNum - 1,
          per_page: perPage,
          sort: sort,
          levels: $scope.multipleLevels.levels,
          tags: $scope.multipleTags.tags,
          username: username,
          inverse: inverse
        }, (function(object) {
          $scope.tasks = object.content || [];
          $scope.totalItems = object.total || 0;
          if (!object.pageable) {
            return $scope.currentPage = 1;
          } else {
            return $scope.currentPage = object.pageable.page + 1;
          }
        }), function(error) {
          return console.warn(error);
        });
      };
      init();
      pageChanged = function(currentPage) {
        $scope.currentPage = currentPage || 1;
        return requestTasks($scope.currentPage, $scope.itemsPerPage);
      };
      deleteTask = function(task) {
        if (!currentUser.isAdmin()) {
          return;
        }
        return taskService.destroy({
          id: task.id
        }, (function() {
          var index;
          index = $scope.tasks.indexOf(task);
          $scope.tasks.splice(index, 1);
          return pageChanged($scope.currentPage);
        }), null);
      };
      sortBy = function(predicate) {
        $scope.predicate = predicate;
        $scope.reverse = !$scope.reverse;
        requestTasks($scope.currentPage, $scope.itemsPerPage);
      };
      isSolved = function(id) {
        if (!currentUser.isLoggedIn()) {
          return false;
        }
        return _.contains($scope.solvedIds, id);
      };
      $scope.deleteTask = deleteTask;
      $scope.pageChanged = pageChanged;
      $scope.sortBy = sortBy;
      $scope.isAuthorized = function() {
        return currentUser.hasRole(['MODERATOR', 'ADMIN']);
      };
      $scope.isAdmin = currentUser.isAdmin;
      return $scope.isSolved = isSolved;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('UserProfileController', [
    '$q', '$scope', '$routeParams', '$http', '$location', 'UserService', 'TaskService', 'ModalService', 'CurrentUserService', 'Country', 'LoaderService', function($q, $scope, $routeParams, $http, $location, User, taskService, modalService, currentUser, Country, loaderService) {
      var defaultValues, fields, pageChanged, requestTasks, sortBy, updateDialog, updateImageDialog, user_name;
      loaderService.toggle();
      defaultValues = {
        tasksAdded: [],
        tasksSolved: [],
        itemsPerPage: 10,
        currentPageAdded: 1,
        currentPageSolved: 1,
        predicate: 'rating',
        reverse: true,
        tags: [],
        show_loader: false
      };
      angular.extend($scope, defaultValues);
      user_name = $routeParams.username;
      fields = ['id', 'taskName', 'rating', 'author', 'level', 'creationDate'];
      User.get({
        id: user_name
      }, (function(user) {
        $scope.user = user;
      }), function(error) {
        console.warn(error);
        $scope.flash.clearQueue();
        $scope.flash.setMessage(error.data);
        $location.path('/users');
      }).$promise["finally"](function() {
        return loaderService.toggle();
      });
      if (currentUser.equalsTo(user_name)) {
        $scope.profileTemplateUrl = 'partials/users/_as_user.html';
      } else {
        $scope.profileTemplateUrl = 'partials/users/_as_guest.html';
      }
      User.getUserTags({
        id: user_name
      }, (function(res) {
        var key;
        for (key in res) {
          if (key === '$promise') {
            break;
          }
          $scope.tags.push({
            name: key,
            count: res[key]
          });
        }
      }), function(error) {
        return console.warn(error);
      });
      requestTasks = function(pageNum, type) {
        var params, sort;
        sort = $scope.reverse ? '-' + $scope.predicate : $scope.predicate;
        params = {
          username: user_name,
          page: pageNum - 1,
          per_page: $scope.itemsPerPage,
          sort: sort,
          fields: fields
        };
        switch (type) {
          case 'added':
            return taskService.addedByUser(params, (function(res) {
              if (res.content !== void 0 && res.content.length > 0) {
                $scope.tasksAdded = res.content;
                $scope.totalItemsAdded = res.total;
                $scope.currentPageAdded = res.pageable.page + 1;
              }
            }), function(error) {
              return console.warn(error);
            });
          case 'solved':
            return taskService.query(params, (function(res) {
              if (res.content !== void 0 && res.content.length > 0) {
                $scope.tasksSolved = res.content;
                $scope.totalItemsSolved = res.total;
                $scope.currentPageSolved = res.pageable.page + 1;
              }
            }), function(error) {
              return console.warn(error);
            });
        }
      };
      requestTasks($scope.currentPageAdded, 'added');
      requestTasks($scope.currentPageSolved, 'solved');
      pageChanged = function(type, pageNum) {
        requestTasks(pageNum, type);
      };
      sortBy = function(predicate, type) {
        $scope.predicate = predicate;
        $scope.reverse = !$scope.reverse;
        if (type === 'solved') {
          requestTasks($scope.currentPageSolved, type);
        } else {
          requestTasks($scope.currentPageAdded, type);
        }
      };
      updateDialog = function() {
        modalService.openUserUpdateDialog($scope);
      };
      updateImageDialog = function() {
        modalService.openImageUpdateDialog($scope);
      };
      $scope.pageChanged = pageChanged;
      $scope.openUpdateDialog = updateDialog;
      $scope.openImageUpdateDialog = updateImageDialog;
      $scope.equalsTo = currentUser.equalsTo;
      $scope.sortBy = sortBy;
      $scope.countries = Country.query();
    }
  ]);

  'use strict';

  angular.module('taskbookApp').controller('UsersController', [
    '$scope', 'UserService', 'CurrentUserService', function($scope, User, currentUser) {
      var banUser, change, changeRole, defaultValues, deleteUser, pageChanged, requestUsers, resetFilterName, sortBy;
      defaultValues = {
        users: [],
        itemsPerPage: 10,
        currentPage: 1,
        roles: ['ADMIN', 'MODERATOR', 'USER'],
        updated_user: '',
        predicate: 'rating',
        reverse: true,
        nameQuery: ''
      };
      angular.extend($scope, defaultValues);
      requestUsers = function(pageNum, perPage) {
        var sort;
        sort = $scope.reverse ? '-' + $scope.predicate : $scope.predicate;
        return User.query({
          page: pageNum - 1,
          per_page: perPage,
          sort: sort,
          name_query: $scope.nameQuery
        }, function(object) {
          $scope.users = object.content || [];
          $scope.totalItems = object.total || 0;
          if (!object.pageable) {
            return $scope.currentPage = 1;
          } else {
            return $scope.currentPage = object.pageable.page + 1;
          }
        });
      };
      requestUsers($scope.currentPage, $scope.itemsPerPage);
      pageChanged = function(currentPage) {
        $scope.currentPage = currentPage || 1;
        return requestUsers($scope.currentPage, $scope.itemsPerPage);
      };
      change = function() {
        return $scope.updated_user = '';
      };
      changeRole = function(user) {
        var userToUpdate;
        if (!currentUser.isAdmin()) {
          return;
        }
        userToUpdate = {
          username: user.username,
          roles: user.roles,
          isEnabled: user.isEnabled
        };
        return User.update({
          id: userToUpdate.username
        }, userToUpdate).$promise.then((function() {
          return $scope.updated_user = userToUpdate.username;
        }), function(error) {
          console.warn(error.data);
          return $scope.updated_user = '';
        });
      };
      banUser = function(user) {
        var userToUpdate;
        if (!currentUser.isAdmin()) {
          return;
        }
        userToUpdate = {
          username: user.username,
          roles: user.roles,
          isEnabled: user.isEnabled ? false : true
        };
        return User.update({
          id: userToUpdate.username
        }, userToUpdate).$promise.then((function() {
          return user.isEnabled = userToUpdate.isEnabled;
        }), function(error) {
          return console.warn(error.data);
        });
      };
      deleteUser = function(user) {
        if (!currentUser.isAdmin()) {
          return;
        }
        return User.destroy({
          id: user.username
        }, (function() {
          var index;
          index = $scope.users.indexOf(user);
          $scope.users.splice(index, 1);
          return pageChanged($scope.currentPage);
        }), function(error) {
          return console.warn(error.data);
        });
      };
      sortBy = function(predicate) {
        $scope.predicate = predicate;
        $scope.reverse = !$scope.reverse;
        requestUsers($scope.currentPage, $scope.itemsPerPage);
      };
      resetFilterName = function() {
        $scope.nameQuery = '';
        requestUsers($scope.currentPage, $scope.itemsPerPage);
      };
      $scope.pageChanged = pageChanged;
      $scope.change = change;
      $scope.changeRole = changeRole;
      $scope.banUser = banUser;
      $scope.deleteUser = deleteUser;
      $scope.sortBy = sortBy;
      $scope.isAuthorized = function() {
        return currentUser.hasRole(['ADMIN']);
      };
      $scope.equalsTo = currentUser.equalsTo;
      return $scope.resetFilterName = resetFilterName;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').directive('ngConfirmClick', [
    function() {
      return {
        link: function(scope, element, attr) {
          var clickAction, msg;
          msg = attr.ngConfirmClick || 'Are you sure?';
          clickAction = attr.confirmedClick;
          return element.bind('click', function() {
            if (window.confirm(msg)) {
              return scope.$eval(clickAction);
            }
          });
        }
      };
    }
  ]);

  'use strict';

  angular.module('taskbookApp').directive('equals', function() {
    return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, elem, attrs, ngModel) {
        var validate;
        if (!ngModel) {
          return;
        }
        scope.$watch(attrs.ngModel, function() {
          validate();
        });
        attrs.$observe('equals', function(val) {
          validate();
        });
        return validate = function() {
          var val1, val2;
          val1 = ngModel.$viewValue;
          val2 = attrs.equals;
          ngModel.$setValidity('equals', !val1 || !val2 || val1 === val2);
        };
      }
    };
  });

  'use strict';


  /* Ангулярная ng-model не работает с input данными типа "файл"
   поэтому необходимо создать кастомную директиву для бинда
   файлов к переменным
   */

  angular.module('taskbookApp').directive('fileModel', [
    '$parse', function($parse) {
      return {
        restrict: 'A',
        link: function(scope, element, attrs) {
          var model, modelSetter;
          model = $parse(attrs.fileModel);
          modelSetter = model.assign;
          return element.bind('change', function() {
            return scope.$apply(function() {
              return modelSetter(scope, element[0].files[0]);
            });
          });
        }
      };
    }
  ]);

  'use strict';

  angular.module('taskbookApp').factory('Country', [
    '$resource', function($resource) {
      return $resource('data/countries.json', {}, {
        query: {
          method: 'GET',
          isArray: true
        }
      });
    }
  ]);

  angular.module('taskbookApp').factory('Messages', [
    '$resource', function($resource) {
      return $resource('data/messages.json', {}, {
        query: {
          method: 'GET',
          isArray: true
        }
      });
    }
  ]);

  angular.module('taskbookApp').factory('Developers', [
    '$resource', function($resource) {
      return $resource('data/developers.json', {}, {
        query: {
          method: 'GET',
          isArray: true
        }
      });
    }
  ]);

  'use strict';

  angular.module('taskbookApp').factory('TaskService', [
    '$resource', 'HOST', function($resource, HOST) {
      var BASE_PATH;
      BASE_PATH = HOST + 'rest/api/v1/tasks';
      return $resource(BASE_PATH + '/:id', {}, {
        query: {
          method: 'GET'
        },
        save: {
          method: 'POST'
        },
        update: {
          method: 'PUT',
          url: BASE_PATH + '/:id',
          params: {
            id: '@id'
          }
        },
        destroy: {
          method: 'DELETE'
        },
        check: {
          method: 'POST',
          params: {
            id: 'test'
          }
        },
        tagsQuery: {
          method: 'GET',
          params: {
            id: 'tags'
          },
          isArray: true
        },
        levelsQuery: {
          method: 'GET',
          params: {
            id: 'levels'
          },
          isArray: true
        },
        envsQuery: {
          method: 'GET',
          params: {
            id: 'environment'
          },
          isArray: true
        },
        vote: {
          method: 'POST',
          url: BASE_PATH + '/:id/vote',
          params: {
            id: '@id',
            username: '@username',
            delta: '@delta'
          }
        },
        addedByUser: {
          method: 'GET',
          url: BASE_PATH + '/user/:username/added',
          params: {
            username: '@username'
          }
        },
        solvedByUserIds: {
          method: 'GET',
          url: BASE_PATH + '/user/:name/solved_ids',
          params: {
            name: '@name'
          },
          isArray: true
        }
      });
    }
  ]);

  'use strict';

  angular.module('taskbookApp').factory('UserService', [
    '$resource', 'HOST', function($resource, HOST) {
      var BASE_PATH;
      BASE_PATH = HOST + 'rest/api/v1/users';
      return $resource(BASE_PATH + '/:id', {
        id: '@id'
      }, {
        query: {
          method: 'GET'
        },
        save: {
          method: 'POST'
        },
        update: {
          method: 'PUT'
        },
        destroy: {
          method: 'DELETE'
        },
        login: {
          method: 'POST',
          params: {
            id: 'login'
          }
        },
        recover: {
          method: 'POST',
          params: {
            id: 'recover',
            email: '@email'
          }
        },
        sendFeedback: {
          method: 'POST',
          url: BASE_PATH + '/feedback'
        },
        getUserTags: {
          method: 'GET',
          url: BASE_PATH + '/:id/tags',
          params: {
            id: '@id'
          }
        }
      });
    }
  ]);

  angular.module('taskbookApp').filter('datestamp', [
    function() {
      return function(unixdate) {
        var date, months;
        if (!unixdate) {
          return '';
        }
        months = ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'];
        date = new Date(unixdate);
        return months[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
      };
    }
  ]);

  angular.module('taskbookApp').filter('timestamp', [
    function() {
      return function(unixdate) {
        var date, hours, minutes, seconds;
        if (!unixdate) {
          return '';
        }
        date = new Date(unixdate);
        hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
        minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
        seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
        return hours + ':' + minutes + ':' + seconds;
      };
    }
  ]);


  /**
   * Created by prima on 10.03.15.
   */

  angular.module('taskbookApp').factory('AuthorizationService', [
    '$location', '$rootScope', 'CurrentUserService', 'MessageService', function($location, $rootScope, currentUser, message) {
      return {
        taskCreatorsOnly: function() {
          if (!currentUser.getEnabled()) {
            $location.path('/');
          } else {
            return 'authorized';
          }
        },
        taskEditorsOnly: function(author) {
          if (!currentUser.hasRole(['MODERATOR', 'ADMIN']) && !currentUser.equalsTo(author)) {
            if (_.contains($location.path(), 'edit')) {
              $location.path('/');
              return;
            } else {
              $rootScope.flash.clearQueue();
              $rootScope.flash.setMessage(message.get('task_verification'));
              $location.path('/tasks');
              return;
            }
          }
          return 'authorized';
        }
      };
    }
  ]);

  'use strict';


  /**
   * Current user service
   */

  angular.module('taskbookApp').factory('CurrentUserService', [
    '$resource', '$location', '$http', '$rootScope', '$cookieStore', 'HOST', function($resource, $location, $http, $rootScope, $cookieStore, HOST) {
      var User, user, userMethods;
      User = $resource(HOST + 'rest/api/v1/users/:id', {}, {
        load: {
          method: 'GET',
          params: {
            id: 'current_user'
          }
        },
        login: {
          method: 'POST',
          params: {
            id: 'login'
          }
        },
        logout: {
          method: 'GET',
          url: HOST + '/j_spring_security_logout'
        }
      });
      user = {};
      User.load({}, function(data) {
        return angular.extend(user, data);
      });
      userMethods = {
        isLoggedIn: function() {
          return !!$cookieStore.get('token' || !!user.username);
        },
        setUser: function(data) {
          user.roles = [];
          if (data) {
            $cookieStore.put('token', 'token-stub');
            $cookieStore.put('username', data.username);
            angular.extend(user, data);
          } else {
            $cookieStore.remove('token');
            $cookieStore.remove('username');
            user.username = null;
          }
        },
        getUsername: function() {
          return user.username || $cookieStore.get('username');
        },
        getFullname: function() {
          return user.fullName;
        },
        getEmail: function() {
          return user.email;
        },
        getPasswordHash: function() {
          return user.password;
        },
        getEnabled: function() {
          return user.isEnabled;
        },
        getNonReadOnly: function() {
          return user.isNonReadOnly;
        },
        getRoles: function() {
          return user.roles;
        },
        isRegular: function() {
          return !_.intersection(user.roles, ['ADMIN', 'MODERATOR']).length;
        },
        isAdmin: function() {
          return _.contains(user.roles, 'ADMIN');
        },
        isModerator: function() {
          return _.contains(user.roles, 'MODERATOR');
        },
        addRole: function(role) {
          user.roles.push(role);
        },
        setRoles: function(roles) {
          user.roles = roles;
        },
        hasRole: function(rolesToCheck) {
          if (user.roles === void 0) {
            return false;
          } else {
            return _.intersection(user.roles, rolesToCheck).length > 0;
          }
        },
        equalsTo: function(name) {
          var username;
          username = user.username || $cookieStore.get('username');
          if (!name || !username) {
            return false;
          } else {
            return username.toLowerCase() === name.toLowerCase();
          }
        },
        login: function(params, cb, cbErr) {
          return User.login(params, cb, cbErr);
        },
        logout: function(message) {
          message = message || 'До свидания!';
          User.logout();
          $cookieStore.remove('token');
          $cookieStore.remove('username');
          user = angular.extend({}, userMethods);
          $rootScope.flash.setMessage(message);
          return $location.url('/');
        },
        load: function(cb, cbErr) {
          return User.load({}, cb, cbErr);
        }
      };
      angular.extend(user, userMethods);
      return user;
    }
  ]);

  'use strict';


  /* Выделяем метод загрузки файлов в отдельный сервис - на случай,
    если понадобиться загружать картинки к задачам, например.
    ВАЖНО: метод использует FormData, которая не поддерживается в
    InternetExplorer 9 и его более ранних версиях
   */

  angular.module('taskbookApp').service('UploadService', [
    '$http', function($http) {
      this.send = function(file) {
        var fd, uploadUrl;
        uploadUrl = 'http://deviantsart.com';
        fd = new FormData;
        fd.append('file', file);
        return $http.post(uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: {
            'Content-Type': void 0
          }
        });
      };
    }
  ]);

  'use strict';

  angular.module('taskbookApp').factory('flash', function($rootScope, $document) {
    var currentMessage, queue;
    queue = [];
    currentMessage = '';
    $rootScope.$on('$routeChangeSuccess', function() {
      return currentMessage = queue.shift() || '';
    });
    angular.element($document[0].body).on('click', function() {
      return currentMessage = queue.shift() || '';
    });
    return {
      setMessage: function(message) {
        return queue.push(message);
      },
      getMessage: function() {
        return currentMessage;
      },
      clearQueue: function() {
        return queue = [];
      }
    };
  });

  'use strict';

  angular.module('taskbookApp').service('LoaderService', function() {
    this.toggle = function() {
      return angular.element('#global-loader').toggleClass('loader-hidden');
    };
    this.isVisible = function() {
      return angular.element('#global-loader').hasClass('loader-hidden');
    };
    return this;
  });

  'use strict';

  angular.module('taskbookApp').factory('MessageService', [
    'Messages', function(Messages) {
      var messageMethods, messages;
      messages = Messages.query();
      messageMethods = {
        get: function(name) {
          return _.result(_.find(messages, function(el) {
            return el.name === name;
          }), 'text');
        }
      };
      angular.extend(messages, messageMethods);
      return messages;
    }
  ]);

  'use strict';

  angular.module('taskbookApp').factory('ModalService', [
    'ngDialog', function(ngDialog) {
      this.openTaskTestResult = function(result) {
        return ngDialog.open({
          template: 'partials/modals/task-test-result.html',
          data: result
        });
      };
      this.openContactForm = function() {
        return ngDialog.open({
          template: 'partials/modals/contact-form.html'
        });
      };
      this.openUserUpdateDialog = function(scope) {
        return ngDialog.open({
          template: 'partials/modals/update-user-profile.html',
          controller: 'UpdateUserController',
          scope: scope
        });
      };
      this.openImageUpdateDialog = function(scope) {
        return ngDialog.open({
          template: 'partials/modals/update-user-image.html',
          controller: 'UpdateUserImageController',
          scope: scope
        });
      };
      return this;
    }
  ]);

}).call(this);
