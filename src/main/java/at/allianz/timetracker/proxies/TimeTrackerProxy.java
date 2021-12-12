package at.allianz.timetracker.proxies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import at.allianz.timetracker.dtos.TimeRecord;

@Component
public class TimeTrackerProxy {

	private String timeRecordsResourceUrl = System.getenv("TIME_TRACKER_URL") != null
			? System.getenv("TIME_TRACKER_URL")
			: "http://localhost:8080/records";

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param email  optional search query, email address
	 * @param offset optional search query, skip the first n results
	 * @param length optional search query, limit response to m results
	 * @return the found records or an empty list if none were found
	 */
	public List<TimeRecord> getRecords(Optional<String> email, Optional<Long> offset, Optional<Long> length) {
		TimeRecord[] records = (restTemplate.getForObject(getGetUrlParams(email, offset, length), TimeRecord[].class));
		return cleanRecords(records);
	}

	/**
	 * sends a POST to the backend with the given time record
	 * 
	 * @param timeRecord the new {@link TimeRecord} to add
	 */
	public void addRecord(TimeRecord timeRecord) {
		restTemplate.postForEntity(timeRecordsResourceUrl, getRequest(timeRecord), TimeRecord.class);
	}

	/**
	 * Removes any null records from the result
	 * @param records a list of {@link TimeRecord}s
	 * @return a list of records with the null entries omitted
	 */
	private List<TimeRecord> cleanRecords(TimeRecord[] records) {
		if (null == records) {
			return Collections.emptyList();
		}
		if (Arrays.asList(records).contains(null)) {
			return Arrays.asList(Arrays.stream(records).filter(Objects::nonNull).toArray(TimeRecord[]::new));
		}
		return Arrays.asList(records);
	}

	/**
	 * Creates the url params for the get request
	 * 
	 * @param email  the optional email query
	 * @param offset the optional offset query
	 * @param length the optional length query
	 * @return the created url params
	 */
	private String getGetUrlParams(Optional<String> email, Optional<Long> offset, Optional<Long> length) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(timeRecordsResourceUrl);
		if (email.isPresent()) {
			uriBuilder.queryParam("email", email.get());
		}
		if (offset.isPresent()) {
			uriBuilder.queryParam("offset", offset.get());
		}
		if (length.isPresent()) {
			uriBuilder.queryParam("length", length.get());
		}
		return uriBuilder.encode().toUriString();
	}

	/**
	 * Generates a request based on the given {@link TimeRecord}
	 * 
	 * @param timeRecord the record from which to generate the request
	 * @return the generated request
	 */
	private HttpEntity<MultiValueMap<String, String>> getRequest(TimeRecord timeRecord) {
		return new HttpEntity<>(getPostParams(timeRecord), getPostHeaders());
	}

	/**
	 * Generates the post request headers
	 * 
	 * @return the generated headers
	 */
	private HttpHeaders getPostHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	/**
	 * Generates the post request params based on the given {@link TimeRecord}
	 * 
	 * @param timeRecord the {@link TimeRecord} from which to generate the params
	 * @return the generated params
	 */
	private MultiValueMap<String, String> getPostParams(TimeRecord timeRecord) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("email", timeRecord.getEmail());
		params.add("start", timeRecord.getStart());
		params.add("end", timeRecord.getEnd());
		return params;
	}

}
