(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js";
	  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

var loginsettingsApp = angular.module('loginsettingsApp', ['ngRoute']);

loginsettingsApp.run(['$window', function($window){
	console.log("RUN");
	$(document).bind('fbInit',function(){
	    console.log('fbInit complete; FB Object is Available');
	});
	
	$window.fbAsyncInit = function(){
	    FB.init({
	      appId      : '144098182659869', // App ID
	      channelUrl : 'http://dibs.intec.ugent.be:8080/login.html', // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true,  // parse XFBML
	      version    : 'v2.5' // use graph api version 2.5
	    });
	    
	    console.log("FB_INIT");
	    $(document).trigger('fbInit'); // trigger event
	    
	    /*$http
	    .get('/browseUsers', {
	    	params:{
	    		id:uid
	    	}
	     })
	     .success(function (data,status) {
	    	 console.log("SUCCES");
	    	 console.log(data);
	    	// return data;
	     });*/
	};
}]);

loginsettingsApp.factory('facebookService', function($q) {
	window.fbAsyncInit();
	console.log("FACTORY");
	
	return {
        getInfo: function() {
            var deferred = $q.defer();
            FB.api('/me', {
                fields: 'id, first_name, last_name, email'
                
            }, function(response) {
                if (!response || response.error) {
                    deferred.reject('Error occured');
                    console.log("error");
                } else {
                    deferred.resolve(response);
                    console.log("success");
                }
                
            });
            return deferred.promise;
        }
    };
    	/*getMyStatus: function() {
    		var deferred = $q.defer();
	    	FB.api("/{status-id}",
    			if (!response || response.error) {
                    deferred.reject('Error occured');
                    console.log("error");
                } else {
                    deferred.resolve(response);
                    console.log("success");
                }
    		);
            return deferred.promise;
    	}*/
});

loginsettingsApp.controller('loginsettingsCtrl', function($scope,facebookService){
	console.log("CONTROLLER");
	/*$scope.checkLoginState = function() {
		var status = facebookService.getMyStatus();
		if(status == 'connected'){
			facebookService.getInfo() 
		     .then(function(response) {
		       $scope.id = response.id;
		       console.log(response);
		     }
		    );
		}
	}*/
	
	$scope.getMyFirstName = function(){
		console.log("TEST");
		facebookService.getInfo() 
		     .then(function(response) {
		       $scope.first_name = response.first_name;
		       console.log(response);
		     }
	    );
	}
	
	$scope.getMyLastName = function(){
		facebookService.getInfo() 
		     .then(function(response) {
		       $scope.last_name = response.last_name;
		       console.log(response);
		     }
	    );
	}
	
	$scope.getMyEmail = function(){
		facebookService.getInfo() 
		     .then(function(response) {
		       $scope.email = response.email;
		       console.log(response);
		     }
	    );
	}
	
	$scope.change = function(){
		console.log('Tot hier zijn we dan geraakt');
		$scope.email = $scope.user.email;
	}
	
	$scope.submit = function(){
		console.log('Hoe gaan we da nu doen met die posts?');
		//Go to profile
	}
});

//return window.location.href = "index";

/* logout = function() {
	  var _self = this;
	  FB.logout(function(response) {
	    $rootScope.$apply(function() { 
	      $rootScope.user = _self.user = {}; 

	    }); 
	 });
  }*/
