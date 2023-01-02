var app = angular.module("app", []);

app.controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/api/v1';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.totalPages = response.data.totalPages;
                $scope.productList = response.data.content;
                $scope.pageNumber = response.data.pageable.pageNumber ? 1 + response.data.pageable.pageNumber : 1;
            });
    };

    $scope.loadCart = function () {
        $http.get(contextPath + '/cart')
            .then(function (response) {
                $scope.orderList = response.data;
            });
    };

    $scope.addToCart = function (productId, name, price) {
        const orderDto = {
            productId: productId,
            name: name,
            price: price
        };
        $http({
            url: contextPath + '/cart',
            method: 'POST',
            dataType: 'json',
            data: orderDto,
            headers: { "Content-Type": "application/json" }
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.addOne = function (productId) {
        $http({
            url: contextPath + '/cart/incr/' + productId,
            method: 'PUT'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeOne = function (productId) {
        $http({
            url: contextPath + '/cart/decr/' + productId,
            method: 'PUT',
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.deleteOrder = function (productId) {
        $http.delete(contextPath + '/cart/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.filterPrice = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                p: $scope.p,
                min_price: $scope.minPrice,
                max_price: $scope.maxPrice,
                name_part: $scope.namePart
            }
        }).then(function (response) {
            $scope.totalPages = response.data.totalPages;
            $scope.productList = response.data.content;
            $scope.pageNumber = response.data.pageable.pageNumber ? 1 + response.data.pageable.pageNumber : 1;
        });
    }
    $scope.loadProducts();
    $scope.loadCart();

});
