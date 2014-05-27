function Hello($scope, $http) {
    $http.get('http://localhost:8080/gs-rest-service-0.1.0/get-all-kittypes').
        success(function(data) {
            $scope.greeting = data;
        });
}