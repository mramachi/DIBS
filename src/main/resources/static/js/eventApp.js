var eventApp = angular.module('eventApp',['facebook',  'ui.bootstrap','ngRoute','ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

/*Factory*/
eventApp.factory('eventsFactory', function ($window) {
    var factory={};
    var KEY = 'eventApp.SelectedValue';

    /*Share data add*/
    factory.addData = function(newObj) {
    	var mydata = [];
        mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        } else {
            mydata = [];
        }
        if(newObj.length>0){
        	for(var i=0;i<newObj.length;i++){
        		mydata.push(newObj[i]);
        	}
        }else{
        	mydata.push(newObj);
        }
        
        return $window.sessionStorage.setItem(KEY, JSON.stringify(mydata));
    };
    
    factory.clearData=function(){
    	$window.sessionStorage.clear(KEY);
    }

    /*Share data get*/
    factory.getData = function(){
        var mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        }
        return mydata || [];
    };
    
    factory.getEvents = function () {
        return events;
    };

    factory.insertEvent = function (name,date,place) {
        var ID = events.length + 1;
        events.push({
            id: ID,
            name: name,
            date: date,
            place: place
        });
    };

    factory.deleteEvent = function (id) {
        for (var i = events.length - 1; i >= 0; i--) {
            if (events[i].id === id) {
                events.splice(i, 1);
                break;
            }
        }
    };
    return factory;

});



/*First controller for index.html*/
eventApp.controller('EventController', 
function ($scope, $http, eventsFactory, $window) {
	  $scope.thisWeekendEvents = [],
	  $scope.allEvents = [],
	  $scope.totalTodayPages=0;
	  $scope.totalTomorrowPages = 0;
	  $scope.totalThisWeekendPages=0;
	  $scope.totalAllEventsPages=0;
	  
	  
	  $scope.event;
	  $scope.numberOfEvents=9;
	  $scope.MyWidth="30%";
	  
	  angular.element($window).bind('resize', function() {
		    $scope.$apply(function() {
		    	if($window.innerWidth<800){
		    		$scope.numberOfEvents=3;
		    		$scope.MyWidth="100%";
		    	}
		    	else{
		    		$scope.numberOfEvents=9;
		    		$scope.MyWidth="30%";
		    	}
		    	
		        $scope.changeThemEvents(0);
		    });
		});
 
	  
	  $scope.range = function(min, max, step) {
		    step = step || 1;
		    var input = [];
		    for (var i = min; i <= max; i += step) {
		        input.push(i);
		    }
		    return input;
	};
	

	
	$scope.getAllEvents = function(page,pagesize){
		
		page++;
		console.log(page);
		
		
		$http
	    .get('/allEvents', {
	    	params:{
	    		page:page,
	    		pagesize:pagesize
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.allEvents=data.content;
	    	 $scope.totalAllEventsPages=data.totalPages;
	    	 //pagination limitation
	    	 if(page>1){
	    		 if(page<$scope.totalAllEventsPages-3){
	    			 $scope.AllEventsStartPage=page-2;
	    		 }
	    		 else{
	    			 $scope.AllEventsStartPage=$scope.totalAllEventsPages-5;
	    			 if($scope.AllEventsStartPage<0){
	    				 $scope.AllEventsStartPage=0;
	    			 }
	    		 }
	    	 } 
	    	 else{
	    		 $scope.AllEventsStartPage=0;
	    	 }
	    	
	    	 
	     });
		return $scope.allEvents;
	}
	

	 $scope.getEvents = function (kind,date,city,page){
		 var date = new Date(date); // make the string a date
		 date.setDate(date.getDate() + 1); // next day (otherwise wrong and picks yesterday -- don't know why)
		 var day = date.getUTCDate(); // get day
		 var month = date.getUTCMonth()+1; // get month
		 var year = date.getUTCFullYear(); // get year


	     window.location.href = "/eventsResult?d="+day+"&m="+month+"&y="+year+"&city="+city+"&pagesize=9";
	 }
	 
	 $scope.getTodayEvents = function(page,pagesize){
		 var today = new Date(); // get current date
		 page++;
		 
		 $http
		    .get('/searchEvent', {
		    	params:{
		    		p:page,
		    		city:"ALL",
		    	//	kind:all,
		    		d: today.getUTCDate(),
		    		m:today.getUTCMonth()+1,
		    		y: today.getUTCFullYear() ,
		    		pagesize:pagesize
		    	}
		     })
		     .success(function (data,status) {
		    	 console.log("TODAY");
		    	 console.log(data);
		    	$scope.todayEvents = data.content;
		    	$scope.totalTodayPages = data.totalPages;
		    	//pagination limitation
		    	if(page>1){
		    		 if(page<$scope.totalTodayPages-3){
		    			 $scope.TodayStartPage=page-2;
		    		 }
		    		 else{
		    			 $scope.TodayStartPage=$scope.totalTodayPages-5;
		    			 if($scope.TodayStartPage<0){
		    				 $scope.TodayStartPage=0;
		    			 }
		    		 }
		    	 } 
		    	 else{
		    		 $scope.TodayStartPage=0;
		    	 }
		     });
	 }
	 
	 $scope.getTomorrowEvents = function(page,pagesize){
		 var tomorrow = new Date(); // get current date
		 tomorrow.setDate(tomorrow.getDate() + 1); // tomorrow
		 console.log("tomorrow");
		 page++;
		 
		 $http
		    .get('/searchEvent', {
		    	params:{
		    		p:page,
		    		city:"ALL",
		    	//	kind:all,
		    		d: tomorrow.getUTCDate(),
		    		m:tomorrow.getUTCMonth()+1,
		    		y: tomorrow.getUTCFullYear() ,
		    		pagesize:pagesize
		    	}
		     })
		     .success(function (data,status) {
		    	 console.log("TOMORROW");
		    	 console.log(data);
		    	$scope.tomorrowEvents = data.content;
		    	$scope.totalTomorrowPages = data.totalPages;
		    	//pagination limitation
		    	if(page>1){
		    		 if(page<$scope.totalTomorrowPages-3){
		    			 $scope.TomorrowStartPage=page-2;
		    		 }
		    		 else{
		    			 $scope.TomorrowStartPage=$scope.totalTomorrowPages-5;
		    			 if($scope.TomorrowStartPage<0){
		    				 $scope.TomorrowStartPage=0;
		    			 }
		    		 }
		    	 } 
		    	 else{
		    		 $scope.TomorrowStartPage=0;
		    	 }
		     });
		 
	 }
	 
	 $scope.changeThemEvents = function(page){
		  $scope.getTodayEvents(page,$scope.numberOfEvents);
		  $scope.getTomorrowEvents(page,$scope.numberOfEvents);
		  $scope.getAllEvents(page,$scope.numberOfEvents);
		  $scope.getThisWeekendEvents(page,$scope.numberOfEvents);
	  }
	 
	 $scope.getThisWeekendEvents = function(page,pagesize){
		 var curr = new Date; // get current date
		 var first = curr.getDate() - curr.getDay(); // First day is the day of the month - the day of the week
		 var saterday = first + 6; // last day is the first day + 6
		 var sunday = first + 7;

		 var sat = new Date(curr.setDate(saterday));
		 var sun = new Date(curr.setDate(sunday));
		 var temp = null;

			page++;
			console.log(page);
			
			 $http
			    .get('/thisWeekend', {
			    	params:{
			    		page:page,
			    		pagesize:pagesize
			    	}
			     })
			     .success(function (data,status) {
			    	$scope.thisWeekendEvents = data.content;
			    	$scope.totalThisWeekendPages = data.totalPages;
			    	//pagination limitation
			    	if(page>1){
			    		 if(page<$scope.totalThisWeekendPages-3){
			    			 $scope.ThisWeekendStartPage=page-2;
			    		 }
			    		 else{
			    			 $scope.ThisWeekendStartPage=$scope.totalThisWeekendPages-5;
			    			 if($scope.ThisWeekendStartPage<0){
			    				 $scope.ThisWeekendStartPage=0;
			    			 }
			    		 }
			    	 } 
			    	 else{
			    		 $scope.ThisWeekendStartPage=0;
			    	 }
			     });
			
			return $scope.thisWeekendEvents;
	 }
	 
	 
	
	 
	 $scope.resultEvent=function(id){
		 window.location.href="/eventpage?id=".concat(id);
	 }

	 $scope.init = function(){
		  if($window.innerWidth<800){
			  $scope.numberOfEvents=3;
			  $scope.changeThemEvents(0);
			  $scope.MyWidth="100%";
		  }
	  }
	  
	  $scope.init();
	 
	 

});





/*Controller for eventsResult*/
eventApp.controller('EventResultController', function ($window, $scope,$http,eventsFactory){
	 $scope.MyWidth="30%";
	 $scope.numberOfEvents=9;
	 
	  
	 $scope.range = function(min, max, step) {
		    step = step || 1;
		    var input = [];
		    for (var i = min; i <= max; i += step) {
		        input.push(i);
		    }
		    return input;
	};
	
	  angular.element($window).bind('resize', function() {
		    $scope.$apply(function() {
		    	if($window.innerWidth<800){
		    		$scope.numberOfEvents=3;
		    		$scope.MyWidth="90%";
		    	}
		    	else{
		    		$scope.numberOfEvents=9;
		    		$scope.MyWidth="30%";
		    	}
		    	
		        $scope.changeThemEvents(0);
		        console.log($scope.MyWidth);
		    });
		});
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}

	$scope.day = $scope.getURLParameter("d");
	$scope.month = $scope.getURLParameter("m");
	$scope.year = $scope.getURLParameter("y");
	$scope.city = $scope.getURLParameter("city");
	$scope.resultEvents;
	
	$scope.getEvents = function(page, pagesize){
		page++;

		$http
	    .get('/searchEvent', {
	        params: {
	           // kind: kind,
	            d:$scope.day,
	            m:$scope.month,
	            y:$scope.year,
	            city:$scope.city,
	            p:page,
	            pagesize:pagesize
	        }
	     })
	     .success(function (data,status) {
	    	 $scope.resultEvents = data.content;
	    	 $scope.totalPages = data.totalPages;
	    	 //pagination
	    	 if(page>1){
	    		 if(page<$scope.totalPages-3){
	    			 $scope.EventResultStartPage=page-2;
	    		 }
	    		 else{
	    			 $scope.EventResultStartPage=$scope.totalPages-5;
	    			 if($scope.EventResultStartPage<0){
	    				 $scope.EventResultStartPage=0;
	    			 }
	    		 }
	    	 } 
	    	 else{
	    		 $scope.EventResultStartPage=0;
	    	 }
	     });
	}
	
	$scope.resultEvent=function(id){
		 window.location.href="/eventpage?id=".concat(id);
	 }
	
	
	$scope.resultPartner = function(id){
		console.log("in resultPartenr");
		window.location.href="/partnerpage?id=".concat(id);
	}
	

	$scope.changeThemEvents = function(page){
		$scope.getEvents(0,$scope.numberOfEvents);
	}
	
	 $scope.init = function(){
		  if($window.innerWidth<800){
			  $scope.numberOfEvents=3;
			  $scope.changeThemEvents(0);
			  $scope.MyWidth="90%";
		  }
	  }
	  
	  $scope.init();
	
    
});




/*Controller for EventPage*/
eventApp.controller('eventPageCtrl', function($scope,eventsFactory, $http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	
		$scope.event= null;
		
	$scope.init = function(){
		var captured = $scope.getURLParameter("id"); // Value is in [1] ('384' in our case)
		$scope.eventid = captured ? captured : '4';
		$scope.findEventByID($scope.eventid);
		$scope.findTicksAvl($scope.eventid);
	}
	
	$scope.findTicksAvl = function(id){
		$http
	    .get('/ticksAvailable', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 console.log(data);
	    	 $scope.ticksavl=data;
	     });
	}

	
	 $scope.findEventByID = function(id){

		$http
	    .get('/findEventByID', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.event = data;
	    	 return data;
	     });
			
			
		}
	 
	 $scope.init();
	 
		$scope.resultPartner = function(id){
			window.location.href="/partnerpage?id=".concat(id);
		}
		
		$scope.DibsIt = function(id){
			window.location.href="/bestellen?id=".concat(id);
		}
		
		
		
	
});

/*Controller for PartnerPage*/
eventApp.controller('PartnerController', function($window,$scope,$http){
	$scope.numberOfEvents=6;
	
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	
	angular.element($window).bind('resize', function() {
	    $scope.$apply(function() {
	    	if($window.innerWidth<800){
	    		$scope.numberOfEvents=4;
	    	}
	    	else{
	    		$scope.numberOfEvents=6;
	    	}
	    	
	        $scope.getPartnerEvents(0,$scope.numberOfEvents);
	    });
	});
	
	$scope.getPartnerByID = function(id){
		$http
	    .get('/partner/findPartnerByID', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.partner = data;
	     });
	}
	
	$scope.init = function(){
		$scope.partnerid = $scope.getURLParameter("id");
		$scope.getPartnerByID($scope.partnerid);
		if($window.innerWidth<800){
			  $scope.numberOfEvents=4;
			  $scope.getPartnerEvents(0,$scope.numberOfEvents);
			  $scope.MyWidth="100%";
		}
	}
	
	$scope.getPartnerEvents = function(page, pagesize){
		$scope.partnerid = $scope.getURLParameter("id");
		page++;
		
		$http
	    .get('/browseEventsByPartnerID', {
	    	params:{
	    		id:$scope.partnerid,
	    		page:page,
	    		pagesize:pagesize
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.events = data.content;
	    	 $scope.totalPages = data.totalPages;
	    	//pagination limitation
		    	if(page>1){
		    		 if(page<$scope.totalPages-3){
		    			 $scope.StartPage=page-2;
		    		 }
		    		 else{
		    			 $scope.StartPage=$scope.totalPages-5;
		    			 if($scope.StartPage<0){
		    				 $scope.StartPage=0;
		    			 }
		    		 }
		    	 } 
		    	 else{
		    		 $scope.StartPage=0;
		    	 }
	     });
	}
	
	$scope.range = function(min, max, step) {
	    step = step || 1;
	    var input = [];
	    for (var i = min; i <= max; i += step) {
	        input.push(i);
	    }
	    return input;
	};
	
	$scope.init();
	
	$scope.resultEvent=function(id){
		 window.location.href="/eventpage?id=".concat(id);
	 }
});


/*Dates*/
eventApp.controller('AppCtrl', function($scope) {
	  $scope.myDate = new Date();

	  $scope.minDate = new Date(
	      $scope.myDate.getFullYear() -1,
	      $scope.myDate.getMonth(),
	      $scope.myDate.getDate());

	  $scope.maxDate = new Date(
	      $scope.myDate.getFullYear() +2,
	      $scope.myDate.getMonth(),
	      $scope.myDate.getDate());
	  

});

eventApp.config(function($mdDateLocaleProvider) {
	
	//Nederlandse maanden
	 $mdDateLocaleProvider.months = ['januari', 'februari', 'maart','april','mei','juni','juli','augustus','september','oktober','november','december'];
	  $mdDateLocaleProvider.shortMonths = ['januari', 'februari', 'maart','april','mei','juni','juli','augustus','september','oktober','november','december'];
	  $mdDateLocaleProvider.days = ['zondag', 'maandag', 'dinsdag', 'woensdag','donderdag','vrijdag','zaterdag'];
	  $mdDateLocaleProvider.shortDays = ['Z', 'M', 'D', 'W','D','V','Z'];
	  $mdDateLocaleProvider.firstDayOfWeek = 1;
	  $mdDateLocaleProvider.formatDate = function(date) {
		    return date ? moment(date).format('DD/MM/YYYY') : '';
		  };
		  
		  $mdDateLocaleProvider.parseDate = function(dateString) {
		    var m = moment(dateString, 'DD/MM/YYYY', true);
		    return m.isValid() ? m.toDate() : new Date(NaN);
		  };
	
});

eventApp.config([
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

eventApp.controller('fbController', function($scope, $timeout,  Facebook, $http) {
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
    
    $scope.editUser = function(){
    	return window.location.href="editprofilepage?id=".concat($scope.user.userID);
    }
    
    $scope.postUser = function(){
    	$http
	    .get('/updateUser', {
	    	params:{
	    		userId:$scope.user.userID,
	    		userFirstName: $scope.user.userFirstName,
	    		userLastName: $scope.user.userLastName,
	    		userEmail: $scope.user.userEmail,
	    		userPhoto: $scope.user.userPhotoPath,
	    		userPhone: $scope.user.userPhone
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.user=data;
	    	 alert("Successfully saved.")
	     })
	     .error(function(data,status){
	    	 console.log(status);
	     });
    }
    
    $scope.UserProfile = function(){
    	return window.location.href="profilepage?id=".concat($scope.user.userID);
    }
    
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
        	var url = window.location.href;
        	var res = url.split("/");
	            res = res.pop();
	       res = res.split('?');
	       res = res[0];
        	if(res != "profilepage")
        		location.reload();
        	else
        		window.location.href="/index"
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
                             


