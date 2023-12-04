package com.ingrid.User.Demo;

import com.ingrid.User.Demo.Services.UserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class UserDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDemoApplication.class, args);
	}

	@RequestMapping(value = "/api")
	@RestController
	public static class UserDemo {
		@Autowired
		private UserDemoService userDemoService;

		// INFO /api/messages
		@GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity getMessages() {
			String messages = userDemoService.getMessages();
			return ResponseEntity.ok(messages);
		}

		// INFO /api/messages/count
		@GetMapping(value = "/messages/count", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Long> getMessageCount() {
			long messageCount = userDemoService.getMessageCount();
			return ResponseEntity.status(HttpStatus.OK).body(messageCount);
		}

		// INFO /api/messages
		@PostMapping(value = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ResponseEntity storeMessage(@RequestBody UserMessageDto message) {
			userDemoService.storeMessage(message);
			return ResponseEntity.noContent().build();
		}

		public static class UserMessageDto {
			public String content;
		}
	}
}
