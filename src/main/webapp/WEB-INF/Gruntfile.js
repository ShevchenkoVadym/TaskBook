'use strict';

module.exports = function(grunt) {

  // Load grunt tasks automatically
  require('load-grunt-tasks')(grunt);

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    bower: {
      install: {
        options: {
          targetDir: 'tmp/vendor',
          layout: 'byType',
          bowerOptions: {
            forceLatest: true
          }
        }
      }
    },
    html2js: {
      options: {
        base: 'app/'
      },
      dist: {
        src: [ 'app/partials/**/*.html' ],
        dest: 'dist/templates.js'
      }
    },
    concat: {
      vendor: {
        options: {
          separator: ';'
        },
        files: {
          'dist/vendor.js': [
            'app/vendor/jquery/*.js',
            'app/vendor/angular/*.min.js',
            'app/vendor/angular-route/*.js',
            'app/vendor/angular-resource/*.js',
            'app/vendor/angular-cookies/*.js',
            'app/vendor/angular-animate/*.js',
            'app/vendor/other/**/*.js'
          ]
        }
      },
      js: {
        options: {
          separator: ';'
        },
        files: {
          'dist/app.js': [
            'app/js/app.js',
            'dist/coffee.js',
            'dist/templates.js'
          ],
          'build/taskbook-js/js/app.js': [
            'app/js/app.js',
            'dist/coffee.js',
            'dist/templates.js'
          ]
        }
      },
      css: {
        src: [
          'app/assets/css/bootstrap.css',
          'app/assets/css/font-awesome.min.css',
          'app/assets/css/vendor/*.css',
          'app/assets/css/404.css',
          'app/assets/css/app.css'
        ],
        dest: 'dist/styles.css'
      }
    },
    coffee: {
      compile: {
        options: {
          join: true
        },
        files: {
          'dist/coffee.js': ['app/js/**/*.coffee']
        }
      }
    },
    uglify: {
      dist: {
        files: {
          'dist/app.js': [ 'dist/app.js' ]
        },
        options: {
          mangle: false
        }
      }
    },
    cssmin: {
      css: {
        src: [ 'dist/styles.css' ],
        dest: 'dist/styles.min.css'
      },
      dev: {
        src: [ 'dist/styles.css' ],
        dest: 'build/taskbook-js/assets/css/styles.min.css'
      }
    },
    clean: {
      temp: {
        src: [ 'tmp' ]
      }
    },
    watch: {
      js: {
        files: ['app/js/**/*.js'],
        tasks: ['concat:js'],
        options: {
          debounceDelay: 500
        }
      },
      coffee: {
        files: ['app/js/**/*.coffee'],
        tasks: ['coffee:compile', 'concat:js'],
        options: {
          debounceDelay: 500
        }
      },
      css: {
        files: ['app/assets/css/**/*.css'],
        tasks: ['concat:css', 'cssmin:dev'],
        options: {
          debounceDelay: 500
        }
      },
      html: {
        files: ['app/partials/**/*.html'],
        tasks: ['html2js:dist', 'concat:js'],
        options: {
          debounceDelay: 500
        }
      },
      data: {
        files: ['app/data/**/*.json'],
        tasks: ['copy:data'],
        options: {
          debounceDelay: 500
        }
      }
    },
    copy: {
      data: {
        files: [
          {expand: true, cwd: 'app/', src: ['data/**/*.json'], dest: 'build/taskbook-js/'}
        ]
      }
    },
    connect: {
      server: {
        options: {
          hostname: 'localhost',
          port: 9000,
          base: 'app/'
        }
      }
    },
    concurrent: {
      options: {
        logConcurrentOutput: true,
        limit: 5
      },
      dev: {
        tasks: ['watch:js', 'watch:coffee', 'watch:css', 'watch:html', 'watch:data']
      }
    },
    compress: {
      dist: {
        options: {
          archive: 'build/taskbook-js.zip'
        },
        files: [{
          expand: true,
          cwd: 'app/',
          src: [ 'index.html' ],
          dest: '/'
        }, {
          expand: true,
          cwd: 'dist/',
          src: [ 'app.js' ],
          dest: '/js/'
        }, {
          expand: true,
          cwd: 'dist/',
          src: [ 'vendor.js' ],
          dest: '/js/'
        }, {
          expand: true,
          cwd: 'app/',
          src: [ 'vendor/map/**' ],
          dest: '/js/'
        }, {
          expand: true,
          cwd: 'dist/',
          src: [ 'styles.min.css' ],
          dest: '/assets/css/'
        }, {
          expand: true,
          cwd: 'app/',
          src: [ 'assets/fonts/**' ],
          dest: '/'
        }, {
          expand: true,
          cwd: 'app/',
          src: [ 'assets/img/**' ],
          dest: '/'
        }, {
          expand: true,
          cwd: 'app/',
          src: [ 'data/**' ],
          dest: '/'
        }]
      }
    }
  });

  grunt.registerTask('build-minified', [  'html2js:dist', 'coffee:compile', 'concat:vendor', 'concat:js',
    'concat:css', 'uglify:dist', 'cssmin:css', 'clean:temp', 'compress:dist' ]);

  grunt.registerTask('build-dev', [  'html2js:dist', 'coffee:compile', 'concat:vendor', 'concat:js',
    'concat:css', 'cssmin:css', 'clean:temp', 'compress:dist' ]);

  grunt.registerTask('compile-html', [ 'html2js:dist' ]);

  grunt.registerTask('dev', ['concurrent:dev']);

  grunt.registerTask('build-vendor', ['concat:vendor']);
};
