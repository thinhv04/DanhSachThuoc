var app = angular.module('quanlybenhvienApp',[]);
app.controller('VaiTroController', function($scope,$http)
{
    $scope.VaiTroList = [];
    $scope.VaiTro = {};

    $scope.getVaiTro = function () {
        $http.get('/api/vaitro').then(function (response) {
            $scope.VaiTroList = response.data;
        })
    }
    $scope.getVaiTro();
});
