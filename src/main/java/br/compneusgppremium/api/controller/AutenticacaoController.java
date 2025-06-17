package br.compneusgppremium.api.controller;


import br.compneusgppremium.api.controller.dto.RespostaDto;
import br.compneusgppremium.api.service.TokenService;
import br.compneusgppremium.api.controller.dto.TokenDto;
import br.compneusgppremium.api.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Console;


@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer", true));
		} catch (AuthenticationException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verifique seu usuário e senha");
		}
	}
	
}
