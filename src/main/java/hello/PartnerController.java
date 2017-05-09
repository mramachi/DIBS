package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.model.Partner;
import hello.repo.PartnerRepository;

@Controller
@RequestMapping("/partner/*")
public class PartnerController {
	private static final int PAGE_SIZE = 9;
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@RequestMapping("/createPartner")
	@ResponseBody
	public Partner createPartner(@RequestParam(name="n", required=true) String pName, @RequestParam(name="email", required=true) String pEmail, @RequestParam(name="link", required=false, defaultValue="") String pLink){
		Partner p = new Partner(pName, pEmail, pLink);
		Partner check = partnerRepository.findByPartnerName(pName);
		if(check!=null){
			if(check.getPartnerLink() == null) check.setPartnerLink("");
			boolean emailEqual = check.getPartnerEmail().equals(p.getPartnerEmail());
			boolean linkEqual = check.getPartnerLink().equals(p.getPartnerLink());
			
			if (emailEqual && linkEqual) { //duplicate
				System.out.println("This partner alreay exists");
			}
			else {
				System.out.println("Use the update method to update records");
			}
			return null;
		}
		else {
			partnerRepository.save(p);
			System.out.println("New partner saved");
			return p;
		}
	}
	
	//@RequestMapping(value="/updatePartner", method = RequestMethod.POST)
	@RequestMapping("/updatePartner")
	@ResponseBody
	public Partner updatePartner(@RequestParam(name="id", required=true) long id,
								@RequestParam(name="name", required=true) String pName, 
								@RequestParam(name="contactfn", required=true) String pfName,
								@RequestParam(name="contactln", required=true) String plName,
								@RequestParam(name="username", required=true) String username,
								@RequestParam(name="password", required=true) String password,
								@RequestParam(name="email", required=true, defaultValue="") String pEmail,
								@RequestParam(name="link", required=false, defaultValue="") String pLink){
		Partner p = partnerRepository.findByPartnerID(id);
		System.err.println(pEmail);
		if(p!=null){
			if(!pEmail.equals(""))
				p.setPartnerEmail(pEmail);
			if(!pLink.equals(""))
				p.setPartnerLink(pLink);
			if(!pName.equals(""))
				p.setPartnerName(pName);
			if(!pfName.equals(""))
				p.setPartnerContactFirstName(pfName);
			if(!plName.equals(""))
				p.setPartnerContactLastName(plName);
			if(!username.equals(""))
				p.setUsername(username);
			if(!password.equals(""))
				p.setPassword(password);
		
			//*TODO: Checken of user en pasword uniek is. passwords in hash opslaan.*//
			return partnerRepository.saveAndFlush(p); // immediate change in database!
		
		}
		else {
			System.out.println("Partner to update was not found");
			return null;
		}
	}
	
	//Pageable voorbeeld. Geeft partners in pages van PAGE_SIZE terug
		@RequestMapping("/pageable")
		@ResponseBody
		public Page<Partner> page(@RequestParam(name="page", required=false, defaultValue="1") int pageNumber){
			PageRequest req = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "eventName");
			return partnerRepository.findAll(req);
		}
		
		//find Partner By ID
		@RequestMapping("/findPartnerByID")
		@ResponseBody
		public Partner getPartnerByID(@RequestParam(name="id", required=true, defaultValue="1") long partnerid ){
			return partnerRepository.findByPartnerID(partnerid);
		}

}
