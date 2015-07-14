// Karma configuration
// Generated on Thu Jan 15 2015 13:34:59 GMT+0500 (YEKT)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: './',

    // list of files / patterns to load in the browser
    files: [
      '../build/taskbook-js/js/vendor.js',
      'http://www.google.com/jsapi',
      '../build/taskbook-js/js/app.js',
      '../app/vendor/angular-mocks/angular-mocks.js',
      'customMatchers.js',
      'karma-helper.js',
      'examples/**/*.js'
    ],

    // list of files to exclude
    exclude: [],

    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],

    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['PhantomJS'],

    plugins: [
      'karma-chrome-launcher',
      'karma-jasmine',
      'karma-phantomjs-launcher',
      'karma-coverage'
    ],

    // coverage reporter generates the coverage
    reporters: ['progress', 'coverage'],

    preprocessors: {
      '../build/taskbook-js/js/app.js': ['coverage']
    },

    // optionally, configure the reporter
    coverageReporter: {
      type : 'html',
      dir : 'coverage/'
    },

    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,

    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_ERROR,

    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
