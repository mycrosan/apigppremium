package br.compneusgppremium.api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("api/upload")
class ImageController {
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty()){
            throw new RuntimeException("Arquivo não valido!");
        }
        String folder = "/Users/sandy/Documents/Projetos/gp-premium";
        try {
            Path pathFolder = Paths.get(folder);
            Files.createDirectories(pathFolder);

            Path pathFile = Paths.get(folder + file.getOriginalFilename());
            Files.write(pathFile, file.getBytes());


        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Tudo ok Picareta Dev");
        return new ResponseEntity(HttpStatus.OK);
    }

}



//@Controller
//class FileUploadController {
//
//    private final StorageService storageService;
//
//    @Autowired
//    public FileUploadController(StorageService storageService) {
//        this.storageService = storageService;
//    }
//
//    @GetMapping("/api/upload")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
//    @PostMapping("/api/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/";
//    }
//
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
//
//}