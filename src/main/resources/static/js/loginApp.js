(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js";
	  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

var loginApp = angular.module('loginApp', ['ngRoute']);

loginApp.run(['$window', function($window, $routeProvider, $http){
	$window.fbAsyncInit = function(){
	    FB.init({
	      appId      : '144098182659869', // App ID
	      channelUrl : 'http://dibs.intec.ugent.be:8080/login.html', // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true,  // parse XFBML
	      version    : 'v2.5' // use graph api version 2.5
	    });
	};
}]);

function checkLoginState($http) {
	FB.getLoginStatus(function(response, $http) {
	  if (response.status == 'connected') {
		  var uid = response.authResponse.userID;
		  var expiration = response.authResponse.expiresIn; // Als expiration date = 0, moet user uitgelogd worden.
		  console.log(uid);
		  console.log(expiration);
		  //CHECK OF DE USER AL DAN NIET IN DE DATABASE ZIT
		  httpPostAsync(uid, $http);
	  } else if (response.status == 'not_authorized') {
		  console.log('Please Authorize.');
	  } else {
		  console.log('Please log in.');
	  }
	})
};

//CHECK of de user in de database zit
function httpPostAsync(uid, $http){
    var xmlHttp = new XMLHttpRequest();
    var existing;
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("POST", '/browseUsers'+"?userId="+uid, true); // true for asynchronous
    xmlHttp.send(null);
    xmlHttp.onreadystatechange = callbackFunction;

    function callbackFunction(){
      if (xmlHttp.readyState == 4){
    	    existing = xmlHttp.responseText;
    	    console.log(existing);
    	    if(existing == "false"){
    	    	console.log("Je bent nieuw!");
    	    	//window.location.href = "/loginsettings" -- NIET DOEN, want facebook suckt = asynchroon = probleem
	    		FB.api('/me', {fields: 'first_name, last_name, email'}, function(response) {
	    		      console.log(JSON.stringify(response));
	    		      var firstname = response.first_name;
	    		      var lastname = response.last_name;
	    		      var email = response.email;
	    		      
	    		      console.log(firstname);
	    		      console.log(lastname);
	    		      console.log(email);
	    		      console.log(uid);
	    		      //Data van de nieuwe user doorsturen naar de database
	    		      httpPostInfoAsync(uid, firstname, lastname, email, $http);
	    		 });
    	    } else if (existing == "true"){
    	    	console.log("Welkom terug, ouwe rakker!")
    	    } else {
    	    	console.log("Er is iets mis gegaan, probeer opnieuw!")
    	    }
      }
    }
}

//STUUR de info door van de nieuwe gebruiker.
function httpPostInfoAsync(uid, firstname, lastname, email, $http){
	/*var xmlHttp = new XMLHttpRequest();
    var existing;
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", '/createUser'+"?userId="+uid+"&userFirstName="+firstname+"&userLastName="+lastname+"&userEmail="+email, true); // true for asynchronous
    xmlHttp.send(null);
    xmlHttp.onreadystatechange = callbackFunction;
    
    function callbackFunction(){
        if (xmlHttp.readyState == 4){
      	    existing = xmlHttp.responseText;
      	    console.log(existing);
        }
      }*/
	console.log("BEGIN CREATE USER");
	console.log(uid);
	console.log(firstname);
	console.log(lastname);
	console.log(email);
	$http
    .get('/createUser', {
    	params:{
    		userId:uid,
    		userFirstName:firstname,
    		userLastName:lastname,
    		userEmail:email
    	}
     })
     .success(function (data,status) {
    	 console.log("SCCESS");
     });
}


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


