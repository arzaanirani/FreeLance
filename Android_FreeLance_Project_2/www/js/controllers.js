var projects = [{
    "name" : "Charcuterieboards.com",
    "id": 0,
    "timeline": "May 2015-June 2015",
    "technologies": "HTTP, TCP, Network Layer, Routers",
    "detail": "Charcuterieboards.com, an e commerce website selling organic shaped charcuterie boards and cutting boards. The website is running on shopify platform."  },
  {
    "name" : "Sonoplace",
    "id": 1,
    "timeline": "Jan 2014-Feb 2014",
    "technologies": "Nodejs, Express JS, Javascript, PassportJS"
  },
  {
    "name" : "Jessicagagne.ca",
    "id": 2,
    "timeline": "March 2012-July 2012",
    "technologies": "Ionic, Angular, Cordova, Apache, Android"
  }];

angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('HomeCtrl', function($scope) {

})

.controller('ProjectCtrl', function($scope){
  $scope.projects = projects;
})

.controller('ProjectDetailCtrl', function($scope, $stateParams){
  $scope.getProjectDetail = function(id){
    return projects[id];
  }

  $scope.project = $scope.getProjectDetail($stateParams.id);
});
