function Hello($scope, $http) {
    $http.get('/getPlekInRij').
        success(function(data) {
            $scope.event = data;
        });
}
