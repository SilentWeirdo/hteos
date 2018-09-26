module.exports = function (grunt) {
    var path = require('path');
    grunt.initConfig({
        //Read the package.json (optional)
        pkg: grunt.file.readJSON('package.json'),
        banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - ' +
        '<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>' +
        '<%= pkg.homepage ? "* " + pkg.homepage + "\\n" : "" %>' +
        '* Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %> - <%= pkg.author.email %>\n' +
        'License On ：<%=pkg.license%> */\n',
        meta: {},
        concat: {
            js: {
                src: ['css/preloader.css','css/index.css','css/store.css'],
                dest: 'dist/css/app.all.css'
            },
            css: {
                src: ['js/config/dist.js', 'js/app.js','js/controller/*.js', 'js/storage/Remote.js'],
                dest: 'dist/js/app.all.js'
            }
        },
        copy: {
            html: {
                files: [
                    {
                        expand: true,
                        cwd: 'html',
                        src: ['**'],
                        dest: 'dist'
                    }
                ]
            },
            fonts:{
                files: [
                    {
                        expand: true,
                        cwd: 'fonts',
                        src: ['**'],
                        dest: 'dist/fonts'
                    }
                ]
            },
            images:{
                files: [
                    {
                        expand: true,
                        cwd: 'images',
                        src: ['**'],
                        dest: 'dist/images'
                    }
                ]
            },
            tpl:{
                files: [
                    {
                        expand: true,
                        cwd: 'tpl',
                        src: ['**'],
                        dest: 'dist/tpl'
                    }
                ]
            }
        },
        uglify: {
            options: {
                banner: '<%=banner%>'
            },
            dev: {
                files: {
                    'dist/js/app.min.js': ['dist/js/app.all.js']
                }
            }
        },
        cssmin: {
            target: {
                files: {
                    'dist/css/app.min.css': ['dist/css/app.all.css']
                }
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');

    grunt.registerTask('dist', ['concat', 'copy', 'uglify', 'cssmin']);

}