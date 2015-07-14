module.exports = {
  acceptAlert: function () {
    browser.switchTo().alert().
      then(
        function (alert) {
          alert.accept();
        },
        function (error) {
        });
  },
  signIn: function(login, password) {
    element(by.buttonText('Вход')).click();

    element(by.model('login_try.username')).sendKeys(login);
    element(by.model('login_try.password')).sendKeys(password);

    element(by.buttonText('Войти')).click();

    browser.sleep(1000);
  },
  signOut: function() {
    element(by.buttonText('Выход')).click();
  },
  fillRegForm: function(username, email, password) {
    element(by.model('new_user.username')).sendKeys(username);
    element(by.model('new_user.email')).sendKeys(email);
    element(by.model('new_user.password')).sendKeys(password);
    element(by.model('password_verify')).sendKeys(password);
  }
};