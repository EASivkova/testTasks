var app = angular.module('UserDirectoryApp', [ ]);

function PeopleCtrl($scope, $http, $log) {
	
	$scope.Users = [];
	$scope.UsersEdit = [];

	$scope.loadPeople = function() {
        var httpRequest = $http({
            method: 'GET',
            url: 'rest/users/list.json'
        }).success(function(data, status, headers, config) {
            $log.info(status);
			$scope.Users = data;
        });
    };

    $scope.loadPeople();
    
    $scope.savePeople = function() {
        $log.info('start save');
        var httpRequest = $http({
            method: 'PUT',
            url: 'rest/users/save.json',
            data: $scope.Users
        }).success(function(status) {
            $log.info(status);
			$scope.loadPeople();
        });
    };

    $scope.addPerson = function() {
        $log.info('start add');
    	var person = new Object();
    	person.fio = $scope.fioText;
        $scope.fioText = '';
    	person.login = $scope.loginText;
        $scope.loginText = '';
    	person.password = $scope.passwordText;
        $scope.passwordText = '';
        var httpRequest = $http({
            method: 'POST',
            url: 'rest/users/add.json',
            data: person
        }).success(function(status) {
            $log.info(status);
			$scope.loadPeople();
        });
    };

    $scope.deletePerson = function(person){
        $log.info('start delete');
		var httpRequest = $http({
	        method: 'POST',
	        url: 'rest/users/delete.json',
	        data: person
	    }).success(function(status) {
	        $log.info(status);
			$scope.loadPeople();
	    });
	};
    
}
