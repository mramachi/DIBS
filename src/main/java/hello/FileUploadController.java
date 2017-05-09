package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.model.Partner;
import hello.model.User;
import hello.repo.EventRepository;
import hello.repo.PartnerRepository;
import hello.repo.UserRepository;

@Controller
public class FileUploadController {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	PartnerRepository partnerRepository;
	
	/// USER METHODS ///
	@RequestMapping(method = RequestMethod.GET, value = "/user/uploadPhoto")
	public String provideUserUploadInfo(Model model) {
		File rootFolder = new File(Application.img_user);
		//System.err.println(Application.img_user+": "+rootFolder);
		List<String> fileNames = Arrays.stream(rootFolder.listFiles())
			.map(f -> f.getName())
			.collect(Collectors.toList());
		
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/uploadPhoto")
	public String handleFileUpload(@RequestParam("name") String name,
								   @RequestParam("file") MultipartFile file,
								   @RequestParam("id") long id,
								   RedirectAttributes redirectAttributes) {
		
		
		
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:/";
		}

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(Application.img_user + "/" + name)));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + name + "!");
				
				
				User u = userRepository.findByUserID(id);
				u.setUserPhotoPath("../img/user/".concat(name));
				userRepository.saveAndFlush(u);
				
				File rootFolder = new File(Application.img_user);
				System.err.println(Application.img_user+": "+rootFolder);
				List<String> fileNames = Arrays.stream(rootFolder.listFiles())
					.map(f -> f.getName())
					.collect(Collectors.toList());
				
				return "redirect:/editprofilepage?id=".concat(id+"");
			}
			catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		}
		else {
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name + " because the file was empty");
		}

			return "redirect:/";
		}
	
	
	/// EVENT METHODS ///
	@RequestMapping(method = RequestMethod.GET, value = "/event/uploadPhoto")
	public String provideEventUploadInfo(Model model) {
		File rootFolder = new File(Application.img_event);
		System.err.println(Application.img_event+": "+rootFolder);
		List<String> fileNames = Arrays.stream(rootFolder.listFiles())
			.map(f -> f.getName())
			.collect(Collectors.toList());
		
		return "uploadFormEvent";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/event/uploadPhoto")
	public String handleEventFileUpload(@RequestParam("name") String name,
								   @RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {
		
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:/";
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:/";
		}

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(Application.img_event + "/" + name)));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + name + "!");
			}
			catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		}
		else {
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name + " because the file was empty");
		}

		return "uploadFormEvent";
	}
	
	
	/// PARTNER METHODS ///
	@RequestMapping(method = RequestMethod.GET, value = "/partner/uploadPhoto")
	public String providePartnerUploadInfo(Model model) {
		File rootFolder = new File(Application.img_event);
		System.err.println(Application.img_partner+": "+rootFolder);
		List<String> fileNames = Arrays.stream(rootFolder.listFiles())
			.map(f -> f.getName())
			.collect(Collectors.toList());
		
		return "uploadFormPartner";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/partner/uploadPhoto")
	public String handlepartnerFileUpload(@RequestParam("name") String name,
									@RequestParam("id") long id,
								   @RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {
		
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:/";
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:/";
		}

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(Application.img_partner + "/" + name)));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + name + "!");
				
				Partner p = partnerRepository.findByPartnerID(id);
				p.setPartnerImagePath("../img/partner/".concat(name));
				partnerRepository.saveAndFlush(p);
				return "redirect:/crud/editPartner?id=".concat(id+"");
				
			}
			catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		}
		else {
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name + " because the file was empty");
		}

		return "redirect:/";
	}
}