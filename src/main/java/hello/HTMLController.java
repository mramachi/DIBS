package hello;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.mailing.adminMail;
import hello.repo.OfferRepository;
import hello.service.Locker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.servlet.http.HttpSession;

@Controller
public class HTMLController {
		 
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPALockDemo");
	
	@RequestMapping("/demo")
    public String demo(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "Demonstratie";
    }
	@RequestMapping("/demo_event")
    public String demo2(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "Demonstratie_event";
    }
	
    @RequestMapping("/greeting")
    public String GetGreeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    @RequestMapping(value={"/index", "/"})
    public String Getindex(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    	return "index";
    }
    
    @RequestMapping("/loginuser")
    public String Getlogin(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, HttpSession session) {
        System.err.println("LOGIN: "+session.getAttribute("uid"));
    	return "login/loginUser";
    }
    
    @RequestMapping("/loginadmin")
    public String loginadmin() {
        return "login/loginadmin";
    }
    
    @RequestMapping("/registeruser")
    public String registerUser() {
    	return "login/registerUser";
    }
    
    @RequestMapping("/registeradmin")
    public String registeradmin() {
        return "login/registerAdmin";
    }
    
    @RequestMapping("/userOrAdmin")
    public String userOrAdmin(@RequestParam(value="login", required=true, defaultValue="World") boolean login) {
        /*login=true => login
         * login=false => register*/
    	
    	return "login/ChooseUserOrAdmin";
    }
    
    @RequestMapping("/loginTemplate")
    public String GetloginTemplate(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, HttpSession session) {
    	return "loginBar";
    }
    
    @RequestMapping("/loginsettings")
    public String Getloginsettings(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, HttpSession session) {
    	return "loginsettings";
    }
    
    @RequestMapping("/eventsResult")
    public String GetEventResult(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, @RequestParam(value="d", required=true, defaultValue="1") int d,@RequestParam(value="m", required=true, defaultValue="1") int month, @RequestParam(value="y", required=true, defaultValue="1") int year, @RequestParam(value="city", required=true, defaultValue="All") String city) {
        return "eventsResult";
    }
    
    @RequestMapping("/eventpage")
    public String GetEventPage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, @RequestParam(value="eventid", required=true, defaultValue="4") long eventid) {
    	return "eventpage";
    }
    
    @RequestMapping("/eventsTemplate")
    public String GetEventTemplate(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "eventsTemplate";
    }
    
    @RequestMapping("/profilepage")
    public String GetProfilePage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model,@RequestParam(value="id", required=true, defaultValue="") long id) {
        return "profilepage";
    }
    
    @RequestMapping("/editprofilepage")
    public String GetEditProfilePage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model,@RequestParam(value="id", required=true, defaultValue="") long id) {
        return "editUserProfile";
    }
    
    @RequestMapping("/partnerpage")
    public String GetPartnerPage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model,@RequestParam(value="id", required=true, defaultValue="4") long partnerid) {
        return "partnerpage";
    }
    
    /*
    @RequestMapping(value="/lock",method=RequestMethod.GET)
    public String handlist(@RequestParam("myparam") List<String> params) {
		Locker a = new Locker(params.get(0), params);
		
		Thread t1 = new Thread(a);
		
		t1.start();
		
    	
    	return "login";
    }
    */
    @RequestMapping("/wachtrij")
    public String wachtrij(@RequestParam(value="name", required=false, defaultValue="World") String event, Model model,HttpSession session) throws InterruptedException {
    	return "VoorbeeldStephanieHTML";
    }
    


    @RequestMapping("/crud/adminPartner")
    public String GetAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, @RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/adminPartner";
    }
    
    @RequestMapping("/crud/editPartner")
    public String GetEditAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, @RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/editPartner";
    }
    
    @RequestMapping("/crud/EventAdminPartner")
    public String GetEventAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, @RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/EventAdminPartner";
    }
    
    @RequestMapping("/crud/EditEventAdmin")
    public String EditEventAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    									@RequestParam(value="id", required=true, defaultValue="World" )long id,
    									 @RequestParam(value="partnerid", required=true, defaultValue="World" )long partnerid) {
        return "adminPartner/EditEventAdmin";
    }
    
    @RequestMapping("/crud/CreateEventAdmin")
    public String EditEventAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    									 @RequestParam(value="id", required=true, defaultValue="World" )long partnerid) {
        return "adminPartner/CreateEventAdmin";
    }
    
    @RequestMapping("/crud/EditOfferAdmin")
    public String EditOfferAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    									@RequestParam(value="id", required=true, defaultValue="World" )long id,
    									 @RequestParam(value="partnerid", required=true, defaultValue="World" )long partnerid) {
        return "adminPartner/EditOfferAdmin";
    }

    @RequestMapping("/crud/CreateOfferAdmin")
    public String CreateOfferAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    									 @RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/CreateOfferAdmin";
    }
  
    
    
    @RequestMapping("/payOrder")
    public String payOrder(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    							@RequestParam(value="id", required=true )long id,
    							@RequestParam(value="offerid", required=true )List<Long> offerid) {
        return "Bestellen/PayOrder";
    }
    
    
    
    
    @RequestMapping("/NoTickets")
    public String NoTickets(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "Bestellen/NoTickets";
    }

    @RequestMapping("/crud/OfferAdminPartner")
    public String OfferAdminPartner(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    							@RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/OfferAdminPartner";
    }
    
    @RequestMapping("/crud/contactUs")
    public String ContactAdmin(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    							@RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/contactUs";
    }
    
    @RequestMapping("/crud/deletePage")
    public String DeleteAdmin(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    							@RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "adminPartner/DeleteAdmin";
    }
    /*
    @RequestMapping("/")
    String uid(HttpSession session){
    	UUID uid = (UUID) session.getAttribute("uid");
    	if (uid == null) {
    		uid = UUID.randomUUID();
    	}
    	session.setAttribute("uid", uid);
    	//return uid.toString();
    	 System.err.println("INDEX: "+session.getAttribute("uid"));
    	return "lock";
    }
    */
    @RequestMapping("/bestellen")
    public String order(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, 
    							@RequestParam(value="id", required=true, defaultValue="World" )long id) {
        return "Bestellen/bestellen";

    }
    // Start mousti's playground
    @RequestMapping("/bestellen2")
    public String lock(@RequestParam(value="name") String name, Model model, 
    							@RequestParam(value="id" )long id) {
    	Locker l = new Locker();
        Thread t = new Thread(l,"mijn thread");
        t.start();
        synchronized(t){
            try{
            	System.out.println("aaa: 1" + l.b);
                System.out.println("Waiting for t2 to complete...");
                t.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Total is: AAAAAAA" );
        	System.out.println("aaa: 2  " + l.b);
        }
    	return "Bestellen/bestellen";

    }
    @RequestMapping("/bestellen3")
    public String lock2(@RequestParam(value="name") String name, Model model, 
    							@RequestParam(value="id" )long id) {
        Locker l = new Locker();
        Thread t = new Thread(l,"mijn thread");
        t.start();
        synchronized(t){
            try{
            	System.out.println("bbbb: 1 " + l.b);

                System.out.println("Waiting for t1 to complete...");
                t.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        	System.out.println("bbbb: 2 " + l.b);
            System.out.println("Total is: BBBBB" );
        }
    	return "Bestellen/bestellen";

    }
    
    @RequestMapping("/cancel")
    public String cancelBooking() {
        return "Bestellen/cancelBooking";

    }
    
    @RequestMapping("/ticketspaid")
    public String paidBooking() {
        return "Bestellen/ticketsPaid";

    }
}
    
    
  