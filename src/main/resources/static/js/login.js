(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js";
	  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

var loginApp = angular.module('loginApp', ['ngRoute']);

loginApp.run(['$rootScope', '$window', function($rootScope, $window, $routeProvider, $http){
	$window.fbAsyncInit = function($rootScope){
	    FB.init({
	      appId      : '1255620737799541', // App ID
	      channelUrl : 'http://dibs.intec.ugent.be:8080/login.html', // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true,  // parse XFBML
	      version    : 'v2.5' // use graph api version 2.5
	    });
	    
	    FB.getLoginStatus(function(response, $rootScope) {
		  if (response.status == 'connected') {
		    var uid = response.authResponse.userID;
		    var expiration = response.authResponse.expiresIn; // Als expiration date = 0, moet user uitgelogd worden.
		    console.log(expiration);
		    userInfo(uid); // Oproepen van de userinfo om te verzenden naar de backend
		  }
		});
	    
	    // Additional initialization code here
	};

	$window.userInfo = function(uid) {
	    FB.api('/me', {fields: 'name,email'}, function(response) {
	      console.log(JSON.stringify(response));
	      var name = response.name;
	      var email = response.email;
	      console.log(name);
	      console.log(email);
	      console.log(uid);
	    });
	    
	    
	    $http
	    .get('/browseUsers', {
	    	params:{
	    		id:uid
	    	}
	     })
	     .success(function (data,status) {
	    	 console.log("SUCCES");
	    	 console.log(data);
	    	// return data;
	     });
	};
}]);


loginApp.controller('loginCtrl', function($scope,$http) {
	$scope.login = function(){
		/* FIRST TRY */
		//$rootScope.email = $scope.email;
		if($scope.user.email == 'Moustapha.Ramachi@gmail.com' && $scope.user.password == 'Mousti'){
			//$rootScope.loggedIn = true;
			return window.location.href = "index";
		} else {
			alert('Incorrect email adress or password!');
		}
		/* SECOND TRY */
		//$http.post('/api/authenticate', { username: username, password: password })
        	//.success(function (response) {
        		//callback(response);
        	//});
	}
});

loginApp.controller('registerCtrl', function($scope,$http) {
	$scope.list = [];
	$scope.register = function(){
		if($scope.user.name && $scope.user.email1 && $scope.user.password1){
			if($scope.user.email1 == $scope.user.email2){
				if($scope.user.password1 == $scope.user.password2){
					//bla + check of nog niet in de lijst
		    			$scope.list.push($scope.user.name);
				} else {
					alert('Your password does not match!');
				}
			} else {
				alert('Your email adress does not match!');
			}
		} else {
			alert('Please fill in all fields!');
		}
	}
});

/*loginApp.controller('contactCtrl', function ($scope, $http) {
    $scope.result = 'hidden'
    $scope.resultMessage;
    $scope.formData; //formData is an object holding the name, email, subject, and message
    $scope.submitButtonDisabled = false;
    $scope.submitted = false; //used so that form errors are shown only after the form has been submitted
    $scope.submit = function(contactform) {
        $scope.submitted = true;
        $scope.submitButtonDisabled = true;
        if (contactform.$valid) {
            $http({
                method  : 'POST',
                url     : 'contact-form.php',
                data    : $.param($scope.formData),  //param method from jQuery
                headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  //set the headers so angular passing info as form data (not request payload)
            }).success(function(data){
                console.log(data);
                if (data.success) { //success comes from the return json object
                    $scope.submitButtonDisabled = true;
                    $scope.resultMessage = data.message;
                    $scope.result='bg-success';
                } else {
                    $scope.submitButtonDisabled = false;
                    $scope.resultMessage = data.message;
                    $scope.result='bg-danger';
                }
            });
        } else {
            $scope.submitButtonDisabled = false;
            $scope.resultMessage = 'Failed <img src="http://www.chaosm.net/blog/wp-includes/images/smilies/icon_sad.gif" alt=":(" class="wp-smiley">  Please fill out all the fields.';
            $scope.result='bg-danger';
        }
    }
});*/


// All the Facebook-shit in the world!! (Zie txt Laura voor alternatieve brol!)

/*loginApp.run(['$rootScope', '$window',
  function($rootScope, $window) {

  $rootScope.user = {};
  
	window.fbAsyncInit = function() {
	    FB.init({
	      appId      : '1255620737799541', // App ID
	      channelUrl : 'http://dibs.intec.ugent.be:8080/login.html', // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true  // parse XFBML
	    });
	    
	    FB.getLoginStatus(function(response) {
		  if (response.status == 'connected') {
		    console.log(response.authResponse.accessToken);
		  }
		});
	    // Additional initialization code here
	  };
	  
	  (function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_US/skd.js";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	  
  window.loggedIn = function() {
	  FB.getLoginStatus(function(response) {
		  if (response.status == 'connected') {
		    var uid = response.authResponse.userID;
		    var accessToken = response.authResponse.accessToken;
		  } else if (response.status == 'not_authorized') {
		    // the user is logged in to Facebook, 
		    // but has not authenticated your app
		  } else {
		    // the user isn't logged in to Facebook.
		  }
	});
  };
  
  watchLoginChange = function() {
	  var _self = this;
	  FB.Event.subscribe('auth.authResponseChange', function(res) {
	    if (res.status === 'connected') {
	      _self.getUserInfo();
	    } 
	    else {
	    }
	  });
  }
  
  getUserInfo = function() {
	  var _self = this;
	  FB.api('/me', function(res) {
	    $rootScope.$apply(function() { 
	      $rootScope.user = _self.user = res; 

	    });
	 });
  }

  logout = function() {
	  var _self = this;
	  FB.logout(function(response) {
	    $rootScope.$apply(function() { 
	      $rootScope.user = _self.user = {}; 

	    }); 
	 });
  }
}]);*/

//All the Google-shit in the world!!
/*
function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	console.log('Name: ' + profile.getName());
	console.log('Image URL: ' + profile.getImageUrl());
	console.log('Email: ' + profile.getEmail());
}

//Sign out of our app without signing out of Google
//MOET IN DE HTML FILE!!!
<a href="#" onclick="signOut();">Sign out</a>
<script>
  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }
</script>
*/
