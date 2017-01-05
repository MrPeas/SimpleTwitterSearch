package com.example.controller.profile;

import com.example.config.PictureUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

/**
 * Created by Janusz on 05.01.2017.
 */
@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
    private final Resource picturesDir;
    private final Resource anonymousPicture;

    @Autowired
    PictureUploadController(PictureUploadProperties uploadProperties){
        picturesDir=uploadProperties.getUploadPath();
        anonymousPicture=uploadProperties.getAnonymousPicture();
    }

    @RequestMapping("upload")
    public String uploadPage(){
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String onUpload(MultipartFile file,
                           RedirectAttributes redirectAttributes, Model model) throws IOException{
        if(file.isEmpty()||!isImage(file)){
            redirectAttributes.addFlashAttribute("error");
            return "redirect:/upload";
        }
            Resource picturePath=copyFileToPictures(file);
            model.addAttribute("picturePath",picturePath);
        return "profile/uploadPage";
    }

    @RequestMapping("/uploadPicture")
    public void getUploadPicture( HttpServletResponse response,
                                  @ModelAttribute("picturePath")Resource picturePath) throws IOException {
        response.setHeader("Content-Type",
                URLConnection.guessContentTypeFromName(picturePath.getFilename()));
        IOUtils.copy(picturePath.getInputStream(),response.getOutputStream());
    }


    @RequestMapping("uploadError")
    public ModelAndView onUploadError(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error",request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE));
        return modelAndView;
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException exception){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error",exception.getMessage());
        return modelAndView;
    }
    @ModelAttribute("picturePath")
    public Resource picturePath(){
        return anonymousPicture;
    }
    private String getFileExtension(String name){
        return name.substring(name.lastIndexOf("."));
    }
    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }
    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension=getFileExtension(file.getOriginalFilename());
        File tempFile=File.createTempFile("pic",fileExtension,picturesDir.getFile());
        try(InputStream in= file.getInputStream();
            OutputStream out=new FileOutputStream (tempFile)) {
            IOUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

}
