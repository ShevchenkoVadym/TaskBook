beforeEach(function () {
  jasmine.addMatchers({
    toEqualData: function(util, customEqualityTesters) {
      return {
        compare:
          function(actual, expected) {
            return {
              pass:
                angular.equals(actual, expected)
            };
          }
      };
    }
  });
});