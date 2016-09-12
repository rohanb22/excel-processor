

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rohan.model.ExcelFile;
import com.rohan.model.ValidationResult;
import com.rohan.service.FileUploadService;
import com.rohan.validator.FileValidator;

@Controller
@RequestMapping("/single")
public class SingleFileUploadController {

    @Autowired
    private FileValidator fileValidator;

    @Autowired
    FileUploadService fileUploadServiceImpl;
    
    @ModelAttribute
    public ExcelFile fileBucket(){
        return new ExcelFile();
    }

    @InitBinder
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String single(){
        return "single";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleFormUpload(@Valid ExcelFile bucket,
                                   BindingResult result,
                                   RedirectAttributes redirectMap) throws IOException {
    	ValidationResult validationResult = null;
		
        if (result.hasErrors()){
            return "single";
        }
        validationResult = fileUploadServiceImpl.uploadFile(bucket.getFile());
       
        if (result.hasErrors()){
            return "single";
        }
        
        /*MultipartFile file = bucket.getFile();
        InputStream in = file.getInputStream();
        File destination = new File("/tmp/" + file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(in, destination);

        redirectMap.addFlashAttribute("filename", file.getOriginalFilename());*/
        return "redirect:success";
    }
}
