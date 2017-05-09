var partnerApp = angular.module('partnerApp',['ui.bootstrap','ngRoute','ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

/*voor editPartner.html*/
partnerApp.controller('EditPartnerController', function ($scope, $http) {

	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	
	$scope.init = function(){
		$scope.showSuccessAlert = false;
		 $scope.showDangerAlert = false;
		$scope.partnerid = $scope.getURLParameter("id");
		$scope.getPartnerByID($scope.partnerid);
	}
	
	$scope.getPartnerByID = function(id){
		$http
	    .get('/partner/findPartnerByID', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.partner = data;
	    	 console.log($scope.partner);
	     });
	}
	
	$scope.init();
	
	$scope.postForm = function(){
		console.log("postForm");
		console.log($scope.partner);
		$http
	    .get('../partner/updatePartner', {
	    	params:{
	    		id: $scope.partnerid,
	    		name:$scope.partner.partnerName,
	    		contactfn:$scope.partner.partnerContactFirstName,
	    		contactln:$scope.partner.partnerContactLastName,
	    		username:$scope.partner.username,
	    		password:$scope.partner.password,
	    		email:$scope.partner.partnerEmail
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.successTextAlert = "Changes Saved.";
	    	 $scope.showSuccessAlert = true;
	    	 $scope.showDangerAlert = false;
	    	 
	     })
	     .error(function(data,status){
	    	 $scope.dangerTextAlert = "Changes Not Saved. Have you filled in all the necessary fields?";
	    	 $scope.showDangerAlert = true;
	    	 $scope.showSuccessAlert = false;
	     });
	}
	
	

		
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat(id);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat(id);
		}
		
		$scope.EditPartner = function(id){
			return window.location.href = "editPartner?id=".concat(id);
		}
		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat(id);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat(id);
		}
		
		$scope.ReportProblem = function(id){
			return window.location.href="reportProblem?id=".concat(id);
		}
		
		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat(id);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat(id);
		}
	
});

partnerApp.controller('PartnerController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.partnerid=$scope.getURLParameter("id");
		$scope.getPartnerByID($scope.partnerid);
		$scope.url=window.location.href;
		
	}
	
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
	
	$scope.init();
	
	//HREF
	$scope.EventAdminPartner = function(id){
		return window.location.href = "EventAdminPartner?id=".concat(id);
	}
	
	$scope.AdminPartner = function(id){
		return window.location.href = "adminPartner?id=".concat(id);
	}
	
	$scope.EditPartner = function(id){
		return window.location.href = "editPartner?id=".concat(id);
	}
	
	$scope.StatsAdminPartner = function(id){
		return window.location.href="statsPartner?id=".concat(id);
	}
	
	$scope.ContactUs = function(id){
		return window.location.href="contactUs?id=".concat(id);
	}
	
	$scope.ReportProblem = function(id){
		return window.location.href="reportProblem?id=".concat(id);
	}
	
	$scope.DeletePage = function(id){
		return window.location.href="deletePage?id=".concat(id);
	}
	
	$scope.OfferAdminPartner = function(id){
		return window.location.href="OfferAdminPartner?id=".concat(id);
	}
});

partnerApp.controller('EventPartnerController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.partnerid=$scope.getURLParameter("id");
		$scope.getPartnerByID($scope.partnerid);
		$scope.getEventsByPartnerID();
	}
	
	
	$scope.getPartnerByID = function(id){
		$http
	    .get('/partner/findPartnerByID', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.partner = data;
	    	 console.log(data);
	     });
	}
	
	
	$scope.DeleteEvent = function(id){
		$http
	    .get('/deleteEvent', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	alert("Successfully deleted");
	     });
		
	
	}
	
		$scope.getEventsByPartnerID = function(){
			$scope.partnerid = $scope.getURLParameter("id");
			
			$http
		    .get('/browseAllEventsByPartnerID', {
		    	params:{
		    		id:$scope.partnerid
		    	}
		     })
		     .success(function (data,status) {
		    	 console.log(data);
		    	 $scope.events=data;
		     });
		}
		
		$scope.init();
		
		
	
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat(id);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat(id);
		}
		
		$scope.EditEvent = function(id){
			return window.location.href = "EditEventAdmin?id=".concat(id).concat("&partnerid=").concat($scope.partnerid);
		}
		
		$scope.EditPartner = function(id){
			return window.location.href = "editPartner?id=".concat(id);
		}
		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat(id);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat(id);
		}
		
		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat(id);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat(id);
		}
		
		$scope.CreateEvent = function(){
			return window.location.href="CreateEventAdmin?id=".concat($scope.partnerid);
		}
	
});



partnerApp.controller('EditEventAdminController',function($scope,$http){
	
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.eventid=$scope.getURLParameter("id");
		$scope.getEventByID($scope.eventid);
		$scope.partnerid=$scope.getURLParameter("partnerid");
		$scope.showDangerAlert = false;
		$scope.showSuccessAlert = false;
		
	}
	
	
	$scope.getEventByID = function(id){
		$http
	    .get('/findEventByID', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.event = data;
	    	 
	    	 //timestart
	    	 console.log(data);
	    
	    	 //$scope.event.eventTimeStart = ($scope.event.eventTimeStart).toString();
	 		 //$scope.event.eventTimeStop = $scope.event.eventTimeStop.toString();
	     });
	}
	
	$scope.postForm = function(){
		console.log("postForm");
		console.log($scope.eventid);
		console.log($scope.event.eventDescription);
		$http
	    .get('../updateEvent', {
	    	params:{
	    		id: $scope.eventid,
	    		name:$scope.event.eventName,
	    		descr:$scope.event.eventDescription,
	    		visible:$scope.event.eventIsVisible,
	    		date:$scope.event.eventDate,
	    		start:$scope.event.eventTimeStart,
	    		stop:$scope.event.eventTimeStop,
	    		city:$scope.event.location.locationCity,
	    		nr: $scope.event.location.locationHouseNumber,
	    		street: $scope.event.location.locationStreet,
	    		zip: $scope.event.location.locationZIP
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.successTextAlert = "Changes Saved.";
	    	 $scope.showSuccessAlert = true;
	    	 $scope.showDangerAlert = false;
	    	 
	     })
	     .error(function(data,status){
	    	 $scope.dangerTextAlert = "Changes Not Saved. Have you filled in all the necessary fields?";
	    	 $scope.showDangerAlert = true;
	    	 $scope.showSuccessAlert = false;
	     });
	}
	
		
	$scope.init();
	
	//HREF
	$scope.EventAdminPartner = function(id){
		return window.location.href = "EventAdminPartner?id=".concat(id);
	}
	
	$scope.AdminPartner = function(id){
		return window.location.href = "adminPartner?id=".concat(id);
	}
	
	$scope.EditEvent = function(id){
		return window.location.href = "EditEventAdmin?id=".concat(id).concat("&partnerid=").concat($scope.partnerid);
	}
	
	
	$scope.EditPartner = function(id){
		return window.location.href = "editPartner?id=".concat(id);
	}
	
	$scope.StatsAdminPartner = function(id){
		return window.location.href="statsPartner?id=".concat(id);
	}
	
	$scope.ContactUs = function(id){
		return window.location.href="contactUs?id=".concat(id);
	}
	
	$scope.DeletePage = function(id){
		return window.location.href="deletePage?id=".concat(id);
	}
	
	$scope.OfferAdminPartner = function(id){
		return window.location.href="OfferAdminPartner?id=".concat(id);
	}
});

partnerApp.controller('CreateEventAdminController',function($scope,$http){
	
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.partnerid=$scope.getURLParameter("id");
		$scope.showDangerAlert = false;
		$scope.showSuccessAlert = false;
		
	}
	
	
	
	$scope.CreateEvent = function(){
		console.log("CREATE EVENT");
		console.log($scope.event);
		$http
	    .get('../createEvent', {
	    	params:{
	    		partnerid:$scope.partnerid,
	    		name:$scope.event.eventName,
	    		descr:$scope.event.eventDescription,
	    		visible:$scope.event.eventIsVisible,
	    		date:$scope.event.eventDate,
	    		start:$scope.event.eventTimeStart,
	    		stop:$scope.event.eventTimeStop,
	    		city:$scope.event.location.locationCity,
	    		nr: $scope.event.location.locationHouseNumber,
	    		street: $scope.event.location.locationStreet,
	    		zip: $scope.event.location.locationZIP
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.successTextAlert = "Changes Saved.";
	    	 $scope.showSuccessAlert = true;
	    	 $scope.showDangerAlert = false;
	    	 
	     })
	     .error(function(data,status){
	    	 $scope.dangerTextAlert = "Changes Not Saved. Have you filled in all the necessary fields?";
	    	 $scope.showDangerAlert = true;
	    	 $scope.showSuccessAlert = false;
	     });
	}
	
		
	$scope.init();
	
	//HREF
	$scope.EventAdminPartner = function(id){
		return window.location.href = "EventAdminPartner?id=".concat(id);
	}
	
	$scope.AdminPartner = function(id){
		return window.location.href = "adminPartner?id=".concat(id);
	}
	
	$scope.EditEvent = function(id){
		return window.location.href = "EditEventAdmin?id=".concat(id).concat("&partnerid=").concat($scope.partnerid);
	}
	
	
	$scope.EditPartner = function(id){
		return window.location.href = "editPartner?id=".concat(id);
	}
	
	$scope.StatsAdminPartner = function(id){
		return window.location.href="statsPartner?id=".concat(id);
	}
	
	$scope.ContactUs = function(id){
		return window.location.href="contactUs?id=".concat(id);
	}
	
	$scope.DeletePage = function(id){
		return window.location.href="deletePage?id=".concat(id);
	}
	
	$scope.OfferAdminPartner = function(id){
		return window.location.href="OfferAdminPartner?id=".concat(id);
	}
});

partnerApp.controller('OfferPartnerController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.partnerid=$scope.getURLParameter("id");
		$scope.getPartnerByID($scope.partnerid);
		$scope.getOffersByPartnerID();
	}
	
	$scope.DeleteOffer = function(id){
		$http
	    .get('/deleteOffer', {
	    	params:{
	    		id:id
	    	}
	     })
	     .success(function (data,status) {
	    	 alert("Successfully deleted");
	     });
	}
	
	
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
	
	
		$scope.getOffersByPartnerID = function(){
			$scope.partnerid = $scope.getURLParameter("id");
			
			$http
		    .get('/browseOffersByPartnerID', {
		    	params:{
		    		id:$scope.partnerid
		    	}
		     })
		     .success(function (data,status) {
		    	 $scope.offers=data;
		    	 
		     });
		}
		
		$scope.init();
		

		
		
		
		
	
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.EditEvent = function(id){
			console.log(id);
			$http
		    .get('/getEventIDByOfferID', {
		    	params:{
		    		id:id
		    	}
		     })
		     .success(function (data,status) {
		    	 return window.location.href = "EditEventAdmin?id=".concat(data).concat("&partnerid=").concat($scope.partnerid);
		     });
			
		}
		
		$scope.EditOffer = function(id){
			return window.location.href = "EditOfferAdmin?id=".concat(id).concat("&partnerid=").concat($scope.partnerid);
		}
		
		$scope.EditPartner = function(id){
			return window.location.href = "editPartner?id=".concat($scope.partnerid);
		}
		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat($scope.partnerid);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat($scope.partnerid);
		}

		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat($scope.partnerid);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.CreateOffer = function(){
			return window.location.href="CreateOfferAdmin?id=".concat($scope.partnerid);
		}
	
});

partnerApp.controller('EditOfferAdminController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.showDangerAlert = false;
		$scope.showSuccessAlert = false;
		$scope.offerid=$scope.getURLParameter("id");
		$scope.getOffersByPartnerID();
		$scope.partnerid=$scope.getURLParameter("partnerid");
	}
	
	$scope.postForm = function(){
		console.log("postForm");
		$http
	    .get('../updateOffer', {
	    	params:{
	    		id: $scope.offerid,
	    		seatRow:$scope.o.seatRow,
	    		seatColumn:$scope.o.seatColumn,
	    		dl:$scope.o.offerDeadline,
	    		visible:$scope.o.offerIsVisible,
	    		ticksavl:$scope.o.offerTicksAvl,
	    		price:$scope.o.offerPrice,
	    		discount:$scope.o.offerDiscount
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.successTextAlert = "Changes Saved.";
	    	 $scope.showSuccessAlert = true;
	    	 $scope.showDangerAlert = false;
	    	 
	     })
	     .error(function(data,status){
	    	 $scope.dangerTextAlert = "Changes Not Saved. Have you filled in all the necessary fields?";
	    	 $scope.showDangerAlert = true;
	    	 $scope.showSuccessAlert = false;
	     });
	}

	
	
		$scope.getOffersByPartnerID = function(){
			
			$http
		    .get('/findByOfferID', {
		    	params:{
		    		id:$scope.offerid
		    	}
		     })
		     .success(function (data,status) {
		    	 $scope.o=data;
		    	 
		    	 //deadline
		 		 $scope.o.offerDeadline = moment($scope.o.offerDeadline).format("YYYY-MM-DD HH:mm:ss");
		 		console.log($scope.o.offerDeadline);
		     });
		}
		
		$scope.init();
	
		
	
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.EditEvent = function(id){
			console.log(id);
			$http
		    .get('/getEventIDByOfferID', {
		    	params:{
		    		id:id
		    	}
		     })
		     .success(function (data,status) {
		    	 return window.location.href = "EditEventAdmin?id=".concat(data).concat("&partnerid=").concat($scope.partnerid);
		     });
			
		}
		
		$scope.EditOffer = function(id){
			return window.location.href = "EditOfferAdmin?id=".concat(id).concat("&partnerid=").concat($scope.partnerid);
		}
		
		$scope.EditPartner = function(id){
			return window.location.href = "editPartner?id=".concat($scope.partnerid);
		}
		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat($scope.partnerid);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat($scope.partnerid);
		}
		
		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat($scope.partnerid);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat($scope.partnerid);
		}
	
});

partnerApp.controller('CreateOfferAdminController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.showDangerAlert = false;
		$scope.showSuccessAlert = false;
		$scope.partnerid=$scope.getURLParameter("partner");
		$scope.getEventsByPartnerID();
	}
	
	$scope.getEventsByPartnerID = function(){
		$scope.partnerid = $scope.getURLParameter("id");
		
		$http
	    .get('/browseAllEventsByPartnerID', {
	    	params:{
	    		id:$scope.partnerid
	    	}
	     })
	     .success(function (data,status) {
	    	 console.log(data);
	    	 $scope.events=data;
	     });
	}
	
	$scope.createOffer = function(){
		console.log($scope.o.event.eventID);
		$http
	    .get('../createOffer', {
	    	params:{
	    		eventid:$scope.o.event.eventID,
	    		seatRow:$scope.o.seatRow,
	    		seatColumn:$scope.o.seatColumn,
	    		dl:$scope.o.offerDeadline,
	    		visible:$scope.o.offerIsVisible,
	    		ticksavl:$scope.o.offerTicksAvl,
	    		price:$scope.o.offerPrice,
	    		discount:$scope.o.offerDiscount
	    	}
	     })
	     .success(function (data,status) {
	    	 $scope.successTextAlert = "Changes Saved.";
	    	 $scope.showSuccessAlert = true;
	    	 $scope.showDangerAlert = false;
	    	 
	     })
	     .error(function(data,status){
	    	 $scope.dangerTextAlert = "Changes Not Saved. Have you filled in all the necessary fields?";
	    	 $scope.showDangerAlert = true;
	    	 $scope.showSuccessAlert = false;
	     });
	}

	
	
		$scope.getOffersByPartnerID = function(){
			
			$http
		    .get('/findByOfferID', {
		    	params:{
		    		id:$scope.offerid
		    	}
		     })
		     .success(function (data,status) {
		    	 $scope.o=data;
		    	 
		    	 //deadline
		 		 $scope.o.offerDeadline = moment($scope.o.offerDeadline).format("YYYY-MM-DD HH:mm:ss");
		 		console.log($scope.o.offerDeadline);
		     });
		}
		
		$scope.init();
	
		
	
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.EditEvent = function(id){
			$http
		    .get('/getEventIDByOfferID', {
		    	params:{
		    		id:id
		    	}
		     })
		     .success(function (data,status) {
		    	 return window.location.href = "EditEventAdmin?id=".concat(data).concat("&partnerid=").concat($scope.partnerid);
		     });
			
		}
		
		$scope.EditOffer = function(id){
			return window.location.href = "EditOfferAdmin?id=".concat(id).concat("&partnerid=").concat($scope.partnerid);
		}
		
		$scope.EditPartner = function(id){
			return window.location.href = "editPartner?id=".concat($scope.partnerid);
		}
		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat($scope.partnerid);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat($scope.partnerid);
		}
		
		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat($scope.partnerid);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat($scope.partnerid);
		}
	
});

partnerApp.controller('ContactUsAdminController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.partnerid=$scope.getURLParameter("id");
		$scope.mail = [];
	}
	
	$scope.init();
	
	$scope.contactUs = function(){
		console.log("CONTACT");
		console.log($scope.mail);
		$http
	    .get('/SendMail', {
	    	params:{
	    		sender: $scope.mail.from,
	    		subject: $scope.mail.subject,
	    		content: $scope.mail.message,
	    		receiver: "dibsevents@gmail.com"
	    	}
	     })
	     .success(function (data,status) {
	    	alert("Mail successfully Send!");
	     });
	}
		
	
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat($scope.partnerid);
		}
		

		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat($scope.partnerid);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat($scope.partnerid);
		}
		
		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat($scope.partnerid);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat($scope.partnerid);
		}
	
});


partnerApp.controller('DeleteAdminController', function($scope,$http){
	$scope.getURLParameter = function(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
	

	$scope.init = function(){
		$scope.partnerid=$scope.getURLParameter("id");
	}
	
	$scope.init();
	
	$scope.deletePage = function(){
		var message = "ID partner: ".concat($scope.partnerid);
		$http.get('/SendMail', {
  	    	params:{
  	    		sender: "dibsevents@gmail.com",
  	    		receiver: "dibsevents@gmail.com",
  	    		subject: "Partner wants to delete page",
  	    		content: message
  	    	}
  	     })
  	     .success(function (data,status) {
  	    	 alert("Your request has been successfully send!")
  	     })
  	     .error(function(data,status){
  	    	
  	     });
	}
	
		
	
		//HREF
		$scope.EventAdminPartner = function(id){
			return window.location.href = "EventAdminPartner?id=".concat($scope.partnerid);
		}
		
		$scope.AdminPartner = function(id){
			return window.location.href = "adminPartner?id=".concat($scope.partnerid);
		}
		

		
		$scope.StatsAdminPartner = function(id){
			return window.location.href="statsPartner?id=".concat($scope.partnerid);
		}
		
		$scope.ContactUs = function(id){
			return window.location.href="contactUs?id=".concat($scope.partnerid);
		}
		
		$scope.DeletePage = function(id){
			return window.location.href="deletePage?id=".concat($scope.partnerid);
		}
		
		$scope.OfferAdminPartner = function(id){
			return window.location.href="OfferAdminPartner?id=".concat($scope.partnerid);
		}
	
});


