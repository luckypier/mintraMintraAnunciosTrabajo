angular.module('project', ['mongolab','ngResource']).
  config(function($routeProvider) {
    $routeProvider.
      when('/', {controller:ListCtrl, templateUrl:'list.html'}).
      when('/edit/:projectId', {controller:EditCtrl, templateUrl:'detail.html'}).
      when('/new', {controller:CreateCtrl, templateUrl:'detail.html'}).
      otherwise({redirectTo:'/'});
  });
 
 
function ListCtrl($scope, $resource, $http, Project) {

    $scope.projects = Project.query({},{});
    /*
    $scope.projects = [
        {"site":"site", "name":"name", "description":"description", "id":"id"},
        {"site":"site2", "name":"name2", "description":"description2", "id":"id2"}
    ]*/

    /*
    $http.get('http://localhost:8080/todo').success(function(data) {
        $scope.projects = data;
    });
*/

}
 
 
function CreateCtrl($scope, $location, Project) {
  $scope.save = function() {
    Project.save($scope.project, function(project) {
      $location.path('/edit/' + project._id.$oid);
    });
  }
}
 
 
function EditCtrl($scope, $location, $routeParams, Project) {
    var self = this;


    console.log('==== '+$routeParams.projectId);

    /*
    Project.get(
        {id: $routeParams.projectId}, function(project) {
            self.original = project;
            $scope.project = new Project(self.original);
        }
    );
    */

    Project.get(
            {'toDoId': $routeParams.projectId}, function(project) {
                self.original = project;
                $scope.project = new Project(self.original);
            }
        );

    $scope.isClean = function () {
        return angular.equals(self.original, $scope.project);
    }

    $scope.destroy = function () {
        self.original.destroy(function () {
            $location.path('/list');
        });
    };

    $scope.save = function () {
        $scope.project.update(function () {
            $location.path('/');
        });
    };
}

angular.module('myApp.services', ['ngResource'])
    .factory('AngularIssues', function($resource){
        return $resource('https://api.github.com/repos/angular/angular.js/issues', {})
    })
    .value('version', '0.1');