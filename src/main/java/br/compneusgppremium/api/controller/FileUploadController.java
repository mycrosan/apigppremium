package br.compneusgppremium.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.compneusgppremium.api.message.ResponseMessage;
import br.compneusgppremium.api.service.FilesStorageService;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@Autowired
	FilesStorageService storageService;

	@PostMapping("/api/upload")
	public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		String message = "";

		try {
			List<String> fileNames = new ArrayList<>();

			Arrays.asList(files).stream().forEach(file -> {

				String name = file.getOriginalFilename();
				String fname="", ext="", totalname="";
				System.out.println("\n\nFile name : " + name + "\n\n");
				Pattern p = Pattern.compile(".[a-zA-Z0-9]+");//find all matches where first char is '.' then alphanumeric
				Matcher m1 = p.matcher(name);//it will store all matches but we want last match eg abc.def.jpg we need .jpg ext
				while (m1.find())
				{
					ext = m1.group();
				}

//				fname = name.substring(0, name.length()-(ext.length()));
				fname = UUID.randomUUID().toString();
				System.out.println("Name : " + fname);
				System.out.println("Ext  : " + ext);
				totalname = fname;
				if(ext != "")//if ext is there concat
					totalname += ext;
				System.out.println("Full name  : " + totalname);

				storageService.save(file);
				fileNames.add(totalname);
				try {
					String filename = file.getOriginalFilename(); // Give a random filename here.
					byte[] bytes = file.getBytes();
					String insPath = "uploads/" + totalname;
					// Directory path where you want to save ;
					Files.write(Paths.get(insPath), bytes);
				}
				catch (IOException e) {
					System.out.println(e);
				}

			});

			message = "Uploaded the files successfully: " + fileNames;
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Falha ao fazer o upload do arquivo!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}



//	@GetMapping("/files")
//	public ResponseEntity<List<FileInfo>> getListFiles() {
//		List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//			String filename = path.getFileName().toString();
//			String url = MvcUriComponentsBuilder
//					.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//
//			return new FileInfo(filename, url);
//		}).collect(Collectors.toList());
//
//		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
}
