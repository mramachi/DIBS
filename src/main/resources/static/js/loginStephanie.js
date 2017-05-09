angular.module('loginApp', ['facebook'])

  .config([
    'FacebookProvider',
    function(FacebookProvider) {
     var myAppId = '144098182659869';
     
     // You can set appId with setApp method
      FacebookProvider.setAppId('myAppId');
     
     /**
      * After setting appId you need to initialize the module.
      * You can pass the appId on the init method as a shortcut too.
      */
     FacebookProvider.init(myAppId);
     
    }
  ])
  
  .controller('MainController', [
    '$scope',
    '$timeout',
    'Facebook', 
    '$http',
    function($scope, $timeout,  Facebook, $http) {
      
      // Define user empty data :/
      $scope.user = {};
      $scope.user.userPhotoPath = "";
      
      // Defining user logged status
      $scope.logged = false;
      
      // And some fancy flags to display messages upon user status change
      $scope.byebye = false;
      $scope.salutation = false;
      
      /**
       * Watch for Facebook to be ready.
       * There's also the event that could be used
       */
      $scope.$watch(
        function() {
          return Facebook.isReady();
        },
        function(newVal) {
          if (newVal)
            $scope.facebookReady = true;
        }
      );
      
      var userIsConnected = false;
      
      
      Facebook.getLoginStatus(function(response) {
        if (response.status == 'connected') {
          userIsConnected = true;
          $scope.logged=true;
          
          //http get
          $http
    	    .get('/browseFacebookUsers', {
    	    	params:{
    	    		id:response.authResponse.userID
    	    		
    	    	}
    	     })
    	     .success(function (data,status) {
    	    	 $scope.user=data;
    	    	
    	     });
        }
      });
      
      /**
       * IntentLogin
       */
      $scope.IntentLogin = function() {
    	  $scope.user.userPhotoPath = "https://d13yacurqjgara.cloudfront.net/users/12755/screenshots/1037374/hex-loader2.gif";
        if(!userIsConnected) {
          $scope.login();
        }
      };
      
      /**
       * Login
       */
       $scope.login = function() {
         Facebook.login(function(response) {
          if (response.status == 'connected') {
            $scope.logged = true;
            $scope.me();
            
          }
        
        });
       };
       
       /**
        * me 
        */
        $scope.me = function() {
          Facebook.api('/me', {fields: 'first_name,last_name,email,picture.width(150).heigh(150)'}, function(response) {
            /**
             * Using $scope.$apply since this happens outside angular framework.
             */
            $scope.$apply(function() {
              $scope.user = response;
              console.log($scope.user);
             
              $http
      	    .get('/createUser', {
      	    	params:{
      	    		userId:$scope.user.id,
      	    		userFirstName: $scope.user.first_name,
      	    		userLastName: $scope.user.last_name,
      	    		userEmail: $scope.user.email,
      	    		userPhoto: $scope.user.picture.data.url
      	    	}
      	     })
      	     .success(function (data,status) {
      	    	 $scope.user=data;
      	    	 console.log(data);
      	     })
      	     .error(function(data,status){
      	    	 console.log(status);
      	     });
          
            });
            
          });
        };
      
      /**
       * Logout
       */
      $scope.logout = function() {
        Facebook.logout(function() {
          $scope.$apply(function() { 
            location.reload();
          });
        });
      };
      
      
      
      /**
       * Taking approach of Events :D
       */
      $scope.$on('Facebook:statusChange', function(ev, data) {
    
        if (data.status == 'connected') {
          $scope.$apply(function() {
            $scope.salutation = true;
            $scope.byebye     = false;    
          });
        } else {
          $scope.$apply(function() {
            $scope.salutation = false;
            $scope.byebye     = true;
            
            // Dismiss byebye message after two seconds
            $timeout(function() {
              $scope.byebye = false;
            }, 2000)
          });
        }
        
        
      });
      
      
    }
  ])
  .controller('LoginController', [
    '$scope',
    '$timeout',
    'Facebook', 
    '$http',
    function($scope, $timeout,  Facebook, $http) {
    	
    	$scope.getURLParameter = function(name) {
    		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
    	}
    	
    	$scope.user = function(){
    		var l = $scope.getURLParameter("login");
    		if(l=="true")
    			window.location.href="loginuser";
    		else
    			window.location.href="registeruser"
    	}
    	
    	$scope.admin = function(){
    		var l = $scope.getURLParameter("login");
    		
    		if(l=="true")
    			window.location.href="loginadmin";
    		else
    			window.location.href="registeradmin"
    	}
    }])
    .controller('RegisterController', [
            '$scope',
            '$http',
            function($scope, $http) {
            
    	$scope.sendMail = function(){
    		var message = "Name: ".concat($scope.firstname).concat(" ").concat($scope.lastname).concat("\n").concat("Email: ").concat($scope.email).concat("\n").concat("Company Name: ").concat($scope.companyName).concat("\n").concat("Message: ").concat($scope.message);
    		$http
      	    .get('/SendMail', {
      	    	params:{
      	    		sender: $scope.email,
      	    		receiver: "dibsevents@gmail.com",
      	    		subject: "Partner Contact",
      	    		content: message
      	    	}
      	     })
      	     .success(function (data,status) {
      	    	 alert("Your mail has been successfully send!")
      	     })
      	     .error(function(data,status){
      	    	
      	     });
    	}
            	
    }]);
  
 