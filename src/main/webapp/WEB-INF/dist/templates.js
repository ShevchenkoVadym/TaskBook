angular.module('templates-dist', ['partials/404.html', 'partials/about.html', 'partials/footer.html', 'partials/forms/_form-messages.html', 'partials/forms/password-recover.html', 'partials/forms/registration.html', 'partials/forms/sign-in.html', 'partials/forms/social-auth.html', 'partials/header.html', 'partials/home.html', 'partials/login.html', 'partials/modals/contact-form.html', 'partials/modals/task-test-result.html', 'partials/modals/update-user-image.html', 'partials/modals/update-user-profile.html', 'partials/other/_info-task-rating.html', 'partials/other/_info.html', 'partials/playground.html', 'partials/tasks/task-detail.html', 'partials/tasks/task-new.html', 'partials/tasks/tasks.html', 'partials/tests/test-detail.html', 'partials/tests/tests.html', 'partials/users/_as_guest.html', 'partials/users/_as_user.html', 'partials/users/_user_added_tasks.html', 'partials/users/_user_tabs.html', 'partials/users/_user_tasks.html', 'partials/users/user-profile.html', 'partials/users/users.html']);

angular.module("partials/404.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/404.html",
    "<div id=\"page-not-found\" class=\"text-center\">\n" +
    "  <h1>\n" +
    "    NullPointerException\n" +
    "  </h1>\n" +
    "\n" +
    "  <div>\n" +
    "    <h3 style=\"color: red;\">Что-то пошло не так...</h3>\n" +
    "    <br /><br />\n" +
    "    Мы не можем найти страницу, которую вы ищете. Скорее всего запрашиваемая страница не существует или была удалена.\n" +
    "    <br>\n" +
    "    Если ошибка повторится, пожалуйста, <a href ng-click=\"openContactForm()\">сообщите нам.</a>\n" +
    "    <br /><br />\n" +
    "  </div>\n" +
    "  <div>\n" +
    "    <a class=\"btn btn-primary\" href=\"#/\" style=\"color: #FFF;\">Вернуться на главную.</a>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/about.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/about.html",
    "<div id=\"about-page\">\n" +
    "  <h3>Добро пожаловать!</h3>\n" +
    "\n" +
    "  <div class=\"row\">\n" +
    "    <div class=\"col-md-8\">\n" +
    "      Хотите научиться программировать на языке Java? Или укрепить уже существующие навыки?\n" +
    "      Тогда вы попали по адресу! Сайт содержит множество задач\n" +
    "      <!--{{totalTasks}}-->\n" +
    "      <!--<span ng-switch=\"getLastTaskCount()\">-->\n" +
    "        <!--<span ng-switch-when=\"1\">-->\n" +
    "          <!--задачу-->\n" +
    "        <!--</span>-->\n" +
    "        <!--<span ng-switch-when=\"2\">-->\n" +
    "          <!--задачи-->\n" +
    "        <!--</span>-->\n" +
    "        <!--<span ng-switch-default>-->\n" +
    "          <!--задач-->\n" +
    "        <!--</span>-->\n" +
    "      <!--</span>-->\n" +
    "      различного уровня сложности: от самых\n" +
    "      простых для начинающих и до очень сложных для профессинальных разработчиков.\n" +
    "    </div>\n" +
    "    <div class=\"col-md-4\">\n" +
    "      <a href=\"#/tasks\">\n" +
    "        <img src=\"assets/img/about/tasks.png\">\n" +
    "      </a>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <hr>\n" +
    "\n" +
    "  <div class=\"row\">\n" +
    "    <div class=\"col-md-4\">\n" +
    "      <a href=\"#/tasks/new\">\n" +
    "        <img src=\"assets/img/about/add_task.png\" style=\"width: 90%;\">\n" +
    "      </a>\n" +
    "    </div>\n" +
    "    <div>\n" +
    "      Знаете интересную задачу для решения на языке Java? Поделитесь ею с нами!\n" +
    "      Для добавления задачи на сайт нужно написать компилирующийся исходный код\n" +
    "      решения и несколько тестов к нему на JUnit или TestNG.\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <hr>\n" +
    "\n" +
    "  <div class=\"row middle\">\n" +
    "    <div class=\"col-md-4\">\n" +
    "      Проверка решения происходит на сервере\n" +
    "    </div>\n" +
    "    <div class=\"col-md-4\">\n" +
    "      <img src=\"assets/img/about/success.png\" style=\"width: 100%;\">\n" +
    "    </div>\n" +
    "    <div class=\"col-md-4\">\n" +
    "      <img src=\"assets/img/about/fail.png\" style=\"width: 90%;\">\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <hr>\n" +
    "\n" +
    "  <div class=\"row middle\">\n" +
    "    <div class=\"col-md-6\">\n" +
    "      За каждую решенную или добавленную на сайт задачу начисляется рейтинг\n" +
    "    </div>\n" +
    "    <div class=\"col-md-6\">\n" +
    "      <img src=\"assets/img/about/rating.png\" style=\"width: 100%;\">\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <div class=\"row\">\n" +
    "    <div class=\"col-md-12 text-center\">\n" +
    "      <button class=\"btn btn-primary btn-lg\" ng-click=\"randomTask()\">Решить задачу!</button>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <hr>\n" +
    "\n" +
    "  <div class=\"row\">\n" +
    "    <div class=\"col-md-8\">\n" +
    "      Проект выполнен студентами <a href=\"http://javarush.ru\">JavaRush.ru</a> JavaRush - играй и учи Java одновременно.\n" +
    "\n" +
    "      <ul style=\"list-style: none\">\n" +
    "        <li ng-repeat=\"dev in developers\">\n" +
    "          <a href=\"{{dev.profile}}\">{{dev.name}}</a>\n" +
    "        </li>\n" +
    "      </ul>\n" +
    "    </div>\n" +
    "    <div class=\"col-md-4\">\n" +
    "      Возник вопрос? <button class=\"btn btn-warning\" ng-click=\"openContactForm()\">Задай его нам!</button>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/footer.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/footer.html",
    "<div class=\"container\">\n" +
    "  <div id=\"footer\">\n" +
    "    &copy; 2014 - {{currentYear}} JavaRush.ru\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/forms/_form-messages.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/forms/_form-messages.html",
    "<div class=\"form-message\" ng-hide=\"formError === undefined\">\n" +
    "  <div class=\"alert alert-danger\" role=\"alert\">\n" +
    "    <span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n" +
    "    <span class=\"sr-only\">Ошибка:</span>\n" +
    "    {{formError}}\n" +
    "  </div>\n" +
    "</div>\n" +
    "\n" +
    "<div class=\"form-message\" ng-hide=\"formInfo === undefined\">\n" +
    "  <div class=\"alert alert-info\" role=\"alert\">\n" +
    "    <span class=\"glyphicon glyphicon-info-sign\" aria-hidden=\"true\"></span>\n" +
    "    {{formInfo}}\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/forms/password-recover.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/forms/password-recover.html",
    "<div ng-controller=\"LoginController\" class=\"ngdialog-message\">\n" +
    "\n" +
    "<div>\n" +
    "  <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "</div>\n" +
    "\n" +
    "<form name=\"formRecoverPassword\" class=\"form-horizontal\" style=\"margin-top: 10px;\">\n" +
    "  <div class=\"form-group\">\n" +
    "    <div class=\"col-sm-12\">\n" +
    "      <input type=\"email\" ng-model=\"lostPasswdEmail\" autofocus placeholder=\"Электронная почта\" class=\"form-control\" required />\n" +
    "    </div>\n" +
    "  </div>\n" +
    "  <div class=\"form-group text-center\">\n" +
    "    <div class=\"col-sm-12\">\n" +
    "      <input type=\"submit\" class=\"btn btn-sm btn-primary\" value=\"Восстановить пароль\"\n" +
    "             ng-click=\"recoverPassword(lostPasswdEmail)\" ng-disabled=\"formRecoverPassword.$invalid\"\n" +
    "             ng-hide=\"show_loader\" />\n" +
    "      <img ng-show=\"show_loader\" src=\"assets/img/ajax-loader.gif\" class=\"loader\">\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</form>\n" +
    "\n" +
    "</div>");
}]);

angular.module("partials/forms/registration.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/forms/registration.html",
    "<div ng-controller=\"RegistrationController\" class=\"ngdialog-message\">\n" +
    "\n" +
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-9\" style=\"border-right: 1px solid #DDD\">\n" +
    "\n" +
    "    <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "\n" +
    "    <div class=\"form-message\" ng-hide=\"!formNewUser.$error.required\">\n" +
    "      <div class=\"alert alert-warning\" role=\"alert\">\n" +
    "        <span class=\"glyphicon glyphicon-warning-sign\" aria-hidden=\"true\"></span>\n" +
    "        Все поля обязательны к заполнению.\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <form name=\"formNewUser\" class=\"form-horizontal\" style=\"margin-top: 10px;\" autocomplete=\"off\">\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12\">\n" +
    "          <div class=\"has-feedback\" ng-class=\"{'has-error': formNewUser.username.$invalid, 'has-success': !formNewUser.username.$invalid}\">\n" +
    "            <div class=\"input-group\">\n" +
    "          <span class=\"input-group-addon\">\n" +
    "            <span class=\"glyphicon glyphicon-user\"></span>\n" +
    "          </span>\n" +
    "              <input type=\"text\" ng-model=\"new_user.username\" ng-pattern=\"/^[a-zA-Z0-9_]+$/\" autofocus placeholder=\"Логин\" class=\"form-control\" name=\"username\" required />\n" +
    "            </div>\n" +
    "\n" +
    "            <span class=\"glyphicon glyphicon-ok form-control-feedback\" aria-hidden=\"true\" ng-show=\"!formNewUser.username.$invalid\"></span>\n" +
    "            <span class=\"glyphicon glyphicon-remove form-control-feedback\" aria-hidden=\"true\" ng-show=\"formNewUser.username.$invalid\"></span>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12\">\n" +
    "          <div class=\"has-feedback\" ng-class=\"{'has-error': formNewUser.email.$invalid, 'has-success': !formNewUser.email.$invalid}\">\n" +
    "            <div class=\"input-group\">\n" +
    "          <span class=\"input-group-addon\">\n" +
    "            <span class=\"glyphicon glyphicon-envelope\"></span>\n" +
    "          </span>\n" +
    "              <input type=\"text\" ng-pattern=\"/^[_a-z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[_a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$/\"\n" +
    "                     ng-model=\"new_user.email\" placeholder=\"Эл.почта\" class=\"form-control\" name=\"email\" required />\n" +
    "            </div>\n" +
    "\n" +
    "            <span class=\"glyphicon glyphicon-ok form-control-feedback\" aria-hidden=\"true\" ng-show=\"!formNewUser.email.$invalid\"></span>\n" +
    "            <span class=\"glyphicon glyphicon-remove form-control-feedback\" aria-hidden=\"true\" ng-show=\"formNewUser.email.$invalid\"></span>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12\">\n" +
    "          <div class=\"has-feedback\" ng-class=\"{'has-error': formNewUser.password.$invalid || new_user.password != password_verify, 'has-success': !formNewUser.password.$invalid && new_user.password == password_verify}\">\n" +
    "            <div class=\"input-group\">\n" +
    "          <span class=\"input-group-addon\">\n" +
    "            <span class=\"glyphicon glyphicon-lock\"></span>\n" +
    "          </span>\n" +
    "              <input type=\"password\" ng-model=\"new_user.password\" equals=\"{{password_verify}}\" placeholder=\"Пароль\" class=\"form-control\" name=\"password\" required />\n" +
    "            </div>\n" +
    "\n" +
    "            <span class=\"glyphicon glyphicon-ok form-control-feedback\" aria-hidden=\"true\" ng-show=\"!formNewUser.password.$invalid && new_user.password == password_verify\"></span>\n" +
    "            <span class=\"glyphicon glyphicon-remove form-control-feedback\" aria-hidden=\"true\" ng-show=\"formNewUser.password.$invalid || new_user.password != password_verify\"></span>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12\">\n" +
    "          <div class=\"has-feedback\" ng-class=\"{'has-error': formNewUser.password_verify.$invalid, 'has-success': !formNewUser.password_verify.$invalid}\">\n" +
    "            <div class=\"input-group\">\n" +
    "          <span class=\"input-group-addon\">\n" +
    "            <span class=\"glyphicon glyphicon-lock\"></span>\n" +
    "          </span>\n" +
    "              <input type=\"password\" ng-model=\"password_verify\" equals=\"{{new_user.password}}\" placeholder=\"Подтверждение пароля\" class=\"form-control\" name=\"password_verify\" required />\n" +
    "            </div>\n" +
    "\n" +
    "            <span class=\"glyphicon glyphicon-ok form-control-feedback\" aria-hidden=\"true\" ng-show=\"!formNewUser.password_verify.$invalid\"></span>\n" +
    "            <span class=\"glyphicon glyphicon-remove form-control-feedback\" aria-hidden=\"true\" ng-show=\"formNewUser.password_verify.$invalid\"></span>\n" +
    "\n" +
    "            <div class=\"text-center\" ng-show=\"new_user.password != password_verify\" style=\"color: red;\">Пароль и подтверждение должны совпадать</div>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12 text-center\">\n" +
    "          <input type=\"submit\" class=\"btn btn-sm btn-primary\" value=\"Создать аккаунт\" ng-click=\"signUp(new_user)\" ng-disabled=\"formNewUser.$invalid\"/>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </form>\n" +
    "  </div>\n" +
    "\n" +
    "  <div class=\"col-md-3 text-center\" id=\"social-auth\">\n" +
    "    <div class=\"text-center\">Войти через соцсети</div>\n" +
    "\n" +
    "    <div ng-include src=\"'partials/forms/social-auth.html'\"></div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "\n" +
    "</div>");
}]);

angular.module("partials/forms/sign-in.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/forms/sign-in.html",
    "<div ng-controller=\"LoginController\" class=\"ngdialog-message\">\n" +
    "\n" +
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-10\" style=\"border-right: 1px solid #DDD\">\n" +
    "    <div>\n" +
    "      <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "    </div>\n" +
    "\n" +
    "    <form name=\"formNewSession\" class=\"form-horizontal\" style=\"margin-top: 5px;\">\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12\">\n" +
    "          <input type=\"text\" ng-model=\"login_try.username\" autofocus placeholder=\"Логин\" class=\"form-control\" required />\n" +
    "        </div>\n" +
    "      </div>\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12\">\n" +
    "          <input type=\"password\" ng-model=\"login_try.password\" placeholder=\"Пароль\" class=\"form-control\" required />\n" +
    "        </div>\n" +
    "      </div>\n" +
    "      <!--<div class=\"form-group\">-->\n" +
    "        <!--<div class=\"col-sm-12\">-->\n" +
    "          <!--<div class=\"checkbox\">-->\n" +
    "            <!--<label>-->\n" +
    "              <!--<input type=\"checkbox\" ng-model=\"remember_me\" /> Запомнить меня-->\n" +
    "            <!--</label>-->\n" +
    "          <!--</div>-->\n" +
    "        <!--</div>-->\n" +
    "      <!--</div>-->\n" +
    "      <div class=\"form-group\">\n" +
    "        <div class=\"col-sm-12 text-center\">\n" +
    "          <input type=\"submit\" class=\"btn btn-sm btn-primary\" value=\"Войти\"\n" +
    "                 ng-click=\"signIn(login_try)\" ng-disabled=\"formNewSession.$invalid\"\n" +
    "                 ng-hide=\"show_loader\" />\n" +
    "          <img ng-show=\"show_loader\" src=\"assets/img/ajax-loader.gif\" class=\"loader\">\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </form>\n" +
    "  </div>\n" +
    "\n" +
    "  <div class=\"col-md-2 text-center\" id=\"social-auth\">\n" +
    "    <div ng-include src=\"'partials/forms/social-auth.html'\"></div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "\n" +
    "\n" +
    "</div>");
}]);

angular.module("partials/forms/social-auth.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/forms/social-auth.html",
    "<div class=\"social\">\n" +
    "  <a href=\"/auth/vkontakte?scope=email\" class=\"btn btn-vk\"><span class=\"fa fa-vk fa-2x\"></span></a>\n" +
    "</div>\n" +
    "\n" +
    "<div class=\"social\">\n" +
    "  <a href=\"/auth/facebook?scope=email\" class=\"btn btn-facebook\"><span class=\"fa fa-facebook fa-2x\"></span></a>\n" +
    "</div>\n" +
    "\n" +
    "<div class=\"social\">\n" +
    "  <form action=\"/auth/google\" method=\"POST\">\n" +
    "    <input type=\"hidden\" name=\"scope\" value=\"email profile\" />\n" +
    "    <input type=\"hidden\" name=\"access_type\" value=\"offline\" />\n" +
    "    <button type=\"submit\" class=\"btn btn-google-plus\"><span class=\"fa fa-google-plus fa-2x\"></span></button>\n" +
    "  </form>\n" +
    "</div>\n" +
    "\n" +
    "<div style=\"display:none\">\n" +
    "  <a href=\"/auth/facebook?scope=email\"><button class=\"btn btn-facebook\"><i class=\"icon-facebook\"></i> | Facebook Sign In</button></a>\n" +
    "  <a href=\"/auth/vkontakte?scope=email\"><button class=\"btn btn-vk\"><i class=\"icon-vk\"></i> | Vkontakte Sign In</button></a>\n" +
    "  <a href=\"/auth/google?scope=email%20profile&access_type=offline\"><button class=\"btn btn-google-plus\"><i class=\"icon-google-plus\"></i> | Google Sign In</button></a>\n" +
    "</div>");
}]);

angular.module("partials/header.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/header.html",
    "<!-- Modal dialogs: Sign in/Sign up/Password Recovery -->\n" +
    "<script type=\"text/ng-template\" id=\"session-signup\">\n" +
    "  <div ng-include src=\"'partials/forms/registration.html'\"></div>\n" +
    "</script>\n" +
    "<script type=\"text/ng-template\" id=\"session-signin\">\n" +
    "  <tabset justified=\"true\" style=\"margin-top: 10px;\">\n" +
    "    <tab heading=\"Вход\">\n" +
    "      <div ng-include src=\"'partials/forms/sign-in.html'\"></div>\n" +
    "    </tab>\n" +
    "    <tab heading=\"Восстановление пароля\">\n" +
    "      <div ng-include src=\"'partials/forms/password-recover.html'\"></div>\n" +
    "    </tab>\n" +
    "  </tabset>\n" +
    "</script>\n" +
    "\n" +
    "<nav class=\"navbar navbar-default\">\n" +
    "  <div class=\"container\">\n" +
    "    <!-- Brand and toggle get grouped for better mobile display -->\n" +
    "    <div class=\"navbar-header\">\n" +
    "      <a href=\"#/\">\n" +
    "        <img ng-src=\"assets/img/javarush_icon.png\" style=\"max-height: 50px;\">\n" +
    "      </a>\n" +
    "    </div>\n" +
    "\n" +
    "    <!-- Collect the nav links, forms, and other content for toggling -->\n" +
    "    <div class=\"navbar\">\n" +
    "      <ul class=\"nav navbar-nav\">\n" +
    "        <li ng-class=\"{'active': isMenuItemActive('/')}\"><a href=\"#/\">Главная</a></li>\n" +
    "        <li ng-class=\"{'active': isMenuItemActive('/tasks')}\"><a href=\"#/tasks\">Задачи</a></li>\n" +
    "        <!-- <li><a href=\"#/tests\">Тесты</a></li> -->\n" +
    "        <li ng-class=\"{'active': isMenuItemActive('/users')}\"><a href=\"#/users\">Пользователи</a></li>\n" +
    "        <li ng-class=\"{'active': isMenuItemActive('/about')}\"><a href=\"#/about\">О сайте</a></li>\n" +
    "        <li ng-if=\"isLoggedIn()\" ng-class=\"{'active': isMenuItemActive('/tasks/new')}\">\n" +
    "          <a href=\"#/tasks/new\">Добавить задачу</a>\n" +
    "        </li>\n" +
    "      </ul>\n" +
    "      \n" +
    "      <!--<form class=\"navbar-form navbar-left\" role=\"search\" style=\"width: 225px;\">-->\n" +
    "        <!--<div class=\"input-group\">-->\n" +
    "          <!--<input type=\"text\" class=\"form-control\" placeholder=\"Поиск по сайту...\">-->\n" +
    "          <!--<span class=\"input-group-addon\">-->\n" +
    "            <!--<i class=\"glyphicon glyphicon-search\"></i>-->\n" +
    "          <!--</span>-->\n" +
    "        <!--</div>-->\n" +
    "      <!--</form>-->\n" +
    "\n" +
    "      <ul class=\"nav navbar-nav navbar-right\">\n" +
    "        <li ng-show=\"isLoggedIn()\">\n" +
    "          <p class=\"navbar-text\">Привет, <a href=\"#/users/{{getUsername()}}\">{{getFullname() || getUsername()}}</a>!</p>\n" +
    "        </li>\n" +
    "        <li ng-show=\"isLoggedIn()\">\n" +
    "          <button type=\"button\" class=\"btn btn-danger navbar-btn\" ng-click=\"logout()\">\n" +
    "            Выход\n" +
    "          </button>\n" +
    "        </li>\n" +
    "        <li ng-show=\"!isLoggedIn()\">\n" +
    "          <button type=\"button\" class=\"btn btn-info navbar-btn\" ng-dialog=\"session-signin\">\n" +
    "            Вход\n" +
    "          </button>\n" +
    "        </li>\n" +
    "        <li ng-show=\"!isLoggedIn()\">&nbsp;</li>\n" +
    "        <li ng-show=\"!isLoggedIn()\">\n" +
    "          <button type=\"button\" class=\"btn btn-success navbar-btn\" ng-dialog=\"session-signup\">\n" +
    "            Регистрация\n" +
    "          </button>\n" +
    "        </li>\n" +
    "      </ul>\n" +
    "    </div><!-- /.navbar-collapse -->\n" +
    "  </div><!-- /.container-fluid -->\n" +
    "</nav>\n" +
    "\n" +
    "</div>");
}]);

angular.module("partials/home.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/home.html",
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-6 col-sm-12\">\n" +
    "    <h3>Топ пользователей</h3>\n" +
    "    <table class=\"table table-bordered table-condensed table-hover\">\n" +
    "      <thead>\n" +
    "        <th>#</th>\n" +
    "        <th>Страна</th>\n" +
    "        <th>Пользователь</th>\n" +
    "        <th>Рейтинг</th>\n" +
    "        <th>Задач решено</th>\n" +
    "        <!--<th>Тестов пройдено</th>-->\n" +
    "      </thead>\n" +
    "      <tr ng-repeat=\"user in users\" class=\"item-listing\">\n" +
    "        <td>{{$index + 1}}</td>\n" +
    "        <td>\n" +
    "          <span ng-if=\"user.country\">\n" +
    "            <img ng-src=\"assets/img/flags/16/{{user.country + '.png'}}\">\n" +
    "          </span>\n" +
    "          <span ng-if=\"!user.country\">\n" +
    "            <span class=\"glyphicon glyphicon-question-sign\"></span>\n" +
    "          </span>\n" +
    "        </td>\n" +
    "        <td><a href=\"#/users/{{user.username}}\">{{user.username}}</a></td>\n" +
    "        <td>{{user.rating}}</td>\n" +
    "        <td>{{user.tasksSolved}}</td>\n" +
    "        <!--<td>{{user.passed}}</td>-->\n" +
    "      </tr>\n" +
    "    </table>\n" +
    "  </div>\n" +
    "  <div class=\"col-md-6 col-sm-12\">\n" +
    "    <h3>Топ Задач</h3>\n" +
    "    <table class=\"table table-bordered table-condensed table-hover\">\n" +
    "      <thead>\n" +
    "        <th>#</th>\n" +
    "        <th>Название</th>\n" +
    "        <th>Рейтинг</th>\n" +
    "        <th>Автор</th>\n" +
    "      </thead>\n" +
    "      <tr ng-repeat=\"task in tasks\" class=\"item-listing\">\n" +
    "        <td>{{$index + 1}}</td>\n" +
    "        <td><a href=\"#/tasks/{{task.id}}\">{{task.taskName}}</a></td>\n" +
    "        <td>{{task.rating}}</td>\n" +
    "        <td><a href=\"#/users/{{task.author}}\">{{task.author}}</a></td>\n" +
    "      </tr>\n" +
    "    </table>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/login.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/login.html",
    "<div class=\"row\" style=\"margin-top: 20px;\">\n" +
    "  <div class=\"col-md-offset-3 col-md-6\">\n" +
    "  <tabset justified=\"true\" class=\"tab-animation\">\n" +
    "\n" +
    "    <!-- ЛОГИН / ВХОД -->\n" +
    "\n" +
    "    <tab heading=\"Вход\">\n" +
    "      <div ng-include src=\"'partials/forms/sign-in.html'\"></div>\n" +
    "    </tab>\n" +
    "\n" +
    "    <!-- РЕГИСТРАЦИЯ -->\n" +
    "\n" +
    "    <tab heading=\"Регистрация\">\n" +
    "      <div ng-include src=\"'partials/forms/registration.html'\"></div>\n" +
    "    </tab>\n" +
    "\n" +
    "    <!-- ВОССТАНОВЛЕНИЕ ПАРОЛЯ -->\n" +
    "\n" +
    "    <tab heading=\"Восстановление пароля\">\n" +
    "      <div ng-include src=\"'partials/forms/password-recover.html'\"></div>\n" +
    "    </tab>\n" +
    "  </tabset>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/modals/contact-form.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/modals/contact-form.html",
    "<div class=\"vertical-middle\" style=\"margin-top: 15px;\">\n" +
    "    <div class=\"panel panel-default\">\n" +
    "      <div class=\"panel-heading\">\n" +
    "        <h2 class=\"panel-title\">Форма обратной связи</h2>\n" +
    "      </div>\n" +
    "      <div ng-controller=\"ContactController\" class=\"panel-body\">\n" +
    "        <form name=\"contactform\" class=\"form-horizontal\" role=\"form\">\n" +
    "          <div class=\"form-group\" ng-class=\"{ 'has-error': contactform.inputEmail.$invalid && submitted }\">\n" +
    "            <!--<label for=\"inputEmail\" class=\"col-md-3 control-label\">Эл. почта</label>-->\n" +
    "            <div class=\"col-md-12\">\n" +
    "              <input ng-model=\"formData.inputEmail\" type=\"email\" class=\"form-control\" id=\"inputEmail\" name=\"inputEmail\" placeholder=\"Адрес эл. почты\" required>\n" +
    "            </div>\n" +
    "          </div>\n" +
    "          <div class=\"form-group\" ng-class=\"{ 'has-error': contactform.inputSubject.$invalid && submitted }\">\n" +
    "            <!--<label for=\"inputSubject\" class=\"col-md-3 control-label\">Тема</label>-->\n" +
    "            <div class=\"col-md-12\">\n" +
    "              <input ng-model=\"formData.inputSubject\" type=\"text\" class=\"form-control\" id=\"inputSubject\" name=\"inputSubject\" placeholder=\"Тема сообщения\" required>\n" +
    "            </div>\n" +
    "          </div>\n" +
    "          <div class=\"form-group\" ng-class=\"{ 'has-error': contactform.inputMessage.$invalid && submitted }\">\n" +
    "            <!--<label for=\"inputMessage\" class=\"col-md-3 control-label\">Сообщение</label>-->\n" +
    "            <div class=\"col-md-12\">\n" +
    "              <textarea ng-model=\"formData.inputMessage\" class=\"form-control\" rows=\"4\" id=\"inputMessage\" name=\"inputMessage\" placeholder=\"Текст сообщения...\" required></textarea>\n" +
    "            </div>\n" +
    "          </div>\n" +
    "          <div class=\"form-group\">\n" +
    "            <div class=\"col-md-12 text-center\">\n" +
    "              <button class=\"btn btn-default\" ng-click=\"submit()\" ng-hide=\"sendInProgress\">\n" +
    "                Отправить\n" +
    "              </button>\n" +
    "              <img ng-show=\"sendInProgress\" src=\"assets/img/ajax-loader.gif\" class=\"loader\">\n" +
    "            </div>\n" +
    "          </div>\n" +
    "        </form>\n" +
    "        <div class=\"alert text-center\" ng-class=\"{'alert-danger': !result.success, 'alert-success': result.success }\" style=\"padding: 15px; margin: 0;\" ng-show=\"result.done\">{{ result.message }}</div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("partials/modals/task-test-result.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/modals/task-test-result.html",
    "<div style=\"margin-top: 15px;\">\n" +
    "\n" +
    "  <div ng-switch=\"ngDialogData.compilation\">\n" +
    "    <div ng-switch-when=\"FAILURE\">\n" +
    "      <div class=\"alert alert-danger\" role=\"alert\">\n" +
    "        <span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>\n" +
    "        <span ng-if=\"ngDialogData.not_saved != undefined\">\n" +
    "          Не удалось сохранить задачу.\n" +
    "        </span>\n" +
    "        Код решения не компилируется:\n" +
    "        <br><br>\n" +
    "        <small>\n" +
    "          {{ngDialogData.answer}}\n" +
    "        </small>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div ng-switch-when=\"OK\">\n" +
    "      <div ng-if=\"ngDialogData.tests == 'FAILURE' || ngDialogData.tests == 'Error'\">\n" +
    "        <div class=\"alert alert-danger\" role=\"alert\">\n" +
    "          <span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>\n" +
    "          <span ng-if=\"ngDialogData.not_saved != undefined\">\n" +
    "            Не удалось сохранить задачу.\n" +
    "          </span>\n" +
    "          <span ng-switch=\"ngDialogData.random\">\n" +
    "            <span ng-switch-when=\"1\">\n" +
    "              Решение не прошло тестирование.\n" +
    "            </span>\n" +
    "            <span ng-switch-when=\"2\">\n" +
    "              Решение не засчитано. Решение не прошло тестирование.\n" +
    "            </span>\n" +
    "            <span ng-switch-when=\"3\">\n" +
    "              Решение не засчитано. Все ли условия задачи выполнены?\n" +
    "            </span>\n" +
    "            <span ng-switch-default>\n" +
    "              Решение не засчитано. Представь что ты физик ядерщик и от твоего кода зависит жизнь миллионов людей.\n" +
    "              Перепроверь свое решение еще раз.\n" +
    "            </span>\n" +
    "          </span>\n" +
    "          <br><br>\n" +
    "          <small>\n" +
    "            {{ngDialogData.answer}}\n" +
    "          </small>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div ng-if=\"ngDialogData.tests == 'OK'\">\n" +
    "        <div class=\"alert alert-success\" role=\"alert\">\n" +
    "          <span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>\n" +
    "          <span ng-switch=\"ngDialogData.statusCode\">\n" +
    "            <span ng-switch-when=\"201\">\n" +
    "              Задача успешно сохранена!\n" +
    "              <br/>\n" +
    "              <span ng-if=\"isRegular()\">\n" +
    "                И будет опубликована после проверки модератором.\n" +
    "              </span>\n" +
    "            </span>\n" +
    "            <span ng-switch-when=\"200\">\n" +
    "              Задача успешно обновлена!\n" +
    "              <br/>\n" +
    "              <span ng-if=\"isRegular()\">\n" +
    "                <small>И будет опубликована после проверки модератором.</small>\n" +
    "              </span>\n" +
    "            </span>\n" +
    "            <span ng-switch-default>\n" +
    "              Задача успешно прошла тестирование!\n" +
    "            </span>\n" +
    "          </span>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div ng-switch-when=\"NO_ANSWER\">\n" +
    "      <div class=\"alert alert-danger\" role=\"alert\">\n" +
    "        <span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>\n" +
    "        <span ng-if=\"ngDialogData.statusCode === 503\">\n" +
    "          Не удалось получить ответ от сервера.\n" +
    "        </span>\n" +
    "        <span ng-if=\"ngDialogData.statusCode !== 503\">\n" +
    "          {{ ngDialogData.answer }}\n" +
    "        </span>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "</div>");
}]);

angular.module("partials/modals/update-user-image.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/modals/update-user-image.html",
    "<div class=\"ngdialog-message text-center\">\n" +
    "  <form class=\"form-horizontal\" name=\"formUploadUserImg\">\n" +
    "    <div class=\"form-group text-center\">\n" +
    "      <div class=\"user-img-wrap\">\n" +
    "        <img ng-src=\"{{!user.imageUrl ? 'assets/img/user_photo_male.png' : user.imageUrl}}\" class=\"img-thumbnail user-img\">\n" +
    "      </div>\n" +
    "    </div>\n" +
    "    <div class=\"form-group text-center\" ng-hide=\"show_loader\">\n" +
    "      <div class=\"col-md-3\" ng-show=\"!userImg\">\n" +
    "        <div class=\"fileUpload btn btn-default\">\n" +
    "          <span>Выбрать</span>\n" +
    "          <input type=\"file\" file-model=\"userImg\" class=\"upload\" />\n" +
    "        </div>\n" +
    "      </div>\n" +
    "      <div class=\"col-md-3\" ng-show=\"!!userImg\">\n" +
    "        <input class=\"btn btn-primary\" ng-click=\"uploadUserImage(userImg)\" type=\"submit\" value=\"Загрузить\" ng-disabled=\"userImg == undefined\" />\n" +
    "      </div>\n" +
    "      <div ng-if=\"user.imageUrl != ''\">\n" +
    "        <div class=\"col-md-4\">\n" +
    "          <input type=\"text\" ng-model=\"userImg.name\" class=\"form-control\" readonly>\n" +
    "        </div>\n" +
    "        <div class=\"col-md-4\">\n" +
    "          <button  class=\"btn btn-danger\" ng-click=\"deleteImage()\">\n" +
    "            Удалить изображение\n" +
    "          </button>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "      <div ng-if=\"user.imageUrl == ''\">\n" +
    "        <div class=\"col-md-8\">\n" +
    "          <input type=\"text\" ng-model=\"userImg.name\" readonly>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "    <div class=\"form-group text-center\" ng-show=\"show_loader\">\n" +
    "      <div class=\"col-md-12\">\n" +
    "        <img src=\"assets/img/ajax-loader.gif\" style=\"max-height: 34px;\">\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </form>\n" +
    "  <div style=\"text-align: left;\">\n" +
    "    <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/modals/update-user-profile.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/modals/update-user-profile.html",
    "<div class=\"ngdialog-message\">\n" +
    "  <h5>Изменение профиля:</h5>\n" +
    "  \n" +
    "  <div style=\"text-align: left;\">\n" +
    "    <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "  </div>\n" +
    "\n" +
    "  <form name=\"formUpdateUser\" class=\"form-horizontal text-center\">\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <div class=\"input-group\">\n" +
    "          <span class=\"input-group-addon\">\n" +
    "            <span class=\"glyphicon glyphicon-user\"></span>\n" +
    "          </span>\n" +
    "          <input type=\"text\" ng-model=\"user.username\" class=\"form-control\" name=\"username\" required ng-disabled=\"true\" />\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <div class=\"has-feedback\" ng-class=\"{'has-error': formUpdateUser.email.$invalid, 'has-success': !formUpdateUser.email.$invalid}\">\n" +
    "          <div class=\"input-group\">\n" +
    "            <span class=\"input-group-addon\">\n" +
    "              <span class=\"glyphicon glyphicon-envelope\"></span>\n" +
    "            </span>\n" +
    "            <input type=\"email\" ng-model=\"user.email\" placeholder=\"Эл.почта\" class=\"form-control\" name=\"email\" required />\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <div class=\"has-feedback has-success\">\n" +
    "          <div class=\"input-group\">  \n" +
    "            <span class=\"input-group-addon\">\n" +
    "              <span class=\"glyphicon glyphicon-globe\"></span>\n" +
    "            </span>\n" +
    "            <select ng-model=\"user.country\" ng-options=\"country as country for country in countries\" class=\"form-control\"></select>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <b>Изменение пароля</b> <i>(оставте поля пустыми, если не хотите менять пароль)</i>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <div class=\"input-group\">\n" +
    "          <span class=\"input-group-addon\">\n" +
    "            <span class=\"glyphicon glyphicon-lock\"></span>\n" +
    "          </span>\n" +
    "          <input type=\"password\" ng-model=\"current_password\" placeholder=\"Текущий пароль\" class=\"form-control\" name=\"password\" />\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <div class=\"has-feedback\" ng-class=\"{'has-error': formUpdateUser.new_password.$invalid || new_password != password_verify, 'has-success': !formUpdateUser.new_password.$invalid && new_password === password_verify}\">\n" +
    "          <div class=\"input-group\">\n" +
    "            <span class=\"input-group-addon\">\n" +
    "              <span class=\"glyphicon glyphicon-lock\"></span>\n" +
    "            </span>\n" +
    "            <input type=\"password\" ng-model=\"new_password\" equals=\"{{password_verify}}\" placeholder=\"Новый пароль\" class=\"form-control\" name=\"new_password\" ng-disabled=\"current_password === undefined || current_password.length === 0\" />\n" +
    "          </div>\n" +
    "\n" +
    "          <span class=\"glyphicon glyphicon-ok form-control-feedback\" aria-hidden=\"true\" ng-show=\"!formUpdateUser.new_password.$invalid && new_password === password_verify\"></span>\n" +
    "          <span class=\"glyphicon glyphicon-remove form-control-feedback\" aria-hidden=\"true\" ng-show=\"formUpdateUser.new_password.$invalid || new_password != password_verify\"></span>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12\">\n" +
    "        <div class=\"has-feedback\" ng-class=\"{'has-error': formUpdateUser.password_verify.$invalid || (new_password !== password_verify), 'has-success': !formUpdateUser.password_verify.$invalid}\">\n" +
    "          <div class=\"input-group\">\n" +
    "            <span class=\"input-group-addon\">\n" +
    "              <span class=\"glyphicon glyphicon-lock\"></span>\n" +
    "            </span>\n" +
    "            <input type=\"password\" ng-model=\"password_verify\" equals=\"{{new_password}}\" placeholder=\"Подтверждение нового пароля\" class=\"form-control\" name=\"password_verify\" ng-disabled=\"current_password === undefined || current_password.length === 0\"/>\n" +
    "          </div>\n" +
    "\n" +
    "          <span class=\"glyphicon glyphicon-ok form-control-feedback\" aria-hidden=\"true\" ng-show=\"!formUpdateUser.password_verify.$invalid\"></span>\n" +
    "          <span class=\"glyphicon glyphicon-remove form-control-feedback\" aria-hidden=\"true\" ng-show=\"formUpdateUser.password_verify.$invalid\"></span>\n" +
    "\n" +
    "          <div class=\"text-center\" ng-show=\"new_password != password_verify\" style=\"color: red;\">Пароль и подтверждение должны совпадать</div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <!--\n" +
    "    <div class=\"form-group\">\n" +
    "      <h5>Изменение пароля:</h5>\n" +
    "    </div>\n" +
    "    -->\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-sm-12 text-center\">\n" +
    "        <input type=\"submit\" ng-click=\"updateUser(user, current_password, new_password);\" class=\"btn btn-sm btn-success\" value=\"Обновить\" ng-disabled=\"formUpdateUser.$invalid || (new_password !== password_verify)\" />\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </form>\n" +
    "  \n" +
    "</div>");
}]);

angular.module("partials/other/_info-task-rating.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/other/_info-task-rating.html",
    "<div class=\"ngdialog-message\">\n" +
    "    При решении задачи Вам могут быть засчитано разное количество баллов:\n" +
    "    <br /><br />\n" +
    "    <ul>\n" +
    "        <li>Решение задачи с первого раза:                 xx баллов</li>\n" +
    "        <li>Решение задачи со второго раза:                xx / 2 баллов</li>\n" +
    "        <li>Решение задачи с третьего и последующего раза: xx / 3 баллов</li>\n" +
    "    </ul>\n" +
    "</div>");
}]);

angular.module("partials/other/_info.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/other/_info.html",
    "<div class=\"ngdialog-message\">\n" +
    "    Обязательные поля при создании или редактирования задачи:\n" +
    "    <br /><br />\n" +
    "\n" +
    "    <ul>\n" +
    "        <li>Название задачи</li>\n" +
    "        <li>Условие задачи - текстовое описание требований к выполнению</li>\n" +
    "        <li>Эталонное решение задачи - собственно само решение</li>\n" +
    "        <li>Шаблон задачи - начальные строки, которые показываются пользователю при решении</li>\n" +
    "        <li>Тесты - тест для задачи (Чтобы проверить эталонное решение, а после проверять решения пользователей)</li>\n" +
    "    </ul>\n" +
    "</div>");
}]);

angular.module("partials/playground.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/playground.html",
    "");
}]);

angular.module("partials/tasks/task-detail.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/tasks/task-detail.html",
    "<script type=\"text/ng-template\" id=\"help\">\n" +
    "  <div ng-include src=\"'partials/other/_info-task-rating.html'\"></div>\n" +
    "</script>\n" +
    "\n" +
    "<div class=\"text-center\">\n" +
    "  <h3>\n" +
    "    {{task.taskName}}\n" +
    "    <a href=\"#/tasks/{{task.id}}/edit\" class=\"btn btn-xs btn-warning\" style=\"color: #FFF\"\n" +
    "      ng-if=\"taskEditorsOnly()\">\n" +
    "    </a>\n" +
    "    <button type=\"button\" ng-if=\"isAdmin()\" class=\"btn btn-xs btn-danger\"\n" +
    "            confirmed-click=\"deleteTask(task)\" ng-confirm-click=\" Удалить задачу? \">\n" +
    "      Delete\n" +
    "    </button>\n" +
    "  </h3>\n" +
    "</div>\n" +
    "\n" +
    "<div class=\"row field\">\n" +
    "  <div class=\"col-md-6\">\n" +
    "    <div class=\"task-info\">\n" +
    "      <label>Автор:</label> <a href=\"#/users/{{task.author}}\">{{task.author}}</a>\n" +
    "    </div>\n" +
    "    <div class=\"task-info\">\n" +
    "      <label>Уровень:</label> <a href=\"#/tasks?levels={{task.level}}\">{{task.level}}</a>\n" +
    "    </div>\n" +
    "    <div class=\"task-info\">\n" +
    "      <label>Баллы: </label> <a href=\"#/tasks?levels={{task.level}}\">{{task.author}}</a> <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "    </div>\n" +
    "    <br>\n" +
    "    <div class=\"task-info\">\n" +
    "      <label>Тэги:</label>\n" +
    "      <span class=\"glyphicon glyphicon-tags\"></span>&nbsp;\n" +
    "      <span ng-repeat=\"tag in task.tags\">\n" +
    "\n" +
    "        <a href=\"#/tasks?tags={{tag}}\" class=\"btn btn-xs btn-default\">{{tag}}</a>\n" +
    "      </span>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "  <div class=\"col-md-6\">\n" +
    "    <div class=\"task-info task-rating\">\n" +
    "      <label>Рейтинг:</label>\n" +
    "      <a href class=\"like-task\" ng-if=\"isLoggedIn() && !hasLiked() && !hasDisliked()\" ng-click=\"vote(1)\">\n" +
    "        <span class=\"fa fa-thumbs-up\"></span>\n" +
    "      </a>\n" +
    "      <span ng-class=\"{'like-task': hasLiked(), 'dislike-task': hasDisliked()}\">\n" +
    "        <b>{{ task.rating }}</b>\n" +
    "      </span>\n" +
    "      <a href class=\"dislike-task\" ng-if=\"isLoggedIn() && !hasLiked() && !hasDisliked()\" ng-click=\"vote(-1)\">\n" +
    "        <span class=\"fa fa-thumbs-down\"></span>\n" +
    "      </a>\n" +
    "      <br />\n" +
    "      <span class=\"vote-info\" ng-if=\"!hasLiked() && !hasDisliked()\">\n" +
    "        Всего голосов: {{ task.votersAmount }}\n" +
    "      </span>\n" +
    "      <span class=\"vote-info\" ng-if=\"hasLiked()\">\n" +
    "        Вам\n" +
    "        <span ng-if=\"task.likedBy.length - 1 > 0\">\n" +
    "           и еще {{ task.likedBy.length - 1 }} {{ task.likedBy.length - 1 == 1 ? 'пользователю' : 'пользователям' }}\n" +
    "        </span>\n" +
    "        понравилась эта задача.\n" +
    "      </span>\n" +
    "      <span class=\"vote-info\" ng-if=\"hasDisliked()\">\n" +
    "        Вам\n" +
    "        <span ng-if=\"task.dislikedBy.length - 1 > 0\">\n" +
    "           и еще {{ task.dislikedBy.length - 1}} {{ task.dislikedBy.length - 1 == 1 ? 'пользователю' : 'пользователям' }}\n" +
    "        </span>\n" +
    "        не понравилась эта задача.\n" +
    "      </span>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "\n" +
    "<div class=\"field\">\n" +
    "  <label>Описание:</label><br />\n" +
    "  <textarea ng-model=\"task.condition\" ng-disabled=\"true\" name=\"description\" class=\"condition-area\" ng-style=\"condStyle\"></textarea>\n" +
    "</div>\n" +
    "\n" +
    "<div>\n" +
    "  <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "</div>\n" +
    "\n" +
    "<div ng-if=\"isLoggedIn()\">\n" +
    "  <div ng-if=\"isEnabled()\">\n" +
    "    <form name=\"formSolution\" style=\"margin-top: 10px;\">\n" +
    "      <div class=\"form-group\">\n" +
    "        <label>Код решения:</label><br />\n" +
    "        <div class=\"ui-cm-editor\">\n" +
    "          <textarea ui-codemirror ui-codemirror-opts=\"editorOptions\"\n" +
    "                    ng-model=\"task.templateCode\" required>\n" +
    "          </textarea>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group text-center\">\n" +
    "        <input type=\"submit\" ng-hide=\"show_loader\" ng-click=\"check(task.templateCode)\" class=\"btn btn-primary\" ng-disabled=\"formSolution.$invalid\" value=\"Отправить на проверку\" />\n" +
    "        <img ng-show=\"show_loader\" src=\"assets/img/ajax-loader.gif\" style=\"max-height: 34px;\">\n" +
    "      </div>\n" +
    "    </form>\n" +
    "\n" +
    "    <div class=\"text-center\" ng-if=\"isNonReadOnly\">\n" +
    "      <dir-disqus disqus-shortname=\"javapractice\"\n" +
    "                  disqus-identifier=\"{{ task.id }}\"\n" +
    "                  disqus-url=\"{{ disqus_url }}\"\n" +
    "                  ready-to-bind=\"{{ disqus_ready }}\">\n" +
    "      </dir-disqus>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <div ng-if=\"!isEnabled()\" style=\"margin-top: 10px;\" class=\"text-center\">\n" +
    "      <span style=\"color: red\">\n" +
    "        Ваш аккаунт заблокирован.\n" +
    "      </span>\n" +
    "  </div>\n" +
    "\n" +
    "</div>\n" +
    "\n" +
    "<div ng-show=\"!isLoggedIn()\" style=\"margin-top: 10px;\" class=\"text-center\">\n" +
    "  Чтобы решить задачу <a href ng-dialog=\"session-signin\">войдите</a> или\n" +
    "  <a href ng-dialog=\"session-signup\">зарегистрируйтесь.</a>\n" +
    "</div>");
}]);

angular.module("partials/tasks/task-new.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/tasks/task-new.html",
    "<script type=\"text/ng-template\" id=\"help\">\n" +
    "  <div ng-include src=\"'partials/other/_info.html'\"></div>\n" +
    "</script>\n" +
    "\n" +
    "<div ng-hide=\"global_loader\">\n" +
    "  <div ng-include src=\"'partials/forms/_form-messages.html'\"></div>\n" +
    "\n" +
    "  <div class=\"form-message\" style=\"height: 52px;\">\n" +
    "    <div class=\"alert alert-warning\" role=\"alert\" style=\"margin: 10px 0 0;\" ng-hide=\"!formTaskNew.$error.required\">\n" +
    "      <span class=\"glyphicon glyphicon-warning-sign\" aria-hidden=\"true\"></span>\n" +
    "      Все поля обязательны к заполнению.\n" +
    "    </div>\n" +
    "  </div>\n" +
    "\n" +
    "  <form name=\"formTaskNew\" class=\"form-horizontal new_task_form\">\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-md-6\">\n" +
    "        <div class=\"field\">\n" +
    "          <label>Введите эталонное решение задачи:</label>\n" +
    "          <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "\n" +
    "          <div class=\"ui-cm-editor\">\n" +
    "          <textarea ui-codemirror ui-codemirror-opts=\"editorOptions\" ng-model=\"task.sourceCode\" required>\n" +
    "          </textarea>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"col-md-6\">\n" +
    "        <div class=\"field\">\n" +
    "          <label>Шаблон задачи:</label>\n" +
    "          <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "\n" +
    "          <div class=\"ui-cm-editor\">\n" +
    "          <textarea ui-codemirror ui-codemirror-opts=\"editorOptions\" ng-model=\"task.templateCode\" class=\"textarea-full\" required>\n" +
    "          </textarea>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div class=\"form-group\">\n" +
    "      <div class=\"col-md-8\">\n" +
    "        <div class=\"field\">\n" +
    "          <div class=\"task-info\">\n" +
    "            <label>Тесты:</label>\n" +
    "            <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "          </div>\n" +
    "          <div class=\"task-info\">\n" +
    "            <select ng-model=\"task.testEnvironment\" ng-options=\"lib for lib in libs\"></select>\n" +
    "          </div>\n" +
    "\n" +
    "          <div class=\"ui-cm-editor\">\n" +
    "            <textarea ui-codemirror ui-codemirror-opts=\"editorOptions\" ng-model=\"task.tests\" class=\"textarea-full\" required>\n" +
    "            </textarea>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"col-md-4\">\n" +
    "        <div class=\"field\" ng-if=\"!isRegular()\">\n" +
    "          <label>Статус:</label>\n" +
    "          <br />\n" +
    "          <select ng-model=\"task.lifeStage\" ng-options=\"stage for stage in lifeStages\" class=\"form-control\"></select>\n" +
    "        </div>\n" +
    "\n" +
    "        <div class=\"field\">\n" +
    "          <label>Название задачи:</label>\n" +
    "          <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "          <br />\n" +
    "          <input type=\"text\" ng-model=\"task.taskName\" class=\"form-control\" required />\n" +
    "        </div>\n" +
    "\n" +
    "        <div class=\"field\">\n" +
    "          <label>Уровень:</label>\n" +
    "          <br />\n" +
    "          <select ng-model=\"task.level\" ng-options=\"level for level in levels\" class=\"form-control\"></select>\n" +
    "        </div>\n" +
    "\n" +
    "        <div class=\"field\">\n" +
    "          <label>Тэги:</label>\n" +
    "          <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "          <br />\n" +
    "          <ui-select multiple tagging tagging-label=\"false\" reset-search-input ng-model=\"task.tags\">\n" +
    "            <ui-select-match placeholder=\"Нажмите, чтобы выбрать тег...\">{{$item}}</ui-select-match>\n" +
    "            <ui-select-choices repeat=\"tag in tags\">\n" +
    "              {{tag}}\n" +
    "            </ui-select-choices>\n" +
    "          </ui-select>\n" +
    "        </div>\n" +
    "\n" +
    "        <div class=\"field\">\n" +
    "          <label>Условие задачи:</label>\n" +
    "          <a href ng-dialog=\"help\"><span class=\"glyphicon glyphicon-question-sign\"></span></a>\n" +
    "          <br />\n" +
    "          <textarea ng-model=\"task.condition\" class=\"form-control\" required></textarea>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </form>\n" +
    "\n" +
    "  <div class=\"text-center new_task_controls\">\n" +
    "    <ul>\n" +
    "      <li>\n" +
    "        <button ng-hide=\"show_publish_loader\" type=\"button\" class=\"btn btn-success\"\n" +
    "                ng-disabled=\"formTaskNew.$invalid\" data-ng-click=\"publish(task)\"\n" +
    "                popover=\"Опубликовать\" popover-trigger=\"mouseenter\" popover-placement=\"left\">\n" +
    "          <span class=\"fa fa-floppy-o fa-2x\"></span>\n" +
    "        </button>\n" +
    "        <img ng-show=\"show_publish_loader\" src=\"assets/img/ajax-loader.gif\" class=\"loader\">\n" +
    "      </li>\n" +
    "      <li>\n" +
    "        <button ng-hide=\"show_test_loader\" type=\"button\" class=\"btn btn-warning\"\n" +
    "                ng-disabled=\"formTaskNew.$invalid\" data-ng-click=\"check(task)\"\n" +
    "                popover=\"Проверить\" popover-trigger=\"mouseenter\" popover-placement=\"left\">\n" +
    "          <span class=\"fa fa fa-check-square-o fa-2x\"></span>\n" +
    "        </button>\n" +
    "        <img ng-show=\"show_test_loader\" src=\"assets/img/ajax-loader.gif\" class=\"loader\">\n" +
    "      </li>\n" +
    "      <!--<li>\n" +
    "        <button class=\"btn btn-default\" data-ng-click=\"draft(task)\">\n" +
    "          Сохранить<br>в черновики\n" +
    "        </button>\n" +
    "      </li>-->\n" +
    "    </ul>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/tasks/tasks.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/tasks/tasks.html",
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-1\">\n" +
    "    Уровень:\n" +
    "  </div>\n" +
    "  <div class=\"col-md-10\">\n" +
    "    <ui-select multiple ng-model=\"multipleLevels.levels\" ng-change=\"pageChanged(currentPage)\">\n" +
    "      <ui-select-match placeholder=\"Click to select level...\">{{$item}}</ui-select-match>\n" +
    "      <ui-select-choices repeat=\"level in levels\">\n" +
    "        {{level}}\n" +
    "      </ui-select-choices>\n" +
    "    </ui-select>\n" +
    "  </div>\n" +
    "  <div class=\"col-md-1\">\n" +
    "    <button ng-click=\"multipleLevels.levels = undefined\" class=\"btn btn-default\"\n" +
    "            ng-disabled=\"!multipleLevels.levels.length\" ng-class=\"{'red': multipleLevels.levels.length > 0}\">\n" +
    "      <span class=\"glyphicon glyphicon-remove\"></span>\n" +
    "    </button>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div class=\"row tag-filter\">\n" +
    "  <div class=\"col-md-1\">\n" +
    "    Тэги:\n" +
    "  </div>\n" +
    "  <div class=\"col-md-10\">\n" +
    "    <ui-select multiple search-enabled tagging tagging-label=\"false\" reset-search-input ng-model=\"multipleTags.tags\"  ng-change=\"pageChanged(currentPage)\">\n" +
    "      <ui-select-match placeholder=\"Нажмите, чтобы выбрать тег...\">{{$item}}</ui-select-match>\n" +
    "      <ui-select-choices repeat=\"tag in tags | filter:$select.search\">\n" +
    "        {{tag}}\n" +
    "      </ui-select-choices>\n" +
    "    </ui-select>\n" +
    "  </div>\n" +
    "  <div class=\"col-md-1\">\n" +
    "    <button ng-click=\"multipleTags.tags = undefined\" class=\"btn btn-default\"\n" +
    "            ng-disabled=\"!multipleTags.tags.length\" ng-class=\"{'red': multipleTags.tags.length > 0}\">\n" +
    "      <span class=\"glyphicon glyphicon-remove\"></span>\n" +
    "    </button>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div class=\"row\" ng-show=\"isLoggedIn()\">\n" +
    "  <div class=\"col-md-12\">\n" +
    "    <form>\n" +
    "      <label>Показать:</label>\n" +
    "      <input type=\"radio\" ng-model=\"solvedCriteria\" value=\"all\" ng-change=\"pageChanged()\">  Все\n" +
    "      <input type=\"radio\" ng-model=\"solvedCriteria\" value=\"not_solved\" ng-change=\"pageChanged()\"> Не решенные\n" +
    "      <input type=\"radio\" ng-model=\"solvedCriteria\" value=\"solved\" ng-change=\"pageChanged()\"> Решенные\n" +
    "    </form>\n" +
    "  </div>\n" +
    "</div>\n" +
    "\n" +
    "<table class=\"table table-bordered table-condensed table-hover all-tasks\">\n" +
    "  <thead>\n" +
    "  <th>#</th>\n" +
    "  <th>\n" +
    "    <a ng-click=\"sortBy('taskName')\" class=\"sort-button\">\n" +
    "      Название\n" +
    "        <span ng-show=\"predicate == 'taskName'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th>\n" +
    "    <a ng-click=\"sortBy('rating')\" class=\"sort-button\">\n" +
    "      Рейтинг\n" +
    "        <span ng-show=\"predicate == 'rating'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th>\n" +
    "    <a ng-click=\"sortBy('author')\" class=\"sort-button\">\n" +
    "      Автор\n" +
    "        <span ng-show=\"predicate == 'author'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th>\n" +
    "    <a ng-click=\"sortBy('successfulAttempts')\" class=\"sort-button\">\n" +
    "      Решили\n" +
    "        <span ng-show=\"predicate == 'successfulAttempts'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th>\n" +
    "    <a ng-click=\"sortBy('level')\" class=\"sort-button\">\n" +
    "      Уровень\n" +
    "        <span ng-show=\"predicate == 'level'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th>\n" +
    "    <a ng-click=\"sortBy('creationDate')\" class=\"sort-button\">\n" +
    "      Добавлена\n" +
    "        <span ng-show=\"predicate == 'creationDate'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th ng-if=\"isAuthorized()\">\n" +
    "    <a ng-click=\"sortBy('lifeStage')\" class=\"sort-button\">\n" +
    "      Статус\n" +
    "        <span ng-show=\"predicate == 'lifeStage'\">\n" +
    "          <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "          <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "  </th>\n" +
    "  <th ng-if=\"isAuthorized()\">Редактировать</th>\n" +
    "  <th ng-if=\"isAdmin()\">Удалить</th>\n" +
    "  </thead>\n" +
    "  <tr ng-repeat=\"task in tasks\" id=\"task-{{task.id}}\">\n" +
    "    <td>{{ tasks.indexOf(task) + 1 + itemsPerPage*(currentPage -1) }}</td>\n" +
    "    <td>\n" +
    "      <a href=\"#/tasks/{{task.id}}\">\n" +
    "        <span ng-if=\"isSolved(task.id)\" class=\"solved\" popover=\"Задача решена\" popover-trigger=\"mouseenter\" popover-placement=\"top\">\n" +
    "          <i class=\"fa fa-check-circle\"></i>\n" +
    "        </span>\n" +
    "        {{task.taskName}}\n" +
    "      </a>\n" +
    "    </td>\n" +
    "    <td>{{task.rating}}</td>\n" +
    "    <td><a href=\"#/users/{{task.author}}\">{{task.author}}</a></td>\n" +
    "    <td>{{task.successfulAttempts}}</td>\n" +
    "    <td>{{task.level}}</td>\n" +
    "    <td>{{task.creationDate | datestamp}}</td>\n" +
    "    <td ng-if=\"isAuthorized()\">{{task.lifeStage}}</td>\n" +
    "    <td ng-if=\"isAuthorized()\">\n" +
    "      <a href=\"/#/tasks/{{task.id}}/edit\" class=\"btn btn-xs btn-warning\" style=\"color: #FFF\">\n" +
    "        Edit\n" +
    "      </a>\n" +
    "    </td>\n" +
    "    <td ng-if=\"isAdmin()\">\n" +
    "      <button type=\"button\" class=\"btn btn-xs btn-danger\" confirmed-click=\"deleteTask(task)\" ng-confirm-click=\" Удалить задачу '{{ task.taskName }}'? \">\n" +
    "        Delete\n" +
    "      </button>\n" +
    "    </td>\n" +
    "  </tr>\n" +
    "</table>\n" +
    "<div class=\"text-center\" ng-if=\"totalItems > itemsPerPage\">\n" +
    "  <pagination boundary-links=\"true\" total-items=\"totalItems\" ng-model=\"currentPage\"\n" +
    "        items-per-page=\"itemsPerPage\" class=\"pagination-sm\" previous-text=\"&lsaquo;\" next-text=\"&rsaquo;\"\n" +
    "        first-text=\"&laquo;\" last-text=\"&raquo;\" ng-change=\"pageChanged(currentPage)\">\n" +
    "  </pagination>\n" +
    "</div>");
}]);

angular.module("partials/tests/test-detail.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/tests/test-detail.html",
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-3\"></div>\n" +
    "  <div class=\"col-md-6\" style=\"text-align: center;\">\n" +
    "  <form name=\"testForm\">\n" +
    "  <ul class=\"test-q\">\n" +
    "    <li ng-repeat=\"question in test.questions\">\n" +
    "      <i>Вопрос {{$index + 1}}. {{question.text}}</i>\n" +
    "      <div ng-switch on=\"question.code\">\n" +
    "        <div ng-switch-when=\"none\">\n" +
    "        </div>\n" +
    "        <div ng-switch-default>\n" +
    "          <div class=\"code-input\">\n" +
    "            {{question.code}}\n" +
    "          </div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "      <ul class=\"test-q\">\n" +
    "        <li ng-repeat=\"answer in question.answers\">\n" +
    "          <div ng-switch on=\"question.acceptMultiple\">\n" +
    "            <div ng-switch-when=\"true\">\n" +
    "              <label>\n" +
    "                <input type=\"checkbox\"/> {{answer}}\n" +
    "              </label>\n" +
    "            </div>\n" +
    "            <div ng-switch-default>\n" +
    "              <label>\n" +
    "                <input type=\"radio\" name=\"name\" />\n" +
    "                {{answer}}\n" +
    "              </label>          \n" +
    "            </div>\n" +
    "          </div>\n" +
    "        </li>\n" +
    "      </ul>\n" +
    "    </li>\n" +
    "  </ul>\n" +
    "  <button class=\"btn btn-success\" style=\"margin-left:auto;margin-right:auto;width:100px;\">Проверить</button>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/tests/tests.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/tests/tests.html",
    "<div class=\"columns\">\n" +
    "  <div class=\"best-items\">\n" +
    "    <h3>Тесты</h3>\n" +
    "    <div class=\"row\" style=\"margin-bottom: 15px;\">\n" +
    "      <div class=\"col-md-3\"></div>\n" +
    "      <div class=\"col-md-1\">\n" +
    "        Уровень:\n" +
    "      </div>\n" +
    "      <div class=\"col-md-2\">\n" +
    "        <select multiple ng-multiple=\"true\">\n" +
    "          <option value=\"\">All</option>\n" +
    "          <option value=\"\">Beginner</option>\n" +
    "          <option value=\"\">Junior</option>\n" +
    "          <option value=\"\">Middle</option>\n" +
    "          <option value=\"\">Senior</option>\n" +
    "        </select>\n" +
    "      </div>\n" +
    "      <div class=\"col-md-1\">\n" +
    "        Тэги:\n" +
    "      </div>\n" +
    "      <div class=\"col-md-2\">\n" +
    "        <select multiple ng-multiple=\"true\">\n" +
    "          <option value=\"\">Lorem</option>\n" +
    "          <option value=\"\">Ipsum</option>\n" +
    "          <option value=\"\">Amet</option>\n" +
    "          <option value=\"\">Dolorem</option>\n" +
    "          <option value=\"\">Elit</option>\n" +
    "        </select>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "    <table class=\"table table-bordered table-hover\" style=\"width: 700px;\">\n" +
    "      <thead>\n" +
    "        <th>Название</th>\n" +
    "        <th>Рейтинг</th>\n" +
    "        <th>Прошли</th>\n" +
    "        <th>Комментарии</th>\n" +
    "        <th>Уровень</th>\n" +
    "      </thead>\n" +
    "      <tr dir-paginate=\"test in tests | itemsPerPage: 15\">\n" +
    "        <td><a href=\"#/tests/{{test.id}}\">{{test.id}}</a></td>\n" +
    "        <td>{{test.rating}}</td>\n" +
    "        <td>{{test.successfulAttempts}}</td>\n" +
    "        <td>{{test.topLevelCommentsId.length}}</td>\n" +
    "        <td>{{test.level}}</td>\n" +
    "      </tr>\n" +
    "    </table>\n" +
    "    <div class=\"text-center\">\n" +
    "      <dir-pagination-controls template-url=\"bower_components/angular-utils-pagination/dirPagination.tpl.html\">\n" +
    "      </dir-pagination-controls>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/users/_as_guest.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/_as_guest.html",
    "<h3 class=\"user-header\">{{user.fullName || user.username}}</h3>\n" +
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-3 text-center\" style=\"border-right: 1px solid #eaeaea\">\n" +
    "      <div class=\"user-image-wrap\">\n" +
    "        <img ng-src=\"{{!user.imageUrl ? 'assets/img/user_photo_male.png' : user.imageUrl }}\" class=\"img-thumbnail user-img\">\n" +
    "      </div>\n" +
    "      <h2><b>{{user.rating}}</b></h2>\n" +
    "      <h5>рейтинг</h5>\n" +
    "  </div>\n" +
    "  <div class=\"col-md-9\">\n" +
    "      <table class=\"left-align\">\n" +
    "          <tbody>\n" +
    "          <tr>\n" +
    "              <td class=\"text-muted\"><i>О пользователе&nbsp;&nbsp;</i></td>\n" +
    "              <td>Никнейм:</td>\n" +
    "              <td>{{user.username}}</td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td></td>\n" +
    "            <td>Откуда:</td>\n" +
    "            <td>\n" +
    "                <img ng-src=\"assets/img/flags/16/{{user.country + '.png'}}\" ng-if=\"user.country\">\n" +
    "                <span class=\"glyphicon glyphicon-question-sign\" ng-if=\"!user.country\"></span>\n" +
    "                <small>{{user.country}}</small>\n" +
    "            </td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td></td>\n" +
    "            <td>Зарегистрирован:</td>\n" +
    "            <td>{{user.creationDate | datestamp}}</td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td></td>\n" +
    "            <td>Последний визит:</td>\n" +
    "            <td>{{user.lastVisit | datestamp}} {{user.lastVisit | timestamp}}</td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td class=\"text-muted\"><i>Статистика</i></td>\n" +
    "            <td style=\"width:150px;\">Задач решено:</td>\n" +
    "            <td>\n" +
    "              {{totalItemsSolved || 0}}\n" +
    "            </td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td></td>\n" +
    "            <td>Задач добавлено:</td>\n" +
    "            <td>\n" +
    "              {{totalItemsAdded || 0}}\n" +
    "            </td>\n" +
    "          </tr>\n" +
    "        </tbody>\n" +
    "      </table>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div class=\"row\" style=\"border-top: 1px solid #EEE; margin-top: 10px;\">\n" +
    "  <div ng-include src=\"'partials/users/_user_tabs.html'\"></div>\n" +
    "</div>");
}]);

angular.module("partials/users/_as_user.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/_as_user.html",
    "<div class=\"row\">\n" +
    "  <div class=\"col-md-3\" style=\"border-right: 1px solid #eaeaea\">\n" +
    "    <section class=\"text-center\">\n" +
    "      <div class=\"user-image-wrap\">\n" +
    "        <img ng-src=\"{{!user.imageUrl ? 'assets/img/user_photo_male.png' : user.imageUrl }}\" class=\"img-thumbnail user-img\">\n" +
    "      </div>\n" +
    "      <input class=\"btn btn-default\" ng-click=\"openImageUpdateDialog()\" \n" +
    "               value=\"Изменить фото\" ng-if=\"equalsTo(user.username)\" />\n" +
    "    </section>\n" +
    "    <section style=\"margin-top: 15px;\">\n" +
    "        <table class=\"left-align\">\n" +
    "          <thead>\n" +
    "            <th colspan='2'>Инфо:</th>\n" +
    "          </thead>\n" +
    "          <tbody>\n" +
    "          <tr>\n" +
    "            <td style=\"width:85px;\">Имя(логин):</td>\n" +
    "            <td>{{user.username}}</td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td>Эл.почта:</td>\n" +
    "            <td>{{user.email}}</td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td>Страна:</td>\n" +
    "            <td>\n" +
    "              <img ng-src=\"assets/img/flags/16/{{user.country + '.png'}}\" ng-if=\"user.country\">\n" +
    "              <span class=\"glyphicon glyphicon-question-sign\" ng-if=\"!user.country\"></span>\n" +
    "              &nbsp;<small>{{user.country}}</small>\n" +
    "            </td>\n" +
    "          </tr>\n" +
    "        </tbody>\n" +
    "      </table>\n" +
    "      <div style=\"margin: 10px 0 10px; color: red; line-height: 20px;\">\n" +
    "        <div ng-if=\"!user.isNonReadOnly\">\n" +
    "          <div style=\"\">Доступ: Read-only</div>\n" +
    "        </div>\n" +
    "        <div ng-if=\"!user.isEnabled\">\n" +
    "          <div style=\"\">Статус: Пользователь забанен</div>\n" +
    "        </div>\n" +
    "      </div>\n" +
    "      <div style=\"margin-bottom: 25px;\" class=\"text-center\" \n" +
    "            ng-if=\"equalsTo(user.username)\">\n" +
    "        <input class=\"btn btn-default\" ng-click=\"openUpdateDialog()\" \n" +
    "               value=\"Редактировать\" />\n" +
    "      </div>\n" +
    "    </section>\n" +
    "    <section>\n" +
    "      <table class=\"left-align\">\n" +
    "          <thead>\n" +
    "            <th colspan='2'>Статистика:</th>\n" +
    "          </thead>\n" +
    "          <tbody>\n" +
    "          <tr>\n" +
    "            <td style=\"width:150px;\">Задач решено:</td>\n" +
    "            <td>\n" +
    "              {{totalItemsSolved || 0}}\n" +
    "            </td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td>Задач добавлено:</td>\n" +
    "            <td>\n" +
    "              {{totalItemsAdded || 0}}\n" +
    "            </td>\n" +
    "          </tr>\n" +
    "          <tr>\n" +
    "            <td>Рейтинг:</td>\n" +
    "            <td>{{user.rating}}</td>\n" +
    "          </tr>\n" +
    "        </tbody>\n" +
    "      </table>\n" +
    "    </section>\n" +
    "  </div>\n" +
    "  <div class=\"col-md-9\">\n" +
    "    <div ng-include src=\"'partials/users/_user_tabs.html'\"></div>\n" +
    "  </div>\n" +
    "</div>");
}]);

angular.module("partials/users/_user_added_tasks.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/_user_added_tasks.html",
    "<!-- Задачи добавленные пользователем -->\n" +
    "<div>\n" +
    "  <table class=\"table table-hover table-condensed\">\n" +
    "    <thead>\n" +
    "      <th>#</th>\n" +
    "      <th style=\"text-align: left;\">\n" +
    "          <a ng-click=\"sortBy('taskName', 'added')\" class=\"sort-button\">\n" +
    "            Название\n" +
    "            <span ng-show=\"predicate == 'taskName'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <th>\n" +
    "          <a ng-click=\"sortBy('rating', 'added')\" class=\"sort-button\">\n" +
    "            Рейтинг\n" +
    "            <span ng-show=\"predicate == 'rating'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <th>\n" +
    "          <a ng-click=\"sortBy('level', 'added')\" class=\"sort-button\">\n" +
    "            Уровень\n" +
    "            <span ng-show=\"predicate == 'level'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <th>\n" +
    "          <a ng-click=\"sortBy('creationDate', 'added')\" class=\"sort-button\">\n" +
    "            Добавлена\n" +
    "            <span ng-show=\"predicate == 'creationDate'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <th ng-if=\"equalsTo(user.username)\"></th>\n" +
    "    <!--  <th ng-if=\"equalsTo(user.username)\">Статус</th>-->\n" +
    "    </thead>\n" +
    "    <tbody ng-if=\"tasksAdded.length > 0\">\n" +
    "      <tr ng-repeat=\"task in tasksAdded\">\n" +
    "        <td>{{itemIndex}}</td>\n" +
    "        <td style=\"text-align: left;\">\n" +
    "          <a href=\"#/tasks/{{task.id}}\">\n" +
    "              {{task.taskName.substring(0, 100)}}\n" +
    "          </a>\n" +
    "        </td>\n" +
    "        <td>{{task.rating}}</td>\n" +
    "        <td>{{task.level}}</td>\n" +
    "        <td>{{task.creationDate | datestamp }}</td>\n" +
    "        <td ng-if=\"equalsTo(user.username)\">\n" +
    "          <a href=\"/#/tasks/{{task.id}}/edit\" class=\"btn btn-xs btn-warning\" style=\"color: #FFF\">\n" +
    "            Edit\n" +
    "          </a>\n" +
    "        </td>\n" +
    "       <!-- <td ng-if=\"equalsTo(user.username)\"></td>-->\n" +
    "      </tr>\n" +
    "    </tbody>\n" +
    "    <tbody ng-if=\"tasksAdded.length == 0\">\n" +
    "      <tr>\n" +
    "        <td colspan=\"5\" class=\"text-center\">\n" +
    "          <span ng-if=\"equalsTo(user.username)\">\n" +
    "            Вы еще не добавили ни одной задачи.\n" +
    "          </span>\n" +
    "          <span ng-if=\"!equalsTo(user.username)\">\n" +
    "            Пользователь не добавил ни одной задачи.\n" +
    "          </span>\n" +
    "        </td>\n" +
    "      </tr>\n" +
    "    </tbody>\n" +
    "  </table>\n" +
    "  <div class=\"text-center\" ng-if=\"totalItemsAdded > itemsPerPage\">\n" +
    "      <pagination boundary-links=\"true\" total-items=\"totalItemsAdded\" ng-model=\"currentPageAdded\"\n" +
    "                  items-per-page=\"itemsPerPage\" class=\"pagination-sm\" previous-text=\"&lsaquo;\" next-text=\"&rsaquo;\"\n" +
    "                  first-text=\"&laquo;\" last-text=\"&raquo;\" ng-change=\"pageChanged('added', currentPageAdded)\">\n" +
    "      </pagination>\n" +
    "  </div>\n" +
    "  <!-- <div class=\"text-center\" ng-if=\"equalsTo(user.username)\">\n" +
    "    <a href=\"#/tasks/new\" class=\"btn btn-info\">Добавить новую задачу</a>\n" +
    "  </div> -->\n" +
    "</div>");
}]);

angular.module("partials/users/_user_tabs.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/_user_tabs.html",
    "<div style=\"padding: 15px 0 15px;\">\n" +
    "  <label>Теги:</label>\n" +
    "  <span class=\"glyphicon glyphicon-tags\"></span>&nbsp;\n" +
    "  <a ng-repeat=\"tag in tags\" href=\"#/tasks?tags={{tag.name}}\"\n" +
    "     class=\"btn btn-default\" style=\"margin-right: 5px;\"\n" +
    "     ng-class=\"{'btn-xs': tag.count < 10,\n" +
    "                'btn-sm': 10 <= tag.count && tag.count < 20,\n" +
    "                'btn-lg': tag.count > 30}\">\n" +
    "    {{tag.name}} <span class=\"badge badge-info\">{{tag.count}}</span>\n" +
    "  </a>\n" +
    "</div>\n" +
    "\n" +
    "<tabset class=\"tab-animation\">\n" +
    "  <tab>\n" +
    "    <tab-heading>\n" +
    "      Добавленные задачи\n" +
    "    </tab-heading>\n" +
    "    <div ng-include src=\"'partials/users/_user_added_tasks.html'\"\n" +
    "         ng-class=\"{'col-md-8': !equalsTo(user.username)}\"></div>\n" +
    "  </tab>\n" +
    "  <tab>\n" +
    "    <tab-heading>\n" +
    "      Решенные задачи\n" +
    "    </tab-heading>\n" +
    "    <div ng-include src=\"'partials/users/_user_tasks.html'\"\n" +
    "         ng-class=\"{'col-md-8': !equalsTo(user.username)}\"></div>\n" +
    "  </tab>\n" +
    "</tabset>");
}]);

angular.module("partials/users/_user_tasks.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/_user_tasks.html",
    "<!-- Задачи решенные пользователем -->\n" +
    "<div>\n" +
    "  <table class=\"table table-hover table-condensed\">\n" +
    "    <thead>\n" +
    "      <th>#</th>\n" +
    "      <th style=\"text-align: left;\">\n" +
    "          <a ng-click=\"sortBy('taskName', 'solved')\" class=\"sort-button\">\n" +
    "            Название\n" +
    "            <span ng-show=\"predicate == 'taskName'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <th>\n" +
    "          <a ng-click=\"sortBy('rating', 'solved')\" class=\"sort-button\">\n" +
    "            Рейтинг\n" +
    "            <span ng-show=\"predicate == 'rating'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <th>\n" +
    "          <a ng-click=\"sortBy('author', 'solved')\" class=\"sort-button\">\n" +
    "            Автор\n" +
    "            <span ng-show=\"predicate == 'author'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "      <!--<th ng-if=\"equalsTo(user.username)\">Статус\n" +
    "      </th>-->\n" +
    "      <th>\n" +
    "          <a ng-click=\"sortBy('level', 'solved')\" class=\"sort-button\">\n" +
    "            Уровень\n" +
    "            <span ng-show=\"predicate == 'level'\">\n" +
    "              <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "              <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "            </span>\n" +
    "          </a>\n" +
    "      </th>\n" +
    "    </thead>\n" +
    "    <tbody>\n" +
    "      <tr ng-repeat=\"task in tasksSolved\">\n" +
    "        <td>{{itemIndex}}</td>\n" +
    "        <td style=\"text-align: left;\">\n" +
    "          <a href=\"#/tasks/{{task.id}}\">\n" +
    "            {{task.taskName.substring(0, 100)}}\n" +
    "          </a>\n" +
    "        </td>\n" +
    "        <td>{{task.rating}}</td>\n" +
    "        <td><a href=\"#/users/{{task.author}}\">{{task.author}}</a></td>\n" +
    "       <!-- <td ng-if=\"equalsTo(user.username)\"></td>-->\n" +
    "        <td>{{task.level}}</td>\n" +
    "      </tr>\n" +
    "    </tbody>\n" +
    "      <tbody ng-if=\"tasksSolved.length == 0\">\n" +
    "      <tr>\n" +
    "        <td colspan=\"5\" class=\"text-center\">\n" +
    "          <span ng-if=\"equalsTo(user.username)\">\n" +
    "            Вы еще не решили ни одной задачи.\n" +
    "          </span>\n" +
    "          <span ng-if=\"!equalsTo(user.username)\">\n" +
    "            Пользователь не решил ни одной задачи.\n" +
    "          </span>\n" +
    "        </td>\n" +
    "      </tr>\n" +
    "      </tbody>\n" +
    "  </table>\n" +
    "  <div class=\"text-center\" ng-if=\"totalItemsSolved > itemsPerPage\">\n" +
    "      <pagination boundary-links=\"true\" total-items=\"totalItemsSolved\" ng-model=\"currentPageSolved\"\n" +
    "                  items-per-page=\"itemsPerPage\" class=\"pagination-sm\" previous-text=\"&lsaquo;\" next-text=\"&rsaquo;\"\n" +
    "                  first-text=\"&laquo;\" last-text=\"&raquo;\" ng-change=\"pageChanged('solved', currentPageSolved)\">\n" +
    "      </pagination>\n" +
    "  </div>\n" +
    "  <!-- <div class=\"text-center\" ng-if=\"equalsTo(user.username)\">\n" +
    "    <a href class=\"btn btn-info\">Решить случайную задачу</a>\n" +
    "  </div> -->\n" +
    "</div>");
}]);

angular.module("partials/users/user-profile.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/user-profile.html",
    "<div ng-include src=\"profileTemplateUrl\">\n" +
    "</div>");
}]);

angular.module("partials/users/users.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("partials/users/users.html",
    "<div class=\"columns\">\n" +
    "  <div class=\"best-items\">\n" +
    "    <form name=\"searchByName\" class=\"form-inline\" role=\"search\">\n" +
    "      <div class=\"input-group search-field has-feedback\">\n" +
    "        <span class=\"input-group-addon\">\n" +
    "          <span class=\"glyphicon glyphicon-search\"></span>\n" +
    "        </span>\n" +
    "        <input type=\"search\" size=\"15\" class=\"form-control\" placeholder=\"Поиск пользователя по имени...\"\n" +
    "                   autofocus ng-model=\"nameQuery\" />\n" +
    "        <span class=\"input-group-btn\">\n" +
    "          <button class=\"btn btn-default\" type=\"submit\" ng-click=\"pageChanged(currentPage)\">Искать!</button>\n" +
    "        </span>\n" +
    "      </div><!-- /input-group -->\n" +
    "      <div class=\"form-group\" ng-hide=\"!nameQuery.length\">\n" +
    "        <button class=\"btn btn-default\" ng-click=\"resetFilterName()\">\n" +
    "          <span class=\"glyphicon glyphicon-remove red\"></span>\n" +
    "        </button>\n" +
    "      </div>\n" +
    "    </form>\n" +
    "    <table class=\"table table-bordered table-condensed table-hover all-users\">\n" +
    "      <thead>\n" +
    "        <th>#</th>\n" +
    "        <th>\n" +
    "            <a ng-click=\"sortBy('country')\" class=\"sort-button\">\n" +
    "                Страна\n" +
    "                <span ng-show=\"predicate == 'country'\">\n" +
    "                    <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "                    <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "                </span>\n" +
    "            </a>\n" +
    "        </th>\n" +
    "        <th>\n" +
    "            <a ng-click=\"sortBy('username')\" class=\"sort-button\">\n" +
    "                Пользователь\n" +
    "                <span ng-show=\"predicate == 'username'\">\n" +
    "                    <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "                    <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "                </span>\n" +
    "            </a>\n" +
    "        </th>\n" +
    "        <th>\n" +
    "            <a ng-click=\"sortBy('rating')\" class=\"sort-button\">\n" +
    "                Рейтинг\n" +
    "                <span ng-show=\"predicate == 'rating'\">\n" +
    "                    <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "                    <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "                </span>\n" +
    "            </a>\n" +
    "        </th>\n" +
    "        <th>\n" +
    "            <a ng-click=\"sortBy('tasksSolved')\" class=\"sort-button\">\n" +
    "                Задач решено\n" +
    "                <span ng-show=\"predicate == 'tasksSolved'\">\n" +
    "                    <span ng-show=\"!reverse\" class=\"glyphicon glyphicon-arrow-up\"></span>\n" +
    "                    <span ng-show=\"reverse\" class=\"glyphicon glyphicon-arrow-down\"></span>\n" +
    "                </span>\n" +
    "            </a>\n" +
    "        </th>\n" +
    "        <th ng-if=\"isAuthorized()\" colspan=\"3\">\n" +
    "          Set role\n" +
    "        </th>\n" +
    "        <th ng-if=\"isAuthorized()\">Ban</th>\n" +
    "        <th ng-if=\"isAuthorized()\">Delete</th>\n" +
    "      </thead>\n" +
    "      <tr ng-repeat=\"user in users\" id=\"user-{{user.username}}\">\n" +
    "        <td>{{ users.indexOf(user) + 1 + itemsPerPage*(currentPage -1) }}</td>\n" +
    "        <td>\n" +
    "            <span ng-if=\"user.country\">\n" +
    "              <img ng-src=\"assets/img/flags/24/{{user.country + '.png'}}\">\n" +
    "            </span>\n" +
    "            <span ng-if=\"!user.country\">\n" +
    "              <span class=\"glyphicon glyphicon-question-sign\"></span>\n" +
    "            </span>\n" +
    "        </td>\n" +
    "        <td><a href=\"#/users/{{user.username}}\">{{user.username}}</a></td>\n" +
    "        <td>{{1*user.rating}}</td>\n" +
    "        <td>{{user.tasksSolved}}</td>\n" +
    "        \n" +
    "        <td ng-if=\"!equalsTo(user.username) && (isAuthorized())\">\n" +
    "          <span class=\"glyphicon glyphicon-ok\" ng-show=\"updated_user == user.username\" id=\"updated_user\">\n" +
    "          </span>\n" +
    "        </td>\n" +
    "        <td ng-if=\"!equalsTo(user.username) && (isAuthorized())\">\n" +
    "          <select multiple ng-model=\"user.roles\" ng-options=\"rol for rol in roles\" ng-change=\"change()\"></select>\n" +
    "        </td>\n" +
    "        <td ng-if=\"!equalsTo(user.username) && (isAuthorized())\">\n" +
    "          <input type=\"button\" class=\"btn btn-xs btn-success\" ng-click=\"changeRole(user)\" value=\"Set\" />\n" +
    "        </td>\n" +
    "        \n" +
    "        <td ng-if=\"!equalsTo(user.username) && (isAuthorized())\">\n" +
    "          <div ng-switch on=\"user.isEnabled\">\n" +
    "            <div ng-switch-when=\"false\">\n" +
    "              <button type=\"button\" class=\"btn btn-xs btn-success\" ng-click=\"banUser(user)\">\n" +
    "                Unban\n" +
    "              </button>\n" +
    "            </div>\n" +
    "            <div ng-switch-default>\n" +
    "              <button type=\"button\" class=\"btn btn-xs btn-danger\" ng-click=\"banUser(user)\">\n" +
    "                Ban\n" +
    "              </button>\n" +
    "            </div>\n" +
    "          </div>\n" +
    "        </td>\n" +
    "        \n" +
    "        <td ng-if=\"!equalsTo(user.username) && (isAuthorized())\">\n" +
    "          <button type=\"button\" class=\"btn btn-xs btn-danger\" confirmed-click=\"deleteUser(user)\" ng-confirm-click=\"Удалить пользователя '{{user.username}}'?\">\n" +
    "            Delete\n" +
    "          </button>\n" +
    "        </td>\n" +
    "      </tr>\n" +
    "    </table>\n" +
    "    <div class=\"text-center\" ng-if=\"totalItems > itemsPerPage\">\n" +
    "        <pagination boundary-links=\"true\" total-items=\"totalItems\" ng-model=\"currentPage\"\n" +
    "                    items-per-page=\"itemsPerPage\" class=\"pagination-sm\" previous-text=\"&lsaquo;\" next-text=\"&rsaquo;\"\n" +
    "                    first-text=\"&laquo;\" last-text=\"&raquo;\" ng-change=\"pageChanged(currentPage)\">\n" +
    "        </pagination>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>");
}]);
