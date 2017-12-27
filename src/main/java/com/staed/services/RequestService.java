package com.staed.services;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.staed.beans.Attachment;
import com.staed.beans.Note;
import com.staed.beans.Request;
import com.staed.beans.Info;
import com.staed.dao.AttachmentDAO;
import com.staed.dao.InfoDAO;
import com.staed.dao.NoteDAO;
import com.staed.dao.RequestDAO;

public class RequestService extends Service {
	private static RequestDAO reqDAO;
	private Request request;
	
	private static AttachmentDAO attachDAO;
	private static NoteDAO noteDAO;
	private static InfoDAO infoDAO;
	
	public RequestService() {
		reqDAO = new RequestDAO();
		request = null;
		
		attachDAO = new AttachmentDAO();
		noteDAO = new NoteDAO();
		infoDAO = new InfoDAO();
	}
	
	@Override
	public void close() {
		reqDAO.close();
		
		attachDAO.close();
		noteDAO.close();
		infoDAO.close();
	}
	
	public List<Request> displayAll(String email) {
		return reqDAO.getAllRequest(email);
	}
	
	public String displayOne(int id, String email) {
		request = reqDAO.getRequest(id, email);
		int rid = request.getId();
		
		Gson gson = new Gson();
		String req = gson.toJson(request);
		String info = gson.toJson(infoDAO.getInfo(rid));
		String notes = gson.toJson(noteDAO.getNotes(rid));
		String attachments = gson.toJson(attachDAO.getAttachments(rid));
		
		return mergeJson(req, info, notes, attachments);
	}
	
	public boolean add(String email, int evtTypeId, int formatId,
			int state, float cost, LocalDate evtDate, Period timeMissed,
			LocalDate lastReviewed) {
		Request req = new Request(0, email, evtTypeId, formatId, state, cost,
								  evtDate, timeMissed, lastReviewed);
		// TODO retrieve created request for the requestId
		Info info;
		Attachment file;
		Note note;
		
		reqDAO.addRequest(req);
		return false;
	}
	
	// TODO Service ModifyRequest
	public boolean modifyRequest() {
		Request obj = null;
		//reqDAO.updateRequest();
		return false;
	}
	
	private String mergeJson(String ...jsons) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		
		Collection<Map<String, Object>> jsonMaps = new ArrayList<>();
		for (String json : jsons) {
			jsonMaps.add(gson.fromJson(json, type));
		}
		
		Map<String, Object> prev = new HashMap<>();
		Iterator<Map<String, Object>> jsonIter = jsonMaps.iterator();
		while (jsonIter.hasNext()) {
			Map<String, Object> cur = jsonIter.next();
			
			prev = Stream.of(prev, cur)
				.map(Map::entrySet)
				.flatMap(Set::stream)
				.collect(Collectors.toMap(
						Entry::getKey,
						Entry::getValue,
						(oldEntry, newEntry) -> { 
							return oldEntry; 
						}
				));
		}
		
		return gson.toJson(prev);
	}
	
}