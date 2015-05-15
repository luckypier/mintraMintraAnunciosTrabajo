// This is a module for cloud persistance in mongolab - https://mongolab.com
angular.module('mongolab', ['ngResource']).
    factory('Project', function($resource) {
      /*
      var Project = $resource('https://api.mongolab.com/api/1/databases' +
          '/projects/collections/projects/:id',
          { apiKey: 'UBUT50WTOMDQDYnBGplI-KHgJdvctIxw' }, {
            update: { method: 'PUT' }
          }
      );*/

        var Project = $resource('/todo/:toDoId', {toDoId:'@id'} );

 
      Project.prototype.update = function(cb) {
        return Project.update({toDoId: this.id},
            angular.extend({}, this, {_id:undefined}), cb);
      };

      Project.prototype.destroy = function(cb) {
        return Project.remove({id: this._id.$oid}, cb);
      };
 
      return Project;
    });