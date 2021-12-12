package at.allianz.timetracker.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.allianz.timetracker.dtos.TimeRecord;
import at.allianz.timetracker.proxies.TimeTrackerProxy;

@RestController
@RequestMapping(TimeTrackerController.TIME_RECORDS_PATH)
public class TimeTrackerController {

	@Autowired
	private TimeTrackerProxy timeTrackerProxy;

	protected static final String TIME_RECORDS_PATH = "/";

	@CrossOrigin
	@GetMapping(value = "/records")
	public ResponseEntity<List<TimeRecord>> getRecords(@RequestParam(required = false) Optional<String> email,
			@RequestParam(required = false) Optional<Long> offset,
			@RequestParam(required = false) Optional<Long> length) {
		System.out.println("getRecords called");
		return ResponseEntity.ok(timeTrackerProxy.getRecords(email, offset, length));
	}

	@CrossOrigin
	@PostMapping(value = "/records")
	public ResponseEntity<Void> addRecord(@RequestParam Optional<TimeRecord> timeRecord) {
		System.out.println(timeRecord);
		if (timeRecord.isPresent()) {
			System.out.println("addRecord called");
			timeTrackerProxy.addRecord(timeRecord.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

}
