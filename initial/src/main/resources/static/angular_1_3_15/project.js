angular.module('project', ['firebase', 'ngRoute'])
 
.value('fbURL', 'https://sb-plnkr.firebaseio.com/so:28942661')
.service('fbRef', function(fbURL) {
  return new Firebase(fbURL)
})
.service('fbAuth', function($q, $firebaseAuth, fbRef) {
  var auth;
  return function () {
      if (auth) return $q.when(auth);
      var authObj = $firebaseAuth(fbRef);
      if (authObj.$getAuth()) {
        return $q.when(auth = authObj.$getAuth());
      }
      var deferred = $q.defer();
      authObj.$authAnonymously().then(function(authData) {
          auth = authData;
          deferred.resolve(authData);
      });
      return deferred.promise;
  }
})
.service('Projects', function($q, $firebaseArray, fbRef) {
    this.sync = $firebaseArray(fbRef);
    this.sync.$loaded().then(function(data) {
      var projects = data;
    });
    return this.sync;
  })
 
.config(function($routeProvider) {
  $routeProvider
    /*.when('/', {
      controller:'ProjectListController as projectList',
      templateUrl:'list.html',
      resolve: {
        projects: function (Projects) {
          return Projects.fetch();
        }
      }
    }) */
    .when('/', {
        controller:'ProjectListController as projectList',
        templateUrl:'list.html'
    })
    .when('/edit/:projectId', {
      controller:'EditProjectController as editProject',
      templateUrl:'detail.html'
    })
    .when('/new', {
      controller:'NewProjectController as editProject',
      templateUrl:'detail.html'
    })
    .otherwise({
      redirectTo:'/'
    });
})
 
.controller('ProjectListController', function($scope, $location, Projects) {
  Projects.$loaded().then(function(data){
    $scope.projects = data;
    console.log('LOADED '+$scope.projects.length);
  });

})
 
.controller('NewProjectController', function($location, Projects) {
  var editProject = this;
  editProject.save = function() {
      Projects.$add(editProject.project).then(function(data) {
          $location.path('/');
      });
  };
})
 
.controller('EditProjectController',
  function($location, $routeParams, Projects) {
    var editProject = this;
    var projectId = $routeParams.projectId,
        projectIndex;
 
    editProject.projects = Projects.projects;
    projectIndex = editProject.projects.$indexFor(projectId);
    editProject.project = editProject.projects[projectIndex];
 
    editProject.destroy = function() {
        editProject.projects.$remove(editProject.project).then(function(data) {
            $location.path('/');
        });
    };
 
    editProject.save = function() {
        editProject.projects.$save(editProject.project).then(function(data) {
           $location.path('/');
        });
    };
});