var bestelApp = angular.module('bestelApp',['facebook','timer']);

bestelApp.controller('SelectOrderController', function ($rootScope, $scope, $http) {
	$scope.stoel = null;
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	
	$scope.getSeats = function(id){
		$http
	    .get('/browseOffersByEventID', {
	        params: {
	           id:id
	        }
	     })
	     .success(function (data,status) {
	    	 $scope.stoel = data;
	    	 for(var i=0;i<$scope.stoel.length;i++)
	    		 $scope.stoel[i].selected=false;
	     });

	}

	$scope.init = function(){
		$scope.eventid=$scope.getURLParameter("id");
		 $scope.getSeats($scope.eventid);
		
	}
	
	$scope.attachURLofferid = function(url, offerid){
		return url.concat("&offerid=").concat(offerid);
	}
	
	$scope.DibsSeats = function(){
		console.log($rootScope.user);
		if($rootScope.user.userID ==undefined){
			alert("You need to be logged in!");
		}
		else{
			$scope.selectedStoel=[];
			for(var i=0;i<$scope.stoel.length;i++){
				if($scope.stoel[i].selected==true){
					delete $scope.stoel[i].selected;
					$scope.selectedStoel.push($scope.stoel[i].offerID);
				}
			}
			var url;
			
			if($scope.selectedStoel.length>0){
			//http sturen
			$http
		    .get('/passSelectedStoelen', {
		        params: {
		           id:$scope.eventid,
		           offerids: $scope.selectedStoel,
		           userid:$rootScope.user.userID
		        }
		     })
		     .success(function (data,status) {
		    	 console.log("SUCCESS");
		     });
			
			url="payOrder?id=".concat($scope.eventid);
			for(var i=0;i<$scope.selectedStoel.length;i++){
				url = $scope.attachURLofferid(url,$scope.selectedStoel[i]);
			}
			}
			else{
				url="NoTickets";
			}
			
			
	
			return window.location.href=url;
		}
	}
	
	$scope.init();
	
	
});

bestelApp.controller('PayOrderController', function ($rootScope, $scope, $http) {

	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	
	$scope.findByOfferID = function(id){
		$http
	    .get('/findByOfferID', {
	        params: {
	           id:id,
	        }
	     })
	     .success(function (data,status) {
	    	 $scope.stoel.push(data);
	     });
	}
	
	$scope.DibsSeats = function(){
		
		 
		$http
	    .get('/paybooking', {
	        params: {
	           id:$scope.eventid,
	           offerids: $scope.offerids,
	           userid:$rootScope.user.userID
	        }
	     })
	     .success(function (data,status) {
	 		$http.get('/SendMail', {
	   	    	params:{
	   	    		sender: "dibsevents@gmail.com",
	   	    		receiver: $rootScope.user.userEmail,
	   	    		subject: "Ticket confirmation",
	   	    		content: "You paid! Yes!"
	   	    	}
	   	     })
	   	     .success(function (data,status) {
	   	    	console.log($rootScope.user);
	   	     	window.location.href="ticketspaid";
	   	     })
	   	     .error(function(data,status){
	   	    	
	   	     });
	    	
	     }).error(function(data,status){
	    	 window.location.href="error";
	     });
	}
	
	$scope.finished = function(){
		console.log($rootScope);
		$http
	    .get('/cancelbooking', {
	        params: {
	           id:$scope.eventid,
	           offerids: $scope.offerids,
	           userid:$rootScope.user.userID
	        }
	     })
	     .success(function (data,status) {
	    	 window.location.href="cancel";
	     });
	}
	
	$scope.init = function(){
		$scope.eventid=$scope.getURLParameter("id");
		$scope.stoel = [];
		var u = window.location.href;
		
		var array = u.split(/[?&]/).reduce(function(a,b,c){
			  var p=b.split("="), k=p[0], v=decodeURIComponent(p[1]);
			  if(!p[1])return a;
			  a[k]=a[k]||[];
			  a[k].push(v);
			 return a;
			}, {});
		
		$scope.stoel=[];
		for(var i=0;i<array.offerid.length;i++){
			$scope.findByOfferID(array.offerid[i]);
			
		}
		$scope.offerids=array.offerid;
	
		
		
	}
	

	
	$scope.init();
});

bestelApp.config([
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
                  
        }]);

        bestelApp.controller('fbController', function($rootScope, $scope, $timeout,  Facebook, $http) {
        	 // Define user empty data :/
            $rootScope.user = {};
            $rootScope.user.userPhotoPath = "";
            
            // Defining user logged status
            $scope.logged=false;
            
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
          	    	 $rootScope.user=data;
          	     });
              }
            });
            
            /**
             * IntentLogin
             */
            $scope.IntentLogin = function() {
          	  $rootScope.user.userPhotoPath = "https://d13yacurqjgara.cloudfront.net/users/12755/screenshots/1037374/hex-loader2.gif";
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
             
             $scope.MyProfile = function(){
            	 console.log($scope.user);
            	 var url ="profilepage?id=".concat($scope.user.userID);
            	 return window.location.href=url;
             }
             
             
             /**
              * me 
              */
              $scope.me = function() {
                Facebook.api('/me', {fields: 'first_name,last_name,email,picture.width(150).heigh(150)'}, function(response) {
                  /**
                   * Using $scope.$apply since this happens outside angular framework.
                   */
                  $scope.$apply(function() {
                    $rootScope.user = response;
                   
                    $http
            	    .get('/createUser', {
            	    	params:{
            	    		userId:$rootScope.user.id,
            	    		userFirstName: $rootScope.user.first_name,
            	    		userLastName: $rootScope.user.last_name,
            	    		userEmail: $rootScope.user.email,
            	    		userPhoto: $rootScope.user.picture.data.url
            	    	}
            	     })
            	     .success(function (data,status) {
            	    	 $rootScope.user=data;
            	    	 
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
            }
            
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
        });
                 