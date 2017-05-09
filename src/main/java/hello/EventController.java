package hello;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.mailing.adminMail;
import hello.model.Event;
import hello.model.Location;
import hello.model.Offer;
import hello.model.Purchase;
import hello.model.User;
import hello.repo.EventRepository;
import hello.repo.LocationRepository;
import hello.repo.PurchaseRepository;
import hello.repo.UserRepository;
import hello.service.CancelHandler;
import hello.service.EventService;
import hello.service.Locker;
import hello.service.OfferService;

@Controller
@RequestMapping("/*")
public class EventController {
	private static final int PAGE_SIZE = 9;

	@Autowired
	EventRepository eventRepository;
	// @Autowired
	// OfferRepository offerRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PurchaseRepository purchaseRepository;

	@Autowired
	EventService eventService;
	@Autowired
	OfferService offerService;

	@Autowired
	Locker l;
	@Autowired
	CancelHandler cancelHandler;
	

	/// EVENT REQUESTMAPPINGS ///

	// returns JSON string of created event
	@RequestMapping("/createEvent")
	@ResponseBody
	public void createEvent(@RequestParam(name = "partnerid", required = true) long partnerid,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "descr", required = true) String description,
			@RequestParam(name = "visible", required = true) String visible,
			@RequestParam(name = "date", required = true) String date,
			@RequestParam(name = "start", required = true) String timestart,
			@RequestParam(name = "stop", required = true) String timestop,
			@RequestParam(name = "city", required = true) String city,
			@RequestParam(name = "zip", required = true) String zip,
			@RequestParam(name = "nr", required = true) String nr,
			@RequestParam(name = "street", required = true) String street) throws ParseException {
		boolean success = true;
		
		Event e = new Event();

		if (e != null) {
			if (!(name.equals("")))
				e.setEventName(name);
			else
				success = false;
			
			if (!description.equals(""))
				e.setEventDescription(description);
			else
				success = false;
			
			if ((visible.toUpperCase().equals("TRUE") || visible.toUpperCase().equals("FALSE"))) {
				if (visible.equals("TRUE"))
					e.setEventIsVisible(true);
				else
					e.setEventIsVisible(false);
			} else {
				success = false;
			}
			
			if (!date.equals("")) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date d = format.parse(date);
				java.sql.Date dd = new java.sql.Date(d.getTime());
				e.setEventDate(dd);
			} else {
				success = false;
			}
		
			if (!timestart.equals("")) {
				String d = date.concat(" ").concat(timestart);
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date parsedDate = dateFormat.parse(d);
					java.sql.Time ts1 = new java.sql.Time(parsedDate.getTime());
					e.setEventTimeStart(ts1);

				} catch (Exception ex) {// this generic but you can control
										// another types of exception
					ex.printStackTrace();
				}
			} else {
				success = false;
			}
		

			if (!timestop.equals("")) {
				String d = date.concat(" ").concat(timestop);
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date parsedDate = dateFormat.parse(d);
					java.sql.Time ts1 = new java.sql.Time(parsedDate.getTime());
					e.setEventTimeStop(ts1);
				} catch (Exception ex) {// this generic but you can control
										// another types of exception
					ex.printStackTrace();
				}
			} else {
				success = false;
			}

			if (!city.equals("")){
				int n = Integer.parseInt(nr);
				char[] z = zip.toCharArray();
				Location l = locationRepository.findLocation(street, n, city,z);
				if(l!=null){
					e.setLocation(l);
				}
				else{
					l = new Location();
					l.setLocationCity(city);
					l.setLocationHouseNumber(n);
					l.setLocationStreet(street);
					l.setLocationZIP(z);
					locationRepository.saveAndFlush(l);
					e.setLocation(l);
				}
			}
			else
				success = false;
			
			if(partnerid>=0)
				e.setPartnerID(partnerid);
			else
				success=false;

			// *TODO: Checken of user en pasword uniek is. passwords in hash
			// opslaan.*//
		
			if (success == true)
				System.err.println(e.toString());
				System.err.println(eventService.getEventRepository().saveAndFlush(e)); // immediate change in the database!
			
		}
		
	}
	

	@RequestMapping("/updateEvent")
	@ResponseBody
	public Event updateEvent(@RequestParam(name = "id", required = true) long id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "descr", required = true) String description,
			@RequestParam(name = "visible", required = true) String visible,
			@RequestParam(name = "date", required = true) String date,
			@RequestParam(name = "start", required = true) String timestart,
			@RequestParam(name = "stop", required = true) String timestop,
			@RequestParam(name = "city", required = true) String city,
			@RequestParam(name = "zip", required = true) String zip,
			@RequestParam(name = "nr", required = true) String nr,
			@RequestParam(name = "street", required = true) String street) throws ParseException {
		Event e = eventService.getEventRepository().findByEventID(id);
		boolean success = true;

		if (e != null) {
			if (!(name.equals("")))
				e.setEventName(name);
			else
				success = false;
			
			if (!description.equals(""))
				e.setEventDescription(description);
			else
				success = false;
			
			if ((visible.toUpperCase().equals("TRUE") || visible.toUpperCase().equals("FALSE"))) {
				if (visible.equals("TRUE"))
					e.setEventIsVisible(true);
				else
					e.setEventIsVisible(false);
			} else {
				success = false;
			}
			
			if (!date.equals("")) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date d = format.parse(date);
				java.sql.Date dd = new java.sql.Date(d.getTime());
				e.setEventDate(dd);
			} else {
				success = false;
			}
		
			if (!timestart.equals("")) {
				String d = date.concat(" ").concat(timestart);
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date parsedDate = dateFormat.parse(d);
					java.sql.Time ts1 = new java.sql.Time(parsedDate.getTime());
					e.setEventTimeStart(ts1);

				} catch (Exception ex) {// this generic but you can control
										// another types of exception
					ex.printStackTrace();
				}
			} else {
				success = false;
			}
		

			if (!timestop.equals("")) {
				String d = date.concat(" ").concat(timestop);
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date parsedDate = dateFormat.parse(d);
					java.sql.Time ts1 = new java.sql.Time(parsedDate.getTime());
					e.setEventTimeStop(ts1);
				} catch (Exception ex) {// this generic but you can control
										// another types of exception
					ex.printStackTrace();
				}
			} else {
				success = false;
			}

			if (!city.equals("")){
				int n = Integer.parseInt(nr);
				char[] z = zip.toCharArray();
				Location l = locationRepository.findLocation(street, n, city,z);
				if(l!=null){
					e.setLocation(l);
				}
				else{
					l = e.getLocation();
					l.setLocationCity(city);
					l.setLocationHouseNumber(n);
					l.setLocationStreet(street);
					l.setLocationZIP(z);
					locationRepository.saveAndFlush(l);
				}
			}
			else
				success = false;

			// *TODO: Checken of user en pasword uniek is. passwords in hash
			// opslaan.*//
		
			if (success == true)
				return eventService.getEventRepository().saveAndFlush(e); //immediate change in DB!
			else
				return null;
		} else {
			System.out.println("Event to update was not found");
			return null;
		}
	}

	@RequestMapping("searchEvent")
	@ResponseBody
	public Page<Event> searchEvent(@RequestParam(name = "city", required = true) String city,
			@RequestParam(name = "d", required = true) int day, @RequestParam(name = "m", required = true) int month,
			@RequestParam(name = "y", required = true) int year,
			@RequestParam(name = "p", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "pagesize", required = true, defaultValue = "9") int pagesize) {
		// Set date values
		java.sql.Date d;
		if (day < 0 || day > 31 || month < 0 || month > 12 || year < 2014 || year > 2020) {
			d = null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.set(Calendar.MONTH, month - 1);
			d = new java.sql.Date(calendar.getTime().getTime());
		}

		// set pagination
		PageRequest req = new PageRequest(pageNumber - 1, pagesize, Sort.Direction.DESC, "eventName");
		Page<Event> e = null;
		if (!city.toString().toUpperCase().equals("ALL")) {
			if (d != null) {
				e = eventService.getEventRepository().findByCityByDate(city, d, req);
			} else {
				e = eventService.getEventRepository().findByCity(city, req);
			}
		} else if (d != null) {
			e = eventService.getEventRepository().findByDate(d, req);
		} else {
			e = eventService.getEventRepository().findAll(req);
		}
		return e;
	}

	@RequestMapping("/deleteEvent")
	@ResponseBody
	public Event deleteEvent(@RequestParam(value = "id", required = true) long eventID) {
		Event e;
		if (eventService.getEventRepository().exists(eventID)) {
			System.out.println("Event(" + eventID + ") bestaat");
			e = eventService.getEventRepository().findByEventID(eventID);
			eventService.getEventRepository().delete(e);
			return e;
		} else {
			System.out.println("Event(" + eventID + ") werd niet in de Database gevonden");
			return null;
		}
	}

	// Pageable voorbeeld. Geeft events in pages van PAGE_SIZE terug
	@RequestMapping("/allEvents")
	@ResponseBody
	public Page<Event> browseEvents(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "pagesize", required = true, defaultValue = "9") int pagesize) {
		PageRequest req = new PageRequest(pageNumber - 1, pagesize, Sort.Direction.DESC, "eventName");
		Calendar calendar = Calendar.getInstance();
		Date d = calendar.getTime();
		//return eventService.getEventRepository().findAll(req);
		return eventService.getEventRepository().findAllFutureEvents(d, req); //toont geen events in het verleden :P
	}

	@RequestMapping("/browseEvents")
	@ResponseBody
	public Page<Event> browseEvents(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "kind", required = false, defaultValue = "1") String kind,
			@RequestParam(name = "place", required = false, defaultValue = "1") String place,
			@RequestParam(name = "date", required = false, defaultValue = "1") Timestamp date,
			@RequestParam(name = "pagesize", required = true, defaultValue = "" + PAGE_SIZE) int pagesize) {
		PageRequest req = new PageRequest(pageNumber - 1, pagesize, Sort.Direction.DESC, "eventName");
		return eventService.getEventRepository().findAll(req);
	}

	@RequestMapping("/findEventByID")
	@ResponseBody
	public Event findEventByID(@RequestParam(name = "id", required = true, defaultValue = "4") long id) {
		return eventService.getEventRepository().findByEventID(id);
	}

	@RequestMapping("/browseEventsByPartnerID")
	@ResponseBody
	public Page<Event> browseEvents(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "id", required = true) long partnerid,
			@RequestParam(name = "pagesize", required = false, defaultValue = "" + PAGE_SIZE) int pagesize) {
		PageRequest req = new PageRequest(pageNumber - 1, pagesize, Sort.Direction.DESC, "eventName");
		return eventService.getEventRepository().findByPartnerID(partnerid, req);

	}

	@RequestMapping("/browseAllEventsByPartnerID")
	@ResponseBody
	public List<Event> browseEvents(@RequestParam(name = "id", required = true, defaultValue = "1") long partnerid) {
		return eventService.getEventRepository().findAllByPartnerID(partnerid);
	}

	@RequestMapping("/ticksAvailable")
	@ResponseBody
	public int ticksAvailable(@RequestParam(name = "id", required = true, defaultValue = "") long eventID) {
		Event e = eventService.getEventRepository().findByEventID(eventID);
		if (e == null) {
			System.out.println("no event with ID=" + eventID);
			return -1;
		} else {
			List<Offer> o = e.getOffers();
			int numbOfTicks = 0;
			for (int i = 0; i < o.size(); i++) {
				numbOfTicks += o.get(i).getOfferTicksAvl();
			}

			return numbOfTicks;
		}
	}
	
	@RequestMapping("/thisWeekend")
	@ResponseBody
	public Page<Event> thisWeekend(@RequestParam(name = "page", required=false, defaultValue="1") int page,
			@RequestParam(name = "pagesize", required=false, defaultValue="1") int pagesize) {
		System.err.println(page);
		PageRequest req = new PageRequest(page - 1, pagesize, Sort.Direction.DESC, "eventDate");
		Calendar calendar = Calendar.getInstance();
		Date d = calendar.getTime();
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		Date d2 = calendar.getTime();
		Page<Event> r = eventRepository.thisWeekendPage(d, d2, req);
		return r;
	}

	/// OFFER REQUESTMAPPINGS ///

	@RequestMapping("/createOffer")
	@ResponseBody
	public Offer createOffer(@RequestParam(name = "eventid", required = true) long id,
			@RequestParam(name = "seatRow", required = true) String seatRow,
			@RequestParam(name = "seatColumn", required = true) String seatColumn,
			@RequestParam(name = "dl", required = true) String deadline,
			@RequestParam(name = "visible", required = true) String visible,
			@RequestParam(name = "ticksavl", required = true) int ticksavl,
			@RequestParam(name = "price", required = true) double price,
			@RequestParam(name = "discount", required = true) double discount) throws ParseException {
		
		Offer o=new Offer();
		boolean success = true;
		
			
			Event e = eventService.getEventRepository().findByEventID(id);
			if(e!=null){
				o.setEvent(e);
			}
			else{
				success=false;
			}
				
			
			if (!(seatColumn.equals("")))
				o.setSeatColumn(seatColumn);
			else
				success = false;

			if (!(seatRow.equals("")))
				o.setSeatRow(seatRow);
			else
				success = false;

			if (!deadline.equals("")) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date parsedDate = dateFormat.parse(deadline);
					java.sql.Timestamp ts1 = new java.sql.Timestamp(parsedDate.getTime());
					o.setOfferDeadline(ts1);
				} catch (Exception ex) {// this generic but you can control
										// another types of exception
					ex.printStackTrace();
				}
			} else
				success = false;

			if ((visible.toUpperCase().equals("TRUE") || visible.toUpperCase().equals("FALSE"))) {
				if (visible.equals("TRUE"))
					o.setOfferIsVisible(true);
				else
					o.setOfferIsVisible(false);
			} else {
				success = false;
			}

			if (ticksavl > 0) {
				o.setOfferTicksAvl(ticksavl);
			} else {
				success = false;
			}

			if (price >= 0) {
				o.setOfferPrice(price);
			} else {
				success = false;
			}

			if (discount > 0) {
				o.setOfferDiscount(discount);
			} else {
				success = false;
			}

			// *TODO: Checken of user en pasword uniek is. passwords in hash
			// opslaan.*//
			if (success == true)
				return offerService.getOfferRepository().saveAndFlush(o); // immediate
			else
				return null;
		
	}

	// Deze methode werkt nog niet. Test zelf maar :P Ik snap wat er fout is,
	// maar weet nog niet hoe het op te lossen
	@RequestMapping("/browseOffersByEventID")
	@ResponseBody
	public List<Offer> browseOffers(@RequestParam(name = "id", required = true) long eventID) {
		Event e = eventService.getEventRepository().findByEventID(eventID);
		if (e == null) {
			System.out.println("no event with ID=" + eventID);
			return null;
		} else {
			return e.getOffers();
		}
	}

	@RequestMapping("/updateOffer")
	@ResponseBody
	public Offer updateOffer(@RequestParam(name = "id", required = true) long id,
			@RequestParam(name = "seatRow", required = true) String seatRow,
			@RequestParam(name = "seatColumn", required = true) String seatColumn,
			@RequestParam(name = "dl", required = true) String deadline,
			@RequestParam(name = "visible", required = true) String visible,
			@RequestParam(name = "ticksavl", required = true) int ticksavl,
			@RequestParam(name = "price", required = true) double price,
			@RequestParam(name = "discount", required = true) double discount) throws ParseException {
		Offer o = offerService.getOfferRepository().findByOfferID(id);
		System.err.println("TEST");
		boolean success = true;

		if (o != null) {
			if (!(seatColumn.equals("")))
				o.setSeatColumn(seatColumn);
			else
				success = false;

			if (!(seatRow.equals("")))
				o.setSeatRow(seatRow);
			else
				success = false;

			if (!deadline.equals("")) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date parsedDate = dateFormat.parse(deadline);
					java.sql.Timestamp ts1 = new java.sql.Timestamp(parsedDate.getTime());
					o.setOfferDeadline(ts1);
				} catch (Exception ex) {// this generic but you can control
										// another types of exception
					ex.printStackTrace();
				}
			} else
				success = false;

			if ((visible.toUpperCase().equals("TRUE") || visible.toUpperCase().equals("FALSE"))) {
				if (visible.equals("TRUE"))
					o.setOfferIsVisible(true);
				else
					o.setOfferIsVisible(false);
			} else {
				success = false;
			}

			if (ticksavl > 0) {
				o.setOfferTicksAvl(ticksavl);
			} else {
				success = false;
			}

			if (price >= 0) {
				o.setOfferPrice(price);
			} else {
				success = false;
			}

			if (discount > 0) {
				o.setOfferDiscount(discount);
			} else {
				success = false;
			}

			// *TODO: Checken of user en pasword uniek is. passwords in hash
			// opslaan.*//
			if (success == true)
				return offerService.getOfferRepository().saveAndFlush(o); // immediate
																			// change
																			// in
																			// database!
			else
				return null;
		} else {
			System.out.println("Event to update was not found");
			return null;
		}
	}

	@RequestMapping("/findByOfferID")
	@ResponseBody
	public Offer findByOfferID(@RequestParam(name = "id", required = true) long offerID) {
		Offer o = offerService.getOfferRepository().findByOfferID(offerID);
		if (o == null) {

			return null;
		} else {
			return o;
		}
	}

	@RequestMapping("/browseOffersByPartnerID")
	@ResponseBody
	public List<Offer> browseOfferByPartnerID(@RequestParam(name = "id", required = true) long partnerID) {
		List<Offer> offers = offerService.getOfferRepository().findByOfferPartnerID(partnerID);
		if (offers == null) {
			System.out.println("no offers with oartnerID=" + partnerID);
			return null;
		} else {
			return offers;
		}
	}

	@RequestMapping("/getEventIDByOfferID")
	@ResponseBody
	public long getEventIDByOfferID(@RequestParam(name = "id", required = true) long offerID) {
		Offer o = offerService.getOfferRepository().findByOfferID(offerID);

		if (o == null) {
			System.out.println("no offers with oartnerID=" + offerID);
			return -1;
		} else {
			return o.getEvent().getEventID();
		}
	}
	@RequestMapping("/deleteOffer")
	@ResponseBody
	public boolean deleteOffer(@RequestParam(value = "id", required = true) long id) {
		Offer o = offerService.getOfferRepository().findByOfferID(id);
		if (o!=null) {
			offerService.getOfferRepository().delete(o);
			return true;
		} else {
			System.out.println("Offer(" + id + ") werd niet in de Database gevonden");
			return false;
		}
	}

	/// USER REQUESTMAPPINGS ///

	// value = "/createUser", method = RequestMethod.PUT

	@RequestMapping("/createUser")
	@ResponseBody
	public User createUser(@RequestParam(name = "userId", required = true) long id,
			@RequestParam(name = "userFirstName", required = true) String firstname,
			@RequestParam(name = "userLastName", required = true) String lastname,
			@RequestParam(name = "userEmail", required = false) String email,
			@RequestParam(name = "userPhoto", required = false) String url) {
		User u = userRepository.findByFacebookID(id);
		if (u == null) {
			Location l = locationRepository.findByLocationID(500);
			u = new User(firstname, id, lastname, email, "", "/", url, l);
			userRepository.saveAndFlush(u);
			return u;
		} else {
			return u;
		}
	}
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public User updateUser(@RequestParam(name = "userId", required = true) long id,
			@RequestParam(name = "userFirstName", required = true) String firstname,
			@RequestParam(name = "userLastName", required = true) String lastname,
			@RequestParam(name = "userEmail", required = false) String email,
			@RequestParam(name = "userPhoto", required = false) String url,
			@RequestParam(name = "userPhone", required = false) String phone) {
		User u = userRepository.findByUserID(id);
		if (u != null) {
			u.setUserEmail(email);
			u.setUserFirstName(firstname);
			u.setUserLastName(lastname);
			u.setUserPhone(phone);
			u.setUserPhotoPath(url);
			userRepository.saveAndFlush(u);
			return u;
		} else {
			return u;
		}
	}


	@RequestMapping("/browseUsers")
	@ResponseBody
	public Boolean browseUsers(@RequestParam(name = "userId", required = true) long id) {
		return userRepository.exists(id);
	}

	@RequestMapping("/browseFacebookUsers")
	@ResponseBody
	public User browseFacebookUsers(@RequestParam(name = "id", required = true) long fbid) {
		return userRepository.findByFacebookID(fbid);
	}

	// PURCHASE REQUESTMAPPINGS ///

	@RequestMapping("/createPurchase")
	@ResponseBody
	public Purchase createPurchase() {
		Timestamp ts = new Timestamp((new java.util.Date()).getTime());
		User u = userRepository.findByUserID(2);
		Offer o = offerService.getOfferRepository().findByOfferID(8);
		Purchase p = new Purchase(o, u, ts);
		purchaseRepository.save(p);
		return p;
	}
	
	//MAIL //
	
	  
    @RequestMapping("/SendMail")
    public String SendMailAdmin(@RequestParam(value="sender", required=true, defaultValue="sender unknown") String Sender,
    							@RequestParam(value="receiver", required=true, defaultValue="receiver unknown") String Receiver,
    							@RequestParam(value="subject", required=true, defaultValue="Subject unknown" )String Subject,
    							@RequestParam(value="content", required=true, defaultValue="Content unknown" )String Content) {
        	System.err.println("SEND MAIL");
    		adminMail s = new adminMail(Sender, Receiver, Subject, Content);
        	
        	
        	Thread t = new Thread(s, "mail thread");
    		t.run();
    		return "index";
    		
    	
    }

	// SEATS REQUESTMAPPINGS ///

	@RequestMapping("/passSelectedStoelen")
	@ResponseBody
	public void passSelectedStoelen(@RequestParam(name = "id", required = true) long id,
			@RequestParam(name = "offerids", required = true) long[] offerids,
			@RequestParam(name = "userid", required = true) long userid) {
		
		/*System.err.println("USER = " + userid);
		for (int i = 0; i < offerids.length; i++) {
			System.out.println("->" + offerids[i]);
		}
		System.out.println("eventid is: " + id);

		l.fillLocker();
		*/
		l.setSeatToBook(offerids);
		l.setEvent(id);
		l.setUser(userid);
		l.fillLocker();
		Thread t = new Thread(l, "mijn thread");

		t.start();
		synchronized (t) {
			try {
				System.out.println("Waiting for t1 to complete...");
				t.wait();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			if (l.b)
				System.out.println("boeking is succes");
			else
				System.out.println("boeking failed");

		}

		// end mousti's playground

	}
	
	@RequestMapping("/cancelbooking")
	@ResponseBody
	public void cancelbooking(@RequestParam(name = "id", required = true) long eventid,
			@RequestParam(name = "offerids", required = true) long[] offerids,
			@RequestParam(name = "userid", required = true) long userid) {
		
		System.err.println("Cancel Booking");
		System.err.println("EVENTID: " + eventid);
		System.err.println("OFFERIDS: ");
		for(int i=0;i<offerids.length;i++){
			System.err.println(offerids[i]);
		}
		System.err.println("USERID: "+ userid);
		
		l.fillLocker();
		
		Thread c;
		cancelHandler.setEventID(eventid);
		
		//synchronized (cancelHandler.lock) 
		synchronized (cancelHandler)
		{
			//set cancelHandler attributes
			cancelHandler.setEventID(eventid);
			cancelHandler.setOfferID(offerids);
			cancelHandler.setUserID(userid);
			
			//Create and start thread
			c = new Thread(cancelHandler, "Cancel Thread");
			c.start();	
		}
		synchronized (c) {
			try {
				c.wait();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/paybooking")
	@ResponseBody
	public void paybooking(@RequestParam(name = "id", required = true) long eventid,
			@RequestParam(name = "offerids", required = true) long[] offerids,
			@RequestParam(name = "userid", required = true) long userid) {
		
	}
	
	
}
